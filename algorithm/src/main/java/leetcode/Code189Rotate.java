package leetcode;

import java.util.Arrays;

/**
 * 189. 轮转数组 (中等)
 */
public class Code189Rotate {

    // 新数组
    public void rotate(int[] nums, int k) {
        int length = nums.length;
        int[] ints = new int[length];

        for (int i = 0; i < length; i++) {
            ints[(i + k) % length] = nums[i];
        }
        // don't understand asshole leetCode say wrong from it's answer,
        // nums = ints;
        // nums = Arrays.copyOf(ints, length);
        nums = Arrays.copyOf(ints, length);
        System.arraycopy(ints, 0, nums, 0, length);
        System.out.println(Arrays.toString(nums));
    }

    // 翻转数组
    public void rotate1(int[] nums, int k) {
        int length = nums.length;
        int divider = k % length;
        this.reveres(nums, 0, length - 1);
        this.reveres(nums, divider, length - 1);
        this.reveres(nums, 0, divider - 1);
        System.out.println(Arrays.toString(nums));
    }

    private void reveres(int[] nums, int head, int tail) {
        while (head < tail) {
            int temp = nums[head];
            nums[head] = nums[tail];
            nums[tail] = temp;
            head++;
            tail--;
        }
    }

    public int gcd(int x, int y) {
        return y > 0 ? gcd(y, x % y) : x;
    }

    public static void main(String[] args) {
        Code189Rotate rotate = new Code189Rotate();
        System.out.println(rotate.gcd(6, 2));
        // int[] ints = {
        // 1, 2, 3, 4, 5, 6, 7
        // };
        // rotate.rotate(ints, 3);
        // rotate.rotate1(ints, 3);
    }
}
