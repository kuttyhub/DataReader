package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Scanner;

public class JSONFileReader extends DataParser implements Runnable {

    private enum colNames{name,designation,salary,experience}
    @Override
    public void run() {
        try {
            JSONParser parser = new JSONParser();
            JSONArray datas= (JSONArray) parser.parse(new FileReader(getFile()));
            for(Object row :datas){
                JSONObject data = (JSONObject) row;

                Employee emp =mapEmployeeData(data);
                if (emp != null){
                    addEmployee(emp);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private Employee mapEmployeeData(JSONObject row){
        try {
            Employee emp = new Employee(
                    (String) row.get("name"),
                    (String) row.get("designation"),
                    Double.parseDouble(row.get("salary").toString()),
                    Integer.parseInt(row.get("experience").toString())
            );
            return  emp;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
