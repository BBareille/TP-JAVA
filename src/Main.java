import Console.Console;
import database.SqlConnection;

public class Main {
    public static void main(String[] args){
        SqlConnection connection = new SqlConnection();
        try {
            connection.connect();

           Console console = new Console(connection);
           console.run();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}