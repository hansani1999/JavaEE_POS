package dto;

public class ItemDTO {
    private String itemCode;
    private String description;
    private int qtyOnHand;
    private double unitPrice;

    public ItemDTO() {
    }

    public ItemDTO(String itemCode, String description, int qtyOnHand, double unitPrice) {
        this.setItemCode(itemCode);
        this.setDescription(description);
        this.setQtyOnHand(qtyOnHand);
        this.setUnitPrice(unitPrice);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
