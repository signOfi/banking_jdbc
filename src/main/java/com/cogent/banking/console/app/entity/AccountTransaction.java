package com.cogent.banking.console.app.entity;

import java.util.Date;

public class AccountTransaction {

    private int transactionId;
    private int withdrawOrDeposit;
    private double beforeBalance;
    private double afterBalance;
    private Date timestamp;
    private int accountId;

    public AccountTransaction(int transactionId, double beforeBalance, int withdrawOrDeposit, double afterBalance, Date timestamp, int accountId) {
        this.transactionId = transactionId;
        this.beforeBalance = beforeBalance;
        this.withdrawOrDeposit = withdrawOrDeposit;
        this.afterBalance = afterBalance;
        this.timestamp = timestamp;
        this.accountId = accountId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getWithdrawOrDeposit() {
        return withdrawOrDeposit;
    }

    public void setWithdrawOrDeposit(int withdrawOrDeposit) {
        this.withdrawOrDeposit = withdrawOrDeposit;
    }

    public double getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(double beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    public double getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(double afterBalance) {
        this.afterBalance = afterBalance;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
