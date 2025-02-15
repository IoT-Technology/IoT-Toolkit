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
package iot.technology.client.toolkit.nb.service.lwm2m.domain;

import org.eclipse.californium.elements.util.Bytes;
import org.eclipse.leshan.client.LeshanClient;
import org.eclipse.leshan.core.CertificateUsage;
import org.eclipse.leshan.core.model.LwM2mModelRepository;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author mushuwei
 */
public class Lwm2mConfigSettingsDomain {

    private String serverUrl;

    private boolean bootstrap;

    private String endpoint;

    public File modelsFolder;

    private Integer lifetimeInSec;

    private Integer comPeriodInSec;

    private String localAddress;

    private boolean dtls;

    private String lwm2mChooseAlgorithm;

    private String identity;

    private Bytes sharekey;

    private PrivateKey cprik;

    private PublicKey cpubk;

    private PublicKey spubk;

    private X509Certificate ccert;

    private X509Certificate scert;

    private CertificateUsage certUsage;

    private List<Certificate> trustStore;

    private LeshanClient leshanClient;

    private LwM2mModelRepository repository;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public boolean isBootstrap() {
        return bootstrap;
    }

    public File getModelsFolder() {
        return modelsFolder;
    }

    public void setModelsFolder(File modelsFolder) {
        this.modelsFolder = modelsFolder;
    }

    public void setBootstrap(boolean bootstrap) {
        this.bootstrap = bootstrap;
    }

    public boolean isDtls() {
        return dtls;
    }

    public void setDtls(boolean dtls) {
        this.dtls = dtls;
    }

    public String getLwm2mChooseAlgorithm() {
        return lwm2mChooseAlgorithm;
    }

    public void setLwm2mChooseAlgorithm(String lwm2mChooseAlgorithm) {
        this.lwm2mChooseAlgorithm = lwm2mChooseAlgorithm;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public Bytes getSharekey() {
        return sharekey;
    }

    public void setSharekey(Bytes sharekey) {
        this.sharekey = sharekey;
    }

    public PrivateKey getCprik() {
        return cprik;
    }

    public void setCprik(PrivateKey cprik) {
        this.cprik = cprik;
    }

    public PublicKey getCpubk() {
        return cpubk;
    }

    public void setCpubk(PublicKey cpubk) {
        this.cpubk = cpubk;
    }

    public PublicKey getSpubk() {
        return spubk;
    }

    public void setSpubk(PublicKey spubk) {
        this.spubk = spubk;
    }

    public X509Certificate getCcert() {
        return ccert;
    }

    public void setCcert(X509Certificate ccert) {
        this.ccert = ccert;
    }

    public X509Certificate getScert() {
        return scert;
    }

    public void setScert(X509Certificate scert) {
        this.scert = scert;
    }

    public CertificateUsage getCertUsage() {
        return certUsage;
    }

    public void setCertUsage(CertificateUsage certUsage) {
        this.certUsage = certUsage;
    }

    public LeshanClient getLeshanClient() {
        return leshanClient;
    }

    public void setLeshanClient(LeshanClient leshanClient) {
        this.leshanClient = leshanClient;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public Integer getComPeriodInSec() {
        return comPeriodInSec;
    }

    public void setComPeriodInSec(Integer comPeriodInSec) {
        this.comPeriodInSec = comPeriodInSec;
    }

    public List<Certificate> getTrustStore() {
        return trustStore;
    }

    public void setTrustStore(List<Certificate> trustStore) {
        this.trustStore = trustStore;
    }

    public Integer getLifetimeInSec() {
        return lifetimeInSec;
    }

    public void setLifetimeInSec(Integer lifetimeInSec) {
        this.lifetimeInSec = lifetimeInSec;
    }

    public LwM2mModelRepository getRepository() {
        return repository;
    }

    public void setRepository(LwM2mModelRepository repository) {
        this.repository = repository;
    }
}
