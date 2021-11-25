package view.tm;

public class ModelTM {
    String modelID;
    String modelName;

    public ModelTM(String modelID, String modelName) {
        this.modelID = modelID;
        this.modelName = modelName;
    }

    public ModelTM() {
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
        return "ModelTM{" +
                "modelID='" + modelID + '\'' +
                ", modelName='" + modelName + '\'' +
                '}';
    }
}
