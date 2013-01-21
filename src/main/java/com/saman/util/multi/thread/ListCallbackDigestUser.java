package com.saman.util.multi.thread;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ListCallbackDigestUser implements ListDigestListener {

	private Map<Integer, byte[]> digestmap = Collections
			.synchronizedMap(new HashMap<Integer, byte[]>());

	private static volatile int fileSize = 0;

	private synchronized void addFileSize() {
		fileSize++;
	}

	private synchronized void minusFileSize() {
		fileSize--;
	}

	/**
	 * 计算某个文件的消息摘要码
	 */
	public void calculateDigest(File inputFile, int sort) {
		ListCallbackDigest callback = new ListCallbackDigest(inputFile, sort);
		callback.addDigestListener(this);
		addFileSize();
		Thread t = new Thread(callback);
		t.start();
	}

	public synchronized void digestCalculated(byte digest[], int sort) {
		System.out.println("receive one result by sort:" + sort);
		minusFileSize();
		this.digestmap.put(sort, digest);
		if (fileSize == 0) {
			StringBuffer result = new StringBuffer();
			for (int i = 0; i < 4; i++)
			{
				addDigest(result, digestmap.get(i));
			}
			System.out.println(result);
		}
	}

	private void addDigest(StringBuffer result, byte[] digByte) {
		for (int j = 0; j < digByte.length; j++) {
			result.append(digByte[j] + " ");
		}
	}

	public static void main(String[] args) {
		String arr[] = { "src/main/resources/com/saman/util/thread/1.txt",
				"src/main/resources/com/saman/util/thread/2.txt",
				"src/main/resources/com/saman/util/thread/3.txt",
				"src/main/resources/com/saman/util/thread/4.txt" };
		args = arr;
		ListCallbackDigestUser cb = new ListCallbackDigestUser();
		for (int i = 0; i < args.length; i++) {
			cb.calculateDigest(new File(args[i]), i);
		}
	}
}
