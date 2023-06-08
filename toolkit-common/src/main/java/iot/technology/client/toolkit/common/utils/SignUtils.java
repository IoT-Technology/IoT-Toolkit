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
package iot.technology.client.toolkit.common.utils;

import iot.technology.client.toolkit.common.constants.TelecomSettings;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;
import iot.technology.client.toolkit.common.http.HttpResponseEntity;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SignUtils {

    /**
     *
     * @param param config param
     * @param timestamp unix timestamp
     * @param application appKey
     * @param secret appSecret
     * @param body data of request body, if it is `GET` request, then is null
     * @return signed character string
     */
    public static String aepSignAlgorithm(Map<String, String> param, long timestamp, String application, String secret, byte[] body) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("application").append(":").append(application).append("\n");
        sb.append("timestamp").append(":").append(timestamp).append("\n");
        if (param != null) {
            TreeSet<String> keys = new TreeSet<>(param.keySet());
            for (String s : keys) {
                String val = param.get(s);
                sb.append(s).append(":").append(val == null ? "" : val).append("\n");
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        if (body != null && body.length > 0) {
            baos.write(body);
            baos.write("\n".getBytes(StandardCharsets.UTF_8));
        }
        String string = baos.toString(StandardCharsets.UTF_8);
        byte[] bytes;
        SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSha1");
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        bytes = mac.doFinal(string.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     *
     * @return offset of time
     */
    public static long getTelecomRequestTimeOffset() throws Exception {
        long offset = 0;
        HttpRequestEntity request = new HttpRequestEntity();
        request.setUrl(TelecomSettings.TEL_ECHO_URL);
        request.setType("telecom");
        long start = System.currentTimeMillis();
        HttpResponseEntity response = HttpRequestExecutor.executeGet(request);
        long end = System.currentTimeMillis();

        Map<String, List<String>> multiMap = response.getMultiMap();
        List<String> headerTime = multiMap.get(TelecomSettings.timestampHeader);
        if (headerTime.size() > 0) {
            long serviceTime = Long.parseLong(headerTime.get(0));
            offset = serviceTime - (start + end) / 2L;
        }
        return offset;
    }

    /**
     * get data string
     *
     * @param timestamp unix timestamp
     * @return data string
     */
    public static String getTelecomDataString(long timestamp) {
        DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        ZoneId timeZone = TimeZone.getTimeZone("GMT").toZoneId();
        return dateformat.format(Instant.ofEpochMilli(timestamp).atZone(timeZone));
    }

    /**
     * get mobile api token
     *
     * @param version         version
     * @param resourceName    resource
     * @param expirationTime  expiration time of api
     * @param signatureMethod signature method
     * @param accessKey       accessKey
     * @return api token of mobile
     */
    public static String getMobileAssembleToken(String version, String resourceName, String expirationTime, String signatureMethod,
                                                String accessKey) {
        StringBuilder sb = new StringBuilder();
        try {
            String res = URLEncoder.encode(resourceName, UTF_8);
            String sig = URLEncoder.encode(generatorSignature(version, resourceName, expirationTime, accessKey, signatureMethod), UTF_8);
            sb.append("version=")
                    .append(version)
                    .append("&res=")
                    .append(res)
                    .append("&et=")
                    .append(expirationTime)
                    .append("&method=")
                    .append(signatureMethod)
                    .append("&sign=")
                    .append(sig);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            System.out.format(ColorUtils.redError("getMobileAssembleToken failed!"));
        }
        return sb.toString();
    }

    public static String generatorSignature(String version, String resourceName, String expirationTime, String accessKey,
                                            String signatureMethod) throws
            NoSuchAlgorithmException, InvalidKeyException {
        String encryptText = expirationTime + "\n" + signatureMethod + "\n" + resourceName + "\n" + version;
        String signature;
        byte[] bytes = hmacEncrypt(encryptText, accessKey, signatureMethod);
        signature = Base64.getEncoder().encodeToString(bytes);
        return signature;
    }

    public static byte[] hmacEncrypt(String data, String key, String signatureMethod) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec signinKey = new SecretKeySpec(Base64.getDecoder().decode(key),
                "Hmac" + signatureMethod.toUpperCase());

        Mac mac = Mac.getInstance("Hmac" + signatureMethod.toUpperCase());
        mac.init(signinKey);
        return mac.doFinal(data.getBytes(Charset.defaultCharset()));
    }
}
