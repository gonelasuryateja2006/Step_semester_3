import java.util.Arrays;

public class StudentDiscount{

    static class LibraryMember {
        private String memberId;
        private int borrowLimit;
        private int booksBorrowed;

        private int[] fineHistory;
        private int fineCount;

        public LibraryMember(
                String memberId,
                int borrowLimit) {

            if (memberId == null ||
                    memberId.trim().isEmpty() ||
                    memberId.length() < 4) {

                throw new IllegalArgumentException(
                        "Invalid member ID"
                );
            }

            if (borrowLimit <= 0) {
                throw new IllegalArgumentException(
                        "Borrow limit must be positive"
                );
            }

            this.memberId = memberId;
            this.borrowLimit = borrowLimit;
            this.booksBorrowed = 0;

            this.fineHistory = new int[10];
            this.fineCount = 0;
        }

        public void borrowBook() {
            if (booksBorrowed >= borrowLimit) {
                throw new IllegalStateException(
                        "Borrow limit reached"
                );
            }

            booksBorrowed++;
        }

        public int getBooksBorrowed() {
            return booksBorrowed;
        }

        protected void chargeFine(int amount) {

            if (fineCount >= fineHistory.length) {
                throw new IllegalStateException(
                        "Fine history is full"
                );
            }

            fineHistory[fineCount] = amount;
            fineCount++;
        }

        public int[] getFineHistory() {

            return Arrays.copyOf(
                    fineHistory,
                    fineCount
            );
        }

        public int getTotalFine() {

            int total = 0;

            for (int i = 0; i < fineCount; i++) {
                total += fineHistory[i];
            }

            return total;
        }
    }

    static class StudentMember extends LibraryMember {

        private String course;

        public StudentMember(
                String memberId,
                int borrowLimit,
                String course) {

            super(memberId, borrowLimit);
            this.course = course;
        }

        @Override
        protected void chargeFine(int amount) {

            super.chargeFine(amount / 2);
        }

        public String getCourse() {
            return course;
        }
    }

    public static void main(String[] args) {

        StudentMember student =
                new StudentMember(
                        "STU5",
                        3,
                        "CSE"
                );

        student.chargeFine(100);

        System.out.println(
                student.getTotalFine()
        );

        int[] history =
                student.getFineHistory();

        history[0] = 999;

        System.out.println(
                Arrays.toString(
                        student.getFineHistory()
                )
        );
    }
}