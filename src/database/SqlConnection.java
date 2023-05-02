package database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.function.Consumer;

public class SqlConnection {

    private String url = "jdbc:mysql://localhost:3306/tpjavadal";
    private String user = "root";
    private String pwd = "";

    private Connection connection;

    public SqlConnection(){}

    public SqlConnection(String url, String user, String pwd) {
        this.url = url;
        this.user = user;
        this.pwd = pwd;
    }


    public void connect() throws SQLException {

        System.out.println("Connecting database...");

        this.connection = DriverManager.getConnection(this.url, this.user, this.pwd);
    }

    public Connection getConnection() {
        return connection;
    }

    public Consumer initDatabase() throws IOException, SQLException {

        Statement statement = connection.createStatement();
        Path sqlScriptPath = Paths.get("C:\\Users\\bapti\\IdeaProjects\\TP-JAVA\\database.sql");
        List<String> sqlScript = Files.readAllLines(sqlScriptPath);
        for(String script: sqlScript) {
            Integer success =  statement.executeUpdate(script);
            System.out.println("Query: " + script);
        }
        System.out.println("Base initialiser");
        return null;
    }
}
