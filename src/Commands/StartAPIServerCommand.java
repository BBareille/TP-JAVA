package Commands;

import Console.Command;
import DAL.DAL;
import database.SqlConnection;
import server.Server;

import java.io.IOException;

public class StartAPIServerCommand extends Command {
    public StartAPIServerCommand(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
    }

    @Override
    public Object apply(Object o) {
        try {
            Server.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
