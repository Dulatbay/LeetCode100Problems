import java.util.*;

public class Medium {
    static class MyCalendar {

        TreeSet<Integer> startSet = new TreeSet<>();
        TreeSet<Integer> endSet = new TreeSet<>();

        public MyCalendar() {

        }

        public boolean book(int start, int end) {
            if (startSet.isEmpty()) {
                startSet.add(start);
                endSet.add(end);
                return true;
            }


            if (endSet.getLast() <= start) {
                startSet.add(start);
                endSet.add(end);
                return true;

            } else if (startSet.getFirst() >= end) {
                startSet.add(start);
                endSet.add(end);
                return true;

            }


            var ss = startSet.iterator();
            var ss2 = startSet.iterator();
            var es = endSet.iterator();

            if (ss2.hasNext()) ss2.next();

            while (ss2.hasNext()) {
                var start1 = ss.next();
                var start2 = ss2.next();
                var end1 = es.next();

                if (start1 <= start && start2 >= start) {
                    if (end1 <= start && end <= start2) {
                        startSet.add(start);
                        endSet.add(end);
                        return true;
                    } else return false;
                }

            }

            return false;
        }
    }

    static class MyCalendarTwo {

        TreeSet<Pair> booked = new TreeSet<>();
        TreeSet<Pair> doubleBooked = new TreeSet<>();

        static class Pair implements Comparable<Pair> {
            public int start, end;

            public Pair(int start, int end) {
                this.start = start;
                this.end = end;
            }

            public Pair() {
            }

            @Override
            public int compareTo(Pair o) {
                if (this.start == o.start) {
                    return this.end - o.end;
                }
                return start - o.start;
            }
        }


        static Pair getInterval(int start1, int end1, int start2, int end2) {
            Pair pair = new Pair();
            pair.start = Math.max(start1, start2);
            pair.end = Math.min(end1, end2);
            return pair;
        }

        public MyCalendarTwo() {

        }

        public boolean book(int start, int end) {
            if (booked.isEmpty()) {
                booked.add(new Pair(start, end));
                return true;
            }

            TreeSet<Pair> temp = new TreeSet<>();

            for (var pair : booked) {
                if (check(start, end, pair.start, pair.end)) {
                    for (var pair2 : doubleBooked) {
                        if (check(pair2.start, pair2.end, start, end)) {
                            return false;
                        }
                    }
                    temp.add(getInterval(pair.start, pair.end, start, end));
                }
            }

            doubleBooked.addAll(temp);
            booked.add(new Pair(start, end));
            return true;
        }
    }

    public String reverseWords(String s) {
        s = s.trim();
        String[] arr = Arrays.stream(s.split(" "))
                .map(String::trim)
                .toArray(String[]::new);
        Collections.reverse(Arrays.asList(arr));
        return String.join(" ", arr);
    }

    public String convert(String s, int numRows) {
        if (numRows == 1)
            return s;


        int i = 0, j = 0, n = s.length();
        String[] lines = new String[Math.min(numRows, n)];
        while (i < n) {
            if (j % 2 == 0) {
                String tmp = s.substring(i, Math.min(n, i + numRows));
                for (int k = 0; k < tmp.length(); k++) {
                    if (lines[k] == null) lines[k] = "";
                    lines[k] += tmp.charAt(k);
                }
                i += numRows;
            } else {
                String tmp = s.substring(i, Math.min(n, i + (numRows - 2)));
                for (int k = numRows - 2, l = 0; k > 0; k--, l++) {
                    if (lines[k] == null)
                        lines[k] = "";
                    if (l < tmp.length())
                        lines[k] += tmp.charAt(l);
                }
                i += numRows - 2;
            }
            j++;
        }

        return String.join("", lines);
    }

    public boolean isPalindrome(String s) {
        var list = Arrays.stream(s.split(" "))
                .map(String::trim)
                .filter(i -> !i.isBlank())
                .toList();

        String joined = String.join("", list);

        return joined.contentEquals(new StringBuilder(joined).reverse());
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> arr = new ArrayList<>();
        int n = nums.length;

        Arrays.sort(nums);

        for (int i = 0; i < n; i++) {
            if (i > 0 && nums[i] == nums[i - 1])
                continue;

            int j = i + 1;
            int k = n - 1;

            while (j < k) {
                int total = nums[i] + nums[j] + nums[k];

                if (total == 0) {
                    arr.add(List.of(nums[i], nums[j], nums[k]));

                    while (j < k && nums[j] == nums[j - 1])
                        j++;
                } else if (total > 0) {
                    k--;
                } else {
                    j++;
                }
            }
        }

        return arr;
    }

    public void rotate(int[][] matrix) {
        for (int i = 0; i < matrix.length / 2; i++) {
            rotate(matrix, i);
        }
    }

    public void rotate(int[][] matrix, int offset) {
        int n = matrix.length;

        for (int k = 0; k < n - 1 - offset; k++) {
            int prev = matrix[offset][offset], x = 1 + offset, y = offset;
            boolean toRight = true, toTop = false, isX = true;

            while (x < n - offset && y < n - offset && x >= offset && y >= offset) {
                int tmp = matrix[y][x];
                matrix[y][x] = prev;
                prev = tmp;


                if (x == n - 1 - offset && y == offset) {
                    isX = false;
                    toTop = false;
                } else if (x == n - 1 - offset && y == n - 1 - offset) {
                    isX = true;
                    toRight = false;
                } else if (x == offset && y == n - 1 - offset) {
                    isX = false;
                    toTop = true;
                }

                if (isX) {
                    if (toRight) {
                        x++;
                    } else {
                        x--;
                    }
                } else {
                    if (toTop) {
                        y--;
                    } else {
                        y++;
                    }
                }

                System.out.println(y + " " + x);
            }
        }
    }

