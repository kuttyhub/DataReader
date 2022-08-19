package com.company.dataparser;

import com.company.Employee;
import com.company.custom_exception.InvalidDataTypeException;
import com.company.dataparser.DataParser;

import java.io.*;

public class TXTFileParser extends DataParser {

    private enum colNames{name,designation,salary,experience}

    @Override
    public void run() {

        try {
            BufferedReader bf = new BufferedReader(new FileReader(getFile()));
            String line ;

            while ((line = bf.readLine()) != null && line.contains(",")){
                Employee emp = mapEmployeeData(line);
                if (emp != null){
                    addEmployee(emp);
                }
            }
            bf.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            setEmployees(null);

        } catch (IOException e) {
            e.printStackTrace();
        }

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
                    Double.parseDouble(datas[colNames.salary.ordinal()]),
                    Integer.parseInt(datas[colNames.experience.ordinal()])
            );
            return  emp;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
