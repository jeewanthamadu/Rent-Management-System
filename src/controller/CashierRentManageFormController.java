package controller;


import com.jfoenix.controls.JFXDatePicker;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Driver;
import model.Rent;
import model.RentDetail;
import model.Vehicle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import view.tm.RentFormTM;


import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class CashierRentManageFormController {

    public StackPane manageRentFormContext;
    public Label lblDate;
    public Label lblTime;
    public ComboBox<String> cmbCustomer;
    public ComboBox<String> cmbVehicleNumber;
    public TextField txtVehicleCost;
    public TextField txtVehicleDesc;
    public ComboBox<String> cmbDriverID;
    public TextField txtDriverName;
    public TextField txtRentId;
    public DatePicker rentDate;
    public DatePicker returnDate;
    public TableView<RentFormTM> tblRent;
    public TableColumn colVehicleNumber;
    public TableColumn colDescription;
    public TableColumn colCostDay;
    public TableColumn colDriverId;
    public TableColumn colCustomerId;
    public TableColumn colTotalDays;
    public TableColumn colTotalCost;
    public Label lblLastCost;
    public TextField txtCustomerID;
    public TextField txtSearchNic;
    Double  tot;
    String gD;
    String gV;
    int ttDays;

    static ObservableList<RentFormTM> parentCartObList = FXCollections.observableArrayList();

    public void initialize()  {
        disablePreviousDates((JFXDatePicker) rentDate);
        disablePreviousDates((JFXDatePicker) returnDate);
        loadDateAndTime();
        rentDate.setValue(LocalDate.now());
        try {
            loadVehicle();
            loadDriver();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbVehicleNumber.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setVehicleData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbDriverID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setDriverData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cmbDriverID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            gD =newValue;
        });

        cmbVehicleNumber.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            gV =newValue;
        });

        setRentID();
        setTableData();
    }

    private void disablePreviousDates(JFXDatePicker datePicker) {
        Callback<DatePicker, DateCell> callB = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        LocalDate today = LocalDate.now();
                        setDisable(empty || item.compareTo(today) < 0);
                    }

                };
            }
        };
        datePicker.setDayCellFactory(callB);
    }

    private void setTableData(){
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCostDay.setCellValueFactory(new PropertyValueFactory<>("cost1Day"));
        colDriverId.setCellValueFactory(new PropertyValueFactory<>("driverID"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colTotalDays.setCellValueFactory(new PropertyValueFactory<>("totalDays"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        //parentCartObList.clear();

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
    private void setRentID(){
        try {
            txtRentId.setText(new rentController().getRentId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnAddNewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("../view/AddNewCustomerForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene=new Scene(load);
        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle("Add New Customer");
        stage.show();
        stage.centerOnScreen();
        stage.setResizable(false);
    }

    private void loadVehicle () throws SQLException, ClassNotFoundException {
        List<String> vehicleNumber = new VehicleController().getAllVehicleDetails();
        cmbVehicleNumber.getItems().addAll(vehicleNumber);

    }
    private void setVehicleData (String vehicleData) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new VehicleController().passVehicle(vehicleData);
        if(vehicleData==null){
        }else {
            txtVehicleCost.setText(""+vehicle.getCost());
            txtVehicleDesc.setText(vehicle.getDescription());
        }
    }

    private void loadDriver () throws SQLException, ClassNotFoundException {
        List<String> DriverId = new DriverController().getAllDriverDetails();
        cmbDriverID.getItems().addAll(DriverId);
    }
    private void setDriverData (String driverData) throws SQLException, ClassNotFoundException {
        Driver driver = new DriverController().passDriver(driverData);
        if(driverData==null){
        }else {
            txtDriverName.setText(driver.getDriverUserName());
          }

    }


    public void btnLogout(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage =(Stage) this.manageRentFormContext.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnRentOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ArrayList<RentDetail> rentDetail = new ArrayList<>();

        for(RentFormTM temp : parentCartObList){
            ArrayList<Driver> drivers = new rentController().getDrivers(temp.getDriverID());
            ArrayList<Driver> driverArrayList =new ArrayList<>();
            for (Driver driver: drivers) {
                driverArrayList.add(new Driver(
                        driver.getDriverUserName(),
                        driver.getName(),
                        driver.getDailyCost()*ttDays,
                        driver.getNic(),
                        driver.getTeleNum(),
                        driver.getStatus()
                ));
            }

            rentDetail.add(new RentDetail(
                    txtRentId.getText(),
                    temp.getRentDate(),
                    temp.getReturnDate(),
                    temp.getDriverID(),
                    temp.getVehicleNumber(),
                    temp.getTotalCost(),
                    "Rent",
                    driverArrayList
            ));

        }
        Rent rent = new Rent(
                txtRentId.getText(),
                lblDate.getText(),
                txtCustomerID.getText(),
                gD,
                tot,
                rentDetail
        );
        if(new rentController().saveRent(rent)){
            setTableData();
            setRentID();
            try {
                JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/RentBill.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(design);
                ObservableList<RentFormTM> items = tblRent.getItems();
                String input = lblLastCost.getText();
                String rentId = txtRentId.getText();
                HashMap map=new  HashMap();
                map.put("RentId",rentId);
                map.put("Total",input);
                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map , new  JRBeanArrayDataSource(items.toArray()));
                JasperViewer.viewReport(jasperPrint,false);

            } catch (JRException e) {
                e.printStackTrace();
            }
            clearFields();
            parentCartObList.clear();
            setRentID();
            new NotificationController().set("Success","\tThis vehicle rent successfully",0,4);
        }else {
            new NotificationController().set("Error","\tsomething went wrong",0,6);
        }

    }
    public void btnAddTblOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(txtDriverName.getText().isEmpty()){
            new NotificationController().set("Error","\tDriver Field Is Empty",0,6);
            return;
        }if(returnDate.getValue() == null){
            new NotificationController().set("Error","\tAny date Field Is Empty",0,6);
            return;
        } if(txtCustomerID.getText().isEmpty()){
            new NotificationController().set("Error","\tMissing Customer",0,6);
            return;
        }

        long rentD = rentDate.getValue().toEpochDay();
        long returnD = returnDate.getValue().toEpochDay();
            String vehicleNumber = gV;
            String description = txtVehicleDesc.getText();
            Double cost01D = Double.parseDouble(txtVehicleCost.getText());
            String driverID = gD;
            String customerID =txtCustomerID.getText();
            int totalDays = (int) Math.abs(rentD-returnD);
            ttDays=totalDays;
            String rD =  rentDate.getValue().toString();
            String retD = returnDate.getValue().toString();
            Double totalCost = cost01D*totalDays;

            if(!(txtDriverName.getText().isEmpty() || txtDriverName.getText().equals(""))){
                ArrayList<Driver> driverArrayList = new rentController().getDrivers(driverID);
                Double dailyCost = driverArrayList.get(0).getDailyCost();
                double driverTotal = dailyCost * totalDays;
                totalCost+=driverTotal;
            }


        RentFormTM rentFormTM = new RentFormTM(
                vehicleNumber,
                description,
                cost01D,
                driverID,
                customerID,
                totalDays,
                totalCost,
                rD,
                retD
        );
        int rowNumber = isExists(rentFormTM);
        if(rowNumber==-1){
            parentCartObList.add(rentFormTM);
        }else {
            RentFormTM newTM = new RentFormTM(
                    vehicleNumber,
                    description,
                    cost01D,
                    driverID,
                    customerID,
                    totalDays,
                    totalCost,
                    rD,
                    retD
            );
            parentCartObList.remove(rowNumber);
            parentCartObList.add(newTM);
        }
        tblRent.setItems(parentCartObList);
        calculateCost();
    }

    private void clearFields() throws SQLException, ClassNotFoundException {
        txtDriverName.clear();
        txtVehicleDesc.clear();
        txtVehicleCost.clear();
        rentDate.setValue(LocalDate.now());
        returnDate.setValue(null);
        cmbVehicleNumber.getItems().clear();
        loadVehicle();
        cmbDriverID.getItems().clear();
        loadDriver();
    }


    private void calculateCost(){
        double totalLast = 0;
        for (RentFormTM rentFormTM:parentCartObList
             ) {
            totalLast+=rentFormTM.getTotalCost();
              }
        String format = new DecimalFormat("0.00").format(totalLast);
        lblLastCost.setText(String.valueOf(format+"/="));
        tot =Double.parseDouble(format);
    }
    //check tables duplicate items
    private int isExists (RentFormTM rentFormTM){
        for (int i = 0; i<parentCartObList.size();i++){
            if (rentFormTM.getVehicleNumber().equals(parentCartObList.get(i).getVehicleNumber())) {
                return i;
            }
        }
        return -1;
         }

    public void txtSearch(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        TextField textField = new rentController().searchCustomer(txtSearchNic);
        txtCustomerID.setText(textField.getText());
        if(txtSearchNic.getText().isEmpty()|| txtSearchNic.getText().equals("") ){
            txtCustomerID.clear();
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       txtSearchNic.clear();
       txtCustomerID.clear();
       clearFields();
    }
}
