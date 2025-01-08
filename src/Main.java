import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println(main.primeSubOperation(new int[] {13,11,16,6,9}));
    }

    public static String makeFancyString(String s) {
        StringBuilder sb = new StringBuilder();
        // aaabaaaa

        for (int i = 0; i < s.length(); ) {
            int j = i + 1;

            while (j < s.length() && s.charAt(i) == s.charAt(j)) {
                j++;
            }

            sb.append(s, i, Math.min(i + 2, j));

            i = j;
        }


        return sb.toString();
    }

    public boolean canSortArray(int[] nums) {
        int maxPrevSegment = Integer.MIN_VALUE;
        int curMaxSegment = nums[0];
        int curMinSegment = nums[0];
        int countOfBits = Integer.bitCount(nums[0]);

        for(int i = 1; i < nums.length; i++) {
            if(Integer.bitCount(nums[i]) == countOfBits) {
                curMinSegment = Math.min(curMinSegment, nums[i]);
                curMaxSegment = Math.max(curMaxSegment, nums[i]);
            } else {
                if(maxPrevSegment > curMinSegment) return false;

                maxPrevSegment = curMaxSegment;
                curMaxSegment = nums[i];
                curMinSegment = nums[i];
                countOfBits = Integer.bitCount(nums[i]);
            }
        }


        return maxPrevSegment < curMinSegment;
    }


    public  boolean primeSubOperation(int[] nums) {
        int N = 10001;
        boolean[] isPrime = new boolean[N];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;
        for(int i = 2; i < N; i++) {
            if(isPrime[i]) {
                for(int j = i * i; j < N; j+=i) {
                    isPrime[j] = false;
                }
            }
        }

        for(int i = nums.length - 2; i >= 0; i--) {
            if(nums[i] >= nums[i + 1]) {
                for(int j = N - 1; j > 1; j--) {
                    if(isPrime[j] && nums[i] - j < nums[i + 1] && j < nums[i]) {
                        isPrime[j] = false;
                        nums[i] -= j;
                        break;
                    }
                    if(j == 2) return false;
                }

            }
        }


        return true;
    }



}