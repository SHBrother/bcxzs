package com.edingyc.bcxzs.Utils.randomId;

import org.springframework.stereotype.Component;

@Component
public class IDGenerator {
	private final static int MAX_SERIALNUMBER = 62 * 62 - 1;
	private AtomicHex62 number = new AtomicHex62(MAX_SERIALNUMBER);


	/**
	 * 生成ID<br>
	 * 62进制随机号码（1位）+62进制区域号（1位）+62进制节点号（2位）+从UTC时间1970年开始的毫秒数转换为62进制（10位）+62
	 * 进制自增序号（2位）
	 * 
	 * @return
	 */
	public String generateID() {
		String id = "";
		id += "0";
		// 从UTC时间1970年开始的毫秒数转换为62进制（10位）
		id += Hex62Util.longToHex62(System.currentTimeMillis(), 12, '0');
		// 62进制自增序号（2位）
		id += number.incrementAndGet();
		return id;
	}

	public String generateRamdomID() {
		String id = "";
		id += "1";
		id += Hex62Util.longToHex62(System.currentTimeMillis(), 12, '0');
		id += number.incrementAndGet();
		return id;
	}


	public static void main(String[] args) {
	}
}
