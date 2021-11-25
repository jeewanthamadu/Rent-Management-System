package model;

public class VehicleModel {
    String modelID;
    String modelName;

    public VehicleModel(String modelID, String modelName) {
        this.modelID = modelID;
        this.modelName = modelName;
    }

    public VehicleModel() {
    }

    public String getModelID() {
        return modelID;
    }

    public void setModelID(String modelID) {
        this.modelID = modelID;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return "VehicleModel{" +
                "modelID='" + modelID + '\'' +
                ", modelName='" + modelName + '\'' +
                '}';
    }
}
