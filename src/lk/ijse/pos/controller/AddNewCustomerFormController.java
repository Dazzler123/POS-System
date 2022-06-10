package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.CustomerBO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import lk.ijse.pos.dto.CustomerDTO;

import java.sql.SQLException;

public class AddNewCustomerFormController {
    public TextField txtCustName;
    public TextField txtCustAddress;
    public TextField txtCustCity;
    public TextField txtCustProvince;
    public TextField txtCustPostalCode;
    public TextField txtCustID;
    public ComboBox cbxCustTitle;

    // Dependency Injection (DI)
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize(){
        //load titles to Customer title combo box
        ObservableList<String> titleList = FXCollections.observableArrayList("Mr.","Mrs.","Ms.","Ven.","Dr.","Hon.");
        cbxCustTitle.setItems(titleList);

    }

    public void btnAddNewCustomer(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CustomerDTO c = new CustomerDTO(
                txtCustID.getText(),String.valueOf(cbxCustTitle.getSelectionModel().getSelectedItem()),
                txtCustName.getText(),txtCustAddress.getText(),
                txtCustCity.getText(),txtCustProvince.getText(),
                txtCustPostalCode.getText()
                );

        if(customerBO.saveCustomer(c)){
            // confirmation alert
            new Alert(Alert.AlertType.CONFIRMATION,"Customer saved successfully.").show();

            // clear all textfields after adding a new customer
            txtCustID.clear();
            cbxCustTitle.getSelectionModel().clearSelection();
            txtCustName.clear();
            txtCustAddress.clear();
            txtCustCity.clear();
            txtCustProvince.clear();
            txtCustPostalCode.clear();
        }
    }
}
