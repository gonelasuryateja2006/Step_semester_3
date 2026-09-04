import java.util.Arrays;
public class BookingReceipt {
    private final String bookingId;
    private final String[] seatNumbers;
    public BookingReceipt(String bookingId, String[] seatNumbers) {
        this.bookingId = bookingId;
        // Defensive copy
        this.seatNumbers = Arrays.copyOf(
                seatNumbers,
                seatNumbers.length
        );
    }
    public String getBookingId() {
        return bookingId;
    }
    public String[] getSeatNumbers() {
        // Defensive copy
        return Arrays.copyOf(
                seatNumbers,
                seatNumbers.length
        );
    }
    public BookingReceipt withUpdatedSeat(
            int index,
            String newSeat) {
        if (index < 0 || index >= seatNumbers.length) {
            throw new IndexOutOfBoundsException();
        }
        String[] updatedSeats =
                Arrays.copyOf(
                        seatNumbers,
                        seatNumbers.length
                );
        updatedSeats[index] = newSeat;
        return new BookingReceipt(
                bookingId,
                updatedSeats
        );
    }
    public static String processNightlySettlement(
            BookingReceipt[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;
        for (BookingReceipt receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (receipt instanceof GroupBookingReceipt) {
                group++;
            } else {
                individual++;
            }
        }
        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + group + " group | "
                + individual + " individual";
    }
    public static void main(String[] args) {
        BookingReceipt b =
                new BookingReceipt(
                        "CH-1001",
                        new String[]{"A1", "A2"}
                );
        String[] seats = b.getSeatNumbers();
        seats[0] = "X";
        System.out.println(
                Arrays.toString(b.getSeatNumbers())
        );
        BookingReceipt updated =
                b.withUpdatedSeat(1, "A3");
        System.out.println(
                Arrays.toString(b.getSeatNumbers())
        );
        System.out.println(
                Arrays.toString(updated.getSeatNumbers())
        );
        BookingReceipt[] receipts = {
                new GroupBookingReceipt(
                        "CH-2002",
                        new String[]{"B1", "B2"},
                        2
                ),
                null,
                new BookingReceipt(
                        "CH-3003",
                        new String[]{"C1"}
                )
        };
        System.out.println(
                processNightlySettlement(receipts)
        );
    }
}
// Group Booking Receipt
class GroupBookingReceipt extends BookingReceipt {
    private final int groupSize;
    public GroupBookingReceipt(
            String bookingId,
            String[] seatNumbers,
            int groupSize) {
        super(bookingId, seatNumbers);
        this.groupSize = groupSize;
    }
    public int getGroupSize() {
        return groupSize;
    }
}