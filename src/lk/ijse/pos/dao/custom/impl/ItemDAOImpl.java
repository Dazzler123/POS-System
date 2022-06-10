package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public String generateID() {
        try {
            ResultSet rs = CrudUtil.execute("SELECT MAX(ItemCode) FROM Item");
            if (rs.next()) {
                //rs.getString("MAX(ItemCode)");
                if (rs.getString("MAX(ItemCode)") == null) {
                    return "P001";
                } else {
                    Long itemCode = Long.parseLong(rs.getString("MAX(ItemCode)").substring(2, rs.getString("MAX(ItemCode)").length()));
                    itemCode++;
                    return "P0" + String.format("%02d", itemCode);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT ItemCode FROM Item");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()) {
            ids.add(result.getString("ItemCode"));
        }
        return ids;
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item");
        ArrayList<Item> itemList = new ArrayList<>();
        while (resultSet.next()) {
            itemList.add(
                    new Item(
                            resultSet.getString("ItemCode"),
                            resultSet.getString("Description"),
                            resultSet.getString("PackSize"),
                            resultSet.getBigDecimal("UnitPrice"),
                            resultSet.getInt("QtyOnHand")
                    )
            );
        }
        return itemList;
    }

    @Override
    public boolean save(Item item) throws SQLException, ClassNotFoundException {
        ///////////////////////INSERTING TO DATABASE///////////////////////////////////
        return (CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?,?)",
                item.getItemCode(), item.getDescription(),
                item.getPackSize(), item.getUnitPrice(),
                item.getQtyOnHand()));
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return (CrudUtil.execute("UPDATE Item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?",
                item.getDescription(), item.getPackSize(),
                item.getUnitPrice(), item.getQtyOnHand(), item.getItemCode()));
    }

    @Override
    public boolean delete(String itemCode) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Item WHERE ItemCode=?", itemCode);
    }

    @Override
    public ArrayList<Item> getItems(String itemCode) throws SQLException, ClassNotFoundException {
        ArrayList<Item> itemList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item WHERE ItemCode=?", itemCode);
        while (resultSet.next()) {
            itemList.add(
                    new Item(
                            resultSet.getString("ItemCode"),
                            resultSet.getString("Description"),
                            resultSet.getString("PackSize"),
                            resultSet.getBigDecimal("UnitPrice"),
                            resultSet.getInt("QtyOnHand")
            ));
        }
        return itemList;
    }

    @Override
    public String getQty(String itemCode) throws SQLException, ClassNotFoundException {
         ResultSet resultSet = CrudUtil.execute("SELECT QtyOnHand FROM Item WHERE ItemCode=?", itemCode);
         if(resultSet.next()){
             return resultSet.getString("QtyOnHand");
         }
         return null;
    }

    @Override
    public boolean setQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Item SET QtyOnHand=? WHERE ItemCode=?", qty, itemCode);
    }

}
