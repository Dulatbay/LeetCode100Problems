import java.util.*;

public class Main {
    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        final int n = Integer.parseInt(sc.nextLine());

        int i = 1;

        Map<Integer, Node> map = new HashMap<>();

        while (i <= n) {
            var node = getNode(map, i);
            var line = sc.nextLine().split(" ");


            node.val = Integer.parseInt(line[0]);


            for (int j = 1; j < line.length; j++) {
                var tmp = getNode(map, Integer.valueOf(line[j]));
                tmp.children.add(i);
            }
            i++;
        }

        boolean[] visited = new boolean[n + 1];

        int sum = 0;


        var list = map.values()
                .stream()
                .sorted((a, b) -> b.children.size() - a.children.size())
                .toList();

        for (var item : list) {
            if (visited[item.index]) continue;
            visited[item.index] = true;

            int mn = item.val;

            for (var j : item.children) {
                mn = Math.max(observeNode(map.get(j), visited, map), mn);
            }

            sum += mn;
        }

        System.out.println(sum);
    }

    public static int observeNode(Node node, boolean[] visited, Map<Integer, Node> map) {
        if (visited[node.getIndex()]) return 0;
        visited[node.getIndex()] = true;

        int mn = node.val;

        for (var j : node.children) {
            mn = Math.max(mn, observeNode(map.get(j), visited, map));
        }

        return mn;
    }


    public static Node getNode(Map<Integer, Node> map, Integer key) {
        return map.computeIfAbsent(key, k -> new Node(key));
    }

    public static class Node {
        private final int index;
        public int val;
        public List<Integer> children = new ArrayList<>();

        public Node(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }
    }

}