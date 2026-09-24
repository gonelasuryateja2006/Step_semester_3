abstract class DeliveryNote {
    protected final String trackingId;

    public DeliveryNote(String trackingId) {
        this.trackingId = trackingId;
    }

    public abstract String confirmDelivery();

    public String confirmDelivery(String signature) {
        return confirmDelivery()
                + ", signed by " + signature;
    }
}

class ParcelNote extends DeliveryNote {

    public ParcelNote(String trackingId) {
        super(trackingId);
    }

    @Override
    public String confirmDelivery() {
        return "Parcel " + trackingId + " delivered";
    }
}

class LetterNote extends DeliveryNote {

    public LetterNote(String trackingId) {
        super(trackingId);
    }

    @Override
    public String confirmDelivery() {
        return "Letter " + trackingId + " delivered";
    }
}

public class PackageDropOffLog {

    static void logAll(DeliveryNote[] notes) {
        for (DeliveryNote note : notes) {
            System.out.println(note.confirmDelivery());
        }
    }

    public static void main(String[] args) {
        ParcelNote parcel = new ParcelNote("TRK-1");

        System.out.println(parcel.confirmDelivery());

        System.out.println(
                parcel.confirmDelivery("J. Smith")
        );

        // Upcasting
        DeliveryNote reference = parcel;

        DeliveryNote[] notes = {
                reference,
                new LetterNote("TRK-2")
        };

        logAll(notes);
    }
}