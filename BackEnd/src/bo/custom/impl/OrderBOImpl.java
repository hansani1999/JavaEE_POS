package bo.custom.impl;

import bo.custom.OrderBO;
import dao.custom.CustomerDAO;
import dao.custom.ItemDAO;
import dao.custom.OrderDAO;
import dao.custom.OrderDetailDAO;
import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dao.custom.impl.OrderDetailDAOImpl;
import dto.CustomerDTO;
import dto.ItemDTO;
import dto.ItemDetail;
import dto.OrderDTO;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    OrderDAO orderDAO = new OrderDAOImpl();
    OrderDetailDAO orderDetailDAO = new OrderDetailDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();
    ItemDAO itemDAO = new ItemDAOImpl();


    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
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
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
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
    public ArrayList<OrderDTO> getAllOrders(Connection connection) throws SQLException {
        ArrayList<OrderDTO> orders = new ArrayList<>();
        for (Order order : orderDAO.getAll(connection)) {
            orders.add(new OrderDTO(
                    order.getOrderId(),
                    order.getcId(),
                    order.getOrderDate(),
                    order.getCost()
            ));
        }
        return orders;
    }

    @Override
    public ItemDTO searchItem(Connection connection, String itemCode) throws SQLException {
        Item item = itemDAO.search(connection, itemCode);
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
    public CustomerDTO searchCustomer(Connection connection, String customerId) throws SQLException {
        Customer customer = customerDAO.search(connection, customerId);
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
    public boolean placeOrder(Connection connection, OrderDTO dto) throws SQLException {
        Order order = new Order(
                dto.getOrderId(),
                dto.getcId(),
                dto.getOrderDate(),
                dto.getCost()
        );

        connection.setAutoCommit(false);
        boolean isSavedOrder = orderDAO.save(connection,order);
        if (!isSavedOrder) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        ArrayList<ItemDetail> itemDetails = dto.getDetails();
        for (ItemDetail itemDetail : itemDetails) {
            OrderDetail detail = new OrderDetail(dto.getOrderId(),itemDetail.getItemCode(),itemDetail.getOrderQty(),itemDetail.getUnitPrice());
            boolean isSavedDetail = orderDetailDAO.save(connection,detail);

            if (!isSavedDetail) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            Item item = itemDAO.search(connection,detail.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand()-detail.getQty());
            boolean isUpdatedQty = itemDAO.update(connection,item);
            if (!isUpdatedQty) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;

    }

    @Override
    public String generateOrderId(Connection connection) throws SQLException {
        return null;
    }
}
