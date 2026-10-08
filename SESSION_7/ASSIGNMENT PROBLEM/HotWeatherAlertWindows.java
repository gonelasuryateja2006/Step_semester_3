public class HotWeatherAlertWindows {

    public static int countAlerts(int[] readings, int k, int threshold) {
        if (readings.length < k) {
            return 0;
        }

        // Use long to avoid potential integer overflow during summation
        long currentSum = 0;
        long targetSum = (long) k * threshold; // Avoid decimal division: average >= threshold <=> sum >= k * threshold

        // Compute sum for the first window of size k
        for (int i = 0; i < k; i++) {
            currentSum += readings[i];
        }

        int alertCount = 0;
        if (currentSum >= targetSum) {
            alertCount++;
        }

        // Slide the window across the array
        for (int i = k; i < readings.length; i++) {
            currentSum += readings[i] - readings[i - k]; // Add incoming, subtract outgoing element
            if (currentSum >= targetSum) {
                alertCount++;
            }
        }

        return alertCount;
    }

    public static void main(String[] args) {
        int[] readings = {2, 2, 2, 5, 5, 5, 8};
        int k = 3;
        int threshold = 4;
        System.out.println(countAlerts(readings, k, threshold)); // Output: 3
    }
}