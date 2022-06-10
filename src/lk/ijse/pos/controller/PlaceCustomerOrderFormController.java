package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.PlaceOrderBO;
import com.jfoenix.controls.JFXButton;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDetailDTO;
import lk.ijse.pos.dto.OrdersDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.pos.util.DateAndTime;
import lk.ijse.pos.util.UINavigation;
import lk.ijse.pos.view.tm.CartTM;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceCustomerOrderFormController {
    public ComboBox cbxItemCode;
    public Label lblDescription;
    public Label lblPackSize;
    public Label lblUnitPrice;
    public Label lblQtyOnHand;
    public TextField txtQty;
    public ComboBox cbxCustID;
    public Label lblCity;
    public Label lblProvince;
    public Label lblPostalCode;
    public Label lblCustTitle;
    public Label lblCustName;
    public Label lblCustAddress;
    public TableView tblItemList;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TextField txtEditQty;
    public Label lblOrderID;
    public Label lblDate;
    public Label lblTime;
    public Label lblAM_PM;
    public Label lblTotalAmount;
    public TextField txtDiscount;
    public Label lblTotalDiscount;
    public Label lblDiscountInRs;
    public TableColumn colTotal;
    public int selectedRow;
    public JFXButton btnRemove;
    public JFXButton btnPlaceOrder;


    //Dependency injection - property injection
    private final PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PLACE_ORDER);

    //item list in the cart
    private final ObservableList<CartTM> itemList = FXCollections.observableArrayList();

    public void initialize() throws SQLException, ClassNotFoundException {
        // load data and time
        DateAndTime.loadDateAndTime(lblDate, lblTime, lblAM_PM);

        //disable confirm order btn
        btnPlaceOrder.setDisable(true);

        //generate new order id
        generateNewOrderID();

        //add items to the table
        colItemCode.setCellValueFactory(new PropertyValueFactory("ItemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory("Description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory("PackSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("UnitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory("Qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory("Total"));

        // load all customer ids to cbxCustID comboBox
        loadAllCustomerIDs();

        // load all customer details
        cbxCustID.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        loadAllCustomers(newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        // load all item codes to cbxItemCode comboBox
        loadAllItemCodes();

        // load all item details
        cbxItemCode.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        loadAllItems(newValue);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        //calculate discount amount for the label
        txtDiscount.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    calculateDiscount(newValue);
                });

        // get selected item from the table
        tblItemList.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    getSelectedItem((CartTM) newValue);
                }));

        //get changing qty on hand from database
        lblQtyOnHand.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        setQty();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

    }

    private void generateNewOrderID() throws SQLException, ClassNotFoundException {
        lblOrderID.setText(placeOrderBO.generateOrderID());
//        try {
//            ResultSet rs = CrudUtil.execute("SELECT MAX(OrderID) FROM Orders");
//            if (rs.next()) {
//                //rs.getString("MAX(OrderID)");
//                if (rs.getString("MAX(OrderID)") == null) {
//                    lblOrderID.setText("O001");
//                } else {
//                    Long OrderID = Long.parseLong(rs.getString("MAX(OrderID)").substring(2, rs.getString("MAX(OrderID)").length()));
//                    OrderID++;
//                    lblOrderID.setText("O0" + String.format("%02d", OrderID));
//                }
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    private void getSelectedItem(CartTM newValue) {
        txtEditQty.setText(String.valueOf(newValue.getQty()));

        //calculate total discount given
        Double unitPrice = Double.parseDouble(String.valueOf(newValue.getUnitPrice()));
        int qty = Integer.parseInt(String.valueOf(newValue.getQty()));
        // unit price * qty = amt - total
        Double amt = unitPrice * qty;
        selectedRow = tblItemList.getSelectionModel().getSelectedIndex();
    }

    private void loadAllCustomerIDs() throws SQLException, ClassNotFoundException {
        ObservableList<String> custIdList = FXCollections.observableList(placeOrderBO.getAllCustomerIDs());
        cbxCustID.setItems(custIdList);
//        ResultSet result = CrudUtil.execute("SELECT CustID FROM Customer");
//        ArrayList<String> ids = new ArrayList<>();
//        while (result.next()) {
//            ids.add(result.getString(1));
//        }
    }

    private void loadAllCustomers(Object newValue) throws SQLException, ClassNotFoundException {
        String custID = String.valueOf(newValue);

        ArrayList<CustomerDTO> custList = placeOrderBO.getAllCustomers(custID);
        for (CustomerDTO c : custList) {
            lblCustTitle.setText(c.getCustTitle());
            lblCustName.setText(c.getCustName());
            lblCustAddress.setText(c.getCustAddress());
            lblCity.setText(c.getCustCity());
            lblProvince.setText(c.getCustProvice());
            lblPostalCode.setText(c.getCustPostalCode());
        }
//        try {
//            ResultSet resultSet = CrudUtil.execute("SELECT * FROM Customer WHERE CustID=?", newValue);
//            if (resultSet.next()) {
//                lblCustTitle.setText(resultSet.getString(2));
//                lblCustName.setText(resultSet.getString(3));
//                lblCustAddress.setText(resultSet.getString(4));
//                lblCity.setText(resultSet.getString(5));
//                lblProvince.setText(resultSet.getString(6));
//                lblPostalCode.setText(resultSet.getString(7));
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    private void loadAllItemCodes() throws SQLException, ClassNotFoundException {
        ObservableList<String> itemIdList = FXCollections.observableList(placeOrderBO.getAllItemCodes());
        cbxItemCode.setItems(itemIdList);
//        ResultSet result = CrudUtil.execute("SELECT ItemCode FROM Item");
//        ArrayList<String> ids = new ArrayList<>();
//        while (result.next()) {
//            ids.add(result.getString(1));
//        }
    }

    private void loadAllItems(Object newValue) throws SQLException, ClassNotFoundException {
        String itemCode = String.valueOf(newValue);

        ArrayList<ItemDTO> items = placeOrderBO.getAllItems(itemCode);
        for (ItemDTO i : items) {
            lblDescription.setText(i.getDescription());
            lblPackSize.setText(i.getPackSize());
            lblUnitPrice.setText(String.valueOf(i.getUnitPrice()));
            lblQtyOnHand.setText(String.valueOf(i.getQtyOnHand()));
        }
//        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item WHERE ItemCode=?", newValue);
//        if (resultSet.next()) {
//            lblDescription.setText(resultSet.getString(2));
//            lblPackSize.setText(resultSet.getString(3));
//            lblUnitPrice.setText(String.valueOf(resultSet.getDouble(4)));
//            lblQtyOnHand.setText(String.valueOf(resultSet.getInt(5)));
//        }
    }

    private void setQty() throws SQLException, ClassNotFoundException {
        String itemCode = String.valueOf(cbxItemCode.getSelectionModel().getSelectedItem());

        placeOrderBO.getQty(itemCode);
//        CrudUtil.execute("SELECT QtyOnHand FROM Item WHERE ItemCode=?",
//                cbxItemCode.getSelectionModel().getSelectedItem());
    }

    public void btnAddNewCustomer(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        UINavigation.setUI("AddNewCustomerForm", "Add New Customer");
    }

    private void calculateDiscount(String newValue) {
        Double discountPercentage = Double.parseDouble(txtDiscount.getText());
        Double unitPrice = Double.parseDouble(lblUnitPrice.getText());

        //Discount percentage / 100 * unit price = discount amount
        Double calculateDiscount = discountPercentage / 100 * unitPrice;
        lblDiscountInRs.setText(String.valueOf(calculateDiscount));
    }

    public void btnAddItem(ActionEvent actionEvent) {
        tblItemList.refresh();
        Double discountPercentage = Double.parseDouble(txtDiscount.getText());
        Double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        int qtyOnHand = Integer.parseInt(lblQtyOnHand.getText());

        //check if asked qty is more than the available qty on hand
        if (qty > qtyOnHand) {
            new Alert(Alert.AlertType.WARNING, "Invalid Quantity!").show();
        } else {

            //Discount percentage / 100 * unit price = discount amount
            Double calculateDiscount = discountPercentage / 100 * unitPrice;

            // final price issued
            Double price = unitPrice - calculateDiscount;
            Double finalPrice = price * qty;
            //BigDecimal total = BigDecimal.valueOf(finalPrice);
            double totalDiscount = calculateDiscount * qty;

            ////////////////////////////////////////////////////////////////////
            CartTM isExists = isExists(String.valueOf(cbxItemCode.getValue()));
            if (isExists != null) {
                for (CartTM temp : itemList
                ) {
                    if (temp.equals(isExists)) {
                        temp.setQty((temp.getQty() + qty));
                        temp.setTotal(temp.getTotal() + finalPrice);
                        temp.setDiscount(temp.getDiscount() + totalDiscount);
                    }
                }
            } else {
                //--------------------load items to the table--------------------//
                CartTM cartTM = new CartTM(
                        String.valueOf(cbxItemCode.getSelectionModel().getSelectedItem()
                        ), lblDescription.getText(), lblPackSize.getText(),
                        Double.parseDouble(lblUnitPrice.getText()), qty, finalPrice);
                itemList.add(cartTM);
                tblItemList.setItems(itemList);

                // send total discount amount separately
                cartTM.setDiscount(totalDiscount);

                //remove item on action
                btnRemove.setOnAction(e -> {
                    itemList.remove(cartTM);
                    calculateTotal();
                });
            }

            tblItemList.refresh();
            calculateTotal();

            //confirm order btn remains disable if no items added to the cart
            if (tblItemList.getItems().isEmpty()) {
                btnPlaceOrder.setDisable(true);
            } else {
                //enable confirm order button to place order
                btnPlaceOrder.setDisable(false);
            }
            //System.out.println(itemList);
        }
    }

    private void calculateTotal() {
        double total = 0;
        for (CartTM tm : itemList
        ) {
            total += tm.getTotal();
        }
        lblTotalAmount.setText(String.valueOf(total));
    }

    private CartTM isExists(String itemCode) {
        for (CartTM tm : itemList
        ) {
            if (tm.getItemCode().equals(itemCode)) {
                return tm;
            }
        }
        return null;
    }

    public void btnRemoveItem(ActionEvent actionEvent) {
        //already implemented in line 318
    }

    public void btnSetQty(ActionEvent actionEvent) {
        // 1.qty eka table eke change wenna ona
        // 2.dena qty eka wenas weddi qtyOnHand ekath wenas wenna ona

//        //record item code
//        ItemsTM item = (ItemsTM) tblItemList.getSelectionModel().getSelectedItem();
//        item.getItemCode();
//
//        //record new qty
//        int newQty = Integer.parseInt(txtEditQty.getText());
//        updatedQty.add(newQty);
//        // replace new qty on the ui table
//        item.setQty(newQty);
//        tblItemList.refresh();
    }

    public void btnConfirmOrder(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        OrdersDTO order = new OrdersDTO(
                lblOrderID.getText(),
                lblDate.getText(),
                String.valueOf(cbxCustID.getValue())
        );

        ArrayList<OrderDetailDTO> details = new ArrayList<>();
        for (CartTM tm : itemList
        ) {
            details.add(
                    new OrderDetailDTO(
                            lblOrderID.getText(),
                            tm.getItemCode(),
                            tm.getQty(),
                            tm.getDiscount()
                    )
            );
        }

        if (placeOrderBO.placeOrder(order, details)) {
            new Alert(Alert.AlertType.CONFIRMATION, "Order placed successfully.").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Error!").show();
        }

        //generate new order id
        generateNewOrderID();

        // clear ui for a new order
        clearUI();

    }

    private void clearUI() {
        tblItemList.getItems().clear();
        txtDiscount.clear();
        txtQty.clear();
        lblQtyOnHand.setText("");
        lblUnitPrice.setText("");
        lblPackSize.setText("");
        lblDescription.setText("");
        lblPackSize.setText("");
        cbxItemCode.getSelectionModel().clearSelection();
        cbxCustID.getSelectionModel().clearSelection();
        lblCustName.setText("");
        lblCustTitle.setText("");
        lblCustAddress.setText("");
        lblPostalCode.setText("");
        lblCity.setText("");
        lblProvince.setText("");
        itemList.clear();
    }

//    public boolean saveOrder(OrdersDTO order) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("INSERT INTO Orders VALUES(?,?,?)",
//                order.getOrderID(), order.getOrderDate(), order.getCustID());
//    }

//    private boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("UPDATE Item SET QtyOnHand=QtyOnHand-? WHERE ItemCode=?", qty, itemCode);
//    }

    public void btnCancelOrder(ActionEvent actionEvent) {
//        new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to cancel the order?").show();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
