package com.jettdrafahl.money_sender.cli;

import com.jettdrafahl.money_sender.exception.ResourceNotFoundException;
import com.jettdrafahl.money_sender.model.Account;
import com.jettdrafahl.money_sender.model.Transaction;
import com.jettdrafahl.money_sender.model.User;
import com.jettdrafahl.money_sender.service.AccountService;
import com.jettdrafahl.money_sender.service.TransactionService;
import com.jettdrafahl.money_sender.service.UserService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MoneySenderCLI {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);
    private User loggedInUser = null;

    public MoneySenderCLI(AccountService accountService, TransactionService transactionService, UserService userService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    public void start() {
        boolean running = true;
        while (running) {
            if (loggedInUser == null) {
                System.out.println("\n===== 💰 Money Sender CLI 💰 =====");
                System.out.println("1️⃣  Login");
                System.out.println("2️⃣  Exit");
                System.out.print("Choose an option: ");

                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    switch (choice) {
                        case 1 -> login();
                        case 2 -> {
                            System.out.println("👋 Exiting... Thank you for using Money Sender CLI!");
                            running = false;
                        }
                        default -> System.out.println("❌ Invalid choice, try again.");
                    }
                } catch (Exception e) {
                    System.out.println("❌ Invalid input! Please enter numbers only.");
                    scanner.nextLine();
                }
            } else {
                showMenuBasedOnRole(loggedInUser);
            }
        }
    }

    private void login() {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            User user = userService.login(username, password);
            if (user != null) {
                loggedInUser = user;
                System.out.println("✅ Login successful! Welcome, " + user.getUsername());
                showMenuBasedOnRole(user);
            } else {
                System.out.println("❌ Invalid credentials, please try again.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error during login. Please try again.");
        }
    }

    private void logout() {
        loggedInUser = null;
        System.out.println("👋 Successfully logged out.");
    }

    private void showMenuBasedOnRole(User user) {
        if (user.getRole().equals("admin")) {
            showAdminMenu();
        } else {
            showUserMenu();
        }
    }

    private void showAdminMenu() {
        System.out.println("\n===== 💰 Money Sender CLI (Admin) 💰 =====");
        System.out.println("1️⃣  Create Account");
        System.out.println("2️⃣  View My Accounts");
        System.out.println("3️⃣  Transfer Money");
        System.out.println("4️⃣  View Transactions Sent");
        System.out.println("5️⃣  View Transactions Received");
        System.out.println("6️⃣  Delete Account");
        System.out.println("7️⃣  Logout");
        System.out.print("Choose an option: ");

        handleAdminMenu();
    }

    private void showUserMenu() {
        System.out.println("\n===== 💰 Money Sender CLI (User) 💰 =====");
        System.out.println("1️⃣  Create Account");
        System.out.println("2️⃣  View My Accounts");
        System.out.println("3️⃣  Transfer Money");
        System.out.println("4️⃣  View Transactions Sent");
        System.out.println("5️⃣  View Transactions Received");
        System.out.println("6️⃣  Logout");
        System.out.print("Choose an option: ");

        handleUserMenu();
    }


    private void handleAdminMenu() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> viewMyAccounts();
                case 3 -> transferMoney();
                case 4 -> viewTransactionsBySenderMenu();
                case 5 -> viewTransactionsByReceiverMenu();
                case 6 -> deleteAccount();
                case 7 -> logout();
                default -> System.out.println("❌ Invalid choice, try again.");
            }
        } catch (Exception e) {
            System.out.println("❌ Invalid input! Please enter numbers only.");
            scanner.nextLine();
        }
    }


    private void handleUserMenu() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> viewMyAccounts();
                case 3 -> transferMoney();
                case 4 -> viewTransactionsBySenderMenu();
                case 5 -> viewTransactionsByReceiverMenu();
                case 6 -> logout();
                default -> System.out.println("❌ Invalid choice, try again.");
            }
        } catch (Exception e) {
            System.out.println("❌ Invalid input! Please enter numbers only.");
            scanner.nextLine();
        }
    }


    private void createAccount() {
        try {
            if (loggedInUser == null) {
                System.out.println("❌ Error: No user is logged in.");
                return;
            }

            System.out.print("Enter initial balance: ");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter account type (e.g., Checking, Savings): ");
            String accountType = scanner.nextLine().toLowerCase();

            Account account = new Account(null, loggedInUser.getId(), balance, accountType);
            Long accountId = accountService.createAccount(account);
            System.out.println("✅ Account created successfully! Account ID: " + accountId);
        } catch (Exception e) {
            System.out.println("❌ Error creating account. Please check your input.");
            scanner.nextLine();
        }
    }


    private void viewMyAccounts() {
        if (loggedInUser == null) {
            System.out.println("❌ Error: No user is logged in.");
            return;
        }

        List<Account> accounts = accountService.getAccountsByUserId(loggedInUser.getId());
        if (accounts.isEmpty()) {
            System.out.println("📭 No accounts found for this user.");
        } else {
            accounts.forEach(System.out::println);
        }
    }


    private void transferMoney() {
        try {
            System.out.print("Enter sender account ID: ");
            Long senderId = scanner.nextLong();
            System.out.print("Enter receiver account ID: ");
            Long receiverId = scanner.nextLong();
            System.out.print("Enter amount: ");
            double amount = scanner.nextDouble();

            if (amount <= 0) {
                System.out.println("❌ Error: Transfer amount must be greater than zero.");
                return;
            }

            Account senderAccount = accountService.getAccountById(senderId);
            if (senderAccount == null) {
                System.out.println("❌ Error: Sender account not found.");
                return;
            }
            if (senderAccount.getBalance() < amount) {
                System.out.println("❌ Error: Insufficient funds.");
                return;
            }

            Transaction transaction = new Transaction(null, senderId, receiverId, amount, new Timestamp(System.currentTimeMillis()));
            Long transactionId = transactionService.createTransaction(transaction);

            if (transactionId != null) {
                System.out.println("✅ Transfer successful! Transaction ID: " + transactionId);
            } else {
                System.out.println("❌ Error during transfer.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error during transfer. Please check your input.");
            scanner.nextLine();
        }
    }

    private void viewTransactionsBySenderMenu() {
        // Use logged-in user's accounts to get transactions sent
        List<Account> userAccounts = accountService.getAccountsByUserId(loggedInUser.getId());
        if (userAccounts.isEmpty()) {
            System.out.println("❌ No accounts found for this user.");
            return;
        }

        // Let's assume we allow the user to select an account
        System.out.println("Select an account to view sent transactions:");
        for (int i = 0; i < userAccounts.size(); i++) {
            System.out.println((i + 1) + ". " + userAccounts.get(i).getAccountType() + " - ID: " + userAccounts.get(i).getId());
        }
        System.out.print("Choose an option: ");
        int accountChoice = scanner.nextInt();
        scanner.nextLine();

        if (accountChoice < 1 || accountChoice > userAccounts.size()) {
            System.out.println("❌ Invalid choice, try again.");
            return;
        }

        Long senderAccountId = userAccounts.get(accountChoice - 1).getId();
        viewTransactionsBySender(senderAccountId);
    }


    private void viewTransactionsByReceiverMenu() {
        // Use logged-in user's accounts to get transactions received
        List<Account> userAccounts = accountService.getAccountsByUserId(loggedInUser.getId());
        if (userAccounts.isEmpty()) {
            System.out.println("❌ No accounts found for this user.");
            return;
        }

        // Let's assume we allow the user to select an account
        System.out.println("Select an account to view received transactions:");
        for (int i = 0; i < userAccounts.size(); i++) {
            System.out.println((i + 1) + ". " + userAccounts.get(i).getAccountType() + " - ID: " + userAccounts.get(i).getId());
        }
        System.out.print("Choose an option: ");
        int accountChoice = scanner.nextInt();
        scanner.nextLine();

        if (accountChoice < 1 || accountChoice > userAccounts.size()) {
            System.out.println("❌ Invalid choice, try again.");
            return;
        }

        Long receiverAccountId = userAccounts.get(accountChoice - 1).getId();
        viewTransactionsByReceiver(receiverAccountId);
    }


    private void viewTransactionsBySender(Long senderAccountId) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsBySender(senderAccountId);
            if (transactions.isEmpty()) {
                System.out.println("📭 No transactions sent from this account.");
                return;
            }

            transactions.forEach(transaction -> {
                System.out.println("Transaction ID: " + transaction.getId());
                System.out.println("Sender Account ID: " + transaction.getSenderAccountId());
                System.out.println("Receiver Account ID: " + transaction.getReceiverAccountId());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println("Timestamp: " + transaction.getTimestamp());
                System.out.println("---------------");
            });
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewTransactionsByReceiver(Long receiverAccountId) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByReceiver(receiverAccountId);
            if (transactions.isEmpty()) {
                System.out.println("📭 No transactions received by this account.");
                return;
            }

            transactions.forEach(transaction -> {
                System.out.println("Transaction ID: " + transaction.getId());
                System.out.println("Sender Account ID: " + transaction.getSenderAccountId());
                System.out.println("Receiver Account ID: " + transaction.getReceiverAccountId());
                System.out.println("Amount: " + transaction.getAmount());
                System.out.println("Timestamp: " + transaction.getTimestamp());
                System.out.println("---------------");
            });
        } catch (ResourceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    private void deleteAccount() {
        try {
            System.out.print("Enter account ID to delete: ");
            Long accountId = scanner.nextLong();
            boolean deleted = accountService.deleteAccount(accountId);
            if (deleted) {
                System.out.println("✅ Account deleted successfully!");
            } else {
                System.out.println("❌ Account not found.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error deleting account.");
            scanner.nextLine();
        }
    }
}
