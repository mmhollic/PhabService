package accountpackage;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private Connection getConnection() throws SQLException {
        // Implement method to establish and return a database connection.
        // This could be a simple DriverManager.getConnection call, or use a connection pool.
        String dbUrl = "jdbc:postgresql://localhost:5432/postgres";

        try {
            // Registers the driver
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
        }
        Connection conn= DriverManager.getConnection(dbUrl, "postgres", "");
        return conn;

    }

    public Account getAccountByNumber(String accountNumber) throws SQLException {
        String sql = "SELECT id, account_number, balance FROM accounts WHERE account_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accountNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(rs.getInt("id"), rs.getString("account_number"), rs.getBigDecimal("balance"));
                }
            }
        }
        return null;
    }

    public Account getAccountBalance(String accountNumber) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try {
            Connection conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql);
            Account ac=null;
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ac=new Account(rs.getInt("id"), rs.getString("account_number"), rs.getBigDecimal("balance"));
            }
            return ac;

        }
        catch (Exception e) {
            String warning=e.getMessage();
            System.out.println(warning);
        }
        return null; // Or throw an exception if the account is not found
    }
    public List<Account> getAccountBalances() throws SQLException {
        String sql = "SELECT * FROM accounts WHERE true";
        try {
            Connection conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ArrayList<Account> ac=new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ac.add(new Account(rs.getInt("id"), rs.getString("account_number"), rs.getBigDecimal("balance")));
            }
            return ac;

        }
        catch (Exception e) {
            String warning=e.getMessage();
            System.out.println(warning);
        }
        return null; // Or throw an exception if the account is not found
    }

    public void updateAccountBalance(String accountNumber, BigDecimal newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBigDecimal(1, newBalance);
            pstmt.setString(2, accountNumber);
            pstmt.executeUpdate();
        }
    }
    public void resetAccounts() throws SQLException {
        String sql = "create table accounts id integer default 0 not null,balance real default 0 not null, account_number varchar(16) default 0 not null";
        try (Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.execute();
        }
    }

    // Additional methods for transactions, and other database interactions
}
