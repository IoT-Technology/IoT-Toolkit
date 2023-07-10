package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum SecurityAlgorithm {

    PSK("psk", "Pre-Shared Key"),

    RPK("rpk", "Raw Public Key"),

    X509("x509", "X.509 Certificate");

    private String code;

    private String value;

    SecurityAlgorithm(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
