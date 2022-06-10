package lk.ijse.pos.dao;

import java.sql.SQLException;

public interface CrudDAO<T> extends SuperDAO{ // T as entity classes

    public boolean save(T entity) throws SQLException, ClassNotFoundException; //Customer, Item, Orders, OrderDetail
    public String generateID()throws SQLException, ClassNotFoundException; //Item , Orders

}
