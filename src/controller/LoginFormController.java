package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane LoginContext;
    public JFXPasswordField TxtPassword;
    public JFXTextField TxtUserName;
    public Label LblInvalidLogin;


    public void BtnSignInOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        User user = new User(TxtUserName.getText(),TxtPassword.getText());
        String role = new UserController().login(user);

        if(role.equals("Admin")){
            LblInvalidLogin.setVisible(false);
            URL resource = getClass().getResource("../view/AdminDashBoardForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) LoginContext.getScene().getWindow();
            window.setScene(new Scene(load));
            window.centerOnScreen();
            window.setResizable(false);

        }else if (role.equals("Cashier")){
            LblInvalidLogin.setVisible(false);
            URL resource = getClass().getResource("../view/CashierDashBoardForm.fxml");
            Parent load = FXMLLoader.load(resource);
            Stage window = (Stage) LoginContext.getScene().getWindow();
            window.setScene(new Scene(load));
            window.centerOnScreen();
            window.setResizable(false);
        }else{
            new NotificationController().set("Login Failed","\tSomething went wrong.Try Again",4,12);
            LblInvalidLogin.setVisible(true);
        }

    }


}
