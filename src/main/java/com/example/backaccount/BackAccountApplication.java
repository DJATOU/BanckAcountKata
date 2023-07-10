package com.example.backaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class BackAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackAccountApplication.class, args);

        // Create some sample accounts
        Account account1 = new Account();
        Account account2 = new Account();

        // Deposit and withdrawal operations on account1
        account1.deposit(1000);
        account1.withdraw(500);

        // Deposit and withdrawal operations on account2
        account2.deposit(2000);
        account2.withdraw(1000);

        // Print the statements for the accounts
        System.out.println("Account 1 Statement:");
        System.out.println(account1.getStatement());

        System.out.println("Account 2 Statement:");
        System.out.println(account2.getStatement());
    }
}
