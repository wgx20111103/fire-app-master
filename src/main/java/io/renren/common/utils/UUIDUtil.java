package io.renren.common.utils;

import java.util.UUID;

public class UUIDUtil {
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static void main(String[] args) {
		System.out.println(uuid());
	}

}
