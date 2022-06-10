package lk.ijse.pos.entity;

import java.time.LocalDate;

public class Orders {
    private String OrderID;  //primary key
    private LocalDate OrderDate;
    private String CustID;

    public Orders(String orderID, LocalDate orderDate, String custID) {
        OrderID = orderID;
        OrderDate = orderDate;
        CustID = custID;
    }

    public Orders() {
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public LocalDate getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        OrderDate = orderDate;
    }

    public String getCustID() {
        return CustID;
    }

    public void setCustID(String custID) {
        CustID = custID;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "OrderID='" + OrderID + '\'' +
                ", OrderDate=" + OrderDate +
                ", CustID='" + CustID + '\'' +
                '}';
    }
}
