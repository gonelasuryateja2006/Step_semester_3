import java.util.*;
public class CampusNoticeBroadcaster {
    interface NotificationChannel {
        void send(Student student, Notice notice);
    }
    static class EmailChannel implements NotificationChannel {
        @Override
        public void send(Student student, Notice notice) {
            System.out.println("[Email → " + student.name + "] "
                    + notice.title + ".");
        }
    }
    static class SmsChannel implements NotificationChannel {
        @Override
        public void send(Student student, Notice notice) {
            System.out.println("[SMS → " + student.name + "] "
                    + notice.title + ".");
        }
    }
    static class AppChannel implements NotificationChannel {
        @Override
        public void send(Student student, Notice notice) {
            System.out.println("[App → " + student.name + "] "
                    + notice.title + ".");
        }
    }
    static class Student {
        private final String name;
        private final String department;
        private final List<NotificationChannel> channels;
        Student(String name, String department,
                NotificationChannel... preferredChannels) {
            if (name == null || name.trim().isEmpty()
                    || department == null || department.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "Student name and department are required.");
            }
            if (preferredChannels == null
                    || preferredChannels.length == 0) {
                throw new IllegalArgumentException(
                        "At least one preferred channel is required.");
            }
            this.name = name.trim();
            this.department = department.trim().toUpperCase(Locale.ROOT);
            List<NotificationChannel> selected = new ArrayList<>();
            for (NotificationChannel channel : preferredChannels) {
                if (channel == null) {
                    throw new IllegalArgumentException(
                            "Preferred channels cannot be null.");
                }
                if (!selected.contains(channel)) {
                    selected.add(channel);
                }
            }
            channels = Collections.unmodifiableList(selected);
        }
    }
    static class Notice {
        private final String title;
        private final Set<String> targetDepartments;
        Notice(String title, String... departments) {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("A title is required.");
            }
            if (departments == null || departments.length == 0) {
                throw new IllegalArgumentException(
                        "At least one target department is required.");
            }
            Set<String> targets = new LinkedHashSet<>();
            for (String department : departments) {
                if (department == null || department.trim().isEmpty()) {
                    throw new IllegalArgumentException(
                            "Target departments cannot be blank.");
                }
                targets.add(department.trim().toUpperCase(Locale.ROOT));
            }
            this.title = title.trim();
            targetDepartments = Collections.unmodifiableSet(targets);
        }
    }
    static class NoticeBoard {
        private final Set<Student> students = new LinkedHashSet<>();
        private final List<Notice> notices = new ArrayList<>();
        public void registerStudent(Student student) {
            students.add(Objects.requireNonNull(student));
        }
        public void postNotice(String title, String... departments) {
            Notice notice;
            try {
                notice = new Notice(title, departments);
            } catch (IllegalArgumentException exception) {
                System.out.println(
                        "Cannot post notice: " + exception.getMessage());
                return;
            }
            notices.add(notice);
            System.out.println("Notice '" + notice.title + "' posted to "
                    + String.join(", ", notice.targetDepartments) + ".");
            for (Student student : students) {
                if (notice.targetDepartments.contains(student.department)) {
                    for (NotificationChannel channel : student.channels) {
                        channel.send(student, notice);
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        NoticeBoard board = new NoticeBoard();
        Student asha = new Student(
                "Asha", "CSE", new EmailChannel(), new AppChannel());
        Student ravi = new Student(
                "Ravi", "ECE", new SmsChannel());
        board.registerStudent(asha);
        board.registerStudent(ravi);
        board.postNotice("Lab Closed Tomorrow", "CSE");
        board.postNotice("Fee Deadline Extended", "CSE", "ECE");
        board.postNotice("Sports Day");
    }
}
