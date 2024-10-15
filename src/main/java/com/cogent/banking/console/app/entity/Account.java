package com.cogent.banking.console.app.entity;


public class Account {

    private int accountNumber;
    private Double balance;
    private boolean isActive;
    private int userId;

    public Account () {

    }

    public Account(Double balance, int userId) {
        this.balance = balance;
        this.isActive = false;
        this.userId = userId;
    }

    public Account(int accountNumber, Double balance, int userId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.userId = userId;
        this.isActive = false;
    }

    public Account(int accountNumber, Double balance, boolean isActive, int userId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.isActive = isActive;
        this.userId = userId;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", balance=" + balance +
                ", isActive=" + isActive +
                ", userId=" + userId +
                '}';
    }
}
