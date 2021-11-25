package view.tm;

public class ReturnTM {


    private String rentID;
    private String rentDate;
    private String returnDate;
    private String driverID;
    private String vehicleNumber;
    private Double Fine;
    private String status;


    public ReturnTM(String rentID, String rentDate, String returnDate, String driverID, String vehicleNumber, Double fine, String status) {
        this.rentID = rentID;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.driverID = driverID;
        this.vehicleNumber = vehicleNumber;
        this. Fine = fine;
        this.status = status;
    }

    public ReturnTM(String rentID, String rentDate, String returnDate, String driverID, String vehicleNumber) {
        this.rentID = rentID;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.driverID = driverID;
        this.vehicleNumber = vehicleNumber;
    }

    public ReturnTM(String rentID, String rentDate, String returnDate, String driverID, String vehicleNumber, Double fine) {
        this.setRentID(rentID);
        this.setRentDate(rentDate);
        this.setReturnDate(returnDate);
        this.setDriverID(driverID);
        this.setVehicleNumber(vehicleNumber);
        setFine(fine);
    }

    public ReturnTM() {
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

    public Double getFine() {
        return Fine;
    }

    public void setFine(Double fine) {
        Fine = fine;
    }

    @Override
    public String toString() {
        return "ReturnTM{" +
                "rentID='" + rentID + '\'' +
                ", rentDate='" + rentDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", driverID='" + driverID + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", Fine=" + Fine +
                ", status='" + status + '\'' +
                '}';
    }
}
