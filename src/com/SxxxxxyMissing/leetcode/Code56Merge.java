package com.SxxxxxyMissing.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 56. 合并区间 (中等)
 */
public class Code56Merge {

    //排序
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 1) {
            return intervals;
        }
        // 数组排序 会十分浪费时间
        // 对你每次说爱我的时间做排序
        Arrays.sort(intervals, (e, x) -> e[0] - x[0]);
        // 初始下标
        // 爱我的次数
        int index = 0;
        //初始值
        int[][] ans = new int[intervals.length][];
        ans[0] = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            // 不合并
            // 你重新爱我
            if (intervals[i][0] > ans[index][1]) {
                // 记录, 次数 + 1
                ans[++index]  = intervals[i];
            }
            // 合并
            // 原来你还爱我
            else {
                // 与上一次时间合并
                ans[index][1] = Math.max(intervals[i][1], ans[index][1]);
            }
        }

        return Arrays.copyOf(ans, index + 1);
    }

    public int[][] merge1(int[][] intervals) {
        if (intervals.length == 1) {
            return intervals;
        }
        Arrays.sort(intervals, (e, x) -> e[0] - x[0]);
        List<int[]> list = new ArrayList<>();
        int left = intervals[0][0];
        int right = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] > right) {
                list.add(new int[]{
                        left, right
                });
                left = intervals[i][0];
                right =  intervals[i][1];
            }
            else {
                right = Math.max(right, intervals[i][1]);
            }
        }
        list.add(new int[]{
                left, right
        });
        return list.toArray(new int[list.size()][2]);
    }

}
