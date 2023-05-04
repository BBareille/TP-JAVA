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

public class AddModelCommand extends Command<Object, String> {

    private DAL model;

    public AddModelCommand(Integer id, String displayName, SqlConnection connection, DAL model) {
        super(id, displayName, connection, model);
        this.model = model;
    }

    @Override
    public String apply(Object o) {
        Class<?> modelClass = model.getClass();
        Scanner scanner = new Scanner(System.in);
        Field[] fields = model.getClass().getDeclaredFields();
        Object obj;
        try {
            obj = modelClass.getConstructor(SqlConnection.class).newInstance(connection);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

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
                        System.out.println("Veuillez entrez une date correcte");
                    }
                }else if(type.equals(Integer.class) || type.equals(int.class)){
                    try{
                        attribute.set(obj, Integer.parseInt(t));
                    } catch (Exception e){
                        System.out.println("Veuillez entrez une nombre correcte");
                    }

                } else if (type.equals(Long.class) || type.equals(long.class)) {
                    try {
                        attribute.set(obj, Long.parseLong(t));
                    }catch (Exception e){
                        System.out.println("Veuillez entrez une nombre correcte");
                    }
                } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                    try {
                        Boolean b = Boolean.parseBoolean(t);
                        attribute.set(obj, b);
                    }catch (Exception e){
                        System.out.println("Veuillez entrez un booléen correcte (true/ false)");
                    }
                } else{
                    if(t == null) return "Saisie incorrecte";
                    try {
                        attribute.set(obj, t);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        try {
            model.create((DAL)obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return "Succès ! ";
    }
}
