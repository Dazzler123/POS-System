package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.ManageOrderBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.view.tm.ItemsTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageOrderBOImpl implements ManageOrderBO {

    //Dependency injection - property injection
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<ItemsTM> getOrderDetails(String orderID) throws SQLException, ClassNotFoundException {
       return orderDetailDAO.getAll(orderID);
    }

    @Override
    public ArrayList<String> getOrderIDs(String custID) throws SQLException, ClassNotFoundException {
        return orderDAO.getIDs(custID);
    }

    @Override
    public ArrayList<String> getCustIDs() throws SQLException, ClassNotFoundException {
        return customerDAO.getIDs();
    }

    @Override
    public String getOrderDate(String orderDate) throws SQLException, ClassNotFoundException {
        return orderDAO.getDate(orderDate);
    }

    @Override
    public String getCustomerName(String custID) throws SQLException, ClassNotFoundException {
        return customerDAO.getName(custID);
    }

    @Override
    public boolean updateOrderQty(int qty, String orderID, String itemCode) throws SQLException, ClassNotFoundException {
        if(orderDetailDAO.update(qty,orderID,itemCode)){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteItem(String orderID, String itemCode) throws SQLException, ClassNotFoundException {
        if(orderDetailDAO.delete(orderID,itemCode)){
            return true;
        }
        return false;
    }

}
