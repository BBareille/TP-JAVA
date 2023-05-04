package Commands;

import Console.Command;
import DAL.DAL;
import database.SqlConnection;

import java.util.Scanner;

public class FindOneCommand extends Command<Object, String> {

    public FindOneCommand(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
    }

    @Override
    public String apply(Object o) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID de votre cible : ");
        try {
            Integer id = Integer.valueOf(scanner.next());
            if (model.findOne(id) == null) {
                return "Cette entité n'existe pas";
            } else
                return model.findOne(id).toString();
        }catch (Exception e) {
            return "Veuillez entrez un nombre";
        }
    }
}
