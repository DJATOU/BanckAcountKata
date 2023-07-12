package com.example.backaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BackAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackAccountApplication.class, args);

        // Create some sample accounts
        Account account1 = new Account();
        Account account2 = new Account();

        // Deposit and withdrawal operations on account1
        account1.deposit(1000);
        account1.withdraw(500);
        account1.withdraw(300);

        // Deposit and withdrawal operations on account2
        account2.deposit(2000);
        account2.withdraw(1000);

        // Get transaction history
        List<Transaction> transactionHistory1 = account1.getTransactionHistory();
        List<Transaction> transactionHistory2 = account2.getTransactionHistory();

        System.out.println("Transaction History Account 1:");
        System.out.println("Date\t\tAmount\t\tBalance\t\tOperation");
        for (Transaction transaction : transactionHistory1) {
            System.out.println(transaction.toString());
        }

        System.out.println("\nTransaction History Account 2:");
        System.out.println("Date\t\tAmount\t\tBalance\t\tOperation");
        for (Transaction transaction : transactionHistory2) {
            System.out.println(transaction.toString());
        }

        System.out.println("\nTotal Balance Account 1: " + account1.getBalance());
        System.out.println("Total Balance Account 2: " + account2.getBalance());
    }

}
