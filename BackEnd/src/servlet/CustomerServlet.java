package servlet;

import bo.custom.CustomerBO;
import bo.custom.impl.CustomerBOImpl;
import dao.CrudUtil;
import dao.custom.impl.CustomerDAOImpl;
import dto.CustomerDTO;
import entity.Customer;

import javax.annotation.Resource;
import javax.json.*;
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
import java.util.ArrayList;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    CustomerBO customerBO = new CustomerBOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            Connection connection = dataSource.getConnection();
            String option = req.getParameter("option");
            String searchId = req.getParameter("cusID");

            switch (option) {
                case "GETALL":
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                    for (CustomerDTO dto : customerBO.getAllCustomers(connection)) {
                        JsonObjectBuilder obj = Json.createObjectBuilder();
                        obj.add("id",dto.getCusId());
                        obj.add("name",dto.getCusName());
                        obj.add("address",dto.getCusAddress());
                        obj.add("salary",dto.getSalary());
                        arrayBuilder.add(obj.build());
                    }
                    /*ResultSet rst = CrudUtil.executeQuery(connection,"SELECT * FROM Customer");
                    System.out.println("ResultSet "+rst);
                    resp.setContentType("application/json");

                    while (rst.next()) {
                        String id = rst.getString(1);
                        String name = rst.getString(2);
                        String address = rst.getString(3);
                        double salary = rst.getDouble(4);

                        JsonObjectBuilder obj = Json.createObjectBuilder();
                        obj.add("id", id);
                        obj.add("name", name);
                        obj.add("address", address);
                        obj.add("salary", salary);
                        arrayBuilder.add(obj.build());
                    }*/
                    //Generate a custom response
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    response.add("status", 200);
                    response.add("message", "Done");
                    response.add("data", arrayBuilder.build());
                    writer.print(response.build());
                    break;

                case "SEARCH":
                    CustomerDTO dto = customerBO.searchCustomer(connection, searchId);
                    JsonArrayBuilder arrayBuilder2 = Json.createArrayBuilder();
                    if (dto!=null){
                        JsonObjectBuilder obj = Json.createObjectBuilder();
                        obj.add("id", dto.getCusId());
                        obj.add("name", dto.getCusName());
                        obj.add("address", dto.getCusAddress());
                        obj.add("salary", dto.getSalary());
                        arrayBuilder2.add(obj.build());

                        //Generate a custom response
                        JsonObjectBuilder response2 = Json.createObjectBuilder();
                        response2.add("status", 200);
                        response2.add("message", "Done");
                        response2.add("data", arrayBuilder2.build());
                        writer.print(response2.build());
                    } else {
                        //Generate a custom response
                        JsonObjectBuilder response2 = Json.createObjectBuilder();
                        response2.add("status", 400);
                        response2.add("message", "No results match your search");
                        response2.add("data", arrayBuilder2.build());
                        writer.print(response2.build());
                        resp.setStatus(HttpServletResponse.SC_OK);
                    }
                    break;
            }
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerID = req.getParameter("customerID"); // name value from the input field
        String customerName = req.getParameter("customerName");
        String customerAddress = req.getParameter("customerAddress");
        String salary = req.getParameter("customerSalary");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = dataSource.getConnection();
            /*PreparedStatement pstm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?)");
            pstm.setObject(1, customerID);
            pstm.setObject(2, customerName);
            pstm.setObject(3, customerAddress);
            pstm.setObject(4, salary);
            boolean b = pstm.executeUpdate() > 0;*/
            boolean b = customerBO.saveCustomer(connection, new CustomerDTO(customerID, customerName, customerAddress, Double.parseDouble(salary)));

            if (b) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 200);
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            }
            connection.close();
        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());

            resp.setStatus(HttpServletResponse.SC_OK); //200
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String address = jsonObject.getString("address");
        String salary = jsonObject.getString("salary");

        resp.setContentType("application/json");
        try {
            Connection connection = dataSource.getConnection();
            /*PreparedStatement pstm = connection.prepareStatement("UPDATE Customer SET name=?,address=?,salary=? WHERE id=?");
            pstm.setObject(1, name);
            pstm.setObject(2, address);
            pstm.setObject(3, salary);
            pstm.setObject(4, id);
            boolean b = pstm.executeUpdate() > 0;*/

            boolean b = customerBO.updateCustomer(connection, new CustomerDTO(id, name, address, Double.parseDouble(salary)));


            if (b) {
                //writer.write("Customer Updated");
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", 200);
                response.add("message", "Updated Successfully");
                response.add("data", "");
                writer.print(response.build());
            } else {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", 400);
                response.add("message", "Error");
                response.add("data", "Wrong ID");
                writer.print(response.build());
            }
            connection.close();
        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 500);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            resp.setStatus(200);
            writer.print(response.build());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerID = req.getParameter("CusID");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = dataSource.getConnection();
            /*PreparedStatement pstm = connection.prepareStatement("DELETE FROM Customer WHERE id=?");
            pstm.setObject(1, customerID);

            boolean b = pstm.executeUpdate() > 0;*/

            boolean b = customerBO.deleteCustomer(connection, customerID);


            if (b) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Deleted Successfully");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());

            } else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "Wrong Id Inserted");
                objectBuilder.add("message", "");
                writer.print(objectBuilder.build());
            }

            connection.close();
        } catch (SQLException throwables) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status", 400);
            objectBuilder.add("message", "Error");
            objectBuilder.add("data", throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
            resp.setStatus(HttpServletResponse.SC_OK); //200
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}