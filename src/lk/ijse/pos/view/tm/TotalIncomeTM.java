package lk.ijse.pos.view.tm;

public class TotalIncomeTM {

     private String orderDate;
     private String orderID;
     private String itemCode;
     private double income;

    public TotalIncomeTM(String orderDate, String orderID, String itemCode, double income) {
        this.orderDate = orderDate;
        this.orderID = orderID;
        this.itemCode = itemCode;
        this.income = income;
    }

    public TotalIncomeTM() {
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    @Override
    public String toString() {
        return "TotalIncomeTM{" +
                "orderDate='" + orderDate + '\'' +
                ", orderID='" + orderID + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", income=" + income +
                '}';
    }
}
