package com.cogent.banking.console.app.service.serviceImpl;

import com.cogent.banking.console.app.entity.Account;
import com.cogent.banking.console.app.entity.User;
import com.cogent.banking.console.app.service.AccountDao;
import com.cogent.banking.console.app.service.AccountTransactionDao;
import com.cogent.banking.console.app.service.TransferTransactionDao;
import com.cogent.banking.console.app.util.AccountTransactionFactory;
import com.cogent.banking.console.app.util.ConnectionFactory;
import com.cogent.banking.console.app.util.TransferTransactionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AccountDaoImpl implements AccountDao {

    private final User user;
    private final Connection connection;
    private final Map<Integer, Account> map;

    /* Transactional Dependencies */
    private final AccountTransactionDao accountTranscationDao;
    private final TransferTransactionDao transferTransactionDao;

    public AccountDaoImpl(User user) {
        this.user = user;
        this.connection = ConnectionFactory.getConnection();
        this.map = new HashMap<>();

        /* Transactional Dependencies */
        this.accountTranscationDao = AccountTransactionFactory.getAccountTransaction();
        this.transferTransactionDao = TransferTransactionFactory.getTransferTransaction();
    }

    @Override
    public void createAccount(int userId, double balance) {
        String query = "insert into account (userId, balance, isActive) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setDouble(2, balance);
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();                      // commit to DB
            System.out.println("You have successfully applied for an account, it's pending approval");
        } catch (SQLException e) {
            System.out.println("Error code for adding " + e);
        }
    }

    private void populateHashMap() {
        String query = "SELECT * FROM account where userId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            /* Query all account and add to hashmap iff  */
            while (resultSet.next()) {
                int sqlIsValid = resultSet.getInt("isActive");
                boolean isValid = sqlIsValid == 1;

                int key = resultSet.getInt("accountId");

                if (!map.containsKey(key)) {
                    map.put(key, new Account(
                            resultSet.getInt("accountId"),
                            resultSet.getDouble("balance"),
                            isValid,
                            resultSet.getInt("userId")
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error code for getting " + e);
        }
    }

    @Override
    public void printAllAccounts() {

        populateHashMap();

        if (map.isEmpty()) {
            System.out.println("No accounts linked to this user.");
            return;
        }

        for (Integer key: map.keySet())
            System.out.println(map.get(key));
    }

    @Override
    public boolean printAllActiveAccounts() {
        boolean accountDoesExist = false;
        populateHashMap();

        if (map.isEmpty())
            return accountDoesExist;

        for (Integer key: map.keySet()) {
            if (map.get(key).isActive()) {
                accountDoesExist = true;
                System.out.println(map.get(key));
            }
        }

        return accountDoesExist;
    }

    /* Private method that only works for deposit/withdraw */
    private void updateBalance(double beforeBalance, double afterBalance, int accountId, int withdraw_deposit) {
        String query = "UPDATE ACCOUNT set balance = ? where accountId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setDouble(1, afterBalance);
            preparedStatement.setInt(2, accountId);
            preparedStatement.executeUpdate();

            /* Log the transaction to the DB */
            accountTranscationDao.createTransaction(withdraw_deposit, beforeBalance, afterBalance, accountId);


        } catch (SQLException e) {
            System.out.println("Error code for updating Balance " + e);
        }
    }

    /* Private method that checks if a accountId exists, and checks if account is active and account belongs to user */
    private Account validateAccountIdAndBelongsToUser(int accountId, int userId) {
        populateHashMap();

        /* First validate if the account id exists */
        if (!map.containsKey(accountId)) {
            System.out.println("The account id does not exist try again");
            return null;
        }

        /* Check if the account belongs to USER and is active */
        Account account = map.get(accountId);
        if (account.getUserId() != userId) {
            System.out.println("The account id you have entered does not belong to YOU!");
            return null;
        } else if (!account.isActive()) {
            System.out.println("Your account has not been approved by the bank yet!");
            return null;
        }

        return account;
    }

    @Override
    public void deposit(double amount, int accountId, int userId) {

        Account account = validateAccountIdAndBelongsToUser(accountId, userId);
        if (account == null)
            return;

        /* Persist the changes onto the database */
        double beforeBalance = account.getBalance();
        double newBalance = account.getBalance() + amount;
        updateBalance(beforeBalance, newBalance, accountId, 1);

        /* Update the balance in the hashmap, and Print the new balance */
        account.setBalance(account.getBalance() + amount);
        System.out.println("Your new account balance after the deposit is " + " $" + account.getBalance());
    }

    @Override
    public boolean withdraw(double amount, int accountId, int userId) {
        Account account = validateAccountIdAndBelongsToUser(accountId, userId);
        if (account == null)
            return false;

        double currentBalance = account.getBalance();

        /* Handle not enough funds */
        if (amount > currentBalance) {
            System.out.println("This will exceed your currentBalance, cannot perform a withdraw");
            return false;
        }

        /* Handle enough funds */
        else {
            /* Persist the changes */
            double oldBalance = account.getBalance();
            double newBalance = account.getBalance() - amount;
            updateBalance(oldBalance, newBalance, accountId, 0);

            /* Changes the val in the hashmap and prints */
            account.setBalance(newBalance);
            System.out.println("Withdraw has been successful your new balance is " + account.getBalance() + "$ ");
            return true;
        }
    }

    private void depositTransferForeignKey(Account account, double amount, int accountId) {
            if (account != null) {
                /* Persist the changes onto the database */
                double beforeBalance = account.getBalance();
                double newBalance = account.getBalance() + amount;
                updateBalance(beforeBalance, newBalance, accountId, 1);

                /* Update the balance in the hashmap, and Print the new balance */
                account.setBalance(account.getBalance() + amount);
                System.out.println("Your new account balance after the deposit is " + " $" + account.getBalance());
                return;
            }
        System.out.println("Not able to find the accountID, transfer not successful");
        return;
    }

    @Override
    public void viewBalance(int userId, int accountId) {
        populateHashMap();
        if (map.containsKey(accountId)) {
            Account account = map.get(accountId);
            if (account.getUserId() == userId) {
                System.out.println("Account Balance $" + account.getBalance());
            } else {
                System.out.println("The accountId does not match the userId");
            }
        } else {
            System.out.println("Account Id does not exist");
        }
    }

    @Override
    public void transfer(int userId, int accountId, int fkAccountId, double amount) {
        Account accountPK = validateAccountIdAndBelongsToUser(accountId, userId);
        Account accountFK = validateForiegnAccount(fkAccountId);

        /* Check the PK account and FK account for valid */
        if (accountPK == null)
            return;
        if (accountFK == null)
            return;

        double oldBalancePK = accountPK.getBalance();

        /* Withdraw from PK account */
        if (withdraw(amount, accountId, userId)) {

            double newBalancePK = accountPK.getBalance();

            /* Enough funds, can deposit into the FK account */
            depositTransferForeignKey(accountFK, amount, fkAccountId);

            /* Print message */
            System.out.println("Amount has been successfully transferred");

            /* Records transaction from sender side */
            transferTransactionDao.createTransaction(accountPK.getUserId(),
                    accountFK.getUserId(), oldBalancePK, newBalancePK);
        }

        else {
            System.out.println("Amount has NOT been transferred");
        }
    }

    private Account validateForiegnAccount(int accountId) {

        /* TODO: Validate the account is in the table, and is active */
        String query = "SELECT * FROM account WHERE accountId = ? AND isActive = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2,1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int sqlIsValid = resultSet.getInt("isActive");
                boolean isValid = sqlIsValid == 1;
                return new Account(
                        resultSet.getInt("accountId"),
                        resultSet.getDouble("balance"),
                        isValid,
                        resultSet.getInt("userId")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error code for getting " + e);
        }
        System.out.println("The account you want to transfer is not an active account or D.N.E");
        return null;
    }

}
