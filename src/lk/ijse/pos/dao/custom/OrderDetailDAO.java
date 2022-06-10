package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.entity.OrderDetail;
import lk.ijse.pos.view.tm.ItemsTM;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
    public ArrayList<ItemsTM> getAll(String orderID) throws SQLException, ClassNotFoundException;
    public boolean update(int qty, String orderID, String itemCode) throws SQLException, ClassNotFoundException;
    public boolean delete(String orderID, String itemCode) throws SQLException, ClassNotFoundException;
    public boolean save(OrderDetail details) throws SQLException, ClassNotFoundException;
}
