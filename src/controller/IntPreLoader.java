package controller;
import animatefx.animation.Bounce;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IntPreLoader implements Initializable {
    public Label lblLoading;

    public static Label lblLoading1;
    @FXML
    public Circle circle01;
    @FXML
    public Circle circle02;
    public Circle circle03;
    public Circle circle04;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblLoading1=lblLoading;
        new Bounce(circle01).setCycleCount(4).setDelay(Duration.valueOf("500ms")).play();
        new Bounce(circle02).setCycleCount(4).setDelay(Duration.valueOf("1000ms")).play();
        new Bounce(circle03).setCycleCount(4).setDelay(Duration.valueOf("1100ms")).play();
        new Bounce(circle04).setCycleCount(4).setDelay(Duration.valueOf("1300ms")).play();
    }

    public String checkFunction(){
        final String[] massage={""};
        Thread t1=new Thread(() -> {
            massage[0]="Loading";
            Platform.runLater(() -> lblLoading1.setText(massage[0]));
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2=new Thread(() -> {
            massage[0]="Loading Successfully.";
            Platform.runLater(() -> lblLoading1.setText(massage[0]));
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3=new Thread(() -> {
            massage[0]="Opening..";
            Platform.runLater(() -> lblLoading1.setText(massage[0]));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(150);
                        Stage stage=new Stage();
                        Parent root = FXMLLoader.load(getClass().getResource("../view/loginForm.fxml"));
                        Scene scene=new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        });

        try {
            t1.start();
            t1.join();
            t2.start();
            t2.join();
            t3.start();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return massage[0];
    }
}
