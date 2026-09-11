import java.util.*;
public class WordFrequency {
    static void printFilteredWordFrequency(String feedback) {
        // Convert to lowercase
        String cleaned = feedback.toLowerCase();
        // Remove punctuation
        cleaned = cleaned.replace(".", "");
        cleaned = cleaned.replace(",", "");
        // Split into words
        String[] words = cleaned.split("\\s+");
        // Stop words
        Set<String> stopWords = new HashSet<>(
                Arrays.asList("the", "was", "and", "a", "is", "of", "in")
        );
        // Count frequencies
        HashMap<String, Integer> frequency = new HashMap<>();
        for (String word : words) {
            if (stopWords.contains(word)) {
                continue;
            }
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }
        // Sort by frequency in descending order
        List<Map.Entry<String, Integer>> list =
                new ArrayList<>(frequency.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        // Print result
        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(
                    entry.getKey() + ": " + entry.getValue()
            );
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter feedback paragraph:");
        String feedback = sc.nextLine();
        printFilteredWordFrequency(feedback);
        sc.close();
    }
}
