package dao;

import config.DBConnect;
import entity.CartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CartDAO {
    // Lấy danh sách sản phẩm trong giỏ hàng
    public List<CartItem> getCartItems(int accountId) throws ClassNotFoundException {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT c.cart_item_id, c.account_id, c.book_id, b.title AS bookTitle, b.cover_image, " +
                       "c.quantity, c.book_format, b.price, c.added_at " +
                       "FROM CartItem c " +
                       "JOIN Books b ON c.book_id = b.book_id " +
                       "WHERE c.account_id = ?";

        try (Connection conn = DBConnect.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int cartItemId = rs.getInt("cart_item_id");
                int accId = rs.getInt("account_id");
                int bookId = rs.getInt("book_id");
                String bookTitle = rs.getString("bookTitle");
                String coverImage = rs.getString("cover_image"); // Lấy hình ảnh sách
                int quantity = rs.getInt("quantity");
                String bookFormat = rs.getString("book_format");
                double price = rs.getDouble("price");
                Timestamp addedAt = rs.getTimestamp("added_at");

                cartItems.add(new CartItem(cartItemId, accId, bookId, bookTitle, coverImage, quantity, bookFormat, price, addedAt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return cartItems;
    }

    public void addToCart(int accountId, int bookId, int quantity, String bookFormat) throws ClassNotFoundException {
        String checkQuery = "SELECT quantity FROM CartItem WHERE account_id = ? AND book_id = ? AND book_format = ?";
        String updateQuery = "UPDATE CartItem SET quantity = quantity + ? WHERE account_id = ? AND book_id = ? AND book_format = ?";
        String insertQuery = "INSERT INTO CartItem (account_id, book_id, quantity, book_format) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnect.connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            checkStmt.setInt(1, accountId);
            checkStmt.setInt(2, bookId);
            checkStmt.setString(3, bookFormat);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, accountId);
                updateStmt.setInt(3, bookId);
                updateStmt.setString(4, bookFormat);
                updateStmt.executeUpdate();
            } else {
                insertStmt.setInt(1, accountId);
                insertStmt.setInt(2, bookId);
                insertStmt.setInt(3, quantity);
                insertStmt.setString(4, bookFormat);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật số lượng sách trong giỏ hàng
public void updateCartByBookId(int accountId, int bookId, int quantity) throws ClassNotFoundException {
    String query = "UPDATE CartItem SET quantity = ? WHERE account_id = ? AND book_id = ?";
    
    try (Connection conn = DBConnect.connect();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setInt(1, quantity);
        stmt.setInt(2, accountId);
        stmt.setInt(3, bookId);
        
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Xóa sản phẩm khỏi giỏ hàng bằng book_id
public void removeFromCartByBookId(int accountId, int bookId) throws ClassNotFoundException {
    String query = "DELETE FROM CartItem WHERE account_id = ? AND book_id = ?";
    
    try (Connection conn = DBConnect.connect();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setInt(1, accountId);
        stmt.setInt(2, bookId);
        
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // Xóa toàn bộ giỏ hàng của người dùng
public void clearCart(int accountId) throws ClassNotFoundException {
    String query = "DELETE FROM CartItem WHERE account_id = ?";
    try (Connection conn = DBConnect.connect();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, accountId);
        int rowsAffected = stmt.executeUpdate();
        
        if (rowsAffected > 0) {
            System.out.println("Đã xóa toàn bộ giỏ hàng thành công!");
        } else {
            System.out.println("Không có sản phẩm nào để xóa.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



}
