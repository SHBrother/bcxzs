package com.edingyc.bcxzs.Utils;

import com.edingyc.bcxzs.exception.ExceptionMsg;
import com.edingyc.bcxzs.exception.WrapException;

import java.security.MessageDigest;


public class MD5Util {

	public static String encrypt(String dataStr) throws WrapException{
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(dataStr.getBytes("UTF8"));
			byte s[] = m.digest();
			String result = "";
			for (int i = 0; i < s.length; i++) {
				result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00)
						.substring(6);
			}
			return result;
		} catch (Exception e) {
			throw new WrapException(ExceptionMsg.MD5ParseError.getCode(),ExceptionMsg.MD5ParseError.getMsg());
		}
	}

}
