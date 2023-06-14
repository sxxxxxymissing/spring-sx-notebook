package com.SxxxxxyMissing.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 49. 字母异位词分组 (中等) 排序2最快
 */
public class Code49GroupAnagrams {

    // 排序1
    public List<List<String>> groupAnagrams(String[] strs) {
        return new ArrayList<>(Arrays.stream(strs).collect(Collectors.groupingBy(str -> {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            return String.valueOf(chars);
        })).values());
    }

    // 排序2
    public List<List<String>> groupAnagrams1(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        char[] chars;
        for (String str : strs) {
            chars = str.toCharArray();
            Arrays.sort(chars);
            String value = String.valueOf(chars);
            if (!map.containsKey(value)) {
                map.put(value, new ArrayList<>());
            }
            map.get(value).add(str);
        }
        return new ArrayList<>(map.values());
    }

    // 计数1
    public List<List<String>> groupAnagrams2(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();
        char[] chars;
        for (String str : strs) {
            StringBuilder builder = new StringBuilder();
            chars = str.toCharArray();
            int[] counts = new int[26];
            for (char c : chars) {
                counts[c - 'a']++;
            }
            for (int i = 0; i < counts.length; i++) {
                if (counts[i] > 0) {
                    builder.append(i + 'a').append(counts[i]);
                }
            }
            String key = builder.toString();
            if (map.containsKey(key)) {
                map.get(key).add(str);
            }
            else {
                List<String> list = new ArrayList<>();
                list.add(str);
                map.put(key, list);
            }
        }
        return new ArrayList<>(map.values());
    }

    // 质数
    public List<List<String>> groupAnagrams3(String[] strs) {
        int[] prime = {
            2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101
        };
        Map<Integer, List<String>> map = new HashMap<>();
        char[] chars;
        for (String str : strs) {
            chars = str.toCharArray();
            int key = 1;
            for (char c : chars) {
                key = key * prime[c - 'a'];
            }
            if (map.containsKey(key)) {
                map.get(key).add(str);
            }
            else {
                List<String> list = new ArrayList();
                list.add(str);
                map.put(key, list);
            }
        }
        return new ArrayList<>(map.values());
    }

    // 计数2
    public List<List<String>> groupAnagrams4(String[] strs) {
        return new ArrayList<>(Arrays.stream(strs).collect(Collectors.groupingBy(str -> {
            char[] chars = str.toCharArray();
            int[] counts = new int[26];
            for (char c : chars) {
                counts[c - 'a']++;
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < counts.length; i++) {
                if (counts[i] > 0) {
                    builder.append(i + 'a').append(counts[i]);
                }
            }
            return builder.toString();
        })).values());
    }

    public static void main(String[] args) {
        String[] str = {
            "abbbbbbbbbbb", "aaaaaaaaaaab"
        };
        Code49GroupAnagrams groupAnagrams = new Code49GroupAnagrams();
        System.out.println(groupAnagrams.groupAnagrams(str));
        System.out.println(groupAnagrams.groupAnagrams1(str));
        System.out.println(groupAnagrams.groupAnagrams2(str));
        System.out.println(groupAnagrams.groupAnagrams3(str));
        System.out.println(groupAnagrams.groupAnagrams4(str));

    }

}
