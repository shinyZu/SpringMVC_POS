package business.custom;

import business.SuperBO;
import dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> searchItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException;

    String getItemCount(Connection connection) throws SQLException, ClassNotFoundException;

    String getLastCode(Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getCodeDescriptions(Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException;

    boolean addItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException;
}
