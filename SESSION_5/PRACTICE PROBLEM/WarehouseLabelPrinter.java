interface Printable {
    String printLabel();
}

class PackageBox implements Printable {
    private final String trackingId;

    public PackageBox(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String printLabel() {
        return "Package label: " + trackingId;
    }
}

class Invoice implements Printable {
    private final String invoiceNumber;

    public Invoice(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public String printLabel() {
        return "Invoice label: " + invoiceNumber;
    }
}

public class WarehouseLabelPrinter {

    static void printAll(Printable[] items) {
        for (Printable item : items) {
            System.out.println(item.printLabel());
        }
    }

    public static void main(String[] args) {
        PackageBox packageBox = new PackageBox("TRK-88");
        Invoice invoice = new Invoice("INV-42");

        Printable[] items = {packageBox, invoice};

        printAll(items);
    }
}