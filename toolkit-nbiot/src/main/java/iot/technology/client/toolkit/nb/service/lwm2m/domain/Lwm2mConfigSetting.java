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
package iot.technology.client.toolkit.nb.service.lwm2m.domain;

import iot.technology.client.toolkit.common.constants.ConfirmCodeEnum;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.node.LwM2MPortNode;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class Lwm2mConfigSetting implements Serializable {

    private String lwm2mServer;

    private String lwm2mPort;

    private String lwm2mBootstrapServer;

    private String lwm2mEndpoint;

    private String lwm2mLocalAddress;

    private String lwm2mModelsFolder;

    private String lwm2mLifeTime;

    private String lwm2mCommunicationPeriod;

    private String lwm2mDtls;

    private String lwm2mChooseAlgorithm;

    private String lwm2mPskIdentity;

    private String lwm2mPskShareKey;

    private String lwm2mClientPrivateKey;

    private String lwm2mClientPublicKey;

    private String lwm2mServerPublicKey;

    private String lwm2mClientCert;

    private String lwm2mServerCert;

    private String lwm2mCertUsage;


    public String getLwm2mServer() {
        return lwm2mServer;
    }

    public void setLwm2mServer(String lwm2mServer) {
        this.lwm2mServer = lwm2mServer;
    }

    public String getLwm2mPort() {
        return lwm2mPort;
    }

    public void setLwm2mPort(String lwm2mPort) {
        this.lwm2mPort = lwm2mPort;
    }

    public String getServerAndPort() {
        StringBuilder sb = new StringBuilder();
        if (lwm2mDtls.equals(ConfirmCodeEnum.YES.getValue())) {
            sb.append("coaps://");
            sb.append(lwm2mServer);
            sb.append(":");
            sb.append(StringUtils.isBlank(lwm2mPort) ? LwM2MPortNode.DEFAULT_COAPS_PORT : lwm2mPort);
        } else {
            sb.append("coap://");
            sb.append(lwm2mServer);
            sb.append(":");
            sb.append(StringUtils.isBlank(lwm2mPort) ? LwM2MPortNode.DEFAULT_COAP_PORT : lwm2mPort);
        }
        return sb.toString();
    }

    public String getLwm2mBootstrapServer() {
        return lwm2mBootstrapServer;
    }

    public void setLwm2mBootstrapServer(String lwm2mBootstrapServer) {
        this.lwm2mBootstrapServer = lwm2mBootstrapServer;
    }

    public String getLwm2mEndpoint() {
        return lwm2mEndpoint;
    }

    public void setLwm2mEndpoint(String lwm2mEndpoint) {
        this.lwm2mEndpoint = lwm2mEndpoint;
    }

    public String getLwm2mLocalAddress() {
        return lwm2mLocalAddress;
    }

    public void setLwm2mLocalAddress(String lwm2mLocalAddress) {
        this.lwm2mLocalAddress = lwm2mLocalAddress;
    }

    public String getLwm2mModelsFolder() {
        return lwm2mModelsFolder;
    }

    public void setLwm2mModelsFolder(String lwm2mModelsFolder) {
        this.lwm2mModelsFolder = lwm2mModelsFolder;
    }

    public String getLwm2mLifeTime() {
        return lwm2mLifeTime;
    }

    public void setLwm2mLifeTime(String lwm2mLifeTime) {
        this.lwm2mLifeTime = lwm2mLifeTime;
    }

    public String getLwm2mCommunicationPeriod() {
        return lwm2mCommunicationPeriod;
    }

    public void setLwm2mCommunicationPeriod(String lwm2mCommunicationPeriod) {
        this.lwm2mCommunicationPeriod = lwm2mCommunicationPeriod;
    }

    public String getLwm2mDtls() {
        return lwm2mDtls;
    }

    public void setLwm2mDtls(String lwm2mDtls) {
        this.lwm2mDtls = lwm2mDtls;
    }

    public String getLwm2mChooseAlgorithm() {
        return lwm2mChooseAlgorithm;
    }

    public void setLwm2mChooseAlgorithm(String lwm2mChooseAlgorithm) {
        this.lwm2mChooseAlgorithm = lwm2mChooseAlgorithm;
    }

    public String getLwm2mPskIdentity() {
        return lwm2mPskIdentity;
    }

    public void setLwm2mPskIdentity(String lwm2mPskIdentity) {
        this.lwm2mPskIdentity = lwm2mPskIdentity;
    }

    public String getLwm2mPskShareKey() {
        return lwm2mPskShareKey;
    }

    public void setLwm2mPskShareKey(String lwm2mPskShareKey) {
        this.lwm2mPskShareKey = lwm2mPskShareKey;
    }

    public String getLwm2mClientPrivateKey() {
        return lwm2mClientPrivateKey;
    }

    public void setLwm2mClientPrivateKey(String lwm2mClientPrivateKey) {
        this.lwm2mClientPrivateKey = lwm2mClientPrivateKey;
    }

    public String getLwm2mClientPublicKey() {
        return lwm2mClientPublicKey;
    }

    public void setLwm2mClientPublicKey(String lwm2mClientPublicKey) {
        this.lwm2mClientPublicKey = lwm2mClientPublicKey;
    }

    public String getLwm2mServerPublicKey() {
        return lwm2mServerPublicKey;
    }

    public void setLwm2mServerPublicKey(String lwm2mServerPublicKey) {
        this.lwm2mServerPublicKey = lwm2mServerPublicKey;
    }

    public String getLwm2mClientCert() {
        return lwm2mClientCert;
    }

    public void setLwm2mClientCert(String lwm2mClientCert) {
        this.lwm2mClientCert = lwm2mClientCert;
    }

    public String getLwm2mServerCert() {
        return lwm2mServerCert;
    }

    public void setLwm2mServerCert(String lwm2mServerCert) {
        this.lwm2mServerCert = lwm2mServerCert;
    }

    public String getLwm2mCertUsage() {
        return lwm2mCertUsage;
    }

    public void setLwm2mCertUsage(String lwm2mCertUsage) {
        this.lwm2mCertUsage = lwm2mCertUsage;
    }
}
