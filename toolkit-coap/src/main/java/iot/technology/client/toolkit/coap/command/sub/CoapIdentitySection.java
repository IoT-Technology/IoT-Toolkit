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
