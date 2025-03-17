package entity;

import java.sql.Timestamp;

public class CartItem {
    private int cartItemId;
    private int accountId;
    private int bookId;
    private int quantity;
    private String bookFormat; // Ebook hoặc Physical
    private Timestamp addedAt;

    public CartItem() {}

    public CartItem(int cartItemId, int accountId, int bookId, int quantity, String bookFormat, Timestamp addedAt) {
        this.cartItemId = cartItemId;
        this.accountId = accountId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.bookFormat = bookFormat;
        this.addedAt = addedAt;
    }

    // Getters & Setters
    public int getCartItemId() { return cartItemId; }
    public void setCartItemId(int cartItemId) { this.cartItemId = cartItemId; }

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getBookFormat() { return bookFormat; }
    public void setBookFormat(String bookFormat) { this.bookFormat = bookFormat; }

    public Timestamp getAddedAt() { return addedAt; }
    public void setAddedAt(Timestamp addedAt) { this.addedAt = addedAt; }
}
