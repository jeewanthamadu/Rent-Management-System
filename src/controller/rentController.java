package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import model.Customer;
import model.Driver;
import model.Rent;
import model.RentDetail;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class rentController {

    public ArrayList<Driver> getDrivers (String driverID) throws SQLException, ClassNotFoundException {
        ArrayList<Driver> list =new ArrayList<>();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Driver WHERE Driver_ID='" + driverID + "'").executeQuery();
        while (rst.next()) {
            list.add(new Driver(
                    rst.getString(1),
                    rst.getString(2),
                    Double.parseDouble(rst.getString(3)),
                    rst.getString(4)
            ));
        }
        return list;
    }

    public String getRentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Rent ORDER BY Rent_ID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId += 1;
            if (tempId < 10) {
                return "R-00" + tempId;
            } else if (tempId < 100) {
                return "R-0" + tempId;
            } else {
                return "R-" + tempId;
            }
        } else {
            return "R-000";
        }
    }

    public boolean saveRent(Rent r) {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement("INSERT INTO Rent VALUES (?,?,?,?,?)");
            stm.setObject(1, r.getRentID());
            stm.setObject(2, r.getRent_Date());
            stm.setObject(3, r.getCustomerID());
            stm.setObject(4, r.getDriverID());
            stm.setObject(5, r.getTotal());
            if (stm.executeUpdate() > 0) {
                if (saveRentDetail(r.getRentID(), r.getRentDetails())) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public boolean saveRentDetail(String id, ArrayList<RentDetail> rentDetail) throws SQLException, ClassNotFoundException {
        for (RentDetail temp : rentDetail
        ) {
            PreparedStatement stm = DbConnection.getInstance().getConnection().
                    prepareStatement("INSERT INTO Rent_Detail VALUES (?,?,?,?,?,?,?)");
            stm.setObject(1, id);
            stm.setObject(2, temp.getRentDate());
            stm.setObject(3, temp.getReturnDate());
            stm.setObject(4, temp.getDriverID());
            stm.setObject(5, temp.getVehicleNumber());
            stm.setObject(6, temp.getTotal());
            stm.setObject(7, temp.getStatus());

            if (stm.executeUpdate() > 0) {
                if (updateStatus(temp.getVehicleNumber(), "Rent", temp.getDriverArrayList(),temp.getRentID())) {
                } else {
                    return false;
                }
            } else {
                return false;
            }

        }
        return true;
    }
    //change status vehicle
    private boolean updateStatus(String vehicleNumber, String status, ArrayList<Driver>drivers, String rentId) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement
                ("UPDATE Vehicle SET Status ='" + status + "' WHERE Vehicle_Number='" + vehicleNumber + "'");
        if (stm.executeUpdate() > 0){

            if (updateSalary(drivers,rentId)){
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean updateSalary (ArrayList<Driver>drivers,String rentId) throws SQLException, ClassNotFoundException {
        for (Driver temp : drivers){
            PreparedStatement pst = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Driver_Salary VALUES (?,?,?,?)");
            pst.setObject(1,temp.getName());
            pst.setObject(2,temp.getDailyCost());
            pst.setObject(3,temp.getDriverUserName());
            pst.setObject(4,rentId);

            if(pst.executeUpdate()>0){
                if(updateDriverStatus(temp.getDriverUserName(),"Hired")){
                }else{
                    return false;
                }
            }else {
                return false;
            }
        }
        return true;
    }

    private boolean updateDriverStatus( String driverId,String status) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement
                ("UPDATE Driver SET Status ='" + status + "' WHERE Driver_ID='" + driverId + "'");
        return stm.executeUpdate() > 0;
    }


    public static TextField searchCustomer(TextField nic) throws SQLException, ClassNotFoundException {
        TextField textField = new TextField();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer WHERE Nic LIKE '%" + nic.getText() + "%' ").executeQuery();
        while (rst.next()){
            String id = rst.getString(1);
            textField.setText(id);
        }
        return textField;
    }

}

