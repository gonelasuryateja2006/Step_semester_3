class FeeAccount {
    String accountType;
    FeeAccount(String accountType) {
        this.accountType = accountType;
    }
}
class HostelFeeAccount extends FeeAccount {
    HostelFeeAccount() {
        super("Hostel");
    }
}
public class M5_AccountPayment {
    static int hostelCount = 0;
    static int dayScholarCount = 0;
    static void processPayment(
        FeeAccount account,
        double amount
    ) {
        if (account instanceof HostelFeeAccount) {
            System.out.println(
                "Paid in two installments (hostel account)"
            );
            hostelCount++;

        } else {
            System.out.println(
                "Paid in one go (day-scholar account)"
            );
            dayScholarCount++;
        }
    }
    public static void main(String[] args) {
        FeeAccount[] accounts = {
            new HostelFeeAccount(),
            new HostelFeeAccount(),
            new FeeAccount("Day Scholar"),
            new FeeAccount("Day Scholar")
        };
        double amount = 60000;
        // Process entire batch
        for (FeeAccount account : accounts) {
            processPayment(account, amount);
        }
        // Print counters once
        System.out.println(
            "Hostel accounts processed: " + hostelCount +
            " | Day-scholar accounts processed: " +
            dayScholarCount
        );
    }
}