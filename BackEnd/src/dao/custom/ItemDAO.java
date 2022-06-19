package dao.custom;

import dao.CrudDAO;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Connection, Item> {
    ArrayList<Item> search(Connection connection, Item item) throws SQLException, ClassNotFoundException;

    String getCount(Connection connection) throws SQLException, ClassNotFoundException;

    String getLastCode(Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<Item> getCodeDescriptions(Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException;

//    boolean add(Connection connection, Item item) throws SQLException, ClassNotFoundException;
//
//    boolean update(Connection connection, Item item) throws SQLException, ClassNotFoundException;
//
//    boolean delete(Connection connection, Item item) throws SQLException, ClassNotFoundException;
}
