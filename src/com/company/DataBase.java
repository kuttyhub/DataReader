package com.company;

import com.company.custom_exception.EmptyFileException;
import com.company.custom_exception.FileTypeNotMatchedExecption;
import com.company.custom_exception.UnknownFileTypeException;
import com.company.dataparser.CSVFileParser;
import com.company.dataparser.DataParser;
import com.company.dataparser.JSONFileReader;
import com.company.dataparser.TXTFileParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DataBase {
    public HashMap<String, ArrayList<Employee>> getTables() {
        return tables;
    }

    private HashMap<String,ArrayList<Employee>> tables;
    private HashMap<String,DataParser> types;

    public DataBase() {
        this.tables = new HashMap<>();
        this.types = new HashMap<>();
        initiateTypes();
    }

    public void initiateTypes(){
        types.put("txt",new TXTFileParser());
        types.put("csv",new CSVFileParser());
        types.put("json",new JSONFileReader());
    }


    public void parseData(String type,String fileName){

        String basePath = System.getProperty("user.dir") + "/src/";

        try {
            if (!types.containsKey(type)){
                throw new UnknownFileTypeException("Unknown File Type !");
            }
            String fileExtention = fileName.substring(fileName.lastIndexOf('.')+1);
            if (!type.equals(fileExtention)){
                throw new FileTypeNotMatchedExecption("File Type Not Matched !");
            }


            File file = getFileByName(basePath + fileName);
            DataParser parser = types.get(type);
            parser.setFile(file);

            Thread thread = new Thread(parser);
            thread.start();
            thread.join();

            tables.put(type,parser.getEmployees());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private File getFileByName(String path) throws FileNotFoundException,EmptyFileException {

        File file = new File(path);
        Scanner inFile = new Scanner(new FileReader(file));

        if(!inFile.hasNextLine()){
            throw new EmptyFileException("File is Empty!");
        }

        return file;
    }
}
