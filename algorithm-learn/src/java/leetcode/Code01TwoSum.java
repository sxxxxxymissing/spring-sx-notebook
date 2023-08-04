package java.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和 (简单)
 */
public class Code01TwoSum {

    //动态滑窗
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int ans;
        for (int i = 0; i < nums.length; i++) {
            // 获得差值
            ans = target - nums[i];
            // 判断这个值是否存在
            if (map.containsKey(ans)) {
                return new int[] {
                    map.get(ans), i
                };
            }
            // 将数组本身维护进map中
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
