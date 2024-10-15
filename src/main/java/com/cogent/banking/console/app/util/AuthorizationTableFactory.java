package com.cogent.banking.console.app.util;


import com.cogent.banking.console.app.service.AuthorizationTableDao;
import com.cogent.banking.console.app.service.transactionImpl.AuthorizationTableDaoImpl;

public class AuthorizationTableFactory {

    private static AuthorizationTableDao authorizationTableDao;

    private AuthorizationTableFactory() {

    }

    public static AuthorizationTableDao getAuthorizationTableDao() {
        if (authorizationTableDao == null)
            authorizationTableDao = new AuthorizationTableDaoImpl();
        return authorizationTableDao;
    }

}
