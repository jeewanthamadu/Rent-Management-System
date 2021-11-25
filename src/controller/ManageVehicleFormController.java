package controller;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.Validation;
import view.tm.CategoryTM;
import view.tm.ModelTM;
import view.tm.UserTM;
import view.tm.VehicleTM;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ManageVehicleFormController {
    public StackPane manageVehicleContext;
    public TableView <VehicleTM>tblVehicle;
    public TableColumn colVehicleNumber;
    public TableColumn colVehicleModelID;
    public TableColumn colVehicleCategorizeID;
    public TableColumn colVehicleDescription;
    public TableColumn colVehicleCost;
    public TableColumn colVehicleStatus;
    public TextField txtModelID;
    public TextField txtModelName;
    public Label lblDate;
    public Label lblTime;
    public TextField txtCategorizeID;
    public TextField txtCategorizeName;
    public TextField txtVehicleNumber;
    public TextField txtDescription;
    public TextField txtCost;
    public TextField txtStatus;
    public TableView <CategoryTM>tblCategory;
    public TableColumn colCategorize_ID;
    public TableColumn colCategorizeName;
    public TableView<ModelTM> tblModel;
    public TableColumn colModelID;
    public TableColumn colModelName;
    public ComboBox<String> cmbModelID;
    public ComboBox<String> cmbCategoryID;
    public Button btnRepair;
    public Button btnSetAvailable;
    public JFXButton btnModAdd;
    public JFXButton btnCatAdd;
    public JFXButton btnVehicleAdd;

    String selectModel ;
    String selectCategory;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> map1 = new LinkedHashMap();
    LinkedHashMap<TextField, Pattern> map2 = new LinkedHashMap();
    Pattern modelId = Pattern.compile("^(M-)[0-9]{3,4}$");
    Pattern categoryId = Pattern.compile("^(C-|c-)[0-9]{3,4}$");
    Pattern modelName = Pattern.compile("^[A-z ]{3,10}$");
    Pattern categoryName = Pattern.compile("^[A-z]{3,10}$");
    Pattern vehicleNumber = Pattern.compile("^[A-Z ]{2,3}[-]{0,1}[0-9]{1,4}$");
    Pattern description = Pattern.compile("^[A-z 0-9  ]{2,30}$");
    Pattern cost = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");



    public void initialize() throws SQLException, ClassNotFoundException {
        loadDateAndTime();
        addModelTable();
        addCategoryTable();
        addModel();
        addCategory();
        addVehicleTable();

        btnVehicleAdd.setDisable(true);
        btnModAdd.setDisable(true);
        btnCatAdd.setDisable(true);

        cmbModelID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectModel=newValue;
        });
        cmbCategoryID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectCategory=newValue;
        });

        storeVehicleValidations();
        storeModelValidations();
        storeCategoryValidations();

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

    private void storeVehicleValidations() {
        map2.put(txtVehicleNumber,vehicleNumber);
        map2.put(txtDescription,description);
        map2.put(txtCost,cost);
    }
    private void storeModelValidations() {
        map.put(txtModelID,modelId);
        map.put(txtModelName,modelName);
    }
    private void storeCategoryValidations() {
        map1.put(txtCategorizeID,categoryId);
        map1.put(txtCategorizeName,categoryName);
    }

    private void addCategory () throws SQLException, ClassNotFoundException {
        cmbCategoryID.getItems().setAll(new VehicleController().getAllCategory());
    }
    private void addModel() throws SQLException, ClassNotFoundException {
        cmbModelID.getItems().setAll(new VehicleController().getAllModels());
    }

    public void addVehicleTable() throws SQLException, ClassNotFoundException {
        ObservableList<VehicleTM> vehicleTMS = new VehicleController().getAllVehicles();

        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colVehicleDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colVehicleCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colVehicleStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colVehicleModelID.setCellValueFactory(new PropertyValueFactory<>("modelId"));
        colVehicleCategorizeID.setCellValueFactory(new PropertyValueFactory<>("categoryId"));

        tblVehicle.setItems(vehicleTMS);
    }
     public void clearVehicleTbl(){
        txtVehicleNumber.clear();
        txtDescription.clear();
        txtCost.clear();
        cmbModelID.setValue("ModelID");
        cmbCategoryID.setValue("CategoryID");
        txtStatus.setText("Available");
    }

    public void tblVehicleOnMouseOnClick(MouseEvent mouseEvent) {
        try{
            VehicleTM vehicleTM = tblVehicle.getSelectionModel().getSelectedItem();
            txtVehicleNumber.setText(vehicleTM.getVehicleNumber());
            txtCost.setText (""+vehicleTM. getCost());
            txtStatus.setText(vehicleTM.getStatus());
            txtDescription.setText(vehicleTM.getDescription());
            cmbCategoryID.setValue(vehicleTM.getCategoryId());
            cmbModelID.setValue(vehicleTM.getModelId());

            if(txtStatus.getText().equals("Repair")){
                btnRepair.setVisible(false);
                btnSetAvailable.setVisible(true);
            }else{
                btnSetAvailable.setVisible(false);
                btnRepair.setVisible(true);
            }
        } catch (Exception e) {
        }

    }

    public void btnVehicleAddonAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle(
                txtVehicleNumber.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtCost.getText()),
                txtStatus.getText(),
                selectModel,
                selectCategory
        );
        if(new VehicleController().addRentVehicle(vehicle)){

            new NotificationController().set("Success","\tVehicle Saved Successfully",0,10);
            addVehicleTable();
            clearVehicleTbl();
        }else {
            new NotificationController().set("something went wrong","\tPlease Try Again",0,7);

        }
    }
    public void btnVehicleRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        VehicleTM getSelectRow = tblVehicle.getSelectionModel().getSelectedItem();
        String vehicleNumber = getSelectRow.getVehicleNumber();

        if(new VehicleController().deleteVehicle(vehicleNumber)){
            new NotificationController().set("Removed","\tVehicle Removed Successfully",0,1);
            addVehicleTable();
            clearVehicleTbl();
        }else{
          new NotificationController().set("something went wrong","\tTry again",0,7);
        }
    }
    public void btnVehicleUpdateOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        VehicleTM VehicleTM = new VehicleTM(
                txtVehicleNumber.getText(),
                txtDescription.getText(),
                Double.parseDouble(txtCost.getText()),
                txtStatus.getText(),
                cmbModelID.getValue(),
                cmbCategoryID.getValue()

        );
        if(new  VehicleController().updateVehicle(VehicleTM)){
            addVehicleTable();
            clearVehicleTbl();
            new NotificationController().set("Update","\tVehicle Update Successfully",0,3);
        }else {
           new NotificationController().set("something went wrong","\tTry again",0,7);
        }
    }

    public void btnModelAddonAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        VehicleModel vehicleModel = new VehicleModel(
                txtModelID.getText(),
                txtModelName.getText()
           );

        if (new ModelController().saveModel(vehicleModel)){
            new NotificationController().set("Success","\tAdd Vehicle Model Successfully",0,2);
            addModelTable();
            addModel();
            clearModelField();
        }else {
          new NotificationController().set("something went wrong","\tPlease Try Again",0,7);
        }
        clearModelField();
    }
    public void btnCategoryAddonAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        VehicleCategory vehicleCategory = new VehicleCategory(
                txtCategorizeID.getText(),
                txtCategorizeName.getText()
        );

        if (new CategoryController().saveCategory(vehicleCategory)){
            new NotificationController().set("Success","\tAdd Vehicle Category Successfully",0,2);
            addCategoryTable();
            addCategory();
            clearCategoryField();
        }else {
            new NotificationController().set("something went wrong","\tPlease Try Again",0,7);
        }
        clearCategoryField();
    }

    public void btnModelRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        ModelTM getSelectRow = tblModel.getSelectionModel().getSelectedItem();
        String modelID = getSelectRow.getModelID();
        if(new ModelController().deleteModel(modelID)){
            new NotificationController().set("Removed","\tRemoved Vehicle Model Successfully",0,1);
            addModelTable();
            addModel();
            clearModelField();
        }else{
           new NotificationController().set("something went wrong","\tTry again",0,7);

        }
    }
    public void btnCategoryRemoveOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        CategoryTM getSelectRow = tblCategory.getSelectionModel().getSelectedItem();
        String categoryID = getSelectRow.getCategory_ID();
        if(new CategoryController().deleteCategory(categoryID)){
            new NotificationController().set("Removed","\tRemoved Vehicle Category Successfully",0,1);
            addCategoryTable();
            addCategory();
            clearCategoryField();
        }else{
            new NotificationController().set("something went wrong","\tTry again",0,7);
        }
    }

    public void clearModelField() {
       txtModelID.clear();
       txtModelName.clear();
       txtModelID.requestFocus();
    }
    public void clearCategoryField() {
        txtCategorizeID.clear();
        txtCategorizeName.clear();
        txtCategorizeID.requestFocus();
    }

    public void addModelTable() throws SQLException, ClassNotFoundException {
        ObservableList<ModelTM> modelTMS = ModelController.getAllModels();
        colModelID .setCellValueFactory(new PropertyValueFactory<>("modelID"));
        colModelName.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        tblModel.setItems(modelTMS);
    }

    public void addCategoryTable() throws SQLException, ClassNotFoundException {
        ObservableList<CategoryTM> categoryTMS = CategoryController.getAllCategory();
        colCategorize_ID .setCellValueFactory(new PropertyValueFactory<>("category_ID"));
        colCategorizeName.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        tblCategory.setItems(categoryTMS);
    }

    public void btnLogOut(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("../view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage =(Stage) this.manageVehicleContext.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

    public void btnRepairOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String repairId = new RepairController().getRepairId();
        Repair repair = new Repair(
                txtVehicleNumber.getText(),
                repairId,
                "Repair"
        );
        if(new RepairController().saveRepair(repair)){
            new NotificationController().set("Success","\tVehicle AddTo Repair",0,11);
            addVehicleTable();
            clearVehicleTbl();
        }else{
            new NotificationController().set("something went wrong","\tTry again",0,7);
        }
    }


    public void btnSetAvailableOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String repairId = new RepairController().getRepairId();
        Repair repair = new Repair(
                txtVehicleNumber.getText(),
                repairId,
                "Available"
        );
        if(new RepairController().saveRepair(repair)){
            new NotificationController().set("Success","\tVehicle Add TO Cart Successfully ",0,11);
            addVehicleTable();
            clearVehicleTbl();
        }else{
            new NotificationController().set("something went wrong","\tTry again",0,7);
        }
    }

    public void txtModelsKeyRelease(KeyEvent keyEvent) {
        btnModAdd.setDisable(true);
        Object response = Validation.validate(map, btnModAdd, "white");
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField error = (TextField) response;
                error.requestFocus();
            } else if (response instanceof Boolean) {
                new NotificationController().set("Fill Completed", "Continue Your Process ", 0, 9);
            }

        }
    }

    public void txtCategoryKeyRelease(KeyEvent keyEvent) {
        btnCatAdd.setDisable(true);
        Object response = Validation.validate(map1, btnCatAdd, "white");
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField error = (TextField) response;
                error.requestFocus();
            } else if (response instanceof Boolean) {

            }
        }
    }

    public void txtVehicleKeyRelease(KeyEvent keyEvent) {
        btnVehicleAdd.setDisable(true);
        Object response = Validation.validate(map2, btnVehicleAdd, "white");
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                TextField error = (TextField) response;
                error.requestFocus();
            } else if (response instanceof Boolean) {
                new NotificationController().set("Fill Completed", "Continue Your Process ", 0, 9);
            }
        }
    }

    public void VehicleShowDetailsOnClick(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("../view/reports/VehicleDetails.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        }

    }
}
