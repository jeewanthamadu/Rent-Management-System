package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class CashierDashBoardFormController {

    public AnchorPane cashierMainContext;
    public ToggleButton btnCashierHomeID;

    public void initialize(){
            btnCashierHomeID.fire();
    }

    public void btnManageRentFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierRentManageForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierMainContext.getChildren().clear();
        cashierMainContext.getChildren().add(load);

    }

    public void btnManageReturnFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/CashierReturnVehicleForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierMainContext.getChildren().clear();
        cashierMainContext.getChildren().add(load);
    }

    public void btnRentHistoryOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RentHistoryForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierMainContext.getChildren().clear();
        cashierMainContext.getChildren().add(load);
    }

    public void btnCashierHomeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminHomeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierMainContext.getChildren().clear();
        cashierMainContext.getChildren().add(load);
    }

    public void btnDriverPaymentFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/DriverPaymentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        cashierMainContext.getChildren().clear();
        cashierMainContext.getChildren().add(load);

    }
}
