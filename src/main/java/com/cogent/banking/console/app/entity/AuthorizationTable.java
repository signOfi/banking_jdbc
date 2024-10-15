package com.cogent.banking.console.app.entity;

import java.util.Date;

public class AuthorizationTable {

    private int authorizationId;
    private int employeeId;
    private Date timestamp;
    private int approve_deny;

    public AuthorizationTable(int authorizationId, int employeeId, Date timestamp, int approve_deny) {
        this.authorizationId = authorizationId;
        this.employeeId = employeeId;
        this.timestamp = timestamp;
        this.approve_deny = approve_deny;
    }

    public int getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(int authorizationId) {
        this.authorizationId = authorizationId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getApprove_deny() {
        return approve_deny;
    }

    public void setApprove_deny(int approve_deny) {
        this.approve_deny = approve_deny;
    }

    @Override
    public String toString() {
        return "AuthorizationTable{" +
                "authorizationId=" + authorizationId +
                ", employeeId=" + employeeId +
                ", timestamp=" + timestamp +
                ", approve_deny=" + approve_deny +
                '}';
    }
}