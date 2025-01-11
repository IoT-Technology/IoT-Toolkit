/*
 * Copyright © 2019-2025 The Toolkit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iot.technology.client.toolkit.coap.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import iot.technology.client.toolkit.coap.domain.AvailableResource;
import iot.technology.client.toolkit.coap.domain.CoapSupportType;
import iot.technology.client.toolkit.common.constants.HttpStatus;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.utils.CollectionUtils;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.MimeTypeUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.eclipse.californium.elements.util.StringUtil;
import org.w3c.dom.Document;
import picocli.CommandLine;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author mushuwei
 */
public class CoapClientService {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    public static int coapContentType(String contentType) {
        int coapContentTypeCode = -1;
        try {
            coapContentTypeCode = Integer.parseInt(contentType);
        } catch (NumberFormatException ignored) {
        }
        return (coapContentTypeCode < 0) ? MediaTypeRegistry.parse(contentType) : coapContentTypeCode;
    }

    public String requestInfo(String method, String path) {
        return "  " + ColorUtils.colorBold((method.toUpperCase(Locale.ROOT) + ":" + path), "cyan");
    }


    public String prettyPrint(CoapResponse coapResponse, String header) {
        if (coapResponse == null) {
            return CommandLine.Help.Ansi.AUTO.string("@|bold,red NULL response!|@");
        }
        Response r = coapResponse.advanced();
        int httpStatusCode = r.getCode().codeClass * 100 + r.getCode().codeDetail;
        HttpStatus httpStatus = HttpStatus.resolve(httpStatusCode);
        String status = ColorUtils.colorBold(String.format("%s-%s", httpStatusCode, Objects.requireNonNull(httpStatus).getReasonPhrase()),
                httpStatus.isError() ? "red" : "cyan");
        String rtt = Objects.nonNull(r.getApplicationRttNanos()) ? "" + r.getApplicationRttNanos() / 1000000 : "";

        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.lineSeparator());
        String separator = "------------ %s ------------";
        // resource url
        if (StringUtils.hasText(header)) {
            sb.append(header).append(StringUtil.lineSeparator);
        }
        String responseHeader = String.format(separator, bundle.getString("coap.response"));
        //coap response
        sb.append(ColorUtils.colorBold(responseHeader, "green")).append(StringUtil.lineSeparator());
        // application RTT (round trip time)
        sb.append(String.format("RTT:        %sms", ColorUtils.colorBold(rtt, "cyan"))).append(StringUtil.lineSeparator());
        // coap type
        sb.append(String.format("Type:       %s", ColorUtils.colorBold(r.getType().toString(), "cyan"))).append(StringUtil.lineSeparator());
        // coap token
        sb.append(String.format("Token:      %s", ColorUtils.colorBold(r.getTokenString(), "cyan"))).append(StringUtil.lineSeparator());
        // coap code
        sb.append(String.format("Code:       %s", status)).append(StringUtil.lineSeparator());
        // coap MID
        sb.append(String.format("MID:        %s", ColorUtils.colorBold(r.getMID() + "", "cyan"))).append(StringUtil.lineSeparator());
        // coap options
        sb.append(String.format("Options:    %s", ColorUtils.colorBold(r.getOptions().toString(), "cyan")))
                .append(StringUtil.lineSeparator());

        String payloadHeader = String.format(separator, bundle.getString("coap.payload"));
        sb.append(ColorUtils.colorBold(payloadHeader, "green")).append(StringUtil.lineSeparator());

