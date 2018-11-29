package home;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    //region components
    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnTransaction;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnPayslips;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignout;

    @FXML
    private Button btnMonth;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlTransaction;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlPayslips;

    @FXML
    private ImageView imgProfile;

    @FXML
    private Button btnCustSave;

    @FXML
    private TextField txtCusNIC;

    @FXML
    private TextField txtCusName;

    @FXML
    private TextField txtCusMobile;

    @FXML
    private TextField txtCusAccount;

    @FXML
    private TextField txtCusAddress;

    @FXML
    private Button btnTrnSave;

    @FXML
    private TextField txtTrnGrossWeight;

    @FXML
    private TextField txtTrnDedWater;

    @FXML
    private TextField txtTrnDedOther;

    @FXML
    private TextField txtTrnNetWeight;

    @FXML
    private TextField txtTrnAdvance;

    @FXML
    private TextField txtTrnDate;

    @FXML
    private Label txtTrnCusID;

    @FXML
    private Label txtTrnCusName;

    @FXML
    private ComboBox cmbTrnArea;

    //endregion

    public static int year,month,date;

    private List<List<String>> results;
    private List<String> cols, vals;

    private static  Controller controller;

    public Controller(){
        controller = this;
    }

    public static Controller getInstance(){
        if(controller!=null){
            return controller;
        }else {
            return new Controller();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DBOperations.startMysql();
        results = new ArrayList<>();

        Node[] nodes = new Node[10];
        for (int i = 0; i < nodes.length; i++) {
            try {

                final int j = i;
                nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));

                //give the items some effect
                nodes[i].setOnMouseEntered(event -> {
                    nodes[j].setStyle("-fx-background-color : #0A0E3F");
                });
                nodes[i].setOnMouseExited(event -> {
                    nodes[j].setStyle("-fx-background-color : #02030A");
                });

                pnItems.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        cols = new ArrayList<>();
        vals = new ArrayList<>();

        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);
        date = Calendar.getInstance().get(Calendar.DATE);
        btnMonth.setText(year+"/"+month);

        pnlTransaction.setStyle("-fx-background-color : #02030A");
        pnlTransaction.toFront();

    }


    public void handleClicks(ActionEvent actionEvent) {
        //region Customer
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color : #02030A");
            pnlCustomer.toFront();
        }if (actionEvent.getSource() == btnCustSave){
            cols.clear();
            vals.clear();

            cols.add("nic");
            cols.add("name");
            cols.add("mobile");
            cols.add("account_no");
            cols.add("address");

            vals.add(txtCusNIC.getText());
            vals.add(txtCusName.getText());
            vals.add(txtCusMobile.getText());
            vals.add(txtCusAccount.getText());
            vals.add(txtCusAddress.getText());

            insertCustomers(cols,vals);
        }
        //endregion

        //region Report
        if (actionEvent.getSource() == btnPayslips) {
            pnlPayslips.setStyle("-fx-background-color : #02030A");
            pnlPayslips.toFront();
        }
        //endregion

        //region Overview
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();

            fetchTransactions(null, "customer_id = 1");

            for(int i = 0; i < results.size(); i++){
                for(int j = 0; j < results.get(i).size(); j++){
                    System.out.println(results.get(i).get(j));
                }
            }
        }
        //endregion

        //region Transaction
        if(actionEvent.getSource()==btnTransaction)
        {
            pnlTransaction.setStyle("-fx-background-color : #02030A");
            pnlTransaction.toFront();
        }
        if(actionEvent.getSource()==btnTrnSave){
            cols.clear();
            vals.clear();

            cols.add("customer_id");
            cols.add("date");
            cols.add("gross_weight");
            cols.add("ded_water");
            cols.add("ded_other");
            cols.add("net_weight");
            cols.add("adv_payment");

            vals.add(txtTrnCusID.getText());
            vals.add(txtTrnDate.getText());
            vals.add(txtTrnGrossWeight.getText());
            vals.add(txtTrnDedWater.getText());
            vals.add(txtTrnDedOther.getText());
            vals.add(txtTrnNetWeight.getText());
            vals.add(txtTrnAdvance.getText());

            insertTransactions(cols,vals);

            txtTrnGrossWeight.setText("");
            txtTrnDedWater.setText("");
            txtTrnDedOther.setText("");
            txtTrnNetWeight.setText("");
            txtTrnAdvance.setText("");
        }

        //region MonthPicker
        if(actionEvent.getSource() == btnMonth){
            try {
                Stage stage = new Stage();
                Parent root = null;
                System.out.println("It came here");
                stage.initStyle(StageStyle.UNDECORATED);
                root = FXMLLoader.load(
                        CalendarController.class.getResource("MonthPicker.fxml"));
                stage.setScene(new Scene(root));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(
                        ((Node)actionEvent.getSource()).getScene().getWindow() );
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        //endregion

        //endregion

        //region Signout
        if(actionEvent.getSource()==btnSignout) {
            System.out.println("Signout button");
            try {
                Stage stage = new Stage();
                Parent root = null;
                stage.initStyle(StageStyle.UNDECORATED);
                root = FXMLLoader.load(
                        DialogController.class.getResource("ModalDialog.fxml"));
                stage.setScene(new Scene(root));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(
                        ((Node)actionEvent.getSource()).getScene().getWindow() );
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //endregion

        //region Close
        if(actionEvent.getSource() == btnClose){
            System.out.println("EXITING");
            Platform.exit();
        }
        //endregion
    }

    //region DisplayMonth
    public static void setMonth(int yr, int mnth){
        year = yr;
        month = mnth;
        getInstance().monthUpdater();
    }

    public void monthUpdater(){
        if(month<10){
            btnMonth.setText(year + "/0" + month);
        }else {
            btnMonth.setText(year + "/" + month);
        }
    }
    //endregion

    //region Operations
    private void fetchCustomers(List<String> cols, String where){
        results = new DBOperations().executeQuery(DBOperations.TYPE_SELECT,"customer",cols, null,null, where);
    }

    private void insertCustomers(List<String> cols, List<String> values){
        results = new DBOperations().executeQuery(DBOperations.TYPE_INSERT,"customer",cols,values, null, null);
    }

    private void fetchTransactions(List<String> cols, String where){
        String join = " INNER JOIN customer ON transactions.customer_id = customer.id ";
        results = new DBOperations().executeQuery(DBOperations.TYPE_SELECT,"transactions",cols, null,join, where);
    }

    private void insertTransactions(List<String> cols, List<String> values){
        results = new DBOperations().executeQuery(DBOperations.TYPE_INSERT,"transactions",cols,values, null,null);
    }
    //endregion

}
