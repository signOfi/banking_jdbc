package com.cogent.banking.console.app.service;

public interface AccountDao {
    void deposit(double amount, int accountId, int userId);
    boolean withdraw(double amount, int accountId, int userId);
    void viewBalance(int userId, int accountId);
    void transfer(int userId, int accountId, int transferToId, double amount);
    void createAccount(int userId, double balance);
    void printAllAccounts();
    boolean printAllActiveAccounts();
}
