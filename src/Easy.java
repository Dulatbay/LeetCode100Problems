public class Easy {

    // #1 - https://leetcode.com/problems/minimum-operations-to-exceed-threshold-value-i
    public int minOperations(int[] nums, int k) {
        int mp = 0;

        for (var num : nums)
            if (num < k)
                mp++;

        return mp;
    }
}
