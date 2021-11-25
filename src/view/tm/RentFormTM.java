package view.tm;

import model.Driver;
import model.RentDetail;

import java.util.ArrayList;
import java.util.Date;

public class RentFormTM {

    private String vehicleNumber;
    private String description;
    private double cost1Day;
    private String driverID;
    private String customerID;
    private int totalDays;
    private double totalCost;
    private String rentDate;
    private String returnDate;
    private ArrayList<Driver> driverArrayList;



    public RentFormTM(String vehicleNumber, String description, double cost1Day, String driverID, String customerID, int totalDays, double totalCost, String rentDate, String returnDate, ArrayList<Driver> driverArrayList) {
        this.vehicleNumber = vehicleNumber;
        this.description = description;
        this.cost1Day = cost1Day;
        this.driverID = driverID;
        this.customerID = customerID;
        this.totalDays = totalDays;
        this.totalCost = totalCost;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.driverArrayList = driverArrayList;
    }

    public RentFormTM() {
    }

    public RentFormTM(String vehicleNumber, String description, double cost1Day, String driverID, String customerID, int totalDays, double totalCost, String rentDate, String returnDate) {
        this.vehicleNumber = vehicleNumber;
        this.description = description;
        this.cost1Day = cost1Day;
        this.driverID = driverID;
        this.customerID = customerID;
        this.totalDays = totalDays;
        this.totalCost = totalCost;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
    }


    public RentFormTM(String vehicleNumber, String description, double cost1Day, String driverID, String customerID, int totalDays, double totalCost) {
        this.vehicleNumber = vehicleNumber;
        this.description = description;
        this.cost1Day = cost1Day;
        this.driverID = driverID;
        this.customerID = customerID;
        this.totalDays = totalDays;
        this.totalCost = totalCost;
    }

    public ArrayList<Driver> getDriverArrayList() {
        return driverArrayList;
    }

    public void setDriverArrayList(ArrayList<Driver> driverArrayList) {
        this.driverArrayList = driverArrayList;
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

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost1Day() {
        return cost1Day;
    }

    public void setCost1Day(double cost1Day) {
        this.cost1Day = cost1Day;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }


    @Override
    public String toString() {
        return "RentFormTM{" +
                "vehicleNumber='" + vehicleNumber + '\'' +
                ", description='" + description + '\'' +
                ", cost1Day=" + cost1Day +
                ", driverID='" + driverID + '\'' +
                ", customerID='" + customerID + '\'' +
                ", totalDays=" + totalDays +
                ", totalCost=" + totalCost +
                ", rentDate='" + rentDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", driverArrayList=" + driverArrayList +
                '}';
    }
}
