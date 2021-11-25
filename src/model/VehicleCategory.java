package model;

public class VehicleCategory {
    String category_ID;
    String categoryName;

    public VehicleCategory(String category_ID, String categoryName) {
        this.category_ID = category_ID;
        this.categoryName = categoryName;
    }

    public VehicleCategory() {
    }

    public String getCategory_ID() {
        return category_ID;
    }

    public void setCategory_ID(String category_ID) {
        this.category_ID = category_ID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "VehicleCategory{" +
                "category_ID='" + category_ID + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
