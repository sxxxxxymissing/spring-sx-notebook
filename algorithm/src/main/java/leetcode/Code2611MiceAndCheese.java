package leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 2611. 老鼠与奶酪 (中等)
 */
public class Code2611MiceAndCheese {

    // 贪心 + 排序
    public int miceAndCheese(int[] reward1, int[] reward2, int k) {
        int[] result = new int[reward1.length];
        int ans = 0;
        for (int i = 0; i < reward1.length; i++) {
            /** 得到所有差值 */
            result[i] = reward1[i] - reward2[i];
            /** 初始化所有值 */
            ans += reward2[i];
        }
        /** 升序列排序 */
        Arrays.sort(result);
        /** 计算根据最大差值对ans 更新 */
        for (int i = 1; i <= k; i++) {
            //更新
            ans += result[result.length - i];
        }
        return ans;
    }

    // 贪心 + 队列
    public int miceAndCheese1(int[] reward1, int[] reward2, int k) {
        Queue<Integer> queue = new PriorityQueue<>();
        int ans = 0;
        for (int i = 0; i < reward1.length; i++) {
            /** 初始化所有值 */
            ans += reward2[i];
            queue.offer(reward1[i] - reward2[i]);
            if (queue.size() > k) {
                queue.poll();
            }
        }

        /** 取出所有queue中的值 */
        while (!queue.isEmpty()) {
            ans += queue.poll();
        }
        return ans;
    }

    public static void main(String[] args) {
        Code2611MiceAndCheese miceAndCheese = new Code2611MiceAndCheese();
        int[] reward1 = {
            1, 1, 3, 4
        };
        int[] reward2 = {
            4, 4, 1, 1
        };
        System.out.println(miceAndCheese.miceAndCheese(reward1, reward2, 2));
        System.out.println(miceAndCheese.miceAndCheese1(reward1, reward2, 2));
    }
}
