package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.custom.CustomerDAO;
import dao.custom.impl.CustomerDAOImpl;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = new CustomerDAOImpl();

    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList();
        for (Customer customer : customerDAO.getAll(connection)) {
            allCustomers.add(new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            ));
        }
        return allCustomers;
    }

    @Override
    public boolean saveCustomer(Connection connection, CustomerDTO dto) throws SQLException {
        return customerDAO.save(connection,new Customer(dto.getCusId(),dto.getCusName(),dto.getCusAddress(),dto.getSalary()));
    }

    @Override
    public CustomerDTO searchCustomer(Connection connection, String id) throws SQLException {
        Customer customer = customerDAO.search(connection, id);
        if (customer!=null){
            return new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getAddress(),
                    customer.getSalary()
            );
        }else {
            return null;
        }

    }

    @Override
    public boolean updateCustomer(Connection connection, CustomerDTO dto) throws SQLException {
        return customerDAO.update(connection,new Customer(dto.getCusId(),dto.getCusName(),dto.getCusAddress(),dto.getSalary()));
    }

    @Override
    public boolean deleteCustomer(Connection connection, String id) throws SQLException {
        return customerDAO.delete(connection,id);
    }
}
