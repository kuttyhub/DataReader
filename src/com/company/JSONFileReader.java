package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JSONFileReader extends DataParser{

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
                }else{
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private Employee mapEmployeeData(JSONObject row) throws DataFormatMismatchException {
        try {

            if(!row.get("salary").toString().matches("[0-9]+"))
            {
                throw new InvalidDataTypeException("Salary should be Number");
            }

            Employee emp = new Employee(
                    (String) row.get("name"),
                    (String) row.get("designation"),
                    Double.parseDouble(row.get("salary").toString()),
                    Integer.parseInt(row.get("experience").toString())
            );
            return  emp;

        }catch (Exception e){
            e.printStackTrace();
            throw new DataFormatMismatchException("The row data format is not matched");
        }
    }
}
