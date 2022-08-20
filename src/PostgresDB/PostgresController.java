package PostgresDB;

import Models.Employee;
import customException.EmptyFileException;
import customException.FileTypeNotMatchedExecption;
import customException.InvalidDataTypeException;
import customException.UnknownFileTypeException;
import dataparser.DataParser;
import dataparser.DataParserFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class PostgresController {
    private PostgresDB postgresDB;

    public PostgresController(PostgresDB postgresDB) {
        this.postgresDB = postgresDB;
    }

    public void createTable() throws SQLException {
        String query = "drop table if exists employee;" +
                "create table employee(id serial primary key,name varchar(30),designation varchar(30),salary float ,experience int,fileType varchar(30));";
        System.out.println("Table Creating " + postgresDB.getStatement().execute(query));
    }

    public void addRatingColumn() throws  SQLException{
        String query = "alter table employee add column rating float";
        postgresDB.getStatement().execute(query);
    }

    public void populateRatingColumn(int minValue,int maxValue) throws SQLException{

//      (random()* (high-low + 1) + low)
        String query = "update employee set rating = random()* (?-? + 1) + ?";
        PreparedStatement preparedStatement = postgresDB.getConnection().prepareStatement(query);
        preparedStatement.setInt(1,maxValue);
        preparedStatement.setInt(2,minValue);
        preparedStatement.setInt(3,minValue);

        preparedStatement.execute();
        preparedStatement.close();
    }

    public void updateSalaryBasedOnRating() throws SQLException{
        String query ="update employee set salary =" +
                "CASE\n" +
                "    WHEN rating>4 THEN salary + salary*0.3 \n" +
                "    WHEN rating>3 THEN salary + salary*0.2\n" +
                "    WHEN rating>2 THEN salary + salary*0.1\n" +
                "    ELSE salary\n" +
                "END;";
        postgresDB.getStatement().execute(query);`
    }

    public void getCountOfHigerExperience(int lowerLimit) throws SQLException{
        String query = "select count(*) from employee where experience > ?";
        PreparedStatement preparedStatement = postgresDB.getConnection().prepareStatement(query);
        preparedStatement.setInt(1,lowerLimit);
        ResultSet result = preparedStatement.executeQuery();
        if(result.next()){
            System.out.println("Total number of employee have experience higher then " + lowerLimit +" years is -> " + result.getString(1));

        }
        preparedStatement.close();

    }

    public void prepareSQLBunchInsert(ArrayList<Employee> employees){
        PreparedStatement preparedStatement =null;
        try {
            if(employees == null) throw new InvalidDataTypeException("Can't Insert corrected data !");

            String query = "insert into employee (name,designation,salary,experience,fileType) values(?,?,?,?,?)";
            preparedStatement = postgresDB.getConnection().prepareStatement(query);

            for(Employee employee:employees){
                preparedStatement.setString(1,employee.getName());
                preparedStatement.setString(2,employee.getDesignation());
                preparedStatement.setFloat(3,employee.getSalary());
                preparedStatement.setInt(4,employee.getExperience());
                preparedStatement.setString(5,employee.getFileType());
                preparedStatement.addBatch();
            }

            int[] updateCounts = preparedStatement.executeBatch();
            System.out.println(updateCounts.length);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public  Thread getParseThread(String type,String fileName)
            throws UnknownFileTypeException,FileTypeNotMatchedExecption,FileNotFoundException,EmptyFileException {

        String basePath = System.getProperty("user.dir") + "/src/";

        DataParserFactory dataParserFactory = new DataParserFactory();
        DataParser parser = dataParserFactory.getParserObject(type);

        if (parser == null){
            throw new UnknownFileTypeException("Unknown File Type !");
        }

        String fileExtention = fileName.substring(fileName.lastIndexOf('.')+1);
        if (!type.equals(fileExtention)){
            throw new FileTypeNotMatchedExecption("File Type Not Matched !");
        }


        File file = getFileByName(basePath + fileName);
        parser.setFile(file);

        return new Thread(parser){
            @Override
            public void run() {
                super.run();
                prepareSQLBunchInsert(parser.parseData());
            }
        };
    }

    private File getFileByName(String path) throws FileNotFoundException,EmptyFileException {

        File file = new File(path);
        Scanner fileContent = new Scanner(new FileReader(file));

        if(!fileContent.hasNextLine()){
            throw new EmptyFileException("File is Empty!");
        }

        return file;
    }
}
