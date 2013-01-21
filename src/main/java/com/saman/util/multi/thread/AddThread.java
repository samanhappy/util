package com.saman.util.multi.thread;

public class AddThread extends Thread{

	@Override
	public void run() {
		DataCenter.synAdd();
		System.out.println(DataCenter.i);
	}
}
