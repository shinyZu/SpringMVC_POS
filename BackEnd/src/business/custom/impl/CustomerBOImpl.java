package business.custom.impl;

import business.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactoryInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> search(Connection connection, CustomerDTO dto) throws ClassNotFoundException, SQLException {
        ArrayList<CustomerDTO> customerDetails = new ArrayList<>();
        for (Customer c : customerDAO.search(connection, new Customer(dto.getCustomerId(), dto.getCustomerName()))) {
            customerDetails.add(new CustomerDTO(
                    c.getCustomerId(),
                    c.getCustomerName(),
                    c.getCustomerAddress(),
                    c.getCustomerContact()
            ));
        }
//        CrudUtil.getConnection().close();
        return customerDetails;
    }

    @Override
    public String isDuplicateContact(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.isDuplicateContact(connection, new Customer(dto.getCustomerId(), dto.getCustomerContact()));
    }

    @Override
    public String getCustomerCount(Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.getCount(connection);
    }

    @Override
    public String getLastId(Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.getLastId(connection);
    }

    @Override
    public ArrayList<CustomerDTO> getIdNames(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> idNames = new ArrayList<>();
        for (Customer c : customerDAO.getIdNames(connection)) {
            idNames.add(new CustomerDTO(
                    c.getCustomerId(),
                    c.getCustomerName()
            ));
        }
        return idNames;
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer c : customerDAO.getAll(connection)) {
            allCustomers.add(new CustomerDTO(
                    c.getCustomerId(),
                    c.getCustomerName(),
                    c.getCustomerAddress(),
                    c.getCustomerContact()
            ));
        }
        return allCustomers;
    }

    @Override
    public boolean addCustomer(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(connection, new Customer(
                dto.getCustomerId(),
                dto.getCustomerName(),
                dto.getCustomerAddress(),
                dto.getCustomerContact()
        ));

    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(connection, new Customer(
                dto.getCustomerId(),
                dto.getCustomerName(),
                dto.getCustomerAddress(),
                dto.getCustomerContact()
        ));
    }

    @Override
    public boolean deleteCustomer(Connection connection, CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(connection, new Customer(dto.getCustomerId()));
    }
}
