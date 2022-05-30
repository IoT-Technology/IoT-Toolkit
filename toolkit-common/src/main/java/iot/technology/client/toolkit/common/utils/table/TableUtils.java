/*$Id: $
 * author   date   comment
 * cwjcsu@gmail.com  2016年5月19日  Created
 */
package iot.technology.client.toolkit.common.utils.table;

public class TableUtils {
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");

	public static String repeat(char ch, int count) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < count; ++i) {
			buffer.append(ch);
		}
		return buffer.toString();
	}

	public static int sum(int[] d) {
		int sum = 0;
		for (int d0 : d) {
			sum += d0;
		}
		return sum;
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isChChar(char ch) {
		return ch > 255 || ch < 0;
	}

	/**
	 * Intercepts a substring (start to end, assuming 1 byte for an ASCII character and 2 bytes for a non-ASCII character)
	 * with the length of the substring being end-start
	 *
	 * @param string
	 * @param start
	 * @param end
	 * @return
	 */
	public static String subCHString(String string, int start, int end) {
		if (string != null) {
			int len = 0;
			StringBuilder strs = new StringBuilder();
			int D = 0;
			for (int i = 0; i < string.length(); i++) {
				int d = isChChar(string.charAt(i)) ? 2 : 1;
				len += d;
				if (end < len || D >= (end - start)) {
					return strs.toString();
				} else if (start < len) {
					strs.append(string.charAt(i));
					D += d;
				}
			}
			return new String(strs);
		}
		return string != null ? string.substring(start, end) : null;
	}

	public static String subCHString(String string, int start) {
		return subCHString(string, start, Integer.MAX_VALUE);
	}

	/**
	 * @param string
	 * @param start
	 * @param length
	 * @return
	 */
	public static String subCHStringByLenth(String string, int start, int length) {
		return subCHString(string, start, start + length);
	}

	public static int lengthCH(String string) {
		if (string != null) {
			int len = 0;
			for (int i = 0; i < string.length(); i++) {
				if (isChChar(string.charAt(i))) {
					len += 2;
				} else {
					len++;
				}
			}
			return len;
		}
		return 0;
	}
}
