package dto;

import java.sql.Date;
import java.util.ArrayList;

public class OrderDTO {
     private String orderId;
     private String cId;
     private Date orderDate;
     private double cost;
     private ArrayList<ItemDetail> details;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String cId, Date orderDate, double cost, ArrayList<ItemDetail> details) {
        this.setOrderId(orderId);
        this.setcId(cId);
        this.setOrderDate(orderDate);
        this.setCost(cost);
        this.setDetails(details);
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

    public ArrayList<ItemDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<ItemDetail> details) {
        this.details = details;
    }
}
