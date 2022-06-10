package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.ManageOrderBO;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.pos.view.tm.ItemsTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManageCustomerOrderFormController {
    public TableView tblItemList;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public TextField txtEditQty;
    public ComboBox cbxCustID;
    public ComboBox cbxOrderID;
    public Label lblOrderDate;
    public Label lblCustName;
    public JFXButton btnRemove;

    //Dependency injection - property injection
    private final ManageOrderBO manageOrderBO = (ManageOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.MANAGE_ORDER);

    //order item list
    private final ObservableList<ItemsTM> itemList = FXCollections.observableArrayList();

    private final ArrayList<String> removeItems = new ArrayList<>();
    private final ArrayList<Integer> updatedQty = new ArrayList<>();
    private final ArrayList<String> itemCodes = new ArrayList<>();

    public void initialize() throws SQLException, ClassNotFoundException {
        colItemCode.setCellValueFactory(new PropertyValueFactory("ItemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory("Description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory("PackSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("UnitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory("Qty"));
        colDiscount.setCellValueFactory(new PropertyValueFactory("Discount"));

        // load all customer ids to cbxCustID comboBox
        loadAllCustomerIDs();

        //set customer name and load order id's
        cbxCustID.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        getCustomerName(newValue);
                        //load order id's of the selected customer
                        loadAllOrderIDs(newValue);
                        tblItemList.refresh();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        //set order date
        cbxOrderID.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        getOrderDate(newValue);
                        // clear table for new records set
                        tblItemList.getItems().clear();

                        // load all order details to the table
                        getOrderDetails(String.valueOf(newValue));
                        tblItemList.refresh();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });


        // get selected item from the table
        tblItemList.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    getSelectedItem((ItemsTM) newValue);
                }));
    }

    private void getSelectedItem(ItemsTM newValue) {
        txtEditQty.setText(String.valueOf(newValue.getQty()));
//        //calculate total discount given
//        Double unitPrice = Double.parseDouble(String.valueOf(newValue.getUnitPrice()));
//        int qty = Integer.parseInt(String.valueOf(newValue.getQty()));
//        // unit price * qty = amt - total
//        Double amt = unitPrice * qty;

        //remove item on action
        btnRemove.setOnAction(e -> {
            itemList.remove(newValue);
            removeItems.add(newValue.getItemCode());
        });
        tblItemList.refresh();
    }

    private void getOrderDetails(String OrderID) throws SQLException, ClassNotFoundException {
        //clear all existing records
        tblItemList.getItems().clear();

        //load records to the table
        ArrayList<ItemsTM> list = manageOrderBO.getOrderDetails(OrderID);
        for (ItemsTM i : list) {
            itemList.add(new ItemsTM(i.getItemCode(), i.getDescription(), i.getPackSize(), i.getUnitPrice(), i.getQty(), i.getDiscount()));
        }
        tblItemList.setItems(itemList);
//        ResultSet resultSet = CrudUtil.execute("SELECT i.ItemCode, i.Description, i.PackSize, i.UnitPrice, oD.OrderQty, oD.Discount\n" +
//                "FROM Item AS i, OrderDetail AS oD \n" +
//                "WHERE i.ItemCode=oD.ItemCode AND oD.OrderID=?", OrderID);
//        while (resultSet.next()) {
//            itemList.add(
//                    new ItemsTM(
//                            resultSet.getString("ItemCode"),
//                            resultSet.getString("Description"),
//                            resultSet.getString("PackSize"),
//                            resultSet.getDouble("UnitPrice"),
//                            resultSet.getInt("OrderQty"),
//                            resultSet.getDouble("Discount")
//                    ));
//            tblItemList.setItems(itemList);
//        }
//             else {
//                new Alert(Alert.AlertType.ERROR, "Failed to load Order details!").show();
//            }
    }

    private void loadAllOrderIDs(Object newValue) throws SQLException, ClassNotFoundException {
        String custID = String.valueOf(newValue);

        ObservableList<String> orderIdList = FXCollections.observableArrayList(manageOrderBO.getOrderIDs(custID));
        cbxOrderID.setItems(orderIdList);
//        ResultSet resultSet = CrudUtil.execute("SELECT OrderID FROM Orders WHERE CustID=?", newValue);
//        ArrayList<String> ids = new ArrayList<>();
//        while (resultSet.next()) {
//            ids.add(resultSet.getString(1));
//        }
        //        }else{
//            new Alert(Alert.AlertType.ERROR,"No orders was placed by the selected Customer.").show();
//        }
    }

    private void loadAllCustomerIDs() throws SQLException, ClassNotFoundException {
        ObservableList<String> custIDList = FXCollections.observableList(manageOrderBO.getCustIDs());
        cbxCustID.setItems(custIDList);
//        ResultSet result = CrudUtil.execute("SELECT CustID FROM Customer");
//        ArrayList<String> ids = new ArrayList<>();
//        while (result.next()) {
//            ids.add(result.getString(1));
//        }
    }

    private void getOrderDate(Object newValue) throws SQLException, ClassNotFoundException {
        String orderID = String.valueOf(newValue);

        lblOrderDate.setText(manageOrderBO.getOrderDate(orderID));
//        ResultSet resultSet = CrudUtil.execute("SELECT OrderDate FROM Orders WHERE OrderID=?",
//                newValue);
//        if (resultSet.next()) {
//            lblOrderDate.setText(resultSet.getString("OrderDate"));
//        }
    }

    private void getCustomerName(Object newValue) throws SQLException, ClassNotFoundException {
        String custID = String.valueOf(newValue);

        lblCustName.setText(manageOrderBO.getCustomerName(custID));
//        ResultSet custName = CrudUtil.execute("SELECT CustName FROM Customer WHERE CustID=?",
//                newValue);
//        if (custName.next()) {
//            lblCustName.setText(custName.getString(1));
//        }
    }

    public void btnRemoveItem(ActionEvent actionEvent) {
        // already implemented in line 104 inside a listener
    }

    public void btnSetQty(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        //record item code
        ItemsTM item = (ItemsTM) tblItemList.getSelectionModel().getSelectedItem();
        itemCodes.add(item.getItemCode());

        //record new qty
        int newQty = Integer.parseInt(txtEditQty.getText());
        updatedQty.add(newQty);
        // replace new qty on the ui table
        item.setQty(newQty);
        tblItemList.refresh();
    }

    public void btnConfirmEdits(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String OrderID = String.valueOf(cbxOrderID.getSelectionModel().getSelectedItem());
        boolean update;
        boolean delete;

//        ArrayList<Integer> qtys = new ArrayList<>();
//        ArrayList<String> itemCods = new ArrayList<>();
//        ArrayList<String> remItmCodes = new ArrayList<>();
//
//        //get item qtys
//        for (Integer tm : updatedQty) {
//            qtys.add(tm);
//        }
//        // get item codes
//        for (String tm2 : itemCodes) {
//            itemCods.add(tm2);
//        }
//
//                    CrudUtil.execute("UPDATE OrderDetail SET OrderQty=? WHERE OrderID=? AND ItemCode=?",
//                    qtys.get(i), oid, String.valueOf(itemCods.get(i)));
//
//                // get item codes of removed items
//        for(String tm3 : removeItems){
//            remItmCodes.add(tm3);
//        }
//
//                    CrudUtil.execute("DELETE FROM OrderDetail WHERE OrderID=? AND ItemCode=?",
//                    oid, String.valueOf(remItmCodes.get(i)));


        //update qty of items in order
        for (int i = 0; i < updatedQty.size(); i++) {
            update = manageOrderBO.updateOrderQty(updatedQty.get(i), OrderID, itemCodes.get(i));
        }

        //remove items from order
        for (int i = 0; i < removeItems.size(); i++) {
            delete = manageOrderBO.deleteItem(OrderID, removeItems.get(i));
        }
        new Alert(Alert.AlertType.CONFIRMATION, "Edits Confirmed.").show();
    }

    public void btnCancelEdits(ActionEvent actionEvent) {
        //   new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to cancel the order?");
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
