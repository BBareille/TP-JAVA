import database.SqlConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;
import database.SqlConnection.*;

public class Console {

    Scanner scanner;
    Integer commandAsked;
    ArrayList<Command> commandList;

    public void Console(){
        this.scanner = scanner;
        this.commandList = new ArrayList<>();
    }

    public void setCommandAsked(Integer n){
        this.commandAsked = n;
    }
    public void mainMenu() throws SQLException {
        SqlConnection connection = new SqlConnection();
        connection.connect();
        Scanner scanner = new Scanner(System.in);
        int nombre = 0;
        while (nombre != 6) {

            command(c -> {
                try {
                    connection.initDatabase();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }, "1. Initialiser base de données", 1);

            System.out.println("2. Gérer formations");
            System.out.println("3. Gérer catégories");
            System.out.println("4. Gérer formateurs");
            System.out.println("5. Gérer stagiaires");
            command(c -> System.out.println("Good bye ! :)"), "6. Quitter", 6);

            nombre = scanner.nextInt();
            setCommandAsked(nombre);
        }
    }

    public <T> void command(Consumer<T> command, T t, Integer n){
        System.out.println(t);
        if(n == commandAsked){
            command.accept(t);
        }

    }
}
