package Commands;

import Console.Command;
import DAL.DAL;
import database.SqlConnection;

public class AttachToTraining extends Command<Object, String> {
    public AttachToTraining(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
    }

    @Override
    public String apply(Object o) {
        return null;
    }
}
