package model;

import java.util.ArrayList;

public class Rent {
    private String rentID;
    private String rent_Date;
    private String customerID;
    private String driverID;
    private Double total;
    private ArrayList<RentDetail>rentDetails;

    public Rent(String rentID, Double total, ArrayList<RentDetail> rentDetails) {
        this.rentID = rentID;
        this.total = total;
        this.rentDetails = rentDetails;
    }


    public Rent(String rentID, String rent_Date, String customerID, String driverID, Double total, ArrayList<RentDetail> rentDetails) {
        this.rentID = rentID;
        this.rent_Date = rent_Date;
        this.customerID = customerID;
        this.driverID = driverID;
        this.total = total;
        this.rentDetails = rentDetails;
    }

    public Rent() {
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    public String getRentID() {
        return rentID;
    }

    public void setRentID(String rentID) {
        this.rentID = rentID;
    }

    public String getRent_Date() {
        return rent_Date;
    }

    public void setRent_Date(String rent_Date) {
        this.rent_Date = rent_Date;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public ArrayList<RentDetail> getRentDetails() {
        return rentDetails;
    }

    public void setRentDetails(ArrayList<RentDetail> rentDetails) {
        this.rentDetails = rentDetails;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "rentID='" + rentID + '\'' +
                ", rent_Date='" + rent_Date + '\'' +
                ", customerID='" + customerID + '\'' +
                ", driverID='" + driverID + '\'' +
                ", rentDetails=" + rentDetails +
                '}';
    }
}
