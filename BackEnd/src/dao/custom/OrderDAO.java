package dao.custom;

import dao.CrudDAO;
import entity.Order;

import java.sql.Connection;

public interface OrderDAO extends CrudDAO<Connection, Order,String> {
}
