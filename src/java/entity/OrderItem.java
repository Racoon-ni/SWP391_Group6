package entity;

public class OrderItem {

    private int orderItemId;
    private int orderId;
    private int bookId;
    private String bookTitle;
    private String bookFormat;
    private double price;
    private int quantity;
    private String coverImage;

    public OrderItem() {
    }

    public OrderItem(int orderItemId, int orderId, int bookId, String bookTitle, String bookFormat, double price, int quantity) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookFormat = bookFormat;
        this.price = price;
        this.quantity = quantity;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookFormat() {
        return bookFormat;
    }

    public void setBookFormat(String bookFormat) {
        this.bookFormat = bookFormat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}
