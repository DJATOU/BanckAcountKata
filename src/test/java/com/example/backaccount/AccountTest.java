package com.example.backaccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        double initialBalance = 200.0; // Set an initial balance greater than the withdrawal amount
        account.deposit(initialBalance);

        double withdrawalAmount = 300.0; // Set a withdrawal amount greater than the balance

        assertThrows(InsufficientBalanceException.class, () -> account.withdraw(withdrawalAmount));
    }


    @Test
    void getStatement_TransactionsExist_ReturnsStatement() {
        // Set up test data
        Account account = new Account();
        account.deposit(100.0);
        account.withdraw(50.0);

        // Define the expected statement
        String expectedStatement = "Date\t\tAmount\t\tBalance\t\tType\n" +
                "2023-07-12\t100.0\t\t100.0\t\tDEPOSIT\n" +
                "2023-07-12\t-50.0\t\t50.0\t\tWITHDRAWAL\n" +
                "Total Balance:\t\t\t50.0\n";

        // Get the actual statement
        String actualStatement = account.getStatement();

        // Assert the expected and actual statements are equal
        assertEquals(expectedStatement, actualStatement);
    }





    @Test
    void deposit_ZeroAmount_BalanceUnchanged() {
        double initialBalance = account.getBalance();
        double zeroAmount = 0.0;

        assertThrows(IllegalArgumentException.class, () -> account.deposit(zeroAmount));
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    void withdraw_ZeroAmount_BalanceUnchanged() {
        double initialBalance = account.getBalance();
        double zeroAmount = 0.0;

        assertThrows(IllegalArgumentException.class, () -> account.withdraw(zeroAmount));
        assertEquals(initialBalance, account.getBalance());
    }

    @Test
    void deposit_MaximumAmount_BalanceIncreased() {
        double initialBalance = account.getBalance();
        double maximumAmount = Double.MAX_VALUE;

        account.deposit(maximumAmount);

        assertEquals(initialBalance + maximumAmount, account.getBalance());
    }
    @Test
    void withdraw_MaximumAmount_BalanceDecreased() {
        double initialBalance = 1000.0;
        account.deposit(initialBalance);
        double withdrawalAmount = 500.0; // Adjust the withdrawal amount as needed

        account.withdraw(withdrawalAmount);

        assertEquals(initialBalance - withdrawalAmount, account.getBalance());
    }
    @Test
    void multipleTransactions_VerifyBalanceAndHistory() {
        double initialBalance = 1000.0;
        account.deposit(initialBalance);

        // Perform multiple transactions
        double transaction1 = 200.0;
        double transaction2 = -300.0;
        double transaction3 = 150.0;

        account.deposit(transaction1);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(transaction2));
        account.deposit(transaction3);

        // Verify balance
        double expectedBalance = initialBalance + transaction1 + transaction3;
        assertEquals(expectedBalance, account.getBalance());

        // Verify transaction history
        List<Double> expectedTransactions = Arrays.asList(initialBalance, transaction1, transaction3);
        List<Transaction> actualTransactions = account.getTransactionHistory();
        List<Double> actualTransactionAmounts = actualTransactions.stream()
                .map(Transaction::getAmount)
                .collect(Collectors.toList());
        assertEquals(expectedTransactions, actualTransactionAmounts);
    }

}
