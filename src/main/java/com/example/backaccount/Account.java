package com.example.backaccount;

import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deposit(double amount) {
        validatePositiveAmount(amount);
        balance += amount;
        addTransaction(amount, TransactionType.DEPOSIT);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    public void withdraw(double amount) {
        validatePositiveAmount(amount);
        double withdrawalAmount = -Math.abs(amount); // Create a negative withdrawal amount
        validateSufficientBalance(withdrawalAmount);
        balance += withdrawalAmount;
        addTransaction(withdrawalAmount, TransactionType.WITHDRAWAL);
    }

    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactions);
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Date\t\tAmount\t\tBalance\t\tType\n");
        double runningBalance = 0.0;
        for (Transaction transaction : transactions) {
            runningBalance += transaction.getAmount();
            statement.append(transaction.getDate())
                    .append("\t").append(transaction.getAmount())
                    .append("\t\t").append(runningBalance)
                    .append("\t\t").append(transaction.getType())
                    .append("\n");
        }
        statement.append("Total Balance:\t\t\t").append(runningBalance).append("\n");
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
        if (-amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal: " + amount);
        }
    }

    private void addTransaction(double amount, TransactionType type) {
        Transaction transaction = new Transaction(LocalDate.now(), amount, balance, type);
        transactions.add(transaction);
    }

}
