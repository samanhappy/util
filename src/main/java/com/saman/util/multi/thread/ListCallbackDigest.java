package com.saman.util.multi.thread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListCallbackDigest implements Runnable {
	private File inputFile;
	int sort;
	private List<ListDigestListener> listenerList = Collections
			.synchronizedList(new ArrayList<ListDigestListener>());

	public ListCallbackDigest(File inputFile, int sort) {
		this.inputFile = inputFile;
		this.sort = sort;
	}

	public synchronized void addDigestListener(ListDigestListener ler) {
		listenerList.add(ler);
	}

	public synchronized void removeDigestListener(ListDigestListener ler) {
		listenerList.remove(ler);
	}

	private synchronized void sendDigest(byte digest[]) {
		for (ListDigestListener ler : listenerList) {
			ler.digestCalculated(digest, sort);
		}
	}

	public void run() {
		try {
			FileInputStream in = new FileInputStream(inputFile);
			MessageDigest sha = MessageDigest.getInstance("MD5");
			DigestInputStream din = new DigestInputStream(in, sha);
			int b;
			while ((b = din.read()) != -1)
				;
			din.close();
			byte[] digest = sha.digest(); // 摘要码
			long sleepTime = (long)(30000 * Math.random());
			System.out.println("sleep " + sleepTime + " fro sort " + sort);
			Thread.sleep(sleepTime);
			// 完成后，回调主线程静态方法，将文件名-摘要码结果传递给住线程
			this.sendDigest(digest);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}