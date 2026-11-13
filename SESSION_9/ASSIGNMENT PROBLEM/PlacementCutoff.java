public class PlacementCutoff {

    public static int kthHighest(int[] marks, int k) {
        if (marks == null || k < 1 || k > marks.length) {
            throw new IllegalArgumentException("Invalid input or k out of bounds");
        }

        int n = marks.length;
        // The k-th highest element sits at index (n - k) if sorted in ascending order
        int targetIndex = n - k;
        int left = 0;
        int right = n - 1;

        while (left <= right) {
            int pivotIndex = partition(marks, left, right);

            if (pivotIndex == targetIndex) {
                return marks[pivotIndex];
            } else if (pivotIndex < targetIndex) {
                // Target is in the right subarray
                left = pivotIndex + 1;
            } else {
                // Target is in the left subarray
                right = pivotIndex - 1;
            }
        }

        return -1; // Unreachable for valid inputs
    }

    // Lomuto partition scheme using the last element as pivot
    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, right);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] marks1 = {72, 95, 60, 88, 79};
        System.out.println("Output 1: " + kthHighest(marks1, 2)); // Prints: 88

        int[] marks2 = {50, 90, 90, 70};
        System.out.println("Output 2: " + kthHighest(marks2, 2)); // Prints: 90
    }
}