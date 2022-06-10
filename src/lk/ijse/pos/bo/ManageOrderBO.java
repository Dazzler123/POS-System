package lk.ijse.pos.bo;

import lk.ijse.pos.view.tm.ItemsTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ManageOrderBO extends SuperBO {
    public ArrayList<ItemsTM> getOrderDetails(String orderID) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getOrderIDs(String custID) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getCustIDs() throws SQLException, ClassNotFoundException;
    public String getOrderDate(String orderDate) throws SQLException, ClassNotFoundException;
    public String getCustomerName(String custID) throws SQLException, ClassNotFoundException;
    public boolean updateOrderQty(int qty, String orderID, String itemCode) throws SQLException, ClassNotFoundException;
    public boolean deleteItem(String orderID, String itemCode) throws SQLException, ClassNotFoundException;
}
