package models;

import DAL.DAL;
import database.SqlConnection;

public class Category extends DAL<Category> {

    private long id;

    private String name;



    public Category(){
        super(null);
    }
    public Category(SqlConnection connection) {
        super(connection);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    protected void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
