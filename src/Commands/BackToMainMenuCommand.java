package Commands;

import Console.Command;
import DAL.DAL;
import database.SqlConnection;

import static Controller.CommandController.menu;

public class BackToMainMenuCommand extends Command {

    public BackToMainMenuCommand(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
    }

    @Override
    public Object apply(Object o) {
        try {
                    menu();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        return null;
    }
}
