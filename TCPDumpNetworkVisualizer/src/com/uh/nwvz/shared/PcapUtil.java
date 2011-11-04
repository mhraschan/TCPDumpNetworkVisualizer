package com.uh.nwvz.shared;

public class PcapUtil {

	public static String ip(byte[] address) {
		if (address.length == 4) {
			return asString(address, '.', 10);
		} else {
			return asStringIp6(address, true);
		}
	}

	public static String asString(byte[] array, char separator, int radix) {
		return asString(array, separator, radix, 0, array.length);
	}

	public static String asString(byte[] array, char separator) {
		return asString(array, separator, 16); // Default HEX
	}

	public static String asString(byte[] array) {
		return asString(array, ':');
	}

	public static String asString(byte[] array, char separator, int radix,
			int start, int len) {

		final StringBuilder buf = new StringBuilder();

		for (int i = start; i < (start + len); i++) {
			byte b = array[i];
			if (buf.length() != 0) {
				buf.append(separator);
			}

			buf.append(Integer.toString((b < 0) ? b + 256 : b, radix)
					.toUpperCase());
		}

		return buf.toString();
	}

	public static String asStringIp6(byte[] array, boolean holes) {
		StringBuilder buf = new StringBuilder();

		int len = 0;
		int start = -1;
		/*
		 * Check for byte compression where sequential zeros are replaced with
		 * ::
		 */
		for (int i = 0; i < array.length && holes; i++) {
			if (array[i] == 0) {
				if (len == 0) {
					start = i;
				}

				len++;
			}

			/*
			 * Only the first sequence of 0s is compressed, so break out
			 */
			if (array[i] != 0 && len != 0) {
				break;
			}
		}

		/*
		 * Now round off to even length so that only pairs are compressed
		 */
		if (start != -1 && (start % 2) == 1) {
			start++;
			len--;
		}

		if (start != -1 && (len % 2) == 1) {
			len--;
		}

		for (int i = 0; i < array.length; i++) {
			if (i == start) {
				buf.append(':');
				i += len - 1;

				if (i == array.length - 1) {
					buf.append(':');
				}
				continue;
			}

			byte b = array[i];

			if (buf.length() != 0 && (i % 2) == 0) {
				buf.append(':');
			}
			if (b < 16) {
				buf.append('0');
			}
			buf.append(Integer.toHexString((b < 0) ? b + 256 : b).toUpperCase());
		}

		return buf.toString();
	}

}
