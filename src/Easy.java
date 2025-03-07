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


        System.out.println(easy.zigzagTraversal(new int[][]{
                {1,2,1,3},{5,15,7,3},{10,4,14,12}
        }));
    }

    public long pickGifts(int[] gifts, int k) {
        long res = 0;
        Queue<Long> q = new PriorityQueue<>();

        for (int gift : gifts) {
            q.add((long) -gift);
            res += gift;
        }

        while (k > 0 && !q.isEmpty()) {
            var gift = -q.poll();
            res -= gift;

            long left = (long) Math.sqrt(gift);
            q.add(-left);
            res += left;

            k--;
        }


        return res;
    }

    public List<Integer> zigzagTraversal(int[][] grid) {
        boolean[][] arr = new boolean[grid.length][grid[0].length];
        arr[0][0] = true;
        boolean isRight = true;
        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < grid.length; i++) {
            if (isRight) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (arr[i][j])
                        continue;
                    if (i > 0 && arr[i - 1][j])
                        continue;
                    if (i < grid.length - 1 && arr[i + 1][j])
                        continue;
                    if (j > 0 && arr[i][j - 1])
                        continue;
                    if (j < grid[i].length - 1 && arr[i][j + 1])
                        continue;
                    arr[i][j] = true;
                    res.add(grid[i][j]);
                }
            } else {
                for (int j = grid[0].length - 1; j >= 0; j--) {
                    if (arr[i][j])
                        continue;
                    if (i > 0 && arr[i - 1][j])
                        continue;
                    if (i < grid.length - 1 && arr[i + 1][j])
                        continue;
                    if (j > 0 && arr[i][j - 1])
                        continue;
                    if (j < grid[i].length - 1 && arr[i][j + 1])
                        continue;
                    arr[i][j] = true;
                    res.add(grid[i][j]);
                }
            }

            isRight = !isRight;
        }


        return res;
    }


}
