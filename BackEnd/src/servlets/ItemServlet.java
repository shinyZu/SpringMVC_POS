package servlets;

import business.BOFactory;
import business.custom.ItemBO;
import dto.ItemDTO;
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

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    private final ItemBO itemBO = (ItemBO) BOFactory.getBOFactoryInstance().getBO(BOFactory.BOTypes.ITEM);
    @Resource(name = "java:comp/env/jdbc/pos")
    DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = ds.getConnection();
            String option = req.getParameter("option");

            JsonObjectBuilder item = Json.createObjectBuilder();
            JsonArrayBuilder allItems = Json.createArrayBuilder();

            switch (option) {
                case "SEARCH":
                    ItemDTO itemDTO = new ItemDTO(req.getParameter("itemCode"), req.getParameter("description"));
                    ArrayList<ItemDTO> itemDetails = itemBO.searchItem(connection, itemDTO);

                    if (itemDetails != null) {
                        for (ItemDTO dto : itemDetails) {
                            item.add("itemCode", dto.getItemCode());
                            item.add("description", dto.getDescription());
                            item.add("unitPrice", String.format("%.2f", dto.getUnitPrice()));
                            item.add("qtyOnHand", dto.getQtyOnHand());
                        }
                        resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Item Search With Code", item.build()));
                    }
                    break;

                case "GET_COUNT":
                    String itemCount = itemBO.getItemCount(connection);
                    resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Items Counted", itemCount));
                    break;

                case "LAST_CODE":
                    String lastCode = itemBO.getLastCode(connection);

                    if (lastCode != null) {
                        resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Retrieved Last ItemCode...", lastCode));

                    } else {
                        resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "No any Items yet", "null"));
                    }
                    break;

                case "GET_CODE_DESCRIP":
                    ArrayList<ItemDTO> customerCodeDescriptions = itemBO.getCodeDescriptions(connection);
                    if (customerCodeDescriptions != null) {
                        for (ItemDTO dto : customerCodeDescriptions) {
                            item.add("itemCode", dto.getItemCode());
                            item.add("description", dto.getDescription());
                            allItems.add(item.build());
                        }
                        resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Received Codes & Descriptions", allItems.build()));
                    }
                    break;

                case "GETALL":
                    ArrayList<ItemDTO> items = itemBO.getAllItems(connection);
                    if (items != null) {
                        for (ItemDTO dto : items) {
                            item.add("itemCode", dto.getItemCode());
                            item.add("description", dto.getDescription());
                            item.add("unitPrice", String.format("%.2f", dto.getUnitPrice()));
                            item.add("qtyOnHand", dto.getQtyOnHand());
                            allItems.add(item.build());
                        }
                        resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Received All Items", allItems.build()));
                    }
                    break;
            }
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error Occurred", e.getLocalizedMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ItemDTO itemDTO = new ItemDTO(
                req.getParameter("itemCode"),
                req.getParameter("description"),
                Double.parseDouble(req.getParameter("unitPrice")),
                Integer.parseInt(req.getParameter("quantity"))
        );

        try {
            Connection connection = ds.getConnection();
            if (itemBO.addItem(connection, itemDTO)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Item Saved Successfully...", ""));
            }
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_BAD_REQUEST, "Something Went Wrong...", e.getLocalizedMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        ItemDTO itemDTO = new ItemDTO(
                jsonObject.getString("itemCode"),
                jsonObject.getString("description"),
                Double.parseDouble(jsonObject.getString("unitPrice")),
                Integer.parseInt(jsonObject.getString("qty"))
        );

        try {
            Connection connection = ds.getConnection();
            if (itemBO.updateItem(connection, itemDTO)) {
                resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Item Updated Successfully...", ""));

            } else {
                resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_BAD_REQUEST, "Error Occurred While Updating...", ""));
            }
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error Occurred While Updating...", ""));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = ds.getConnection();
            if (itemBO.deleteItem(connection, new ItemDTO(req.getParameter("itemCode")))) {
                resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "Item Deleted Successfully...", ""));

            } else {
                resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_BAD_REQUEST, "Invalid Item Code...", ""));
            }
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error Occurred While Deleting...", e.getLocalizedMessage()));
        }
    }
}
