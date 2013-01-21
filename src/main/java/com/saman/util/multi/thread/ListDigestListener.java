package com.saman.util.multi.thread;

/**
 * 回调接口
 * 
 * @author leizhimin 2008-9-13 17:20:11
 */
public interface ListDigestListener {
	public void digestCalculated(byte digest[], int sort);
}