package dao;

import config.DBConnect;
import entity.Order;
import entity.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public List<Order> getOrdersByAccountId(int accountId) throws ClassNotFoundException, Exception {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE account_id = ? ORDER BY order_date DESC";

        try ( Connection conn = DBConnect.connect();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setAccountId(rs.getInt("account_id"));
                order.setTransactionId(rs.getInt("transaction_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setStatus(rs.getString("status"));
                order.setShippingAddress(rs.getString("shipping_address"));

                order.setOrderItems(getOrderItemsByOrderId(order.getOrderId()));
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order getOrderById(int orderId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Orders WHERE order_id = ?";
        Order order = null;

        try ( Connection conn = DBConnect.connect();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setAccountId(rs.getInt("account_id"));
                order.setTransactionId(rs.getInt("transaction_id"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setStatus(rs.getString("status"));
                order.setOrderDate(rs.getTimestamp("order_date"));
            }
        }

        return order;
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws Exception {
        List<OrderItem> items = new ArrayList<>();
        String query = "SELECT "
                + "oi.order_item_id, oi.order_id, oi.book_id, oi.book_format, oi.price, oi.quantity, "
                + "b.title AS book_title, b.cover_image "
                + "FROM OrderItems oi "
                + "JOIN Books b ON oi.book_id = b.book_id "
                + "WHERE oi.order_id = ?";

        try ( Connection conn = DBConnect.connect();  PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setBookId(rs.getInt("book_id"));
                item.setBookFormat(rs.getString("book_format"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                item.setBookTitle(rs.getString("book_title"));
                item.setCoverImage(rs.getString("cover_image"));

                items.add(item);
            }
        }
        return items;
    }

    public List<Order> getAllOrders() throws SQLException, ClassNotFoundException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders ORDER BY order_date DESC";

        try ( Connection conn = DBConnect.connect();  PreparedStatement stmt = conn.prepareStatement(sql);  ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setAccountId(rs.getInt("account_id"));
                order.setTransactionId(rs.getInt("transaction_id"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setStatus(rs.getString("status"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                orders.add(order);
            }
        }
        return orders;
    }

    public int createOrder(Order order) throws ClassNotFoundException {
        String insertOrderSQL = "INSERT INTO Orders (account_id, transaction_id, order_date, status, shipping_address) VALUES (?, ?, ?, ?, ?)";
        int orderId = -1;

        try ( Connection conn = DBConnect.connect();  PreparedStatement stmt = conn.prepareStatement(insertOrderSQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, order.getAccountId());
            stmt.setInt(2, order.getTransactionId());
            stmt.setTimestamp(3, order.getOrderDate());
            stmt.setString(4, order.getStatus());
            stmt.setString(5, order.getShippingAddress());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    public void createOrderItems(List<OrderItem> items) throws ClassNotFoundException {
        String insertItemSQL = "INSERT INTO OrderItems (order_id, book_id, book_format, price, quantity) VALUES (?, ?, ?, ?, ?)";

        try ( Connection conn = DBConnect.connect();  PreparedStatement stmt = conn.prepareStatement(insertItemSQL)) {

            for (OrderItem item : items) {
                stmt.setInt(1, item.getOrderId());
                stmt.setInt(2, item.getBookId());
                stmt.setString(3, item.getBookFormat());
                stmt.setDouble(4, item.getPrice());
                stmt.setInt(5, item.getQuantity());
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrderStatus(int orderId, String status) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Orders SET status = ? WHERE order_id = ?";

        try ( Connection conn = DBConnect.connect();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        }
    }
    

    public List<Order> filterOrders(String status, Integer accountId, String shippingAddress) throws ClassNotFoundException {
    List<Order> orders = new ArrayList<>();
    StringBuilder query = new StringBuilder("SELECT * FROM Orders WHERE 1=1");

    List<Object> params = new ArrayList<>();

    if (status != null && !status.trim().isEmpty()) {
        query.append(" AND status = ?");
        params.add(status);
    }

    if (accountId != null) {
        query.append(" AND account_id = ?");
        params.add(accountId);
    }

    if (shippingAddress != null && !shippingAddress.trim().isEmpty()) {
        query.append(" AND shipping_address LIKE ?");
        params.add("%" + shippingAddress + "%");
    }

    try (Connection conn = DBConnect.connect();
         PreparedStatement stmt = conn.prepareStatement(query.toString())) {

        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Order o = new Order();
            o.setOrderId(rs.getInt("order_id"));
            o.setAccountId(rs.getInt("account_id"));
            o.setTransactionId(rs.getInt("transaction_id"));
            o.setOrderDate(rs.getTimestamp("order_date"));
            o.setStatus(rs.getString("status"));
            o.setShippingAddress(rs.getString("shipping_address"));
            orders.add(o);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return orders;
}

}
