package asg.StaffManagementModule.Model;

import asg.StaffManagementModule.Model.People;

public class Staff extends People {

    private String position;
    private double salary;
    private String department;

    public Staff(String ID, String name, String gender, String position, double salary, String department) {
        super(ID, name, gender);
        this.position = position;
        this.salary = salary;
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
