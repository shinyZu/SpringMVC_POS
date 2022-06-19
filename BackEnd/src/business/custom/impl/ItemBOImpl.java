package business.custom.impl;

import business.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactoryInstance().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> searchItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemDetails = new ArrayList<>();
        for (Item i : itemDAO.search(connection, new Item(dto.getItemCode(), dto.getDescription()))) {
            itemDetails.add(new ItemDTO(
                    i.getItemCode(),
                    i.getDescription(),
                    i.getUnitPrice(),
                    i.getQtyOnHand()
            ));
        }
        return itemDetails;
    }

    @Override
    public String getItemCount(Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.getCount(connection);
    }

    @Override
    public String getLastCode(Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.getLastCode(connection);
    }

    @Override
    public ArrayList<ItemDTO> getCodeDescriptions(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> codeDescriptions = new ArrayList<>();
        for (Item i : itemDAO.getCodeDescriptions(connection)) {
            codeDescriptions.add(new ItemDTO(
                    i.getItemCode(),
                    i.getDescription()
            ));
        }
        return codeDescriptions;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item i : itemDAO.getAll(connection)) {
            allItems.add(new ItemDTO(
                    i.getItemCode(),
                    i.getDescription(),
                    i.getUnitPrice(),
                    i.getQtyOnHand()
            ));
        }
        return allItems;
    }

    @Override
    public boolean addItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.add(connection, new Item(
                dto.getItemCode(),
                dto.getDescription(),
                dto.getUnitPrice(),
                dto.getQtyOnHand()
        ));
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(connection, new Item(
                dto.getItemCode(),
                dto.getDescription(),
                dto.getUnitPrice(),
                dto.getQtyOnHand()
        ));
    }

    @Override
    public boolean deleteItem(Connection connection, ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(connection, new Item(dto.getItemCode()));
    }
}
