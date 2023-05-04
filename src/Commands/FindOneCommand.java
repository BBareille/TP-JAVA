package Commands;

import Console.Command;
import DAL.DAL;
import database.SqlConnection;

import java.util.Scanner;

public class FindOneCommand extends Command<Object, Object> {

    public FindOneCommand(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
    }

    @Override
    public Object apply(Object o) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID de votre cible : ");
        try {
            Integer id = Integer.valueOf(scanner.next());
            if (model.findOne(id) == null) {
                System.out.println("Cette entité n'existe pas");
            } else
                System.out.println(model.findOne(id).toString());
        }catch (Exception e) {
            System.out.println("Veuillez entrez un nombre");
        }
        return null;
    }
}
