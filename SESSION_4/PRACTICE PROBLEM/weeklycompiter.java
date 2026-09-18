public class weeklycompiter{

    static class LibraryMember {

        private String memberId;
        private int borrowLimit;
        private int booksBorrowed;

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

        public String displayInfo() {

            return "General | Books: "
                    + booksBorrowed;
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

        public String getCourse() {
            return course;
        }

        @Override
        public String displayInfo() {

            return "Student | Course: "
                    + course
                    + " | Books: "
                    + getBooksBorrowed();
        }
    }

    public static String batchPrint(
            LibraryMember[] members) {

        StringBuilder report =
                new StringBuilder();

        for (LibraryMember member : members) {

            // Polymorphic method call
            report.append(
                    member.displayInfo()
            );

            // Safe downcasting
            if (member instanceof StudentMember) {

                StudentMember student =
                        (StudentMember) member;

                report.append(
                        " [Course via downcast: "
                                + student.getCourse()
                                + "]"
                );
            }

            report.append(" | ");
        }

        return report.toString();
    }

    public static void main(String[] args) {

        LibraryMember general =
                new LibraryMember(
                        "LB5",
                        3
                );

        StudentMember student =
                new StudentMember(
                        "STU6",
                        3,
                        "ECE"
                );

        System.out.println(
                batchPrint(
                        new LibraryMember[]{
                                general,
                                student
                        }
                )
        );

        // Example of an unsafe cast:
        //
        // LibraryMember plain =
        //         new LibraryMember("LB6", 3);
        //
        // StudentMember bad =
        //         (StudentMember) plain;
        //
        // This causes ClassCastException.
    }
}