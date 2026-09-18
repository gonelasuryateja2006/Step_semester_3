class CheckInGymMember {

    private static int counter = 2000;

    public final String membershipNumber;

    protected int monthlyFee;

    private int feesPaid = 0;
    private String paymentMode;

    public CheckInGymMember(int monthlyFee) {

        counter++;

        membershipNumber =
                "GYM-" + counter;

        this.monthlyFee = monthlyFee;
    }

    // First overloaded method
    public void payFee(int amount) {
        feesPaid += amount;
    }

    // Second overloaded method
    public void payFee(int amount,
                       String mode) {

        paymentMode = mode;

        // Reuse first method
        payFee(amount);
    }

    public int getFeesPaid() {
        return feesPaid;
    }

    public static int getMembersEnrolled() {
        return counter - 2000;
    }

    public static boolean isValidReferralCode(
            String code) {

        if (code == null ||
                code.length() != 4) {

            return false;
        }

        if (code.charAt(0) != 'G') {
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
}

class CheckInGroupClassMember
        extends CheckInGymMember {

    private String className;

    public CheckInGroupClassMember(
            int monthlyFee,
            String className) {

        super(monthlyFee);
        this.className = className;
    }
}

public class WeeklyCheckInSettlement {

    public static String processWeeklyCheckIn(
            CheckInGymMember[] members) {

        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (CheckInGymMember member : members) {

            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (member instanceof CheckInGroupClassMember) {
                group++;
            } else {
                individual++;
            }
        }

        return processed
                + " processed | "
                + nullSkipped
                + " null skipped | "
                + group
                + " group | "
                + individual
                + " individual";
    }

    public static void main(String[] args) {

        CheckInGymMember m1 =
                new CheckInGymMember(1000);

        System.out.println(
                m1.membershipNumber
        );

        System.out.println(
                CheckInGymMember.getMembersEnrolled()
        );

        // Referral code validation
        System.out.println(
                CheckInGymMember
                        .isValidReferralCode("G45B")
        );

        System.out.println(
                CheckInGymMember
                        .isValidReferralCode("G4B")
        );

        System.out.println(
                CheckInGymMember
                        .isValidReferralCode("X45B")
        );

        // Method overloading
        m1.payFee(500);

        m1.payFee(
                500,
                "UPI"
        );

        System.out.println(
                m1.getFeesPaid()
        );

        CheckInGymMember[] members = {

                new CheckInGroupClassMember(
                        1500,
                        "Zumba"
                ),

                null,

                new CheckInGymMember(
                        1000
                )
        };

        System.out.println(
                processWeeklyCheckIn(members)
        );
    }
}