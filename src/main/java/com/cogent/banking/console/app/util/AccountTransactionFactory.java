package com.cogent.banking.console.app.util;

import com.cogent.banking.console.app.service.AccountTransactionDao;
import com.cogent.banking.console.app.service.transactionImpl.AccountTransactionDaoImpl;

public class AccountTransactionFactory {

    private static AccountTransactionDao accountTransactionDao;

    private AccountTransactionFactory() {

    }
    public static AccountTransactionDao getAccountTransaction() {
        if (accountTransactionDao == null)
            accountTransactionDao = new AccountTransactionDaoImpl();
        return accountTransactionDao;
    }

}
