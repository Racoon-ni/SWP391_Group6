package entity;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class TransactionDetails {

    private int transactionDetailId;
    private int transactionId;
    private int book_id;
    private int quantity;
    private String bookFormat; // "Ebook" or "Physical"
    private double price;
    private String filePath; // For Ebook only
    private String shippingAddress; // New field for shipping address
    private String transactionDate; // Added transaction date field

    public TransactionDetails() {
    }

    public TransactionDetails(int transactionDetailId, int transactionId, int book_id, int quantity,
            String bookFormat, double price, String filePath, String shippingAddress, String transactionDate) {
        this.transactionDetailId = transactionDetailId;
        this.transactionId = transactionId;
        this.book_id = book_id;
        this.quantity = quantity;
        this.bookFormat = bookFormat;
        this.price = price;
        this.filePath = filePath;
        this.shippingAddress = shippingAddress;
        this.transactionDate = transactionDate; // Initialize the new field
    }

    public int getTransactionDetailId() {
        return transactionDetailId;
    }

    public void setTransactionDetailId(int transactionDetailId) {
        this.transactionDetailId = transactionDetailId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
