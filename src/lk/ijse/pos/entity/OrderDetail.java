package lk.ijse.pos.entity;

import java.math.BigDecimal;

public class OrderDetail {
    private String OrderID; //primary key
    private String ItemCode;
    private int OrderQty;
    private BigDecimal Discount;

    public OrderDetail(String orderID, String itemCode, int orderQty, BigDecimal discount) {
        OrderID = orderID;
        ItemCode = itemCode;
        OrderQty = orderQty;
        Discount = discount;
    }

    public OrderDetail() {
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public int getOrderQty() {
        return OrderQty;
    }

    public void setOrderQty(int orderQty) {
        OrderQty = orderQty;
    }

    public BigDecimal getDiscount() {
        return Discount;
    }

    public void setDiscount(BigDecimal discount) {
        Discount = discount;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "OrderID='" + OrderID + '\'' +
                ", ItemCode='" + ItemCode + '\'' +
                ", OrderQty=" + OrderQty +
                ", Discount=" + Discount +
                '}';
    }
}
