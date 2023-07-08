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
