package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.entity.OrderDetail;
import lk.ijse.pos.util.CrudUtil;
import lk.ijse.pos.view.tm.ItemsTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public ArrayList<ItemsTM> getAll(String orderID) throws SQLException, ClassNotFoundException {
        ArrayList<ItemsTM> itemList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT i.ItemCode, i.Description, i.PackSize, i.UnitPrice, oD.OrderQty, oD.Discount\n" +
                "FROM Item AS i, OrderDetail AS oD \n" +
                "WHERE i.ItemCode=oD.ItemCode AND oD.OrderID=?", orderID);
        while (resultSet.next()) {
            itemList.add(
                    new ItemsTM(
                            resultSet.getString("ItemCode"),
                            resultSet.getString("Description"),
                            resultSet.getString("PackSize"),
                            resultSet.getDouble("UnitPrice"),
                            resultSet.getInt("OrderQty"),
                            resultSet.getDouble("Discount")
                    ));
        }
        return itemList;
    }

    @Override
    public boolean update(int qty, String orderID, String itemCode) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE OrderDetail SET OrderQty=? WHERE OrderID=? AND ItemCode=?",
                qty, orderID, itemCode);
    }

    @Override
    public boolean delete(String orderID, String itemCode) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM OrderDetail WHERE OrderID=? AND ItemCode=?",
                orderID, itemCode);
    }

    @Override
    public boolean save(OrderDetail details) throws SQLException, ClassNotFoundException {
        if (CrudUtil.execute("INSERT INTO OrderDetail VALUES(?,?,?,?)",
                details.getOrderID(), details.getItemCode(), details.getOrderQty(), details.getDiscount())) {
            return true;
        }
        return false;
    }

    @Override
    public String generateID() throws SQLException, ClassNotFoundException {
        return null;
    }

}
