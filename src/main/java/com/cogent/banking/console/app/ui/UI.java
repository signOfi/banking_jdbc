package com.cogent.banking.console.app.ui;

import com.cogent.banking.console.app.entity.User;
import com.cogent.banking.console.app.service.AccountDao;
import com.cogent.banking.console.app.service.EmployeeDao;
import com.cogent.banking.console.app.service.UserDao;
import com.cogent.banking.console.app.service.serviceImpl.AccountDaoImpl;
import com.cogent.banking.console.app.service.serviceImpl.EmployeeDaoImpl;
import com.cogent.banking.console.app.service.serviceImpl.UserDaoImpl;
import com.cogent.banking.console.app.util.InputValidation;

import java.util.Scanner;
import java.util.*;

public class UI {

    private final Scanner scanner;
    Set<Integer> options1To3;
    Set<Integer> options1To7;
    private final UserDao userDao;

    public UI(Scanner scanner) {
        this.scanner = scanner;
        populateHashSets();
        this.userDao = new UserDaoImpl();
    }

    public void populateHashSets() {
        options1To3 = new HashSet<>();
        options1To3.add(1);
        options1To3.add(2);
        options1To3.add(3);

        options1To7 = new HashSet<>();
        options1To7.add(1);
        options1To7.add(2);
        options1To7.add(3);
        options1To7.add(4);
        options1To7.add(5);
        options1To7.add(6);
        options1To7.add(7);
    }

    public void start() {
        showLoginRegisterScreen();
    }

    private void printLoginDetails() {
        System.out.println("******* Welcome to Dude Bank *******");
        System.out.println("Would you like to login or register");
        System.out.println("Option 1: Login");
        System.out.println("Option 2: Register");
        System.out.println("Option 3: Terminate");
        System.out.print("Enter your choice here: ");
    }

    private void printUserOptions() {
        System.out.println("******** Customer Options Screen ********");
        System.out.println("Option 1: Apply for a account");
        System.out.println("Option 2: Show all accounts");
        System.out.println("Option 3: Withdraw money into an account");
        System.out.println("Option 4: Deposit money to an account");
        System.out.println("Option 5: View Balance of an account");
        System.out.println("Option 6: Transfer money to another account");
        System.out.println("Option 7: Logout");
        System.out.print("Enter your choice here: ");
    }

    private void printEmployeeOptions() {
        System.out.println("******** Employee Option Screen ********");
        System.out.println("Option 1: Approve/Deny an account");
        System.out.println("Option 2: View a customer's bank account");
        System.out.println("Option 3: Logout");
        System.out.print("Enter your choice here: ");
    }

    public void showLoginRegisterScreen() {
        String userInput;
        do {
            printLoginDetails();
            userInput = scanner.nextLine().trim();
            System.out.print("\n");
            if (InputValidation.validateUserOption(userInput, options1To3)) {

                /* New user is created after each succesful login */
                User userToken;

                switch (userInput) {

                    case "1":

                        /* Login Loop */
                        do {
                            userToken = login();
                            if (userToken != null) {
                                System.out.println("Login was successful.\n");
                                break;
                            }
                            System.out.println("Login was not successful. Please try again\n");
                        } while (true);

                        /* Login has been authenticated */

                        boolean isEmployee = userToken.isEmployee();
                        AccountDao accountDao = new AccountDaoImpl(userToken);
                        EmployeeDao employeeDao = new EmployeeDaoImpl();
                        userInput = "";

                        if (isEmployee) {
                            do {
                                printEmployeeOptions();
                                userInput = scanner.nextLine();
                                if (InputValidation.validateUserOption(userInput, options1To3)) {
                                    switch (userInput) {
                                        case "1":
                                            employeeOption1(employeeDao, userToken);
                                            break;
                                        case "2":
                                            employeeOption2(employeeDao);
                                            break;
                                    }
                                }
                                System.out.print("\n");
                            } while (!userInput.equals("3"));
                        } else {
                            do {
                                printUserOptions();
                                userInput = scanner.nextLine().trim();
                                if (InputValidation.validateUserOption(userInput, options1To7)) {
                                    switch (userInput) {
                                        case "1":
                                            userOption1(userToken, accountDao);
                                            break;
                                        case "2":
                                            userOption2(accountDao);
                                            break;
                                        case "3":
                                            userOption3(userToken, accountDao);
                                            break;
                                        case "4":
                                            userOption4(userToken, accountDao);
                                            break;
                                        case "5":
                                            userOption5(userToken, accountDao);
                                            break;
                                        case "6":
                                            userOption6(userToken, accountDao);
                                            break;
                                    }
                                }
                                System.out.println();
                            } while (!userInput.equals("7"));
                        }

                        break;
                    case "2":
                        /* Register Loop */
                        do {
                            userToken = register();
                            if (userToken != null) {
                                System.out.println("Registration has been successful!");
                                break;
                            }
                            System.out.println("Registration has failed!");
                        } while (true);
                        break;
                    case "3":
                        System.exit(1);
                        break;
                }

            }
            System.out.print("\n");
        } while (true);
    }

    private User login() {
        System.out.println("******** LOGIN SCREEN ********");
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        username = username.trim();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        password = password.trim();

        return userDao.login(username, password);
    }

    private User register() {
        System.out.println("******** Register SCREEN ********");
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine().trim();

        String type = "";
        do {
            System.out.println("What type of USER are you customer or a employee ");
            System.out.print("Enter c or e: ");
            type = scanner.nextLine().trim();
            if (InputValidation.validateEmployeeOrUser(type))
                break;
            System.out.print("Invalid input try again");
        } while (true);

        boolean isEmployee = type.equals("e");
        User user = new User(firstName, lastName, email, username, password, isEmployee);
        return userDao.registerUser(user);
    }

