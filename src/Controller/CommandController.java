package Controller;

import Console.Console;
import Console.Command;
import DAL.DAL;
import database.SqlConnection;
import models.Category;
import models.Former;
import models.Trainee;
import models.Training;
import server.Server;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;

public class CommandController {
    public static void subMenu(DAL obj, String menuName) throws SQLException, NoSuchMethodException {
        Console console = Console.getInstance();
        SqlConnection connection = new SqlConnection();
        connection.connect();
        List<Command> commandList = new ArrayList<>();

        commandList.add(new Command(1, "1. Voir tous les "+ menuName, c -> {
            try {
                System.out.println(findAll(obj));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
        commandList.add(new Command(2, "2. Voir un(e) "+ menuName, c -> {
            try {
                System.out.println(findOne(obj));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
        commandList.add(new Command(3, "3. Ajouter un(e) "+ menuName, c -> {
            try {
                System.out.println(add(obj));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
        commandList.add(new Command(4, "4. Modifier un(e) "+ menuName, c -> {
            try {
                System.out.println(update(obj));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
        commandList.add(new Command(5, "5. Supprimer un(e) "+ menuName, c -> {
            try {
                System.out.println(delete(obj));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));

        if(obj.getClass().getSimpleName().equals("Training")){
            commandList.add(new Command(6, "6. Ajouter un(e) Formateur(ice)", c -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("ID de votre formation cible : ");
                Integer id_training = scanner.nextInt();
                try {
                    if(obj.findOne(id_training) == null){
                        System.out.println("Cette entité n'existe pas");
                    } else {
                        Training training = (Training) obj.findOne(id_training);
                        System.out.println("La formation séléctionné : "+training);
                        Long former_id = scanner.nextLong();
                        training.attachFormer(former_id);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }));
            commandList.add(new Command(7, "7. Ajouter un(e) Stagiaire", c -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("ID de votre formation cible : ");
                Integer id_training = scanner.nextInt();
                try {
                    if(obj.findOne(id_training) == null){
                        System.out.println("Cette entité n'existe pas");
                    } else {
                        Training training = (Training) obj.findOne(id_training);
                        System.out.println("La formation séléctionné : "+training);
                        Long trainee_id = scanner.nextLong();
                        training.attachTrainee(trainee_id);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }));
        }


        console.setCommandList(commandList, false);


    }

    public static void menu() throws SQLException {
        Console console = Console.getInstance();
        SqlConnection connection = new SqlConnection();
        connection.connect();

        List<Command> commandList = new ArrayList<>();

        Command command1 = new Command(1, "1. Initialiser base", c -> {
            CommandController.initDatabase();
        });
        commandList.add(command1);

        Command command2 = new Command<>(2, "2. Gérer les stagiaires", c -> {
            DAL trainee = new Trainee(connection);
            try {
                CommandController.subMenu(trainee, "Stagiaires");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        commandList.add(command2);

        Command command3 = new Command<>(3, "3. Gérer les formateurs", c -> {
            DAL former = new Former(connection);
            try {
                CommandController.subMenu(former, "Formateurs");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        commandList.add(command3);

        Command command4 = new Command<>(4, "4. Gérer les formations", c -> {
            DAL training = new Training(connection);

            try {
                CommandController.subMenu(training, "Formations");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        commandList.add(command4);

        Command command5 = new Command<>(5, "5. Gérer les catégories", c -> {
            DAL category = new Category(connection);

            try {
                CommandController.subMenu(category, "Catégorie");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        commandList.add(command5);

        commandList.add(new Command(6, "6. Démarrer serveur API", c -> {
            try {
                Server.startServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));

        console.setCommandList(commandList, true);
    }

    public static void initDatabase(){
        SqlConnection connection = new SqlConnection();
        try {
            connection.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            connection.initDatabase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String findAll(DAL model) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return model.findAll().toString();
    }

    private static String findOne(DAL model) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
    private static String add(DAL model) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class<?> modelClass = model.getClass();
        Scanner scanner = new Scanner(System.in);
        Field[] fields = model.getClass().getDeclaredFields();
        SqlConnection connection = new SqlConnection();
        Object obj = modelClass.getConstructor(SqlConnection.class).newInstance(connection);

        for (Field attribute: fields) {
            if (!attribute.getName().equals("id")) {
                System.out.println(attribute.getName() + ": ");
                attribute.setAccessible(true);
                Class<?> type = attribute.getType();

                String t = scanner.next();
                if(type.equals(Date.class)){
                    try {
                        Date d = Date.from(LocalDate.parse(t).atStartOfDay().toInstant(ZoneOffset.UTC));
                        attribute.set(obj, d);
                    } catch (Exception e){
                        return "Veuillez entrez une date correcte";
                    }
                }else if(type.equals(Integer.class) || type.equals(int.class)){
                    try{
                        attribute.set(obj, Integer.parseInt(t));
                    } catch (Exception e){
                        return "Veuillez entrez une nombre correcte";
                    }

                } else if (type.equals(Long.class) || type.equals(long.class)) {
                    try {
                        attribute.set(obj, Long.parseLong(t));
                    }catch (Exception e){
                        return "Veuillez entrez une nombre correcte";
                    }
                } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                    try {
                        Boolean b = Boolean.parseBoolean(t);
                        attribute.set(obj, b);
                    }catch (Exception e){
                        return "Veuillez entrez un booléen correcte (true/ false)";
                    }
                } else{
                    if(t == null) return "Saisie incorrecte";
                    attribute.set(obj, t);
                }
            }
        }
        model.create((DAL)obj);

        return "Succès ! ";
    }

    private static String update(DAL model) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID de votre cible : ");
        Integer id = scanner.nextInt();
        DAL modelToModify = model.findOne(id);
        System.out.println("Vous souhaitez modifier cet entité ? Y/N : "+ modelToModify.toString());
        String accept = scanner.next();
        if (accept.equals("Y")){
            Field[] fields = modelToModify.getClass().getDeclaredFields();

            for (Field attribute: fields) {
                if (!attribute.getName().equals("id")) {
                    attribute.setAccessible(true);
                    System.out.println(attribute.getName()+"("+ attribute.get(modelToModify) + "): ");
                    Class<?> type = attribute.getType();

                    String t = scanner.next();
                    if(type.equals(Date.class)){
                        try {
                            Date d = Date.from(LocalDate.parse(t).atStartOfDay().toInstant(ZoneOffset.UTC));
                            attribute.set(modelToModify, d);
                        } catch (Exception e){
                            return "Veuillez entrez une date correcte";
                        }
                    }else if(type.equals(Integer.class) || type.equals(int.class)){
                        try{
                            attribute.set(modelToModify, Integer.parseInt(t));
                        } catch (Exception e){
                            return "Veuillez entrez une nombre correcte";
                        }

                    } else if (type.equals(Long.class) || type.equals(long.class)) {
                        try {
                            attribute.set(modelToModify, Long.parseLong(t));
                        }catch (Exception e){
                            return "Veuillez entrez une nombre correcte";
                        }
                    } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                        try {
                            Boolean b = Boolean.parseBoolean(t);
                            attribute.set(modelToModify, b);
                        }catch (Exception e){
                            return "Veuillez entrez un booléen correcte (true/ false)";
                        }
                    } else{
                        if(t == null) return "Saisie incorrecte";
                        attribute.set(modelToModify, t);
                    }
                }
                    modelToModify.update(modelToModify);
                }
            return "Succès";
        }else return null;
    }

    private static String delete(DAL model) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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
            model.delete(id);
            return "Succès";
        }
        else return "Retour au menu";

    }

    public static void quit(){
        Console console = Console.getInstance();
        System.out.println("Good bye ! :)");
        console.setRunning(false);
    }
}
