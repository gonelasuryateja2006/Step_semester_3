class AttendanceGymMember {
    protected String memberId;
    protected int monthlyFee;
    private int sessionsAttended;

    public AttendanceGymMember(String memberId,
                               int monthlyFee) {

        this.memberId = memberId;
        this.monthlyFee = monthlyFee;
        this.sessionsAttended = 0;
    }

    public void attendSession() {
        sessionsAttended++;
    }

    public int getSessionsAttended() {
        return sessionsAttended;
    }

    public String displayInfo() {
        return "Standard | Sessions: "
                + getSessionsAttended();
    }
}

class AttendancePremiumMember extends AttendanceGymMember {
    private String trainerName;

    public AttendancePremiumMember(String memberId,
                                   int monthlyFee,
                                   String trainerName) {

        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }

    public String getTrainerName() {
        return trainerName;
    }

    @Override
    public String displayInfo() {
        return "Premium | Trainer: "
                + trainerName
                + " | Sessions: "
                + getSessionsAttended();
    }
}

public class MonthlyAttendanceAnnouncer {

    public static String batchPrint(
            AttendanceGymMember[] members) {

        StringBuilder result =
                new StringBuilder();

        for (AttendanceGymMember member : members) {

            // Polymorphism
            result.append(
                    member.displayInfo()
            );

            // Safe downcasting
            if (member instanceof AttendancePremiumMember) {

                AttendancePremiumMember premium =
                        (AttendancePremiumMember) member;

                result.append(
                        " [Trainer via downcast: "
                );

                result.append(
                        premium.getTrainerName()
                );

                result.append("]");
            }

            result.append(" | ");
        }

        return result.toString();
    }

    public static void main(String[] args) {

        AttendanceGymMember[] members = {

                new AttendanceGymMember(
                        "MEM6",
                        1000
                ),

                new AttendancePremiumMember(
                        "MEM7",
                        2000,
                        "Coach Riya"
                )
        };

        System.out.println(
                batchPrint(members)
        );
    }
}