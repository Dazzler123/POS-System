package lk.ijse.pos.dto;

public class OrderDetailDTO {
    private String orderID;
    private String itemCode;
    private int orderQty;
    private double discount;

    public OrderDetailDTO(String orderID, String itemCode, int orderQty, double discount) {
        this.orderID = orderID;
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.discount = discount;

    }

    public OrderDetailDTO() {
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderID='" + orderID + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", orderQty='" + orderQty + '\'' +
                ", discount='" + discount + '\'' +
                '}';
    }
}
