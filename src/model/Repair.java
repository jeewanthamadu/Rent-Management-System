package model;

public class Repair {
    private String vehicleNumber;
    private String repairID;
    private String status;

    public Repair() {
    }

    public Repair(String vehicleNumber, String repairID, String status) {
        this.setVehicleNumber(vehicleNumber);
        this.setRepairID(repairID);

        this.setStatus(status);
    }


    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getRepairID() {
        return repairID;
    }

    public void setRepairID(String repairID) {
        this.repairID = repairID;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Repair{" +
                "vehicleNumber='" + vehicleNumber + '\'' +
                ", repairID='" + repairID + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
