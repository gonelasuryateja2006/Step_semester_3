public class Branches {

    static class LibraryMember {
        private String memberId;
        private int borrowLimit;
        private int booksBorrowed;

        public LibraryMember(String memberId, int borrowLimit) {
            if (memberId == null || memberId.trim().isEmpty()
                    || memberId.length() < 4) {
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
            return "General Member | Books Borrowed: "
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
            return "Student Member | Course: "
                    + course
                    + " | Books Borrowed: "
                    + getBooksBorrowed();
        }
    }

    static class HonorsStudentMember extends StudentMember {
        private int bonusLimit;

        public HonorsStudentMember(
                String memberId,
                int borrowLimit,
                String course,
                int bonusLimit) {

            super(memberId, borrowLimit, course);
            this.bonusLimit = bonusLimit;
        }

        @Override
        public String displayInfo() {
            return "Honors Student Member | Course: "
                    + getCourse()
                    + " | Bonus Limit: "
                    + bonusLimit
                    + " | Books Borrowed: "
                    + getBooksBorrowed();
        }
    }

    static class FacultyMember extends LibraryMember {
        private String department;

        public FacultyMember(
                String memberId,
                int borrowLimit,
                String department) {

            super(memberId, borrowLimit);
            this.department = department;
        }

        @Override
        public String displayInfo() {
            return "Faculty Member | Department: "
                    + department
                    + " | Books Borrowed: "
                    + getBooksBorrowed();
        }
    }

    public static String classifyGeneration(LibraryMember member) {

        if (member instanceof HonorsStudentMember) {
            return "Multilevel descendant (3 generations deep)";
        }

        if (member instanceof StudentMember) {
            return "Student branch (2 generations deep)";
        }

        if (member instanceof FacultyMember) {
            return "Hierarchical sibling (independent branch)";
        }

        return "General Member";
    }

    public static int getTotalBooksBorrowed(
            LibraryMember[] members) {

        int total = 0;

        for (LibraryMember member : members) {
            total += member.getBooksBorrowed();
        }

        return total;
    }

    public static void main(String[] args) {

        LibraryMember general =
                new LibraryMember("STU1", 3);

        StudentMember student =
                new StudentMember("STU2", 3, "CSE");

        HonorsStudentMember honors =
                new HonorsStudentMember(
                        "STU3", 3, "ECE", 2);

        FacultyMember faculty =
                new FacultyMember(
                        "STU4", 5, "Physics");

        System.out.println(general.displayInfo());
        System.out.println(student.displayInfo());
        System.out.println(honors.displayInfo());
        System.out.println(faculty.displayInfo());

        System.out.println(
                classifyGeneration(honors)
        );

        System.out.println(
                classifyGeneration(faculty)
        );

        student.borrowBook();
        student.borrowBook();

        honors.borrowBook();

        faculty.borrowBook();
        faculty.borrowBook();
        faculty.borrowBook();

        LibraryMember[] members = {
                student,
                honors,
                faculty
        };

        System.out.println(
                getTotalBooksBorrowed(members)
        );
    }
}