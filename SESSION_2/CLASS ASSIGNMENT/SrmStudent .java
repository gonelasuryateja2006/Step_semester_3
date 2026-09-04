class SrmStudent {
    static String collegeName;
    static String academicYear;
    String name;
    // Static block
    static {
        collegeName = "SRM University";
        academicYear = "2026";
        System.out.println("College info loaded");
    }
    // Constructor
    SrmStudent(String name) {
        this.name = name;
        System.out.println(
            "Student record created: " + name
        );
    }
}
public class M4_SrmStudent {
    public static void main(String[] args) {
        String[] names = {"Ravi","Meera","Karthik","Divya","Anitha"};
        // Create students in a loop
        for (String name : names) {
            new SrmStudent(name);
        }
    }
}