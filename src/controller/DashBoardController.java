package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.RentDetail;
import model.Vehicle;
import view.tm.ReturnTM;
import view.tm.VehicleTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashBoardController {
    public ObservableList<VehicleTM>getAvailableDetails() throws SQLException, ClassNotFoundException {
        ObservableList<VehicleTM>list = FXCollections.observableArrayList();
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement
                ("SELECT v.Vehicle_Number,v.Description,v.Cost,m.Model_Name,c.Catergorize_Name from Vehicle v,Vehicle_Model m,Vehicle_Catergorize c WHERE v.Status='Available' AND c.Catergorize_ID=v.Catergorize_ID AND m.Model_ID=v.Model_ID GROUP BY v.Vehicle_Number;");
        ResultSet rst = stm.executeQuery();
        while (rst.next()){
            list.add(new VehicleTM(
                    rst.getString(1),
                    rst.getString(2),
                    Double.parseDouble(rst.getString(3)),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return list;
    }

    public List<Vehicle> searchAvailable (String value) throws SQLException, ClassNotFoundException {
        List<Vehicle> vehicleList = new ArrayList<>();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT v.Vehicle_Number,v.Description,v.Cost,m.Model_Name,c.Catergorize_Name from Vehicle v,Vehicle_Model m,Vehicle_Catergorize c WHERE (v.Status='Available' AND c.Catergorize_ID=v.Catergorize_ID AND m.Model_ID=v.Model_ID) AND concat (v.Vehicle_Number,v.Description,v.Cost,m.Model_Name,c.Catergorize_Name)LIKE '%"+value+"%' ").executeQuery();
        while (rst.next()){
            vehicleList.add(new Vehicle(
                    rst.getString(1),
                    rst.getString(2),
                    Double.parseDouble(rst.getString(3)),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return vehicleList;
    }

    public String getCountCustomer() throws SQLException, ClassNotFoundException {
    String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(Customer_ID) FROM Customer ").executeQuery();
            while (rst.next()) {
                count = rst.getString(1);
            }
            return count;
    }

    public String getCountDriver() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(Driver_ID) FROM Driver ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }

    public String getCountVehicles() throws SQLException, ClassNotFoundException {
        String count=null;
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT count(Vehicle_Number) FROM Vehicle WHERE Status='"+"Available"+"' ").executeQuery();
        while (rst.next()) {
            count = rst.getString(1);
        }
        return count;
    }


}
