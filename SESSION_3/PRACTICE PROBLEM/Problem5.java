class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {

        this.memberId = memberId;

        // Defensive copy
        this.bookIds = bookIds.clone();
    }

    public String[] getBookIds() {

        // Defensive copy
        return bookIds.clone();
    }

    public LoanReceipt withCorrectedBookId(
            int index,
            String newId) {

        String[] newBookIds = bookIds.clone();

        if (index >= 0 && index < newBookIds.length) {
            newBookIds[index] = newId;
        }

        return new LoanReceipt(memberId, newBookIds);
    }
}


class ReferenceOnlyLoanReceipt extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(
            String memberId,
            String[] bookIds,
            String roomNumber) {

        super(memberId, bookIds);

        this.roomNumber = roomNumber;
    }
}


class CirculationLedger {

    private static String branchCode;

    // Static block
    static {
        branchCode = "BR-001";
    }

    public static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (LoanReceipt receipt : receipts) {

            // Handle null safely
            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            // Check ReferenceOnlyLoanReceipt
            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed +
                " processed | " +
                nullSkipped +
                " null skipped | " +
                referenceOnly +
                " reference-only | " +
                regular +
                " regular";
    }
}


public class Problem5 {

    public static void main(String[] args) {

        // Create receipt
        LoanReceipt r = new LoanReceipt(
                "LIB-8841",
                new String[]{"BK-100", "BK-101"}
        );

        // Test defensive copy
        String[] ids = r.getBookIds();

        ids[0] = "HACKED";

        System.out.println(r.getBookIds()[0]);

        // Correct book ID
        LoanReceipt corrected =
                r.withCorrectedBookId(1, "BK-102");

        System.out.println(
                r.getBookIds()[0] + " " +
                        r.getBookIds()[1]
        );

        System.out.println(
                corrected.getBookIds()[0] + " " +
                        corrected.getBookIds()[1]
        );

        // Nightly circulation
        LoanReceipt[] receipts = {

                new ReferenceOnlyLoanReceipt(
                        "LIB-001",
                        new String[]{"BK-200"},
                        "Reading Room 3"
                ),

                null,

                new LoanReceipt(
                        "LIB-002",
                        new String[]{"BK-201"}
                )
        };

        System.out.println(
                CirculationLedger.processNightlyCirculation(
                        receipts
                )
        );
    }
}