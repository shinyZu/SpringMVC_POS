package business.custom.impl;

import business.custom.PurchaseOrderBO;
import dao.DAOFactory;
import dao.custom.PurchaseOrderDAO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.OrderDetails;
import entity.Orders;

import javax.json.JsonArray;
import javax.json.JsonValue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    PurchaseOrderDAO purchaseOrderDAO = (PurchaseOrderDAO) DAOFactory.getDAOFactoryInstance().getDAO(DAOFactory.DAOTypes.PURCHASE_ORDER);

    @Override
    public String getOrderCount(Connection connection) throws SQLException, ClassNotFoundException {
        return purchaseOrderDAO.getCount(connection);
    }

    @Override
    public String getLastId(Connection connection) throws SQLException, ClassNotFoundException {
        return purchaseOrderDAO.getLastId(connection);
    }

    @Override
    public ArrayList<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDTO> allOrders = new ArrayList<>();
        for (Orders o : purchaseOrderDAO.getAll(connection)) {
            allOrders.add(new OrderDTO(
                    o.getOrderId(),
                    o.getOrderDate(),
                    o.getOrderCost(),
                    o.getDiscount(),
                    o.getCustomerId()
            ));
        }
        return allOrders;
    }

    @Override
    public ArrayList<OrderDetailDTO> getOrderDetails(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailDTO> allOrderDetails = new ArrayList<>();
        for (OrderDetails od : purchaseOrderDAO.getAllOrderDetails(connection)) {
            allOrderDetails.add(new OrderDetailDTO(
                    od.getOrderId(),
                    od.getItemCode(),
                    od.getOrderQty()
            ));
        }
        return allOrderDetails;
    }

    @Override
    public boolean purchaseOrder(Connection connection, OrderDTO dto, JsonArray orderDetails) throws SQLException, ClassNotFoundException {
        connection.setAutoCommit(false);

        Orders orders = new Orders(
                dto.getOrderId(),
                dto.getOrderDate(),
                dto.getOrderCost(),
                dto.getDiscount(),
                dto.getCustomerId()
        );

        if (purchaseOrderDAO.add(connection, orders)) {

            for (JsonValue value : orderDetails) {
                String itemCode = value.asJsonObject().getString("itemCode");
                String qty = value.asJsonObject().getString("qty");

                OrderDetails orderDetail = new OrderDetails(dto.getOrderId(), itemCode, Integer.parseInt(qty));

                if (purchaseOrderDAO.addOrderDetail(connection, orderDetail)) {
//                    connection.commit();
//                    return true;

                } else {
                    connection.rollback();
                    return false;
                }
            }
            connection.commit();
//            connection.setAutoCommit(true);
            return true;

        } else {
            connection.rollback();
            return false;
        }
    }

    @Override
    public boolean deleteOrder(Connection connection, OrderDTO dto) throws SQLException, ClassNotFoundException {
        return purchaseOrderDAO.delete(connection, new Orders(dto.getOrderId()));
    }
}
