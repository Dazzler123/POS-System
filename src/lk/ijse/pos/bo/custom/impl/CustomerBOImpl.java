package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.CustomerBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.entity.Customer;

import java.sql.SQLException;

public class CustomerBOImpl implements CustomerBO {

    //Dependency injection - property injection
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO c) throws SQLException, ClassNotFoundException {
        if(customerDAO.save(new Customer(c.getCustID(),c.getCustTitle(),
                c.getCustName(),c.getCustAddress(),c.getCustCity(),
                c.getCustProvice(),c.getCustPostalCode()))){
            // return as a confirmation
            return true;
        }
        return false;
    }
}
