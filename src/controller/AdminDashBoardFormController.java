package controller;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;


public class AdminDashBoardFormController {
    public AnchorPane adminMainContext;
    public ToggleButton btnAdminFormId;


    public void initialize() {
            btnAdminFormId.fire();

    }

    public void btnHomeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AdminHomeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminMainContext.getChildren().clear();
        adminMainContext.getChildren().add(load);
    }

    public void btnManageUserOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageUserForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminMainContext.getChildren().clear();
        adminMainContext.getChildren().add(load);
    }

    public void btnManageDriverOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageDriverForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminMainContext.getChildren().clear();
        adminMainContext.getChildren().add(load);

    }

    public void btnManageVehicleFormOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/ManageVehicleForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminMainContext.getChildren().clear();
        adminMainContext.getChildren().add(load);
    }


    public void btnRentHistoryOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/RentHistoryForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminMainContext.getChildren().clear();
        adminMainContext.getChildren().add(load);
    }

    public void btnSystemReportOnAction(ActionEvent actionEvent) throws IOException {
        /*URL resource = getClass().getResource("../view/SystemReportForm.fxml");
        Parent load = FXMLLoader.load(resource);
        adminMainContext.getChildren().clear();
        adminMainContext.getChildren().add(load);*/
    }
}
