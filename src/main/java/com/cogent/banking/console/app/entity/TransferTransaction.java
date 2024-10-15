package com.cogent.banking.console.app.entity;


import java.util.Date;

public class TransferTransaction {

    private int transferId;
    private int from_accountId;
    private int toAccountId;
    private Date timestamp;
    private double beforeBalance;
    private double afterBalance;

    public TransferTransaction(int transferId, int from_accountId, int toAccountId, Date timestamp, double beforeBalance, double afterBalance) {
        this.transferId = transferId;
        this.from_accountId = from_accountId;
        this.toAccountId = toAccountId;
        this.timestamp = timestamp;
        this.beforeBalance = beforeBalance;
        this.afterBalance = afterBalance;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getFrom_accountId() {
        return from_accountId;
    }

    public void setFrom_accountId(int from_accountId) {
        this.from_accountId = from_accountId;
    }

    public int getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(int toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getAfterBalance() {
        return afterBalance;
    }

    public void setAfterBalance(double afterBalance) {
        this.afterBalance = afterBalance;
    }

    public double getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(double beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    @Override
    public String toString() {
        return "TransferTransaction{" +
                "transferId=" + transferId +
                ", from_accountId=" + from_accountId +
                ", toAccountId=" + toAccountId +
                ", timestamp=" + timestamp +
                ", beforeBalance=" + beforeBalance +
                ", afterBalance=" + afterBalance +
                '}';
    }
}
