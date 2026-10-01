import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HotelBookingSystem {

    static class Customer {
        private final String name;

        Customer(String name) {
            this.name = name;
        }
    }

    static abstract class Room {
        private final String name;
        private final List<Reservation> reservations = new ArrayList<>();

        Room(String name) {
            this.name = name;
        }

        public abstract double calculatePrice(long nights);

        private void validateDates(LocalDate start, LocalDate end) {
            if (start == null || end == null || !end.isAfter(start)) {
                throw new IllegalArgumentException(
                        "Checkout must be after check-in.");
            }
        }

        public boolean isAvailable(LocalDate start, LocalDate end) {
            validateDates(start, end);

            for (Reservation reservation : reservations) {
                boolean overlaps =
                        start.isBefore(reservation.end)
                                && end.isAfter(reservation.start);

                if (!reservation.cancelled && overlaps) {
                    return false;
                }
            }

            return true;
        }

        public Reservation reserve(Customer customer,
                                   LocalDate start, LocalDate end) {
            if (!isAvailable(start, end)) {
                System.out.printf(
                        "%s is not available from %s to %s.%n",
                        name, start, end);
                return null;
            }

            Reservation reservation =
                    new Reservation(customer, this, start, end);

            reservations.add(reservation);

            System.out.printf(
                    "Reservation confirmed for %s, %s (%s to %s). "
                            + "Price: $%.2f.%n",
                    customer.name, name, start, end, reservation.price);

            return reservation;
        }
    }

    static class StandardRoom extends Room {
        StandardRoom(String number) {
            super("Standard Room " + number);
        }

        @Override
        public double calculatePrice(long nights) {
            return nights * 100.0;
        }
    }

    static class DeluxeRoom extends Room {
        DeluxeRoom(String number) {
            super("Deluxe Room " + number);
        }

        @Override
        public double calculatePrice(long nights) {
            return nights * 180.0;
        }
    }

    static class Suite extends Room {
        Suite(String number) {
            super("Suite " + number);
        }

        @Override
        public double calculatePrice(long nights) {
            return nights * 300.0;
        }
    }

    static class Reservation {
        private final Customer customer;
        private final Room room;
        private final LocalDate start;
        private final LocalDate end;
        private final LocalDateTime cancellationDeadline;
        private final double price;
        private boolean cancelled;

        private Reservation(Customer customer, Room room,
                            LocalDate start, LocalDate end) {
            this.customer = customer;
            this.room = room;
            this.start = start;
            this.end = end;

            cancellationDeadline = start.minusDays(1).atStartOfDay();

            long nights = ChronoUnit.DAYS.between(start, end);
            price = room.calculatePrice(nights);
        }

        public void cancel(LocalDateTime now) {
            if (cancelled) {
                System.out.println("Reservation is already cancelled.");
                return;
            }

            if (!now.isBefore(cancellationDeadline)) {
                System.out.println(
                        "Cannot cancel: cancellation deadline has passed.");
                return;
            }

            cancelled = true;

            System.out.printf(
                    "Reservation for %s, %s (%s to %s) "
                            + "cancelled successfully.%n",
                    customer.name, room.name, start, end);
        }
    }

    public static void main(String[] args) {
        Customer customerA = new Customer("Customer A");
        Customer customerB = new Customer("Customer B");
        Customer customerC = new Customer("Customer C");

        Room standard = new StandardRoom("101");
        Room deluxe = new DeluxeRoom("201");

        LocalDate start = LocalDate.of(2027, 1, 1);
        LocalDate end = LocalDate.of(2027, 1, 5);

        if (standard.isAvailable(start, end)) {
            System.out.printf("%s is available from %s to %s.%n",
                    standard.name, start, end);
        }

        Reservation reservation = standard.reserve(customerA, start, end);

        standard.reserve(
                customerB,
                LocalDate.of(2027, 1, 3),
                LocalDate.of(2027, 1, 7));

        if (reservation != null) {
            reservation.cancel(LocalDateTime.of(2026, 12, 30, 12, 0));
        }

        deluxe.reserve(
                customerC,
                LocalDate.of(2027, 2, 10),
                LocalDate.of(2027, 2, 12));
    }
}