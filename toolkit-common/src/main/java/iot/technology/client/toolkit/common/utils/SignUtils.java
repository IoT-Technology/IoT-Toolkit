package iot.technology.client.toolkit.common.utils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.TreeSet;

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
}
