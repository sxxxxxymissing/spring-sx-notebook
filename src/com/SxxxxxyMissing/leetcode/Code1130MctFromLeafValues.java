package com.SxxxxxyMissing.leetcode;

import java.util.Stack;

/**
 * 1130 叶值的最小代价生成树 (中等)
 */
public class Code1130MctFromLeafValues {

    public int mctFromLeafValues(int[] arr) {
        Stack<Integer> st = new Stack<>();
        /** 作为栈底用当计算到栈底的时候, 如果没有栈底则会报错 */
        // st.push(Integer.MAX_VALUE);
        int mct = 0;
        for (int i = 0; i < arr.length; i++) {
            /** 如果arr[i] >= Stack中头部元素(不出栈) */
            while (arr[i] >= st.peek()) {
                /** 先出栈并计算最小值 */
                mct += st.pop() * Math.min(st.peek(), arr[i]);
            }
            /** 入栈 */
            st.push(arr[i]);
        }
        while (st.size() > 1) {
            /** 先出栈 乘以 栈顶元素 */
            mct += st.pop() * st.peek();
        }
        return mct;
    }

    public static void main(String[] args) {
        Code1130MctFromLeafValues mct = new Code1130MctFromLeafValues();
        int[] ints = {
            6, 2, 5
        };
        System.out.println(mct.mctFromLeafValues(ints));
    }
}
