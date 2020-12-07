package com.dercg.netty.transport.util;

public class SystemTimeUtil {

	public static int getTimestamp() {
		
		return (int)System.currentTimeMillis()/1000;
	}
}
