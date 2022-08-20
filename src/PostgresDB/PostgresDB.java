package PostgresDB;

import java.sql.*;

public class PostgresDB {
    private final String JDBC_PATH = "jdbc:postgresql://localhost:5432/";
    private String DB_NAME;
    private Connection connection;
    private Statement statement;

    public static PostgresDB instance;


    private PostgresDB(){}

    private PostgresDB(String DB_NAME) {
        this.DB_NAME = DB_NAME;
        this.connection = null;
        this.statement = null;
    }

    public static synchronized PostgresDB createInstance(String DB_NAME){
        if(instance != null){
            return instance;
        }
        instance = new PostgresDB(DB_NAME);

        return instance;
    }

    public void connectDB() throws ClassNotFoundException,SQLException {

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(JDBC_PATH + DB_NAME);
            System.out.println("Connected TO DB...!");

            statement = connection.createStatement();
            System.out.println("Statement Created ...!");


    }

    public String getJDBC_PATH() {
        return JDBC_PATH;
    }

    public String getDB_NAME() {
        return DB_NAME;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void closeAllConnections(){
        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    @Override
    protected void finalize() throws Throwable {
        if(statement != null){
            statement.close();
            System.out.println("Statement closed...!");
        }
        if(connection != null){
            connection.close();
            System.out.println("Connection closed...!");

        }
        super.finalize();
    }
}
