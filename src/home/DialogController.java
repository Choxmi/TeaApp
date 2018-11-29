package home;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogController implements Initializable {

    @FXML
    private javafx.scene.control.Button btnExit;

    @FXML
    private javafx.scene.control.Button btnBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleClicks(ActionEvent event){
        if(event.getSource() == btnExit){
            DBOperations.stopMysql();
            Platform.exit();
        }
        if(event.getSource() == btnBack){
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.close();
        }
    }
}