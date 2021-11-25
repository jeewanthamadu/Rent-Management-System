package model;

public class Vehicle {
    private String vehicleNumber;
    private String description;
    private Double cost;
    private String status;
    private String modelId;
    private String categoryId;

    public Vehicle(String vehicleNumber, String description, Double cost, String modelId, String categoryId) {
        this.vehicleNumber = vehicleNumber;
        this.description = description;
        this.cost = cost;
        this.modelId = modelId;
        this.categoryId = categoryId;
    }

    public Vehicle(String vehicleNumber, String description, Double cost, String status, String modelId, String categoryId) {
        this.setVehicleNumber(vehicleNumber);
        this.setDescription(description);
        this.setCost(cost);
        this.setStatus(status);
        this.setModelId(modelId);
        this.setCategoryId(categoryId);
    }

    public Vehicle() {
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

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleNumber='" + vehicleNumber + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", status='" + status + '\'' +
                ", modelId='" + modelId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
