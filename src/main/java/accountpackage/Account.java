package accountpackage;

import java.math.BigDecimal;

public class Account {
    private int id;
    private String name;
    private int accountNumber;
    private BigDecimal balance;
    public Account(int id, String name, int accountNumber,  BigDecimal balance) {
        this.name=name;
        this.id = id;   // Set by the database insertion
        this.accountNumber = accountNumber; // Some text that uniquely identifies the account
        this.balance = balance; // The current balance of the account
    }
    // Default constructor required by Jackson deserialization
    public Account(){}
    // Getters and Setters for all three fields
    public int getId() {
        return id;
    }
    public int getAccountNumber() {
        return accountNumber;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}