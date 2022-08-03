/*
 * Copyright © 2019-2022 The Toolkit Authors
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

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import java.net.URI;
import java.util.Set;

/**
 * @author mushuwei
 */
public interface CoapClientService {

	/**
	 * Converts content type text into CoAP's media type code.
	 * If the input is a number assumes it is already a CoAP media type code.
	 *
	 * @param contentType - Content type number or text
	 * @return CoAP media type code
	 */
	int coapContentType(String contentType);

	/**
	 * a new CoapClient that sends request to the specified URI.
	 *
	 * @param uri the specified URI
	 * @return new CoapClient
	 */
	CoapClient getCoapClient(URI uri);


	/**
	 * Formats a {@link org.eclipse.californium.core.coap.Response} into a readable String representation.
	 *
	 * @param coapResponse
	 * @param header
	 * @return the pretty print
	 */
	String prettyPrint(CoapResponse coapResponse, String header);

	/**
	 * Formats method and path into request info.
	 *
	 * @param method
	 * @param path
	 * @return
	 */
	String requestInfo(String method, String path);


	/**
	 * print coap protocol description
	 */
	void getCoapDescription();


	/**
	 * get available resources
	 *
	 * @param webLinks
	 * @return
	 */
	String getAvailableResources(Set<WebLink> webLinks);


	/**
	 * get coap media type registry
	 *
	 * @param mediaTypeRegistry CoAP Media Type Registry as defined in RFC 7252
	 * @return command out
	 */
	String getSupportedMediaTypes(MediaTypeRegistry mediaTypeRegistry);
}
