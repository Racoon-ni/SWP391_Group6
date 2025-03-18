package dao;

import config.DBConnect;
import db.DBConnect;
import entity.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // Thêm giao dịch mới (Khi người dùng mua sách)
    public int createTransaction(Transaction transaction) throws ClassNotFoundException, SQLException {
        String query = "INSERT INTO Transactions (account_id, total_price, payment_method, transaction_date, status, is_paid) "
                     + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect.connect();
             PreparedStatement pstmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, transaction.getAccountId());
            pstmt.setDouble(2, transaction.getTotalPrice());
            pstmt.setString(3, transaction.getPaymentMethod());
            pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis())); // Thời gian hiện tại
            pstmt.setString(5, "Pending"); // Mặc định là 'Pending'
            pstmt.setBoolean(6, false); // Chưa thanh toán

            int affectedRows = pstmt.executeUpdate();

            // Lấy transaction_id vừa tạo
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Trả về transaction_id mới
                }
            }
        }
        return -1;
    }

    // Lấy danh sách giao dịch theo account_id (Lịch sử mua hàng)
    public List<Transaction> getTransactionsByAccountId(int accountId) throws ClassNotFoundException, SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM Transactions WHERE account_id = ? ORDER BY transaction_date DESC";

        try (Connection conn = DBConnect.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, accountId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_id"),
                        rs.getDouble("total_price"),
                        rs.getString("payment_method"),
                        rs.getTimestamp("transactionDate"),
                        rs.getString("status"),
                        rs.getBoolean("is_paid")
                ));
            }
        }
        return transactions;
    }

    // Cập nhật trạng thái thanh toán (Khi người dùng thanh toán thành công)
    public boolean updatePaymentStatus(int transactionId, boolean isPaid) throws ClassNotFoundException, SQLException {
        String query = "UPDATE Transactions SET is_paid = ?, status = ? WHERE transaction_id = ?";

        try (Connection conn = DBConnect.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setBoolean(1, isPaid);
            pstmt.setString(2, isPaid ? "Completed" : "Pending");
            pstmt.setInt(3, transactionId);

            return pstmt.executeUpdate() > 0;
        }
    }
}
