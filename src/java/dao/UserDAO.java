package dao;

import config.DBConnect;
import entity.User;
import java.sql.*;

public class UserDAO {
    
        //Hàm để lấy account detail bên Admin
    public User getAccountDetail(int accountId) throws ClassNotFoundException {
        String sql = "SELECT a.account_id, a.username, a.password, a.email, a.role, "
                + "u.full_name, u.phone, u.address, u.image "
                + "FROM Account a "
                + "LEFT JOIN User_Profile u ON a.account_id = u.account_id "
                + "WHERE a.account_id = ?";

        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getBoolean("role"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("image")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Hàm lấy thông tin người dùng theo account_id
    public User getUserById(int accountId) throws ClassNotFoundException {
        String sql = "SELECT a.account_id, a.username, a.password, a.email, a.role, "
                + "u.full_name, u.phone, u.address, u.image "
                + "FROM Account a "
                + "LEFT JOIN User_Profile u ON a.account_id = u.account_id "
                + "WHERE a.account_id = ?";
        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getBoolean("role"),
                        rs.getString("full_name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("image")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    
    public boolean updateUser(int accountId, String full_name, String phone, String address, String image) throws ClassNotFoundException {
        String sql = "UPDATE User_Profile SET full_name = ?, phone = ?, address = ?, image = ? WHERE account_id = ?";
        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
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

    // Hàm thêm mới Account, trả về accountId nếu thành công
    public int insertAccount(User user) throws ClassNotFoundException {
        String sql = "INSERT INTO Account (username, password, email, role) VALUES (?, ?, ?, ?)";
        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setBoolean(4, user.isRole()); // Mặc định false (user thường)

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Trả về accountId vừa tạo
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Lỗi
    }

    // Hàm thêm thông tin User_Profile, nhận accountId vừa tạo
    public boolean insertUserProfile(User user) throws ClassNotFoundException {
        String sql = "INSERT INTO User_Profile (account_id, full_name, phone, address, image) VALUES (?, ?, ?, ?, ?)";
        try ( Connection conn = DBConnect.connect();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getAccountId());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getImage());
            return ps.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Lỗi
    }

}
