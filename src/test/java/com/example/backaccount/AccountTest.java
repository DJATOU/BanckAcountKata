package com.example.backaccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void deposit_ValidAmount_BalanceIncreased() {
        double initialBalance = account.getBalance();
        double amount = 100.0;

        account.deposit(amount);

        assertEquals(initialBalance + amount, account.getBalance());
    }

    @Test
    void deposit_NegativeAmount_ThrowsIllegalArgumentException() {
        double amount = -100.0;

        assertThrows(IllegalArgumentException.class, () -> account.deposit(amount));
    }

    @Test
    void withdraw_ValidAmount_BalanceDecreased() {
        double initialBalance = 500.0;
        account.deposit(initialBalance);

        double withdrawalAmount = 200.0;
        account.withdraw(withdrawalAmount);

        assertEquals(initialBalance - withdrawalAmount, account.getBalance());
    }

    @Test
    void withdraw_NegativeAmount_ThrowsIllegalArgumentException() {
        double amount = -100.0;

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(amount));
    }

    @Test
    void withdraw_InsufficientBalance_ThrowsInsufficientBalanceException() {
        double initialBalance = 100.0;
        account.deposit(initialBalance);

        double withdrawalAmount = 200.0;

        assertThrows(InsufficientBalanceException.class, () -> account.withdraw(withdrawalAmount));
    }

    @Test
    void getStatement_TransactionsExist_ReturnsStatement() {
        // Set up test data
        account.deposit(100.0);
        account.withdraw(50.0);

        // Define expected statement with the appropriate date format and decimal separator
        String expectedStatement = "Date\t\tAmount\t\tBalance\t\tType\n" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\t100,0\t\t100,0\t\tDEPOSIT\n" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "\t-50,0\t\t50,0\t\tWITHDRAWAL\n";

        // Get the actual statement
        String actualStatement = account.getStatement();

        // Print the expected and actual statements for comparison
        System.out.println("Expected Statement:");
        System.out.println(expectedStatement);
        System.out.println("Actual Statement:");
        System.out.println(actualStatement);

        // Perform the assertion after trimming the expected and actual statements
        assertEquals(expectedStatement.trim(), actualStatement.trim());
    }


}
