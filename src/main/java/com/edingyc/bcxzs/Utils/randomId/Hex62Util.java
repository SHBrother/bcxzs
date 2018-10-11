package com.edingyc.bcxzs.Utils.randomId;

import java.util.Random;

public final class Hex62Util {
	public final static int DIVISOR = 62;
	public final static char[] CHAR62 = new char[] { '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z' };

	private final static Random random = new Random();

	/**
	 * 返回62进制字符串
	 * 
	 * @param source
	 * @return
	 */
	public static String longToHex62(long source) {
		return Hex62.longToN62(source);
	}

	/**
	 * 返回指定长度的62进制字符串，不足则前面补c，超出则报错
	 * 
	 * @param source
	 * @param len
	 *            指定长度
	 * @param c
	 *            用于补位的字符
	 * @return
	 */
	public static String longToHex62(long source, int len, char c) {

		String result = longToHex62(source);

		int diff = len - result.length();
		if (diff > 0) {
			while (diff > 0) {
				result = c + result;
				diff--;
			}
			return result;
		} else if (diff == 0) {
			return result;
		} else {
			throw new IllegalArgumentException("Source (" + source
					+ ") is too long");
		}
	}

	/**
	 * 将单个62进制字符转换为10进制
	 * 
	 * @param c
	 * @return
	 */
	public static int hex62CharToInt(char c) {
		if (c >= 48 && c <= 57) // ASCLL码0-9之间
		{
			return c - 48;
		} else if (c >= 65 && c <= 90)// ASCLL码A-Z之间
		{
			return c - 55;
		} else if (c >= 97 && c <= 122)// ASCLL码a-z之间
		{
			return c - 61;
		} else {
			throw new IllegalArgumentException("Char (" + c + ")  is invalid");
		}
	}

	/**
	 * 62进制转换为10进制
	 * 
	 * @param hex62
	 * @return
	 */
	public static long hex62ToLong(String hex62) {
		return Hex62.n62ToLong(hex62);
	}

	public static String getRandomHex62(int len) {
		String result = "";
		for (int i = 0; i < len; i++) {
			result += Hex62Util.CHAR62[random.nextInt(62)];
		}
		return result;
	}

	public static void main(String[] args) {
		// System.out.println(long2Hex62(1L));
		// System.out.println(long2Hex62(10L));
		// System.out.println(long2Hex62(61L));
		// System.out.println(long2Hex62(62L));
		// System.out.println(long2Hex62(63L));
		Long l = 473796641564025320L;
		String s = longToHex62(l, 10, '0');
		System.out.println(String.valueOf(l));
		System.out.println(s);
		System.out.println(Hex62.longToN62(l));
		System.out.println(hex62ToLong(s));
		System.out.println(Hex62.n62ToLong(s));
	}
}
