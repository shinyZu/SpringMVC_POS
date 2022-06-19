package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil {

    private static PreparedStatement getPreparedStatement(Connection conn, String sql, Object... args) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = conn.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            pstm.setObject(i + 1, args[i]);
        }
        return pstm;
    }

    public static boolean executeUpdate(Connection conn, String sql, Object... args) throws SQLException, ClassNotFoundException {
        return getPreparedStatement(conn, sql, args).executeUpdate() > 0;
    }

    public static ResultSet executeQuery(Connection conn, String sql, Object... args) throws SQLException, ClassNotFoundException {
        return getPreparedStatement(conn, sql, args).executeQuery();
    }
}
