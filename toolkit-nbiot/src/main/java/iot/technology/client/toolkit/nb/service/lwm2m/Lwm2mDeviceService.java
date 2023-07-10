package iot.technology.client.toolkit.nb.service.lwm2m;

import iot.technology.client.toolkit.common.constants.SecurityAlgorithm;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.nb.service.lwm2m.domain.DtlsSessionLogger;
import iot.technology.client.toolkit.nb.service.lwm2m.domain.Lwm2mConfigSettingsDomain;
import iot.technology.client.toolkit.nb.service.lwm2m.domain.MyDevice;
import iot.technology.client.toolkit.nb.service.lwm2m.domain.RandomTemperatureSensor;
import org.eclipse.californium.elements.config.Configuration;
import org.eclipse.californium.scandium.config.DtlsConfig;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.leshan.client.LeshanClient;
import org.eclipse.leshan.client.LeshanClientBuilder;
import org.eclipse.leshan.client.californium.endpoint.CaliforniumClientEndpointFactory;
import org.eclipse.leshan.client.californium.endpoint.CaliforniumClientEndpointsProvider;
import org.eclipse.leshan.client.californium.endpoint.coap.CoapOscoreProtocolProvider;
import org.eclipse.leshan.client.californium.endpoint.coaps.CoapsClientEndpointFactory;
import org.eclipse.leshan.client.californium.endpoint.coaps.CoapsClientProtocolProvider;
import org.eclipse.leshan.client.engine.DefaultRegistrationEngineFactory;
import org.eclipse.leshan.client.object.LwM2mTestObject;
import org.eclipse.leshan.client.object.Oscore;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.client.resource.listener.ObjectsListenerAdapter;
import org.eclipse.leshan.client.send.ManualDataSender;
import org.eclipse.leshan.core.model.LwM2mModelRepository;

import java.io.File;
import java.security.cert.CertificateEncodingException;
import java.util.List;

import static org.eclipse.leshan.client.object.Security.*;
import static org.eclipse.leshan.core.LwM2mId.*;

/**
 * @author mushuwei
 */
public class Lwm2mDeviceService {

    private static final int OBJECT_ID_TEMPERATURE_SENSOR = 3303;
    private static final int OBJECT_ID_LWM2M_TEST_OBJECT = 3442;
    private static final String CF_CONFIGURATION_FILENAME = "Californium3.client.properties";
    private static final String CF_CONFIGURATION_HEADER = "lwm2m Client Demo - " + Configuration.DEFAULT_HEADER;


