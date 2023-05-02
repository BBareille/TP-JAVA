package models;

import DAL.DAL;
import database.SqlConnection;

public class Level extends DAL<Level> {

    private Long id;
    private String name;

    public Level(){
        super(null);
    }

    public Level(SqlConnection connection) {
        super(connection);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


/*    public void createFromScanner(Scanner scanner){
        this.name = scanner.nextLine();
        this.age = scanner.nextInt();
    }*/
}
