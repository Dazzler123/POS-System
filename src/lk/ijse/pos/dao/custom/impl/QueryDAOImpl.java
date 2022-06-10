package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.util.CrudUtil;
import lk.ijse.pos.view.tm.TotalIncomeTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<TotalIncomeTM> getTotalIncome(LocalDate from, LocalDate to) throws SQLException, ClassNotFoundException {
        ArrayList<TotalIncomeTM> incomeList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT O.OrderDate, OD.OrderID, OD.ItemCode, ((OD.OrderQty*I.UnitPrice)-OD.Discount) AS Income\n" +
                "        FROM orderdetail AS OD, Item AS I, Orders AS O\n" +
                "        WHERE I.ItemCode=OD.ItemCode AND O.OrderID=OD.OrderID AND (O.OrderDate BETWEEN'" + from + "' AND '" + to + "')\n" +
                "        ORDER BY OD.OrderID");
        while (resultSet.next()) {
            incomeList.add(
                    new TotalIncomeTM(
                            resultSet.getString("OrderDate"),
                            resultSet.getString("OrderID"),
                            resultSet.getString("ItemCode"),
                            resultSet.getDouble("Income")
                    ));
        }
        return incomeList;
    }

}
