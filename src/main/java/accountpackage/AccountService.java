package accountpackage;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO = new AccountDAO();

    public void deposit(int accountNumber, BigDecimal amount) throws SQLException, IllegalArgumentException {
        // Make a deposit
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        // Synchronize on the account number to avoid concurrent updates
        synchronized (this) {
            Account account = accountDAO.getAccountByNumber(accountNumber);
            if (account == null) {
                throw new IllegalArgumentException("Account not found.");
            }

            BigDecimal newBalance = account.getBalance().add(amount);
            accountDAO.updateAccountBalance(accountNumber, newBalance);
        }
    }

    public void withdraw(int accountNumber, BigDecimal amount) throws SQLException, IllegalArgumentException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        Account account = accountDAO.getAccountByNumber(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found.");
        }

        BigDecimal newBalance = account.getBalance().subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Insufficient funds.");
        }

        accountDAO.updateAccountBalance(accountNumber, newBalance);
    }


    public void transfer(int fromAccountNumber, int toAccountNumber, BigDecimal amount) throws SQLException, IllegalArgumentException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        synchronized (this) {
            Account fromAccount = accountDAO.getAccountByNumber(fromAccountNumber);
            Account toAccount = accountDAO.getAccountByNumber(toAccountNumber);

            if (fromAccount == null || toAccount == null) {
                throw new IllegalArgumentException("One or both accounts not found.");
            }

            BigDecimal newFromAccountBalance = fromAccount.getBalance().subtract(amount);
            if (newFromAccountBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Insufficient funds in the source account.");
            }

            BigDecimal newToAccountBalance = toAccount.getBalance().add(amount);

            // Update both accounts within the same transaction
            accountDAO.updateAccountBalance(fromAccountNumber, newFromAccountBalance);
            accountDAO.updateAccountBalance(toAccountNumber, newToAccountBalance);
        }
    }
    public Account viewBalance(int accountNumber) throws SQLException {
        return accountDAO.getAccountBalance(accountNumber);
    }
    public List<Account> viewBalances() throws SQLException {
        return accountDAO.getAccountBalances();
    }
    public void createTables() throws SQLException {
        accountDAO.createTables();
    }
    public Account createAccount(String name) throws SQLException {
        return accountDAO.createAccount(name);
    }
}