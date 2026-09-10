public class ExamHall{
    static void checkDuplicateSeats(int[] seatNumbers) {
        boolean duplicateFound = false;
        for (int i = 0; i < seatNumbers.length; i++) {
            for (int j = i + 1; j < seatNumbers.length; j++) {
                if (seatNumbers[i] == seatNumbers[j]) {
                    // Print only the first occurrence of the duplicate
                    boolean alreadyPrinted = false;
                    for (int k = 0; k < i; k++) {
                        if (seatNumbers[k] == seatNumbers[i]) {
                            alreadyPrinted = true;
                            break;
                        }
                    }
                    if (!alreadyPrinted) {
                        System.out.println(
                                "Duplicate Seat Number Found: " + seatNumbers[i]
                        );
                        duplicateFound = true;
                    }
                }
            }
        }
        if (!duplicateFound) {
            System.out.println("No Duplicate Seats Found");
        }
    }
    public static void main(String[] args) {
        int[] seatNumbers = {101, 102, 103, 102, 105};
        checkDuplicateSeats(seatNumbers);
    }
}