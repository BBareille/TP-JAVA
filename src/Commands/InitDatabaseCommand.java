package Commands;

import Console.Command;
import DAL.DAL;
import database.SqlConnection;

import java.io.IOException;
import java.sql.SQLException;

public class InitDatabaseCommand extends Command {

    public InitDatabaseCommand(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
    }

    @Override
    public Object apply(Object o) {
        try {
            connection.initDatabase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
