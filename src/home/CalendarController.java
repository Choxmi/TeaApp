package home;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {
    @FXML
    private javafx.scene.control.Button btnExit;

    @FXML
    private javafx.scene.control.Button btnBack;

    @FXML
    Label lblYear;

    @FXML
    Button btnCalPrev,btnCalNxt;

    private int year;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblYear.setText(""+Calendar.getInstance().get(Calendar.YEAR));
    }

    public void handleClicks(ActionEvent event){
        if(event.getSource()==btnCalPrev){
            lblYear.setText(String.valueOf(getYear()-1));
        }if(event.getSource()==btnCalNxt){
            lblYear.setText(String.valueOf(getYear()+1));
        }
    }

    public void handleMonths(ActionEvent ev){
        Node node = (Node) ev.getSource();
        String mnth = node.getUserData().toString();
        Controller.setMonth(getYear(),Integer.valueOf(mnth));
        Stage stage = (Stage) btnCalNxt.getScene().getWindow();
        stage.close();
    }

    private int getYear(){
        int yr = Integer.valueOf(lblYear.getText());
        return yr;
    }
}
