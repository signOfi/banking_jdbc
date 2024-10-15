package com.cogent.banking.console.app.service;

public interface AccountTransactionDao {
    public void createTransaction(int withdrawOrDeposit, double beforeBalance, double afterBalance, int accountId);
}
