package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public ArrayList<OrderDetail> getAll(Connection connection) throws SQLException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public OrderDetail search(Connection connection, String s) throws SQLException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean save(Connection connection, OrderDetail orderDetail) throws SQLException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO `Order Detail` VALUES(?,?,?,?)",orderDetail.getOrderId(),orderDetail.getItemCode(),orderDetail.getQty(),orderDetail.getPrice());
    }

    @Override
    public boolean update(Connection connection, OrderDetail orderDetail) throws SQLException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public boolean delete(Connection connection, String s) throws SQLException {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<OrderDetail> searchOrderDetails(Connection connection, String s) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT * FROM `Order` WHERE orderId=?");
        ArrayList<OrderDetail> orderDetailList = new ArrayList();
        while (rst.next()){
            orderDetailList.add(new OrderDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)

            ));
        }
        return orderDetailList;
    }
}
