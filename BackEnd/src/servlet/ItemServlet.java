package servlet;

import bo.custom.ItemBO;
import bo.custom.impl.ItemBOImpl;
import dao.CrudUtil;
import dto.ItemDTO;

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

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    ItemBO itemBO = new ItemBOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            Connection connection = dataSource.getConnection();
            String option = req.getParameter("option");
            String searchId = req.getParameter("itemCode");
            //System.out.println(searchId);

            switch (option) {
                case "GETALL":
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    resp.setContentType("application/json");
                    /*PreparedStatement pstm = connection.prepareStatement("SELECT * FROM Item");
                    ResultSet rst = pstm.executeQuery();

                    while (rst.next()) {
                        String code = rst.getString(1);
                        String description = rst.getString(2);
                        String qtyOnHand = rst.getString(3);
                        double unitPrice = rst.getDouble(4);

                        JsonObjectBuilder obj = Json.createObjectBuilder();
                        obj.add("code", code);
                        obj.add("description", description);
                        obj.add("qtyOnHand", qtyOnHand);
                        obj.add("unitPrice", unitPrice);
                        arrayBuilder.add(obj.build());
                    }*/
                    ArrayList<ItemDTO> allItems = itemBO.getAllItems(connection);
                    for (ItemDTO item : allItems) {
                        JsonObjectBuilder obj = Json.createObjectBuilder();
                        obj.add("code", item.getItemCode());
                        obj.add("description", item.getDescription());
                        obj.add("qtyOnHand", item.getQtyOnHand());
                        obj.add("unitPrice", item.getUnitPrice());
                        arrayBuilder.add(obj.build());
                    }
                    //Generate a custom response
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    response.add("status", 200);
                    response.add("message", "Done");
                    response.add("data", arrayBuilder.build());
                    writer.print(response.build());
                    break;

                case "SEARCH":
                    JsonArrayBuilder arrayBuilder2 = Json.createArrayBuilder();
                    resp.setContentType("application/json");
                    ItemDTO dto = itemBO.searchItem(connection, searchId);
                    if (dto!=null){
                        JsonObjectBuilder obj = Json.createObjectBuilder();
                        obj.add("code",dto.getItemCode());
                        obj.add("description", dto.getDescription());
                        obj.add("qtyOnHand", dto.getQtyOnHand());
                        obj.add("unitPrice", dto.getUnitPrice());
                        arrayBuilder2.add(obj.build());

                        //Generate a custom response
                        JsonObjectBuilder response2 = Json.createObjectBuilder();
                        response2.add("status", 200);
                        response2.add("message", "Done");
                        response2.add("data", arrayBuilder2.build());
                        writer.print(response2.build());
                    }else {
                        //Generate a custom response
                        JsonObjectBuilder response2 = Json.createObjectBuilder();
                        response2.add("status", 400);
                        response2.add("message", "No results match your search");
                        response2.add("data", arrayBuilder2.build());
                        writer.print(response2.build());
                        resp.setStatus(HttpServletResponse.SC_OK);
                    }
                    break;
                    /*PreparedStatement pstm2 = connection.prepareStatement("SELECT * FROM Item WHERE code=?");
                    pstm2.setString(1, searchId);
                    ResultSet rst2 = pstm2.executeQuery();*/
                    /*if(rst2.next()) {
                        String code = rst2.getString(1);
                        String description = rst2.getString(2);
                        String qtyOnHand = rst2.getString(3);
                        double unitPrice = rst2.getDouble(4);

                        JsonObjectBuilder obj = Json.createObjectBuilder();
                        obj.add("code", code);
                        obj.add("description", description);
                        obj.add("qtyOnHand", qtyOnHand);
                        obj.add("unitPrice", unitPrice);
                        arrayBuilder2.add(obj.build());

                        //Generate a custom response
                        JsonObjectBuilder response2 = Json.createObjectBuilder();
                        response2.add("status", 200);
                        response2.add("message", "Done");
                        response2.add("data", arrayBuilder2.build());
                        writer.print(response2.build());
                    }else {
                        //Generate a custom response
                        JsonObjectBuilder response2 = Json.createObjectBuilder();
                        response2.add("status", 400);
                        response2.add("message", "No results match your search");
                        response2.add("data", arrayBuilder2.build());
                        writer.print(response2.build());
                        resp.setStatus(HttpServletResponse.SC_OK);
                    }*/
            }
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("itemCode"); // name value from the input field
        String description = req.getParameter("description");
        String qtyOnHand = req.getParameter("qtyOnHand");
        String unitPrice = req.getParameter("unitPrice");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            Connection connection = dataSource.getConnection();
            boolean b = itemBO.saveItem(connection, new ItemDTO(code, description, Integer.parseInt(qtyOnHand), Double.parseDouble(unitPrice)));

            /*PreparedStatement pstm = connection.prepareStatement("INSERT INTO Item VALUES (?,?,?,?)");
            pstm.setObject(1, code);
            pstm.setObject(2, description);
            pstm.setObject(3, qtyOnHand);
            pstm.setObject(4, unitPrice);
            boolean b = pstm.executeUpdate() > 0;*/

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

        String code = jsonObject.getString("code");
        String description = jsonObject.getString("description");
        String qtyOnHand = jsonObject.getString("qtyOnHand");
        String unitPrice = jsonObject.getString("unitPrice");

        resp.setContentType("application/json");
        try {
            Connection connection = dataSource.getConnection();
           /* PreparedStatement pstm = connection.prepareStatement("UPDATE Item SET description=?,qtyOnHand=?,unitPrice=? WHERE code=?");
            pstm.setObject(1, description);
            pstm.setObject(2, qtyOnHand);
            pstm.setObject(3, unitPrice);
            pstm.setObject(4, code);
            boolean b = pstm.executeUpdate() > 0;*/
            boolean b = itemBO.updateItem(connection, new ItemDTO(code, description, Integer.parseInt(qtyOnHand), Double.parseDouble(unitPrice)));

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
        String itemCode = req.getParameter("itemCode");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = dataSource.getConnection();
            /*PreparedStatement pstm = connection.prepareStatement("DELETE FROM Item WHERE code=?");
            pstm.setObject(1, itemCode);
            boolean b = pstm.executeUpdate() > 0;*/

            boolean b = itemBO.deleteItem(connection, itemCode);

            if (b) {
                //writer.write("Customer Deleted");
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
