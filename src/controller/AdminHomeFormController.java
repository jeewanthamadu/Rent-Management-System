package controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.RentDetail;
import model.Vehicle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.ReturnTM;
import view.tm.VehicleTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AdminHomeFormController {
    public StackPane adminHomeContext;
    public Label lblDate;
    public Label lblTime;
    public TableView tblDashBoard;
    public TableColumn colVehicleNumber;
    public TableColumn colModelID;
    public TableColumn colCategoryID;
    public TableColumn colDescription;
    public TableColumn colCost;
    public JFXTextField txtSearchBar;
    public Label lblRentItems;
    public Label lblCustomers;
    public Label lblDrivers;
    public JFXDatePicker fromDate;
    public JFXDatePicker toDate;


    public void initialize()  {
        loadDateAndTime();
        try {
            addTable();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            lblCustomers.setText(new DashBoardController().getCountCustomer());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            lblDrivers.setText(new DashBoardController().getCountDriver());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            lblRentItems.setText(new DashBoardController().getCountVehicles());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void btnLogout(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage =(Stage) this.adminHomeContext.getScene().getWindow();
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
    public void addTable() throws SQLException, ClassNotFoundException {
        ObservableList<VehicleTM>availableList = new DashBoardController().getAvailableDetails();
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colCategoryID.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colModelID.setCellValueFactory(new PropertyValueFactory<>("modelId"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        tblDashBoard.setItems(availableList);

    }
    public void searchBar(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<Vehicle> vehicleList = new DashBoardController().searchAvailable(txtSearchBar.getText());
        ObservableList<Vehicle> tblData = FXCollections.observableArrayList();
        for (Vehicle vehicle : vehicleList) {
            tblData.add(new Vehicle(
                    vehicle.getVehicleNumber(),
                    vehicle.getDescription(),
                    vehicle.getCost(),
                    vehicle.getModelId(),
                    vehicle.getCategoryId()
            ));
        }
        tblDashBoard.setItems(tblData);
    }

    public void CustomerDetailsOnClick(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/CustomerDetails.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void DriverDetailsOnClick(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/Driver.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void btnFinancialReportOnAction(ActionEvent actionEvent) {
        LocalDate fromDateValue = fromDate.getValue();
        LocalDate toDateValue = toDate.getValue();
        String from = String.valueOf(fromDateValue);
        String to = String.valueOf(toDateValue);
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/FinancialReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            HashMap map=new  HashMap();
            map.put("fromDate",fromDateValue);
            map.put("toDate",toDateValue);
            
            map.put("from",from);
            map.put("to",to);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
