package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import view.tm.DriverTM;
import view.tm.UserTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverController {

    public static ObservableList<DriverTM> getAllUsers () throws SQLException, ClassNotFoundException {
        ObservableList<DriverTM> observableList = FXCollections.observableArrayList();
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Driver ");
        ResultSet rst= pst.executeQuery();
        while (rst.next()){
            observableList.add(new DriverTM(
                    rst.getString(1),
                    rst.getString(2) ,
                    Double.parseDouble(rst.getString(3)),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6)

            ));
        }
        return observableList;
    }

    public List<DriverSalary> searchDriverSalaryHistory (String value) throws SQLException, ClassNotFoundException {
        List<DriverSalary>driverSalaries = new ArrayList<>();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Driver_Salary WHERE Driver_ID LIKE '%"+value+"%' ").executeQuery();
        while (rst.next()){
            driverSalaries.add(new DriverSalary(
                    rst.getString(1),
                    Double.parseDouble(rst.getString(2)),
                    rst.getString(3),
                    rst.getString(4)
            ));
        }
        return driverSalaries;
    }


    public  boolean  saveDriver(Driver driver) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("INSERT INTO Driver VALUES (?,?,?,?,?,?)");
        pst.setObject(1,driver.getDriverUserName());
        pst.setObject(2,driver.getName());
        pst.setObject(3,driver.getDailyCost());
        pst.setObject(4,driver.getNic());
        pst.setObject(5,driver.getTeleNum());
        pst.setObject(6,driver.getStatus());

        return pst.executeUpdate()>0;
    }

    public boolean updateDriver(DriverTM driverTM) throws SQLException, ClassNotFoundException {
        PreparedStatement stm   = DbConnection.getInstance().getConnection().prepareStatement("UPDATE Driver SET Driver_Name=?, Daily_Cost =?, Nic=?, Tele_Number=?,status=? WHERE Driver_ID=?");

        stm.setObject(1,driverTM.getName());
        stm.setObject(2,driverTM.getDailyCost());
        stm.setObject(3,driverTM.getNic());
        stm.setObject(4,driverTM.getTeleNum());
        stm.setObject(5,driverTM.getStatus());

        stm.setObject(6,driverTM.getDriverUserName());
        return stm.executeUpdate()>0;
    }

    public boolean deleteDriver (String driverName) throws SQLException, ClassNotFoundException {
        return DbConnection.getInstance().getConnection().prepareStatement
                ("DELETE FROM Driver WHERE Driver_ID = '" + driverName + "'").executeUpdate() > 0;

    }

    public Driver passDriver (String driver) throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Driver WHERE Driver_ID ='"+driver+"'").executeQuery();
        if (rst.next()){
            return new Driver(
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

    public List<String> getAllDriverDetails() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Driver WHERE Status = '"+"Available"+"'").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }return ids;
    }

}
