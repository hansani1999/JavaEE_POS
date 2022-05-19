package dao.custom;

import dao.CrudDAO;
import entity.Order;

import java.sql.Connection;
import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Connection, Order,String> {
    String generateOrderId(Connection connection) throws SQLException;
}
