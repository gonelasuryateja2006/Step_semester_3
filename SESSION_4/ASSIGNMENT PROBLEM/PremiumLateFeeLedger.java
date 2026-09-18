import java.util.Arrays;

class LedgerGymMember {
    protected String memberId;
    protected int monthlyFee;

    private int[] lateFeeHistory = new int[10];
    private int feeCount = 0;
    private int totalLateFees = 0;

    public LedgerGymMember(String memberId, int monthlyFee) {
        this.memberId = memberId;
        this.monthlyFee = monthlyFee;
    }

    protected void chargeLateFee(int amount) {

        if (feeCount < lateFeeHistory.length) {
            lateFeeHistory[feeCount] = amount;
            feeCount++;
        }

        totalLateFees += amount;
    }

    public int[] getLateFeeHistory() {
        return Arrays.copyOf(
                lateFeeHistory,
                feeCount
        );
    }

    public int getTotalLateFees() {
        return totalLateFees;
    }
}

class LedgerPremiumMember extends LedgerGymMember {
    private String trainerName;

    public LedgerPremiumMember(String memberId,
                               int monthlyFee,
                               String trainerName) {

        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }

    @Override
    protected void chargeLateFee(int amount) {
        super.chargeLateFee(amount / 2);
    }
}

public class PremiumLateFeeLedger {

    public static void main(String[] args) {

        LedgerPremiumMember p =
                new LedgerPremiumMember(
                        "MEM5",
                        2000,
                        "Coach Riya"
                );

        p.chargeLateFee(200);

        System.out.println(
                p.getTotalLateFees()
        );

        int[] history =
                p.getLateFeeHistory();

        System.out.println(
                Arrays.toString(history)
        );

        // Attempt to modify returned copy
        history[0] = 999;

        // Original remains unchanged
        System.out.println(
                Arrays.toString(
                        p.getLateFeeHistory()
                )
        );
    }
}