package dao.custom.impl;

import dao.custom.CustomerDAO;
import entity.Customer;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<Customer> search(Connection connection, Customer customer) throws ClassNotFoundException, SQLException {
        ArrayList<Customer> customerDetails = new ArrayList<>();

        if (!customer.getCustomerId().equals("")) {
            ResultSet rst = CrudUtil.executeQuery(connection, "SELECT * FROM Customer WHERE customerId = ?", customer.getCustomerId());

            while (rst.next()) {
                customerDetails.add(
                        new Customer(
                                rst.getString(1),
                                rst.getString(2),
                                rst.getString(3),
                                rst.getInt(4)
                        )
                );
            }
            return customerDetails;

        } else if (!customer.getCustomerName().equals("")) {
            ResultSet rst = CrudUtil.executeQuery(connection, "SELECT * FROM Customer WHERE customerName = ?", customer.getCustomerName());

            while (rst.next()) {
                customerDetails.add(
                        new Customer(
                                rst.getString(1),
                                rst.getString(2),
                                rst.getString(3),
                                rst.getInt(4)
                        )
                );
            }
            return customerDetails;
        }
        return null;
    }

    @Override
    public String isDuplicateContact(Connection connection, Customer customer) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT customerId, customerContact FROM Customer");

        while (rst.next()) {
            if (rst.getString(2).equals(String.valueOf(customer.getCustomerContact()))) {
                if (rst.getString(1).equals(customer.getCustomerId())) {
                    return "Match";
                } else {
                    return "Duplicate";
                }
            }
        }
//        CrudUtil.getConnection().close();
        return "Unique";
    }

    @Override
    public String getCount(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT COUNT(customerId) FROM Customer");
        if (rst.next()) {
            return rst.getString(1);
        }
        return "0";
    }

    @Override
    public String getLastId(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT customerId FROM Customer ORDER BY customerId DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public ArrayList<Customer> getIdNames(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customerIdNames = new ArrayList<>();

        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT customerId, customerName FROM Customer");

        while (rst.next()) {
            customerIdNames.add(new Customer(
                    rst.getString(1),
                    rst.getString(2)
            ));
        }
        return customerIdNames;
    }

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList<>();

        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT * FROM Customer");
        while (rst.next()) {
            allCustomers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getInt(4)
            ));
        }
        return allCustomers;
    }

    @Override
    public boolean add(Connection connection, Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection, "INSERT INTO Customer VALUES (?,?,?,?)",
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getCustomerAddress(),
                customer.getCustomerContact()
        );
    }

    @Override
    public boolean update(Connection connection, Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection, "UPDATE Customer SET customerName=?,customerAddress=?,customerContact=? WHERE customerId=?",
                customer.getCustomerName(),
                customer.getCustomerAddress(),
                customer.getCustomerContact(),
                customer.getCustomerId()
        );
    }

    @Override
    public boolean delete(Connection connection, Customer customer) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection, "DELETE FROM Customer WHERE customerId = ?",
                customer.getCustomerId()
        );
    }
}
