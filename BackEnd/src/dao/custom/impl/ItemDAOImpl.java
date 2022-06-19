package dao.custom.impl;

import dao.custom.ItemDAO;
import entity.Item;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public ArrayList<Item> search(Connection connection, Item item) throws SQLException, ClassNotFoundException {
        ArrayList<Item> itemDetails = new ArrayList<>();
        if (!item.getItemCode().equals("")) {
            ResultSet rst = CrudUtil.executeQuery(connection, "SELECT * FROM Item WHERE itemCode = ?", item.getItemCode());

            while (rst.next()) {
                itemDetails.add(
                        new Item(
                                rst.getString(1),
                                rst.getString(2),
                                rst.getDouble(3),
                                rst.getInt(4)
                        )
                );
            }
            return itemDetails;

        } else if (!item.getDescription().equals("")) {
            ResultSet rst = CrudUtil.executeQuery(connection, "SELECT * FROM Item WHERE description = ?", item.getDescription());

            while (rst.next()) {
                itemDetails.add(
                        new Item(
                                rst.getString(1),
                                rst.getString(2),
                                rst.getDouble(3),
                                rst.getInt(4)
                        )
                );
            }
            return itemDetails;
        }
        return null;
    }

    @Override
    public String getCount(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT COUNT(itemCode) FROM Item");
        if (rst.next()) {
            return rst.getString(1);
        }
        return "0";
    }

    @Override
    public String getLastCode(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT itemCode FROM Item ORDER BY itemCode DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public ArrayList<Item> getCodeDescriptions(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Item> codeDescriptions = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT itemCode, description FROM Item");
        while (rst.next()) {
            codeDescriptions.add(new Item(
                    rst.getString(1),
                    rst.getString(2)
            ));
        }
        return codeDescriptions;
    }

    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Item> allItems = new ArrayList<>();

        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT * FROM Item");
        while (rst.next()) {
            allItems.add(new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDouble(3),
                    rst.getInt(4)
            ));
        }
        return allItems;
    }

    @Override
    public boolean add(Connection connection, Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection, "INSERT INTO Item VALUES (?,?,?,?)",
                item.getItemCode(),
                item.getDescription(),
                item.getUnitPrice(),
                item.getQtyOnHand()
        );
    }

    @Override
    public boolean update(Connection connection, Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection, "UPDATE Item SET description=?, unitPrice=?, qtyOnHand=? WHERE itemCode=?",
                item.getDescription(),
                item.getUnitPrice(),
                item.getQtyOnHand(),
                item.getItemCode()
        );
    }

    @Override
    public boolean delete(Connection connection, Item item) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection, "DELETE FROM Item WHERE itemCode = ?",
                item.getItemCode()
        );
    }
}
