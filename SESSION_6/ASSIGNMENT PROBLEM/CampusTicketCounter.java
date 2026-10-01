import java.time.LocalDateTime;
import java.util.*;

public class CampusTicketCounter {

    static class Customer {
        private final String name;

        Customer(String name) {
            this.name = name;
        }
    }

    static abstract class Seat {
        private final String number;

        Seat(String number) {
            this.number = number;
        }

        public String getNumber() {
            return number;
        }

        public abstract double getPrice();
    }

    static class RegularSeat extends Seat {
        RegularSeat(String number) {
            super(number);
        }

        public double getPrice() {
            return 150;
        }
    }

    static class PremiumSeat extends Seat {
        PremiumSeat(String number) {
            super(number);
        }

        public double getPrice() {
            return 250;
        }
    }

    static class ReclinerSeat extends Seat {
        ReclinerSeat(String number) {
            super(number);
        }

        public double getPrice() {
            return 400;
        }
    }

    static class Booking {
        private final Customer customer;
        private final Show show;
        private final List<Seat> seats;
        private boolean cancelled;

        private Booking(Customer customer, Show show, List<Seat> seats) {
            this.customer = customer;
            this.show = show;
            this.seats = Collections.unmodifiableList(
                    new ArrayList<>(seats));
        }

        public double calculateTotal() {
            double total = 0;

            for (Seat seat : seats) {
                total += seat.getPrice();
            }

            return total;
        }

        private String seatNumbers() {
            List<String> numbers = new ArrayList<>();

            for (Seat seat : seats) {
                numbers.add(seat.getNumber());
            }

            return String.join(", ", numbers);
        }

        public void cancel(LocalDateTime now) {
            show.cancelBooking(this, now);
        }
    }

    static class Show {
        private final LocalDateTime startTime;
        private final Map<String, Seat> seats = new LinkedHashMap<>();
        private final Set<String> reservedSeats = new HashSet<>();

        Show(LocalDateTime startTime, List<Seat> seatList) {
            this.startTime = startTime;

            for (Seat seat : seatList) {
                if (seats.putIfAbsent(seat.getNumber(), seat) != null) {
                    throw new IllegalArgumentException(
                            "Duplicate seat: " + seat.getNumber());
                }
            }
        }

        public boolean isSeatAvailable(String number) {
            return seats.containsKey(number)
                    && !reservedSeats.contains(number);
        }

        public Booking book(Customer customer, LocalDateTime now,
                            String... requestedSeats) {
            if (!now.isBefore(startTime)) {
                System.out.println("Cannot book: the show has started.");
                return null;
            }

            if (requestedSeats.length < 1 || requestedSeats.length > 6) {
                System.out.println("A booking must contain 1 to 6 seats.");
                return null;
            }

            Set<String> uniqueSeats = new LinkedHashSet<>(
                    Arrays.asList(requestedSeats));

            if (uniqueSeats.size() != requestedSeats.length) {
                System.out.println(
                        "The same seat cannot appear twice in a booking.");
                return null;
            }

            List<Seat> selectedSeats = new ArrayList<>();

            // Validate every seat before reserving any seat.
            for (String number : uniqueSeats) {
                if (!seats.containsKey(number)) {
                    System.out.println("Seat " + number + " does not exist.");
                    return null;
                }

                if (!isSeatAvailable(number)) {
                    System.out.println("Seat " + number
                            + " is already booked for this show.");
                    return null;
                }

                selectedSeats.add(seats.get(number));
            }

            Booking booking = new Booking(customer, this, selectedSeats);
            reservedSeats.addAll(uniqueSeats);

            System.out.printf(
                    "Booking confirmed for %s: %s. Total: ₹%.2f.%n",
                    customer.name, booking.seatNumbers(),
                    booking.calculateTotal());

            return booking;
        }

        private void cancelBooking(Booking booking, LocalDateTime now) {
            if (booking.show != this) {
                System.out.println("Booking belongs to a different show.");
                return;
            }

            if (booking.cancelled) {
                System.out.println("Booking is already cancelled.");
                return;
            }

            if (!now.isBefore(startTime)) {
                System.out.println(
                        "Cannot cancel: the show has already started.");
                return;
            }

            for (Seat seat : booking.seats) {
                reservedSeats.remove(seat.getNumber());
            }

            booking.cancelled = true;

            System.out.println(booking.customer.name
                    + "'s booking cancelled. Seats "
                    + booking.seatNumbers() + " released.");
        }
    }

    public static void main(String[] args) {
        LocalDateTime showTime = LocalDateTime.of(2026, 10, 1, 19, 0);
        LocalDateTime bookingTime = showTime.minusHours(2);

        Show show = new Show(showTime, Arrays.asList(
                new RegularSeat("A1"),
                new RegularSeat("A2"),
                new PremiumSeat("F5"),
                new ReclinerSeat("R1")
        ));

        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");

        Booking ashaBooking = show.book(
                asha, bookingTime, "A1", "A2", "F5");

        show.book(ravi, bookingTime, "A2");
        show.book(ravi, bookingTime, "R1");

        if (ashaBooking != null) {
            ashaBooking.cancel(showTime.minusHours(1));
        }

        show.book(neha, showTime.minusMinutes(30), "A2");
    }
}