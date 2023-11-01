package iot.technology.client.toolkit.coap.service;

import iot.technology.client.toolkit.common.utils.security.CredentialsUtil;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.Response;
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
import java.net.URISyntaxException;
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


    public CoapResponse getResponse(URI uri) {
        CoapResponse response = null;
        try {
        CoapClient client = new CoapClient(uri);
        CoapEndpoint.Builder builder = new CoapEndpoint.Builder()
                .setConfiguration(configuration)
                .setConnector(dtlsConnector);
        client.setEndpoint(builder.build());
        response = client.get();
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
