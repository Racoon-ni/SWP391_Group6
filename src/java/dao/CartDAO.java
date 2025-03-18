package dao;

import config.DBConnect;
import entity.Book;
import entity.CartItem;
import java.sql.*;
import java.util.ArrayList;

public class CartDAO {
   // Thêm sách vào giỏ hàng
public boolean addToCart(int userId, int bookId, int quantity, String bookFormat) throws ClassNotFoundException, SQLException {
    // Kiểm tra xem book_id có tồn tại không
    BookDAO bookDAO = new BookDAO();
    Book book = bookDAO.getBookById(bookId);
    if (book == null) {
        System.out.println("❌ Không thể thêm vào giỏ hàng: book_id không tồn tại");
        return false;
    }

    String sql = "INSERT INTO Cart (user_id, book_id, quantity, book_format) VALUES (?, ?, ?, ?)";
    
    try (Connection conn = new DBConnect().connect();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, userId);
        ps.setInt(2, bookId);
        ps.setInt(3, quantity);
        ps.setString(4, bookFormat);
        
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}



    // Lấy danh sách giỏ hàng của người dùng
    public ArrayList<CartItem> getCartItems(int accountId) throws ClassNotFoundException {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT * FROM CartItem WHERE account_id = ?";
        try (Connection conn = DBConnect.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cartItems.add(new CartItem(
                    rs.getInt("cart_item_id"),
                    rs.getInt("account_id"),
                    rs.getInt("book_id"),
                    rs.getInt("quantity"),
                    rs.getString("book_format"),
                    rs.getTimestamp("added_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    // Xóa một sản phẩm khỏi giỏ hàng
    public boolean removeCartItem(int cartItemId) throws ClassNotFoundException {
        String sql = "DELETE FROM CartItem WHERE cart_item_id = ?";
        try (Connection conn = DBConnect.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa toàn bộ giỏ hàng
    public boolean clearCart(int accountId) throws ClassNotFoundException {
        String sql = "DELETE FROM CartItem WHERE account_id = ?";
        try (Connection conn = DBConnect.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xử lý thanh toán một sản phẩm trong giỏ hàng
    public boolean checkoutSingleItem(int cartItemId, int accountId, double total_price, String paymentMethod) throws ClassNotFoundException {
        String sql = "INSERT INTO Transactions (account_id, total_price, payment_method, transaction_date, status) " +
                     "VALUES (?, ?, ?, ?, 'Completed')";
        try (Connection conn = DBConnect.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.setDouble(2, total_price);
            ps.setString(3, paymentMethod);
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            if (ps.executeUpdate() > 0) {
                return removeCartItem(cartItemId); // Xóa sản phẩm khỏi giỏ hàng sau khi thanh toán
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xử lý thanh toán toàn bộ giỏ hàng
    public boolean checkoutAllItems(int accountId, double total_price, String paymentMethod) throws ClassNotFoundException {
        String sql = "INSERT INTO Transactions (account_id, total_price, payment_method, transaction_date, status) " +
                     "VALUES (?, ?, ?, ?, 'Completed')";
        try (Connection conn = DBConnect.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            ps.setDouble(2, total_price);
            ps.setString(3, paymentMethod);
            ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            if (ps.executeUpdate() > 0) {
                return clearCart(accountId); // Xóa toàn bộ giỏ hàng sau khi thanh toán
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
