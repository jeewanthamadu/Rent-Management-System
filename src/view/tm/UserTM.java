package view.tm;

public class UserTM {
    private String userName;
    private String name;
    private String Address;
    private String teleNumber;
    private String Role;



    public UserTM(String userName, String name, String address, String teleNumber, String role) {
        this.setUserName(userName);
        this.setName(name);
        this.setAddress(address);
        this.setTeleNumber(teleNumber);
        this.setRole(role);
    }

    public UserTM() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTeleNumber() {
        return teleNumber;
    }

    public void setTeleNumber(String teleNumber) {
        this.teleNumber = teleNumber;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "UserTM{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", Address='" + Address + '\'' +
                ", teleNumber='" + teleNumber + '\'' +
                ", Role='" + Role + '\'' +
                '}';
    }

}
