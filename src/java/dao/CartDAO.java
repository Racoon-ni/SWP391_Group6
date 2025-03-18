package dao;

import config.DBConnect;
import entity.CartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {

    // Thêm sách vào giỏ hàng
    public boolean addToCart(int accountId, int bookId, int quantity, String bookFormat) {
        String query = "INSERT INTO CartItem (account_id, book_id, quantity, book_format, added_at) VALUES (?, ?, ?, ?, GETDATE())";
        try (Connection conn = DBConnect.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accountId);
            ps.setInt(2, bookId);
            ps.setInt(3, quantity);
            ps.setString(4, bookFormat);

            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách giỏ hàng theo user
    public List<CartItem> getCartByUser(int accountId) {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT * FROM CartItem WHERE account_id = ?";
        try (Connection conn = DBConnect.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem(
                        rs.getInt("cart_item_id"),
                        rs.getInt("account_id"),
                        rs.getInt("book_id"),
                        rs.getInt("quantity"),
                        rs.getString("book_format"),
                        rs.getTimestamp("added_at")
                );
                cartItems.add(item);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    // Xóa sách khỏi giỏ hàng
    public boolean removeFromCart(int cartItemId) {
        String query = "DELETE FROM CartItem WHERE cart_item_id = ?";
        try (Connection conn = DBConnect.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật số lượng sách trong giỏ hàng
    public boolean updateCartItem(int cartItemId, int quantity) {
        String query = "UPDATE CartItem SET quantity = ? WHERE cart_item_id = ?";
        try (Connection conn = DBConnect.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
