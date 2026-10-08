import java.util.Arrays;

public class TokenQueueMerger {

    public static int[] mergeTokens(int[] counterA, int[] counterB) {
        int m = counterA.length;
        int n = counterB.length;
        int[] merged = new int[m + n];

        int i = 0, j = 0, k = 0;

        // Two pointers comparison
        while (i < m && j < n) {
            if (counterA[i] <= counterB[j]) {
                merged[k++] = counterA[i++];
            } else {
                merged[k++] = counterB[j++];
            }
        }

        // Copy remaining elements from counterA
        while (i < m) {
            merged[k++] = counterA[i++];
        }

        // Copy remaining elements from counterB
        while (j < n) {
            merged[k++] = counterB[j++];
        }

        return merged;
    }

    public static void main(String[] args) {
        int[] counterA1 = {3, 8, 15, 20};
        int[] counterB1 = {5, 8, 12};
        System.out.println(Arrays.toString(mergeTokens(counterA1, counterB1))); // Output: [3, 5, 8, 8, 12, 15, 20]

        int[] counterA2 = {};
        int[] counterB2 = {4, 9};
        System.out.println(Arrays.toString(mergeTokens(counterA2, counterB2))); // Output: [4, 9]
    }
}