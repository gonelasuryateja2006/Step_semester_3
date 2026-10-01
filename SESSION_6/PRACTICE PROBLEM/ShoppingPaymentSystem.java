import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingPaymentSystem {

    enum OrderStatus {
        PENDING, PAID
    }

    interface PaymentMethod {
        String getName();
        boolean processPayment(BigDecimal amount);
    }

    static class CreditCardPayment implements PaymentMethod {
        private final boolean successful;

        CreditCardPayment(boolean successful) {
            this.successful = successful;
        }

        public String getName() {
            return "Credit Card";
        }

        public boolean processPayment(BigDecimal amount) {
            System.out.println("Processing card payment of $" + amount);
            return successful;
        }
    }

    static class PayPalPayment implements PaymentMethod {
        private final boolean successful;

        PayPalPayment(boolean successful) {
            this.successful = successful;
        }

        public String getName() {
            return "PayPal";
        }

        public boolean processPayment(BigDecimal amount) {
            System.out.println("Processing PayPal payment of $" + amount);
            return successful;
        }
    }

    static class BankTransferPayment implements PaymentMethod {
        private final boolean successful;

        BankTransferPayment(boolean successful) {
            this.successful = successful;
        }

        public String getName() {
            return "Bank Transfer";
        }

        public boolean processPayment(BigDecimal amount) {
            System.out.println("Processing bank transfer of $" + amount);
            return successful;
        }
    }

    static class Customer {
        private final String name;

        Customer(String name) {
            this.name = name;
        }
    }

    static class Product {
        private final String name;
        private final BigDecimal price;

        Product(String name, String price) {
            this.name = name;
            this.price = new BigDecimal(price);

            if (this.price.signum() < 0) {
                throw new IllegalArgumentException(
                        "Product price cannot be negative.");
            }
        }
    }

    static class OrderItem {
        private final Product product;
        private final int quantity;

        OrderItem(Product product, int quantity) {
            if (product == null || quantity <= 0) {
                throw new IllegalArgumentException(
                        "Product is required and quantity must be positive.");
            }

            this.product = product;
            this.quantity = quantity;
        }

        public BigDecimal calculateSubtotal() {
            return product.price.multiply(BigDecimal.valueOf(quantity));
        }
    }

    static class Order {
        private final String id;
        private final Customer customer;
        private final List<OrderItem> items = new ArrayList<>();
        private OrderStatus status = OrderStatus.PENDING;

        Order(String id, Customer customer) {
            this.id = id;
            this.customer = customer;

            System.out.println("Order created for " + customer.name + ".");
        }

        public void addProduct(Product product, int quantity) {
            if (status == OrderStatus.PAID) {
                System.out.println("Cannot modify a paid order.");
                return;
            }

            items.add(new OrderItem(product, quantity));
        }

        public BigDecimal calculateTotal() {
            BigDecimal total = BigDecimal.ZERO;

            for (OrderItem item : items) {
                total = total.add(item.calculateSubtotal());
            }

            return total;
        }

        public void pay(PaymentMethod paymentMethod) {
            if (items.isEmpty()) {
                System.out.println(
                        "Cannot process payment for an empty order.");
                return;
            }

            if (status == OrderStatus.PAID) {
                System.out.println("Order is already Paid.");
                return;
            }

            if (paymentMethod == null) {
                throw new IllegalArgumentException(
                        "A payment method is required.");
            }

            System.out.println("Payment initiated via "
                    + paymentMethod.getName() + " for Order " + id + ".");

            boolean success =
                    paymentMethod.processPayment(calculateTotal());

            if (success) {
                status = OrderStatus.PAID;
                System.out.println("Payment for Order " + id
                        + " successful. Order status: Paid.");
            } else {
                System.out.println("Payment for Order " + id
                        + " failed. Order status: Pending.");
            }
        }
    }

    public static void main(String[] args) {
        Product productA = new Product("Product A", "100.00");
        Product productB = new Product("Product B", "50.00");
        Product productC = new Product("Product C", "200.00");

        Order orderX = new Order("X", new Customer("Customer X"));
        orderX.addProduct(productA, 2);
        orderX.addProduct(productB, 1);
        orderX.pay(new CreditCardPayment(true));

        Order orderY = new Order("Y", new Customer("Customer Y"));
        orderY.pay(new CreditCardPayment(true));

        Order orderZ = new Order("Z", new Customer("Customer Z"));
        orderZ.addProduct(productC, 1);
        orderZ.pay(new PayPalPayment(false));
    }
}