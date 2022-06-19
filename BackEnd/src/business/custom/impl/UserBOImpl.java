package business.custom.impl;

import business.custom.UserBO;
import dao.DAOFactory;
import dao.custom.UserDAO;
import dto.UserDetailDTO;
import entity.UserDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactoryInstance().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public ArrayList<UserDetailDTO> getDetails(Connection connection, UserDetailDTO dto) throws SQLException, ClassNotFoundException {
        ArrayList<UserDetailDTO> userDetails = new ArrayList<>();
        for (UserDetails u : userDAO.getUserDetails(connection, new UserDetails(dto.getEmail(), dto.getPassword()))) {
            userDetails.add(new UserDetailDTO(
                    u.getEmail(),
                    u.getPassword()
            ));
        }
        return userDetails;
    }

    @Override
    public boolean addUser(Connection connection, UserDetailDTO dto) throws SQLException, ClassNotFoundException {
        return userDAO.add(connection, new UserDetails(
                dto.getEmail(),
                dto.getPassword()
        ));
    }
}
