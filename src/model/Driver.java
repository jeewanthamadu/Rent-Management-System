package model;

import java.util.ArrayList;

public class Driver {
    private String driverUserName;
    private String name;
    private double dailyCost;
    private String nic;
    private String teleNum;
    private String status;


    public Driver(String driverUserName, String name, double dailyCost, String status) {
        this.driverUserName = driverUserName;
        this.name = name;
        this.dailyCost = dailyCost;
        this.status = status;
    }

    public Driver(String driverUserName, String name, double dailyCost, String nic, String teleNum, String status) {
        this.driverUserName = driverUserName;
        this.name = name;
        this.dailyCost = dailyCost;
        this.nic = nic;
        this.teleNum = teleNum;
        this.status = status;
    }

    public Driver() {

    }


    public String getDriverUserName() {
        return driverUserName;
    }

    public void setDriverUserName(String driverUserName) {
        this.driverUserName = driverUserName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDailyCost() {
        return dailyCost;
    }

    public void setDailyCost(double dailyCost) {
        this.dailyCost = dailyCost;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getTeleNum() {
        return teleNum;
    }

    public void setTeleNum(String teleNum) {
        this.teleNum = teleNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "driverUserName='" + driverUserName + '\'' +
                ", name='" + name + '\'' +
                ", dailyCost=" + dailyCost +
                ", nic='" + nic + '\'' +
                ", teleNum='" + teleNum + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
