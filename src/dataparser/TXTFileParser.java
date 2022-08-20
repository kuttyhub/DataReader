package dataparser;

import Models.Employee;
import customException.InvalidDataTypeException;
import java.io.*;
import java.util.ArrayList;

public class TXTFileParser extends DataParser {

    private enum colNames{name,designation,salary,experience}

    @Override
    public void run() {

    }

    @Override
     public  ArrayList<Employee> parseData() {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(getFile()));
            String line ;

            while ((line = bf.readLine()) != null && line.contains(",")){
                Employee emp = mapEmployeeData(line);
                if (emp != null){
                    addEmployee(emp);
                }else{
                    setEmployees(null);
                    break;
                }
            }
            bf.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            setEmployees(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return getEmployees();
    }

    private Employee mapEmployeeData(String line){
        String[] datas = line.split(",");
        try {
            if(!datas[colNames.salary.ordinal()].matches("[0-9]+"))
            {
                throw new InvalidDataTypeException("Salary should be Number");
            }

            Employee emp = new Employee(
                    datas[colNames.name.ordinal()],
                    datas[colNames.designation.ordinal()],
                    Float.parseFloat(datas[colNames.salary.ordinal()]),
                    Integer.parseInt(datas[colNames.experience.ordinal()]),
                    "txt"
            );
            return  emp;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
