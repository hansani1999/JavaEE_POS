package dto;

public class ItemDetail {
    private String itemCode;
    private int orderQty;
    private double unitPrice;

    public ItemDetail() {
    }

    public ItemDetail(String itemCode, int orderQty, double unitPrice) {
        this.setItemCode(itemCode);
        this.setOrderQty(orderQty);
        this.setUnitPrice(unitPrice);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
