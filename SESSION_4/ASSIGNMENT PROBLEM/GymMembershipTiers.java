class TiersGymMember {
    protected String memberId;
    protected int monthlyFee;
    private int sessionsAttended;

    public TiersGymMember(String memberId, int monthlyFee) {
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
        return "Standard Member | Sessions: "
                + getSessionsAttended();
    }
}

class TiersPremiumMember extends TiersGymMember {
    protected String trainerName;

    public TiersPremiumMember(String memberId,
                              int monthlyFee,
                              String trainerName) {

        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }

    @Override
    public String displayInfo() {
        return "Premium Member | Trainer: "
                + trainerName
                + " | Sessions: "
                + getSessionsAttended();
    }
}

class TiersEliteMember extends TiersPremiumMember {
    private String lockerNumber;

    public TiersEliteMember(String memberId,
                            int monthlyFee,
                            String trainerName,
                            String lockerNumber) {

        super(memberId, monthlyFee, trainerName);
        this.lockerNumber = lockerNumber;
    }

    @Override
    public String displayInfo() {
        return "Elite Member | Trainer: "
                + trainerName
                + " | Locker: "
                + lockerNumber
                + " | Sessions: "
                + getSessionsAttended();
    }
}

class TiersGroupClassMember extends TiersGymMember {
    private String className;

    public TiersGroupClassMember(String memberId,
                                 int monthlyFee,
                                 String className) {

        super(memberId, monthlyFee);
        this.className = className;
    }

    @Override
    public String displayInfo() {
        return "Group Class Member | Class: "
                + className
                + " | Sessions: "
                + getSessionsAttended();
    }
}

public class GymMembershipTiers {

    public static String classifyGeneration(TiersGymMember member) {

        if (member instanceof TiersEliteMember) {
            return "Multilevel descendant (3 generations deep)";
        }

        if (member instanceof TiersGroupClassMember) {
            return "Hierarchical sibling (independent branch)";
        }

        if (member instanceof TiersPremiumMember) {
            return "Premium Member";
        }

        return "Standard Member";
    }

    public static int getTotalSessionsAttended(
            TiersGymMember[] members) {

        int total = 0;

        for (TiersGymMember member : members) {
            total += member.getSessionsAttended();
        }

        return total;
    }

    public static void main(String[] args) {

        TiersGymMember standard =
                new TiersGymMember("MEM1", 1000);

        TiersPremiumMember premium =
                new TiersPremiumMember(
                        "MEM2",
                        2000,
                        "Coach Riya"
                );

        TiersEliteMember elite =
                new TiersEliteMember(
                        "MEM3",
                        3000,
                        "Coach Arjun",
                        "L12"
                );

        TiersGroupClassMember group =
                new TiersGroupClassMember(
                        "MEM4",
                        1500,
                        "Zumba"
                );

        System.out.println(standard.displayInfo());
        System.out.println(premium.displayInfo());
        System.out.println(elite.displayInfo());
        System.out.println(group.displayInfo());

        System.out.println(classifyGeneration(elite));
        System.out.println(classifyGeneration(group));

        // Premium = 3 sessions
        premium.attendSession();
        premium.attendSession();
        premium.attendSession();

        // Elite = 2 sessions
        elite.attendSession();
        elite.attendSession();

        // Group = 4 sessions
        group.attendSession();
        group.attendSession();
        group.attendSession();
        group.attendSession();

        TiersGymMember[] members = {
                premium,
                elite,
                group
        };

        System.out.println(
                getTotalSessionsAttended(members)
        );
    }
}