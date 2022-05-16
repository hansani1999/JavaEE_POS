package bo.custom.impl;

import bo.custom.ItemBO;
import dto.ItemDTO;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    @Override
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public Item searchItem(Connection connection, String id) throws SQLException {
        return null;
    }

    @Override
    public boolean saveItem(Connection connection, ItemDTO dto) throws SQLException {
        return false;
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO dto) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteItem(Connection connection, String id) throws SQLException {
        return false;
    }
}
