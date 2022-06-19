package dao.custom;

import dao.CrudDAO;
import entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Connection, Customer> {
    ArrayList<Customer> search(Connection connection, Customer customer) throws ClassNotFoundException, SQLException;

    String isDuplicateContact(Connection connection, Customer customer) throws SQLException, ClassNotFoundException;

    String getCount(Connection connection) throws SQLException, ClassNotFoundException;

    String getLastId(Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<Customer> getIdNames(Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException;

//    boolean add(Connection connection, Customer customer) throws SQLException, ClassNotFoundException;
//
//    boolean update(Connection connection, Customer customer) throws SQLException, ClassNotFoundException;
//
//    boolean delete(Connection connection, Customer customer) throws SQLException, ClassNotFoundException;
}
