package servlets;

import business.BOFactory;
import business.custom.PurchaseOrderBO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import util.JsonUtil;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/orders")
public class PurchaseOrderServlet extends HttpServlet {

    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBOFactoryInstance().getBO(BOFactory.BOTypes.PURCHASE_ORDER);
    @Resource(name = "java:comp/env/jdbc/pos")
    DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = ds.getConnection();

            JsonArrayBuilder allOrders = Json.createArrayBuilder();
            JsonObjectBuilder order = Json.createObjectBuilder();

            String option = req.getParameter("option");

            switch (option) {
                case "GET_COUNT":
                    String orderCount = purchaseOrderBO.getOrderCount(connection);
                    resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Orders Counted", orderCount));
                    break;

                case "LAST_ID":
                    String lastId = purchaseOrderBO.getLastId(connection);

                    if (lastId != null) {
                        resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Retrieved Last OrderId...", lastId));

                    } else {
                        resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "No any Orders yet", "null"));
                    }
                    break;

                case "GETALL":
                    ArrayList<OrderDTO> orders = purchaseOrderBO.getAllOrders(connection);
                    if (orders != null) {
                        for (OrderDTO dto : orders) {
                            order.add("orderId", dto.getOrderId());
                            order.add("orderDate", String.valueOf(dto.getOrderDate()));
                            order.add("orderCost", dto.getOrderCost());
                            order.add("discount", dto.getDiscount());
                            order.add("customerId", dto.getCustomerId());

                            allOrders.add(order.build());
                        }
                        resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Received All Orders", allOrders.build()));
                    }
                    break;

                case "GET_DETAILS":
                    JsonObjectBuilder detail = Json.createObjectBuilder();
                    JsonArrayBuilder allDetails = Json.createArrayBuilder();

                    ArrayList<OrderDetailDTO> orderDetails = purchaseOrderBO.getOrderDetails(connection);

                    if (orderDetails != null) {
                        for (OrderDetailDTO dto : orderDetails) {
                            detail.add("orderId", dto.getOrderId());
                            detail.add("itemCode", dto.getItemCode());
                            detail.add("orderQty", dto.getOrderQty());
                            allDetails.add(detail.build());
                        }
                        resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Received All Details", allDetails.build()));
                    }
                    break;
            }
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error Occurred", e.getLocalizedMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject1 = reader.readObject();

        String orderId = jsonObject1.getString("orderId");
        String orderDate = jsonObject1.getString("date");
        String orderCost = jsonObject1.getString("subTotal");
        String discount = jsonObject1.getString("discount");
        String customerId = jsonObject1.getString("customerId");
        JsonArray orderDetails = jsonObject1.getJsonArray("orderDetail");

        OrderDTO orderDTO = new OrderDTO(
                orderId,
                java.sql.Date.valueOf(orderDate),
                Double.parseDouble(orderCost),
                Integer.parseInt(discount),
                customerId
        );
        try {
            Connection connection = ds.getConnection();

            if (purchaseOrderBO.purchaseOrder(connection, orderDTO, orderDetails)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Order Saved Successfully...", ""));

            } else {
                resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_BAD_REQUEST, "Couldn't Save Order...", ""));
            }
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_BAD_REQUEST, "Something Went Wrong...", e.getLocalizedMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = ds.getConnection();
            if (purchaseOrderBO.deleteOrder(connection, new OrderDTO(req.getParameter("orderId")))) {
                resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Order Deleted Successfully...", ""));

            } else {
                resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_BAD_REQUEST, "Couldn't Delete Order..", ""));
            }
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error Occurred While Deleting...", e.getLocalizedMessage()));
        }
    }
}
