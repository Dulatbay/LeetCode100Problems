import javax.script.ScriptEngineManager;
import java.util.*;
import java.util.stream.Collectors;

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

        static boolean check(int start1, int end1, int start2, int end2) {
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

//        System.out.println(areSentencesSimilar("My name is Haley", "My Haley"));
//        System.out.println(areSentencesSimilar("d A d A", "A"));
        System.out.println(areSentencesSimilar("A a a a A A A", "A A a a a"));
        System.out.println(areSentencesSimilar("xD iP tqchblXgqvNVdi", "FmtdCzv Gp YZf UYJ xD iP tqchblXgqvNVdi"));
        System.out.println(areSentencesSimilar("A B C D B B", "A B B"));
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

    public class ListNode {
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


    private static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) return false;

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) return false;
        }

        return true;
    }

}

