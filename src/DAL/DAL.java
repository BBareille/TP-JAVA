package DAL;

import database.SqlConnection;
import models.Training;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class DAL<T extends DAL<T>> implements IDAL<T> {

    protected SqlConnection connection;
    public DAL(SqlConnection connection) {
        this.connection = connection;
    }

    @Override
    public List<T> findAll() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class<?> model = this.getClass();
        Field[] attributes = model.getDeclaredFields();
        List<T> listOfModel = new ArrayList<>();

        String sql = "SELECT * FROM "+ model.getSimpleName();
        PreparedStatement statement = this.connection.getConnection().prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()){
            Object obj = model.getConstructor(SqlConnection.class).newInstance(this.connection);

            for (Field attribute: attributes) {
                attribute.setAccessible(true);
                attribute.set(obj, rs.getObject(attribute.getName()));
            }

            listOfModel.add((T)obj);

        }

        statement.close();
        return listOfModel;
    }


    @Override
    public T findOne(long id) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> model = this.getClass();
        Field[] attributes = model.getDeclaredFields();

        String sql = "SELECT * FROM "+ model.getSimpleName() + " WHERE id=?";
        PreparedStatement statement = this.connection.getConnection().prepareStatement(sql);
        statement.setLong(1, id);
        ResultSet rs = statement.executeQuery();
        Object obj = null;
        while(rs.next()){
            obj = model.getConstructor(SqlConnection.class).newInstance(this.connection);

            for (Field attribute: attributes) {
                attribute.setAccessible(true);
                attribute.set(obj, rs.getObject(attribute.getName()));
            }
            System.out.println(obj);
        }

        statement.close();
        return (T) obj;
    }

    @Override
    public T create(T object) throws SQLException, IllegalAccessException {
        Class<?> model = this.getClass();
        Field[] attributes = model.getDeclaredFields();

        StringBuilder query = new StringBuilder("(");
        StringBuilder queryFields = new StringBuilder("(");
        for(Field f : attributes){
            if(!f.getName().equals("id")) {
                queryFields.append(f.getName()+",");
                query.append("?,");
            }
        }
        query.deleteCharAt(query.length() - 1);
        queryFields.deleteCharAt(queryFields.length() - 1);
        query.append(")");
        queryFields.append(")");

        String sql = "INSERT INTO "+ model.getSimpleName()+ queryFields + " VALUES " + query;
        PreparedStatement statement = this.connection.getConnection().prepareStatement(sql,  Statement.RETURN_GENERATED_KEYS);

        int i = 1;
        for(Field f : attributes){
            if(f.getName().equals("id")) continue;

            f.setAccessible(true);
            statement.setObject(i, f.get(object));
            i++;
        }

        if(statement.executeUpdate() == 1){
            long lastId = -1;

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                lastId = Long.parseLong(rs.getString(1));
            }

            object.setId(lastId);
            statement.close();

            return object;
        }else{
            return null;
        }

    }

    @Override
    public T update(T object) throws SQLException, IllegalAccessException {

        Class<?> model = this.getClass();
        Field[] attributes = model.getDeclaredFields();

        StringBuilder queryFields = new StringBuilder("");

        for(Field f: attributes){
            if(!f.getName().equals("id"))
                queryFields.append(f.getName() + " =?,");
        }

        queryFields.deleteCharAt(queryFields.length() - 1);


        String sql = "UPDATE "+ model.getSimpleName()+ " SET " + queryFields + "WHERE id=?";
        PreparedStatement statement = this.connection.getConnection().prepareStatement(sql);


        int i = 1;
        for(Field f : attributes){
            if(f.getName().equals("id")) continue;

            f.setAccessible(true);
            statement.setObject(i, f.get(object));
            i++;
        }

        statement.setObject(i, object.getId());
        statement.executeUpdate();



        return object;
    }

    @Override
    public void delete(long id) throws SQLException {
        String sql = "DELETE FROM "+ this.getClass().getSimpleName()+ " WHERE id=?";
        PreparedStatement statement = connection.getConnection().prepareStatement(sql);
        statement.setLong(1, id);
        statement.execute();

        statement.close();

    }

    protected abstract void setId(Long id);
    protected abstract Long getId();

}
