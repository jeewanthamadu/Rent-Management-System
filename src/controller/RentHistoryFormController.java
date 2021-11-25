package controller;

import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.RentDetail;
import view.tm.ReturnTM;
import view.tm.UserTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RentHistoryFormController {
    public StackPane RentHistoryContext;
    public Label lblDate;
    public Label lblTime;
    public TableView<ReturnTM> tblHistory;
    public TableColumn colRentId;
    public TableColumn colRentDate;
    public TableColumn colReturnDate;
    public TableColumn colDriverId;
    public TableColumn colVehicleNumber;
    public TableColumn colTotal;
    public TableColumn colStatus;
    public TextField txtSearch;


    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();
        addTable();
    }
    public void btnLogout(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage =(Stage) this.RentHistoryContext.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
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
    public ObservableList<ReturnTM>getItems() throws SQLException, ClassNotFoundException {
        ObservableList<ReturnTM> observableList = FXCollections.observableArrayList();
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Rent_Detail ");
        ResultSet rst= pst.executeQuery();
        while (rst.next()){
            observableList.add(new ReturnTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    Double.parseDouble(rst.getString(6)),
                    rst.getString(7)
            ));
        }
        return observableList;
    }
    public void addTable() throws SQLException, ClassNotFoundException {
        ObservableList<ReturnTM> observableList = getItems();

        colRentId.setCellValueFactory(new PropertyValueFactory<>("rentID"));
        colRentDate.setCellValueFactory(new PropertyValueFactory<>("rentDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverID"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("Fine"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblHistory.setItems(observableList);
    }
    public void txtSearch(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<RentDetail> rentDetails = new HistoryController().searchRentHistory(txtSearch.getText());
        ObservableList<ReturnTM> tblData = FXCollections.observableArrayList();
        for (RentDetail rentDetail: rentDetails) {
            tblData.add(new ReturnTM(
                rentDetail.getRentID(),
                rentDetail.getRentDate(),
                rentDetail.getReturnDate(),
                rentDetail.getDriverID(),
                rentDetail.getVehicleNumber(),
                rentDetail.getTotal(),
                rentDetail.getStatus()
        ));
        }
        tblHistory.setItems(tblData);
    }


}
