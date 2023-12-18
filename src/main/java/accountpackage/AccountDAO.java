package accountpackage;

import common.DAO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO extends DAO {
    public Account getAccountByNumber(int accountNumber) throws SQLException {
        // Retrieve an account by its number
        String sql = "SELECT id, name, account_number, balance FROM accounts WHERE account_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, accountNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Account(rs.getInt("id"), rs.getString("name"),rs.getInt("account_number"), rs.getBigDecimal("balance"));
                }
            }
        }
        return null;
    }

    public Account getAccountBalance(int accountNumber) throws SQLException {
        // get account balance
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        try {
            Connection conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql);
            Account ac=null;
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ac=new Account(rs.getInt("id"), rs.getString("name"),rs.getInt("account_number"), rs.getBigDecimal("balance"));
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
        // get account balances for all accounts on the database
        String sql = "SELECT * FROM accounts WHERE true";
        try {
            Connection conn = getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql);
            ArrayList<Account> ac=new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ac.add(new Account(rs.getInt("id"), rs.getString("name"),rs.getInt("account_number"), rs.getBigDecimal("balance")));
            }
            return ac;

        }
        catch (Exception e) {
            String warning=e.getMessage();
            System.out.println(warning);
        }
        return null; // Or throw an exception if the account is not found
    }

    public void updateAccountBalance(int accountNumber, BigDecimal newBalance) throws SQLException {
        // Update an account's balance
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBigDecimal(1, newBalance);
            pstmt.setInt(2, accountNumber);
            pstmt.executeUpdate();
        }
    }
    public void createTables() throws SQLException {
        // Create all database tables - used when setting up new database
        String sqlproducts = "CREATE TABLE products (\n" +
                "    product_id SERIAL PRIMARY KEY,\n" +
                "    name VARCHAR(255) NOT NULL,\n" +
                "    price NUMERIC(10, 2) NOT NULL\n" +
                ");";
        String sqlorders = "CREATE TABLE orders (\n" +
                "    order_id SERIAL PRIMARY KEY,\n" +
                "    customer_id INT NOT NULL,\n" +
                "    order_time TIMESTAMP NOT NULL,\n" +
                "    status VARCHAR(50) NOT NULL,\n" +
                "    total_amount NUMERIC(10, 2) NOT NULL\n" +
                ");";
        String sqlorderdetails = "CREATE TABLE order_details (\n" +
                "    order_detail_id SERIAL PRIMARY KEY,\n" +
                "    order_id INT NOT NULL,\n" +
                "    product_id INT NOT NULL,\n" +
                "    quantity INT NOT NULL,\n" +
                "    FOREIGN KEY (order_id) REFERENCES orders(order_id),\n" +
                "    FOREIGN KEY (product_id) REFERENCES products(product_id)\n" +
                ");";
        String accountsql = "CREATE TABLE accounts (\n" +
                "    id SERIAL PRIMARY KEY,\n" +
                "    name VARCHAR(255) NOT NULL,\n" +
                "    account_number INT NOT NULL,\n" +
                "    balance NUMERIC(10, 2) NOT NULL\n" +
                ");";
        String productslistsql= "INSERT INTO products (name, price) VALUES\n" +
                "('Vicks Vaporub 100g', 3.7),\n" +
                "('Vicks First Defence 15ml', 5),\n" +
                "('Gsk Night Nurse 160ml', 7.5),\n" +
                "('Lemsip Max 16 caps', 3.7),\n" +
                "('Lemsip Standard 10 sachets', 3.5),\n" +
                "('Sudafed Day and Night 16 caps', 3.2),\n" +
                "('Sudafed Max 16 caps', 3.2),\n" +
                "('Benylin Mucus relief 16 caps', 3.2),\n" +
                "('Benylin 4 flu 24 caps', 4.9),\n" +
                "('E45 Psoriasis cream 50ml', 16),\n" +
                "('Eurax Skin cream 100g', 4.2),\n" +
                "('Eucerin Skin relief cream 50ml', 7),\n" +
                "('Eucerin Face scrub 100ml', 6),\n" +
                "('Dermalex Psoriasis cream 150ml', 25),\n" +
                "('Dermalex Repair and Restore 100g', 10),\n" +
                "('Dermalex Eczema cream 30g', 9.7),\n" +
                "('Dermalex Eczema cream 100g', 22.2),\n" +
                "('Cetaphil Moisturising cream 50ml', 7.6),\n" +
                "('Cetaphil Exfoliating cleanser 180ml', 10.1),\n" +
                "('Nurofen Meltlets 16 caps', 3.7),\n" +
                "('Nurofen Express 16 caps', 3.5),\n" +
                "('Nurofen Max strength 32 caps', 6.2),\n" +
                "('Nurofen Standard 16 caps', 3.2),\n" +
                "('Cuprofen Max strength 96 caps', 9),\n" +
                "('Solpadeine Headache 16 caps', 1.6),\n" +
                "('Anadin Extra 16 caps', 2),\n" +
                "('Anadin Triple action 12 caps', 1.9),\n" +
                "('Anadin Original 16 caps', 1.5),\n" +
                "('Disprin Soluble 32 tablets', 2.8),\n" +
                "('Dioralyte Blackcurrant 12 sachets', 7.3),\n" +
                "('Dioralyte Lemon 12 sachets', 7.3),\n" +
                "('Gaviscon Chewable 24 tablets', 3.5),\n" +
                "('Senokot Max 10 tablets', 2.7),\n" +
                "('Gaviscon Advance 300ml', 8.1),\n" +
                "('Benadryl Relief 24 caps', 7.1),\n" +
                "('Piriteze tabs 7 tablets', 2.3),\n" +
                "('Beconase Relief 100 sprays', 4),\n" +
                "('Dettol Antiseptic 500ml', 3),\n" +
                "('Dettol Hand sanitizer 500ml', 6.3),\n" +
                "('Elastoplast plasters 20 plasters', 2),\n" +
                "('TCP Liquid 200ml', 3.2);";

        String sqlwholesaler="INSERT INTO accounts (name,account_number, balance) VALUES\n" +
                "('MedsRUs',1471, 30000);";


        // Set up all database tables
        try (Connection conn = getConnection();
            PreparedStatement pstmorders = conn.prepareStatement(sqlorders);
            PreparedStatement pstmtproducts = conn.prepareStatement(sqlproducts);
            PreparedStatement pstmtorderdetails = conn.prepareStatement(sqlorderdetails);
            PreparedStatement pstmtaccounts = conn.prepareStatement(accountsql);
            PreparedStatement pstmtproductvalues = conn.prepareStatement(productslistsql);
            PreparedStatement pstmtaccountwholesaler = conn.prepareStatement(sqlwholesaler)) {
            pstmorders.execute();
            pstmtproducts.execute();
            pstmtorderdetails.execute();
            pstmtaccounts.execute();
            pstmtproductvalues.execute();
            pstmtaccountwholesaler.execute();
        }
    }

    private int generateAccountNumber() throws SQLException {
        // Get a unique account number string - it will be the largest account number in the database + 1
        String sql = "SELECT MAX(account_number) + 1 AS new_account_number FROM accounts";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("new_account_number");
                }
            }
        }
        return 0;
    }
    public Account createAccount(String name) throws SQLException {
        // Create a new account with a unique account number
        String sql = "INSERT INTO accounts (name, account_number, balance) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, name);
            int accountNumber = generateAccountNumber();
            pstmt.setInt(2, accountNumber);
            pstmt.setBigDecimal(3, BigDecimal.ZERO);
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Account(generatedKeys.getInt(1),name,accountNumber, BigDecimal.ZERO);

                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }


}
