package entity;

import java.sql.Timestamp;

public class Transactions {
    private int transaction_id;
    private int account_id;
    private int book_id;
    private double total_price;
    private String payment_method;
    private Timestamp transaction_date;
    private String status;
    private boolean is_paid;

    // Constructor mặc định
    public Transactions() {}

    // Constructor đầy đủ tham số
    public Transactions(int transaction_id, int account_id, int book_id, double total_price, 
                        String payment_method, Timestamp transaction_date, String status, boolean is_paid) {
        this.transaction_id = transaction_id;
        this.account_id = account_id;
        this.book_id = book_id;
        this.total_price = total_price;
        this.payment_method = payment_method;
        this.transaction_date = transaction_date;
        this.status = status;
        this.is_paid = is_paid;
    }

    // Getters và Setters
    public int getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
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

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public Timestamp getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(Timestamp transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIs_paid() {
        return is_paid;
    }

    public void setIs_paid(boolean is_paid) {
        this.is_paid = is_paid;
    }
}
