package java.leetcode;

/**
 * 寻找两个正序数组的中位数 (困难)
 */
public class Code04FindMedianSortedArrays {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // i为nums1下标, j为nums2下标, k用于循环中位的次数
        int i = 0, j = 0, k = 0, m1 = 0, m2 = 0;
        // 确定中位长度
        int mid = (nums1.length + nums2.length) / 2;
        // k由0循环到中位
        while (k <= mid && (i < nums1.length || j < nums2.length)) {
            // 记录mid-1的值
            m1 = m2;
            // 条件一:如果i = nums1.length(也就是如果i达到了nums1.length的边界, 也就是边界保护)
            // 条件二:(i != nums1.length, j != nums2.length, nums2[j] < nums1[i])
            // j != nums2.length 边界保护
            // 主要目的是为了对nums2[j] < nums1[i]做判断, 然后决定i, j哪一个进行下标向后移动
            if (i == nums1.length || (j != nums2.length && nums2[j] < nums1[i]))
                m2 = nums2[j++];
            else
                m2 = nums1[i++];
            k++;
        }
        // 如果是奇数, 第mid的位置则为中位 nums1 = {1}, nums2 = {1,2} {1,1,2} 那么mid = 1 为中位下标
        // 如果是偶数, mid与mid-1下标则为的平均值则为中位 nums1 = {1,2}, nums2 = {3,4} {1,2,3,4} 那么mid = 2, mid-1 = 1 此时中位应该是 (2+3)/2
        return (nums1.length + nums2.length) % 2 == 1 ? (double) m2 : (double) (m1 + m2) / 2;
    }

    public static void main(String[] args) {

        int[] nums1 = {
            1, 1
        };

        int[] nums2 = {
            1, 2
        };

        Code04FindMedianSortedArrays findMedianSortedArrays = new Code04FindMedianSortedArrays();
        System.out.println(findMedianSortedArrays.findMedianSortedArrays(nums1, nums2));
    }
}
