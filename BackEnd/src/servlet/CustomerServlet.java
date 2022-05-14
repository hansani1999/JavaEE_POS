package servlet;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Customer");
            ResultSet rst = pstm.executeQuery();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            resp.addHeader("Access-Control-Allow-Origin","*");

            while (rst.next()){
                String id = rst.getString(1);
                String name = rst.getString(2);
                String address = rst.getString(3);
                double salary = rst.getDouble(4);

                System.out.println(id+" "+name+" "+address+" "+salary);
                JsonObjectBuilder obj = Json.createObjectBuilder();
                obj.add("id",id);
                obj.add("name",name);
                obj.add("address",address);
                obj.add("salary",salary);
                arrayBuilder.add(obj.build());
            }


            //Generate a custom response
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",200);
            response.add("message","Done");
            response.add("data",arrayBuilder.build());

            writer.print(response.build());

            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}