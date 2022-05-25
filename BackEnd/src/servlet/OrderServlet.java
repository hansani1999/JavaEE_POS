package servlet;

import bo.custom.CustomerBO;
import bo.custom.ItemBO;
import bo.custom.OrderBO;
import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.OrderBOImpl;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dto.*;
import entity.Customer;
import entity.Item;
import entity.Order;

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
import java.sql.SQLException;
import java.text.ParseException;
import java.sql.Date;
import java.util.ArrayList;


@WebServlet(urlPatterns = "/orders")
public class OrderServlet extends HttpServlet {
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    OrderBO orderBO= new OrderBOImpl();
    CustomerBO customerBO = new CustomerBOImpl();
    ItemBO itemBO = new ItemBOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            String option = req.getParameter("option");
            Connection connection = dataSource.getConnection();

            switch (option){
                case "GET_CUS_IDS":
                    JsonArrayBuilder arrayBuilder1 = Json.createArrayBuilder();

                    for (CustomerDTO dto : customerBO.getAllCustomers(connection)) {
                        JsonObjectBuilder obj = Json.createObjectBuilder();
                        obj.add("id",dto.getCusId());
                        arrayBuilder1.add(obj.build());
                    }
                    //Generate a custom response
                    JsonObjectBuilder response1 = Json.createObjectBuilder();
                    response1.add("status", 200);
                    response1.add("message", "Done");
                    response1.add("data", arrayBuilder1.build());
                    writer.print(response1.build());
                    break;
                case "GET_ITEM_IDS":
                    JsonArrayBuilder arrayBuilder2 = Json.createArrayBuilder();
                    ArrayList<ItemDTO> allItems = itemBO.getAllItems(connection);
                    for (ItemDTO item : allItems) {
                        JsonObjectBuilder obj = Json.createObjectBuilder();
                        obj.add("code", item.getItemCode());
                        arrayBuilder2.add(obj.build());
                    }
                    //Generate a custom response
                    JsonObjectBuilder response2 = Json.createObjectBuilder();
                    response2.add("status", 200);
                    response2.add("message", "Done");
                    response2.add("data", arrayBuilder2.build());
                    writer.print(response2.build());
                    break;
                case "GET_ALL_ORDERS":
                    ArrayList<OrderDTO> all = orderBO.getAllOrders(connection);
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    for (OrderDTO dto : all) {
                        JsonObjectBuilder obj = Json.createObjectBuilder();
                        obj.add("orderId",dto.getOrderId());
                        obj.add("cusId",dto.getcId());
                        obj.add("orderDate",(dto.getOrderDate().toString()));
                        obj.add("cost",dto.getCost());
                        arrayBuilder.add(obj.build());
                    }
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    response.add("status", 200);
                    response.add("message", "Done");
                    response.add("data", arrayBuilder.build());
                    writer.print(response.build());
                    break;
                case "GEN_ORDER_ID":
                    String orderId = orderBO.generateOrderId(connection);
                    JsonObjectBuilder response3 = Json.createObjectBuilder();
                    response3.add("status","200");
                    response3.add("message","");
                    response3.add("data",orderId);
                    writer.print(response3.build());
                    break;
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        try {
            Connection connection = dataSource.getConnection();
            ArrayList<ItemDetail> details = new ArrayList<>();
            String orderId = jsonObject.getString("orderId");
            String cusId = jsonObject.getString("cusId");
            String date = jsonObject.getString("orderDate");
            double cost = Double.parseDouble(jsonObject.getString("cost"));
            Date orderDate = Date.valueOf(date);

            for (JsonValue item : jsonObject.getJsonArray("itemList")) {
                String itemCode = item.asJsonObject().getString("itemCode");
                int qty = Integer.parseInt(item.asJsonObject().getString("qty"));
                Double uPrice = Double.parseDouble(item.asJsonObject().getString("uPrice"));
                details.add(new ItemDetail(itemCode,qty,uPrice));
            }

            OrderDTO dto = new OrderDTO(
                    orderId,
                    cusId,
                    orderDate,
                    cost,
                    details
            );
            boolean b = orderBO.placeOrder(connection,dto);
            if (b){
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status",200);
                response.add("message","Order Placed Successfully");
                response.add("data","");
                writer.print(response.build());
            }else {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status",400);
                response.add("message","Error");
                response.add("data","");
                writer.print(response.build());
            }
            connection.close();

        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status",400);
            response.add("message","Error");
            response.add("data",throwables.getLocalizedMessage());
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.print(response.build());
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
