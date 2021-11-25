package model;

public class Customer {
    private String customerID;
    private String customerName;
    private String customerNic;
    private String customerAddress;
    private String customer_Tele_Number;


    public Customer() {
    }

    public Customer(String customerID, String customerName, String customerNic, String customerAddress, String customer_Tele_Number) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerNic = customerNic;
        this.customerAddress = customerAddress;
        this.customer_Tele_Number = customer_Tele_Number;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNic() {
        return customerNic;
    }

    public void setCustomerNic(String customerNic) {
        this.customerNic = customerNic;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomer_Tele_Number() {
        return customer_Tele_Number;
    }

    public void setCustomer_Tele_Number(String customer_Tele_Number) {
        this.customer_Tele_Number = customer_Tele_Number;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID='" + customerID + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerNic='" + customerNic + '\'' +
                ", customerAddress='" + customerAddress + '\'' +
                ", customer_Tele_Number='" + customer_Tele_Number + '\'' +
                '}';
    }

}
