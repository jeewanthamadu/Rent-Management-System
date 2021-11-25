package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.VehicleCategory;
import view.tm.CategoryTM;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryController {
    public  boolean  saveCategory(VehicleCategory  vehicleCategory) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("INSERT INTO Vehicle_Catergorize VALUES (?,?)");
        pst.setObject(1,vehicleCategory.getCategory_ID());
        pst.setObject(2,vehicleCategory.getCategoryName());

        return pst.executeUpdate()>0;

    }

    public static ObservableList<CategoryTM> getAllCategory () throws SQLException, ClassNotFoundException {
        ObservableList<CategoryTM> observableList = FXCollections.observableArrayList();
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Vehicle_Catergorize ");
        ResultSet rst= pst.executeQuery();
        while (rst.next()){
            observableList.add(new CategoryTM(
                    rst.getString(1),
                    rst.getString(2)
            ));
        }
        return observableList;
    }

    public boolean deleteCategory (String category) throws SQLException, ClassNotFoundException {
        return DbConnection.getInstance().getConnection().prepareStatement
                ("DELETE FROM Vehicle_Catergorize WHERE Catergorize_ID = '" + category + "'").executeUpdate() > 0;

    }

}
