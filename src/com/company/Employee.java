package com.company;

public class Employee {
    private String name;
    private String designation;
    private double salary;
    private int experience;


    public Employee(String name, String designation, double salary, int experience) {
        this.name = name;
        this.designation = designation;
        this.salary = salary;
        this.experience = experience;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "(" + name +
                "," + designation +
                "," + salary +
                "," + experience +
                ')';
    }
}
