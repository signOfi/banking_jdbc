package com.cogent.banking.console.app.service;

public interface EmployeeDao {

    void printAllInactiveAccounts();

    /*
    *  View All accounts that have exactly isActive == 0
    *  Employee chooses an account to approve/deny
    */
    void approveDeny(int accountId, int approveOrDeny, int employeeId);

    /*
    *  Get a customer ID
    *  Get all customer account
    *  TODO: Get the customer transactions
    */
    void viewAllCustomerBankAccount(int customerId);

}
