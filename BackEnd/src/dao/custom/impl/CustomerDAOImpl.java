package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException {
        ResultSet rst  = CrudUtil.executeQuery(connection,"SELECT * FROM Customer");
        ArrayList<Customer> allCustomers = new ArrayList();

        while (rst.next()){
            allCustomers.add(new Customer(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4)
            ));
        }
        return allCustomers;
    }

    @Override
    public Customer search(Connection connection, String cusId) throws SQLException {
        ResultSet rst = CrudUtil.executeQuery(connection, "SELECT * FROM Customer WHERE id=?", cusId);
        if (rst.next()){
            return new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDouble(4));
        }else{
            return null;
        }
    }

    @Override
    public boolean save(Connection connection,Customer customer) throws SQLException {
        return CrudUtil.executeUpdate(connection,"INSERT INTO Customer VALUES(?,?,?,?)",customer.getId(),customer.getName(),customer.getAddress(),customer.getSalary());
    }

    @Override
    public boolean update(Connection connection,Customer customer) throws SQLException {
        return CrudUtil.executeUpdate(connection,"UPDATE Customer SET name=?, address=?, salary=? WHERE id=?", customer.getName(),customer.getAddress(),customer.getSalary(),customer.getId());
    }

    @Override
    public boolean delete(Connection connection,String id) throws SQLException {
        return CrudUtil.executeUpdate(connection,"DELETE FROM Customer WHERE id=?",id);
    }

}
