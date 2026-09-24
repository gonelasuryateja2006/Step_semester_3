abstract class Drone {
    protected final String id;

    public Drone(String id) {
        this.id = id;
    }

    public abstract String fly();
}

interface Trackable {
    String getLocation();
}

class DeliveryDrone extends Drone implements Trackable {

    public DeliveryDrone(String id) {
        super(id);
    }

    @Override
    public String fly() {
        return id + " flying with a delivery package";
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}

class ScoutDrone extends Drone {

    public ScoutDrone(String id) {
        super(id);
    }

    @Override
    public String fly() {
        return id + " flying on a scouting mission";
    }
}

class GroundRobot implements Trackable {
    private final String id;

    public GroundRobot(String id) {
        this.id = id;
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}

public class SkylineDeliveryFleet {

    static String getLocationIfTrackable(Object object) {
        if (object instanceof Trackable) {
            Trackable trackableObject = (Trackable) object;
            return trackableObject.getLocation();
        }

        return "Tracking not available";
    }

    public static void main(String[] args) {
        DeliveryDrone deliveryDrone =
                new DeliveryDrone("DR-1");

        ScoutDrone scoutDrone =
                new ScoutDrone("SC-1");

        GroundRobot groundRobot =
                new GroundRobot("GR-1");

        System.out.println(
                getLocationIfTrackable(deliveryDrone)
        );

        System.out.println(
                getLocationIfTrackable(scoutDrone)
        );

        System.out.println(
                getLocationIfTrackable(groundRobot)
        );
    }
}