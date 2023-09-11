package leetcode;

/**
 * 240. 搜索二维矩阵 II (中等)
 */
public class Code240SearchMatrix {

    //暴力枚举
    public boolean searchMatrix(int[][] matrix, int target) {
        int n = matrix[0].length;
        for (int[] ints : matrix) {
            for (int j = 0; j < n; j++) {
                if (ints[j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    //暴力枚举优化 - 二分
    public boolean searchMatrix1(int[][] matrix, int target) {
        for (int[] row : matrix) {
            int index = search(row, target);
            if (index >= 0) {
                return true;
            }
        }
        return false;
    }

    public int search(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        while (low <= high) {
            int mid = (high - low) / 2 + low;
            int num = nums[mid];
            if (num == target) {
                return mid;
            } else if (num > target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    public boolean searchMatrix2(int[][] matrix, int target) {
        int row = 0;
        int col = matrix[0].length - 1;
        int m = matrix.length;
        while (row < m && col > 0) {
            if (matrix[row][col] == target) {
                return true;
            }
            if (matrix[row][col] > target) {
                col--;
            }
            else if (matrix[row][col] < target) {
                row++;
            }
        }
        return false;
    }


}
