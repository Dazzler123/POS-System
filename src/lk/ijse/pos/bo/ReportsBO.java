package lk.ijse.pos.bo;

import lk.ijse.pos.view.tm.TotalIncomeTM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface ReportsBO extends SuperBO {
    public ArrayList<TotalIncomeTM> getTotalIncomeRecords(LocalDate from, LocalDate to) throws SQLException, ClassNotFoundException;
}
