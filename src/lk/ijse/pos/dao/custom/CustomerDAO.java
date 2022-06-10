package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer> {
    public boolean save(Customer c) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException;
    public String getName(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<Customer> getAll(String custID) throws SQLException, ClassNotFoundException;
}
