package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
