package Commands;

import Console.Command;
import Controller.CommandController;
import DAL.DAL;
import database.SqlConnection;


public class ManageMenu extends Command {

    private CommandController commandController;
    public ManageMenu(Integer id, String displayName, SqlConnection connection, DAL model, CommandController commandController) {
        super(id, displayName, connection, model);
        this.commandController = commandController;
    }


    @Override
    public Object apply(Object o) {
            try {
                commandController.subMenu(model, model.getClass().getSimpleName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
    }
}
