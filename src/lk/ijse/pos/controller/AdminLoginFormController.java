package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.ijse.pos.util.UINavigation;

import java.io.IOException;

public class AdminLoginFormController {
    public TextField txtUsername;
    public TextField txtPassword;
    public JFXButton btnClose;

    public void btnLoginToAdmin(ActionEvent actionEvent) throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if(username.equals("admin") && password.equals("1234")){
            Stage stage =(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();

            UINavigation.setUI("AdminForm","Administrator Controls");
        }else{
            new Alert(Alert.AlertType.WARNING,"Incorrect Username or Password!").show();
        }
    }

    public void btnCancelAdminLogin(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
