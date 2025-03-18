package entity;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int accountId;
    private double totalPrice;
    private String paymentMethod;
    private String status;
    private boolean isPaid;
    private Timestamp transactionDate;

    // Constructor mặc định
    public Transaction() {
    }

    // Constructor đầy đủ
    public Transaction(int transactionId, int accountId, double totalPrice, String paymentMethod, String status, boolean isPaid, Timestamp transactionDate) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.isPaid = isPaid;
        this.transactionDate = transactionDate;
    }

    // Getters và Setters
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountId=" + accountId +
                ", totalPrice=" + totalPrice +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status='" + status + '\'' +
                ", isPaid=" + isPaid +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
