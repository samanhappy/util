package com.saman.sort;

/**
 * (1)从数列中挑出一个元素，称为 “基准”（pivot），
 * (2)重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边
 * ）。在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作。
 * (3)递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序
 * 
 * 就空间复杂度来说，主要是递归造成的栈空间的使用，最好情况，递归树的深度为log2n，其空间复杂度也就为O(logn)，最坏情况，需要进行n‐1递归调用，
 * 其空间复杂度为O(n)，平均情况，空间复杂度也为O(logn)
 * 
 * 可惜的是，由于关键字的比较和交换是跳跃进行的，因此，快速排序是一种不稳定的排序方法
 * 
 * @author Administrator
 * 
 */
public class QuickSort extends BasicUtil {

	public static void sort(int[] array, int start, int end) {
		if (start < end) {
			int loc = partition(array, start, end);
			sort(array, start, loc - 1);
			sort(array, loc + 1, end);
		}
	}

	public static int partition(int[] unsorted, int low, int high) {
		int pivot = unsorted[low];
		while (low < high) {
			while (low < high && unsorted[high] > pivot)
				high--;
			unsorted[low] = unsorted[high];
			// display(unsorted);
			while (low < high && unsorted[low] <= pivot)
				low++;
			unsorted[high] = unsorted[low];
			// display(unsorted);
		}
		unsorted[low] = pivot;
		display(unsorted);
		return low;
	}

	public static void main(String[] args) {
		int[] array = { 5, 2, 4, 1, 6, 3, 7 };
		sort(array, 0, array.length - 1);
	}
}
