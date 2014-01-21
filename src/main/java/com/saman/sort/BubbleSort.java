package com.saman.sort;

/**
 * 原理是临近的数字两两进行比较,按照从小到大或者从大到小的顺序进行交换,
 * 
 * 这样一趟过去后,最大或最小的数字被交换到了最后一位,
 * 
 * 然后再从头开始进行两两比较交换,直到倒数第二位时结束,其余类似
 * 
 * 分析一下它的时间复杂度。当最好的情况，也就是要排序的表本身就是有序的，那么我们比较次数，根据最后改进的代码，可以推断出就是n‐1次的比较，没有数据交换，时间复杂度为O(n)。
 * 
 * 当最坏的情况，即待排序表是逆序的情况，此时需要比较n*(n-1)/2次，并作等数量级的记录移动。因此，总的时间复杂度为O(n*n)。
 * 
 * @author Administrator
 * 
 */
public class BubbleSort extends BasicUtil{

	public static void sort1(int[] array) {

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

	/*
	 * 进一步优化：增加是否交换标志，没有交换则排序已经完成
	 */
	public static void sort2(int[] array) {

		int temp = 0, l = array.length;
		boolean flag = true;

		while (flag) {
			flag = false;
			for (int j = 0; j < (l - 1); j++) {
				if (array[j] > array[j + 1]) {
					temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
					flag = true;
				}
			}
			display(array);
			l--;
		}

	}

	public static void main(String[] args) {
		int[] array1 = { 1, 3, 7, 2, 5 };
		sort1(array1);
		System.out.println();
		int[] array2 = { 1, 3, 7, 2, 5 };
		sort2(array2);
	}

	
}
