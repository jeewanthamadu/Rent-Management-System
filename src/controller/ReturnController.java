package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Rent;
import model.RentDetail;
import view.tm.ReturnTM;
import view.tm.VehicleTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnController {

    public ObservableList<ReturnTM>getRentDetails() throws SQLException, ClassNotFoundException {
        ObservableList<ReturnTM>list = FXCollections.observableArrayList();
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Rent_Detail WHERE status = '" + "Rent" + "' ");
        ResultSet rst = stm.executeQuery();
        while (rst.next()){
            list.add(new ReturnTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            ));
        }
        return list;
    }

    public List<RentDetail> searchRentHistory (String value) throws SQLException, ClassNotFoundException {
        List<RentDetail>rentDetails = new ArrayList<>();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Rent_Detail WHERE status = '" + "Rent" + "' AND Rent_ID LIKE '%"+value+"%' ").executeQuery();
        while (rst.next()){
            rentDetails.add(new RentDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)

            ));
        }
        return rentDetails;
    }

    public boolean updateReturn(Rent rent){
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement("UPDATE Rent SET Total = (Total+?) WHERE Rent_ID=?");
            stm.setObject(1, rent.getTotal());
            stm.setObject(2, rent.getRentID());
            if (stm.executeUpdate() > 0){
                if (updateDetail(rent.getRentID(), rent.getRentDetails())) {
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

    public boolean updateDetail(String rID,ArrayList<RentDetail> list) throws SQLException, ClassNotFoundException {
        for (RentDetail tm: list
             ) {
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Rent_Detail SET status=?,Total=(Total+?)WHERE Rent_ID=? AND Vehicle_Number=? AND Rent_ID=?");
            stm.setObject(1,tm.getStatus());
            stm.setObject(2,tm.getTotal());
            stm.setObject(3,rID);
            stm.setObject(4,tm.getVehicleNumber());
            stm.setObject(5,tm.getRentID());
            if(stm.executeUpdate()>0){

            if(updateStatus(tm.getVehicleNumber(), tm.getDriverID(), "Available")){

            }else {
                return false;
            }
        }else {
            return false;
            }
        }
        return true;
    }


    private boolean updateStatus(String vehicleNumber,String driverID, String status) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement
                ("UPDATE Vehicle SET Status ='" + status + "' WHERE Vehicle_Number='" + vehicleNumber + "'");
        if (stm.executeUpdate() > 0) {
                if (updateDriverStatus(driverID, "Available")) {
                } else {
                    return false;
                }
        } else {
            return false;
        }
        return true;
    }


    private boolean updateDriverStatus(String driverID, String status) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement
                ("UPDATE Driver SET Status ='" + status + "' WHERE Driver_ID='" + driverID + "'");
        return stm.executeUpdate() > 0;
    }


}
