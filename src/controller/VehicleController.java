package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Vehicle;
import view.tm.UserTM;
import view.tm.VehicleTM;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleController {

    public List<String> getAllModels() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle_Model").executeQuery();
        List<String> ids = new ArrayList<>();
        while (resultSet.next()){
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public List<String> getAllCategory() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle_Catergorize").executeQuery();
        List<String> ids = new ArrayList<>();
        while (resultSet.next()){
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public boolean addRentVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Vehicle VALUES (?,?,?,?,?,?) ");
        pst.setObject(1,vehicle.getVehicleNumber());
        pst.setObject(2,vehicle.getModelId());
        pst.setObject(3,vehicle.getCost());
        pst.setObject(4,vehicle.getDescription());
        pst.setObject(5,vehicle.getCategoryId());
        pst.setObject(6,vehicle.getStatus());
        return pst.executeUpdate() > 0 ;
    }

    public ObservableList<VehicleTM> getAllVehicles() throws SQLException, ClassNotFoundException {

        ObservableList<VehicleTM>  observableList = FXCollections.observableArrayList();
        ResultSet rst =DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle").executeQuery();
    while(rst.next()){
           observableList.add(new VehicleTM(
                    rst.getString(1),
                    rst.getString(4),
                    Double.parseDouble(rst.getString(3)),
                    rst.getString(6),
                    rst.getString(2),
                    rst.getString(5)

            ));
        }
        return observableList;
    }

    public List<String> getAllVehicleDetails() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle WHERE Status = '"+"Available"+"'").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }return ids;
    }

    public Vehicle passVehicle (String vehicle) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle WHERE Vehicle_Number ='"+vehicle+"'").executeQuery();
        if (rst.next()){
            return new Vehicle(
            rst.getString(1),
            rst.getString(2),
            Double.parseDouble(rst.getString(3)),
            rst.getString(4),
            rst.getString(5),
            rst.getString(6)
      );
         }else {
            return null;
        }
      }

    public boolean deleteVehicle (String vehicleNumber) throws SQLException, ClassNotFoundException {
        return DbConnection.getInstance().getConnection().prepareStatement
                ("DELETE FROM Vehicle WHERE Vehicle_Number = '" + vehicleNumber + "'").executeUpdate() > 0;
    }

    public boolean updateVehicle(VehicleTM vehicleTM) throws SQLException, ClassNotFoundException {
        PreparedStatement stm   = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Vehicle SET Model_ID=?, Cost=?, Description=?,Catergorize_ID =? ,Status=? WHERE Vehicle_Number=?");

        stm.setObject(1,vehicleTM.getModelId());
        stm.setObject(2,vehicleTM.getCost());
        stm.setObject(3,vehicleTM.getDescription());
        stm.setObject(4,vehicleTM.getCategoryId());
        stm.setObject(5,vehicleTM.getStatus());

        stm.setObject(6,vehicleTM.getVehicleNumber());
        return stm.executeUpdate()>0;
    }


}
