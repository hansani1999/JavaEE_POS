package bo.custom.impl;

import bo.custom.CustomerBO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    @Override
    public ArrayList<CustomerDTO> getAll(Connection connection) throws SQLException {
        return null;
    }

    @Override
    public boolean saveCustomer(Connection connection, CustomerDTO dto) throws SQLException {
        return false;
    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO dto) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteCustomer(Connection connection, String id) throws SQLException {
        return false;
    }
}
