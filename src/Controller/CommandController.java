package Controller;

import Commands.*;
import Console.Console;
import Console.Command;
import DAL.DAL;
import database.SqlConnection;
import models.Category;
import models.Former;
import models.Trainee;
import models.Training;
import java.sql.SQLException;
import java.util.*;

public class CommandController {

    private static CommandController instance;

    private SqlConnection connection;
    public CommandController(SqlConnection connection) {
        instance = this;
        this.connection = connection;
    }
    public static CommandController getInstance() {
        return instance;
    }
    public void subMenu(DAL model, String menuName) throws SQLException, NoSuchMethodException {
        Console console = Console.getInstance();
        List<Command> commandList = new ArrayList<>();
        commandList.add(new FindAllCommand(1, "1. Voir tous les " + menuName, connection, model));
        commandList.add(new FindOneCommand(2, "2. Voir un(e) "+ menuName, connection, model));
        commandList.add(new AddModelCommand(3, "3. Ajouter un(e) "+ menuName, connection, model));
        commandList.add(new UpdateModelCommand(4, "4. Modifier un(e) "+ menuName, connection, model));
        commandList.add(new DeleteModelCommand(5, "5. Supprimer un(e) "+ menuName, connection, model));

        if(model.getClass().getSimpleName().equals("Training")){
            commandList.add(new AttachToTraining(6, "6. Ajouter un(e) Formateur(ice)", connection, model, new Former(connection)));
            commandList.add(new AttachToTraining(7, "7. Ajouter un(e) Stagiaire", connection, model, new Trainee(connection)));
        }
        console.setCommandList(commandList, false);
        }

    public void menu() throws SQLException {
        Console console = Console.getInstance();
        Trainee trainee = new Trainee(connection);
        Former former = new Former(connection);
        Training training = new Training(connection);
        Category category = new Category(connection);

        List<Command> commandList = new ArrayList<>();

        commandList.add(new InitDatabaseCommand(1, "1. Initialiser base de données", connection, null));

        commandList.add(new ManageMenu(2, "2. Gérer les stagiaires",connection, trainee, getInstance()));
        commandList.add(new ManageMenu(3, "3. Gérer les formateurs",connection, former, getInstance()));
        commandList.add(new ManageMenu(4, "4. Gérer les formations",connection, training, getInstance()));
        commandList.add(new ManageMenu(5, "5. Gérer les catégories",connection, category, getInstance()));
        commandList.add(new StartAPIServerCommand(6, "6. Démarrer serveur API", connection, null));

        console.setCommandList(commandList, true);
    }
}
