import java.util.Arrays;

public class ContestLeaderboard1{

    // Class representing a student's contest entry
    static class Entry {
        String name;
        int marks;

        Entry(String name, int marks) {
            this.name = name;
            this.marks = marks;
        }

        @Override
        public String toString() {
            return name + " " + marks;
        }
    }

    public static Entry[] leaderboard(Entry[] entries) {
        if (entries == null || entries.length == 0) {
            return new Entry[0];
        }

        int n = entries.length;
        // Marks range from 0 to 100 (101 possible keys)
        int[] count = new int[101];

        // Step 1: Count frequencies using key = 100 - marks
        // Higher marks map to smaller keys so they appear first in the output
        for (int i = 0; i < n; i++) {
            int key = 100 - entries[i].marks;
            count[key]++;
        }

        // Step 2: Compute cumulative counts to determine target positions
        for (int i = 1; i < 101; i++) {
            count[i] += count[i - 1];
        }

        // Step 3: Walk input from right to left to place entries stably
        Entry[] output = new Entry[n];
        for (int i = n - 1; i >= 0; i--) {
            int key = 100 - entries[i].marks;
            int position = count[key] - 1;
            output[position] = entries[i];
            count[key]--;
        }

        return output;
    }

    public static void main(String[] args) {
        Entry[] sampleInput = {
                new Entry("Asha", 90),
                new Entry("Ravi", 75),
                new Entry("Neha", 90),
                new Entry("Kiran", 100),
                new Entry("Meera", 75)
        };

        Entry[] sortedLeaderboard = leaderboard(sampleInput);

        // Prints: [Kiran 100, Asha 90, Neha 90, Ravi 75, Meera 75]
        System.out.println(Arrays.toString(sortedLeaderboard));
    }
}