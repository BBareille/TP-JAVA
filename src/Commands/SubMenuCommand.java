package Commands;

import Console.Command;
import Console.Console;
import DAL.DAL;
import database.SqlConnection;
import models.Former;
import models.Trainee;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubMenuCommand extends Command<Object, List<Command>> {
    private String menuName;
    public SubMenuCommand(Integer id, String displayName, SqlConnection connection, String menuName, DAL model) {
        super(id, displayName, connection, model);
        this.menuName = menuName;
    }

    @Override
    public List<Command> apply(Object obj) {
        List<Command> commandList = new ArrayList<>();

        commandList.add(new FindAllCommand(1, "1.Voir tous les " + menuName, connection, model));
        commandList.add(new FindOneCommand(2, "2. Voir un(e) "+ menuName, connection, model));
        commandList.add(new AddModelCommand(3, "3. Ajouter un(e) "+ menuName, connection, model));
        commandList.add(new UpdateModelCommand(4, "4. Modifier un(e) "+ menuName, connection, model));
        commandList.add(new DeleteModelCommand(5, "5. Supprimer un(e) "+ menuName, connection, model));
        if(obj.getClass().getSimpleName().equals("Training")){
            commandList.add(new AttachToTraining(6, "6. Ajouter un(e) Formateur(ice)", connection, model, new Former(connection)));
            commandList.add(new AttachToTraining(7, "7. Ajouter un(e) Stagiaire", connection, model, new Trainee(connection)));
        }
        return commandList;
    }
}
