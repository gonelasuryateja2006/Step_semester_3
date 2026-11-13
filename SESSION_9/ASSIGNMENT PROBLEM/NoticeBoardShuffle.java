import java.util.Arrays;

public class NoticeBoardShuffle {

    public static int[] bubbleStats(int[] scores) {
        if (scores == null || scores.length <= 1) {
            return new int[]{0, 0};
        }

        int n = scores.length;
        int passes = 0;
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            passes++; // Count a pass as soon as it starts

            // Compare adjacent elements up to the unsorted portion
            for (int j = 0; j < n - 1 - i; j++) {
                if (scores[j] > scores[j + 1]) {
                    // Swap neighbouring elements
                    int temp = scores[j];
                    scores[j] = scores[j + 1];
                    scores[j + 1] = temp;

                    swaps++;
                    swapped = true;
                }
            }

            // Stop early if no swaps occurred during this sweep
            if (!swapped) {
                break;
            }
        }

        return new int[]{passes, swaps};
    }

    public static void main(String[] args) {
        int[] scores1 = {5, 1, 4, 2, 8};
        int[] stats1 = bubbleStats(scores1);
        System.out.println(Arrays.toString(scores1) + " passes = " + stats1[0] + ", swaps = " + stats1[1]);
        // Output: [1, 2, 4, 5, 8] passes = 3, swaps = 4

        int[] scores2 = {1, 2, 3, 4};
        int[] stats2 = bubbleStats(scores2);
        System.out.println(Arrays.toString(scores2) + " passes = " + stats2[0] + ", swaps = " + stats2[1]);
        // Output: [1, 2, 3, 4] passes = 1, swaps = 0
    }
}