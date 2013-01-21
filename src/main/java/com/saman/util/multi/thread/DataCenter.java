package com.saman.util.multi.thread;

public class DataCenter {

	public static int i;

	public static void add() {
		i++;
	}

	public static void minus() {
		i--;
	}

	public synchronized static void synAdd() {
		i++;
	}

	public synchronized static void synMinus() {
		i--;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			new MinusThread().start();
		}
		for (int i = 0; i < 10000; i++) {
			new AddThread().start();
		}
		for (int i = 0; i < 10000; i++) {
			new AddThread().start();
		}
		for (int i = 0; i < 10000; i++) {
			new MinusThread().start();
		}
	}

}
