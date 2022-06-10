package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<Orders> {
    public String generateID() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getIDs(String id) throws SQLException, ClassNotFoundException;
    public String getDate(String id) throws SQLException, ClassNotFoundException;
    public boolean save(Orders order) throws SQLException, ClassNotFoundException;
}
