package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
	// write your code here
        DataBase db = new DataBase();
        db.parseData("txt","Employee_txt.txt");
        db.parseData("csv","Employee_csv.csv");
        db.parseData("json","Employee_json.json");


        HashMap<String,ArrayList<Employee>> tables =  db.getTables();
        for(Map.Entry<String,ArrayList<Employee>> e : tables.entrySet()){
            System.out.println(e.getKey() + " = "+ e.getValue()+"\n");

        }

//        db.parseData("json",basePath+"Employee_json.json");
    }
}