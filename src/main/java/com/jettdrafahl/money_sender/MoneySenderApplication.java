package com.jettdrafahl.money_sender;

import com.jettdrafahl.money_sender.cli.MoneySenderCLI;
import com.jettdrafahl.money_sender.service.AccountService;
import com.jettdrafahl.money_sender.service.TransactionService;
import com.jettdrafahl.money_sender.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoneySenderApplication implements CommandLineRunner {

	private final AccountService accountService;
	private final TransactionService transactionService;
	private final UserService userService;

	public MoneySenderApplication(AccountService accountService, TransactionService transactionService, UserService userService) {
		this.accountService = accountService;
		this.transactionService = transactionService;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(MoneySenderApplication.class, args);
	}

	@Override
	public void run(String... args) {
		MoneySenderCLI cli = new MoneySenderCLI(accountService, transactionService, userService);
		cli.start(); // Start the CLI when the application runs
	}
}
