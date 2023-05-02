package models;

import DAL.DAL;
import database.SqlConnection;


public class Trainee extends DAL<Trainee> {

    String firstName;
    String lastName;

    public Trainee(SqlConnection connection) {
        super(connection);
    }

    @Override
    protected void setId(Long id) {

    }

    @Override
    protected Long getId() {
        return null;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
