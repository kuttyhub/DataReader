package dataparser;

import Models.Employee;
import customException.DataFormatMismatchException;
import customException.InvalidDataTypeException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;

public class JSONFileReader extends DataParser {

    @Override
    public void run() {

    }

    @Override
    public ArrayList<Employee> parseData(){
        try {
            JSONParser parser = new JSONParser();
            JSONArray datas= (JSONArray) parser.parse(new FileReader(getFile()));
            for(Object row :datas){
                JSONObject data = (JSONObject) row;

                Employee emp =mapEmployeeData(data);
                if (emp != null){
                    addEmployee(emp);
                }else{
                    setEmployees(null);
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return getEmployees();
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
                    Float.parseFloat(row.get("salary").toString()),
                    Integer.parseInt(row.get("experience").toString()),
                    "json"
            );
            return  emp;

        }catch (Exception e){
            e.printStackTrace();
            throw new DataFormatMismatchException("The row data format is not matched");
        }
    }
}
