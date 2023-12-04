package accountpackage;

import java.math.BigDecimal;

public class Account {
    private int id;
    private String accountNumber;
    private BigDecimal balance;
    public Account(int id, String accountNumber, BigDecimal balance) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
    // Getters and Setters for all three fields
    public int getId() {
        return id;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}