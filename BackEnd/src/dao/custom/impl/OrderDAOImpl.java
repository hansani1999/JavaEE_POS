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
                    rst.getDouble(6)
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
}
