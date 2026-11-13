import java.util.Arrays;

public class HackathonRegistrationDesk {

    // Class representing a registered hackathon team
    static class Team {
        String name;
        int size;

        Team(String name, int size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public String toString() {
            return name + "(" + size + ")";
        }
    }

    public static void sortTeams(Team[] teams) {
        if (teams == null || teams.length <= 1) {
            return;
        }

        int n = teams.length;

        // Start from the second team and insert each into the sorted left portion
        for (int i = 1; i < n; i++) {
            Team key = teams[i];
            int j = i - 1;

            // Shift earlier teams to the right ONLY while their size is strictly greater
            // Using '>' instead of '>=' preserves the original registration order for ties
            while (j >= 0 && teams[j].size > key.size) {
                teams[j + 1] = teams[j];
                j--;
            }

            // Drop the key team into the open gap
            teams[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        Team[] sampleInput = {
                new Team("Pixel", 4),
                new Team("Byte", 2),
                new Team("Nova", 4),
                new Team("Echo", 3),
                new Team("Atom", 2)
        };

        sortTeams(sampleInput);

        // Prints: [Byte(2), Atom(2), Echo(3), Pixel(4), Nova(4)]
        System.out.println(Arrays.toString(sampleInput));
    }
}