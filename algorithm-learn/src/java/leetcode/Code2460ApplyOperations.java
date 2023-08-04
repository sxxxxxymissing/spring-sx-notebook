package java.leetcode;

import java.util.Arrays;

/**
 * 2040 对数组执行操作 (简单)
 */
public class Code2460ApplyOperations {

    public int[] applyOperations(int[] nums) {
        int n = nums.length;
        for (int i = 0, j = 0; i < n; i++) {
            if (i + 1 < n && nums[i] == nums[i + 1]) {
                nums[i] *= 2;
                nums[i + 1] = 0;
            }
            /** 将坐标 i, j 的位置互换 */
            if (nums[i] != 0) {
                swap(nums, i, j);
                /** 下一个元素存放的位置, 如果前面都是非0,那么 i = j ,如果前面有0, 那么 j = 0 的坐标与下一个非0坐标互换 */
                j++;
            }
        }
        return nums;
    }

    //替换位置
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        Code2460ApplyOperations code2460ApplyOperations = new Code2460ApplyOperations();
        int[] ints = {
            1, 2, 2, 1, 1, 0
        };
        System.out.println(Arrays.toString(code2460ApplyOperations.applyOperations(ints)));
    }

}
