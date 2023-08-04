package java.leetcode;

public class Code53MaxSubArray {

    //暴力枚举～
    public int maxSubArray(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int res = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int sum = Integer.MIN_VALUE;
            int ans = 0;
            for (int j = i; j < nums.length; j++) {
                ans += nums[j];
                sum = Math.max(ans, sum);
            }
            res = Math.max(res, sum);
        }
        return res;
    }

    //动态规划 dp
    public int maxSubArray1(int[] nums) {
        int pre = 0, sum = nums[0];
        for (int i = 0; i < nums.length; i++) {
            // 如何前面的和还没有 nums[i] 大, 那么将和的初始值设置为 nums[i]
            // 你出现至今, 如果还没有新的你好, 那么我将抛弃你选择新的你
            pre = Math.max(pre + nums[i], nums[i]);
            // 记录最大值
            // 但是对不起, 我只能记得最好的你
            sum = Math.max(sum, pre);
        }
        return sum;
    }


    public static void main(String[] args) {
        Code53MaxSubArray maxSubArray = new Code53MaxSubArray();
        int[] ints = {
             -2, 1, -3, 4, -1, 2, 1, -5, 4
//            -2, -1
        };
        System.out.println(maxSubArray.maxSubArray(ints));
        System.out.println(maxSubArray.maxSubArray1(ints));
    }
}
