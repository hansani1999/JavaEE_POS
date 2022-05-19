package bo.custom;

import dto.CustomerDTO;
import dto.ItemDTO;
import dto.OrderDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO {
    ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;

    ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException;

    ItemDTO searchItem(Connection connection,String itemCode) throws SQLException;

    CustomerDTO searchCustomer(Connection connection,String customerId) throws SQLException;

    boolean placeOrder(Connection connection,OrderDTO order) throws SQLException;

    String generateOrderId(Connection connection) throws SQLException;
}
