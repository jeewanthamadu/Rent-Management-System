package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import model.VehicleModel;
import view.tm.ModelTM;
import view.tm.UserTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelController {

    public  boolean  saveModel(VehicleModel vehicleModel) throws SQLException, ClassNotFoundException {
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("INSERT INTO Vehicle_Model VALUES (?,?)");
        pst.setObject(1,vehicleModel.getModelID());
        pst.setObject(2,vehicleModel.getModelName());

        return pst.executeUpdate()>0;

    }

    public static ObservableList<ModelTM> getAllModels () throws SQLException, ClassNotFoundException {
        ObservableList<ModelTM> observableList = FXCollections.observableArrayList();
        Connection con = DbConnection.getInstance().getConnection();
        PreparedStatement pst = con.prepareStatement("SELECT * FROM Vehicle_Model ");
        ResultSet rst= pst.executeQuery();
        while (rst.next()){
            observableList.add(new ModelTM(
                    rst.getString(1),
                    rst.getString(2)
            ));
        }
        return observableList;
    }

    public boolean deleteModel (String Model) throws SQLException, ClassNotFoundException {
        return DbConnection.getInstance().getConnection().prepareStatement
                ("DELETE FROM Vehicle_Model WHERE Model_ID = '" + Model + "'").executeUpdate() > 0;

    }

}
