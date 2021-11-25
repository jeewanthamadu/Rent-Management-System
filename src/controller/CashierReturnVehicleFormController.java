package controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Rent;
import model.RentDetail;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.Validation;
import view.tm.RentFormTM;
import view.tm.ReturnTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class CashierReturnVehicleFormController {
    public StackPane manageReturnVehicleFormContext;
    public Label lblDate;
    public Label lblTime;
    public TableColumn colRentID;
    public TableColumn colVehicleNumber;
    public TableColumn colRentDate;
    public TableColumn colReturnDate;
    public TableColumn colDriverID;
    public TextField txtRentID;
    public TableColumn colComRentID;
    public TableColumn colComVehicleNumber;
    public TableColumn colComRentDate;
    public TableColumn colComReturnDate;
    public TableColumn colComDriverID;
    public TableColumn colComFine;
    public TextField txtVehicleNumber;
    public TextField txtRentDate;
    public TextField txtReturnDate;
    public TextField txtFine;
    public Label lblTotal;
    public TextField txtDriverID;
    public TableView<ReturnTM> tblGetRent;
    public TextField txtSearch;
    public TableView<ReturnTM> tblComReturn;
    public JFXButton btnRentId;

    double total;
    private Rent rentUpdate;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    Pattern fine = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");

    private final ObservableList<ReturnTM> parentCartObList = FXCollections.observableArrayList();


    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();
        setRentTable();
        setReturnTable();
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtFine, fine);
    }

    public void btnLogout(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) this.manageReturnVehicleFormContext.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void clearFields() {
        txtRentID.clear();
        txtVehicleNumber.clear();
        txtRentDate.clear();
        txtReturnDate.clear();
        txtDriverID.clear();
        txtFine.setText("0.00");
        txtRentID.requestFocus();
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() + " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void setReturnTable() throws SQLException, ClassNotFoundException {

        colComRentID.setCellValueFactory(new PropertyValueFactory<>("rentID"));
        colComVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colComRentDate.setCellValueFactory(new PropertyValueFactory<>("rentDate"));
        colComReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colComDriverID.setCellValueFactory(new PropertyValueFactory<>("driverID"));

        colComFine.setCellValueFactory(new PropertyValueFactory<>("Fine"));
    }

    private void setRentTable() throws SQLException, ClassNotFoundException {
        ObservableList<ReturnTM> rentDetails = new ReturnController().getRentDetails();

        colRentID.setCellValueFactory(new PropertyValueFactory<>("rentID"));
        colRentDate.setCellValueFactory(new PropertyValueFactory<>("rentDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colDriverID.setCellValueFactory(new PropertyValueFactory<>("driverID"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));

        tblGetRent.setItems(rentDetails);

    }

    public void btnReturnOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ReturnTM returnTM = new ReturnTM(
                txtRentID.getText(),
                txtRentDate.getText(),
                txtReturnDate.getText(),
                txtDriverID.getText(),
                txtVehicleNumber.getText(),
                Double.parseDouble(txtFine.getText())
        );
        int exists = isExists(returnTM);
        if (exists == -1) {
            parentCartObList.add(returnTM);
        } else {
            ReturnTM newReturnTM = new ReturnTM(
                    txtRentID.getText(),
                    txtRentDate.getText(),
                    txtReturnDate.getText(),
                    txtDriverID.getText(),
                    txtVehicleNumber.getText(),
                    Double.parseDouble(txtFine.getText())
            );
            parentCartObList.remove(exists);
            parentCartObList.add(newReturnTM);
        }
        tblComReturn.setItems(parentCartObList);
        calculateCost();
        clearFields();
    }

    public void btnCompleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
       try {
           ArrayList<RentDetail> list = new ArrayList<>();
           for (ReturnTM temp : parentCartObList
           ) {
               list.add(new RentDetail(
                       temp.getRentID(),
                       temp.getRentDate(),
                       temp.getReturnDate(),
                       temp.getDriverID(),
                       temp.getVehicleNumber(),
                       temp.getFine(),
                       "Returned"
               ));
           }
           for (RentDetail tm : list
           ) {
               Rent update = new Rent(
                       tm.getRentID(),
                       total,
                       list
               );
               rentUpdate = update;
           }
           if (new ReturnController().updateReturn(rentUpdate)) {
               new NotificationController().set("Success", "\tReturn vehicle successfully", 0, 5);
               setReturnTable();
               setRentTable();
               try {
                   JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/ReturnBill.jrxml"));
                   JasperReport compileReport = JasperCompileManager.compileReport(design);
                   ObservableList<ReturnTM> items = tblComReturn.getItems();
                   String input = lblTotal.getText();
                   HashMap map = new HashMap();
                   map.put("Total", input);
                   JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map/* null*/, new JRBeanArrayDataSource(items.toArray()));
                   JasperViewer.viewReport(jasperPrint, false);
               } catch (JRException e) {
                   e.printStackTrace();
               }
               parentCartObList.clear();
           } else {
               new NotificationController().set("Error", "\tsomething went wrong", 0, 6);
           }
       } catch (SQLException throwables) {

       } catch (ClassNotFoundException e) {

       }

    }

    public void txtSearch(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        List<RentDetail> rentDetails = new ReturnController().searchRentHistory(txtSearch.getText());
        ObservableList<ReturnTM> tblData = FXCollections.observableArrayList();
        for (RentDetail rentDetail : rentDetails
        ) {
            tblData.add(new ReturnTM(
                    rentDetail.getRentID(),
                    rentDetail.getRentDate(),
                    rentDetail.getReturnDate(),
                    rentDetail.getDriverID(),
                    rentDetail.getVehicleNumber()
            ));
        }
        tblGetRent.setItems(tblData);
    }

    public void tblRentGet(MouseEvent mouseEvent) {
        try{
            ReturnTM returnTM = tblGetRent.getSelectionModel().getSelectedItem();
            txtRentID.setText(returnTM.getRentID());
            txtVehicleNumber.setText(returnTM.getVehicleNumber());
            txtRentDate.setText(returnTM.getRentDate());
            txtReturnDate.setText(returnTM.getReturnDate());
            txtDriverID.setText(returnTM.getDriverID());
        } catch (Exception e) {

        }

    }

    private void calculateCost() {
        double totalLast = 0;
        for (ReturnTM returnTM : parentCartObList
        ) {
            totalLast += returnTM.getFine();
        }
        String format = new DecimalFormat("0.00").format(totalLast);
        lblTotal.setText(String.valueOf(format + "/="));
        total = Double.parseDouble(format);
    }

    //check tables duplicate items
    private int isExists(ReturnTM returnTM) {
        for (int i = 0; i < parentCartObList.size(); i++) {
            if (returnTM.getVehicleNumber().equals(parentCartObList.get(i).getVehicleNumber())) {
                return i;
            }
        }
        return -1;
    }

    public void txtFineKeyRelease(KeyEvent keyEvent) {
        btnRentId.setDisable(true);
        Object response = Validation.validate(map, btnRentId,"white");
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField error = (TextField) response;
                error.requestFocus();
            } else if (response instanceof Boolean) {
                new Alert(Alert.AlertType.CONFIRMATION, "Added").showAndWait();
            }
        }
    }
}
