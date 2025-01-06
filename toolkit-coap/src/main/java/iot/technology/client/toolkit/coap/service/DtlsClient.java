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

import iot.technology.client.toolkit.common.utils.security.CredentialsUtil;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.config.CoapConfig;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.eclipse.californium.elements.config.Configuration;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.eclipse.californium.scandium.DTLSConnector;
import org.eclipse.californium.scandium.config.DtlsConfig;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.californium.scandium.dtls.cipher.CipherSuite;
import org.eclipse.californium.scandium.dtls.pskstore.AdvancedSinglePskStore;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * @author mushuwei
 */
public class DtlsClient {

    private static final File CONFIG_FILE = new File("Californium3SecureClient.properties");
    private static final String CONFIG_HEADER = "Californium CoAP Properties file for IoT Client";

    static {
        CoapConfig.register();
        DtlsConfig.register();
    }

    private final DTLSConnector dtlsConnector;
    private final Configuration configuration;

    public DtlsClient(DTLSConnector dtlsConnector, Configuration configuration) {
        this.dtlsConnector = dtlsConnector;
        this.configuration = configuration;
    }

    private static Configuration.DefinitionsProvider DEFAULTS = config -> {
        config.set(CoapConfig.MAX_ACTIVE_PEERS, 10);
        config.set(DtlsConfig.DTLS_ROLE, DtlsConfig.DtlsRole.CLIENT_ONLY);
        config.set(DtlsConfig.DTLS_USE_SERVER_NAME_INDICATION, false);
        config.set(DtlsConfig.DTLS_RECOMMENDED_CIPHER_SUITES_ONLY, false);
        config.set(DtlsConfig.DTLS_PRESELECTED_CIPHER_SUITES, CipherSuite.STRONG_ENCRYPTION_PREFERENCE);
        config.setTransient(DtlsConfig.DTLS_CIPHER_SUITES);
    };

    public CoapClient initCoapClient(URI uri) {
        CoapClient client = new CoapClient(uri);
        CoapEndpoint.Builder builder = new CoapEndpoint.Builder()
                .setConfiguration(configuration)
                .setConnector(dtlsConnector);
        client.setEndpoint(builder.build());
        return client;
    }

    public CoapResponse getResponse(URI uri, String accept) {
        CoapResponse response = null;
        try {
            CoapClient client = new CoapClient(uri);
            CoapEndpoint.Builder builder = new CoapEndpoint.Builder()
                    .setConfiguration(configuration)
                    .setConnector(dtlsConnector);
            client.setEndpoint(builder.build());
            var acceptType = CoapClientService.coapContentType(accept);
            response = client.get(acceptType);
            client.shutdown();
        } catch (ConnectorException | IOException e) {
            System.err.println("Error occurred while sending request: " + e);
            System.exit(-1);
        }
        return response;
    }

    public CoapResponse putPayload(URI uri, String payloadContent, String format) {
        CoapResponse response = null;
        try {
            CoapClient client = new CoapClient(uri);
            CoapEndpoint.Builder builder = new CoapEndpoint.Builder()
                    .setConfiguration(configuration)
                    .setConnector(dtlsConnector);
            client.setEndpoint(builder.build());
            var coapResponse = client.put(payloadContent, CoapClientService.coapContentType(format));
            response = coapResponse;
            client.shutdown();
        } catch (ConnectorException | IOException e) {
            System.err.println("Error occurred while sending request: " + e);
            System.exit(-1);
        }
        return response;
    }

    public CoapResponse postPayload(URI uri, String payloadContent,
                                    String format, String accept) {
        CoapResponse response = null;
        try {
            CoapClient client = new CoapClient(uri);
            CoapEndpoint.Builder builder = new CoapEndpoint.Builder()
                    .setConfiguration(configuration)
                    .setConnector(dtlsConnector);
            client.setEndpoint(builder.build());
            response = client.post(payloadContent, CoapClientService.coapContentType(format), CoapClientService.coapContentType(accept));
            client.shutdown();
        } catch (ConnectorException | IOException e) {
            System.err.println("Error occurred while sending request: " + e);
            System.exit(-1);
        }
        return response;
    }

    public CoapResponse deletePayload(URI uri) {
        CoapResponse response = null;
        try {
            var client = new CoapClient(uri);
            CoapEndpoint.Builder builder = new CoapEndpoint.Builder()
                    .setConfiguration(configuration)
                    .setConnector(dtlsConnector);
            client.setEndpoint(builder.build());
            response = client.delete();
            client.shutdown();
        } catch (ConnectorException | IOException e) {
            System.err.println("Error occurred while sending request: " + e);
            System.exit(-1);
        }
        return response;
    }


    public static DtlsClient initDtlsClient(String pskIdentity, String pskSecret) {
        Configuration configuration = Configuration.createWithFile(CONFIG_FILE, CONFIG_HEADER, DEFAULTS);
        Configuration.setStandard(configuration);

        DtlsConnectorConfig.Builder builder = DtlsConnectorConfig.builder(configuration);
        List<CredentialsUtil.Mode> modes = CredentialsUtil.DEFAULT_CLIENT_MODES;
        builder.setAdvancedPskStore(new AdvancedSinglePskStore(pskIdentity, pskSecret.getBytes()));
        CredentialsUtil.setupCredentials(builder, modes);
        // uncomment next line to load pem file for the example
        // CredentialsUtil.loadCredentials(builder, "client.pem");
        DTLSConnector dtlsConnector = new DTLSConnector(builder.build());

        DtlsClient client = new DtlsClient(dtlsConnector, configuration);
        return client;
    }
}
