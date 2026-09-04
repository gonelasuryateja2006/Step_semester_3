class Employee{
    String id;
    double Salary;
    Employee(String id, double Salary){
        this.id = id;
        this.Salary = Salary;
    }
    void raiseSalary(double Salary){
        this.Salary = this.Salary + Salary;
    }
    void display(){
        System.out.println(id + "|  FinalSalary:RS" + Salary);
    }
}
public class Employeelist{
    public static void main(String[] args){
        Employee[]employees={
            new Employee("E-101", 40000),
            new Employee("E-102", 55000),
            new Employee("E-103", 62000),
            new Employee("E-104", 48000),
        };
        double bonus = 500;
        for(Employee employee : employees){
            employee.raiseSalary(bonus);
            employee.display();
        }
    }
}