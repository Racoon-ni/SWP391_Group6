/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConnect;
import entity.Account;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class AccountDAO {

    Connection conn = null;

    // /**
    // *
    // */
    // public AccountDAO() {
    // try {
    // conn = DBConnect.connect();
    // } catch (ClassNotFoundException | SQLException ex) {
    // Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
    // }
    // }
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Account login(String username, String password) throws ClassNotFoundException {
        String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
        try {
            conn = DBConnect.connect();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getBoolean("role"), // 1: admin, 0: user
                        rs.getInt("status") // 1: active, 0: inactive
                );
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public Account getAccountById(int accountId) throws ClassNotFoundException {
        String sql = "SELECT a.account_id, a.username, a.email, a.password, a.role, "
                + "FROM Account "
                + "WHERE a.account_id = ? AND a.status = 1";

        // Kết nối và thực hiện truy vấn
        try (Connection conn = DBConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId); // Gán giá trị accountId vào truy vấn
        try (Connection conn = DBConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId); // Gán giá trị accountId vào truy vấn
            ResultSet rs = ps.executeQuery();

            // Kiểm tra nếu có kết quả trả về
            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getBoolean("role"), // Lấy thông tin role (admin/user)
                        rs.getInt("status") // Lấy thông tin trạng thái (active/inactive)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Nếu không tìm thấy người dùng
        return null; // Nếu không tìm thấy người dùng
    }

<<<<<<< HEAD
    public boolean updateAccount(int accountId, String username, String password, String email) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Account SET username = ?, password = ?, email = ? WHERE account_id = ?";
        try (Connection conn = DBConnect.connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setInt(4, accountId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateAccount(int accountId, String full_name, String phone, String address, String image)
            throws ClassNotFoundException {
        String sql = "UPDATE Account SET full_name = ?, phone = ?, address = ?, image = ? WHERE account_id = ?";
        try (Connection conn = DBConnect.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, full_name);
            ps.setString(2, phone);
            ps.setString(3, address);
            ps.setString(4, image);
            ps.setInt(5, accountId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức cập nhật mật khẩu
    public boolean changePassword(int accountId, String oldPassword, String newPassword) throws ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmtCheck = null;
        PreparedStatement pstmtUpdate = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.connect();
            conn.setAutoCommit(false);

            // Kiểm tra mật khẩu cũ có đúng không
            String queryCheck = "SELECT password FROM Account WHERE account_id = ?";
            pstmtCheck = conn.prepareStatement(queryCheck);
            pstmtCheck.setInt(1, accountId);
            rs = pstmtCheck.executeQuery();

            if (rs.next()) {
                String currentPasswordDB = rs.getString("password");

                if (!currentPasswordDB.equals(oldPassword)) {
                    return false; // Mật khẩu cũ không đúng
                }
            } else {
                return false; // Không tìm thấy tài khoản
            }

            // Cập nhật mật khẩu mới
            String queryUpdate = "UPDATE Account SET password = ? WHERE account_id = ?";
            pstmtUpdate = conn.prepareStatement(queryUpdate);
            pstmtUpdate.setString(1, newPassword);
            pstmtUpdate.setInt(2, accountId);
            pstmtUpdate.executeUpdate();

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmtCheck != null) {
                    pstmtCheck.close();
                }
                if (pstmtUpdate != null) {
                    pstmtUpdate.close();
                }
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    // Kiểm tra xem username có tồn tại chưa

    public boolean isAccountExists(String username) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM Account WHERE username = ?";
        try (Connection conn = DBConnect.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1); // lấy giá trị COUNT(*)
                return count > 0; // nếu > 0 là đã tồn tại
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // log lỗi
        }
        return false; // lỗi hoặc không tìm thấy username
    }

    // Lấy tất cả các account có trong dữ liệu
    // Lấy tất cả các account có trong dữ liệu
    public List<Account> getAllAccounts() throws ClassNotFoundException, SQLException {
        List<Account> accountList = new ArrayList<>();
        String query = "SELECT * FROM Account"; // Truy vấn lấy tất cả tài khoản

        try (Connection conn = DBConnect.connect();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {
        try (Connection conn = DBConnect.connect();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Account acc = new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getBoolean("role"), // 1: admin, 0: user
                        rs.getInt("status"));
                accountList.add(acc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }

    public boolean deleteAccount(int accountID) throws ClassNotFoundException {
        String query = "DELETE FROM Account WHERE account_id = ?";

        try (Connection conn = DBConnect.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, accountID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean toggleLockAccount(int accountID) throws ClassNotFoundException {
        String query = "UPDATE Account SET status = CASE WHEN status = 1 THEN 0 ELSE 1 END WHERE account_id = ?";

        try (Connection conn = DBConnect.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, accountID);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Account> searchAccount(String keyword) throws ClassNotFoundException {
        List<Account> accountList = new ArrayList<>();
        String query = "SELECT * FROM Account WHERE username LIKE ?";

        try (Connection conn = DBConnect.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + keyword + "%"); // Tìm username chứa từ khóa

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Account acc = new Account();
                    acc.setAccountId(rs.getInt("account_id"));
                    acc.setUsername(rs.getString("username"));
                    acc.setEmail(rs.getString("email"));
                    acc.setRole(rs.getBoolean("role")); // true = Admin, false = Customer
                    accountList.add(acc);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accountList;
    }


    public boolean lockAccount(int accountId) {
        String query = "UPDATE Account SET status = 2 WHERE account_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, accountId);
            return ps.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean unlockAccount(int accountId) {
        Connection conn = null;
        PreparedStatement psUpdate = null;
        PreparedStatement psDelete = null;

        try {
            conn = new DBConnect().connect();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1️⃣ Cập nhật trạng thái User về Active (status = 1)
            String updateQuery = "UPDATE Account SET status = 1 WHERE account_id = ?";
            psUpdate = conn.prepareStatement(updateQuery);
            psUpdate.setInt(1, accountId);
            int updateResult = psUpdate.executeUpdate();

            // 2️⃣ Xóa hết WarningUser của user này
            String deleteQuery = "DELETE FROM UserWarning WHERE account_id = ?";
            psDelete = conn.prepareStatement(deleteQuery);
            psDelete.setInt(1, accountId);
            int deleteResult = psDelete.executeUpdate();

            // 3️⃣ Nếu cả hai câu lệnh thành công thì commit transaction
            if (updateResult > 0) {
                conn.commit();
                return true;
            } else {
                conn.rollback();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback(); // Hoàn tác nếu có lỗi
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (psUpdate != null) {
                    psUpdate.close();
                }
                if (psDelete != null) {
                    psDelete.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
