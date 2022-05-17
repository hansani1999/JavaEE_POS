package bo.custom.impl;

import bo.custom.ItemBO;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import dto.ItemDTO;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = new ItemDAOImpl();

    @Override
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException {
        ArrayList<ItemDTO> allItems = new ArrayList();
        for (Item item : itemDAO.getAll(connection)) {
            allItems.add(new ItemDTO(
                    item.getCode(),
                    item.getDescription(),
                    item.getQtyOnHand(),
                    item.getUnitPrice()
            ));
        }
        return allItems;
    }

    @Override
    public ItemDTO searchItem(Connection connection, String id) throws SQLException {
        Item item = itemDAO.search(connection, id);
        if (item!=null){
            return new ItemDTO(
                    item.getCode(),
                    item.getDescription(),
                    item.getQtyOnHand(),
                    item.getUnitPrice()
            );
        }else {
            return null;
        }
    }

    @Override
    public boolean saveItem(Connection connection, ItemDTO dto) throws SQLException {
       return itemDAO.save(connection,new Item(dto.getItemCode(),dto.getDescription(),dto.getQtyOnHand(),dto.getUnitPrice()));
    }

    @Override
    public boolean updateItem(Connection connection, ItemDTO dto) throws SQLException {
        return itemDAO.update(connection,new Item(dto.getItemCode(),dto.getDescription(),dto.getQtyOnHand(),dto.getUnitPrice()));
    }

    @Override
    public boolean deleteItem(Connection connection, String id) throws SQLException {
        return itemDAO.delete(connection,id);
    }
}
