package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.entity.Orders;
import lk.ijse.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public String generateID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT MAX(OrderID) FROM Orders");
        if (rs.next()) {
            //rs.getString("MAX(OrderID)");
            if (rs.getString("MAX(OrderID)") == null) {
                return "O001";
            } else {
                Long OrderID = Long.parseLong(rs.getString("MAX(OrderID)").substring(2, rs.getString("MAX(OrderID)").length()));
                OrderID++;
                return "O0" + String.format("%02d", OrderID);
            }
        }
        return null;
    }

    @Override
    public ArrayList<String> getIDs(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT OrderID FROM Orders WHERE CustID=?", id);
        ArrayList<String> ids = new ArrayList<>();
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    @Override
    public String getDate(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT OrderDate FROM Orders WHERE OrderID=?", id);
        if (resultSet.next()) {
            return resultSet.getString("OrderDate");
        }
        return null;
    }

    @Override
    public boolean save(Orders order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Orders VALUES(?,?,?)",
                order.getOrderID(), order.getOrderDate(), order.getCustID());
    }


}