    private void userOption1(User userToken, AccountDao accountDao) {
        String balance;
        do {
            System.out.print("Enter your initial account balance: ");
            balance = scanner.nextLine().trim();
            if (InputValidation.validateAmount(balance))
                break;
            System.out.println("Invalid account balance try again.");
        } while (true);
        accountDao.createAccount(userToken.getId(), Double.parseDouble(balance));
    }

    private void userOption2(AccountDao accountDao) {
        accountDao.printAllAccounts();
    }

    private void userOption4(User userToken, AccountDao accountDao) {
        if (!accountDao.printAllActiveAccounts()) {
            System.out.println("There is no active accounts associated with this user. Deposit cannot be performed");
            return;
        }

        System.out.print("Select the account number you want to deposit to: ");
        String accountId = scanner.nextLine().trim();

        System.out.print("Enter the amount you would like to deposit: ");
        String balance = scanner.nextLine().trim();

        if (InputValidation.isAnInteger(accountId) && InputValidation.validateAmount(balance)) {
            accountDao.deposit(Double.parseDouble(balance), Integer.parseInt(accountId), userToken.getId());
        } else {
            System.out.println("Your input formatting is invalid try again");
        }
    }


    private void userOption3(User userToken, AccountDao accountDao) {
        if (!accountDao.printAllActiveAccounts()) {
            System.out.println("There is no active accounts associated with this user. Withdraw cannot be performed");
            return;
        }

        System.out.print("Select the account number you want to withdraw the balance of: ");
        String accountId = scanner.nextLine().trim();

        System.out.print("Enter the amount you would like to withdraw: ");
        String withdrawAmount = scanner.nextLine().trim();

        if (InputValidation.isAnInteger(accountId) && InputValidation.validateAmount(withdrawAmount)) {
            accountDao.withdraw(Double.parseDouble(withdrawAmount), Integer.parseInt(accountId), userToken.getId());
        } else {
            System.out.println("Your input formatting is invalid try again");
        }

    }
    private void userOption5(User userToken, AccountDao accountDao) {
        if (!accountDao.printAllActiveAccounts()) {
            System.out.println("There is no active accounts associated with this user. Cannot view balance");
            return;
        }

        System.out.print("Select the account number you want to view the balance of: ");
        String accountId = scanner.nextLine().trim();

        if ((InputValidation.isAnInteger(accountId))) {
            accountDao.viewBalance(userToken.getId(), Integer.parseInt(accountId));
        } else {
            System.out.println("Your input formatting is invalid try again");
        }

    }
    private void userOption6(User userToken, AccountDao accountDao) {

        if (!accountDao.printAllActiveAccounts()) {
            System.out.println("There is no active accounts associated with this user. Cannot transfer money");
            return;
        }

        System.out.print("Select the account number you want to transfer the money from: ");
        String accountId = scanner.nextLine().trim();

        System.out.print("Enter the amount you would like to transfer: ");
        String withdrawAmount = scanner.nextLine().trim();

        System.out.print("Enter the account number you want to transfer money too: ");
        String accountIdFK = scanner.nextLine().trim();

        if (InputValidation.isAnInteger(accountId)
                && InputValidation.validateAmount(withdrawAmount)
                && InputValidation.isAnInteger(accountIdFK)) {

            accountDao.transfer(userToken.getId(),
                    Integer.parseInt(accountId),
                    Integer.parseInt(accountIdFK),
                    Double.parseDouble(withdrawAmount)
                    );

        } else {
            System.out.println("Your input formatting is invalid try again");
        }

    }

    private void employeeOption1(EmployeeDao employeeDao, User userToken) {

        // print all inactive accounts
        System.out.println("Below is all inactive accounts pending approval");
        employeeDao.printAllInactiveAccounts();

        // employee chooses an account id to approve/deny
        System.out.print("\nEnter an account id: ");
        String accountId = scanner.nextLine().trim();

        // employee chooses to approve/deny
        String approveOrDeny;
        do {
            System.out.println("Would you like to approve/deny");
            System.out.print("Enter approve or deny: ");
            approveOrDeny = scanner.nextLine().trim();
            approveOrDeny = approveOrDeny.toLowerCase();
            if (approveOrDeny.equals("deny") || approveOrDeny.equals("approve")) {
                break;
            }
            System.out.println("Invalid input try again!");
        } while(true);

        // If deny don't do anything
        if (approveOrDeny.equals("deny")) {
            employeeDao.approveDeny(Integer.parseInt(accountId), 0, userToken.getId());
            System.out.println("You have entered denied, nothing will happen..");
            return;
        }

        if (InputValidation.isAnInteger(accountId)) {
            employeeDao.approveDeny(Integer.parseInt(accountId), 1, userToken.getId());
        } else {
            System.out.println("Your input formatting is invalid try again");
        }

    }

    private void employeeOption2(EmployeeDao employeeDao) {
        /* Get customer ID by scanner */
        System.out.print("Enter the customer id, you wish to view all information: ");
        String customerId = scanner.nextLine().trim();

        if (InputValidation.isAnInteger(customerId)) {
            employeeDao.viewAllCustomerBankAccount(Integer.parseInt(customerId));
        } else {
            System.out.println("Your input formatting is invalid try again");
        }
    }

}
