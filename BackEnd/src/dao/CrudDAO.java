package dao;

import java.sql.SQLException;

public interface CrudDAO<T, U> extends SuperDAO {

    boolean add(T t, U u) throws SQLException, ClassNotFoundException;

    boolean update(T t, U u) throws SQLException, ClassNotFoundException;

    boolean delete(T t, U u) throws SQLException, ClassNotFoundException;
}
