package business.custom;

import business.SuperBO;
import dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {
    ArrayList<CustomerDTO> search(Connection connection, CustomerDTO dto) throws ClassNotFoundException, SQLException;

    String isDuplicateContact(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException;

    String getCustomerCount(Connection connection) throws SQLException, ClassNotFoundException;

    String getLastId(Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getIdNames(Connection connection) throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException;

    boolean addCustomer(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteCustomer(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException;
}
