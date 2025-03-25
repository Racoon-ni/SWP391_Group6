package entity;
import entity.OrderItem;
import java.sql.Timestamp;
import java.util.List;

public class Order {
    private int orderId;
    private int accountId;
    private int transactionId;
    private Timestamp orderDate;
    private String status;
    private String shippingAddress;
    private List<OrderItem> orderItems; // Optional: dùng cho hiển thị chi tiết đơn hàng

    public Order() {}

    public Order(int orderId, int accountId, int transactionId, Timestamp orderDate, String status, String shippingAddress) {
        this.orderId = orderId;
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.orderDate = orderDate;
        this.status = status;
        this.shippingAddress = shippingAddress;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
} 