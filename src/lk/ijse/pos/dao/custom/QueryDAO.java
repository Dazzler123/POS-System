package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.view.tm.TotalIncomeTM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    public ArrayList<TotalIncomeTM> getTotalIncome(LocalDate from, LocalDate to) throws SQLException, ClassNotFoundException;
}
