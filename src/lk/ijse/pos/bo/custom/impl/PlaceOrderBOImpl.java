package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.PlaceOrderBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDetailDTO;
import lk.ijse.pos.dto.OrdersDTO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.entity.OrderDetail;
import lk.ijse.pos.entity.Orders;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    //Dependency injection - property injection
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAIL);

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateID();
    }

    @Override
    public ArrayList<String> getAllCustomerIDs() throws SQLException, ClassNotFoundException {
        return customerDAO.getIDs();
    }

    public ArrayList<CustomerDTO> getAllCustomers(String custID) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> custList = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll(custID);
        for(Customer c : all){
            custList.add(new CustomerDTO(c.getCustID(),c.getCustTitle(),
                    c.getCustName(),c.getCustAddress(),c.getCity(),
                    c.getProvince(),c.getPostalCode()));
        }
        return custList;
    }

    @Override
    public ArrayList<String> getAllItemCodes() throws SQLException, ClassNotFoundException {
        return itemDAO.getIDs();
    }

    public ArrayList<ItemDTO> getAllItems(String itemCode) throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> itemList = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getItems(itemCode);
        for(Item i : all){
            itemList.add(new ItemDTO(i.getItemCode(),i.getDescription(),i.getPackSize(),i.getUnitPrice(),i.getQtyOnHand()));
        }
        return itemList;
    }

    @Override
    public String getQty(String itemCode) throws SQLException, ClassNotFoundException {
        return itemDAO.getQty(itemCode);
    }

    @Override
    public boolean placeOrder(OrdersDTO order, ArrayList<OrderDetailDTO> details) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> all = new ArrayList<>();
        for(OrderDetailDTO o : details){
            all.add(new OrderDetail(o.getOrderID(),o.getItemCode(),o.getOrderQty(),BigDecimal.valueOf(o.getDiscount())));
        }

        //----------------------------Transaction--------------------//

        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            if (orderDAO.save(new Orders(order.getOrderID(), LocalDate.parse(order.getOrderDate()),order.getCustID()))) {
                if (saveOrderDetails(all)) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        } finally {
            connection.setAutoCommit(true);
        }
        return false;
    }

    @Override
    public boolean saveOrderDetails(ArrayList<OrderDetail> details) throws SQLException, ClassNotFoundException {
        for (OrderDetail det : details) {
            boolean isDetailsSaved = orderDetailDAO.save(det);
            if (isDetailsSaved) {
                if (!itemDAO.setQty(det.getItemCode(), det.getOrderQty())) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

}
