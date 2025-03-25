package dao;

import config.DBConnect;
import entity.WarningUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HUUTHANH
 */
public class UserWarningDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Add a warning to the database
    public boolean addWarning(int accountId, int interactionId) {
        String query = "UPDATE UserWarning SET status = 1 WHERE account_id = ? AND interaction_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, accountId);           // Người bị báo cáo
            ps.setInt(2, interactionId);
            return ps.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get the total warning count for a user
    public int getWarningCount(int accountId) {
        String query = "SELECT COUNT(*) AS warning_count FROM UserWarning WHERE account_id = ?";
        int count = 0;
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, accountId);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("warning_count");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<WarningUser> getWarningUsers() {
        List<WarningUser> warningList = new ArrayList<>();
        String query = "SELECT a.username, u.account_id, " // Thêm dấu phẩy sau u.account_id
                + "       COUNT(u.warning_id) AS warning_count, "
                + "       MAX(u.warning_time) AS latest_warning_time "
                + "FROM UserWarning u "
                + "JOIN Account a ON u.account_id = a.account_id "
                + "WHERE u.status = 1 "
                + "GROUP BY a.username, u.account_id "
                + "HAVING COUNT(u.warning_id) >= 1;";

        try ( Connection conn = new DBConnect().connect();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                WarningUser user = new WarningUser(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getInt("warning_count"),
                        rs.getTimestamp("latest_warning_time")
                );
                warningList.add(user);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return warningList;
    }

    public List<WarningUser> getReportsByCommentId(int commentId) {
        List<WarningUser> reportList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = new DBConnect().connect();
            String query = "SELECT uw.warning_id, uw.account_id_reported, uw.interaction_id, uw.account_id, a.username, uw.note, uw.warning_time "
                    + "FROM UserWarning uw "
                    + "JOIN Account a ON uw.account_id_reported = a.account_id "
                    + "WHERE uw.account_id IN (SELECT account_id FROM Interaction WHERE interaction_id = ?) AND uw.interaction_id = ?";

            ps = conn.prepareStatement(query);
            ps.setInt(1, commentId);
            ps.setInt(2, commentId);
            rs = ps.executeQuery();

            while (rs.next()) {
                WarningUser warning = new WarningUser();
                warning.setAccountId(rs.getInt("account_id"));
                warning.setAccountIdReported(rs.getInt("account_id_reported"));
                warning.setUsername(rs.getString("username"));
                warning.setNote(rs.getString("note"));
                warning.setWarningTime(rs.getTimestamp("warning_time"));
                reportList.add(warning);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return reportList;
    }

    public boolean hasUserReportedComment(int userId, int interactionId) {
        String query = "SELECT COUNT(*) FROM UserWarning WHERE account_id_reported = ? AND interaction_id = ?";
        try ( Connection conn = new DBConnect().connect();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, interactionId);
            try ( ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}
