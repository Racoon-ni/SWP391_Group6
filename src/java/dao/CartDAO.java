package dao;

import config.DBConnect;
import entity.CartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public void addToCart(int accountId, int bookId, int quantity, String bookFormat) throws ClassNotFoundException {
        String query = "INSERT INTO CartItem (account_id, book_id, quantity, book_format) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnect.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, accountId);
            ps.setInt(2, bookId);
            ps.setInt(3, quantity);
            ps.setString(4, bookFormat);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CartItem> getCartByAccountId(int accountId) throws ClassNotFoundException {
        List<CartItem> cartItems = new ArrayList<>();
        String query = "SELECT * FROM CartItem WHERE account_id = ?";
        try (Connection conn = DBConnect.connect();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cartItems.add(new CartItem(
                        rs.getInt("cart_item_id"),
                        rs.getInt("account_id"),
                        rs.getInt("book_id"),
                        rs.getInt("quantity"),
                        rs.getString("book_format")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }
}
