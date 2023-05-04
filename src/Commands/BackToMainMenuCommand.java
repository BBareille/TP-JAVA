package Commands;

import Console.Command;
import Controller.CommandController;
import DAL.DAL;
import database.SqlConnection;

import static Controller.CommandController.getInstance;

public class BackToMainMenuCommand extends Command {

    public BackToMainMenuCommand(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
    }

    @Override
    public Object apply(Object o) {
        CommandController commandController = getInstance();
        try {
                    commandController.menu();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        return null;
    }
}
