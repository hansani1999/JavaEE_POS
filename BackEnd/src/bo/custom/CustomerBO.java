package bo.custom;

import dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO{
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException;
    public boolean saveCustomer(Connection connection,CustomerDTO dto) throws SQLException;
    public CustomerDTO searchCustomer(Connection connection,String id) throws SQLException;
    public boolean updateCustomer(Connection connection, CustomerDTO dto) throws SQLException;
    public boolean deleteCustomer(Connection connection,String id) throws SQLException;
}
