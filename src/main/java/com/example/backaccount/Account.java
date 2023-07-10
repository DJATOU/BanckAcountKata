package com.example.backaccount;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Account {
    private double balance;
    private final List<Transaction> transactions;

    public Account() {
        balance = 0.0;
        transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        validatePositiveAmount(amount);
        balance += amount;
        addTransaction(amount, TransactionType.DEPOSIT);
    }

    public void withdraw(double amount) {
        validatePositiveAmount(amount);
        validateSufficientBalance(amount);
        balance -= amount;
        addTransaction(-amount, TransactionType.WITHDRAWAL);
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Date\t\tAmount\t\tBalance\t\tType\n");
        for (Transaction transaction : transactions) {
            statement.append(transaction.toString()).append("\n");
        }
        return statement.toString();
    }

    public double getBalance() {
        return balance;
    }

    private void validatePositiveAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount: " + amount);
        }
    }

    private void validateSufficientBalance(double amount) {
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal: " + amount);
        }
    }

    private void addTransaction(double amount, TransactionType type) {
        Transaction transaction = new Transaction(LocalDate.now(), amount, balance, type);
        transactions.add(transaction);
    }

}
