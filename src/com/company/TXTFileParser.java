package com.company;

import java.io.*;

public class TXTFileParser extends DataParser  implements Runnable  {

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

            Employee emp = new Employee(
                    datas[colNames.name.ordinal()],
                    datas[colNames.designation.ordinal()],
                    Double.parseDouble(datas[colNames.salary.ordinal()]),
                    Integer.parseInt(datas[colNames.experience.ordinal()])
            );
            return  emp;

        }catch (Exception e){
//            e.printStackTrace();
            return null;
        }
    }
}
