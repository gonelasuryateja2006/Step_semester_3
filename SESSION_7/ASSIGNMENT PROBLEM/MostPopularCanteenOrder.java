import java.util.HashMap;

public class MostPopularCanteenOrder {

    public static class Result {
        public String item;
        public int count;

        public Result(String item, int count) {
            this.item = item;
            this.count = count;
        }

        @Override
        public String toString() {
            return "(\"" + item + "\", " + count + ")";
        }
    }

    public static Result mostPopular(String[] orders) {
        HashMap<String, Integer> counts = new HashMap<>();

        // Pass 1: Count occurrences of each item
        for (String item : orders) {
            counts.put(item, counts.getOrDefault(item, 0) + 1);
        }

        int maxCount = 0;
        String mostPopularItem = "";

        // Pass 2: Iterate over the original order list to break ties using first appearance
        for (String item : orders) {
            int currentCount = counts.get(item);
            if (currentCount > maxCount) {
                maxCount = currentCount;
                mostPopularItem = item;
            }
        }

        return new Result(mostPopularItem, maxCount);
    }

    public static void main(String[] args) {
        String[] orders1 = {"dosa", "idli", "vada", "dosa", "idli", "dosa", "tea"};
        System.out.println(mostPopular(orders1)); // Output: ("dosa", 3)

        String[] orders2 = {"tea", "coffee", "coffee", "tea"};
        System.out.println(mostPopular(orders2)); // Output: ("tea", 2)
    }
}