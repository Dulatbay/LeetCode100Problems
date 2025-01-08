import java.util.*;

public class Hard {

//    static class Node {
//        public int count;
//        public Node[] next = new Node[26];
//    }
//
//    public int[] sumPrefixScores(String[] words) {
//        Node root = new Node();
//
//        for (String word : words) {
//            Node curr = root;
//            for (char ch : word.toCharArray()) {
//                int index = ch - 'a';
//                if (curr.next[index] == null) {
//                    curr.next[index] = new Node();
//                }
//                curr = curr.next[index];
//                curr.count++;
//            }
//        }
//
//        int[] res = new int[words.length];
//
//        int j = 0;
//        for (String word : words) {
//            Node curr = root;
//            int count = 0;
//            for (int i = 0; i < word.length(); i++) {
//                int index = word.charAt(i) - 'a';
//                curr = curr.next[index];
//                count += curr.count;
//            }
//            res[j++] = count;
//        }
//
//        return res;
//    }

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
        Hard hard = new Hard();
        int[][] arr = new int[][]{
                {0, 1, 3, 2}, {5, 1, 2, 5}, {4, 3, 8, 6}
        };
        System.out.println(hard.minimumTime(arr));
    }

    public int calculate(String s) {
        return calculate(s, 0, s.length() - 1);
    }

    public int calculate(String s, int l, int r) {
        Stack<Integer> st = new Stack<>();
        Stack<Character> op = new Stack<>();
        // - (3 + (4 + 5))
        for (int i = l; i <= r; ) {
            char ch = s.charAt(i);

            if (ch == ' ') {
                i++;
                continue;
            }
            // - (3 + (4 + 5))
            if (ch == '(') {
                int k = i + 1;
                int cnt = 1;
                while (k <= r) {
                    if (s.charAt(k) == '(') {
                        cnt++;
                    }
                    if (s.charAt(k) == ')') {
                        cnt--;
                    }
                    if (cnt == 0) {
                        break;
                    }
                    k++;
                }
                st.push(calculate(s, i + 1, k - 1));
                i = k + 1;
            } else if (ch == '-' && st.isEmpty()) {
                int j = i + 1;

                while (j <= r && s.charAt(j) == ' ') {
                    j++;
                }

                String prev = "";
                if (s.charAt(j) == '(') {
                    int k = j + 1;
                    int cnt = 1;
                    while (k <= r) {
                        if (s.charAt(k) == '(') {
                            cnt++;
                        }
                        if (s.charAt(k) == ')') {
                            cnt--;
                        }
                        if (cnt == 0) {
                            break;
                        }
                        k++;
                    }

                    st.push(-(calculate(s, j + 1, k - 1)));

                    i = k + 1;
                } else {
                    while (j <= r && Character.isDigit(s.charAt(j))) {
                        prev += s.charAt(j++);
                    }
                    i = j;
                    st.push(Integer.parseInt(prev));
                }

            } else if (Character.isDigit(ch)) {
                String prev = "" + ch;
                int j = i + 1;
                while (j <= r && Character.isDigit(s.charAt(j))) {
                    prev += s.charAt(j++);
                }

                st.push(Integer.parseInt(prev));

                i = j;
            } else {
                op.push(ch);
                i++;
            }
        }

        while (!op.isEmpty()) {
            char ch = op.removeFirst();
            int n1 = st.removeFirst();
            int n2 = st.removeFirst();

            switch (ch) {
                case '*':
                    st.insertElementAt(n1 * n2, 0);
                    break;
                case '/':
                    st.insertElementAt(n1 / n2, 0);
                    break;
                case '+':
                    st.insertElementAt(n1 + n2, 0);
                    break;
                case '-':
                    st.insertElementAt(n1 - n2, 0);
                    break;
            }
        }

        return st.pop();
    }

