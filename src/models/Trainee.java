package models;

import DAL.DAL;
import database.SqlConnection;


public class Trainee extends DAL<Trainee> {

    String firstName;
    String lastName;
    Long id;
    public Trainee(SqlConnection connection) {
        super(connection);
    }

    @Override
    protected void setId(Long id) {
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