    private LeshanClient createClient(Lwm2mConfigSettingsDomain domain, LwM2mModelRepository repository) {
        try {
            // Initialize object list
            final ObjectsInitializer initializer = new ObjectsInitializer(repository.getLwM2mModel());
            initializer.setClassForObject(OSCORE, Oscore.class);

            if (domain.isBootstrap()) {
                if (domain.isDtls()) {
                    if (domain.getLwm2mChooseAlgorithm().equals(SecurityAlgorithm.PSK.getCode())) {
                        initializer.setInstancesForObject(SECURITY, pskBootstrap(domain.getServerUrl(),
                                domain.getIdentity().getBytes(), domain.getSharekey().getBytes()));
                        initializer.setClassForObject(SERVER, Server.class);
                    }
                    if (domain.getLwm2mChooseAlgorithm().equals(SecurityAlgorithm.RPK.getCode())) {
                        initializer.setInstancesForObject(SECURITY,
                                rpkBootstrap(domain.getServerUrl(), domain.getCpubk().getEncoded(),
                                        domain.getCprik().getEncoded(), domain.getSpubk().getEncoded()));
                        initializer.setClassForObject(SERVER, Server.class);
                    }
                    if (domain.getLwm2mChooseAlgorithm().equals(SecurityAlgorithm.X509.getCode())) {
                        initializer.setInstancesForObject(SECURITY,
                                x509Bootstrap(domain.getServerUrl(), domain.getCcert().getEncoded(),
                                        domain.getCprik().getEncoded(), domain.getScert().getEncoded(),
                                        domain.getCertUsage().code));
                        initializer.setClassForObject(SERVER, Server.class);
                    }
                    initializer.setInstancesForObject(SECURITY, noSecBootstrap(domain.getServerUrl()));
                    initializer.setClassForObject(SERVER, Server.class);
                }

            } else {
                if (domain.getLwm2mChooseAlgorithm().equals(SecurityAlgorithm.PSK.getCode())) {
                    initializer.setInstancesForObject(SECURITY, psk(domain.getServerUrl(), 123,
                            domain.getIdentity().getBytes(), domain.getSharekey().getBytes()));
                    initializer.setInstancesForObject(SERVER, new Server(123, domain.getLifetimeInSec()));
                } else if (domain.getLwm2mChooseAlgorithm().equals(SecurityAlgorithm.RPK.getCode())) {
                    initializer.setInstancesForObject(SECURITY,
                            rpk(domain.getServerUrl(), 123, domain.getCpubk().getEncoded(),
                                    domain.getCprik().getEncoded(), domain.getSpubk().getEncoded()));
                    initializer.setInstancesForObject(SERVER, new Server(123, domain.getLifetimeInSec()));
                } else if (domain.getLwm2mChooseAlgorithm().equals(SecurityAlgorithm.X509.getCode())) {
                    initializer.setInstancesForObject(SECURITY,
                            x509(domain.getServerUrl(), 123, domain.getCcert().getEncoded(),
                                    domain.getCprik().getEncoded(), domain.getScert().getEncoded(),
                                    domain.getCertUsage().code));
                    initializer.setInstancesForObject(SERVER, new Server(123, domain.getLifetimeInSec()));
                } else {
                    initializer.setInstancesForObject(SECURITY, noSec(domain.getServerUrl(), 123));
                    initializer.setInstancesForObject(SERVER, new Server(123, domain.getLifetimeInSec()));
                }
            }
            initializer.setInstancesForObject(DEVICE, new MyDevice());
            initializer.setInstancesForObject(OBJECT_ID_TEMPERATURE_SENSOR, new RandomTemperatureSensor());
            initializer.setInstancesForObject(OBJECT_ID_LWM2M_TEST_OBJECT, new LwM2mTestObject());

            List<LwM2mObjectEnabler> enablers = initializer.createAll();

            // Configure Registration Engine
            DefaultRegistrationEngineFactory engineFactory = new DefaultRegistrationEngineFactory();
            if (domain.getComPeriodInSec() != null)
                engineFactory.setCommunicationPeriod(domain.getComPeriodInSec() * 1000);

            // Create Californium Endpoints Provider:
            // --------------------------------------
            // Define Custom CoAPS protocol provider
            CoapsClientProtocolProvider customCoapsProtocolProvider = new CoapsClientProtocolProvider() {
                @Override
                public CaliforniumClientEndpointFactory createDefaultEndpointFactory() {
                    return new CoapsClientEndpointFactory() {

                        @Override
                        protected DtlsConnectorConfig.Builder createRootDtlsConnectorConfigBuilder(
                                Configuration configuration) {
                            DtlsConnectorConfig.Builder builder = super.createRootDtlsConnectorConfigBuilder(configuration);

                            // Add DTLS Session lifecycle logger
                            builder.setSessionListener(new DtlsSessionLogger());
                            return builder;
                        };
                    };
                }
            };

            // Create client endpoints Provider
            CaliforniumClientEndpointsProvider.Builder endpointsBuilder = new CaliforniumClientEndpointsProvider.Builder(
                    new CoapOscoreProtocolProvider(), customCoapsProtocolProvider);

            // Create Californium Configuration
            Configuration clientCoapConfig = endpointsBuilder.createDefaultConfiguration();

            // Set some DTLS stuff
            // These configuration values are always overwritten by CLI therefore set them to transient.
            clientCoapConfig.setTransient(DtlsConfig.DTLS_RECOMMENDED_CIPHER_SUITES_ONLY);
            clientCoapConfig.setTransient(DtlsConfig.DTLS_CONNECTION_ID_LENGTH);

            // Persist configuration
            File configFile = new File(CF_CONFIGURATION_FILENAME);
            if (configFile.isFile()) {
                clientCoapConfig.load(configFile);
            } else {
                clientCoapConfig.store(configFile, CF_CONFIGURATION_HEADER);
            }

            // Set Californium Configuration
            endpointsBuilder.setConfiguration(clientCoapConfig);

            endpointsBuilder.setClientAddress(domain.getLocalAddress());

            // Create client
            LeshanClientBuilder builder = new LeshanClientBuilder(domain.getEndpoint());
            builder.setObjects(enablers);
            builder.setEndpointsProvider(endpointsBuilder.build());
            builder.setDataSenders(new ManualDataSender());
            if (domain.isDtls() && domain.getLwm2mChooseAlgorithm().equals(SecurityAlgorithm.X509.getCode()))
                builder.setTrustStore(domain.getTrustStore());
            builder.setRegistrationEngineFactory(engineFactory);
            final LeshanClient client = builder.build();

            client.getObjectTree().addListener(new ObjectsListenerAdapter() {

                @Override
                public void objectRemoved(LwM2mObjectEnabler object) {
                    System.out.printf("Object %s v%s disabled.%n", object.getId(), object.getObjectModel().version);
                }

                @Override
                public void objectAdded(LwM2mObjectEnabler object) {
                    System.out.printf("Object %s v%s enabled.%n", object.getId(), object.getObjectModel().version);
                }
            });

            return client;
        } catch (CertificateEncodingException e) {
            System.out.format(ColorUtils.redError("create client failed!"));
        }
        return null;
    }
}
