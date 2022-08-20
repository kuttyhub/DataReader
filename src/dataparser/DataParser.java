package dataparser;

import Models.Employee;

import java.io.File;
import java.util.ArrayList;

public abstract class DataParser implements Runnable{

    private File file;
    private ArrayList<Employee> employees = new ArrayList<>();

    public abstract ArrayList<Employee> parseData();

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public ArrayList<Employee> getEmployees(){
        return employees;
    }

    public void addEmployee(Employee employees) {
        this.employees.add(employees);
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }
}
