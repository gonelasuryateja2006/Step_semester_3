public class MaximumContainerArea {

    public static long maxContainerArea(int[] heights) {
        int left = 0;
        int right = heights.length - 1;
        long maxArea = 0;

        while (left < right) {
            int shorterHeight = Math.min(heights[left], heights[right]);
            int width = right - left;

            long area = (long) shorterHeight * width;
            maxArea = Math.max(maxArea, area);

            // Move the pointer at the shorter boundary.
            if (heights[left] <= heights[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        int[] heights = {1, 8, 6, 2, 5, 4, 8, 3, 7};

        System.out.println(maxContainerArea(heights));
    }
}