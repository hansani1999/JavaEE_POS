package dto;

import java.sql.Date;

public class OrderDTO {
    private String orderId;
    private String cId;
    private Date orderDate;
    private double cost;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String cId, Date orderDate, double cost) {
        this.setOrderId(orderId);
        this.setcId(cId);
        this.setOrderDate(orderDate);
        this.setCost(cost);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
