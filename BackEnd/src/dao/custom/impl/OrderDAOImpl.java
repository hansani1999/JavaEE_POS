package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDAO;
import entity.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public ArrayList<Order> getAll(Connection connection) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT * FROM `Order`");

        ArrayList<Order> orders = new ArrayList<>();
        while (rst.next()) {
            orders.add(new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDouble(4)
            ));
        }
        return orders;
    }

    @Override
    public Order search(Connection connection, String id) throws SQLException {
        ResultSet rst =  CrudUtil.executeQuery(connection,"SELECT * FROM `Order` WHERE orderId=?",id);
        if (rst.next()) {
            return new Order(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getDate(3),
                    rst.getDouble(6)
            );
        }else {
            return null;
        }
    }

    @Override
    public boolean save(Connection connection, Order order) throws SQLException {
        return CrudUtil.executeUpdate(connection,"INSERT  INTO `Order` VALUES (?,?,?,?)",order.getOrderId(),order.getcId(),order.getOrderDate(),order.getCost());
    }

    @Override
    public boolean update(Connection connection, Order order) throws SQLException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean delete(Connection connection, String s) throws SQLException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }


    @Override
    public String generateOrderId(Connection connection) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT orderId FROM `Order` ORDER BY orderId DESC LIMIT 1");

        if (rst.next()){

            int tempId = Integer.
                    parseInt(rst.getString(1).split("-")[1]);
            tempId=tempId+1;
            if (tempId<=9){
                return "OID-00"+tempId;
            }else if(tempId<=99){
                return "OID-0"+tempId;
            }else{
                return "OID-"+tempId;
            }

        }else{
            return "OID-001";
        }
    }
}
