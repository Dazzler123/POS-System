package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean save(Customer c) throws SQLException, ClassNotFoundException {
        ///////////////// INSERTING DATA INTO CUSTOMER TABLE ////////////////////////
        return (CrudUtil.execute("INSERT INTO Customer VALUES(?,?,?,?,?,?,?)",
                c.getCustID(), c.getCustTitle(), c.getCustName(),
                c.getCustAddress(), c.getCity(), c.getProvince(),
                c.getPostalCode()));

    }

    @Override
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT CustID FROM Customer");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()) {
            ids.add(result.getString("CustID"));
        }
        return ids;
    }

    @Override
    public String getName(String id) throws SQLException, ClassNotFoundException {
        ResultSet custName = CrudUtil.execute("SELECT CustName FROM Customer WHERE CustID=?", id);
        if (custName.next()) {
            return custName.getString("CustName");
        }
        return null;
    }

    @Override
    public ArrayList<Customer> getAll(String custID) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> custList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customer WHERE CustID=?", custID);
        while (resultSet.next()) {
            custList.add(
                    new Customer(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getString(6),
                            resultSet.getString(7)
                    ));
        }
        return custList;
    }

    @Override
    public String generateID() {
        return null;
    }
}
