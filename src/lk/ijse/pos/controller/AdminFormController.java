package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.ItemBO;
import com.jfoenix.controls.JFXButton;
import lk.ijse.pos.dto.ItemDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.pos.util.UINavigation;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminFormController {
    public TableView tblItems;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public TextField txtDescription;
    public Label lblNewItemCode;
    public TextField txtPackSize;
    public TextField txtUnitPrice;
    public TextField txtQtyOnHand;
    public JFXButton btnUpdate;
    public JFXButton btnRemove;
    public JFXButton btnNew;

    // Dependency Injection (DI)
    private final ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ITEM);

    public void initialize() throws SQLException, ClassNotFoundException {
        colItemCode.setCellValueFactory(new PropertyValueFactory("ItemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory("Description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory("PackSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("UnitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory("QtyOnHand"));
        
        btnUpdate.setDisable(true);
        btnRemove.setDisable(true);

        //load all items to the table
        loadAllItems();

        //generate new item code
        generateNewItemCode();

        // get selected item from the table
        tblItems.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> {
                    getSelectedItem((ItemDTO) newValue);
                }));
    }

    private void generateNewItemCode() {
        lblNewItemCode.setText(itemBO.generateNewItemCode());
//        try {
//            ResultSet rs = CrudUtil.execute("SELECT MAX(ItemCode) FROM Item");
//            if(rs.next()){
//                //rs.getString("MAX(ItemCode)");
//                if(rs.getString("MAX(ItemCode)")==null){
//                    lblNewItemCode.setText("P001");
//                }else{
//                    Long itemCode = Long.parseLong(rs.getString("MAX(ItemCode)").substring(2,rs.getString("MAX(ItemCode)").length()));
//                    itemCode++;
//                    lblNewItemCode.setText("P0"+String.format("%02d",itemCode));
//                }
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    private void getSelectedItem(ItemDTO newValue) {
        lblNewItemCode.setText(newValue.getItemCode());
        txtDescription.setText(newValue.getDescription());
        txtPackSize.setText(newValue.getPackSize());
        txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));

        btnNew.setDisable(true);
        btnUpdate.setDisable(false);
        btnRemove.setDisable(false);
    }

    private void loadAllItems() throws SQLException, ClassNotFoundException {
        //clear all existing records
        tblItems.getItems().clear();

        ArrayList<ItemDTO> itemList = itemBO.loadAllItems();
        for (ItemDTO item : itemList) {
            tblItems.getItems().add(new ItemDTO(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand()));
        }
//        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Item");
//        ObservableList<ItemDTO> obList = FXCollections.observableArrayList();
//
//        while(resultSet.next()){
//            obList.add(
//                    new ItemDTO(
//                            resultSet.getString("ItemCode"),
//                            resultSet.getString("Description"),
//                            resultSet.getString("PackSize"),
//                            resultSet.getBigDecimal("UnitPrice"),
//                            resultSet.getInt("QtyOnHand")
//                    )
//            );
//        }
//        tblItems.setItems(obList);
    }

    public void btnAddNewItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDTO itemDTO = new ItemDTO(
                lblNewItemCode.getText(),txtDescription.getText(),
                txtPackSize.getText(),new BigDecimal(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        );
        //load data to the ui table
        ObservableList<ItemDTO> list = FXCollections.observableArrayList();
        list.add(itemDTO);
        tblItems.setItems(list);

        //add new item to the database
        if(itemBO.saveItem(itemDTO)){
            //confirmation alert
            new Alert(Alert.AlertType.CONFIRMATION, "Item saved successfully.").show();

            //refresh table
            loadAllItems();
        }
//     ///////////////////////INSERTING TO DATABASE///////////////////////////////////
//        try {
//            if(CrudUtil.execute("INSERT INTO Item VALUES (?,?,?,?,?)",
//                    itemDTO.getItemCode(),itemDTO.getDescription(),
//                    itemDTO.getPackSize(),itemDTO.getUnitPrice(),
//                    itemDTO.getQtyOnHand())) {
//
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public void btnUpdateItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ItemDTO itemDTO = new ItemDTO(
                lblNewItemCode.getText(),txtDescription.getText(),
                txtPackSize.getText(),new BigDecimal(txtUnitPrice.getText()),
                Integer.parseInt(txtQtyOnHand.getText())
        );

        if(itemBO.updateItem(itemDTO)){
            //confirmation alert
            new Alert(Alert.AlertType.CONFIRMATION, "Item updated successfully.").show();

            //refresh table
            loadAllItems();
        }
//        ///////////////////////INSERTING TO DATABASE///////////////////////////////////
//        try {
//            if(CrudUtil.execute("UPDATE Item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?",
//                    itemDTO.getDescription(),itemDTO.getPackSize(),
//                    itemDTO.getUnitPrice(),itemDTO.getQtyOnHand(),itemDTO.getItemCode())){
//
//                //refresh table
//                loadAllItems();
//                new Alert(Alert.AlertType.CONFIRMATION, "Item updated successfully.").show();
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public void btnRemoveItem(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String itemCode = String.valueOf(lblNewItemCode.getText());

        if(itemBO.removeItem(itemCode)){
            //confirmation alert
            new Alert(Alert.AlertType.CONFIRMATION,"Item deleted successfully.").show();

            //refresh table
            loadAllItems();
        }
//        try {
//            if(CrudUtil.execute("DELETE FROM Item WHERE ItemCode=?",
//                    lblNewItemCode.getText())){
//
//                //refresh table
//                loadAllItems();
//                new Alert(Alert.AlertType.CONFIRMATION,"Item deleted successfully.").show();
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public void btnToReports(ActionEvent actionEvent) throws IOException {
        // jasper reports
        UINavigation.setUI("ReportsForm","Reports");
    }

    public void btnBack(ActionEvent actionEvent) {
        Stage stage =(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
