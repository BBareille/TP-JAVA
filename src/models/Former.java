package models;

import DAL.DAL;
import database.SqlConnection;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Former extends DAL<Former> {
    private Long id;
    private String firstName;
    private String lastName;

    public Former() {
        super(null);
    }
    public Former(SqlConnection connection) {
        super(connection);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    public void getFormationList(){

//    }

    @Override
    public String toString() {
        return "Former{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", formation='" + lastName + '\'' +
                '}';
    }
}