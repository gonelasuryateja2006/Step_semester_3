public class HostelLaundryQueue {

    interface WashType {
        String getName();
        int getDuration();
        double getCharge();
    }

    static class QuickWash implements WashType {
        public String getName() { return "Quick"; }
        public int getDuration() { return 30; }
        public double getCharge() { return 20; }
    }

    static class NormalWash implements WashType {
        public String getName() { return "Normal"; }
        public int getDuration() { return 45; }
        public double getCharge() { return 30; }
    }

    static class HeavyWash implements WashType {
        public String getName() { return "Heavy"; }
        public int getDuration() { return 60; }
        public double getCharge() { return 45; }
    }

    static class Student {
        private final String name;

        Student(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    static class WashCycle {
        private final Student student;
        private final WashingMachine machine;
        private final WashType washType;
        private boolean completed;

        private WashCycle(Student student, WashingMachine machine,
                          WashType washType) {
            this.student = student;
            this.machine = machine;
            this.washType = washType;
        }

        public double calculateCharge() {
            return washType.getCharge();
        }

        public boolean isCompleted() {
            return completed;
        }
    }

    static class WashingMachine {
        private final String id;
        private WashCycle currentCycle;

        WashingMachine(String id) {
            this.id = id;
        }

        public boolean isAvailable() {
            return currentCycle == null;
        }

        public void startWash(Student student, WashType washType) {
            if (!isAvailable()) {
                System.out.println("Machine " + id + " is currently busy.");
                return;
            }

            if (student == null || washType == null) {
                throw new IllegalArgumentException(
                        "Student and wash type are required.");
            }

            currentCycle = new WashCycle(student, this, washType);

            System.out.printf(
                    "%s wash started on %s for %s (%d min). "
                            + "Charge: ₹%.2f.%n",
                    washType.getName(), id, student.getName(),
                    washType.getDuration(), currentCycle.calculateCharge());
        }

        public void completeWash() {
            if (isAvailable()) {
                System.out.println("Machine " + id + " has no active cycle.");
                return;
            }

            currentCycle.completed = true;
            currentCycle = null;

            System.out.println(id + " cycle completed. "
                    + id + " is now free.");
        }
    }

    public static void main(String[] args) {
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");

        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        m1.startWash(asha, new QuickWash());
        m1.startWash(ravi, new HeavyWash());
        m2.startWash(ravi, new HeavyWash());

        m1.completeWash();

        m1.startWash(neha, new NormalWash());
    }
}