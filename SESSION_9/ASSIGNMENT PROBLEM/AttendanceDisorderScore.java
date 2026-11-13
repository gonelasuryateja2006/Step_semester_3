import java.util.Arrays;

public class AttendanceDisorderScore {

    public static long countInversions(int[] roll) {
        if (roll == null || roll.length <= 1) {
            return 0;
        }
        // Work on a copy if you don't want to mutate the caller's original array,
        // or sort directly in-place using a single auxiliary array.
        int[] temp = new int[roll.length];
        return mergeSortAndCount(roll, temp, 0, roll.length - 1);
    }

    private static long mergeSortAndCount(int[] arr, int[] temp, int left, int right) {
        long inversions = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;

            // Count inversions in the left half
            inversions += mergeSortAndCount(arr, temp, left, mid);

            // Count inversions in the right half
            inversions += mergeSortAndCount(arr, temp, mid + 1, right);

            // Count split inversions while merging the two sorted halves
            inversions += mergeAndCount(arr, temp, left, mid, right);
        }
        return inversions;
    }

    private static long mergeAndCount(int[] arr, int[] temp, int left, int mid, int right) {
        int i = left;      // Pointer for left subarray [left..mid]
        int j = mid + 1;   // Pointer for right subarray [mid+1..right]
        int k = left;      // Pointer for merged temp array
        long splitInversions = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                // arr[i] > arr[j]: right element is smaller than arr[i]
                // and all remaining elements from i to mid in the left half
                temp[k++] = arr[j++];
                splitInversions += (mid - i + 1);
            }
        }

        // Copy any remaining elements from the left half
        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        // Copy any remaining elements from the right half
        while (j <= right) {
            temp[k++] = arr[j++];
        }

        // Copy the sorted subarray back into the original array
        for (int idx = left; idx <= right; idx++) {
            arr[idx] = temp[idx];
        }

        return splitInversions;
    }

    public static void main(String[] args) {
        int[] roll1 = {2, 4, 1, 3, 5};
        System.out.println("Output 1: " + countInversions(roll1)); // Prints: 3

        int[] roll2 = {5, 4, 3, 2, 1};
        System.out.println("Output 2: " + countInversions(roll2)); // Prints: 10
    }
}