package com.saman.sort;

/**
 * 选择排序的工作原理如下。首先在未排序序列中找到最小元素，存放到排序序列的起始位置，
 * 
 * 然后，再从剩余未排序元素中继续寻找最小元素，然后放到排序序列末尾。以此类推，直到所有元素均排序完毕。
 * 
 * 尽管与冒泡排序同为O(n^2)，但简单选择排序的性能上还是要略优于冒泡排序
 * 
 * @author Administrator
 * 
 */
public class SelectSort extends BasicUtil {

	public static void sort(int[] array) {

		int temp = 0, loc = 0;
		for (int i = 0; i < array.length; i++) {

			// 使用temp存储最小数
			temp = array[i];
			for (int j = i + 1; j < array.length; j++) {
				if (array[j] < temp) {
					temp = array[j];
					loc = j;
				}
			}

			// 相等时不做元素交换
			if (loc != i) {
				// 使用temp作为临时变量
				temp = array[i];
				array[i] = array[loc];
				array[loc] = temp;
			}

			display(array);
		}
	}

	public static void main(String[] args) {
		int array[] = { 4, 2, 6, 3, 7, 5, 8 };
		display(array);
		sort(array);
	}
}
