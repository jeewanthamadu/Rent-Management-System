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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.DriverSalary;
import model.RentDetail;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.DriverSalaryTM;
import view.tm.DriverTM;
import view.tm.RentFormTM;
import view.tm.ReturnTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DriverPaymentFormController {

    public StackPane driverSalaryContext;
    public TableView tblDriverSalary;
    public TableColumn colRentId;
    public TableColumn colDriverID;
    public TableColumn colName;
    public TableColumn colSalary;
    public Label lblDate;
    public Label lblTime;
    public TextField txtSearch;


    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();
        addTable();
    }

    public ObservableList<DriverSalaryTM>getDriverSalaries() throws SQLException, ClassNotFoundException {
        ObservableList<DriverSalaryTM> observableList = FXCollections.observableArrayList();
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Driver_Salary ");
        ResultSet rst= pst.executeQuery();
        while (rst.next()){
            observableList.add(new DriverSalaryTM(
                    rst.getString(1),
                    Double.parseDouble(rst.getString(2)),
                    rst.getString(3),
                    rst.getString(4)

            ));
        }
        return observableList;
    }

    public void addTable() throws SQLException, ClassNotFoundException {
        ObservableList<DriverSalaryTM> observableList = getDriverSalaries();

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDriverID.setCellValueFactory(new PropertyValueFactory<>("driverID"));
        colRentId.setCellValueFactory(new PropertyValueFactory<>("rent_ID"));

        tblDriverSalary.setItems(observableList);
    }

    public void btnLogout(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage =(Stage) this.driverSalaryContext.getScene().getWindow();
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

    public void txtSearch(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<DriverSalary> driverSalaries = new DriverController().searchDriverSalaryHistory(txtSearch.getText());
        ObservableList<DriverSalaryTM> tblData = FXCollections.observableArrayList();
        for (DriverSalary driverSalary: driverSalaries) {
            tblData.add(new DriverSalaryTM(
                    driverSalary.getName(),
                    driverSalary.getSalary(),
                    driverSalary.getDriverID(),
                    driverSalary.getRent_ID()
            ));
        }
        tblDriverSalary.setItems(tblData);

    }

    public void btnReport(MouseEvent mouseEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/DriverSalaryReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
