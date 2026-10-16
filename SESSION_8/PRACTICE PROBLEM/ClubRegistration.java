
import java.util.*;

class Student {
    String rollNumber, name;

    Student(String rollNumber, String name) {
        this.rollNumber = rollNumber;
        this.name = name;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;

        Student s = (Student) obj;
        return rollNumber.equals(s.rollNumber);
    }

    public int hashCode() {
        return rollNumber.hashCode();
    }
}

public class ClubRegistration {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashSet<Student> members = new HashSet<>();
        ArrayList<String> addOutput = new ArrayList<>();
        ArrayList<String> checkOutput = new ArrayList<>();

        while (sc.hasNext()) {
            String operation = sc.next();

            if (operation.equals("ADD")) {
                String roll = sc.next();
                String name = sc.next();

                if (members.add(new Student(roll, name))) {
                    addOutput.add("Added");
                } else {
                    addOutput.add("duplicate rejected");
                }

            } else if (operation.equals("CONTAINS")) {
                String roll = sc.next();
                String name = sc.next();

                checkOutput.add("contains: " +
                        members.contains(new Student(roll, name)));
            }
        }

        for (String s : addOutput) {
            System.out.println(s);
        }

        System.out.println("member count " + members.size());

        for (String s : checkOutput) {
            System.out.println(s);
        }

        sc.close();
    }
}
