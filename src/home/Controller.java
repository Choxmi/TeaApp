package home;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.sf.jasperreports.engine.JRException;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static data.CommonDeffs.DUPLICATE_MSG;
import static data.CommonDeffs.NOT_FOUND_MSG;
import static data.CommonDeffs.SIGNOUT_MSG;

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
    private Button btnPayslips;

    @FXML
    private Pane pnlPayslips;

    @FXML
    private Button btnPayMonth;

    @FXML
    private TextField txtPayPrice;

    @FXML
    private TextField txtPayTransport;

    @FXML
    private TextField txtPayCoreLeaf;

    @FXML
    private TextField txtPayUser;

    @FXML
    private Label lblPayUserName;

    @FXML
    private Pane pnlPayTempCover;

    @FXML
    private Button btnMonthlyRatesSave;

    @FXML
    private ImageView imgProfile;

    @FXML
    private Button btnCustSave;

    @FXML
    private Button btnCusEdit;

    @FXML
    private Button btnCusNew;

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
    private javafx.scene.image.ImageView btnCusSearch;

    @FXML
    private TextField txtCusSearch;

    @FXML
    private Label lblCusID;

    @FXML
    private Label txtCusID;

    @FXML
    private Button btnTrnSave;

    @FXML
    private TextField txtTrnGrossWeight;

    @FXML
    private TextField txtTrnAdvance;

    @FXML
    private TextField txtTrnDate;

    @FXML
    private TextField txtTrnCusID;

    @FXML
    private Label txtTrnCusName;

    @FXML
    private Button btnTrnNxt;

    @FXML
    private Button btnTrnPrev;

    @FXML
    private Button btnExpense;

    @FXML
    private Pane pnlExpenses;

    @FXML
    private RadioButton rdAdvance;

    @FXML
    private RadioButton rdPoison;

    @FXML
    private RadioButton rdDolamite;

    @FXML
    private RadioButton rdTeaPkt;

    @FXML
    private RadioButton rdFert;

    @FXML
    private RadioButton rdOther;

    @FXML
    private TextField txtExpUnitPrice;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TextField txtExpNoUnits;

    @FXML
    private Label lblNoUnits;

    @FXML
    private TextField txtExpDirPrice;

    @FXML
    private Label lblFullPrice;

    @FXML
    private TextField txtExpComments;

    @FXML
    private Label lblComments;

    @FXML
    private Label lblTotPrice;

    @FXML
    private ToggleGroup tgExpenses;

    @FXML
    private TextField btnExpMonth;

    @FXML
    private TextField txtExpUserID;

    @FXML
    private Button btnExpSave;

    @FXML
    private Button btnAnnual;

    @FXML
    private Pane pnlAnnualReport;

    //endregion

    public static int year,month,date;

    private boolean s1,s2 = false;

    private List<List<String>> results,set1,set2;
    private List<String> cols, vals;

    private Button clickedButton;

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
            txtCusNIC.setText("");
            txtCusName.setText("");
            txtCusMobile.setText("");
            txtCusAccount.setText("");
            txtCusAddress.setText("");
            txtCusNIC.setEditable(true);
            txtCusName.setEditable(true);
            txtCusMobile.setEditable(true);
            txtCusAccount.setEditable(true);
            txtCusAddress.setEditable(true);
            lblCusID.setVisible(false);
            txtCusID.setVisible(false);
            btnCustSave.setDisable(false);
            btnCusEdit.setDisable(true);
            btnCusNew.setDisable(false);
            pnlCustomer.toFront();
        } if (actionEvent.getSource() == btnCustSave) {
            cols.clear();
            vals.clear();
            if(Integer.valueOf(txtCusID.getText()) == 0){
                String where = "nic = '" + txtCusNIC.getText() + "'";
                fetchCustomers(null, where);

                System.out.println(results);

                if (results.size() > 0) {
                    System.out.println("Size : " + results.size());
                    openDialog(DUPLICATE_MSG, actionEvent);
                } else {
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

                    insertCustomers(cols, vals);

                    Stage stage = new Stage();

                    String toastMsg = "Customer added";
                    int toastMsgTime = 3500; //3.5 seconds
                    int fadeInTime = 100; //0.5 seconds
                    int fadeOutTime= 100; //0.5 seconds
                    Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);

                    System.out.println(results);
                }
            } else {
                String where = "id = " + txtCusID.getText() + "";
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

                updateCustomers(cols, vals, where);

                Stage stage = new Stage();

                String toastMsg = "Customer details updated";
                int toastMsgTime = 3500; //3.5 seconds
                int fadeInTime = 100; //0.5 seconds
                int fadeOutTime= 100; //0.5 seconds
                Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
                System.out.println(results);
            }
            txtCusNIC.setEditable(true);
            txtCusName.setEditable(true);
            txtCusMobile.setEditable(true);
            txtCusAccount.setEditable(true);
            txtCusAddress.setEditable(true);
            btnCusEdit.setDisable(true);
            btnCusNew.setDisable(false);
            btnCustSave.setDisable(true);
            txtCusID.setText("0");
        } if(actionEvent.getSource() == btnCusEdit){
            txtCusNIC.setEditable(true);
            txtCusName.setEditable(true);
            txtCusMobile.setEditable(true);
            txtCusAccount.setEditable(true);
            txtCusAddress.setEditable(true);
            btnCusEdit.setDisable(true);
            btnCusNew.setDisable(true);
            btnCustSave.setDisable(false);
        } if(actionEvent.getSource() == btnCusNew){
            txtCusNIC.setEditable(true);
            txtCusName.setEditable(true);
            txtCusMobile.setEditable(true);
            txtCusAccount.setEditable(true);
            txtCusAddress.setEditable(true);
            btnCusEdit.setDisable(true);
            btnCustSave.setDisable(false);
            btnCusNew.setDisable(true);
            txtCusID.setText("0");
            txtCusNIC.setText("");
            txtCusName.setText("");
            txtCusMobile.setText("");
            txtCusAccount.setText("");
            txtCusAddress.setText("");
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
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            txtTrnDate.setText(dateFormat.format(date));
            txtTrnCusID.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if(event.getCode().equals(KeyCode.ENTER)){
                        System.out.println("Fetch");
                        String where = "ID = "+txtTrnCusID.getText();
                        fetchCustomers(null,where);
                        if(results.size()==0){
                            openDialog(NOT_FOUND_MSG,event,"User");
                        }
                        System.out.println(results);
                        txtTrnCusID.setText(results.get(0).get(0));
                        txtTrnCusName.setText(results.get(0).get(2));
                    }
                }
            });
            txtTrnCusName.setText("");
        }

        if(actionEvent.getSource()==btnTrnNxt){
            String where = "ID = "+(Integer.valueOf(txtTrnCusID.getText())+1);
            fetchCustomers(null,where);
            if(results.size()==0){
                openDialog(NOT_FOUND_MSG,actionEvent,"User");
            }
            System.out.println(results);
            txtTrnCusID.setText(results.get(0).get(0));
            txtTrnCusName.setText(results.get(0).get(2));
        }

        if(actionEvent.getSource()==btnTrnPrev){
            if(Integer.valueOf(txtTrnCusID.getText()) > 0) {
                String where = "ID = " + (Integer.valueOf(txtTrnCusID.getText()) - 1);
                fetchCustomers(null, where);
                if (results.size() == 0) {
                    openDialog(NOT_FOUND_MSG, actionEvent, "User");
                }
                System.out.println(results);
                txtTrnCusID.setText(results.get(0).get(0));
                txtTrnCusName.setText(results.get(0).get(2));
            }
        }

        if(actionEvent.getSource()==btnTrnSave){
            cols.clear();
            vals.clear();
            if(!txtTrnCusID.getText().equals("")) {
                cols.add("customer_id");
                cols.add("date");
                cols.add("weight");
                cols.add("adv_payment");

                vals.add(txtTrnCusID.getText());
                vals.add(txtTrnDate.getText());
                vals.add(txtTrnGrossWeight.getText());
                vals.add(txtTrnAdvance.getText());

                if(txtTrnAdvance.getText().equals("")&&txtTrnGrossWeight.getText().equals("")){
                    openDialog(NOT_FOUND_MSG,actionEvent,"Fields");
                } else {
                    insertTransactions(cols, vals);
                }

                if(!txtTrnAdvance.getText().equals("")){
                    cols.clear();
                    vals.clear();
                    cols.add("date");
                    vals.add(txtTrnDate.getText());
                    cols.add("user_id");
                    vals.add(txtTrnCusID.getText());
                    cols.add("type");
                    vals.add("ADV");
                    cols.add("price");
                    vals.add(txtTrnAdvance.getText());
                    if(rdOther ==((RadioButton)tgExpenses.getSelectedToggle())){
                        cols.add("comment");
                        vals.add(txtExpComments.getText());
                    }else {
                        System.out.println("Not others");
                    }
                    insertExpenses(cols,vals);
                } else {
                    System.out.println("ADV|"+txtTrnAdvance.getText()+"|");
                }

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Date date = new Date();
                txtTrnDate.setText(dateFormat.format(date));
                txtTrnGrossWeight.setText("");
                txtTrnAdvance.setText("");
            } else {
                openDialog(NOT_FOUND_MSG,actionEvent,"User ID");
            }
        }

        //region MonthPicker
        if(actionEvent.getSource() == btnMonth){
            showMonthPicker(actionEvent);
        }
        //endregion

        //endregion

        //region Expenses
        if (actionEvent.getSource() == btnExpense) {
            pnlExpenses.setStyle("-fx-background-color : #02030A");
            pnlExpenses.toFront();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            btnExpMonth.setText(dateFormat.format(date));
        }

        if (actionEvent.getSource() == btnExpSave) {
            if(((RadioButton)tgExpenses.getSelectedToggle()).getAccessibleText().equals("FER") && Integer.valueOf(txtExpNoUnits.getText()) > 50){
                int year = Integer.valueOf(btnExpMonth.getText().split("/")[0]);
                int month = Integer.valueOf(btnExpMonth.getText().split("/")[1]);
                int day = Integer.valueOf(btnExpMonth.getText().split("/")[2]);

                String mnth = String.valueOf(month);
                if(month < 10){
                    mnth = "0"+month;
                }

                int amount = Integer.valueOf(lblTotPrice.getText());

                cols.clear();
                vals.clear();
                cols.add("date");
                vals.add(year+"/"+mnth+"/"+day);
                cols.add("user_id");
                vals.add(txtExpUserID.getText());
                cols.add("type");
                vals.add(((RadioButton)tgExpenses.getSelectedToggle()).getAccessibleText());
                cols.add("price");
                vals.add(""+amount/2);
                if(rdOther ==((RadioButton)tgExpenses.getSelectedToggle())){
                    cols.add("comment");
                    vals.add(txtExpComments.getText());
                }else {
                    System.out.println("Not others");
                }
                insertExpenses(cols,vals);
                month++;
                if(month >= 12){
                    month = 1;
                }

                mnth = String.valueOf(month);
                if(month < 10){
                    mnth = "0"+month;
                }

                cols.clear();
                vals.clear();
                cols.add("date");
                vals.add(year+"/"+mnth+"/"+day);
                cols.add("user_id");
                vals.add(txtExpUserID.getText());
                cols.add("type");
                vals.add(((RadioButton)tgExpenses.getSelectedToggle()).getAccessibleText());
                cols.add("price");
                vals.add(""+amount/2);
                if(rdOther ==((RadioButton)tgExpenses.getSelectedToggle())){
                    cols.add("comment");
                    vals.add(txtExpComments.getText());
                }else {
                    System.out.println("Not others");
                }
                insertExpenses(cols,vals);
            } else{
                cols.clear();
                vals.clear();
                cols.add("date");
                vals.add(btnExpMonth.getText());
                cols.add("user_id");
                vals.add(txtExpUserID.getText());
                cols.add("type");
                vals.add(((RadioButton)tgExpenses.getSelectedToggle()).getAccessibleText());
                cols.add("price");
                vals.add(lblTotPrice.getText());
                if(rdOther ==((RadioButton)tgExpenses.getSelectedToggle())){
                    cols.add("comment");
                    vals.add(txtExpComments.getText());
                }else {
                    System.out.println("Not others");
                }
                insertExpenses(cols,vals);
            }

//            ReportPrinter reportPrinter = new ReportPrinter();
////            reportPrinter.print_month_report("D:/print.pdf");
//            reportPrinter.printJasper();
//            System.out.println(((RadioButton)tgExpenses.getSelectedToggle()).getAccessibleText());

        }
        //endregion

        //region Payslips
        if (actionEvent.getSource() == btnPayslips) {
            pnlPayslips.setStyle("-fx-background-color : #02030A");
            pnlPayslips.toFront();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
            Date date = new Date();
            String fDate = dateFormat.format(date);
            btnPayMonth.setText(fDate);

            if(checkRatesAvailability(fDate.split("/")[0],fDate.split("/")[1])){
                System.out.println(results);
                txtPayPrice.setText(results.get(0).get(2));
                txtPayTransport.setText(results.get(0).get(3));
                txtPayCoreLeaf.setText(results.get(0).get(4));
                pnlPayTempCover.setVisible(false);
            }

            txtPayUser.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if(event.getCode().equals(KeyCode.ENTER)){
                        System.out.println("Fetch");
                        String where = "ID = "+txtPayUser.getText();
                        fetchCustomers(null,where);
                        if(results.size()==0){
                            openDialog(NOT_FOUND_MSG,event,"User");
                        }else {
                            System.out.println(results);
                            txtPayUser.setText(results.get(0).get(0));
                            lblPayUserName.setText(results.get(0).get(2));

                            String wh = "month =" + btnPayMonth.getText().split("/")[0] + "  AND year = "+btnPayMonth.getText().split("/")[0]+" AND user_id = " + txtPayUser.getText();
                            fetchMonthyOverview(wh);
                        }

                    }
                }
            });

        }

        if(actionEvent.getSource()==btnPayMonth){
            System.out.println("Show month");
            clickedButton = btnPayMonth;
            showMonthPicker(actionEvent);
        }

        if(actionEvent.getSource()==btnMonthlyRatesSave){
            cols.clear();
            vals.clear();

            if(!txtPayPrice.getText().equals("") && !txtPayTransport.getText().equals("") && !txtPayCoreLeaf.getText().equals("")) {
                cols.add("year");
                cols.add("month");
                cols.add("price");
                cols.add("transport");
                cols.add("core_leaf");

                vals.add(btnPayMonth.getText().split("/")[0]);
                vals.add(btnPayMonth.getText().split("/")[1]);
                vals.add(txtPayPrice.getText());
                vals.add(txtPayTransport.getText());
                vals.add(txtPayCoreLeaf.getText());
            }

            insertUpdateMonthlyRates(cols,vals,"", 2);

            generateMonthlyDetails(btnPayMonth.getText().split("/")[0],btnPayMonth.getText().split("/")[1]);

            pnlPayTempCover.setVisible(false);
        }
        //endregion

        //region Annual Report
        if (actionEvent.getSource() == btnAnnual) {
            pnlAnnualReport.setStyle("-fx-background-color : #02030A");
            pnlAnnualReport.toFront();
        }
        //endregion

        //region Signout
        if(actionEvent.getSource()==btnSignout) {
            System.out.println("Signout button");
            openDialog(SIGNOUT_MSG,actionEvent);
        }
        //endregion

        //region Close
        if(actionEvent.getSource() == btnClose){
            System.out.println("EXITING");
            Platform.exit();
        }
        //endregion
    }

    private void showMonthPicker(ActionEvent actionEvent){
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

    public void performMouseClick(MouseEvent event){
        if(event.getSource() == btnCusSearch){
            cols.clear();
            vals.clear();

            String where = "id = '"+txtCusSearch.getText()+"' OR name like '%"+txtCusSearch.getText()+"%' OR mobile = '"+txtCusSearch.getText()+"' OR nic = '"+txtCusSearch.getText()+"'";
            fetchCustomers(null,where);

            System.out.println(results);
            System.out.println(results.size());
            if(results.size() == 1) {
                txtCusNIC.setEditable(false);
                txtCusName.setEditable(false);
                txtCusMobile.setEditable(false);
                txtCusAccount.setEditable(false);
                txtCusAddress.setEditable(false);
                lblCusID.setVisible(true);
                txtCusID.setVisible(true);
                txtCusNIC.setText(results.get(0).get(1));
                txtCusName.setText(results.get(0).get(2));
                txtCusMobile.setText(results.get(0).get(3));
                txtCusAccount.setText(results.get(0).get(4));
                txtCusAddress.setText(results.get(0).get(5));
                txtCusID.setText(results.get(0).get(0));
                btnCustSave.setDisable(true);
                btnCusEdit.setDisable(false);
            }
        }
    }

    //region text change handler
    public void textChangeHandler(javafx.scene.input.KeyEvent event){
        if(event.getSource()==txtExpNoUnits) {
            int units = 0;
            try{
                units = Integer.valueOf(txtExpNoUnits.getText()) * Integer.valueOf(txtExpUnitPrice.getText());
            }catch (Exception e){

            }
            lblTotPrice.setText(String.valueOf(units));
        }

        if(event.getSource()==txtExpDirPrice) {
            lblTotPrice.setText(txtExpDirPrice.getText());
        }
    }
    //endregion

    //region radio button controller
    public void radioHandler(ActionEvent event){
         if(rdAdvance.isSelected()){
             txtExpUnitPrice.setVisible(false);
             lblUnitPrice.setVisible(false);
             txtExpNoUnits.setVisible(false);
             lblNoUnits.setVisible(false);
             txtExpDirPrice.setVisible(true);
             lblFullPrice.setVisible(true);
             txtExpComments.setVisible(false);
             lblComments.setVisible(false);
             lblTotPrice.setVisible(false);
         } else if(rdOther.isSelected()){
             txtExpUnitPrice.setVisible(false);
             lblUnitPrice.setVisible(false);
             txtExpNoUnits.setVisible(false);
             lblNoUnits.setVisible(false);
             txtExpDirPrice.setVisible(true);
             lblFullPrice.setVisible(true);
             txtExpComments.setVisible(true);
             lblComments.setVisible(true);
             lblTotPrice.setVisible(false);
         } else {
             txtExpUnitPrice.setVisible(true);
             lblUnitPrice.setVisible(true);
             txtExpNoUnits.setVisible(true);
             lblNoUnits.setVisible(true);
             txtExpDirPrice.setVisible(false);
             lblFullPrice.setVisible(false);
             txtExpComments.setVisible(false);
             lblComments.setVisible(false);
             lblTotPrice.setVisible(true);
         }
    }
    //endregion

    //region DisplayMonth
    public static void setMonth(int yr, int mnth){
        year = yr;
        month = mnth;
        getInstance().monthUpdater();
    }

    public void monthUpdater(){
        if(month<10){
            clickedButton.setText(year + "/0" + month);
        }else {
            clickedButton.setText(year + "/" + month);
        }
        if(checkRatesAvailability(String.valueOf(year),String.valueOf(month))){
            txtPayPrice.setText(results.get(0).get(2));
            txtPayTransport.setText(results.get(0).get(3));
            txtPayCoreLeaf.setText(results.get(0).get(4));
            pnlPayTempCover.setVisible(false);
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

    private void updateCustomers(List<String> cols, List<String> values, String where){
        results = new DBOperations().executeQuery(DBOperations.TYPE_UPDATE,"customer",cols,values, null, where);
    }

    private void insertExpenses(List<String> cols, List<String> values){
        results = new DBOperations().executeQuery(DBOperations.TYPE_INSERT,"expenses",cols,values, null, null);
    }

    private void fetchTransactions(List<String> cols, String where){
        String join = " INNER JOIN customer ON t.customer_id = customer.id ";
        results = new DBOperations().executeQuery(DBOperations.TYPE_SELECT,"transactions t",cols, null,join, where);
        s1 = true;
    }

    private void insertTransactions(List<String> cols, List<String> values){
        results = new DBOperations().executeQuery(DBOperations.TYPE_INSERT,"transactions",cols,values, null,null);
    }

    private void fetchMonthlyRates(List<String> cols, String where){
        results = new DBOperations().executeQuery(DBOperations.TYPE_SELECT,"monthly_rates",cols, null,null, where);
    }

    private void insertUpdateMonthlyRates(List<String> cols, List<String> values, String where, int primaries){
        results = new DBOperations().executeQuery(DBOperations.TYPE_INSERT_UPDATE,"monthly_rates",cols, values,null, where,primaries);
    }

    private boolean checkRatesAvailability(String year, String month){
        fetchMonthlyRates(null,"year = '"+year+"' AND month = '"+month+"'");
        if(results.size()>0){
            return true;
        }
        return false;
    }

    private void fetchTransactionSum(List<String> cols, String where){
        results = new DBOperations().executeQuery(DBOperations.TYPE_SELECT,"transactions t",cols, null,null, where);
        set1 = results;
    }

    private void fetchExpenses(List<String> cols, String where){
        results = new DBOperations().executeQuery(DBOperations.TYPE_SELECT,"expenses e",cols, null, null, where);
        set2 = results;
    }

    private void insertOverview(List<String> cols, List<String> values){
        results = new DBOperations().executeQuery(DBOperations.TYPE_INSERT,"monthly_overview",cols,values, null,null);
    }

    private void fetchMonthyOverview(String where){
        results = new DBOperations().executeQuery(DBOperations.TYPE_SELECT,"monthly_overview",null, null, null, where);
        set2 = results;
    }
    //endregion

    private void openDialog(int type, Event event, String... opt){
        try {
            Stage stage = new Stage();
            Parent root = null;
            stage.initStyle(StageStyle.UNDECORATED);
            DialogController.setType(type,opt);
            root = FXMLLoader.load(
                    DialogController.class.getResource("ModalDialog.fxml"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node)event.getSource()).getScene().getWindow() );
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateMonthlyDetails(String year, String month){
        cols.clear();

        cols.add("customer_id");
        cols.add("SUM(weight) as weight");

        String where = "MONTH(t.date) = "+month+" AND YEAR(t.date) = "+year+" GROUP BY t.customer_id ORDER BY t.customer_id ASC";

        fetchTransactionSum(cols,where);

        cols.clear();

        cols.add("user_id");
        cols.add("sum(price) as price");
        cols.add("type");

        where = "MONTH(e.date) = "+month+" AND YEAR(e.date) = "+year+" GROUP BY e.user_id, e.type ORDER BY e.user_id ASC";

        fetchExpenses(cols,where);
        System.out.println("SET1 : "+set1);
        System.out.printf("SET2 : "+set2);
        int expenses = 0;
        boolean init = false;
        for(int i = 0;i < set1.size();i++){
            cols.clear();
            vals.clear();
            cols.add("year");
            vals.add(year);
            cols.add("month");
            vals.add(month);
            cols.add("user_id");
            vals.add(set1.get(i).get(0));
            cols.add("kilo");
            vals.add(set1.get(i).get(1));
            System.out.println(set2.get(expenses).get(0)+" VS "+set1.get(i).get(0));
            init = true;
            while (init) {
                if(!set2.get(expenses).get(0).equals(set1.get(i).get(0)) || expenses >= set2.size()) {
                    break;
                }
                System.out.println("IN : "+set2.get(expenses).get(2));
                switch (set2.get(expenses).get(2)) {
                    case "ADV":
                        cols.add("adv");
                        vals.add(set2.get(expenses).get(1));
                        break;
                    case "FER":
                        cols.add("fer");
                        vals.add(set2.get(expenses).get(1));
                        break;
                    case "POI":
                        cols.add("poison");
                        vals.add(set2.get(expenses).get(1));
                        break;
                    case "OTH":
                        cols.add("dec_other");
                        vals.add(set2.get(expenses).get(1));
                        break;
                    case "DOL":
                        cols.add("dolamite");
                        vals.add(set2.get(expenses).get(1));
                        break;
                    case "TEA":
                        cols.add("tea");
                        vals.add(set2.get(expenses).get(1));
                        break;
                    case "TRA":
                        cols.add("transport");
                        vals.add(set2.get(expenses).get(1));
                        break;
                    default:
                        break;
                }
                expenses++;
                if(expenses >= set2.size()){
                    init = false;
                }
            }

        insertOverview(cols,vals);

        }

    }
}
