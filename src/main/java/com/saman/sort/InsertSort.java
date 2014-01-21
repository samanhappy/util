package com.saman.sort;

/**
 * 插入排序的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
 * 
 * 插入排序在实现上，通常采用in-place排序（即只需用到O(1)的额外空间的排序），因而在从后向前扫描过程中，需要反复把已排序元素逐步向后挪位，
 * 为最新元素提供插入空间
 * 
 * 同样的O(n^2)时间复杂度，直接插入排序法比冒泡和简单选择排序的性能要好一些
 * 
 * @author Administrator
 * 
 */
public class InsertSort extends BasicUtil {

	public static void sort(int[] array) {
		int temp = 0, j = 0;
		for (int i = 1; i < array.length; i++) {
			temp = array[i];
			j = i;
			while (j > 0 && array[j - 1] > temp) {
				array[j] = array[j - 1];
				j--;
			}
			array[j] = temp;
			display(array);
		}
	}

	public static void main(String[] args) {
		int[] array = { 4, 1, 6, 3, 2, 7, 5 };
		display(array);
		sort(array);
	}

}
