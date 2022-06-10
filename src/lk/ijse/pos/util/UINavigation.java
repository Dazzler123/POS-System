package lk.ijse.pos.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UINavigation {
    public static void setUI(String URI, String title) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(UINavigation.class.getResource("../view/" + URI + ".fxml"))));
        stage.setTitle(title);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
