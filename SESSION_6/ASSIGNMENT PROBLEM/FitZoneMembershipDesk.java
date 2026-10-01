import java.util.Locale;

public class FitZoneMembershipDesk {

    enum Status {
        ACTIVE, FROZEN, EXPIRED
    }

    interface MembershipPlan {
        double BASE_RATE = 1000;

        String getName();
        int getMonths();
        double calculateFee();
    }

    static class MonthlyPlan implements MembershipPlan {
        public String getName() { return "Monthly"; }
        public int getMonths() { return 1; }

        public double calculateFee() {
            return BASE_RATE * getMonths();
        }
    }

    static class QuarterlyPlan implements MembershipPlan {
        public String getName() { return "Quarterly"; }
        public int getMonths() { return 3; }

        public double calculateFee() {
            return BASE_RATE * getMonths() * 0.90;
        }
    }

    static class AnnualPlan implements MembershipPlan {
        public String getName() { return "Annual"; }
        public int getMonths() { return 12; }

        public double calculateFee() {
            return BASE_RATE * getMonths() * 0.75;
        }
    }

    static class Member {
        private final String name;

        Member(String name) {
            this.name = name;
        }

        public Membership buyMembership(MembershipPlan plan) {
            return new Membership(this, plan);
        }
    }

    static class Membership {
        private final Member member;
        private final MembershipPlan plan;
        private final double fee;
        private Status status;

        private Membership(Member member, MembershipPlan plan) {
            this.member = member;
            this.plan = plan;
            fee = plan.calculateFee();
            status = Status.ACTIVE;

            System.out.printf(
                    Locale.US,
                    "%s membership created for %s. "
                            + "Fee: ₹%,.2f. Status: Active.%n",
                    plan.getName(), member.name, fee);
        }

        private String statusText() {
            switch (status) {
                case ACTIVE:
                    return "Active";
                case FROZEN:
                    return "Frozen";
                default:
                    return "Expired";
            }
        }

        public void checkIn() {
            if (status != Status.ACTIVE) {
                System.out.println("Check-in denied: " + member.name
                        + "'s membership is " + statusText() + ".");
                return;
            }

            System.out.println(member.name + " checked in successfully.");
        }

        public void freeze() {
            if (status != Status.ACTIVE) {
                System.out.println("Cannot freeze "
                        + (status == Status.EXPIRED ? "an " : "a ")
                        + statusText() + " membership.");
                return;
            }

            status = Status.FROZEN;
            System.out.println(member.name
                    + "'s membership frozen. Status: Frozen.");
        }

        public void unfreeze() {
            if (status != Status.FROZEN) {
                System.out.println("Cannot unfreeze "
                        + (status == Status.EXPIRED
                        || status == Status.ACTIVE ? "an " : "a ")
                        + statusText() + " membership.");
                return;
            }

            status = Status.ACTIVE;
            System.out.println(member.name
                    + "'s membership unfrozen. Status: Active.");
        }

        public void expire() {
            if (status == Status.EXPIRED) {
                System.out.println("Membership is already Expired.");
                return;
            }

            status = Status.EXPIRED;
            System.out.println(member.name
                    + "'s membership expired. Status: Expired.");
        }
    }

    public static void main(String[] args) {
        Member asha = new Member("Asha");
        Member ravi = new Member("Ravi");

        Membership ashaMembership =
                asha.buyMembership(new QuarterlyPlan());

        Membership raviMembership =
                ravi.buyMembership(new MonthlyPlan());

        ashaMembership.checkIn();
        ashaMembership.freeze();
        ashaMembership.checkIn();

        raviMembership.expire();
        raviMembership.freeze();
    }
}