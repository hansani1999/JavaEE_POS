package bo.custom;

import dto.CustomerDTO;
import entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO{
    public ArrayList<CustomerDTO> getAll(Connection connection) throws SQLException;
    public boolean saveCustomer(Connection connection,CustomerDTO dto) throws SQLException;
    public boolean updateCustomer(Connection connection, CustomerDTO dto) throws SQLException;
    public boolean deleteCustomer(Connection connection,String id) throws SQLException;
}
