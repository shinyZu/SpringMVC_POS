package dao.custom;

import dao.CrudDAO;
import entity.UserDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserDAO extends CrudDAO<Connection, UserDetails> {

    ArrayList<UserDetails> getUserDetails(Connection connection, UserDetails userDetails) throws SQLException, ClassNotFoundException;
}
