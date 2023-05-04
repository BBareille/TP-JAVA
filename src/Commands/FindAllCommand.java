package Commands;

import Console.Command;
import DAL.DAL;
import database.SqlConnection;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class FindAllCommand extends Command<Object, String> {
    public FindAllCommand(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
    }
    @Override
    public String apply(Object o) {
        try {
            return model.findAll().toString();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
