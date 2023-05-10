package models;

import DAL.DAL;
import database.SqlConnection;

import java.sql.Connection;


public class Trainee extends DAL<Trainee> {

    String firstName;
    String lastName;

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

    Long id;
    public Trainee(SqlConnection connection) {
        super(connection);
    }
    public Trainee() {
        super(null);
    }


    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id = '" + getId() + '\'' +
                '}';
    }
}
