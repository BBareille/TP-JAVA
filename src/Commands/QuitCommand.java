package Commands;

import Console.Command;
import Console.Console;
import DAL.DAL;
import database.SqlConnection;

public class QuitCommand extends Command {
    public QuitCommand(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
    }

    @Override
    public Object apply(Object o) {
        Console console = Console.getInstance();
        System.out.println("Good bye ! :)");
        console.setRunning(false);
        return null;
    }
}
