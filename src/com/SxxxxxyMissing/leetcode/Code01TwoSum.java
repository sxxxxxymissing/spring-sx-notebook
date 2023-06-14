package com.SxxxxxyMissing.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和 (简单)
 */
public class Code01TwoSum {

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int ans;
        for (int i = 0; i < nums.length; i++) {
            ans = target - nums[i];
            if (map.containsKey(ans)) {
                return new int[] {
                    map.get(ans), i
                };
            }
            map.put(nums[i], i);
        }
        return new int[] {};
    }

    public static void main(String[] args) {
        Code01TwoSum twoSum = new Code01TwoSum();
        System.out.println(Arrays.toString(twoSum.twoSum(new int[] {
            2, 7, 11, 15
        }, 9)));
    }
}
