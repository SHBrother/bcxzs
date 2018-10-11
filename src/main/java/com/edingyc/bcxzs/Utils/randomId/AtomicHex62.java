package com.edingyc.bcxzs.Utils.randomId;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicHex62 {

	private AtomicInteger number = new AtomicInteger();
	private ReentrantLock lock = new ReentrantLock();
	private int max = 0;
	private int maxLength = -1;

	public AtomicHex62() {
		this(Integer.MAX_VALUE);
	}

	public AtomicHex62(int max) {
		this.max = max;
		this.maxLength = Hex62Util.longToHex62(max).length();
	}
	
	public String incrementAndGet() {
		lock.lock();
		try {
			if (number.intValue() >= max) {
				number.set(0);
			}
			return Hex62Util.longToHex62(number.incrementAndGet(), maxLength,
					'0');

		} finally {
			lock.unlock();
		}
	}
}
