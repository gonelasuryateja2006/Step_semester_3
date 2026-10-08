public class ClassTopperFinder {

    public static class Result {
        public int rowIndex;
        public int total;

        public Result(int rowIndex, int total) {
            this.rowIndex = rowIndex;
            this.total = total;
        }

        @Override
        public String toString() {
            return "(" + rowIndex + ", " + total + ")";
        }
    }

    public static Result findTopper(int[][] marks) {
        int bestRowIndex = 0;
        int maxTotal = -1;

        for (int i = 0; i < marks.length; i++) {
            int currentTotal = 0;
            for (int j = 0; j < marks[i].length; j++) {
                currentTotal += marks[i][j];
            }

            // Update answer only when total is strictly greater to keep the smallest row index on ties
            if (currentTotal > maxTotal) {
                maxTotal = currentTotal;
                bestRowIndex = i;
            }
        }

        return new Result(bestRowIndex, maxTotal);
    }

    public static void main(String[] args) {
        int[][] marks = {
                {78, 85, 90},
                {88, 92, 79},
                {65, 70, 95}
        };
        System.out.println(findTopper(marks)); // Output: (1, 259)
    }
}