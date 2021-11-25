package controller;

import db.DbConnection;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    public String getCustomerId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer ORDER BY Customer_ID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(1).split("-")[1]);
            tempId += 1;
            if (tempId < 10) {
                return "C-00" + tempId;
            } else if (tempId < 100) {
                return "C-0" + tempId;
            } else {
                return "C-" + tempId;
            }
        } else {
            return "C-000";
        }
    }

    public Boolean saveCustomer (Customer customer) throws SQLException, ClassNotFoundException {
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?,?) ");
        stm.setObject(1,customer.getCustomerID());
        stm.setObject(2,customer.getCustomerName());
        stm.setObject(3,customer.getCustomerAddress());
        stm.setObject(4,customer.getCustomerNic());
        stm.setObject(5,customer.getCustomer_Tele_Number());

         return stm.executeUpdate()>0;
    }

    public Customer showCustomerDetails (String id) throws SQLException, ClassNotFoundException {
        final ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer WHERE Customer_ID ='"+id+"' ").executeQuery();
        if(rst.next()){
            return new Customer(rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5)
            );
        }else {
            return null;
        }
    }

    public List<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Customer").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(rst.getString(1));
        }
        return  ids;
    }


}
