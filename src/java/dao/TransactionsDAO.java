package dao;

import config.DBConnect;
import entity.Transactions;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class TransactionsDAO {

    // Hàm lấy thông tin giao dịch theo transaction_id
    public Transactions getTransactionById(int transactionId) throws ClassNotFoundException {
        String sql = "SELECT t.transaction_id, t.account_id, t.total_price, t.payment_method, t.transaction_date, t.status, t.is_paid "
                + "FROM Transactions t "
                + "WHERE t.transaction_id = ?";
        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, transactionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Transactions(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_id"),
                        rs.getDouble("total_price"),
                        rs.getString("payment_method"),
                        rs.getString("transaction_date"),
                        rs.getString("status"),
                        rs.getBoolean("is_paid")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Hàm lấy tất cả các giao dịch
    public List<Transactions> getAllTransactions() throws ClassNotFoundException {
        List<Transactions> transactions = new ArrayList<>();
        String sql = "SELECT t.transaction_id, t.account_id, t.total_price, t.payment_method, t.transaction_date, t.status, t.is_paid "
                + "FROM Transactions t";
        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transactions transaction = new Transactions(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_id"),
                        rs.getDouble("total_price"),
                        rs.getString("payment_method"),
                        rs.getString("transaction_date"),
                        rs.getString("status"),
                        rs.getBoolean("is_paid")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

}
