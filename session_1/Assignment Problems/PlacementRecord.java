class PlacementRecord{
    String studentName;
    String company;
    double packageLpa;
    
    PlacementRecord(String studentName, String company, double packageLpa) {
        this.studentName = studentName;
        this.company = company;
        this.packageLpa = packageLpa;   
    
    }
    void printRecord(){
        System.out.println(studentName + "->" + company + "@" + packageLpa + "LPA");
    }
}
public class main{
    public static void main(String[] args) {
        PlacementRecord[] records = {
            new PlacementRecord("ravi", "TCS", 4.5),
            new PlacementRecord("Anitha", "ZOHO", 6.2),
            new PlacementRecord("karthika", "Infosys", 4.0),

        };
        for (PlacementRecord record : records) {
            record.printRecord();
        }
        
    }
}