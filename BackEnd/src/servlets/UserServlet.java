package servlets;

import business.BOFactory;
import business.custom.UserBO;
import dto.UserDetailDTO;
import util.JsonUtil;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/user")
public class UserServlet extends HttpServlet {

    private final UserBO userBO = (UserBO) BOFactory.getBOFactoryInstance().getBO(BOFactory.BOTypes.USER);
    @Resource(name = "java:comp/env/jdbc/pos")
    DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObjectBuilder details = Json.createObjectBuilder();
        try {
            Connection connection = ds.getConnection();

            UserDetailDTO userDetailDTO = new UserDetailDTO(req.getParameter("email"), req.getParameter("pwd"));
            ArrayList<UserDetailDTO> userDetails = userBO.getDetails(connection, userDetailDTO);

            if (userDetails != null) {
                for (UserDetailDTO dto : userDetails) {
                    details.add("email", dto.getEmail());
                    details.add("pwd", dto.getPassword());
                }
                resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "UserDetails Retrieved", details.build()));
            }
            connection.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = ds.getConnection();
            UserDetailDTO userDetailDTO = new UserDetailDTO(
                    req.getParameter("email"),
                    req.getParameter("pwd")
            );

            if (userBO.addUser(connection, userDetailDTO)) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_OK, "User Saved Successfully...", "SUCCESS"));
            }
            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            resp.getWriter().print(JsonUtil.generateResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error", e.getLocalizedMessage()));
        }
    }
}
