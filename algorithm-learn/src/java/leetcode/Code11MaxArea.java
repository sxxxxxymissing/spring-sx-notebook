package java.leetcode;

/**
 * 盛最多水的容器 (中等)
 */
public class Code11MaxArea {

    // 暴力枚举
    public int maxArea(int[] height) {
        int ans = 0, right = 0, left = height.length - 1;
        for (int i = 0; i < height.length; i++) {
            for (int j = height.length - 1; j > i; j--) {
                ans = Math.max(ans, (j - i) * Math.min(height[i], height[j]));

            }
        }
        return ans;
    }

    // 双指针
    public int maxArea1(int[] height) {
        int ans = 0, left = 0, right = height.length - 1;
        while (left < right) {
            //先计算当前指针对应的值
            int area = Math.min(height[right], height[left]) * (right - left);
            int h = Math.min(height[left], height[right]);
            ans = Math.max(ans, area);
            //左指针移动到下一个更 h 更大的位置, 高度相等则不动
            while (h >= height[left] && left < right) {
                left++;
            }
            //右指针移动到下一个更 h 更大的位置
            while (h >= height[right] && left < right) {
                right--;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Code11MaxArea maxArea = new Code11MaxArea();
        int[] ints = {
            1, 8, 6, 2, 5, 4, 8, 3, 7
        };
        System.out.println(maxArea.maxArea(ints));
        System.out.println(maxArea.maxArea1(ints));
    }
}
