package home;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static data.CommonDeffs.*;

public class DialogController implements Initializable {

    @FXML
    private javafx.scene.control.Button dNegative;

    @FXML
    private javafx.scene.control.Button dPositive;

    @FXML
    private AnchorPane dBackground;

    @FXML
    private Label dText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDialog(chosenType);
    }

    private static int chosenType = 0;
    private static String optTxt = "";

    public static void setType(int type, String... opt){
        chosenType = type;
        optTxt = opt[0];
    }

    public void handleClicks(ActionEvent event){
        if(event.getSource() == dNegative){
            performClick(0);
        }
        if(event.getSource() == dPositive){
            performClick(1);
        }
    }

    private void initDialog(int type){
        switch (type){
            case 1:
                dText.setText("Are you sure you want to signout?");
                dBackground.setStyle("-fx-background-color: #000000;");
                dPositive.setText("Yes");
                dNegative.setText("No");
                break;
            case 2:
                break;
            case 3:
                dText.setText("Record already exists");
                dBackground.setStyle("-fx-background-color: #FF0000;");
                dPositive.setVisible(false);
                dNegative.setText("Ok");
                break;
            case 4:
                dText.setText(optTxt + " Not Found");
                dBackground.setStyle("-fx-background-color: #FF0000;");
                dPositive.setVisible(false);
                dNegative.setText("Ok");
            default:
                break;
        }
    }

    private void performClick(int btn){
        if(btn == 0){
            switch (chosenType){
                case 1:
                    DBOperations.stopMysql();
                    Platform.exit();
                    break;
                case 2:
                    break;
                case 3:
                    Stage stg3 = (Stage) dPositive.getScene().getWindow();
                    stg3.close();
                    break;
                case 4:
                    Stage stg4 = (Stage) dPositive.getScene().getWindow();
                    stg4.close();
                    break;
                default:
                    break;
            }
        }else if(btn == 1){
            switch (chosenType){
                case 1:
                    Stage stg1 = (Stage) dPositive.getScene().getWindow();
                    stg1.close();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
    }
}