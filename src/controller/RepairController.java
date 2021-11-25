package controller;

import db.DbConnection;
import model.Rent;
import model.Repair;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RepairController {

    public String getRepairId() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Repair_Vehicle ORDER BY Repair_Vehicle_ID DESC LIMIT 1").executeQuery();
        if (rst.next()) {
            int tempId = Integer.parseInt(rst.getString(2).split("-")[1]);
            tempId += 1;
            if (tempId < 10) {
                return "Rep-00" + tempId;
            } else if (tempId < 100) {
                return "Rep-0" + tempId;
            } else {
                return "Rep-" + tempId;
            }
        } else {
            return "Rep-000";
        }
    }

    public boolean saveRepair(Repair repair) {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement("INSERT INTO Repair_Vehicle VALUES (?,?,?)");
            stm.setObject(1, repair.getVehicleNumber());
            stm.setObject(2, repair.getRepairID());

            stm.setObject(3, repair.getStatus());
            if (stm.executeUpdate() > 0){
                if (updateStatus(repair.getVehicleNumber(), repair.getStatus())) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    private boolean updateStatus(String vehicleNumber, String status) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement
                ("UPDATE Vehicle SET Status ='" + status + "' WHERE Vehicle_Number='" + vehicleNumber + "'");
        return stm.executeUpdate() > 0;
    }

}
