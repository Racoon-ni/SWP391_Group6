package dao;

import config.DBConnect;
import java.sql.*;

public class TransactionDAO {

    public int createTransaction(int accountId, double totalPrice, String paymentMethod) throws Exception {
        String query = "INSERT INTO Transactions (account_id, total_price, payment_method, is_paid, status) " +
                       "VALUES (?, ?, ?, 1, 'Completed')";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, accountId);
            stmt.setDouble(2, totalPrice);
            stmt.setString(3, paymentMethod);

            int affected = stmt.executeUpdate();
            if (affected == 0) throw new SQLException("Creating transaction failed, no rows affected.");

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                else throw new SQLException("Creating transaction failed, no ID obtained.");
            }
        }
    }
}
