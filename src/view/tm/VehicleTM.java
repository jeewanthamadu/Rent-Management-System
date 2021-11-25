package view.tm;

public class VehicleTM {
    private String vehicleNumber;
    private String description;
    private Double cost;
    private String status;
    private String modelId;
    private String categoryId;

    public VehicleTM(String status) {
        this.status = status;
    }

    public VehicleTM(String vehicleNumber, String description, Double cost, String modelId, String categoryId) {
        this.vehicleNumber = vehicleNumber;
        this.description = description;
        this.cost = cost;
        this.modelId = modelId;
        this.categoryId = categoryId;
    }

    public VehicleTM(String vehicleNumber, String description, Double cost, String status, String modelId, String categoryId) {
        this.vehicleNumber = vehicleNumber;
        this.description = description;
        this.cost = cost;
        this.status = status;
        this.modelId = modelId;
        this.categoryId = categoryId;
    }

    public VehicleTM() {
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
        return "VehicleTM{" +
                "vehicleNumber='" + vehicleNumber + '\'' +
                ", description ='" + description + '\'' +
                ", cost=" + cost +
                ", status='" + status + '\'' +
                ", modelId='" + modelId + '\'' +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }

}