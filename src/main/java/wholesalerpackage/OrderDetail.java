package wholesalerpackage;

// OrderDetail class - an order detail has an order detail id, an order id, a product id, and a quantity
// There can be one or multiple order details in each order
// When creating an order details to add to the order, only the product id, and quantity are submitted
// The rest of the fields are set by the server
public class OrderDetail {
    private int orderDetailId;
    private int orderId;
    private int productId;
    private int quantity;

    // Default constructor needed by Jackson and Jersey
    public OrderDetail() {
    }
    public OrderDetail(int orderDetailId, int orderId, int productId, int quantity) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getOrderDetailId() {
        return orderDetailId;
    }
    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    // Add getter and setter for quantity
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}