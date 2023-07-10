package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum CertUsageEnum {

    CA("0", "CA constraint"),

    SERVICE_CERT("1", "service certificate constraint"),

    TRUST_ANCHOR_ASSERTION("2", "trust anchor assertion"),

    DOMAIN_ISSUED_CERT("3", "domain issued certificate (Default value)");


    private String code;

    private String value;

    CertUsageEnum(String code, String value) {
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
