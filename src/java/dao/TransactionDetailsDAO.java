package dao;

import config.DBConnect;
import entity.TransactionDetails;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for TransactionDetails
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class TransactionDetailsDAO {

    // Lấy danh sách chi tiết giao dịch theo transaction_id
    public List<TransactionDetails> getTransactionById(int transactionId) throws ClassNotFoundException {
        List<TransactionDetails> transactionDetailsList = new ArrayList<>();
        String query = "SELECT * FROM TransactionDetails WHERE transaction_id = ?";

        try ( Connection conn = DBConnect.connect();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, transactionId);
            try ( ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactionDetailsList.add(mapResultSetToTransactionDetails(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionDetailsList;
    }

    // Lấy danh sách tất cả giao dịch
    public List<TransactionDetails> getAllTransactions() throws ClassNotFoundException {
        List<TransactionDetails> transactionDetailsList = new ArrayList<>();
        String query = "SELECT * FROM TransactionDetails";

        try ( Connection conn = DBConnect.connect();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                transactionDetailsList.add(mapResultSetToTransactionDetails(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionDetailsList;
    }

    // Lấy danh sách tất cả giao dịch của một user theo user_id
    public List<TransactionDetails> getAllTransactionsByUserId(int userId) {
        List<TransactionDetails> transactions = new ArrayList<>();
        String sql = "SELECT td.transaction_detail_id, td.transaction_id, td.book_id, "
                + "td.quantity, td.book_format, td.price, td.file_path, "
                + "t.total_price, t.payment_method, t.transaction_date, t.status, t.is_paid, "
                + "b.title AS book_title, b.author_id, b.description, b.cover_image, "
                + "b.publisher, b.publication_year, b.language, b.book_type "
                + "FROM TransactionDetails td "
                + "JOIN Transactions t ON td.transaction_id = t.transaction_id "
                + "JOIN Books b ON td.book_id = b.book_id "
                + "WHERE t.account_id = ?";

        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransactionDetails(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }

    // Lấy chi tiết một giao dịch theo transaction_id
    public TransactionDetails showTransactionDetails(int transactionId) {
        String sql = "SELECT td.transaction_detail_id, td.transaction_id, td.book_id, "
                + "td.quantity, td.book_format, td.price, td.file_path, "
                + "t.total_price, t.payment_method, t.transaction_date, t.status, t.is_paid, "
                + "b.title AS book_title, b.author_id, b.description, b.cover_image, "
                + "b.publisher, b.publication_year, b.language, b.book_type, "
                + "up.address AS shipping_address "
                + "FROM TransactionDetails td "
                + "JOIN Transactions t ON td.transaction_id = t.transaction_id "
                + "JOIN Books b ON td.book_id = b.book_id "
                + "JOIN User_Profile up ON t.account_id = up.account_id "
                + "WHERE td.transaction_id = ?";

        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, transactionId);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TransactionDetails details = mapResultSetToTransactionDetails(rs);
                    details.setShippingAddress(rs.getString("shipping_address"));
                    return details;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getShippingAddress(int transactionId) {
        String sql = "SELECT up.address FROM User_Profile up "
                + "JOIN Account a ON up.account_id = a.account_id "
                + "JOIN Transactions t ON a.account_id = t.account_id "
                + "WHERE t.transaction_id = ?";

        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, transactionId);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("address");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Trả về null nếu không tìm thấy địa chỉ
    }

    // Phương thức hỗ trợ để map ResultSet thành TransactionDetails
    private TransactionDetails mapResultSetToTransactionDetails(ResultSet rs) throws SQLException {
        TransactionDetails details = new TransactionDetails();
        details.setTransactionDetailId(rs.getInt("transaction_detail_id"));
        details.setTransactionId(rs.getInt("transaction_id"));
        details.setBook_id(rs.getInt("book_id"));  // Fixed setter method
        details.setQuantity(rs.getInt("quantity"));
        details.setBookFormat(rs.getString("book_format"));
        details.setPrice(rs.getDouble("price"));
        details.setFilePath(rs.getString("file_path"));
        details.setTransactionDate(rs.getString("transaction_date"));  // Set transactionDate field
        return details;
    }
}
