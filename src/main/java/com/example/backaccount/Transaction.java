package com.example.backaccount;

import java.text.DecimalFormat;
import java.time.LocalDate;

public record Transaction(LocalDate date, double amount, double balance, TransactionType type) {

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#0.0");
        String formattedAmount = decimalFormat.format(amount);
        String formattedBalance = decimalFormat.format(balance);
        return date + "\t" + formattedAmount + "\t\t" + formattedBalance + "\t\t" + type;
    }
}
