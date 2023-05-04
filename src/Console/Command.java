package Console;

import DAL.DAL;
import database.SqlConnection;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Command<T, R> implements Function<T, R> {

    protected Integer id;
    protected String displayName;

    protected SqlConnection connection;
    protected DAL model;

    public Command(Integer id, String displayName, SqlConnection connection, DAL model){
        this.id = id;
        this.displayName = displayName;
        this.connection = connection;
        this.model = model;
    }
}
