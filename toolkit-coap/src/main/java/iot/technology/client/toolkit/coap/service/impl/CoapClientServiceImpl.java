package iot.technology.client.toolkit.coap.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import iot.technology.client.toolkit.coap.service.CoapClientService;
import iot.technology.client.toolkit.common.constants.HttpStatus;
import iot.technology.client.toolkit.common.utils.MimeTypeUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
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
import java.util.Locale;

/**
 * @author mushuwei
 */
public class CoapClientServiceImpl implements CoapClientService {

	@Override
	public int coapContentType(String contentType) {
		int coapContentTypeCode = -1;
		try {
			coapContentTypeCode = Integer.parseInt(contentType);
		} catch (NumberFormatException nfe) {
		}
		return (coapContentTypeCode < 0) ? MediaTypeRegistry.parse(contentType) : coapContentTypeCode;
	}

	@Override
	public CoapClient getCoapClient(URI uri) {
		return new CoapClient(uri);
	}

	@Override
	public String requestInfo(String method, String path) {
		return "" + cyan(method.toUpperCase(Locale.ROOT) + " " + cyan(path));
	}

	@Override
	public String prettyPrint(CoapResponse coapResponse, String header) {
		if (coapResponse == null) {
			return CommandLine.Help.Ansi.AUTO.string("@|bold,red NULL response!|@");
		}

		Response r = coapResponse.advanced();
		int httpStatusCode = r.getCode().codeClass * 100 + r.getCode().codeDetail;
		HttpStatus httpStatus = HttpStatus.resolve(httpStatusCode);
		String status =
				colorText(String.format("%s-%s", httpStatusCode, httpStatus.getReasonPhrase()), httpStatus.isError() ? "red" : "cyan");

		String rtt = (r.getApplicationRttNanos() != null) ? "" + r.getApplicationRttNanos() : "";

		StringBuffer sb = new StringBuffer();
		sb.append(green("----------------------------------- Response -----------------------------------"))
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
		sb.append(green("................................... Payload ...................................."))
				.append(StringUtil.lineSeparator());
		if (r.getPayloadSize() > 0 && MediaTypeRegistry.isPrintable(r.getOptions().getContentFormat())) {
			sb.append(prettyPayload(r)).append(StringUtil.lineSeparator());
		}
		sb.append(green("--------------------------------------------------------------------------------"));

		return sb.toString();
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
			tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			StringWriter sw = new StringWriter();
			tform.transform(new DOMSource(document), new StreamResult(sw));
			return sw.toString();
		} catch (Exception e) {
			return text;
		}
	}

	private static String prettyLink(String text) {
		try {
			StringBuffer sb = new StringBuffer();
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
