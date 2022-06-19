package business.custom;

import business.SuperBO;
import dto.OrderDTO;
import dto.OrderDetailDTO;

import javax.json.JsonArray;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {
    String getOrderCount(Connection connection) throws SQLException, ClassNotFoundException;

    String getLastId(Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDetailDTO> getOrderDetails(Connection connection) throws SQLException, ClassNotFoundException;

    boolean purchaseOrder(Connection connection, OrderDTO dto, JsonArray orderDetails) throws SQLException, ClassNotFoundException;

    boolean deleteOrder(Connection connection, OrderDTO dto) throws SQLException, ClassNotFoundException;
}
