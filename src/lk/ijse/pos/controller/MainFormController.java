package lk.ijse.pos.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import lk.ijse.pos.util.DateAndTime;
import lk.ijse.pos.util.UINavigation;

import java.io.IOException;

public class MainFormController {
    public Label lblDate;
    public Label lblTime;
    public Label lblAM_PM;

    public void initialize(){
       DateAndTime.loadDateAndTime(lblDate,lblTime,lblAM_PM);
    }

    public void btnPlaceCustomerOrder(ActionEvent actionEvent) throws IOException {
        UINavigation.setUI("PlaceCustomerOrderForm","Place Customer Order");
    }

    public void btnManageCustomerOrder(ActionEvent actionEvent) throws IOException {
        UINavigation.setUI("ManageCustomerOrderForm","Manage Customer Order");
    }

    public void btnAdminLogin(ActionEvent actionEvent) throws IOException {
        UINavigation.setUI("AdminLoginForm","Administrator Login");
    }

//    private void setUi(String URI,String title) throws IOException {
//        Stage stage = new Stage();
//        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+URI+".fxml"))));
//        stage.setTitle(title);
//        //stage.initStyle(StageStyle.UNDECORATED);
//        stage.show();
//    }

}
