package controller;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
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
import model.User;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.Validation;
import view.tm.UserTM;

import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;


public class ManageUserFormController {
    public StackPane manageUserContext;
    public TextField txtUserName;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtTeleNo;
    public TextField txtUserPassword;
    public Label lblDate;
    public Label lblTime;
    public TableView<UserTM> tblUser;
    public TableColumn colUserName;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colTelePhoneNumber;
    public TableColumn colRole;
    public  ComboBox<String> cmbRole;
    public JFXButton btnAdd;
    String getRole;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern name = Pattern.compile("^[A-z ]{3,20}$");
    Pattern address = Pattern.compile("^[A-z 0-9 \\/\\,]{2,50}[A-z 0-9]{1,50}$");
    Pattern teleNumber = Pattern.compile("^[0-9]{10}$");
    Pattern username = Pattern.compile("^[A-z0-9]{3,20}$");
    Pattern password = Pattern.compile("[A-Za-z0-9]{5,15}$");

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();
        addTable();
        cmbRole.getItems().setAll("Admin","Cashier");
        cmbRole.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            getRole=newValue;
        });
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtName,name);
        map.put(txtAddress,address);
        map.put(txtTeleNo,teleNumber);
        map.put(txtUserName,username);
        map.put(txtUserPassword,password);
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

    public void addTable() throws SQLException, ClassNotFoundException {

        ObservableList<UserTM> userTMS = UserController.getAllUsers();
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colTelePhoneNumber.setCellValueFactory(new PropertyValueFactory<>("teleNumber"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("Role"));
        tblUser.setItems(userTMS);
    }

    public void clearField() {
        txtUserName.clear();
        txtAddress.clear();
        cmbRole.setValue(" ");
        txtName.clear();
        txtTeleNo.clear();
        txtUserPassword.clear();
        txtUserName.requestFocus();
    }

    public void btnAddonAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
                User user = new User(
                txtUserName.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtTeleNo.getText(),
                getRole,
                txtUserPassword.getText()
        );
        if (new UserController().saveUser(user)){
            new NotificationController().set("Success","\tUser Saved Successfully",0,9);
            addTable();
        }else {
           new NotificationController().set("something went wrong","\tPlease Try Again",0,7);
        }
        clearField();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        UserTM userTM = new UserTM(
                txtUserName.getText(),
                txtName.getText(),
                txtAddress.getText(),
                txtTeleNo.getText(),
                getRole
        );
        if(new  UserController().updateUser(userTM)){
            addTable();
            clearField();
            new NotificationController().set("Update","\tUser Update Successfully",0,3);
        }else {
            new NotificationController().set("something went wrong","\tTry again",0,7);
        }
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        UserTM getSelectRow = tblUser.getSelectionModel().getSelectedItem();
        String userName = getSelectRow.getUserName();
        if(new UserController().deleteUsers(userName)){
            new NotificationController().set("Removed","\tUser Removed Successfully",0,1);
            addTable();
            clearField();
        }else{
           new NotificationController().set("something went wrong","\tTry again",0,7);
        }
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage =(Stage) this.manageUserContext.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void tblOnMouseClick(MouseEvent mouseEvent) {
        UserTM userTM = tblUser.getSelectionModel().getSelectedItem();
        txtUserName.setText(userTM.getUserName());
        txtName.setText(userTM.getName());
        txtAddress.setText(userTM.getAddress());
        txtTeleNo.setText(userTM.getTeleNumber());
        cmbRole.setValue(userTM.getRole());
    }

    public void txtUserKeyRelease(KeyEvent keyEvent) {
        btnAdd.setDisable(true);
        Object response = Validation.validate(map,btnAdd,"white");
        if (keyEvent.getCode()== KeyCode.ENTER) {
            if (response instanceof TextField){
                TextField error  = (TextField) response;
                error.requestFocus();
            }else if (response instanceof Boolean){
                new NotificationController().set("Fill Completed", "Continue Your Process ", 0, 9);
            }
        }

    }

    public void UserShowDetailsOnClick(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

        try{
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/UsersDetails.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        }

    }

}
