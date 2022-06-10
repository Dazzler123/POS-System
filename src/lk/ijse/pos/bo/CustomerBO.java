package lk.ijse.pos.bo;

import lk.ijse.pos.dto.CustomerDTO;

import java.sql.SQLException;

public interface CustomerBO extends SuperBO {
    public boolean saveCustomer(CustomerDTO c) throws SQLException, ClassNotFoundException;
}
