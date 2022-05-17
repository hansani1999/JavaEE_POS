package bo.custom;

import dto.ItemDTO;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO {
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException;
    public ItemDTO searchItem(Connection connection, String id) throws SQLException;
    public boolean saveItem(Connection connection, ItemDTO dto) throws SQLException;
    public boolean updateItem(Connection connection, ItemDTO dto) throws SQLException;
    public boolean deleteItem(Connection connection,String id) throws SQLException;
}
