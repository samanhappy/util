package com.saman.sort;

/**
 * 原理是临近的数字两两进行比较,按照从小到大或者从大到小的顺序进行交换,
 * 
 * 这样一趟过去后,最大或最小的数字被交换到了最后一位,
 * 
 * 然后再从头开始进行两两比较交换,直到倒数第二位时结束,其余类似
 * 
 * @author Administrator
 * 
 */
public class BubbleSort {

	public static void sort(int[] array) {

		int temp = 0;
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < (array.length - i - 1); j++) {
				if (array[j] > array[j + 1]) {
					temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
			display(array);
		}
	}

	public static void main(String[] args) {
		int[] array = { 1, 3, 7, 2, 5 };
		sort(array);
	}

	public static void display(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + ",");
		}
		System.out.println();
	}
}
