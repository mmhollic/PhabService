package wholesalerpackage;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

// An order has an order id, a customer id, an order time, a status, a total amount, and a list of order details
// The order details are the list of items ordered
// When submitting an order, only the customer id and the list of order details are submitted
// All other fields are set by the server
// The customer id is the customer's bank account number
public class Order {
    private int orderId;
    private int customerId;
    private Timestamp orderTime;
    private String status;
    private BigDecimal totalAmount;
    private List<OrderDetail> orderDetails; // Added field for order details
    // Default constructor needed by Jackson and Jersey
    public Order(){
    }

    public Order(int orderId, int customerId, Timestamp orderTime, String status, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderTime = orderTime;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public Timestamp getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String orderStatus) {
        this.status = orderStatus;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

}