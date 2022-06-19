package dao.custom;

import dao.CrudDAO;
import entity.OrderDetails;
import entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderDAO extends CrudDAO<Connection, Orders> {

    String getCount(Connection connection) throws SQLException, ClassNotFoundException;

    String getLastId(Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<Orders> getAll(Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetails> getAllOrderDetails(Connection connection) throws SQLException, ClassNotFoundException;

//    boolean addOrder(Connection connection, Orders orders) throws SQLException, ClassNotFoundException;

    boolean addOrderDetail(Connection connection, OrderDetails detail) throws SQLException, ClassNotFoundException;

//    boolean delete(Connection connection, Orders orders) throws SQLException, ClassNotFoundException;
}
