package controller;

import db.DbConnection;
import model.RentDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryController {

    public List<RentDetail> searchRentHistory (String value) throws SQLException, ClassNotFoundException {
        List<RentDetail>rentDetails = new ArrayList<>();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Rent_Detail WHERE Rent_ID LIKE '%"+value+"%' ").executeQuery();
        while (rst.next()){
            rentDetails.add(new RentDetail(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    Double.parseDouble(rst.getString(6)),
                    rst.getString(7)
            ));
        }
        return rentDetails;
    }
}
