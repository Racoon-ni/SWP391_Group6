package dao;

import config.DBConnect;
import java.sql.*;

public class TransactionDAO {

    public int createTransaction(int accountId, double totalPrice, String paymentMethod,String address) throws Exception {
        String query = "INSERT INTO Transactions (account_id, total_price, payment_method, is_paid, status, shipping_address) " +
                       "VALUES (?, ?, ?, 1, 'Completed', ?)";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, accountId);
            stmt.setDouble(2, totalPrice);
            stmt.setString(3, paymentMethod);
            stmt.setString(4, address);

            int affected = stmt.executeUpdate();
            if (affected == 0) throw new SQLException("Creating transaction failed, no rows affected.");

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                else throw new SQLException("Creating transaction failed, no ID obtained.");
            }
        }
    }
    
    public void updateTransactionStatus(int transactionId, String newStatus) throws SQLException, ClassNotFoundException {
    String sql = "UPDATE [dbo].[Transactions] " +
                "SET [status] = ? " +
                "WHERE transaction_id = ?";

    try (Connection conn = DBConnect.connect();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, newStatus);
        ps.setInt(2, transactionId);
        
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("Failed to update transaction status. Transaction ID not found: " + transactionId);
        }
    }
}
}
