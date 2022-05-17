package dao.custom;

import dao.CrudDAO;
import entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<Connection, OrderDetail,String> {
    public ArrayList<OrderDetail> searchOrderDetails(Connection connection, String s) throws SQLException;
}
