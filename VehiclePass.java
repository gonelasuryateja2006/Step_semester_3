
import java.util.*;

interface Chargeable {
    void charge();
}

class Vehicle {
    String passNumber, owner, type;
    int fee;

    Vehicle(String type, String passNumber, String owner) {
        this.type = type;
        this.passNumber = passNumber;
        this.owner = owner;

        if (type.equals("Bike") || type.equals("EBike")) {
            fee = 300;
        } else {
            fee = 1000;
        }
    }

    void display() {
        System.out.println(passNumber + " (" + type +
                ") pass fee " + fee);
    }
}

class ElectricVehicle extends Vehicle implements Chargeable {
    ElectricVehicle(String type, String passNumber, String owner) {
        super(type, passNumber, owner);
    }

    public void charge() {
        System.out.println(passNumber + " charging bay allotted");
    }
}

public class VehiclePass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<String, Vehicle> vehicles = new HashMap<>();

        while (sc.hasNext()) {
            String operation = sc.next();

            if (operation.equals("PASS")) {
                String type = sc.next();
                String number = sc.next();
                String owner = sc.next();

                Vehicle v;

                if (type.equals("ECar") || type.equals("EBike")) {
                    v = new ElectricVehicle(type, number, owner);
                } else {
                    v = new Vehicle(type, number, owner);
                }

                vehicles.put(number, v);
                v.display();

            } else if (operation.equals("CHARGE")) {
                String number = sc.next();

                if (!vehicles.containsKey(number)) {
                    System.out.println("Vehicle not found");
                } else {
                    Vehicle v = vehicles.get(number);

                    if (v instanceof Chargeable) {
                        ((Chargeable) v).charge();
                    } else {
                        System.out.println(number +
                                " rejected: charging unsupported");
                    }
                }
            }
        }
        sc.close();
    }
}
