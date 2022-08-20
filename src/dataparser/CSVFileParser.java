package dataparser;

import Models.Employee;
import customException.InvalidDataTypeException;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;

public class CSVFileParser extends DataParser {

    private enum colNames{name,designation,salary,experience}

    private boolean haveTitle;

    public CSVFileParser() {
        this.haveTitle = true;
    }

    @Override
    public void run() {
    }

    @Override
    public ArrayList<Employee> parseData() {
        CSVReader reader;
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
                }else{
                    setEmployees(null);
                    break;
                }
            }
            reader.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return getEmployees();
    }

    private Employee mapEmployeeData(String[] datas){

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
                    "CSV"
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
