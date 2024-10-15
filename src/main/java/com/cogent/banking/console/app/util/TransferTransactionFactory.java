package com.cogent.banking.console.app.util;

import com.cogent.banking.console.app.service.TransferTransactionDao;
import com.cogent.banking.console.app.service.transactionImpl.TransferTransactionDaoImpl;

public class TransferTransactionFactory {

    private static TransferTransactionDao transferTransaction;

    private TransferTransactionFactory() {

    }

    public static TransferTransactionDao getTransferTransaction() {
        if (transferTransaction == null)
            transferTransaction = new TransferTransactionDaoImpl();
        return transferTransaction;
    }

}
