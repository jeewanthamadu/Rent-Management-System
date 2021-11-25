package model;

public class DriverSalary {
    private String name;
    private Double salary;
    private String driverID;
    private String rent_ID;

    public DriverSalary() {
    }

    public DriverSalary(String name, Double salary, String driverID, String rent_ID) {
        this.name = name;
        this.salary = salary;
        this.driverID = driverID;
        this.rent_ID = rent_ID;
    }

    public String getRent_ID() {
        return rent_ID;
    }

    public void setRent_ID(String rent_ID) {
        this.rent_ID = rent_ID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    @Override
    public String toString() {
        return "DriverSalary{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", driverID='" + driverID + '\'' +
                ", rent_ID='" + rent_ID + '\'' +
                '}';
    }
}
