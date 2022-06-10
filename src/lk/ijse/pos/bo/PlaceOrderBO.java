package lk.ijse.pos.bo;

import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDetailDTO;
import lk.ijse.pos.dto.OrdersDTO;
import lk.ijse.pos.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBO {
    public String generateOrderID() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllCustomerIDs() throws SQLException, ClassNotFoundException;
    public ArrayList<CustomerDTO> getAllCustomers(String custID) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllItemCodes() throws SQLException, ClassNotFoundException;
    public ArrayList<ItemDTO> getAllItems(String itemCode) throws SQLException, ClassNotFoundException;
    public String getQty(String itemCode) throws SQLException, ClassNotFoundException;
    public boolean placeOrder(OrdersDTO order, ArrayList<OrderDetailDTO> details) throws SQLException, ClassNotFoundException;
    public boolean saveOrderDetails(ArrayList<OrderDetail> details) throws SQLException, ClassNotFoundException;
}
