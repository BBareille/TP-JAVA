package Commands;

import Console.Command;
import Console.Console;
import DAL.DAL;
import database.SqlConnection;

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
            commandList.add(new AttachToTraining(6, "6. Ajouter un(e) Formateur(ice)", connection, model));
            commandList.add(new AttachToTraining(7, "7. Ajouter un(e) Stagiaire", connection, model));
        }
        return commandList;

//            commandList.add(new Command(6, "6. Ajouter un(e) Formateur(ice)", c -> {
//                Scanner scanner = new Scanner(System.in);
//                System.out.println("ID de votre formation cible : ");
//                Integer id_training = scanner.nextInt();
//                try {
//                    if(obj.findOne(id_training) == null){
//                        System.out.println("Cette entité n'existe pas");
//                    } else {
//                        Training training = (Training) obj.findOne(id_training);
//                        System.out.println("La formation séléctionné : "+training);
//                        Long former_id = scanner.nextLong();
//                        training.attachFormer(former_id);
//                    }
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }));

//            commandList.add(new Command(7, "7. Ajouter un(e) Stagiaire", c -> {
//                Scanner scanner = new Scanner(System.in);
//                System.out.println("ID de votre formation cible : ");
//                Integer id_training = scanner.nextInt();
//                try {
//                    if(obj.findOne(id_training) == null){
//                        System.out.println("Cette entité n'existe pas");
//                    } else {
//                        Training training = (Training) obj.findOne(id_training);
//                        System.out.println("La formation séléctionné : "+training);
//                        Long trainee_id = scanner.nextLong();
//                        training.attachTrainee(trainee_id);
//                    }
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }));



//        commandList.add(new Command(2, "2. Voir un(e) "+ menuName, c -> {
//            try {
//                System.out.println(findOne(obj));
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }));
//        commandList.add(new Command(3, "3. Ajouter un(e) "+ menuName, c -> {
//            try {
//                System.out.println(add(obj));
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }));
//        commandList.add(new Command(4, "4. Modifier un(e) "+ menuName, c -> {
//            try {
//                System.out.println(update(obj));
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }));
//        commandList.add(new Command(5, "5. Supprimer un(e) "+ menuName, c -> {
//            try {
//                System.out.println(delete(obj));
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }));
//
//
//        }
//        return null;
    }
}
