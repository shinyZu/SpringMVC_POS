package business.custom;

import business.SuperBO;
import dto.UserDetailDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    ArrayList<UserDetailDTO> getDetails(Connection connection, UserDetailDTO userDetailsDTO) throws SQLException, ClassNotFoundException;

    boolean addUser(Connection connection, UserDetailDTO userDetailsDTO) throws SQLException, ClassNotFoundException;

}
