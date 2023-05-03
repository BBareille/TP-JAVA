package Console;

import java.util.function.Consumer;

public class Command<T> {
    Integer id;
    String displayName;
    Consumer<T> consumer;

    public Command(Integer id, String displayName, Consumer<T> consumer){
        this.id = id;
        this.displayName = displayName;
        this.consumer = consumer;
    }
}
