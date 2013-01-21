package com.saman.util.multi.thread;

public class MinusThread extends Thread {
	@Override
	public void run() {
		DataCenter.synMinus();
		System.out.println(DataCenter.i);
	}
}
