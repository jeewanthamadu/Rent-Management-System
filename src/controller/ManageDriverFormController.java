package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Driver;
import util.Validation;
import view.tm.DriverTM;


import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageDriverFormController {
    public StackPane manageDriverContext;

    public Label lblDate;
    public Label lblTime;
    public TextField txtUserName;
    public TextField txtName;
    public TextField txtNic;
    public TextField txtTeleNo;
    public TextField txtDailyCost;
    public TableView<DriverTM> tblDriver;
    public TableColumn colDUserName;
    public TableColumn colDName;
    public TableColumn colDNic;
    public TableColumn colDTeleNum;
    public TableColumn colDDailyCost;
    public TableColumn colStatus;
    public TextField txtStatus;
    public JFXButton btnAdd;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();


    Pattern userName = Pattern.compile("^[A-z0-9]{3,20}$");
    Pattern teleNumber = Pattern.compile("^[0-9]{10}$");
    Pattern name = Pattern.compile("^[A-z ]{3,30}$");
    Pattern dailyCost = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern nic = Pattern.compile("^[0-9]{9}[v]|[0-9]{12}$");


    public void initialize() throws SQLException, ClassNotFoundException {
        btnAdd.setDisable(true);
        loadDateAndTime();
        addTable();
        storeValidations();
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e->{
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour()+ " : "+currentTime.getMinute()+ " : "+currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void storeValidations() {
        map.put(txtUserName,userName);
        map.put(txtTeleNo,teleNumber);
        map.put(txtName,name);
        map.put(txtDailyCost,dailyCost);
        map.put(txtNic,nic);
    }

    public void clearField() {
        txtUserName.clear();
        txtName.clear();
        txtTeleNo.clear();
        txtNic.clear();
        txtDailyCost.clear();
        txtUserName.requestFocus();
    }

    public void addTable() throws SQLException, ClassNotFoundException {

        ObservableList<DriverTM> DriverTMS = DriverController.getAllUsers();

        colDUserName.setCellValueFactory(new PropertyValueFactory<>("driverUserName"));
        colDName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colDTeleNum.setCellValueFactory(new PropertyValueFactory<>("teleNum"));
        colDDailyCost.setCellValueFactory(new PropertyValueFactory<>("dailyCost"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblDriver.setItems(DriverTMS);

    }

    public void btnAddonAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Driver driver = new Driver(
                txtUserName.getText(),
                txtName.getText(),
                Double.parseDouble(txtDailyCost.getText()),
                txtNic.getText(),
                txtTeleNo.getText(),
                txtStatus.getText()
        );

        if (new DriverController().saveDriver(driver)){
            new NotificationController().set("Success","\tDriver Saved Successfully",0,9);
            addTable();
            clearField();
        }else {
            new NotificationController().set("something went wrong","\tPlease Try Again",0,7);
        }
        clearField();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        DriverTM driverTM = new DriverTM(
                txtUserName.getText(),
                txtName.getText(),
                Double.parseDouble(txtDailyCost.getText()),
                txtNic.getText(),
                txtTeleNo.getText(),
                txtStatus.getText()

        );
        if(new  DriverController().updateDriver(driverTM)){
            addTable();
            clearField();
            new NotificationController().set("Update","\tDriver Update Successfully",0,3);
        }else {
            new NotificationController().set("something went wrong","\tTry again",0,7);
        }
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        DriverTM getSelectRow = tblDriver.getSelectionModel().getSelectedItem();
        String driverName = getSelectRow.getDriverUserName();
        if(new DriverController().deleteDriver(driverName)){
            new NotificationController().set("Removed","\tDriver Removed Successfully",0,1);
            addTable();
            clearField();
        }else{
            new NotificationController().set("something went wrong","\tTry again",0,7);
        }
    }

    public void tblDriverOnMouseClick(MouseEvent mouseEvent) {
        DriverTM driverTM= null;
        driverTM = tblDriver.getSelectionModel().getSelectedItem();
        txtUserName.setText(driverTM.getDriverUserName());
        txtName.setText(driverTM.getName());
        txtNic.setText(driverTM.getNic());
        txtTeleNo.setText(driverTM.getTeleNum());
       txtDailyCost.setText(""+driverTM.getDailyCost());
    }

    public void btnLogout(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage =(Stage) this.manageDriverContext.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void txtDriverKeyRelease(KeyEvent keyEvent) {
        btnAdd.setDisable(true);
        Object response = Validation.validate(map,btnAdd,"white");
        if (keyEvent.getCode()== KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField error = (TextField) response;
                error.requestFocus();
            } else if (response instanceof Boolean) {
                new NotificationController().set("Fill Completed", "Continue Your Process ", 0, 9);
            }
        }
    }


}

