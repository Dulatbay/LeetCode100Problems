import java.util.*;

public class Hard {

    static class Node {
        public int count;
        public Node[] next = new Node[26];
    }

    public int[] sumPrefixScores(String[] words) {
        Node root = new Node();

        for (String word : words) {
            Node curr = root;
            for (char ch : word.toCharArray()) {
                int index = ch - 'a';
                if (curr.next[index] == null) {
                    curr.next[index] = new Node();
                }
                curr = curr.next[index];
                curr.count++;
            }
        }

        int[] res = new int[words.length];

        int j = 0;
        for (String word : words) {
            Node curr = root;
            int count = 0;
            for (int i = 0; i < word.length(); i++) {
                int index = word.charAt(i) - 'a';
                curr = curr.next[index];
                count += curr.count;
            }
            res[j++] = count;
        }

        return res;
    }

    public static List<Integer> validSequence(String word1, String word2) {
        // Step 1: Precompute positions of each character in word1
        Map<Character, List<Integer>> charPositions = new HashMap<>();
        for (int i = 0; i < word1.length(); i++) {
            char c = word1.charAt(i);
            charPositions.computeIfAbsent(c, k -> new ArrayList<>()).add(i);
        }

        List<Integer> result = new ArrayList<>();
        int prevIndex = -1;  // To track the last chosen index in word1

        // Step 2: Iterate over each character in word2
        for (char c : word2.toCharArray()) {
            if (!charPositions.containsKey(c)) {
                return new ArrayList<>();  // No valid sequence if character is missing in word1
            }

            List<Integer> positions = charPositions.get(c);

            // Step 3: Binary search to find the smallest index in word1 that is greater than prevIndex
            int idx = findNextValidIndex(positions, prevIndex);
            if (idx == -1) {
                return new ArrayList<>();  // No valid sequence possible
            }

            result.add(positions.get(idx));
            prevIndex = positions.get(idx);
        }

        return result;
    }

    // Helper method to find the smallest valid index using binary search
    private static int findNextValidIndex(List<Integer> positions, int prevIndex) {
        int left = 0, right = positions.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (positions.get(mid) > prevIndex) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return (left < positions.size()) ? left : -1;
    }


    /**
     * Your AllOne object will be instantiated and called as such:
     * AllOne obj = new AllOne();
     * obj.inc(key);
     * obj.dec(key);
     * String param_3 = obj.getMaxKey();
     * String param_4 = obj.getMinKey();
     */

    public static void main(String[] args) {
        System.out.println(validSequence("abc", "vbcca"));
        System.out.println(validSequence("abc", "bacdc"));
    }
}
