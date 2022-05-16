package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT  * FROM Item");
        ArrayList<Item> ids = new ArrayList<>();
        while(rst.next()){
            ids.add(new Item(rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4))
            );
        }
        return ids;
    }

    @Override
    public Item search(Connection connection, String id) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery(connection,"SELECT  * FROM Item WHERE code=?",id);
        if (rst.next()){
            return new Item(rst.getString(1),rst.getString(2),rst.getInt(3),rst.getDouble(4));
        }else {
            return null;
        }
    }

    public boolean save(Connection connection, Item item) throws SQLException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO Item VALUES(?,?,?,?)",item.getCode(),item.getDescription(),item.getQtyOnHand(),item.getUnitPrice());
    }

    public boolean update(Connection connection,Item item) throws SQLException {
        return CrudUtil.executeUpdate(connection,"UPDATE Item SET description=?, qtyOnHand=?, unitPrice=? WHERE code=?",item.getDescription(),item.getQtyOnHand(),item.getUnitPrice(),item.getCode());
    }

    public boolean delete(Connection connection,String id) throws SQLException {
        return  CrudUtil.executeUpdate(connection,"DELETE FROM Item WHERE code=?",id);
    }
}
