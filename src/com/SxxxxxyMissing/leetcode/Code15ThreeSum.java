package com.SxxxxxyMissing.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Code15ThreeSum {

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            // 大于0时、那么后面的和一定不为0
            if (nums[i] > 0) {
                break;
            }
            //对 i 去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            //确定2个指针
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                //得到和
                int sum = nums[i] + nums[right] + nums[left];
                //移动左指针、同时去重
                if (sum > 0) {
                    while (left < right && nums[right] == nums[--right]);
                }
                //移动右指针、同时去重
                else if (sum < 0) {
                    while (left < right && nums[left] == nums[++left]);
                }
                else {
                    //为0则获得答案
                    result.add(new ArrayList<>(Arrays.asList(nums[i], nums[right], nums[left])));
                    //移动指针到下一个数字， 同时去重
                    while (left < right && nums[right] == nums[--right]);
                    while (left < right && nums[left] == nums[++left]);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Code15ThreeSum threeSum = new Code15ThreeSum();
        int[] ints = {
            1, -1, -1, 0
        };
        System.out.println(threeSum.threeSum(ints));
    }
}
