public class libraryMember{

    static class LibraryMember {
        private String memberId;
        private int borrowLimit;
        private int booksBorrowed;

        public LibraryMember(String memberId, int borrowLimit) {
            if (memberId == null || memberId.trim().isEmpty()
                    || memberId.length() < 4) {
                throw new IllegalArgumentException(
                        "Member ID must contain at least 4 characters."
                );
            }

            if (borrowLimit <= 0) {
                throw new IllegalArgumentException(
                        "Borrow limit must be positive."
                );
            }

            this.memberId = memberId;
            this.borrowLimit = borrowLimit;
            this.booksBorrowed = 0;
        }

        public void borrowBook() {
            if (booksBorrowed >= borrowLimit) {
                throw new IllegalStateException("Borrow limit reached.");
            }

            booksBorrowed++;
        }

        public int getBooksBorrowed() {
            return booksBorrowed;
        }

        public String displayInfo() {
            return "General Member | Books Borrowed: " + booksBorrowed;
        }
    }

    static class StudentMember extends LibraryMember {
        private String course;

        public StudentMember(String memberId, int borrowLimit, String course) {
            super(memberId, borrowLimit);
            this.course = course;
        }

        public String getCourse() {
            return course;
        }

        @Override
        public String displayInfo() {
            return "Student Member | Course: " + course
                    + " | Books Borrowed: " + getBooksBorrowed();
        }
    }

    public static String enrollBatch(String[] memberIds, int borrowLimit) {
        int enrolled = 0;
        int rejected = 0;

        for (String memberId : memberIds) {
            try {
                new LibraryMember(memberId, borrowLimit);
                enrolled++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Enrolled: " + enrolled + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {

        try {
            LibraryMember member =
                    new LibraryMember("LB1", 3);

            System.out.println(member.displayInfo());

        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected");
        }

        StudentMember student =
                new StudentMember("STU10", 3, "CSE");

        student.borrowBook();
        student.borrowBook();

        System.out.println(student.getBooksBorrowed());

        String[] ids = {
                "STU1",
                "LB1",
                "STU2",
                " ",
                "STU3"
        };

        System.out.println(
                enrollBatch(ids, 3)
        );
    }
}