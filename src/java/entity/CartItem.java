package entity;

import java.sql.Timestamp;

public class CartItem {
    private int cart_item_id;
    private int account_id;
    private int book_id;
    private int quantity;
    private String book_format;
    private Timestamp added_at;

    // Constructor mặc định
    public CartItem() {}

    // Constructor đầy đủ tham số
    public CartItem(int cart_item_id, int account_id, int book_id, int quantity, String book_format, Timestamp added_at) {
        this.cart_item_id = cart_item_id;
        this.account_id = account_id;
        this.book_id = book_id;
        this.quantity = quantity;
        this.book_format = book_format;
        this.added_at = added_at;
    }

    // Getters và Setters
    public int getCart_item_id() {
        return cart_item_id;
    }

    public void setCart_item_id(int cart_item_id) {
        this.cart_item_id = cart_item_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
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

    public String getBook_format() {
        return book_format;
    }

    public void setBook_format(String book_format) {
        this.book_format = book_format;
    }

    public Timestamp getAdded_at() {
        return added_at;
    }

    public void setAdded_at(Timestamp added_at) {
        this.added_at = added_at;
    }
}
