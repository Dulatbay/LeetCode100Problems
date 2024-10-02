import java.util.HashSet;
import java.util.Set;

public class Easy {

    // #1 - https://leetcode.com/problems/minimum-operations-to-exceed-threshold-value-i
    public int minOperations(int[] nums, int k) {
        int mp = 0;

        for (var num : nums)
            if (num < k)
                mp++;

        return mp;
    }

    public int minBitFlips(int start, int goal) {
        int count = 0;
        int xor = start ^ goal; // XOR will give 1 where the bits differ

        while (xor != 0) {
            count += xor & 1; // Increment count if the last bit is 1
            xor >>= 1; // Right-shift to check the next bit
        }


        return count;
    }

    public char kthCharacter(int k) {
        StringBuilder sb = new StringBuilder("a");

        while (sb.length() <= k) {
            String tmp = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            for (int i = 0; i < tmp.length(); i++) {
                char c = (char) (tmp.charAt(i) + (char) 1);
                sb2.append((char) (c % 'z'));
            }
            sb.append(sb2);
        }

        System.out.println(sb);

        return sb.charAt(k - 1);
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (k == 0) return false;
        Set<Integer> visited = new HashSet<>();

        int l = 0;

        for (int r = 0; r < nums.length; r++) {
            if (r - l > k) {
                visited.remove(nums[l++]);
            }
            if (visited.contains(nums[r])) {
                return true;
            }
            visited.add(nums[r]);
        }

        return false;
    }

    public static void main(String[] args) {
        Easy easy = new Easy();

        System.out.println(easy.kthCharacter(5));
        System.out.println(easy.kthCharacter(10));
    }


}
