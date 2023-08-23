package leetcode;

import java.util.LinkedList;
import java.util.List;

public class Code239MaxSlidingWindow {

    public int[] maxSlidingWindow(int[] nums, int k) {

        if (nums.length == k) {
            return nums;
        }
        int[] ints = new int[nums.length - k + 1];
        List<Integer> queue = new LinkedList<>();
        for (int i = 0; i < k; i++) {
            queue.add(nums[i]);
        }


        return null;
    }

}
