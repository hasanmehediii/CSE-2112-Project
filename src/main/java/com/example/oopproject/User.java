package com.example.oopproject;

public record User(String username, String password, String firstName, String lastName, String dateOfBirth,
                   double currentBalance, String lastTransactions) {
}
