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
package iot.technology.client.toolkit.common.utils.security;

import java.io.*;
import java.security.GeneralSecurityException;

/**
 * An helper class to read credentials from various input. <br>
 * To be used you MUST implement at least one method between decode(byte[]) and decode(InputStream).
 * @author mushuwei
 */
public abstract class CredentialsReader<T> {

    /**
     * Read credential from file
     */
    public T readFromFile(String fileName) throws IOException, GeneralSecurityException {
        try (RandomAccessFile f = new RandomAccessFile(fileName, "r")) {
            byte[] bytes = new byte[(int) f.length()];
            f.readFully(bytes);
            return decode(bytes);
        }
    }

    /**
     * Read credential from resource (in a jar, war, ...)
     *
     * @see java.lang.ClassLoader#getResourceAsStream(String)
     */
    public T readFromResource(String resourcePath) throws IOException, GeneralSecurityException {
        try (InputStream in = ClassLoader.getSystemResourceAsStream(resourcePath)) {
            return decode(in);
        }
    }

    /**
     * Decode credential from byte array.
     */
    public T decode(byte[] bytes) throws IOException, GeneralSecurityException {
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes)) {
            return decode(in);
        }
    }

    /**
     * Decode credential from an InputStream.
     */
    public T decode(InputStream in) throws IOException, GeneralSecurityException {
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = in.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();

            return decode(buffer.toByteArray());
        }
    }
}
