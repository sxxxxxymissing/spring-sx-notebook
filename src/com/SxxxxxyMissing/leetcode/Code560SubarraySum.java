package com.SxxxxxyMissing.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 560. 和为 K 的子数组 (中等)
 */
public class Code560SubarraySum {

    public int subarraySum(int[] nums, int k) {
        int count = 0, sum = 0;
        Map<Integer, Integer> mp = new HashMap<>();
        mp.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            // 记录前缀和 sum
            sum += nums[i];
            // sum - k 查找窗口中是已经存在这个 值
            int key = sum - k;
            // 从已存储的所有前缀和用寻找是否有相同的key
            if (mp.containsKey(key)) {
                //加上 sum 出现的次数
                count += mp.get(key);
            }
            // 记录sum出现了多少次
            mp.put(sum, mp.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

    public static void main(String[] args) {
        Code560SubarraySum subarraySum = new Code560SubarraySum();
        int[] ints = {
                1, 2, 1, 2
        };
        System.out.println(subarraySum.subarraySum(ints, 0));
    }
}
