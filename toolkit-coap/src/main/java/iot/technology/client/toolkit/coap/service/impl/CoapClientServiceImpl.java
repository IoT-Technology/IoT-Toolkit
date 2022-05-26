package iot.technology.client.toolkit.coap.service.impl;

import iot.technology.client.toolkit.coap.service.CoapClientService;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import java.net.URI;

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
}
