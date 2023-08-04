package java.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Code54SpiralOrder {

    public List<Integer> spiralOrder(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] pass = new boolean[m][n];
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0, row = 0, col = 0; i < m * n; i++) {
            if (pass[row][col]) {
                pass[row][col] = false;
                list.add(matrix[row][col]);
            }
            if (row < n && col < m) {
                row++;
            }
            if (row == n && col < m) {
                col++;
            }
        }




        return list;
    }

    public static void main(String[] args) {

        String[] arr = new String[1];

        String str = arr.toString();



    }


}
