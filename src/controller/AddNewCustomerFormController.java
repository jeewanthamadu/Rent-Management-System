package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Customer;
import util.Validation;

import java.sql.SQLException;
import java.util.LinkedHashMap;

import java.util.regex.Pattern;

public class AddNewCustomerFormController {

    public JFXTextField txtCustomerId;
    public JFXTextField TxtCustomerName;
    public JFXTextField txtCustomerTele_Number;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerNic;
    public Label lblTryAgain;
    public AnchorPane AddCustomerContext;
    public JFXButton btnAddCustomer;
    public ImageView n;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern customerName = Pattern.compile("^[A-z ]{3,30}$");
    Pattern customerNic = Pattern.compile("^[0-9]{9}[v]|[0-9]{12}$");
    Pattern customerAddress = Pattern.compile("^[A-z0-9/]{6,30}$");
    Pattern customerTeleNumber = Pattern.compile("^[0-9]{10}$");

    public void initialize() {
        btnAddCustomer.setDisable(true);
        setCustomerId();
        storeValidations();
    }

    private void storeValidations() {
        map.put(TxtCustomerName, customerName);
        map.put(txtCustomerNic, customerNic);
        map.put(txtCustomerAddress, customerAddress);
        map.put(txtCustomerTele_Number, customerTeleNumber);
    }

    private void setCustomerId() {
        try {
            txtCustomerId.setText(new CustomerController().getCustomerId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void BtnAddCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(txtCustomerId.getText(),
                TxtCustomerName.getText(),
                txtCustomerNic.getText(),
                txtCustomerAddress.getText(),
                txtCustomerTele_Number.getText()
        );
        if (new CustomerController().saveCustomer(customer)) {
            setCustomerId();
            lblTryAgain.setVisible(false);
            new NotificationController().set("Confirmation", "Customer Saved Successfully", 0, 9);
        } else {
            lblTryAgain.setVisible(true);
            new NotificationController().set("Error", "something went wrong", 0, 6);
        }
        clearFields();
    }

    public void BtnCloseOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) AddCustomerContext.getScene().getWindow();
        stage.close();
    }

    public void clearFields() {
        txtCustomerAddress.clear();
        txtCustomerNic.clear();
        txtCustomerTele_Number.clear();
        TxtCustomerName.clear();
        txtCustomerId.requestFocus();
    }

    public void txtCustomerKeyRelease(KeyEvent keyEvent) {
        btnAddCustomer.setDisable(true);
        Object response = Validation.validate(map, btnAddCustomer,"green");
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField error = (TextField) response;
                error.requestFocus();
            } else if (response instanceof Boolean) {
                new NotificationController().set("Fill Completed", "Continue Your Process ", 0, 9);
            }
        }
    }



}






