package Models;

public class Employee {
    private String name;
    private String designation;
    private float salary;
    private int experience;
    private String fileType;

    public Employee(String name, String designation, float salary, int experience,String fileType) {
        this.name = name;
        this.designation = designation;
        this.salary = salary;
        this.experience = experience;
        this.fileType = fileType;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "(" + name +
                "," + designation +
                "," + salary +
                "," + experience +
                "," + fileType +
                ')';
    }
}
