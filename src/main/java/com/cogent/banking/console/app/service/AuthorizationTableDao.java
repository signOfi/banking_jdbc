package com.cogent.banking.console.app.service;

import java.util.Date;

public interface AuthorizationTableDao {
    void createAuthorization(int employeeId, int approveOrDeny);
}
