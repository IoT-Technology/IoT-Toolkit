/*
 * Copyright © 2019-2023 The Toolkit Authors
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
import iot.technology.client.toolkit.common.utils.MimeTypeUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Response;
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


	public int coapContentType(String contentType) {
		int coapContentTypeCode = -1;
		try {
			coapContentTypeCode = Integer.parseInt(contentType);
		} catch (NumberFormatException ignored) {
		}
		return (coapContentTypeCode < 0) ? MediaTypeRegistry.parse(contentType) : coapContentTypeCode;
	}


	public CoapClient getCoapClient(URI uri) {
		return new CoapClient(uri);
	}


	public String requestInfo(String method, String path) {
		return "" + cyan(method.toUpperCase(Locale.ROOT) + " " + cyan(path));
	}


	public String prettyPrint(CoapResponse coapResponse, String header) {
		if (coapResponse == null) {
			return CommandLine.Help.Ansi.AUTO.string("@|bold,red NULL response!|@");
		}

		Response r = coapResponse.advanced();
		int httpStatusCode = r.getCode().codeClass * 100 + r.getCode().codeDetail;
		HttpStatus httpStatus = HttpStatus.resolve(httpStatusCode);
		String status =
				colorText(String.format("%s-%s", httpStatusCode, Objects.requireNonNull(httpStatus).getReasonPhrase()),
						httpStatus.isError() ? "red" : "cyan");

		String rtt = (r.getApplicationRttNanos() != null) ? "" + r.getApplicationRttNanos() : "";

		StringBuilder sb = new StringBuilder();
		String separator = "----------------------------------- %s -----------------------------------";
		String response = String.format(separator, bundle.getString("coap.response"));
		sb.append(green(response))
				.append(StringUtil.lineSeparator());
		if (StringUtils.hasText(header)) {
			sb.append(header).append(StringUtil.lineSeparator());
		}
		sb.append(
						String.format("MID: %d, Type: %s, Token: %s, RTT: %sms", r.getMID(), cyan(r.getType().toString()), r.getTokenString(), rtt))
				.append(StringUtil.lineSeparator());
		sb.append(String.format("Options: %s", r.getOptions().toString()))
				.append(StringUtil.lineSeparator());
		sb.append(String.format("Status : %s, Payload: %dB", status, r.getPayloadSize()))
				.append(StringUtil.lineSeparator());
		String payload = String.format(separator, bundle.getString("coap.payload"));
		sb.append(green(payload)).append(StringUtil.lineSeparator()).append(StringUtil.lineSeparator());
		if (r.getPayloadSize() > 0 && MediaTypeRegistry.isPrintable(r.getOptions().getContentFormat())) {
			sb.append(prettyPayload(r)).append(StringUtil.lineSeparator());
		}
		sb.append(green(separator));

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
		if (bundle.getLocale().equals(Locale.CHINESE)) {
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),bold " +
					"       RFC7252 CoAP (受限制的应用协议)" + "|@") + "%n");
			System.out.format("" + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"受限应用协议(CoAP)是一种专门的web传输协议" + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"用于物联网中的约束节点和约束网络." + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"该协议专为设备对设备的应用而设计，例如智慧能源或楼宇自动化等场景." + "|@") + "%n");
			System.out.format("" + "%n");
			System.out.format(green("-------------------------- 协议 ---------------------------------") + "%n");
			System.out.format("|      0        |      1        |      2        |      3        |%n");
			System.out.format("|7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|%n");
			System.out.format("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+%n");
			System.out.format("|版本|类型|  TKL |    响应码      |            消息编号         |%n");
			System.out.format("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+%n");
			System.out.format("|   消息会话-如果有,消息会话长度字节(TKL) ...                   |%n");
			System.out.format("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+%n");
			System.out.format("|   可选项 (如果有   ) ...                                      |%n");
			System.out.format("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+%n");
			System.out.format("|1 1 1 1 1 1 1 1|    负载内容 (if any) ...                      |%n");
			System.out.format("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+%n");
			System.out.format("" + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),italic " + "官方地址: "
					+ "https://coap.technology/" + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),italic " + "中文协议文档: "
					+ "https://iot.mushuwei.cn/#/coap/" + "|@") + "%n");
		} else {
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),bold " +
					"RFC7252 CoAP (Constrained Application Protocol)" + "|@") + "%n");
			System.out.format("" + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"The Constrained Application Protocol (CoAP) is a specialized web transfer protocol" + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"for use with constrained nodes and constrained networks in the Internet of Things." + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"The protocol is designed for machine-to-machine (M2M) applications such as smart energy and building automation." +
					"|@")
					+ "%n");
			System.out.format("" + "%n");
			System.out.format(green("------------------------ protocol -------------------------------") + "%n");
			System.out.format("|      0        |      1        |      2        |      3        |%n");
			System.out.format("|7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|7 6 5 4 3 2 1 0|%n");
			System.out.format("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+%n");
			System.out.format("|Ver| T |  TKL  |     Code      |            Message ID         |%n");
			System.out.format("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+%n");
			System.out.format("|   Token (if any, TKL bytes) ...                               |%n");
			System.out.format("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+%n");
			System.out.format("|   Options (if any) ...                                        |%n");
			System.out.format("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+%n");
			System.out.format("|1 1 1 1 1 1 1 1|   Payload (if any) ...                        |%n");
			System.out.format("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+%n");
			System.out.format("" + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),italic " + "The Official address: "
					+ "https://coap.technology/" + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),italic " + "The English reference: "
					+ "https://www.rfc-editor.org/rfc/rfc7252.html" + "|@") + "%n");
		}
	}

	private String typeNames(List<String> contentTypes) {
		return contentTypes.stream()
				.map(Integer::valueOf)
				.map(ct -> MediaTypeRegistry.toString(ct) + " (" + ct + ")")
				.collect(Collectors.joining(", "));
	}

	public static String prettyPayload(Response r) {
		if (r.getOptions().toString().contains(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
			return cyan(prettyJson(r.getPayloadString()));
		} else if (r.getOptions().toString().contains(MimeTypeUtils.APPLICATION_XML_VALUE)) {
			return cyan(prettyXml(r.getPayloadString()));
		} else if (r.getOptions().toString().contains("application/link-format")) {
			return cyan(prettyLink(r.getPayloadString()));
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

	public static String cyan(String text) {
		return colorText(text, "cyan");
	}

	public static String red(String text) {
		return colorText(text, "red");
	}

	public static String green(String text) {
		return colorText(text, "green");
	}

	public static String colorText(String text, String color) {
		return CommandLine.Help.Ansi.AUTO.string("@|bold," + color + " " + text + "|@");
	}
}
