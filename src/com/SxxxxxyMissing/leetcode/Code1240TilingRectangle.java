package com.SxxxxxyMissing.leetcode;

/**
 * 铺瓷砖 (困难)
 */
public class Code1240TilingRectangle {

    //动态规划(抄的)
    public int tilingRectangle(int n, int m) {
        int[][] memo = new int[n + 1][m + 1];
        return dp(memo, n, m);
    }

    int dp(int[][] memo, int n, int m) {
        if (n == m) {
            return 1;
        }

        if (n == 1) {
            return m;

        }
        if (m == 1) {
            return n;
        }
        if (memo[n][m] == 0) {
            int res = n * m;
            for (int i = 1; i <= n / 2; i++) {
                res = Math.min(res, dp(memo, i, m) + dp(memo, n - i, m));
            }
            for (int j = 1; j <= m / 2; j++) {
                res = Math.min(res, dp(memo, n, j) + dp(memo, n, m - j));
            }
            for (int a = 1; a < m && a < n; a++) {
                for (int b = 1; b < m - a; b++) {
                    for (int c = 1; c < n - a; c++) {
                        res = Math.min(res,
                            1 + dp(memo, n - c, b) + dp(memo, c, b + a) + dp(memo, a + c, m - b - a) + dp(memo, n - c - a, m - b));
                    }
                }
            }
            memo[n][m] = res;
        }
        return memo[n][m];
    }

    public int calculateMinimumSquares(int n, int m) {
        int squares = 0;
        while (n != 0 && m != 0) {
            squares += (n * m);
            if (n > m) {
                n = n - m;
            } else {
                m = m - n;
            }
        }
        return squares;
    }

    public static void main(String[] args) {
        Code1240TilingRectangle tilingRectangle = new Code1240TilingRectangle();
        System.out.println(tilingRectangle.calculateMinimumSquares(2,3));
    }

}
