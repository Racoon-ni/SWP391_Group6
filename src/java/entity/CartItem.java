/**
 *
 * @author ThinhLVCE181726 <your.name at your.org>
 */
package entity;

import java.sql.Timestamp;

public class CartItem {

    private int cart_item_id;
    private int account_id;
    private int book_id;
    private String bookTitle; // Lấy từ bảng Books
    private String cover_image; // Hình ảnh của sách
    private int quantity;
    private String book_format;
    private double price; // Lấy từ bảng Books
    private Timestamp added_at;

    public CartItem() {
    }

    public CartItem(int cart_item_id, int account_id, int book_id, String bookTitle,  String cover_image, int quantity, String book_format, double price, Timestamp added_at) {
        this.cart_item_id = cart_item_id;
        this.account_id = account_id;
        this.book_id = book_id;
        this.bookTitle = bookTitle;
        this.cover_image = cover_image;
        this.quantity = quantity;
        this.book_format = book_format;
        this.price = price;
        this.added_at = added_at;
    }

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

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getAdded_at() {
        return added_at;
    }

    public void setAdded_at(Timestamp added_at) {
        this.added_at = added_at;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

}
