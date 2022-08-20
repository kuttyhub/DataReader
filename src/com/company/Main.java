package com.company;

import PostgresDB.PostgresDB;
import Models.CustomFile;
import PostgresDB.PostgresController;

public class Main {

    public static void main(String[] args) {

        String DB_NAME = "quinbay";
        PostgresDB postgresDB = PostgresDB.createInstance(DB_NAME);
        CustomFile[] files = {
                new CustomFile("txt","Employee_txt.txt"),
                new CustomFile("csv","Employee_csv.csv"),
                new CustomFile("json","Employee_json.json"),
        };

        Thread[] threadPool = new Thread[files.length];

        try{
            postgresDB.connectDB();
            PostgresController postgrsController = new PostgresController(postgresDB);

            postgrsController.createTable();



            for (int i=0 ;i<files.length;i++){
                Thread parser = threadPool[i] = postgrsController.getParseThread(files[i].getType(),files[i].getFilename());
                parser.start();
            }
            for (int i = 0; i < files.length; i++) {
                threadPool[i].join();
            }

            postgrsController.addRatingColumn();
            postgrsController.populateRatingColumn(1,5);
            postgrsController.updateSalaryBasedOnRating();
            postgrsController.getCountOfHigerExperience(4);

        }catch (Exception e){
            e.printStackTrace();
        }

        postgresDB.closeAllConnections();
    }

}