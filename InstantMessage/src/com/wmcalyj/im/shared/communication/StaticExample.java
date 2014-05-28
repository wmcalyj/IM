package com.wmcalyj.im.shared.communication;

public class StaticExample {

	private static StaticExample SINGLETON = null;

	public static final StaticExample getInstance() {
		return new StaticExample();
	}

	private static final synchronized void initSingleton() {
		if (null == SINGLETON) {
			SINGLETON = new StaticExample();
		}
	}

	private StaticExample() {
		// empty
	}
}
