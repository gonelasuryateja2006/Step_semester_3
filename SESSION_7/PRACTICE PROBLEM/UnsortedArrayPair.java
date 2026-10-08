import java.util.HashSet;
import java.util.Set;

public class UnsortedArrayPair {

    public static boolean hasPairBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((long) nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean hasPairWithSum(int[] nums, int target) {
        Set<Long> seen = new HashSet<>();

        for (int number : nums) {
            long required = (long) target - number;

            if (seen.contains(required)) {
                return true;
            }

            seen.add((long) number);
        }

        return false;
    }

    public static void main(String[] args) {
        int[] first = {2, 7, 11, 15};
        int[] second = {3, 4, 6};

        System.out.println("Brute force:");
        System.out.println(hasPairBruteForce(first, 9));
        System.out.println(hasPairBruteForce(second, 20));

        System.out.println("HashSet:");
        System.out.println(hasPairWithSum(first, 9));
        System.out.println(hasPairWithSum(second, 20));
    }
}