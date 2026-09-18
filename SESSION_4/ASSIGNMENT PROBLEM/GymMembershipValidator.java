class ValidatorGymMember {
    protected String memberId;
    protected int monthlyFee;
    private int sessionsAttended;

    public ValidatorGymMember(String memberId, int monthlyFee) {
        if (memberId == null ||
                memberId.trim().isEmpty() ||
                memberId.trim().length() < 4) {

            throw new IllegalArgumentException("Invalid Member ID");
        }

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

    public static String signUpBatch(String[] memberIds, int monthlyFee) {
        int signedUp = 0;
        int rejected = 0;

        for (String id : memberIds) {
            try {
                new ValidatorGymMember(id, monthlyFee);
                signedUp++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Signed Up: " + signedUp +
                " | Rejected: " + rejected;
    }
}

class ValidatorPremiumMember extends ValidatorGymMember {
    private String trainerName;

    public ValidatorPremiumMember(String memberId,
                                  int monthlyFee,
                                  String trainerName) {

        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }
}

public class GymMembershipValidator {

    public static void main(String[] args) {

        // Test invalid ID
        try {
            new ValidatorGymMember("GM1", 1000);
        } catch (IllegalArgumentException e) {
            System.out.println("Construction rejected");
        }

        // Premium Member
        ValidatorPremiumMember p =
                new ValidatorPremiumMember(
                        "MEM01",
                        2000,
                        "Coach Riya"
                );

        p.attendSession();
        p.attendSession();

        System.out.println(p.getSessionsAttended());

        // Batch signup
        String[] memberIds = {
                "MEM1",
                "GM1",
                "MEM2",
                " ",
                "MEM3"
        };

        System.out.println(
                ValidatorGymMember.signUpBatch(
                        memberIds,
                        1000
                )
        );
    }
}