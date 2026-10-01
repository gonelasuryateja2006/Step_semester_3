import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
public class AssignmentSubmissionPortal {
    enum Status {
        SUBMITTED, GRADED
    }
    static class Student {
        private final String name;

        Student(String name) {
            this.name = name;
        }
    }
    static abstract class Assignment {
        private final String title;
        private final double maxMarks;
        private final LocalDate dueDate;
        Assignment(String title, double maxMarks, LocalDate dueDate) {
            if (maxMarks <= 0 || !Double.isFinite(maxMarks)) {
                throw new IllegalArgumentException(
                        "Maximum marks must be positive and finite.");
            }
            this.title = title;
            this.maxMarks = maxMarks;
            this.dueDate = dueDate;
        }
        public abstract double penaltyRatePerDay();
        public double calculateFinalMarks(double awardedMarks, long lateDays) {
            double penalty = Math.min(1.0, lateDays * penaltyRatePerDay());
            return awardedMarks * (1 - penalty);
        }
    }
    static class CodingAssignment extends Assignment {
        CodingAssignment(String title, double maxMarks, LocalDate dueDate) {
            super(title, maxMarks, dueDate);
        }
        @Override
        public double penaltyRatePerDay() {
            return 0.10;
        }
    }
    static class WrittenAssignment extends Assignment {
        WrittenAssignment(String title, double maxMarks, LocalDate dueDate) {
            super(title, maxMarks, dueDate);
        }
        @Override
        public double penaltyRatePerDay() {
            return 0.20;
        }
    }
    static class Submission {
        private final Student student;
        private final Assignment assignment;
        private LocalDate submissionDate;
        private String work;
        private Status status;
        private double finalMarks;
        private Submission(Student student, Assignment assignment) {
            this.student = student;
            this.assignment = assignment;
        }
        private long getLateDays() {
            return Math.max(0,
                    ChronoUnit.DAYS.between(
                            assignment.dueDate, submissionDate));
        }
        private void submit(String work, LocalDate date) {
            if (status == Status.GRADED) {
                System.out.println("Cannot resubmit: '" + assignment.title
                        + "' has already been graded.");
                return;
            }
            if (work == null || work.trim().isEmpty() || date == null) {
                throw new IllegalArgumentException(
                        "Work and submission date are required.");
            }
            this.work = work;
            submissionDate = date;
            status = Status.SUBMITTED;
            long lateDays = getLateDays();
            String timing = lateDays == 0
                    ? "on time" : lateDays + " days late";
            System.out.printf(
                    "%s's submission for '%s' received (%s). "
                            + "Status: Submitted.%n",
                    student.name, assignment.title, timing);
        }
        public void grade(double awardedMarks) {
            if (status != Status.SUBMITTED) {
                System.out.println(
                        "Cannot grade: submission must have Submitted status.");
                return;
            }
            if (!Double.isFinite(awardedMarks)
                    || awardedMarks < 0
                    || awardedMarks > assignment.maxMarks) {
                System.out.println("Invalid marks. Enter marks between 0 and "
                        + assignment.maxMarks + ".");
                return;
            }
            long lateDays = getLateDays();
            finalMarks = assignment.calculateFinalMarks(
                    awardedMarks, lateDays);
            status = Status.GRADED;
            double penaltyPercent = Math.min(
                    100, lateDays * assignment.penaltyRatePerDay() * 100);
            System.out.printf("%s graded: %.0f/%.0f",
                    student.name, finalMarks, assignment.maxMarks);
            if (lateDays > 0) {
                System.out.printf(" after %.0f%% late penalty",
                        penaltyPercent);
            }
            System.out.println(". Status: Graded.");
        }
    }
    static class Portal {
        private final List<Submission> submissions = new ArrayList<>();
        public Submission submit(Student student, Assignment assignment,
                                 String work, LocalDate date) {
            // Reuse the same submission for this student and assignment.
            for (Submission submission : submissions) {
                if (submission.student == student
                        && submission.assignment == assignment) {
                    submission.submit(work, date);
                    return submission;
                }
            }
            Submission submission = new Submission(student, assignment);
            submission.submit(work, date);
            submissions.add(submission);
            return submission;
        }
    }
    public static void main(String[] args) {
        Portal portal = new Portal();
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Assignment coding = new CodingAssignment(
                "Linked List Lab", 50, LocalDate.of(2026, 3, 10));
        Assignment written = new WrittenAssignment(
                "Design Essay", 50, LocalDate.of(2026, 3, 12));
        Submission ashaSubmission = portal.submit(
                asha, coding, "LinkedList.java",
                LocalDate.of(2026, 3, 10));
        Submission raviSubmission = portal.submit(
                ravi, written, "Essay.pdf",
                LocalDate.of(2026, 3, 14));
        ashaSubmission.grade(45);
        raviSubmission.grade(40);
        portal.submit(asha, coding, "UpdatedLinkedList.java",
                LocalDate.of(2026, 3, 15));
    }
}
