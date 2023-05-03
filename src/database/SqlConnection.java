package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

    public void initDatabase() throws IOException, SQLException {

        Statement statement = connection.createStatement();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("database.sql");

        if(inputStream == null) {
            System.err.println("erreur chargement fichier");
            return;
        }

        String text = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        String[] sqlScript = text.split(";");
        for(String script: sqlScript) {
            Integer success =  statement.executeUpdate(script);
            System.out.println("Query: " + script);
        }
        System.out.println("Base initialiser");
    }
}
