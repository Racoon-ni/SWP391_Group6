package entity;

import java.sql.Timestamp;

/**
 *
 * @author HUUTHANH
 */
public class WarningUser {

    private int accountId;
    private int accountIdReported;
    private String username;
    private int warningCount;
    private String note;
    private boolean status;
    private Timestamp warningTime;

    public WarningUser() {
    }

    public WarningUser(int accountId, String username, int warningCount, Timestamp warningTime) {
        this.username = username;
        this.warningCount = warningCount;
        this.warningTime = warningTime;
        this.accountId = accountId;

    }

    public WarningUser(int accountId, int accountIdReported, String username, String note, Timestamp warningTime) {
        this.accountId = accountId;
        this.accountIdReported = accountIdReported;
        this.username = username;
        this.note = note;
        this.warningTime = warningTime;
    }

    public WarningUser(int accountId, int accountIdReported, String username, int warningCount, String note, Timestamp warningTime, boolean status) {
        this.accountId = accountId;
        this.accountIdReported = accountIdReported;
        this.username = username;
        this.warningCount = warningCount;
        this.note = note;
        this.warningTime = warningTime;
        this.status = status;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountIdReported() {
        return accountIdReported;
    }

    public void setAccountIdReported(int accountIdReported) {
        this.accountIdReported = accountIdReported;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getWarningCount() {
        return warningCount;
    }

    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(Timestamp warningTime) {
        this.warningTime = warningTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
