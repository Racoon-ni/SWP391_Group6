package dao;

import config.DBConnect;
import entity.Book;
import entity.Category;
import entity.Interaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InteractionDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    //user add comment
    public boolean addComment(int account_id, int book_id, double rating, String comment) {
        String query = "INSERT INTO Interaction(account_id, book_id, rating, comment, status, created_at) VALUES(?, ?, ?, ?, 0, ?)";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, account_id);
            ps.setInt(2, book_id);
            ps.setDouble(3, rating);
            ps.setString(4, comment);
            ps.setTimestamp(5, new Timestamp(new Date().getTime()));
            return ps.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get comments for a specific book
    public List<Interaction> getCommentsByBookId(int bookId) {
        List<Interaction> commentList = new ArrayList<>();
        String query = "SELECT i.interaction_id, i.account_id, i.book_id, i.rating, i.comment, i.created_at, i.status, "
                + "a.username "
                + "FROM Interaction i "
                + "JOIN Account a ON i.account_id = a.account_id "
                + "WHERE i.book_id = ? AND (i.status = 0 OR i.status = 1)"
                + "ORDER BY i.created_at DESC";

        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Interaction interaction = new Interaction(
                        rs.getInt("interaction_id"),
                        rs.getInt("account_id"),
                        rs.getInt("book_id"),
                        null, // action is null for comments
                        rs.getDouble("rating"),
                        rs.getString("comment"),
                        rs.getString("username"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("status")
                );
                commentList.add(interaction);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return commentList;
    }

    // Get average rating for a book
    public double getAverageRatingByBookId(int bookId) {
        String query = "SELECT AVG(rating) as avg_rating FROM Interaction WHERE book_id = ?";
        double avgRating = 0.0;

        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();

            if (rs.next()) {
                avgRating = rs.getDouble("avg_rating");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return avgRating;
    }

    // Count total reviews for a book
    public int getReviewCountByBookId(int bookId) {
        String query = "SELECT COUNT(*) as review_count FROM Interaction WHERE book_id = ?";
        int reviewCount = 0;

        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, bookId);
            rs = ps.executeQuery();

            if (rs.next()) {
                reviewCount = rs.getInt("review_count");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return reviewCount;
    }

    public boolean reportComment(int interactionId, int accountIdReported, String reason) {
        Connection conn = null;
        PreparedStatement psUpdate = null;
        PreparedStatement psInsert = null;

        try {
            conn = new DBConnect().connect();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // 1️⃣ Cập nhật trạng thái comment thành "Reported" (status = 1)
            String updateQuery = "UPDATE Interaction SET status = 1 WHERE interaction_id = ?";
            psUpdate = conn.prepareStatement(updateQuery);
            psUpdate.setInt(1, interactionId);
            int updateResult = psUpdate.executeUpdate();

            // 2️⃣ Chèn vào bảng UserWarning để ghi nhận ai đã báo cáo
            String insertQuery = "INSERT INTO UserWarning (account_id_reported, account_id, note, interaction_id) "
                    + "SELECT ?, account_id, ?, ? FROM Interaction WHERE interaction_id = ?";
            psInsert = conn.prepareStatement(insertQuery);
            psInsert.setInt(1, accountIdReported);
            psInsert.setString(2, reason);
            psInsert.setInt(3, interactionId);
            psInsert.setInt(4, interactionId);
            int insertResult = psInsert.executeUpdate();

            // 3️⃣ Kiểm tra cả hai câu lệnh thành công
            if (updateResult > 0 && insertResult > 0) {
                conn.commit(); // Xác nhận transaction
                return true;
            } else {
                conn.rollback(); // Hoàn tác nếu có lỗi
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
                if (psInsert != null) {
                    psInsert.close();
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

    // Get all comments
    public List<Interaction> getAllComments() {
        List<Interaction> commentList = new ArrayList<>();
        String query = "SELECT i.interaction_id, i.account_id, i.book_id, i.rating, i.comment, i.created_at, i.status, "
                + "a.username, b.title AS book_title "
                + "FROM Interaction i "
                + "JOIN Account a ON i.account_id = a.account_id "
                + "JOIN Books b ON i.book_id = b.book_id "
                + "ORDER BY i.created_at DESC";

        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Interaction interaction = new Interaction(
                        rs.getInt("interaction_id"),
                        rs.getInt("account_id"),
                        rs.getInt("book_id"),
                        null, // No action needed for comments
                        rs.getDouble("rating"),
                        rs.getString("comment"),
                        rs.getString("username"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("status")
                );
                commentList.add(interaction);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return commentList;
    }

    public List<Interaction> getCommentsByStatus(int status) {
        List<Interaction> commentList = new ArrayList<>();
        String query = "SELECT i.interaction_id, i.account_id, i.book_id, i.rating, i.comment, i.created_at, i.status, "
                + "a.username "
                + "FROM Interaction i "
                + "JOIN Account a ON i.account_id = a.account_id "
                + "WHERE i.status = ? "
                + "ORDER BY i.created_at DESC";

        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, status);
            rs = ps.executeQuery();

            while (rs.next()) {
                Interaction interaction = new Interaction(
                        rs.getInt("interaction_id"),
                        rs.getInt("account_id"),
                        rs.getInt("book_id"),
                        null, // action is null for comments
                        rs.getDouble("rating"),
                        rs.getString("comment"),
                        rs.getString("username"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("status")
                );
                commentList.add(interaction);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return commentList;
    }

    public boolean deleteComment(int interactionId) {
        String deleteWarningsQuery = "DELETE FROM UserWarning WHERE interaction_id = ?";
        String deleteCommentQuery = "DELETE FROM Interaction WHERE interaction_id = ?";
        Connection conn = null;
        PreparedStatement psWarnings = null;
        PreparedStatement psComment = null;

        try {
            conn = new DBConnect().connect();
            conn.setAutoCommit(false); // Bắt đầu transaction

            // Xóa tất cả bản ghi trong UserWarning liên quan đến comment này
            psWarnings = conn.prepareStatement(deleteWarningsQuery);
            psWarnings.setInt(1, interactionId);
            psWarnings.executeUpdate();

            // Xóa comment trong Interaction
            psComment = conn.prepareStatement(deleteCommentQuery);
            psComment.setInt(1, interactionId);
            int rowsAffected = psComment.executeUpdate();

            if (rowsAffected == 1) {
                conn.commit(); // Xác nhận transaction
                return true;
            } else {
                conn.rollback(); // Hoàn tác nếu có lỗi
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
                if (psWarnings != null) {
                    psWarnings.close();
                }
                if (psComment != null) {
                    psComment.close();
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

    public boolean updateComment(int interactionId, String newComment, double newRating) {
        String query = "UPDATE Interaction SET comment = ?, rating = ?, created_at = ? WHERE interaction_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setString(1, newComment);
            ps.setDouble(2, newRating);
            ps.setTimestamp(3, new Timestamp(new Date().getTime())); // Update timestamp
            ps.setInt(4, interactionId);
            return ps.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Interaction getCommentById(int interactionId) {
        String query = "SELECT interaction_id, account_id, book_id, rating, comment, created_at FROM Interaction WHERE interaction_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, interactionId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return new Interaction(
                        rs.getInt("interaction_id"),
                        rs.getInt("account_id"),
                        rs.getInt("book_id"),
                        null, // No action needed for comments
                        rs.getDouble("rating"),
                        rs.getString("comment"),
                        null, // Username is not fetched in this query
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
// Disable all comments from a warned user

    public boolean disableUserComments(int accountId, int interactionId) {
        String query = "UPDATE Interaction SET status = 2 WHERE account_id = ? AND interaction_id = ?";
        try {
            conn = new DBConnect().connect();
            ps = conn.prepareStatement(query);
            ps.setInt(1, accountId);
            ps.setInt(2, interactionId);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