//    private static class Node {
//        public int row;
//        public int col;
//        public List<int[]> children;
//        public int cost;
//
//        public Node(int col, int row, int cost, List<int[]> children) {
//            this.col = col;
//            this.row = row;
//            this.cost = cost;
//            this.children = children;
//        }
//
//    }
//
//    public int minimumObstacles(int[][] grid) {
//        int m = grid.length, n = grid[0].length;
//        Node[][] nodes = new Node[m][n];
//
//        for (int row = 0; row < m; row++) {
//            for (int col = 0; col < n; col++) {
//                nodes[row][col] = new Node(row, col, grid[row][col], new ArrayList<>());
//
//                if (row > 0) nodes[row][col].children.add(new int[]{row - 1, col});
//                if (row + 1 < m) nodes[row][col].children.add(new int[]{row + 1, col});
//                if (col > 0) nodes[row][col].children.add(new int[]{row, col - 1});
//                if (col + 1 < n) nodes[row][col].children.add(new int[]{row, col + 1});
//            }
//        }
//
//        int[][] costs = new int[m][n];
//        for (int row = 0; row < m; row++) {
//            for (int col = 0; col < n; col++) {
//                costs[row][col] = Integer.MAX_VALUE;
//            }
//        }
//        costs[0][0] = 0;
//
//
//        boolean[][] visited = new boolean[m][n];
//        visited[0][0] = true;
//
//        Queue<int[]> queue = new LinkedList<>();
//        queue.offer(new int[]{0, 0});
//
//        while (!queue.isEmpty()) {
//            int[] cur = queue.poll();
//            int row = cur[0];
//            int col = cur[1];
//            Node node = nodes[row][col];
//
//            for (int[] child : node.children) {
//                int rowChild = child[0];
//                int colChild = child[1];
//
//                if (!visited[rowChild][colChild] || costs[rowChild][colChild] > nodes[rowChild][colChild].cost + costs[row][col]) {
//                    visited[rowChild][colChild] = true;
//                    queue.offer(new int[]{rowChild, colChild});
//                    costs[rowChild][colChild] = Math.min(costs[rowChild][colChild], nodes[rowChild][colChild].cost + costs[row][col]);
//                }
//            }
//        }
//
//
//        return costs[m - 1][n - 1];
//    }


    private static class Node {
        public int cost;
        public List<int[]> children = new ArrayList<>();

        public Node(int cost) {
            this.cost = cost;
        }
    }

    public int minimumTime(int[][] grid) {
        int n = grid.length, m = grid[0].length;

        Node[][] nodes = new Node[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                nodes[i][j] = new Node(grid[i][j]);

                if (i > 0) nodes[i][j].children.add(new int[]{i - 1, j});
                if (i + 1 < n) nodes[i][j].children.add(new int[]{i + 1, j});
                if (j > 0) nodes[i][j].children.add(new int[]{i, j - 1});
                if (j + 1 < n) nodes[i][j].children.add(new int[]{i, j + 1});
            }
        }

        boolean[][] visited = new boolean[n][m];
        visited[0][0] = true;

        return dfs(0, 0, nodes, 1, visited);
    }

    private int dfs(int curI, int curJ, Node[][] nodes, int t, boolean[][] visited) {
        var curNode = nodes[curI][curJ];
        int min = Integer.MAX_VALUE;

        for (int[] child : curNode.children) {
            var childNode = nodes[child[0]][child[1]];
            int minWay = Integer.MAX_VALUE;

            if (visited[child[0]][child[1]]) continue;
            visited[child[0]][child[1]] = true;


            if (childNode.cost <= t) {
                minWay = Math.min(minWay, dfs(child[0], child[1], nodes, t + 1, visited));
            } else if (curI != 0 && curJ != 0) {
                var need = (childNode.cost - t);
                minWay = Math.min(minWay, dfs(child[0], child[1], nodes, t + need + (need % 2 == 0 ? 1 : 0), visited));
            }

            min = Math.min(min, minWay);
        }

        return min;
    }

}
