package dao;

import config.DBConnect;
import config.DBConnect;
import entity.Transactions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO {
    public boolean createTransaction(Transactions transaction) throws ClassNotFoundException {
        String sql = "INSERT INTO Transactions (account_id, total_price, payment_method, transaction_date, status) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnect.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, transaction.getAccount_id());
            ps.setDouble(2, transaction.getTotal_price());
            ps.setString(3, transaction.getPayment_method());
            ps.setTimestamp(4, transaction.getTransaction_date());
            ps.setString(5, transaction.getStatus());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
