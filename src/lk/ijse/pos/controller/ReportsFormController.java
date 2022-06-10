package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXDatePicker;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.ReportsBO;
import lk.ijse.pos.db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import lk.ijse.pos.view.tm.TotalIncomeTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReportsFormController {
    public JFXDatePicker dateFrom;
    public JFXDatePicker dateTo;
    public TableView<TotalIncomeTM> tblTotalIncome;
    public TableColumn colOrderDate;
    public TableColumn colOrderID;
    public TableColumn colItemCode;
    public TableColumn colIncome;

    //Dependency Injection - property injection
    private final ReportsBO reportsBO = (ReportsBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.REPORT);

    public void initialize() {
        colOrderDate.setCellValueFactory(new PropertyValueFactory("orderDate"));
        colOrderID.setCellValueFactory(new PropertyValueFactory("orderID"));
        colItemCode.setCellValueFactory(new PropertyValueFactory("itemCode"));
        colIncome.setCellValueFactory(new PropertyValueFactory("income"));
    }

    public void btnMostMovableItem(ActionEvent actionEvent) {
//        SELECT I.ItemCode, I.Description, I.PackSize, I.UnitPrice, SUM(OD.OrderQty) AS totqty, OD.Discount
//        FROM orderdetail AS OD, Item AS I
//        WHERE OD.ItemCode=I.ItemCode
//        GROUP BY ItemCode
//        ORDER BY SUM(OD.OrderQty) DESC;
        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/lk/ijse/pos/view/reports/MostMovableItem.jasper"));
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, connection);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnLeastMovableItem(ActionEvent actionEvent) {
//        SELECT I.ItemCode, I.Description, I.PackSize, I.UnitPrice, SUM(OD.OrderQty) AS totqty, OD.Discount
//        FROM orderdetail AS OD, Item AS I
//        WHERE OD.ItemCode=I.ItemCode
//        GROUP BY ItemCode
//        ORDER BY SUM(OD.OrderQty);
        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/lk/ijse/pos/view/reports/LeastMovableItem.jasper"));
            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, connection);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnIncomeReport(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        // get selection date from date pickers
        LocalDate From = dateFrom.getValue();
        LocalDate to = dateTo.getValue();

        //filter by range
//        SELECT O.OrderDate, OD.OrderID, OD.ItemCode, ((OD.OrderQty*I.UnitPrice)-OD.Discount) AS Income
//        FROM orderdetail AS OD, Item AS I, Orders AS O
//        WHERE I.ItemCode=OD.ItemCode AND O.OrderID=OD.OrderID AND (O.OrderDate BETWEEN'2022-05-29' AND '2022-05-29')
//        ORDER BY OD.OrderID;

        //filter by single date
//        SELECT O.OrderDate, OD.OrderID, OD.ItemCode, ((OD.OrderQty*I.UnitPrice)-OD.Discount) AS Income
//        FROM orderdetail AS OD, Item AS I, Orders AS O
//        WHERE I.ItemCode=OD.ItemCode AND O.OrderID=OD.OrderID AND O.OrderDate = '2022-05-29'
//        ORDER BY OD.OrderID;

        /////////////////////////////////////////////////////////////////////////////////
//        try {
//            JasperDesign jd = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/TotalIncome.jrxml"));
//
//            String sql = "SELECT O.OrderDate, OD.OrderID, OD.ItemCode, ((OD.OrderQty*I.UnitPrice)-OD.Discount) AS Income\n" +
//                    "        FROM orderdetail AS OD, Item AS I, Orders AS O\n" +
//                    "        WHERE I.ItemCode=OD.ItemCode AND O.OrderID=OD.OrderID AND (O.OrderDate BETWEEN'"+dateFrom+"' AND '"+dateTo+"')\n" +
//                    "        ORDER BY OD.OrderID";
//            JRDesignQuery newQuery = new JRDesignQuery();
//            newQuery.setText(sql);
//            jd.setQuery(newQuery);
//            JasperReport jr = JasperCompileManager.compileReport(jd);
//            //create connection
//            Connection connection = DBConnection.getInstance().getConnection();
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jr, null, connection);
//            JasperViewer.viewReport(jasperPrint, false);
//
//        } catch (JRException e) {
//            e.printStackTrace();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//        ObservableList<TotalIncomeTM> incomeList = FXCollections.observableArrayList();
//        ResultSet resultSet = CrudUtil.execute("SELECT O.OrderDate, OD.OrderID, OD.ItemCode, ((OD.OrderQty*I.UnitPrice)-OD.Discount) AS Income\n" +
//                "        FROM orderdetail AS OD, Item AS I, Orders AS O\n" +
//                "        WHERE I.ItemCode=OD.ItemCode AND O.OrderID=OD.OrderID AND (O.OrderDate BETWEEN'" + From + "' AND '" + to + "')\n" +
//                "        ORDER BY OD.OrderID");
//
//        while (resultSet.next()) {
//            incomeList.add(
//                    new TotalIncomeTM(
//                            resultSet.getString("OrderDate"),
//                            resultSet.getString("OrderID"),
//                            resultSet.getString("ItemCode"),
//                            resultSet.getDouble("Income")
//                    ));
//        }

        ObservableList<TotalIncomeTM> incomeList = FXCollections.observableArrayList();
        ArrayList<TotalIncomeTM> list = reportsBO.getTotalIncomeRecords(From,to);
        for(TotalIncomeTM tm : list){
            incomeList.add(new TotalIncomeTM(tm.getOrderDate(),tm.getOrderID(),tm.getItemCode(),tm.getIncome()));
        }
        tblTotalIncome.setItems(incomeList);
    }

}
