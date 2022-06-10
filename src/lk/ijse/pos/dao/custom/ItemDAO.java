package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item> {
    public String generateID();
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException;
    public boolean save(Item item) throws SQLException, ClassNotFoundException;
    public boolean update(Item item) throws SQLException, ClassNotFoundException;
    public boolean delete(String itemCode) throws SQLException, ClassNotFoundException;
    public ArrayList<Item> getItems(String itemCode) throws SQLException, ClassNotFoundException;
    public String getQty(String itemCode) throws SQLException, ClassNotFoundException;
    public boolean setQty(String itemCode, int qty) throws SQLException, ClassNotFoundException;
}
