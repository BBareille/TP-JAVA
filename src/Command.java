import java.util.function.Consumer;

public class Command {

    Integer id;

    String displayName;

    public void Command(Integer id, String displayName){
        this.id = id;
        this.displayName = displayName;
    }
}
