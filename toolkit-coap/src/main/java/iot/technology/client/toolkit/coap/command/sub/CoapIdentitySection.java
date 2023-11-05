package iot.technology.client.toolkit.coap.command.sub;

import picocli.CommandLine;

/**
 * @author mushuwei
 */
public class CoapIdentitySection {

    @CommandLine.ArgGroup(exclusive = false,
            heading = "%n@|bold,underline PSK Options|@ %n%n"//
                    + "@|italic " //
                    + "By default use non secure connection.%n"//
                    + "To use CoAP over DTLS with Pre-Shared Key, -i and -p options should be used together." //
                    + "|@%n%n")
    private PskSection psk;


    public static class PskSection {
        @CommandLine.Option(required = true,
                names = { "-i", "--psk-identity" },
                description = { //
                        "Set the server PSK identity in ascii." })
        public String identity;

        @CommandLine.Option(required = true,
                names = { "-p", "--psk-key" },
                description = { //
                        "Set the server Pre-Shared-Key" })
        public String sharekey;
    }


    /* ***** Some convenient method to access to identity easily ****/
    public boolean isPSK() {
        return psk != null;
    }

    public PskSection getPsk() {
        return psk;
    }

    public boolean hasIdentity() {
        return isPSK();
    }
}
