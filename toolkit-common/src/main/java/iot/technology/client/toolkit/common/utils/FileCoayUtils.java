package iot.technology.client.toolkit.common.utils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author mushuwei
 */
public class FileCoayUtils {

	/**
	 * The default buffer size used why copying bytes.
	 */
	public static final int BUFFER_SIZE = 4096;

	/**
	 * Copy the contents of the given Reader into a String.
	 * Closes the reader when done.
	 *
	 * @param in the reader to copy from (may be {@code null} or empty)
	 * @return the String that has been copied to (possibly empty)
	 * @throws IOException in case of I/O errors
	 */
	public static String copyToString(Reader in) throws IOException {
		if (in == null) {
			return "";
		}

		StringWriter out = new StringWriter();
		copy(in, out);
		return out.toString();
	}

	/**
	 * Copy the contents of the given Reader to the given Writer.
	 * Closes both when done.
	 *
	 * @param in  the Reader to copy from
	 * @param out the Writer to copy to
	 * @return the number of characters copied
	 * @throws IOException in case of I/O errors
	 */
	public static int copy(Reader in, Writer out) throws IOException {
		Assert.notNull(in, "No Reader specified");
		Assert.notNull(out, "No Writer specified");

		try {
			int byteCount = 0;
			char[] buffer = new char[BUFFER_SIZE];
			int bytesRead = -1;
			while ((bytesRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
				byteCount += bytesRead;
			}
			out.flush();
			return byteCount;
		} finally {
			try {
				in.close();
			} catch (IOException ex) {
			}
			try {
				out.close();
			} catch (IOException ex) {
			}
		}
	}
}
