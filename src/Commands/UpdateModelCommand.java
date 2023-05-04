package Commands;

import Console.Command;
import DAL.DAL;
import database.SqlConnection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Scanner;

public class UpdateModelCommand extends Command<Object, String> {
    public UpdateModelCommand(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
    }

    @Override
    public String apply(Object o) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("ID de votre cible : ");
        Integer id = scanner.nextInt();
        DAL modelToModify = null;
        try {
            modelToModify = model.findOne(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Vous souhaitez modifier cet entité ? Y/N : "+ modelToModify.toString());
        String accept = scanner.next();
        if (accept.equals("Y")){
            Field[] fields = modelToModify.getClass().getDeclaredFields();

            for (Field attribute: fields) {
                if (!attribute.getName().equals("id")) {
                    attribute.setAccessible(true);
                    try {
                        System.out.println(attribute.getName()+"("+ attribute.get(modelToModify) + "): ");
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
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
                        try {
                            attribute.set(modelToModify, t);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                try {
                    modelToModify.update(modelToModify);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            return "Succès";
        }else return null;
    }
}
