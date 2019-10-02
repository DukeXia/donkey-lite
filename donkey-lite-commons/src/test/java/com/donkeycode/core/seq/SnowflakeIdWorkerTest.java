package com.donkeycode.core.seq;

import org.junit.Test;

import com.donkeycode.core.seqno.SnowflakeIdWorker;

public class SnowflakeIdWorkerTest {

	@Test
	public void test() {

		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
		for (int i = 0; i < 1000000; i++) {
			long id = idWorker.nextId();
			System.out.println(Long.toBinaryString(id));
			System.out.println(id);
		}
	}
}
