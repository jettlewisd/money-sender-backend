package com.jettdrafahl.money_sender.cli;

import com.jettdrafahl.money_sender.model.Account;
import com.jettdrafahl.money_sender.model.Transaction;
import com.jettdrafahl.money_sender.service.AccountService;
import com.jettdrafahl.money_sender.service.TransactionService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class MoneySenderCLI {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final Scanner scanner = new Scanner(System.in);

    public MoneySenderCLI(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n===== 💰 Money Sender CLI 💰 =====");
            System.out.println("1️⃣  Create Account");
            System.out.println("2️⃣  View Account Details");
            System.out.println("3️⃣  View All Accounts");
            System.out.println("4️⃣  Transfer Money");
            System.out.println("5️⃣  View Transactions by Sender");
            System.out.println("6️⃣  View Transactions by Receiver");
            System.out.println("7️⃣  Delete Account");
            System.out.println("8️⃣  Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> createAccount();
                    case 2 -> viewAccountById();
                    case 3 -> viewAllAccounts();
                    case 4 -> transferMoney();
                    case 5 -> viewTransactionsBySender();
                    case 6 -> viewTransactionsByReceiver();
                    case 7 -> deleteAccount();
                    case 8 -> {
                        System.out.println("👋 Exiting... Thank you for using Money Sender CLI!");
                        running = false;
                    }
                    default -> System.out.println("❌ Invalid choice, try again.");
                }
            } catch (Exception e) {
                System.out.println("❌ Invalid input! Please enter numbers only.");
                scanner.nextLine(); // Clear scanner buffer
            }
        }
    }

    private void createAccount() {
        try {
            System.out.print("Enter user ID: ");
            Long userId = scanner.nextLong();
            System.out.print("Enter initial balance: ");
            double balance = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter account type (e.g., Checking, Savings): ");
            String accountType = scanner.nextLine();

            Account account = new Account(null, userId, balance, accountType);
            Long accountId = accountService.createAccount(account);
            System.out.println("✅ Account created successfully! Account ID: " + accountId);
        } catch (Exception e) {
            System.out.println("❌ Error creating account. Please check your input.");
            scanner.nextLine();
        }
    }

    private void viewAccountById() {
        try {
            System.out.print("Enter account ID: ");
            Long accountId = scanner.nextLong();
            Account account = accountService.getAccountById(accountId);
            if (account != null) {
                System.out.println("📄 Account Details: " + account);
            } else {
                System.out.println("❌ Account not found.");
            }
        } catch (Exception e) {
            System.out.println("❌ Invalid input. Please enter a valid account ID.");
            scanner.nextLine();
        }
    }

    private void viewAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("📭 No accounts found.");
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
                System.out.println("❌ Transfer failed. Please check account details.");
            }
        } catch (Exception e) {
            System.out.println("❌ Invalid input! Please enter valid numbers.");
            scanner.nextLine();
        }
    }

    private void viewTransactionsBySender() {
        try {
            System.out.print("Enter sender account ID: ");
            Long senderId = scanner.nextLong();
            List<Transaction> transactions = transactionService.getTransactionsBySender(senderId);

            if (transactions.isEmpty()) {
                System.out.println("📭 No transactions found for this sender.");
            } else {
                System.out.println("\n📜 Transactions for Sender ID: " + senderId);
                System.out.println("-------------------------------------------------");
                System.out.printf("%-10s %-15s %-15s %-10s %-20s%n", "ID", "Sender", "Receiver", "Amount", "Timestamp");
                System.out.println("-------------------------------------------------");
                transactions.forEach(transaction ->
                        System.out.printf("%-10d %-15d %-15d %-10.2f %-20s%n",
                                transaction.getId(), transaction.getSenderAccountId(), transaction.getReceiverAccountId(),
                                transaction.getAmount(), transaction.getTimestamp()));
                System.out.println("-------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("❌ Invalid input. Please enter a valid sender ID.");
            scanner.nextLine();
        }
    }

    private void viewTransactionsByReceiver() {
        try {
            System.out.print("Enter receiver account ID: ");
            Long receiverId = scanner.nextLong();
            List<Transaction> transactions = transactionService.getTransactionsByReceiver(receiverId);

            if (transactions.isEmpty()) {
                System.out.println("📭 No transactions found for this receiver.");
            } else {
                System.out.println("\n📜 Transactions for Receiver ID: " + receiverId);
                System.out.println("-------------------------------------------------");
                System.out.printf("%-10s %-15s %-15s %-10s %-20s%n", "ID", "Sender", "Receiver", "Amount", "Timestamp");
                System.out.println("-------------------------------------------------");
                transactions.forEach(transaction ->
                        System.out.printf("%-10d %-15d %-15d %-10.2f %-20s%n",
                                transaction.getId(), transaction.getSenderAccountId(), transaction.getReceiverAccountId(),
                                transaction.getAmount(), transaction.getTimestamp()));
                System.out.println("-------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("❌ Invalid input. Please enter a valid receiver ID.");
            scanner.nextLine();
        }
    }

    private void deleteAccount() {
        try {
            System.out.print("Enter account ID to delete: ");
            Long accountId = scanner.nextLong();

            Account account = accountService.getAccountById(accountId);
            if (account == null) {
                System.out.println("❌ Error: Account not found.");
                return;
            }

            boolean deleted = accountService.deleteAccount(accountId);
            if (deleted) {
                System.out.println("✅ Account deleted successfully.");
            } else {
                System.out.println("❌ Deletion failed. Ensure the account has no pending transactions.");
            }
        } catch (Exception e) {
            System.out.println("❌ Invalid input. Please enter a valid account ID.");
            scanner.nextLine();
        }
    }
}
