public class Membership {

    static class LibraryMember {

        private static int membersEnrolled = 0;

        private final String memberNumber;

        private int borrowLimit;
        private int booksBorrowed;

        public LibraryMember(int borrowLimit) {

            if (borrowLimit <= 0) {
                throw new IllegalArgumentException(
                        "Borrow limit must be positive"
                );
            }

            membersEnrolled++;

            memberNumber =
                    "LIB-" + (100 + membersEnrolled);

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

        public void borrowBook(String genre) {

            System.out.println(
                    "Genre recorded: " + genre
            );

            borrowBook();
        }

        public int getBooksBorrowed() {
            return booksBorrowed;
        }

        public String getMemberNumber() {
            return memberNumber;
        }

        public static boolean isValidRenewalCode(
                String code) {

            if (code == null ||
                    code.length() != 4) {

                return false;
            }

            if (code.charAt(0) != 'R') {
                return false;
            }

            if (!Character.isDigit(
                    code.charAt(1))) {

                return false;
            }

            if (!Character.isDigit(
                    code.charAt(2))) {

                return false;
            }

            if (!Character.isUpperCase(
                    code.charAt(3))) {

                return false;
            }

            return true;
        }

        public static int getMembersEnrolled() {
            return membersEnrolled;
        }
    }

    static class FacultyMember extends LibraryMember {

        private String department;

        public FacultyMember(
                int borrowLimit,
                String department) {

            super(borrowLimit);
            this.department = department;
        }

        public String getDepartment() {
            return department;
        }
    }

    public static String processNightlyAudit(
            LibraryMember[] members) {

        int processed = 0;
        int nullSkipped = 0;
        int facultyCount = 0;
        int regularCount = 0;

        for (LibraryMember member : members) {

            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (member instanceof FacultyMember) {
                facultyCount++;
            } else {
                regularCount++;
            }
        }

        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + facultyCount
                + " faculty | "
                + regularCount
                + " regular";
    }

    public static void main(String[] args) {

        LibraryMember m1 =
                new LibraryMember(3);

        System.out.println(
                m1.getMemberNumber()
        );

        System.out.println(
                LibraryMember.getMembersEnrolled()
        );

        System.out.println(
                LibraryMember.isValidRenewalCode(
                        "R12A"
                )
        );

        System.out.println(
                LibraryMember.isValidRenewalCode(
                        "R1A"
                )
        );

        System.out.println(
                LibraryMember.isValidRenewalCode(
                        "X12A"
                )
        );

        m1.borrowBook();
        m1.borrowBook("Fiction");

        System.out.println(
                m1.getBooksBorrowed()
        );

        LibraryMember[] members = {
                new FacultyMember(5, "Physics"),
                null,
                new LibraryMember(3)
        };

        System.out.println(
                processNightlyAudit(members)
        );
    }
}