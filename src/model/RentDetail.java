package model;

import java.util.ArrayList;

public class RentDetail {
    private String rentID;
    private String rentDate;
    private String returnDate;
    private String driverID;
    private String vehicleNumber;
    private double total;
    private String status;
    private ArrayList<Driver> driverArrayList;

    public RentDetail(String rentID, String rentDate, String returnDate, String driverID, String vehicleNumber, double total, String status, ArrayList<Driver> driverArrayList) {
        this.rentID = rentID;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.driverID = driverID;
        this.vehicleNumber = vehicleNumber;
        this.total = total;
        this.status = status;
        this.driverArrayList = driverArrayList;
    }

    public RentDetail(String rentID, String rentDate, String returnDate, String driverID, String vehicleNumber) {
        this.rentID = rentID;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.driverID = driverID;
        this.vehicleNumber = vehicleNumber;
    }

    public RentDetail(String rentID, String rentDate, String returnDate, String driverID, String vehicleNumber, double total, String status) {
        this.setRentID(rentID);
        this.setRentDate(rentDate);
        this.setReturnDate(returnDate);
        this.setDriverID(driverID);
        this.setVehicleNumber(vehicleNumber);
        this.setTotal(total);
        this.status = status;
    }

    public RentDetail() {
    }


    public ArrayList<Driver> getDriverArrayList() {
        return driverArrayList;
    }

    public void setDriverArrayList(ArrayList<Driver> driverArrayList) {
        this.driverArrayList = driverArrayList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRentID() {
        return rentID;
    }

    public void setRentID(String rentID) {
        this.rentID = rentID;
    }

    public String getRentDate() {
        return rentDate;
    }

    public void setRentDate(String rentDate) {
        this.rentDate = rentDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "RentDetail{" +
                "rentID='" + rentID + '\'' +
                ", rentDate='" + rentDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", driverID='" + driverID + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", total=" + total +
                ", status='" + status + '\'' +
                ", driverArrayList=" + driverArrayList +
                '}';
    }
}
