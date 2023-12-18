package wholesalerpackage;

import java.math.BigDecimal;

// Describe a product
public class Product {
    private int productId;
    private String name;
    private BigDecimal price;

    public Product(int productId, String name, BigDecimal price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getName() {
        return name;
    }
    public void setName(String productName) {
        this.name = productName;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal productPrice) {
        this.price = productPrice;
    }

}