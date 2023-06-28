package com.SxxxxxyMissing.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 找到字符串中所有字母异位词 (中等)
 */
public class Code438FindAnagrams {

    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int sLength = s.length(), pLength = p.length();
        if (sLength < pLength) {
            return res;
        }
        //记录start、end 位置 只到 end - =  后同时向后移动start、end
        int start = 0, end = 0;
        char[] chars = p.toCharArray();
        //记录p每个字符对应的数字出现的次数
        int[] ints = new int[26];
        //用于移动窗口数据
        int[] win = new int[26];
        for (int i = 0; i < pLength; i++) {
            ints[chars[i] - 'a']++;
        }
        chars = s.toCharArray();
        while (end < sLength) {
            //加入窗口数据
            win[chars[end] - 'a']++;
            //长度相等时比较答案
            if (end - start + 1 == pLength) {
                //相同则记录起点
                if (Arrays.equals(win, ints)) {
                    res.add(start);
                }
                //start像后移动并移除当前位置
                win[chars[start] - 'a']--;
                start++;
            }
            //end 像后移动
            end++;
        }
        return res;
    }

    public List<Integer> findAnagrams1(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int sLength = s.length(), pLength = p.length();
        if (sLength < pLength) {
            return res;
        }
        //记录start、end 位置 只到 end - =  后同时向后移动start、end
        int start = 0, end = 0;
        char[] chars = p.toCharArray();
        //记录p每个字符对应的数字出现的次数
        int[] ints = new int[26];
        //用于移动窗口数据
        int[] win = new int[26];
        for (int i = 0; i < pLength; i++) {
            ints[chars[i] - 'a']++;
        }
        chars = s.toCharArray();
        while (end < sLength) {
            //加入窗口数据
            win[chars[end] - 'a']++;
            //长度相等时比较答案
            if (end - start + 1 == pLength) {
                //相同则记录起点
                if (Arrays.equals(win, ints)) {
                    res.add(start);
                }
                //start像后移动并移除当前位置
                win[chars[start] - 'a']--;
                start++;
            }
            //end 像后移动
            end++;
        }
        return res;
    }

    public static void main(String[] args) {
        Code438FindAnagrams findAnagrams = new Code438FindAnagrams();
        System.out.println(findAnagrams.findAnagrams("abab", "ab"));
        System.out.println(findAnagrams.findAnagrams("cbaebabacd", "abc"));
    }
}
