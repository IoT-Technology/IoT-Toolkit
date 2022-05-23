package iot.technology.client.toolkit.coap.service;

import iot.technology.client.toolkit.coap.service.impl.CoapClientServiceImpl;

/**
 * @author mushuwei
 */
public class CoapFactory {

	public static CoapClientService getService() {
		return new CoapClientServiceImpl();
	}
}
