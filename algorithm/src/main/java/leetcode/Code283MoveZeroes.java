package leetcode;

public class Code283MoveZeroes {

    // 双指针
    public void moveZeroes(int[] nums) {
        for (int i = 0, j = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                this.swap(i, j, nums);
                j++;
            }
        }
        // return nums;
    }

    public void moveZeroes1(int[] nums) {
        int index = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[index++] = num;
            }
        }
        while (index < nums.length) {
            nums[index++] = 0;
        }
    }

    public void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        Code283MoveZeroes moveZeroes = new Code283MoveZeroes();
        int[] ints = {
            0, 1, 0, 3, 12
        };
        // System.out.println(Arrays.toString(moveZeroes.moveZeroes(ints)));
        // System.out.println(Arrays.toString(moveZeroes.moveZeroes1(ints)));
    }
}