    static class Pair {
        public int x;
        public int y;
        public int val;

        public Pair(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Pair p)) return false;
            return x == p.x && y == p.y;
        }
    }

    public int findTheLongestSubstring(String s) {
        // 1 0 1 1 0 1 0 1 0 1 0 1 0 1 0 0 1
        // 1 1 2 3 3 4 4 5 6 7 7 8 8 9 9 9 x

        int r = s.length(), max = -1;

        while (r > 0) {
            boolean found = false;

            for (int i = 0; i + r <= s.length(); i++) {
                boolean[] set = new boolean[26];
                int co = 0;
                Map<Character, Integer> map = new HashMap<>();
                for (int j = i; j < r + i; j++) {
                    char c = s.charAt(j);

                    if (isVowel(c)) {
                        var val = map.get(c);

                        if (val == null)
                            val = 0;

                        val++;

                        if (val % 2 == 1) {
                            co++;
                            set[c - 'a'] = true;
                        } else if (set[c - 'a']) {
                            co--;
                            set[c - 'a'] = false;
                        }

                        map.put(c, val);
                    }
                }

                if (co == 0) {
                    found = true;
                    break;
                }
            }

            if (found) {
                return r;
            } else r--;
        }

        return 0;
    }

    public String largestNumber(int[] nums) {
        StringBuilder sb = new StringBuilder();
        Arrays.sort(nums);

        int cnt = 0, cur = 0;
        for (int num : nums) {
            if (num >= cur) {
                cnt++;

                while (num >= cur) {
                    if (cur == 0) cur = 1;
                    cur *= 10;
                }
            }
        }

        int[][] arr = new int[cnt][2];
        int idx = 0;


        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int d = 1;

            while (num / 10 != 0) {
                num /= 10;
                d++;
            }

            int l = i, r = i;

            while (r < nums.length) {
                int k = nums[r];

                if (k >= Math.pow(10, d)) {
                    r--;
                    break;
                }

                r++;
            }

            if (r == nums.length) r--;

            i = r;


            arr[idx++] = new int[]{l, r};
        }

        cnt = 0;

        while (cnt < nums.length) {
            int maxIdx = -1, maxValue = -1;
            int j = -1;
            for (int i = arr.length - 1; i >= 0; i--) {
                int l = arr[i][0], r = arr[i][1];
                if (l <= r) {
                    int n = getFirstNum(nums[r]);
                    boolean swap = false;
                    if (maxValue == n) {
                        swap = swap(nums[maxIdx], nums[r]);
                    }

                    if (maxValue < n || swap) {
                        maxValue = n;
                        maxIdx = r;
                        j = i;
                    }
                }
            }

            sb.append(nums[maxIdx]);
            cnt++;
            arr[j][1]--;
        }

        if (sb.charAt(0) == '0') return "0";

        return sb.toString();
    }

    public int getFirstNum(int num) {
        while (num / 10 != 0) {
            num /= 10;
        }
        return num;
    }

    public boolean swap(int prevVal, int curVal) {
        long first = connect(prevVal, curVal), second = connect(curVal, prevVal);
        return first < second;
    }

    public long connect(long a, long b) {
        long tmp = b;
        int d = 1;
        while (tmp / 10 != 0) {
            tmp /= 10;
            d++;
        }

        a *= (long) Math.pow(10, d);
        a += b;

        return a;
    }

    public boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'o' || c == 'u' || c == 'i';
    }

    public static void main(String[] args) {
        Medium m = new Medium();
        System.out.println(m.minimumLength("abaacbcbb"));
    }

    public static void shuffle(int[][] arr) {
        int n = 0;

        for (int[] a : arr) {
            n += a.length;
        }

        int[] nums = new int[n];

        int idx = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                nums[idx] = arr[i][j];
                idx++;
            }
        }

        boolean[] visited = new boolean[n];
        Random rnd = new Random();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                int newValueIdx = rnd.nextInt(n);

                // если взятый индекс уже до этого был взят
                if (visited[newValueIdx]) {

                    // крутим цикл до тех пор, пока не натыкаемся на не взятый элемент
                    while (visited[newValueIdx]) {
                        newValueIdx = rnd.nextInt(n);
                    }
                }

                visited[newValueIdx] = true;
                arr[i][j] = nums[newValueIdx];
            }
        }
    }

    public static List<Integer> lexicalOrder(int n) {
        List<Integer> list = new ArrayList<>();
        observe(list, n, 1);
        System.out.println(list);
        return list;
    }

    public static void observe(List<Integer> list,
                               int n,
                               int cur) {
        if (list.size() == n) return;
        list.add(cur);
        if (cur * 10 <= n)
            observe(list, n, cur * 10);
        if (cur + 1 <= n && (cur + 1) % 10 != 0)
            observe(list, n, cur + 1);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        String s1 = getString(l1, "");
        String s2 = getString(l2, "");

        if (s2.length() < s1.length()) {
            var tmp = s2;
            s2 = s1;
            s1 = tmp;
        }

        int prev = 0;

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (; i < s1.length(); i++) {
            int num1 = s1.charAt(i) - '0';
            int num2 = s2.charAt(i) - '0';
            int sum = num1 + num2 + prev;
            int curPrev = 0;
            if (sum > 9) {
                curPrev = sum / 10;
                sum %= 10;
            }
            prev = curPrev;
            sb.append(sum);
        }

        for (; i < s2.length(); i++) {
            int num2 = s2.charAt(i) - '0';
            int sum = num2 + prev;
            int curPrev = 0;
            if (sum > 9) {
                curPrev = sum / 10;
                sum %= 10;
            }
            prev = curPrev;
            sb.append(sum);
        }

        if (prev > 0)
            sb.append(prev);

        i = 0;
        ListNode res = new ListNode();
        ListNode node = res.next;


        while (i < sb.length()) {
            int num = sb.charAt(i);

            node = new ListNode(num);
            node = node.next;
            i++;
        }

        return null;
    }

    public static int findKthNumber(int n, int k) {
        int pref = 1;
        for (int i = 1; i <= k; i++) {
            int size = getSize(pref, pref + 1, n);
            if (size <= k) {
                pref++;
                k -= size;
            } else {
                pref *= 10;
                k--;
            }
        }

        return pref;
    }

    private static int getSize(int pref, int i, int n) {
        return 0;
    }


    public String getString(ListNode node, String s) {
        if (node == null) return s;
        return getString(node.next, s + node.val);
    }

    public long maximumTotalSum(int[] maximumHeight) {
        if (maximumHeight.length == 1) return maximumHeight[0];

        Map<Integer, Integer> map = new HashMap<>();

        for (int num : maximumHeight) {
            var n = map.get(num);
            if (n == null) n = 0;
            map.put(num, n + 1);
        }

        var entryStream = map.entrySet().stream().sorted((a, b) -> b.getKey() - a.getKey()).toList();

        int min = 0;
        long sum = 0;
        int max = entryStream.getFirst().getKey();
        for (var e : entryStream) {
            int n = e.getKey();
            int f = e.getValue();

            int tmp = Math.min(max, n);

            while (f-- > 0) {
                sum += tmp--;
            }

            if (tmp < 0) return -1;

            max = tmp;
        }

        // 2 3 3 4
        //
        return sum;
    }

    public boolean canArrange(int[] arr, int k) {
        int maxK = k, max = -1;
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : arr) {
            var v = map.get(num);

            if (v == null) v = 0;
            map.put(num, v + 1);

            if (max < num) max = num;
        }

        while (maxK < max) {
            maxK += k;
        }

        Arrays.sort(arr);

        int cnt = 0;

        for (int i = 0; i < arr.length; i++) {
            int tmp = maxK;
            while (tmp >= k) {
                int se = Math.abs(tmp - arr[i]);
                var idx = Arrays.binarySearch(arr, se);
                if (idx < 0) {
                    tmp -= k;
                    continue;
                }

                var f = map.get(arr[idx]);
                if (f == 0 || (f == 1 && idx == i)) {
                    tmp -= k;
                    continue;
                }

                map.put(arr[idx], f - 1);
                map.put(arr[i], map.get(arr[i]) - 1);
                cnt++;
                break;
            }
        }

        return cnt == (arr.length / 2);
    }

    class MyCircularDeque {

        List<Integer> list = new LinkedList<>();
        int size;

        public MyCircularDeque(int k) {
            size = k;
        }

        public boolean insertFront(int value) {
            if (size == list.size()) return false;
            list.addFirst(value);
            return true;
        }

        public boolean insertLast(int value) {
            if (size == list.size()) return false;
            list.addLast(value);
            return true;

        }

        public boolean deleteFront() {
            if (list.isEmpty()) return false;
            list.removeFirst();
            return true;
        }

        public boolean deleteLast() {
            if (list.isEmpty()) return false;
            list.removeLast();
            return true;
        }

        public int getFront() {
            if (list.isEmpty()) return -1;
            return list.getFirst();
        }

        public int getRear() {
            if (list.isEmpty()) return -1;
            return list.getLast();
        }

        public boolean isEmpty() {
            return list.isEmpty();
        }

        public boolean isFull() {
            return list.size() == size;
        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            String str = String.valueOf(val);
            if (next != null) {
                return str + "->" + next;
            } else return str;
        }
    }

    public long dividePlayers(int[] skill) {
        Arrays.sort(skill);

        int l = 0, r = skill.length - 1;
        int q = skill[0] + skill[skill.length - 1];

        long sum = 0;
        while (l < r) {
            int s = skill[l++];
            int e = skill[r--];

            if (s + e != q) return -1;
            sum += s * e;
        }

        return sum;
    }

    public int minSubarray(int[] nums, int p) {
        long sum = 0;
        for (int i = 0; i < nums.length; i++)
            sum += nums[i];
        if (sum % p == 0)
            return 0;

        int requiredRem = (int) (sum % p);

        HashMap<Integer, Integer> rem = new HashMap<>();
        rem.put(0, -1);
        sum = 0;
        int res = nums.length;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int curRem = (int) (sum % p);
            int searchFor = curRem - requiredRem;
            if (searchFor < 0)
                searchFor += p;
            if (rem.containsKey(searchFor))
                res = Math.min(res, i - rem.get(searchFor));
            rem.put(curRem, i);
        }

        return res == nums.length ? -1 : res;
    }

    public boolean checkInclusion(String s1, String s2) {
        // my solution
//        if (s1.length() > s2.length()) return false;
//        if (s1.length() == s2.length()) return s1.equals(s2);
//
//        var c1 = s1.toCharArray();
//        Arrays.sort(c1);
//
//        for (int i = 0; i <= s2.length(); i += s1.length()) {
//            String subStr = s2.substring(i, i + s1.length());
//
//            var c2 = subStr.toCharArray();
//            Arrays.sort(c2);
//            boolean isOk = true;
//
//
//            for(int j = 0; j < s2.length(); j++){
//                if(c1[j] != c2[j]) {
//                    isOk = false;
//                    break;
//                }
//            }
//
//            if(isOk) return true;
//        }
//
//        return false;

        // better solution

        if (s1.length() > s2.length()) return false;

        int[] c1 = new int[26];
        int[] c2 = new int[26];


        for (int i = 0; i < s1.length(); i++) {
            c1[s1.charAt(i) - 'a']++;
            c2[s2.charAt(i) - 'a']++;
        }

        for (int i = 0; i + s1.length() < s2.length(); i++) {
            if (isEqual(c1, c2)) return true;

            c2[s2.charAt(i) - 'a']--;
            c2[s2.charAt(i + s1.length()) - 'a']++;
        }


        return isEqual(c1, c2);
    }

    public static boolean areSentencesSimilar(String sentence1, String sentence2) {
        String[] s1 = sentence1.split(" ");
        String[] s2 = sentence2.split(" ");

        if (s1.length < s2.length)
            return check(s2, s1);
        else if (s1.length == s2.length)
            return sentence1.equals(sentence2);
        else
            return check(s1, s2);
    }


    public static boolean check(String[] s1, String[] s2) {
        // A B C D B B
        // A B B
        int i = 0;
        int ins = 0;
        var str1 = String.join(" ", s1);
        var str2 = String.join(" ", s2);

        while (i < s2.length && s1[i].equals(s2[i])) {
            ins += s1[i].length() + 1;
            i++;
        }

        if (ins != 0)
            ins--;

        int j = s2.length - 1, k = s1.length - 1;

        while (j >= i && s1[k].equals(s2[j])) {
            j--;
            k--;
        }

        StringBuilder sb = new StringBuilder();

        while (i < k + 1) {
            sb.append(" ").append(s1[i++]);
        }

        var s = " " + sb.toString().trim() + " ";

        var str3 = Arrays.stream(new StringBuilder(str2).insert(ins, s).toString().split(" ")).filter(st -> !st.isBlank()).toArray();

        if (str3.length != s1.length)
            return false;

        for (i = 0; i < s1.length; i++)
            if (!s1[i].equals(str3[i])) return false;

        return true;
    }

    public int findMinArrowShots(int[][] points) {
        // [1, 6]
        // [2, 8]
        // [7, 12]
        // [10, 16]

        Arrays.sort(points, Comparator.comparingInt(a -> a[0]));

        int min = points[0][0];
        int max = points[0][1];
        int j = 1;
        for (int i = 1; i < points.length; ) {
            int k = i;
            while (i < points.length && isOverlap(min, max, points[i][0], points[i][1])) {
                min = Math.max(min, points[i][0]);
                max = Math.min(max, points[i][1]);
                i++;
            }
            if (i < points.length) {
                min = points[i][0];
                max = points[i][1];
            } else break;
            if (i == k) i++;
            j++;
        }

        return j;
    }

    private static boolean isOverlap(int start1, int end1, int start2, int end2) {
        int interval1 = end1 - start1;
        int interval2 = end2 - start2;

        if (interval1 < interval2) {
            int tmp = start1;
            start1 = start2;
            start2 = tmp;

            tmp = end1;
            end1 = end2;
            end2 = tmp;
        }


        return (start1 <= end2 && end2 <= end1) || (start1 <= start2 && start2 <= end1);
    }


    private static boolean check(int start1, int end1, int start2, int end2) {
        int interval1 = end1 - start1;
        int interval2 = end2 - start2;

        if (interval1 < interval2) {
            int tmp = start1;
            start1 = start2;
            start2 = tmp;

            tmp = end1;
            end1 = end2;
            end2 = tmp;
        }


        return (start1 < end2 && end2 < end1) || (start1 <= start2 && start2 < end1);
    }

    public int minSwaps(String s) {
        Stack<Character> stack = new Stack<>();
        int l = 0;
        while (l < s.length()) {
            var ch = s.charAt(l);
            if (!stack.isEmpty() && stack.peek() == '[' && ch == ']') {
                stack.pop();
            } else {
                stack.push(ch);
            }
            l++;
        }


        return (stack.size()) / 2;
    }

    private static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) return false;

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) return false;
        }

        return true;
    }

    public int maxWidthRamp(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < nums.length; i++) {
            if (stack.isEmpty() || nums[stack.peek()] > nums[i])
                stack.push(i);
        }

        int max = 0;

        for (int i = nums.length - 1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                max = Math.max(max, i - stack.peek());
                stack.pop();
            }
        }

        return max;
    }

    static class ChairPair {
        int time, index;

        public ChairPair(int time, int index) {
            this.time = time;
            this.index = index;
        }
    }

    public int smallestChair(int[][] times, int targetFriend) {
        PriorityQueue<ChairPair> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.time));
        PriorityQueue<Integer> av = new PriorityQueue<>();
        PriorityQueue<ChairPair> dis = new PriorityQueue<>(Comparator.comparingInt(a -> a.time));

        for (int i = 0; i < times.length; i++) {
            pq.offer(new ChairPair(times[i][0], i));
            av.offer(i);
        }

        int t = 0;
        while (!pq.isEmpty()) {
            while (!dis.isEmpty() && dis.peek().time == t) {
                var chairNumber = dis.remove().index;
                av.offer(chairNumber);
            }

            var pair = pq.peek();

            if (pair.time == t) {
                pq.remove();
                int leaveTime = times[pair.index][1];
                var chair = av.remove();

                if (pair.index == targetFriend) return chair;

                dis.offer(new ChairPair(leaveTime, chair));
            }

            t = Math.min(pq.isEmpty() ? Integer.MAX_VALUE : pq.peek().time, dis.isEmpty() ? Integer.MAX_VALUE : dis.peek().time);

            if (t == Integer.MAX_VALUE) break;
        }


        return -1;
    }

    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode preLast = new ListNode(-1, head);
        int i = 1;
        while (i < left) {
            preLast = preLast.next;
            i++;
        }

        ListNode dummy = new ListNode(-1);
        ListNode l = null;
        ListNode cur = preLast.next;
        while (i <= right) {
            var tmp = new ListNode(cur.val);

            tmp.next = dummy;
            if (l == null) l = tmp;
            dummy = tmp;
            cur = cur.next;
            i++;
        }

        if (preLast.next == head) {
            if (l != null)
                l.next = cur;
            return dummy;
        }

        preLast.next = dummy;
        if (l != null)
            l.next = cur;

        return head;
    }


    StringBuilder sb = new StringBuilder();

    public String longestDiverseString(int a, int b, int c) {
        longestDiverseString(a, b, c, '0');
        return sb.toString();
    }

    public void longestDiverseString(int a, int b, int c, char prev) {
        int[] maxChar = getMax(a, b, c, prev);
        int maxSize = 0;
        if (maxChar[1] == 'a') {
            if (maxChar[0] == 1) {
                maxSize = Math.min(2, a);
            } else maxSize = 1;
            a -= maxSize;
        } else if (maxChar[1] == 'b') {
            if (maxChar[0] == 1) {
                maxSize = Math.min(2, b);
            } else maxSize = 1;
            b -= maxSize;
        } else {
            if (maxChar[0] == 1) {
                maxSize = Math.min(2, c);
            } else maxSize = 1;
            c -= maxSize;
        }

        for (int i = 0; i < maxSize; i++) {
            sb.append((char) maxChar[1]);
        }

        prev = (char) maxChar[1];

        boolean isA = (prev == 'a' && a > 0) || a == 0,
                isB = (prev == 'b' && b > 0) || b == 0,
                isC = (prev == 'c' && c > 0) || c == 0;

        if (isA && isB && isC)
            return;

        longestDiverseString(a, b, c, prev);
    }

    public int[] getMax(int a, int b, int c, char prev) {
        int tmp = Math.max(a, Math.max(b, c));

        if (prev == '0') {

            if (tmp == a) return new int[]{1, 'a'};
            else if (tmp == b) return new int[]{1, 'b'};
            else return new int[]{1, 'c'};
        }

        if (prev == 'a') return b > c ? new int[]{tmp == b ? 1 : 0, 'b'} : new int[]{tmp == c ? 1 : 0, 'c'};
        else if (prev == 'b') return a > c ? new int[]{tmp == a ? 1 : 0, 'a'} : new int[]{tmp == c ? 1 : 0, 'c'};
        else return a > b ? new int[]{tmp == a ? 1 : 0, 'a'} : new int[]{tmp == b ? 1 : 0, 'b'};
    }

    public int maximumSwap(int num) {
        List<Integer> list = new LinkedList<>();

        while (num != 0) {
            list.addFirst(num % 10);
            num /= 10;
        }

        var arr = list.stream().mapToInt(i -> i).toArray();

        return getMaxFromArr(maximumSwap(arr, 0));
    }

    public int getMaxFromArr(int[] arr) {
        int res = 0;
        for (int num : arr) {
            res = (res * 10) + (num);
        }


        return res;
    }

    public int[] maximumSwap(int[] nums, int l) {
        if (l >= nums.length) return nums;
        int maxIdx = l;

        for (int i = nums.length - 1; i >= l + 1; i--) {
            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }

        if (maxIdx == l) {
            return maximumSwap(nums, l + 1);
        }

        int tmp = nums[l];
        nums[l] = nums[maxIdx];
        nums[maxIdx] = tmp;

        return nums;
    }

    public int countMaxOrSubsets(int[] nums) {
        int maxOr = nums[0];

        for (int i = 1; i < nums.length; i++) {
            maxOr |= nums[i];
        }

        return getCountOfSubsets(nums, maxOr);
    }

    Integer cnt;

    private void dfs(int[] nums, int i, List<Integer> cur, int maxOr) {
        if (i == nums.length) {
            if (cur.isEmpty()) return;
            int curOr = cur.getFirst();

            for (var num : cur) {
                curOr |= num;
            }

            if (curOr == maxOr) cnt++;

            return;
        }
        cur.add(nums[i]);
        dfs(nums, i + 1, cur, maxOr);
        cur.removeLast();
        dfs(nums, i + 1, cur, maxOr);
    }

    private Integer getCountOfSubsets(int[] nums, int maxOr) {
        List<Integer> cur = new ArrayList<>();
        cnt = 0;
        dfs(nums, 0, cur, maxOr);

        return cnt;
    }

    public char findKthBit(int n, int k) {
        StringBuilder sb = new StringBuilder("0");
        while (n > 0) {
            if (sb.length() >= k) {
                return sb.charAt(k - 1);
            }

            var reversed = invertAndReverse(sb.toString());

            sb.append(1).append(reversed);

            n--;
        }

        return '0';
    }

    private String invertAndReverse(String str) {
        StringBuilder sb = new StringBuilder();
        for (var ch : str.toCharArray()) {
            if (ch == '0') sb.append('1');
            else sb.append('0');
        }

        return sb.reverse().toString();
    }


    public int maxUniqueSplit(String s) {
        return maxUniqueSplit(s, 0, new HashSet<>());
    }

    public int maxUniqueSplit(String s, int start, Set<String> used) {
        if (s.length() <= start) return 0;

        int max = 1;

        for (int i = start + 1; i <= s.length(); i++) {
            var subStr = s.substring(start, i);

            if (!used.contains(subStr)) {
                used.add(subStr);
                max = Math.max(max, maxUniqueSplit(s, i, used) + 1);
                used.remove(subStr);
            }
        }

        return max;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public long kthLargestLevelSum(TreeNode root, int k) {
        TreeMap<Integer, Long> sum = new TreeMap<>();
        processNode(root, sum, k);

        if (k > sum.size()) return -1;

        return sum.values().stream().sorted(Comparator.reverseOrder()).toList().get(k - 1);
    }

    public void processNode(TreeNode node, TreeMap<Integer, Long> sums, int curLvl) {
        var val = node.val;
        sums.put(curLvl, sums.getOrDefault(curLvl, 0L) + val);

        if (node.left != null) processNode(node.left, sums, curLvl + 1);
        if (node.right != null) processNode(node.right, sums, curLvl + 1);
    }


    public int longestSquareStreak(int[] nums) {
        int[] count = new int[100001];

        for (int num : nums) count[num]++;

        int max = -1;
        boolean[] visited = new boolean[100001];
        for (int num : nums) {
            if (visited[num]) continue;
            visited[num] = true;
            long sqL = (long) num * num;

            if (sqL > 100001) continue;

            int sq = (int) sqL;
            int cur = 1;

            while (count[sq] != 0) {
                cur += 1;
                visited[sq] = true;
                sqL = (long) sq * sq;

                if (sqL > 100001) continue;
                sq = (int) sqL;
            }

            if (cur == count[sq]) continue;
            max = Math.max(cur, max);
        }


        return max;
    }

    public int findLengthOfShortestSubarray(int[] arr) {
        int right = arr.length - 1;
        while (right > 0 && arr[right] >= arr[right - 1]) {
            right--;
        }

        int ans = right;
        int left = 0;
        while (left < right && (left == 0 || arr[left - 1] <= arr[left])) {
            // find next valid number after arr[left]
            while (right < arr.length && arr[left] > arr[right]) {
                right++;
            }
            // save length of removed subarray
            ans = Math.min(ans, right - left - 1);
            left++;
        }
        return ans;
    }

    public long maximumSubarraySum(int[] nums, int k) {
        long sum = 0, curSum = 0;
        int n = nums.length,
                l = 0,
                r = 0;
        boolean[] dict = new boolean[100001];


        while (r < n) {
            if (dict[nums[r]]) {
                while (l < r && nums[l] != nums[r]) {
                    dict[nums[l]] = false;
                    curSum -= nums[l++];
                }
                dict[nums[l]] = false;
                curSum -= nums[l++];
            }

            dict[nums[r]] = true;
            curSum += nums[r];

            if (r - l == k - 1) {
                sum = Math.max(sum, curSum);
                dict[nums[l]] = false;
                curSum -= nums[l++];
            }
            r++;
        }

        return sum;
    }

    public int takeCharacters(String s, int k) {
        int[] freq = new int[3];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        for (int i = 0; i < 3; i++) {
            if (freq[i] < k) return -1;
        }

        int left = 0, right = 0, n = s.length(), maxWindow = 0;
        int[] window = new int[3];
        while (right < n) {
            window[s.charAt(right) - 'a']++;

            while (left <= right && (freq[0] - window[0] < k ||
                    freq[1] - window[1] < k ||
                    freq[2] - window[2] < k)) {
                window[s.charAt(left) - 'a']--;
                left++;
            }

            maxWindow = Math.max(maxWindow, right - left + 1);
            right++;
        }


        return n - maxWindow;
    }


    public static class Node {
        public int index;
        public List<Integer> children = new ArrayList<>();

        public Node(int index) {
            this.index = index;
        }
    }


    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(i);

            if (i + 1 != n)
                nodes[i].children.add(i + 1);
        }

        int[] result = new int[queries.length];
        int index = 0;


        for (int[] query : queries) {
            Node from = nodes[query[0]];
            from.children.add(query[1]);

            result[index++] = bfs(n, nodes);
        }

        return result;
    }

    public int bfs(int n, Node[] nodes) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[n];
        int[] dist = new int[n];


        visited[0] = true;
        q.add(0);
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;


        while (!q.isEmpty()) {
            int cur = q.poll();
            var node = nodes[cur];

            for (int child : node.children) {
                if (!visited[child]) {
                    visited[child] = true;
                    q.add(child);
                    dist[child] = Math.min(dist[child], dist[cur] + 1);
                }
            }
        }


        return dist[n - 1];
    }


    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        // for each X store every Y
        List<TreeSet<Integer>> xDists = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            xDists.add(new TreeSet<>());
        }

        // for each Y store every X
        List<TreeSet<Integer>> yDists = new ArrayList<>(m);

        for (int i = 0; i < m; i++) {
            yDists.add(new TreeSet<>());
        }

        boolean[][] cells = new boolean[m][n];

        for (int[] g : guards) {
            yDists.get(g[0]).add(g[1]);
            xDists.get(g[1]).add(g[0]);

            cells[g[0]][g[1]] = true;
        }

        for (int[] w : walls) {
            yDists.get(w[0]).add(w[1]);
            xDists.get(w[1]).add(w[0]);

            cells[w[0]][w[1]] = true;
        }

        for (int[] g : guards) {
            var distY = xDists.get(g[1]);
            var distX = yDists.get(g[0]);

            int top = 0,
                    bottom = 0,
                    right = 0,
                    left = 0;

            if (distX.isEmpty()) {
                left = g[1];
                right = (n - 1) - g[1];
            } else {
                var leftDist = distX.lower(g[1]);
                var rightDist = distX.higher(g[1]);

                if (leftDist == null) left = g[1];
                else {
                    left = g[1] - leftDist - 1;
                }

                if (rightDist == null) right = (n - 1) - g[1];
                else {
                    right = rightDist - g[1] - 1;
                }
            }

            if (distY.isEmpty()) {
                top = g[0];
                bottom = (m - 1) - g[0];
            } else {
                var topDist = distY.lower(g[0]);
                var bottomDist = distY.higher(g[0]);

                if (topDist == null) top = g[0];
                else {
                    top = g[0] - topDist - 1;
                }

                if (bottomDist == null) bottom = (m - 1) - g[0];
                else {
                    bottom = bottomDist - g[0] - 1;
                }
            }

            for (int i = g[1] - left; i <= g[1] + right; i++) {
                cells[g[0]][i] = true;
            }

            for (int i = g[0] - top; i <= g[0] + bottom; i++) {
                cells[i][g[1]] = true;
            }
        }

        int sz = 0;

        for (boolean[] row : cells) {
            for (boolean cell : row) {
                if (!cell) sz++;
            }
        }

        return sz;
    }

    private final Queue<Integer> queue = new PriorityQueue<>();

    public int kthSmallest(TreeNode root, int k) {
        observe(root);

        while (k > 1) {
            k--;
            queue.poll();
        }


        String str = "hello";


        return queue.poll();
    }

    private void observe(TreeNode node) {
        queue.add(node.val);
        if (node.left != null) observe(node.left);
        if (node.right != null) observe(node.right);
    }

    class LRUCache {
        private final Integer[] values = new Integer[100001];
        private final List<Integer> used = new LinkedList<>();
        private final int capacity;

        public LRUCache(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            if (values[key] == null) return -1;

            used.stream()
                    .filter(i -> i.equals(key))
                    .findFirst().ifPresent(used::remove);
            used.addLast(key);

            return values[key];
        }

        public void put(int key, int value) {
            if (capacity == used.size() && values[key] == null) {
                var removed = used.removeFirst();
                values[removed] = null;
                used.addLast(key);
                values[key] = value;
            } else if (capacity == used.size()) {
                used.stream()
                        .filter(i -> i.equals(key))
                        .findFirst().ifPresent(used::remove);
                used.addLast(key);
                values[key] = value;
            } else {
                used.addLast(key);
                values[key] = value;
            }
        }
    }

    public boolean canChange(String start, String target) {
        int i = 0, j = 0, n = start.length();

        while (i < n && j < n) {
            if (start.charAt(i) == '_') {
                i++;
                continue;
            }

            if (start.charAt(i) == 'L') {
                while (j < n && target.charAt(j) != 'L') j++;
                if (i < j) return false;
                j++;
            } else if (start.charAt(i) == 'R') {
                while (j < n && target.charAt(j) != 'R') j++;
                if (i > j) return false;
                j++;
            }
            i++;
        }

        return true;
    }

    public boolean[] isArraySpecial(int[] nums, int[][] queries) {
        List<Set<Integer>> arr = new ArrayList<>();
        arr.add(new HashSet<>());
        arr.getFirst().add(nums[0]);
        for (int i = 1; i < nums.length; i++) {
            boolean isPrevEven = nums[i - 1] % 2 == 0;
            boolean isCurEven = nums[i] % 2 == 0;
            if ((isPrevEven && isCurEven) || (!isPrevEven && !isCurEven)) {
                arr.add(new HashSet<>());
            }
            arr.getLast().add(nums[i]);
        }


        boolean[] res = new boolean[queries.length];
        int j = 0;
        for (int[] q : queries) {
            int l = -1, r = -1;
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i).contains(nums[q[0]])) {
                    l = i;
                    r = arr.get(i).contains(nums[q[1]]) ? i : -1;

                    if (l == r) break;
                }
            }

            res[j++] = (r == l);
        }

        return res;
    }

    public int maximumLength(String s) {
        int max = -1;

        for (int i = 0; i < s.length(); i++) {

            for (int size = 1; size + i <= s.length(); size++) {
                boolean isOk = true;
                String str = s.substring(i, i + size);
                char f = str.charAt(0);

                for (char c : str.toCharArray()) {
                    if (c != f) {
                        isOk = false;
                        break;
                    }
                }

                if (!isOk) {
                    break;
                }

                int substrs = find(s, str);

                if (substrs > 2) max = Math.max(max, str.length());

            }
        }


        return max;
    }

    private int find(String source, String target) {
        int cnt = 0;

        int i = 0;

        while (i < source.length()) {
            if (source.charAt(i) == target.charAt(0)) {
                int j = 1, t = i + 1;
                while (j < target.length() && t < source.length() && target.charAt(j) == source.charAt(t)) {
                    t++;
                    j++;
                }

                if (j == target.length()) {
                    cnt++;
                }
            }

            i++;
        }

        return cnt;
    }

    public int[] getFinalState(int[] nums, int k, int multiplier) {
        if (multiplier == 1) return nums;
        if (multiplier == 0) return new int[nums.length];
        if (k == 0) return nums;

        Queue<int[]> q = new PriorityQueue<int[]>(nums.length, (a, b) -> {
            if (a[0] == b[0])
                return a[1] - b[1];
            return a[0] - b[0];
        });

        for (int i = 0; i < nums.length; i++) {
            q.add(new int[]{nums[i], i});
        }

        while (k-- > 0) {
            int[] num = q.poll();
            int newValue = num[0] * multiplier;
            nums[num[1]] = newValue;
            q.add(new int[]{newValue, num[1]});
        }

        return nums;
    }

    public String repeatLimitedString(String s, int repeatLimit) {
        char[] symbols = s.toCharArray();
        Arrays.sort(symbols);

        StringBuilder sb = new StringBuilder();
        boolean[] used = new boolean[s.length()];
        int i = s.length() - 1, cnt = 0;
        int[] next = new int[26];

        Arrays.fill(next, -1);

        while (i >= 0) {
            int j = i - 1;
            while (j >= 0 && symbols[j] == symbols[i]) j--;
            if (j == -1) break;

            next[symbols[i] - 'a'] = j;
            i = j;
        }

        i = s.length() - 1;

        while (i >= 0) {
            int j = i;
            while (j >= 0 && cnt < repeatLimit && symbols[i] == symbols[j]) {
                if (used[j]) {
                    j--;
                    continue;
                }
                used[j] = true;
                cnt++;
                j--;
                sb.append(symbols[i]);
            }

            if (j >= 0 && cnt == repeatLimit && symbols[j] == symbols[i]) {
                int k = next[symbols[j] - 'a'];
                if (k == -1) break;
                sb.append(symbols[k]);
                used[k] = true;
                if (k > 0 && symbols[k] == symbols[k - 1])
                    next[symbols[j] - 'a']--;
                else
                    next[symbols[j] - 'a'] = next[symbols[k] - 'a'];
            }

            cnt = 0;
            i = j;
        }

        return sb.toString();
    }

    public List<Integer> largestValues(TreeNode root) {
        List<Integer> list = new LinkedList<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);


        while (!q.isEmpty()) {
            int sz = q.size();
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < sz; i++) {
                TreeNode node = q.poll();
                max = Math.max(max, node.val);
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }
            list.add(max);
        }


        return list;
    }

    public int maxScoreSightseeingPair(int[] values) {
        int max = 0;
        // value - index
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> {
            if (a[0] == b[0]) return b[1] - a[1];
            return b[0] - a[0];
        });

        for (int i = 0; i < values.length; i++) {
            q.offer(new int[]{values[i], i});
        }
        int[] first = q.poll();
        while (!q.isEmpty()) {
            int[] second = q.poll();

            if (first[1] > second[1]) {
                max = Math.max(max, calculate(second[1], first[1], values));
            } else {
                max = Math.max(max, calculate(first[1], second[1], values));
            }

            if (q.isEmpty()) break;

            int[] third = q.poll();
            if (first[1] > third[1]) {
                max = Math.max(max, calculate(third[1], first[1], values));
            } else {
                max = Math.max(max, calculate(first[1], third[1], values));
            }

            if (second[1] > third[1]) {
                max = Math.max(max, calculate(third[1], second[1], values));
            } else {
                max = Math.max(max, calculate(second[1], third[1], values));
            }

            first = second;
        }

        return max;
    }

    private int calculate(int l, int r, int[] values) {
        return values[l] + values[r] + (l - r);
    }


    public int maximumAmount(int[][] coins) {
        return getMax(0, 0, 2, 0, coins);
    }

    public int getMax(int i, int j, int cnt, int c, int[][] coins) {
        if (i == coins.length - 1 && j == coins[0].length - 1) {
            if(coins[i][j] < 0 && cnt != 0) return c;
            else return c + coins[i][j];
        }
        int k = c;
        if (coins[i][j] < 0) {
            if (cnt != 0 && j != coins[0].length - 1) {
                c = getMax(i, j + 1, cnt - 1, k, coins);
            }
            if (j != coins[0].length - 1) {
                c = Math.max(c, getMax(i, j + 1, cnt, k + coins[i][j], coins));
            }
            if (cnt != 0 && i != coins.length - 1) {
                c = Math.max(c, getMax(i + 1, j, cnt - 1, k, coins));
            }
            if (i != coins.length - 1) {
                c = Math.max(c, getMax(i + 1, j, cnt, k + coins[i][j], coins));
            }
        } else {
            if (j != coins[0].length - 1)
                c = Math.max(c, getMax(i, j + 1, cnt, k + coins[i][j], coins));
            if (i != coins.length - 1)
                c = Math.max(c, getMax(i + 1, j, cnt, k + coins[i][j], coins));
        }

        return c;
    }

    public boolean canBeValid(String s, String locked) {
        if(s.length() % 2 == 1) return false;
        if(s.charAt(0) == ')' && locked.charAt(0) == '1') return false;

        return canBeValid(s, locked, 0, s.length());
    }

    public boolean canBeValid(String s, String l, int idx, int len) {
        if(idx == len) return true;
        boolean res = false;
        boolean correctStart = s.charAt(idx) == '(' || l.charAt(idx) == '0';

        if(correctStart && (s.charAt(idx + 1) == ')' || l.charAt(idx + 1) == '0'))
            res = canBeValid(s, l, idx + 2, len);

        if(res) return true;

        if(correctStart && (s.charAt(len - 1) == ')' || l.charAt(idx) == '0'))
            res = canBeValid(s, l, idx + 1, len - 1);

        return res;
    }

    public int minimumLength(String s) {
        int[] freq = new int[26];

        for(char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int cnt = 0;

        for(int f : freq) {
            while(f >= 3) {
                f -= 2;
            }

            cnt += f;
        }

        return cnt;
    }

}

