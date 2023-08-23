package leetcode;

public class Code238ProductExceptSelf {

    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        for (int i = 1, j = 1; i <= n; i++) {
            ans[i - 1] = j;
            j *= nums[i - 1];
        }
        for (int i = n, j = 1; i >= 1; i--) {
            ans[i - 1] *= j;
            j *= nums[i - 1];
        }
        return ans;
    }

    // 优化
    public int[] productExceptSelf1(int[] nums) {
        int len = nums.length;
        int[] ans = new int[len];
        ans[0] = nums[0];
        for (int i = 1; i < len; i++) { // 左侧的累乘积
            ans[i] = ans[i - 1] * nums[i];
        }
        int mul = 1; // 右侧的累乘积
        for (int i = len - 1; i > 0; i--) {
            ans[i] = ans[i - 1] * mul;
            mul *= nums[i];
        }
        ans[0] = mul;
        return ans;
    }

}
