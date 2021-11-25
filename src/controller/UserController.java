package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import view.tm.UserTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {

    public String login (User login) throws SQLException, ClassNotFoundException {
        String username = login.getUserName();
        String password = login.getUserPassword();

        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `User` WHERE User_Name = ? AND User_Password =md5(?)");
        stm.setObject(1,username);
        stm.setObject(2,password);
        ResultSet rst = stm.executeQuery();

        if(rst.next()){
            return rst.getString(5);
        }else{
           return "" ;
        }
    }

    public  boolean  saveUser(User user) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("INSERT INTO `user` VALUES (?,?,?,?,?,md5(?))");
        pst.setObject(1,user.getUserName());
        pst.setObject(2,user.getName());
        pst.setObject(3,user.getAddress());
        pst.setObject(4,user.getTelNo());
        pst.setObject(5,user.getRole());
        pst.setObject(6,user.getUserPassword());

        return pst.executeUpdate()>0;

    }

    public static ObservableList<UserTM> getAllUsers () throws SQLException, ClassNotFoundException {
        ObservableList<UserTM> observableList = FXCollections.observableArrayList();
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM `User` ");
        ResultSet rst= pst.executeQuery();
        while (rst.next()){
           observableList.add(new UserTM(
               rst.getString(1),
               rst.getString(2) ,
               rst.getString(3),
               rst.getString(4),
               rst.getString(5)
            ));
        }
        return observableList;
    }

    public boolean deleteUsers (String userName) throws SQLException, ClassNotFoundException {
        return DbConnection.getInstance().getConnection().prepareStatement
                ("DELETE FROM `user` WHERE User_Name = '" + userName + "'").executeUpdate() > 0;
    }

    public boolean updateUser(UserTM userTM) throws SQLException, ClassNotFoundException {
        PreparedStatement stm   = DbConnection.getInstance().getConnection().prepareStatement("UPDATE `User` SET Name=?, Address=?, Tele_No=?,Role =? WHERE User_Name=?");
        stm.setObject(1,userTM.getName());
        stm.setObject(2,userTM.getAddress());
        stm.setObject(3,userTM.getTeleNumber());
        stm.setObject(4,userTM.getRole());
        stm.setObject(5,userTM.getUserName());
        return stm.executeUpdate()>0;
    }

}
