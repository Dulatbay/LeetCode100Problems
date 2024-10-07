import java.util.*;

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

    public int minLength(String s) {
        Stack<Character> st = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);

            if (!st.isEmpty() && ((cur == 'B' && st.peek() == 'A') || (cur == 'D' && st.peek() == 'C'))) {
                st.pop();
            } else {
                st.push(cur);
            }
        }


        return st.size();
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

    public int[] arrayRankTransform(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] a = Arrays.copyOf(arr, arr.length);
        Arrays.sort(arr);

        int rank = 1;
        for (int i = 0; i < arr.length; ) {
            int j = i + 1;
            while (j < arr.length && arr[j] == arr[i]) {
                j++;
            }
            map.put(arr[i], rank++);
            i = j;
        }

        // 1 1 2 3 4
        //

        for (int i = 0; i < arr.length; i++) {
            a[i] = map.get(arr[i]);
        }

        return a;
    }

    public static void main(String[] args) {
        Easy easy = new Easy();


        System.out.println(easy.minLength("CCADDADDDBBCDDBABACADABAABADCABDCCBDACBDBAADDABCABBCABBDDAABCBCBBCCCDBCDDDADAACBCACDDBBA"));
    }


}
