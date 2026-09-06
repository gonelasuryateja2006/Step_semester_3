class LibraryMember {

    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswer;

    private boolean membershipIdSet = false;

    // Public no-argument constructor
    public LibraryMember() {
    }

    // Membership ID
    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String id) {

        if (!membershipIdSet) {
            membershipId = id;
            membershipIdSet = true;
        }
    }

    // Name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Premium Member
    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    // Security Answer - write only
    public void setSecurityAnswer(String answer) {

        if (answer != null) {
            securityAnswer = Integer.toHexString(answer.hashCode());
        }
    }
}

public class Problem4 {

    public static void main(String[] args) {

        LibraryMember m = new LibraryMember();

        m.setMembershipId("LIB-8841");
        m.setName("Priya Nair");
        m.setPremiumMember(true);

        System.out.println(m.getMembershipId());

        m.setMembershipId("FAKE-0000");

        System.out.println(m.getMembershipId());

        System.out.println(m.isPremiumMember());

        m.setSecurityAnswer("BlueMountain");
    }
}