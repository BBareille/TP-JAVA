package Commands;

import Console.Command;
import DAL.DAL;
import database.SqlConnection;

public class StartAPIServerCommand extends Command {
    public StartAPIServerCommand(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
    }

    @Override
    public Object apply(Object o) {
        return null;
    }
}
