package Commands;

import Console.Command;
import Controller.CommandController;
import DAL.DAL;
import database.SqlConnection;


public class ManageMenu extends Command {

    private CommandController commandController;
    public ManageMenu(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
        this.commandController = CommandController.getInstance();
    }


    @Override
    public Object apply(Object o) {
            try {
                this.commandController.subMenu(model, model.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return null;
    }
}
