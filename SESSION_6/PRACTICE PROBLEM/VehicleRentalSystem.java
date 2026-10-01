public class VehicleRentalSystem {

    static class Customer {
        private final String name;

        Customer(String name) {
            this.name = name;
        }
    }

    static abstract class Vehicle {
        private final String name;
        private Rental activeRental;

        Vehicle(String name) {
            this.name = name;
        }

        public abstract double calculateCharge(int days);

        public boolean isAvailable() {
            return activeRental == null;
        }

        public Rental rent(Customer customer, int days) {
            if (customer == null || days <= 0) {
                throw new IllegalArgumentException(
                        "Customer is required and days must be positive.");
            }

            if (!isAvailable()) {
                System.out.println(name + " is currently unavailable.");
                return null;
            }

            activeRental = new Rental(this, customer, days);

            System.out.printf(
                    "%s rented successfully by %s. Rental charge: $%.2f.%n",
                    name, customer.name, activeRental.getCharge());

            return activeRental;
        }

        private void release(Rental rental) {
            if (activeRental != rental) {
                throw new IllegalStateException("Rental is not active.");
            }

            activeRental = null;
        }
    }

    static class Sedan extends Vehicle {
        Sedan(String name) {
            super(name);
        }

        @Override
        public double calculateCharge(int days) {
            return days * 50.0;
        }
    }

    static class SUV extends Vehicle {
        SUV(String name) {
            super(name);
        }

        @Override
        public double calculateCharge(int days) {
            return days * 80.0;
        }
    }

    static class Truck extends Vehicle {
        Truck(String name) {
            super(name);
        }

        @Override
        public double calculateCharge(int days) {
            return days * 100.0;
        }
    }

    static class Rental {
        private final Vehicle vehicle;
        private final Customer customer;
        private final int days;
        private final double charge;
        private boolean returned;

        private Rental(Vehicle vehicle, Customer customer, int days) {
            this.vehicle = vehicle;
            this.customer = customer;
            this.days = days;
            this.charge = vehicle.calculateCharge(days);
        }

        public double getCharge() {
            return charge;
        }

        public void returnVehicle() {
            if (returned) {
                System.out.println("Vehicle has already been returned.");
                return;
            }

            vehicle.release(this);
            returned = true;

            System.out.println(vehicle.name + " returned by "
                    + customer.name + ".");
        }
    }

    public static void main(String[] args) {
        Customer customer1 = new Customer("Customer 1");
        Customer customer2 = new Customer("Customer 2");
        Customer customer3 = new Customer("Customer 3");

        Vehicle sedan = new Sedan("Sedan A");
        Vehicle suv = new SUV("SUV B");

        Rental rental1 = sedan.rent(customer1, 3);

        sedan.rent(customer2, 2);

        if (rental1 != null) {
            rental1.returnVehicle();
        }

        suv.rent(customer3, 5);
    }
}