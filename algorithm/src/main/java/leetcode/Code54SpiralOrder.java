package leetcode;

import java.util.ArrayList;
import java.util.List;

public class Code54SpiralOrder {

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        // 设定边界
        int left = 0, right = matrix[0].length - 1, top = 0, bottom = matrix.length - 1;
        while (true) {
            // 从左到右
            for (int i = left; i <= right; i++) {
                res.add(matrix[top][i]);
            }
            // 判断边界
            if (++top > bottom) {
                break;
            }
            // 从上到下
            for (int i = top; i <= bottom; i++) {
                res.add(matrix[i][right]);
            }
            // 判断边界
            if (--right < left) {
                break;
            }
            // 从右到左
            for (int i = right; i >= left; i--) {
                res.add(matrix[bottom][i]);
            }
            // 判断边界
            if (--bottom < top) {
                break;
            }
            // 从下到上
            for (int i = bottom; i >= top; i--) {
                res.add(matrix[i][left]);
            }
            // 判断边界
            if (++left > right) {
                break;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[][] ints = {
            {
                1, 2, 3, 4
            }, {
                5, 6, 7, 8
            }, {
                9, 10, 11, 12
            }
        };
        Code54SpiralOrder spiralOrder = new Code54SpiralOrder();
        System.out.println(spiralOrder.spiralOrder(ints));
    }

}
