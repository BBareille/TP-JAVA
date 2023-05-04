package Commands;

import Console.Command;
import DAL.DAL;
import database.SqlConnection;

import java.sql.SQLException;
import java.util.Scanner;

public class DeleteModelCommand extends Command {
    public DeleteModelCommand(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
    }

    @Override
    public Object apply(Object o) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("L'id de l'entité à supprimer");
        Long id;
        try{
            id = scanner.nextLong();
            DAL modelToDelete = model.findOne(id);
            System.out.println("Entité que vous voulez supprimer : " + modelToDelete);
            System.out.println("Êtes-vous sûr de supprimer ? Y/N");
        }catch (Exception e) {
            return "Entrez un id correcte";
        }



        String deleteConfirm = scanner.next();
        if(deleteConfirm.equals("Y")){
            try {
                model.delete(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return "Succès";
        }
        else return "Retour au menu";
    }
}
