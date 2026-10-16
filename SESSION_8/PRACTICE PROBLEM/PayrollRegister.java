
import java.util.*;

class Payslip {
    String name, type;
    int pay;

    Payslip(String name, String type, int pay) {
        this.name = name;
        this.type = type;
        this.pay = pay;
    }

    public String toString() {
        return "Payslip[name=" + name +
                ", type=" + type +
                ", pay=" + pay + "]";
    }
}

public class PayrollRegister {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Payslip> staff = new ArrayList<>();

        while (sc.hasNext()) {
            String type = sc.next();

            if (type.equals("END")) {
                break;
            }

            String name = sc.next();
            int pay;

            if (type.equals("FullTime")) {
                pay = sc.nextInt();

            } else if (type.equals("PartTime")) {
                int hours = sc.nextInt();
                int rate = sc.nextInt();
                pay = hours * rate;

            } else if (type.equals("Intern")) {
                pay = sc.nextInt();

            } else {
                continue;
            }

            staff.add(new Payslip(name, type, pay));
        }

        int total = 0;
        int highest = -1;
        String topEarner = "";

        for (Payslip p : staff) {
            System.out.println(p);
            total += p.pay;

            if (p.pay > highest) {
                highest = p.pay;
                topEarner = p.name;
            }
        }

        System.out.println("Total " + total);

        if (!staff.isEmpty()) {
            System.out.println("top earner " + topEarner);
        }

        sc.close();
    }
}
