public class WarehouseGridSummary {

    static class Summary {
        long totalItems;
        int row;
        int col;

        Summary(long totalItems, int row, int col) {
            this.totalItems = totalItems;
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return "(" + totalItems + ", (" + row + ", " + col + "))";
        }
    }

    public static Summary warehouseSummary(int[][] grid) {
        long total = 0;
        int maximum = -1;
        int maxRow = -1;
        int maxCol = -1;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                int items = grid[row][col];
                total += items;

                // Strictly greater preserves the first maximum.
                if (items > maximum) {
                    maximum = items;
                    maxRow = row;
                    maxCol = col;
                }
            }
        }

        if (maxRow == -1) {
            throw new IllegalArgumentException(
                    "The grid must contain at least one bin."
            );
        }

        return new Summary(total, maxRow, maxCol);
    }

    public static void main(String[] args) {
        int[][] grid = {
                {4, 9, 2},
                {7, 1, 6},
                {3, 12, 5}
        };

        System.out.println(warehouseSummary(grid));
    }
}