        // coap payload
        if (r.getPayloadSize() > 0 && MediaTypeRegistry.isPrintable(r.getOptions().getContentFormat())) {
            sb.append(prettyPayload(r)).append(StringUtil.lineSeparator());
        }
        return sb.toString();
    }

    public String prettyPrint(Response r, String header) {
        if (r == null) {
            return CommandLine.Help.Ansi.AUTO.string("@|bold,red NULL response!|@");
        }
        int httpStatusCode = r.getCode().codeClass * 100 + r.getCode().codeDetail;
        HttpStatus httpStatus = HttpStatus.resolve(httpStatusCode);
        String status = ColorUtils.colorBold(String.format("%s-%s", httpStatusCode, Objects.requireNonNull(httpStatus).getReasonPhrase()),
                httpStatus.isError() ? "red" : "cyan");
        String rtt = Objects.nonNull(r.getApplicationRttNanos()) ? "" + r.getApplicationRttNanos() / 1000000 : "";

        StringBuilder sb = new StringBuilder();
        sb.append(StringUtil.lineSeparator());
        String separator = "------------ %s ------------";
        // resource url
        if (StringUtils.hasText(header)) {
            sb.append(header).append(StringUtil.lineSeparator);
        }
        String responseHeader = String.format(separator, bundle.getString("coap.response"));
        //coap response
        sb.append(ColorUtils.colorBold(responseHeader, "green")).append(StringUtil.lineSeparator());
        // application RTT (round trip time)
        sb.append(String.format("RTT:        %sms", ColorUtils.colorBold(rtt, "cyan"))).append(StringUtil.lineSeparator());
        // coap type
        sb.append(String.format("Type:       %s", ColorUtils.colorBold(r.getType().toString(), "cyan"))).append(StringUtil.lineSeparator());
        // coap token
        sb.append(String.format("Token:      %s", ColorUtils.colorBold(r.getTokenString(), "cyan"))).append(StringUtil.lineSeparator());
        // coap code
        sb.append(String.format("Code:       %s", status)).append(StringUtil.lineSeparator());
        // coap MID
        sb.append(String.format("MID:        %s", ColorUtils.colorBold(r.getMID() + "", "cyan"))).append(StringUtil.lineSeparator());
        // coap options
        sb.append(String.format("Options:    %s", ColorUtils.colorBold(r.getOptions().toString(), "cyan")))
                .append(StringUtil.lineSeparator());

        String payloadHeader = String.format(separator, bundle.getString("coap.payload"));
        sb.append(ColorUtils.colorBold(payloadHeader, "green")).append(StringUtil.lineSeparator());

        // coap payload
        if (r.getPayloadSize() > 0 && MediaTypeRegistry.isPrintable(r.getOptions().getContentFormat())) {
            sb.append(prettyPayload(r)).append(StringUtil.lineSeparator());
        }
        return sb.toString();
    }


    public String getAvailableResources(Set<WebLink> webLinks) {
        List<AvailableResource> arList = new ArrayList<>();
        AtomicInteger i = new AtomicInteger(1);
        if (!webLinks.isEmpty()) {
            webLinks.forEach(wl -> {
                AvailableResource ar = new AvailableResource();
                ar.setRowNum(String.valueOf(i.get()));
                ar.setPath(wl.getURI());
                ar.setResourceType(CollectionUtils.listToString(wl.getAttributes().getResourceTypes()));
                ar.setContentType(typeNames(wl.getAttributes().getContentTypes()));
                arList.add(ar);
                i.getAndIncrement();
            });
        }
        return AsciiTable.getTable(arList, Arrays.asList(
                new Column().with(AvailableResource::getRowNum),
                new Column().header("Path").headerAlign(HorizontalAlign.CENTER)
                        .dataAlign(HorizontalAlign.LEFT)
                        .with(AvailableResource::getPath),
                new Column().header("Resource Type").headerAlign(HorizontalAlign.CENTER)
                        .dataAlign(HorizontalAlign.LEFT)
                        .with(AvailableResource::getResourceType),
                new Column().header("Content Type").headerAlign(HorizontalAlign.CENTER)
                        .dataAlign(HorizontalAlign.LEFT)
                        .with(AvailableResource::getContentType)));
    }


    public String getSupportedMediaTypes() {
        List<CoapSupportType> stList = new ArrayList<>();
        MediaTypeRegistry.getAllMediaTypes()
                .forEach(i -> {
                    CoapSupportType supportType = new CoapSupportType();
                    supportType.setTypeId("" + i);
                    supportType.setTypeName(MediaTypeRegistry.toString(i));
                    stList.add(supportType);
                });
        return AsciiTable.getTable(stList, Arrays.asList(
                new Column().with(CoapSupportType::getRowName),
                new Column().header("Type Id").headerAlign(HorizontalAlign.CENTER)
                        .dataAlign(HorizontalAlign.LEFT).with(CoapSupportType::getTypeId),
                new Column().header("Type Name").headerAlign(HorizontalAlign.CENTER)
                        .dataAlign(HorizontalAlign.LEFT).with(CoapSupportType::getTypeName)));
    }

    public void getCoapDescription() {
        StringBuilder sb = new StringBuilder();
        if (bundle.getLocale().equals(Locale.CHINESE)) {
            sb.append(ColorUtils.colorBold("       RFC7252 CoAP (受限制的应用协议)", "cyan")).append(StringUtils.lineSeparator());
            sb.append(StringUtils.lineSeparator());
            sb.append(ColorUtils.italicText("受限应用协议(CoAP)是一种专门的web传输协议")).append(StringUtils.lineSeparator());
            sb.append(ColorUtils.italicText("用于物联网中的约束节点和约束网络.")).append(StringUtils.lineSeparator());
            sb.append(ColorUtils.italicText("该协议专为设备对设备的应用而设计，例如智慧能源或楼宇自动化等场景."))
                    .append(StringUtils.lineSeparator());
            sb.append(StringUtils.lineSeparator());
            sb.append(ColorUtils.colorBold("-------------------------- 协议 ---------------------------------", "green"))
                    .append(StringUtils.lineSeparator());
            sb.append("|      0        |      1        |      2        |      3        |").append(StringUtils.lineSeparator());
            sb.append("|7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|").append(StringUtils.lineSeparator());
            sb.append("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+").append(StringUtils.lineSeparator());
            sb.append("|版本|类型|  TKL |    响应码      |            消息编号         |").append(StringUtils.lineSeparator());
            sb.append("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+").append(StringUtils.lineSeparator());
            sb.append("|   消息会话-如果有,消息会话长度字节(TKL) ...                   |").append(StringUtils.lineSeparator());
            sb.append("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+").append(StringUtils.lineSeparator());
            sb.append("|   可选项 (如果有   ) ...                                      |").append(StringUtils.lineSeparator());
            sb.append("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+").append(StringUtils.lineSeparator());
            sb.append("|1 1 1 1 1 1 1 1|    负载内容 (if any) ...                      |").append(StringUtils.lineSeparator());
            sb.append("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+").append(StringUtils.lineSeparator());
            sb.append(StringUtils.lineSeparator());
            sb.append(ColorUtils.colorItalic("官方地址: https://coap.technology/", "cyan")).append(StringUtils.lineSeparator());
            sb.append(ColorUtils.colorItalic("中文协议文档: https://iot.mushuwei.cn/#/coap/", "cyan")).append(StringUtils.lineSeparator());
        } else {
            sb.append(ColorUtils.colorBold("RFC7252 CoAP (Constrained Application Protocol)", "cyan")).append(StringUtils.lineSeparator());
            sb.append(StringUtils.lineSeparator());
            sb.append(ColorUtils.italicText("The Constrained Application Protocol (CoAP) is a specialized web transfer protocol"))
                    .append(StringUtils.lineSeparator());
            sb.append(ColorUtils.italicText("for use with constrained nodes and constrained networks in the Internet of Things."))
                    .append(StringUtils.lineSeparator());
            sb.append(ColorUtils.italicText(
                            "The protocol is designed for machine-to-machine (M2M) applications such as smart energy and building automation."))
                    .append(StringUtils.lineSeparator());
            sb.append(StringUtils.lineSeparator());
            sb.append(ColorUtils.colorBold("------------------------ protocol -------------------------------", "green"))
                    .append(StringUtils.lineSeparator());
            sb.append("|      0        |      1        |      2        |      3        |").append(StringUtils.lineSeparator());
            sb.append("|7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|").append(StringUtils.lineSeparator());
            sb.append("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+").append(StringUtils.lineSeparator());
            sb.append("|Ver| T |  TKL  |     Code      |            Message ID         |").append(StringUtils.lineSeparator());
            sb.append("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+").append(StringUtils.lineSeparator());
            sb.append("|   Token (if any, TKL bytes) ...                               |").append(StringUtils.lineSeparator());
            sb.append("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+").append(StringUtils.lineSeparator());
            sb.append("|   Options (if any) ...                                        |").append(StringUtils.lineSeparator());
            sb.append("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+").append(StringUtils.lineSeparator());
            sb.append("|1 1 1 1 1 1 1 1|   Payload (if any) ...                        |").append(StringUtils.lineSeparator());
            sb.append("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+").append(StringUtils.lineSeparator());
            sb.append(ColorUtils.colorItalic("The Official address: https://coap.technology/", "cyan")).append(StringUtils.lineSeparator());
            sb.append(ColorUtils.colorItalic("The English reference: https://www.rfc-editor.org/rfc/rfc7252.html", "cyan"))
                    .append(StringUtils.lineSeparator());
        }
        System.out.println(sb);
    }

    private String typeNames(List<String> contentTypes) {
        return contentTypes.stream()
                .map(Integer::valueOf)
                .map(ct -> MediaTypeRegistry.toString(ct) + " (" + ct + ")")
                .collect(Collectors.joining(", "));
    }

    public static String prettyPayload(Response r) {
        if (r.getOptions().toString().contains(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
            return ColorUtils.colorBold(prettyJson(r.getPayloadString()), "cyan");
        } else if (r.getOptions().toString().contains(MimeTypeUtils.APPLICATION_XML_VALUE)) {
            return ColorUtils.colorBold(prettyXml(r.getPayloadString()), "cyan");
        } else if (r.getOptions().toString().contains("application/link-format")) {
            return ColorUtils.colorBold(prettyLink(r.getPayloadString()), "cyan");
        }
        return r.getPayloadString();
    }

    private static String prettyJson(String text) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object jsonObject = mapper.readValue(text, Object.class);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (IOException io) {
            return text;
        }
    }

    private static String prettyXml(String text) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(text);

            Transformer tform = TransformerFactory.newInstance().newTransformer();
            tform.setOutputProperty(OutputKeys.INDENT, "yes");
            tform.setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "2");
            StringWriter sw = new StringWriter();
            tform.transform(new DOMSource(document), new StreamResult(sw));
            return sw.toString();
        } catch (Exception e) {
            return text;
        }
    }

    private static String prettyLink(String text) {
        try {
            StringBuilder sb = new StringBuilder();
            for (String link : text.split(",")) {
                sb.append(link).append(StringUtil.lineSeparator());
            }
            return sb.toString();
        } catch (Exception io) {
            return text;
        }
    }

    public Response getResponse(URI uri, String protocol,
                                String identity, String sharekey,
                                String accept) {
        try {
            if (protocol.equals("UDP")) {
                var client = new CoapClient(uri);
                var acceptType = coapContentType(accept);
                return client.get(acceptType).advanced();
            } else if (protocol.equals("DTLS")) {
                if (StringUtils.isNotBlank(identity) && StringUtils.isNotBlank(sharekey)) {
                    var client = DtlsClient.initDtlsClient(identity, sharekey);
                    CoapResponse response = client.getResponse(uri, accept);
                    return response.advanced();
                }
                throw new IllegalArgumentException("coaps、psk options should be used together.");
            }
            throw new IllegalArgumentException("protocol " + protocol + " not supported!");
        } catch (ConnectorException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CoapResponse putPayload(URI uri, String protocol,
                                   String identity, String sharekey,
                                   String payloadContent, String format) {
        try {
            if (protocol.equals("UDP")) {
                var coapClient = new CoapClient(uri);
                var response = coapClient.put(payloadContent, this.coapContentType(format));
                return response;
            } else if (protocol.equals("DTLS")) {
                if (StringUtils.isNotBlank(identity) && StringUtils.isNotBlank(sharekey)) {
                    var dtlsClient = DtlsClient.initDtlsClient(identity, sharekey);
                    return dtlsClient.putPayload(uri, payloadContent, format);
                }
                throw new IllegalArgumentException("coaps、psk options should be used together.");
            }
            throw new IllegalArgumentException("protocol " + protocol + " not supported!");
        } catch (ConnectorException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CoapResponse postPayload(URI uri, String protocol,
                                    String identity, String sharekey,
                                    String payloadContent,
                                    String format, String accept) {
        try {
            if (protocol.equals("UDP")) {
                var client = new CoapClient(uri);
                return client.post(payloadContent, coapContentType(format), coapContentType(accept));
            } else if (protocol.equals("DTLS")) {
                if (StringUtils.isNotBlank(identity) && StringUtils.isNotBlank(sharekey)) {
                    var dtlsClient = DtlsClient.initDtlsClient(identity, sharekey);
                    return dtlsClient.postPayload(uri, payloadContent, format, accept);
                }
                throw new IllegalArgumentException("coaps、psk options should be used together.");
            }
            throw new IllegalArgumentException("protocol " + protocol + " not supported!");
        } catch (ConnectorException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CoapResponse deletePayload(URI uri, String protocol,
                                      String identity, String sharekey) {
        try {
            if (protocol.equals("UDP")) {
                var client = new CoapClient(uri);
                return client.delete();
            } else if (protocol.equals("DTLS")) {
                if (StringUtils.isNotBlank(identity) && StringUtils.isNotBlank(sharekey)) {
                    var dtlsClient = DtlsClient.initDtlsClient(identity, sharekey);
                    return dtlsClient.deletePayload(uri);
                }
                throw new IllegalArgumentException("coaps、psk options should be used together.");
            }
            throw new IllegalArgumentException("protocol " + protocol + " not supported!");
        } catch (ConnectorException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CoapClient getCoapClient(URI uri, String protocol,
                                           String identity, String sharekey) {
        if (protocol.equals("UDP")) {
            var client = new CoapClient(uri);
            return client;
        } else if (protocol.equals("DTLS")) {
            if (StringUtils.isNotBlank(identity) && StringUtils.isNotBlank(sharekey)) {
                var dtlsClient = DtlsClient.initDtlsClient(identity, sharekey);
                var client = dtlsClient.initCoapClient(uri);
                return client;
            }
            throw new IllegalArgumentException("coaps、psk options should be used together.");
        }
        throw new IllegalArgumentException("protocol " + protocol + " not supported!");
    }
}
