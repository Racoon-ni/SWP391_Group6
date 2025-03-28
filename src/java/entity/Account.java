/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author SE18-CE180628-Nguyen Pham Doan Trang
 */
public class Account {

    private int accountId;
    private String username;
    private String password;
    private String email;
    private int status; // 1: active, 0: inactive, 2 block comment
    private boolean role; // true: admin, false: user
    private String fullName;
    private String phone;
    private String address;
    private String image;

    public Account() {
    }

    public Account(int accountId, String username, String password, String email, boolean role, String fullName,
            String phone, String address, String image) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

    public Account(int accountId, String username, String email, boolean role, String fullName, String phone,
            String address, String image) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.image = image;
    }

    public Account(int accountId, String username, String password, String email, boolean role, int status) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public Account(String username, String password, String email) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

     public Account(int accountId, String username, String password, String email, boolean role, String fullName,
            String phone, String address, String image,int status) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.image = image;
        this.status = status;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String full_name) {
        this.fullName = full_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAccount_id() {
        return this.accountId; // Trả về ID tài khoản
        // throw new UnsupportedOperationException("Not supported yet."); // Generated
        // from
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
