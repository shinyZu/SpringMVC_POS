package dao.custom.impl;

import dao.custom.UserDAO;
import entity.UserDetails;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    @Override
    public boolean add(Connection connection, UserDetails user) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate(connection, "INSERT INTO UserDetails VALUES (?,?)",
                user.getEmail(),
                user.getPassword()
        );
    }

    @Override
    public boolean update(Connection connection, UserDetails userDetails) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean delete(Connection connection, UserDetails userDetails) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<UserDetails> getUserDetails(Connection connection, UserDetails user) throws SQLException, ClassNotFoundException {
        ArrayList<UserDetails> userDetails = new ArrayList<>();
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT * FROM UserDetails WHERE email = ?", user.getEmail());

        while (rst.next()) {
            userDetails.add(
                    new UserDetails(
                            rst.getString(1),
                            rst.getString(2)
                    )
            );
        }
        return userDetails;
    }
}
