package dao;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudUtil{
    public static PreparedStatement getPreparedStatement(Connection connection,String sql, Object...args) throws SQLException {
        PreparedStatement pst = connection.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            pst.setObject(i+1,args[i]);
        }
        return pst;
    }

    public static ResultSet executeQuery(Connection connection,String sql, Object...args) throws SQLException {
        ResultSet set = getPreparedStatement(connection,sql, args).executeQuery();
        System.out.println(set);
        return set;
    }

    public static boolean executeUpdate(Connection connection,String sql, Object...args) throws SQLException {
        return getPreparedStatement(connection,sql, args).executeUpdate()>0;
    }

}
