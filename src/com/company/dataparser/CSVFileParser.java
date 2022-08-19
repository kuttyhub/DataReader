package com.company.dataparser;

import com.company.Employee;
import com.company.custom_exception.InvalidDataTypeException;
import com.company.dataparser.DataParser;
import com.opencsv.CSVReader;

import java.io.FileReader;

public class CSVFileParser extends DataParser {

    private enum colNames{name,designation,salary,experience}

    private boolean haveTitle;

    public CSVFileParser() {
        this.haveTitle = true;
    }

    @Override
    public void run() {
        CSVReader reader = null;
        try
        {
            reader = new CSVReader(new FileReader(getFile()));
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null)
            {
                if(isHaveTitle()){
                    setHaveTitle(false);
                    continue;
                }
                Employee emp = mapEmployeeData(nextLine);
                if (emp != null){
                    addEmployee(emp);
                }
            }
            reader.close();
//            BufferedReader bf = new BufferedReader(new FileReader(getFile()));
//            String line ;
//
//            while ((line = bf.readLine()) != null && line.contains(",")){
//                Employee emp = mapEmployeeData(line);
//                if (emp != null){
//                    addEmployee(emp);
//                }
//            }
//            bf.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private Employee mapEmployeeData(String[] datas){
//        String[] datas = line.split(",");
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

    public boolean isHaveTitle() {
        return haveTitle;
    }

    public void setHaveTitle(boolean haveTitle) {
        this.haveTitle = haveTitle;
    }
}
