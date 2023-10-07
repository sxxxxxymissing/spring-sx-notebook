package leetcode;

import java.util.HashMap;
import java.util.Map;

public class Code03LengthOfLongestSubstring {

    // 动态滑窗 map 版
    public int lengthOfLongestSubstring(String s) {
        int ans = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < s.length(); end++) {
            char alpha = s.charAt(end);
            // 判断在map中是否有, 也就是前面字符串是否出现过, 如果有则将指针指向下一位
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha) + 1, start);
            }
            // 获取最大值
            ans = Math.max(ans, end - start + 1);
            map.put(alpha, end);
        }
        return ans;
    }

    // 动态滑窗 数组版
    public int lengthOfLongestSubstring1(String s) {
        int ans = 0, start = 0;
        int[] ints = new int[127];
        for (int i = 0; i < s.length(); i++) {
            char alpha = s.charAt(i);
            if (ints[alpha] > 0) {
                start = Math.max(ints[alpha], start);
            }
            // 获取最大值
            ans = Math.max(ans, i - start + 1);
            // 指向上一次出现的位置并指向下一位
            ints[alpha] = i + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        Code03LengthOfLongestSubstring longestSubstring = new Code03LengthOfLongestSubstring();
        System.out.println(longestSubstring.lengthOfLongestSubstring("abcabcbb"));
        System.out.println(longestSubstring.lengthOfLongestSubstring1("abcabcbb"));
    }

}
