package com.luckysun.paycode;

import java.text.DecimalFormat;

public class PayCodeUtils {

	/**
	 * 生成付款码
	 */
	public static String generate(String id) {
		DecimalFormat df = new DecimalFormat("00000");
		int key = (int) (Math.random() * 9000 + 1000);
		long time = Long.valueOf(String.valueOf(System.currentTimeMillis()).substring(0, 10));
		long uid = 0;
		if (key % 2 == 0) {
			time = time - key;
			uid = Long.valueOf(id) + key;
		} else {
			uid = Long.valueOf(id) - key;
			time = time + key;
		}
		String idStr;
		if (uid < 0) {
			idStr = "1" + df.format(Math.abs(uid));
		} else {
			idStr = "2" + df.format(uid);
		}
		StringBuilder builder = new StringBuilder(idStr).append(time);
		return builder.toString();
	}
}
