package entity;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class Transactions {

    private int transactionId;
    private int accountId;
    private double totalPrice;
    private String paymentMethod;
    private String transactionDate;
    private String status; // "Pending", "Completed", "Cancelled"
    private boolean isPaid;

    public Transactions() {
    }

    public Transactions(int transactionId, int accountId, double totalPrice, String paymentMethod, String transactionDate, String status, boolean isPaid) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.transactionDate = transactionDate;
        this.status = status;
        this.isPaid = isPaid;
    }

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

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    
}
