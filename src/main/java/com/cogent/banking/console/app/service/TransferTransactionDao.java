package com.cogent.banking.console.app.service;

public interface TransferTransactionDao {

    void createTransaction(int fromId, int toId, double beforeBalance, double afterBalance);

}
