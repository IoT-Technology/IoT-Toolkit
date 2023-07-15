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
package iot.technology.client.toolkit.nb.service.lwm2m;

import iot.technology.client.toolkit.common.constants.SecurityAlgorithm;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.lwm2m.domain.*;
import org.eclipse.californium.elements.config.Configuration;
import org.eclipse.californium.scandium.config.DtlsConnectorConfig;
import org.eclipse.leshan.client.LeshanClient;
import org.eclipse.leshan.client.LeshanClientBuilder;
import org.eclipse.leshan.client.californium.endpoint.CaliforniumClientEndpointFactory;
import org.eclipse.leshan.client.californium.endpoint.CaliforniumClientEndpointsProvider;
import org.eclipse.leshan.client.californium.endpoint.coap.CoapOscoreProtocolProvider;
import org.eclipse.leshan.client.californium.endpoint.coaps.CoapsClientEndpointFactory;
import org.eclipse.leshan.client.californium.endpoint.coaps.CoapsClientProtocolProvider;
import org.eclipse.leshan.client.engine.DefaultRegistrationEngineFactory;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.*;
import org.eclipse.leshan.client.resource.listener.ObjectsListenerAdapter;
import org.eclipse.leshan.client.send.ManualDataSender;
import org.eclipse.leshan.core.model.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.cert.CertificateEncodingException;
import java.util.*;

import static org.eclipse.leshan.client.object.Security.*;
import static org.eclipse.leshan.core.LwM2mId.*;


/**
 * @author mushuwei
 */
public class Lwm2mDeviceService {

    public LwM2mModelRepository createModel(Lwm2mConfigSettingsDomain domain) {
        List<ObjectModel> models = ObjectLoader.loadAllDefault();
        if (domain.modelsFolder != null) {
            models.addAll(ObjectLoader.loadObjectsFromDir(domain.getModelsFolder(), true));
        }
        return new LwM2mModelRepository(models);
    }

    public LeshanClient createClient(Lwm2mConfigSettingsDomain domain, LwM2mModelRepository repository) {
        final LeshanClient client;

        try {
            // Initialize object list
            final ObjectsInitializer initializer = new ObjectsInitializer(repository.getLwM2mModel());
            if (domain.isBootstrap()) {
                if (domain.isDtls()) {
                    if (SecurityAlgorithm.RPK.getCode().equals(domain.getLwm2mChooseAlgorithm())) {
                        initializer.setInstancesForObject(SECURITY, pskBootstrap(domain.getServerUrl(),
                                domain.getIdentity().getBytes(), domain.getSharekey().getBytes()));
                        initializer.setClassForObject(SERVER, Server.class);
                    }
                    if (SecurityAlgorithm.RPK.getCode().equals(domain.getLwm2mChooseAlgorithm())) {
                        initializer.setInstancesForObject(SECURITY,
                                rpkBootstrap(domain.getServerUrl(), domain.getCpubk().getEncoded(),
                                        domain.getCprik().getEncoded(), domain.getSpubk().getEncoded()));
                        initializer.setClassForObject(SERVER, Server.class);
                    }
                    if (SecurityAlgorithm.X509.getCode().equals(domain.getLwm2mChooseAlgorithm())) {
                        initializer.setInstancesForObject(SECURITY,
                                x509Bootstrap(domain.getServerUrl(), domain.getCcert().getEncoded(),
                                        domain.getCprik().getEncoded(), domain.getScert().getEncoded(),
                                        domain.getCertUsage().code));
                        initializer.setClassForObject(SERVER, Server.class);
                    }
                }
                initializer.setInstancesForObject(SECURITY, noSecBootstrap(domain.getServerUrl()));
                initializer.setClassForObject(SERVER, Server.class);
            } else {
                if (SecurityAlgorithm.PSK.getCode().equals(domain.getLwm2mChooseAlgorithm())) {
                    initializer.setInstancesForObject(SECURITY, psk(domain.getServerUrl(), 123,
                            domain.getIdentity().getBytes(), domain.getSharekey().getBytes()));
                    initializer.setInstancesForObject(SERVER, new Server(123, domain.getLifetimeInSec()));
                } else if (SecurityAlgorithm.RPK.getCode().equals(domain.getLwm2mChooseAlgorithm())) {
                    initializer.setInstancesForObject(SECURITY,
                            rpk(domain.getServerUrl(), 123, domain.getCpubk().getEncoded(),
                                    domain.getCprik().getEncoded(), domain.getSpubk().getEncoded()));
                    initializer.setInstancesForObject(SERVER, new Server(123, domain.getLifetimeInSec()));
                } else if (SecurityAlgorithm.X509.getCode().equals(domain.getLwm2mChooseAlgorithm())) {
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

            List<LwM2mObjectEnabler> enablers = initializer.createAll();

            // Configure Registration Engine
            DefaultRegistrationEngineFactory engineFactory = new DefaultRegistrationEngineFactory();
            if (domain.getComPeriodInSec() != null) {
                engineFactory.setCommunicationPeriod(domain.getComPeriodInSec() * 1000);
            }

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

            CaliforniumClientEndpointsProvider.Builder endpointsBuilder = new CaliforniumClientEndpointsProvider.Builder(
                    new CoapOscoreProtocolProvider());

            // Create Californium Configuration
            Configuration clientCoapConfig = endpointsBuilder.createDefaultConfiguration();
            // Set Californium Configuration
            endpointsBuilder.setConfiguration(clientCoapConfig);
            endpointsBuilder.setClientAddress(convert(domain.getLocalAddress()));

            // Create client
            LeshanClientBuilder builder = new LeshanClientBuilder(domain.getEndpoint());
            builder.setObjects(enablers);
            builder.setEndpointsProvider(endpointsBuilder.build());
            builder.setDataSenders(new ManualDataSender());
            if (domain.isDtls() && domain.getLwm2mChooseAlgorithm().equals(SecurityAlgorithm.X509.getCode()))
                builder.setTrustStore(domain.getTrustStore());
            builder.setRegistrationEngineFactory(engineFactory);
            client = builder.build();

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

    public InetAddress convert(String value) {
        try {
            if (StringUtils.isBlank(value) || value.equals("*")) {
                // create a wildcard address meaning any local address.
                return new InetSocketAddress(0).getAddress();
            }
            return InetAddress.getByName(value);
        } catch (UnknownHostException ignored) {
        }
        return new InetSocketAddress(0).getAddress();
    }
}
