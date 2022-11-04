package iot.technology.client.toolkit.common.utils;

import iot.technology.client.toolkit.common.constants.TelecomSettings;
import iot.technology.client.toolkit.common.http.HttpGetResponseEntity;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
     * @throws Exception
     */
    public static long getTelecomRequestTimeOffset() throws Exception {
        long offset = 0;
        HttpRequestEntity request = new HttpRequestEntity();
        request.setUrl(TelecomSettings.ECHO_URL);
        request.setType("telecom");
        long start = System.currentTimeMillis();
        HttpGetResponseEntity response = HttpRequestExecutor.executeGet(request);
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
     * @param timestamp unix timestamp
     * @return data string
     */
    public static String getTelecomDataString (long timestamp) {
        Date date = new Date(timestamp);
        DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        ZoneId timeZone = TimeZone.getTimeZone("GMT").toZoneId();
        return dateformat.format(Instant.ofEpochMilli(timestamp).atZone(timeZone));
    }
}
