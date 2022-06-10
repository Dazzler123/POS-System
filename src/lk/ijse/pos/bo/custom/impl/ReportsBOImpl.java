package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.ReportsBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.view.tm.TotalIncomeTM;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReportsBOImpl implements ReportsBO {

    //Dependency injection - property injection
    QueryDAO queryDAO = (QueryDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.REPORT);

    @Override
    public ArrayList<TotalIncomeTM> getTotalIncomeRecords(LocalDate from, LocalDate to) throws SQLException, ClassNotFoundException {
        return queryDAO.getTotalIncome(from, to);
    }
}
