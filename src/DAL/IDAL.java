package DAL;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface IDAL<T> {

    List<T> findAll() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
    T findOne(long id) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
    T create(T object) throws SQLException, IllegalAccessException;
    T update(T object) throws SQLException, IllegalAccessException;
    void delete(long id) throws SQLException;

}
