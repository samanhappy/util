package com.saman.util.multi.thread;

import java.io.File;

/**
 * 静态非同步回调
 * 
 * @author leizhimin 2008-9-11 23:00:12
 */
public class StaticCallbackDigestUserInterface {
	/**
	 * 接收摘要码，输出到控制台
	 * 
	 * @param digest
	 *            摘要码
	 * @param inputFileName
	 *            输入的文件名
	 */
	public synchronized static void receiveDigest(byte[] digest, String inputFileName) {
		StringBuffer result = new StringBuffer(inputFileName);
		result.append(": ");
		for (int j = 0; j < digest.length; j++) {
			result.append(digest[j] + " ");
		}
		System.out.println(result);
	}

	public static void main(String[] args) {
		String arr[] = { "src/main/resources/com/saman/util/thread/1.txt",
				"src/main/resources/com/saman/util/thread/2.txt",
				"src/main/resources/com/saman/util/thread/3.txt",
				"src/main/resources/com/saman/util/thread/4.txt" };
		args = arr;
		for (int i = 0; i < args.length; i++) {
			File f = new File(args[i]);
			StaticCallbackDigest cb = new StaticCallbackDigest(f);
			Thread t = new Thread(cb);
			t.start();
		}

	}
}
