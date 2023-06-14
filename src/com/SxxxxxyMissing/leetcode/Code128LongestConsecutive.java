package com.SxxxxxyMissing.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Code128LongestConsecutive {

    // 排序
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        int ans = 1, sum = 1;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                continue;
            }
            if (nums[i] + 1 == nums[i + 1]) {
                sum++;
            }
            else {
                ans = Math.max(sum, ans);
                sum = 1;
            }
        }
        ans = Math.max(sum, ans);
        return ans;
    }

    // 动态规划
    public int longestConsecutive1(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (!map.containsKey(num)) {
                // 左右边界长度
                Integer right = map.getOrDefault(num + 1, 0);
                Integer left = map.getOrDefault(num - 1, 0);
                // 更新长度
                int sum = right + left + 1;
                ans = Math.max(ans, sum);
                map.put(num, sum);
                // 更新边界长度
                map.put(num - left, sum);
                map.put(num + right, sum);
            }
        }
        return ans;
    }

    // 哈希表
    public int longestConsecutive2(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int ans = 0, sum = 1;
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }
        for (int num : set) {
            // num必须为起点
            if (!set.contains(num - 1)) {
                while (set.contains(++num)) {
                    sum++;
                }
                ans = Math.max(ans, sum);
                sum = 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Code128LongestConsecutive longestConsecutive = new Code128LongestConsecutive();
        int[] ints = {
            0, 3, 7, 2, 5, 8, 4, 6, 0, 1
        };
        System.out.println(longestConsecutive.longestConsecutive(ints));
        System.out.println(longestConsecutive.longestConsecutive1(ints));
        System.out.println(longestConsecutive.longestConsecutive2(ints));
    }
}
