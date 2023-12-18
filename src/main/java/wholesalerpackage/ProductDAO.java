package wholesalerpackage;

import common.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DAO {

    // Retrieve all products from the database
    public List<Product> getProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, name, price FROM products";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(rs.getInt("product_id"), rs.getString("name"), rs.getBigDecimal("price")));
            }
        }
        return products;
    }

    // Retrieve a product by its id
    public Product getProductById(int productId) throws SQLException {
        String sql = "SELECT product_id, name, price FROM products WHERE product_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getBigDecimal("price")
                    );
                }
            }
        }
        return null;
    }
}