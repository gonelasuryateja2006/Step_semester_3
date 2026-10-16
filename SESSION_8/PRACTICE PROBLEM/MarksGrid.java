
import java.util.*;

public class MarksGrid {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<String> names = new ArrayList<>();
        ArrayList<int[]> marks = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();

            if (line.isEmpty() || line.equals("END")) {
                break;
            }

            int start = line.indexOf('[');
            int end = line.indexOf(']');

            String name = line.substring(0, start).trim();
            String values = line.substring(start + 1, end);

            String[] parts = values.split(",");
            int[] m = new int[3];

            for (int i = 0; i < 3; i++) {
                m[i] = Integer.parseInt(parts[i].trim());
            }

            names.add(name);
            marks.add(m);
        }

        int[] subjectTotal = new int[3];
        int highest = -1;
        String topper = "";

        System.out.print("Totals ");

        for (int i = 0; i < marks.size(); i++) {
            int total = 0;

            for (int j = 0; j < 3; j++) {
                total += marks.get(i)[j];
                subjectTotal[j] += marks.get(i)[j];
            }

            System.out.print(names.get(i) + " " + total);

            if (i < marks.size() - 1) {
                System.out.print(", ");
            }

            if (total > highest) {
                highest = total;
                topper = names.get(i);
            }
        }

        System.out.println();

        System.out.printf("averages ");
        for (int j = 0; j < 3; j++) {
            double average = marks.isEmpty() ? 0 :
                    (double) subjectTotal[j] / marks.size();

            System.out.printf("%.2f", average);

            if (j < 2) {
                System.out.print(", ");
            }
        }

        System.out.println();
        if (!marks.isEmpty()) {
            System.out.println("topper " + topper +
                    " (" + highest + ")");
        }

        sc.close();
    }
}
