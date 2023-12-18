package wholesalerpackage;

import accountpackage.AccountService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WholesalerService {
    private static final int WHOLESALE_ACCOUNT_ID = 1471; // The account number of the wholesaler is hardcoded here
    private ProductDAO productDAO = new ProductDAO();
    private OrderDAO orderDAO = new OrderDAO();
    // Assuming BankAccountService is similar to the one reviewed earlier
    private AccountService bankAccountService = new AccountService();

    public List<Product> viewProducts() throws SQLException {
        return productDAO.getProducts();
    }

    public Order getOrder(int orderId) throws SQLException {
        return orderDAO.getOrderById(orderId);
    }

    public Order placeOrder(Order order) throws SQLException {
        // Initialize order fields
        BigDecimal totalAmount = calculateTotalAmount(order.getOrderDetails());
        order.setTotalAmount(totalAmount);
        order.setOrderTime(new Timestamp(System.currentTimeMillis()));
        order.setStatus("PENDING");

        try {
            // Add the order to the database
            //orderDAO.beginTransaction();
            orderDAO.createOrder(order);

            // Make the financial transaction
            // Assuming a method in BankAccountService to handle payment
            bankAccountService.transfer(order.getCustomerId(), WHOLESALE_ACCOUNT_ID, totalAmount);

            // Commit transaction
            //orderDAO.commitTransaction();
        } catch (SQLException e) {
            // Rollback transaction in case of error
            //orderDAO.rollbackTransaction();
            throw e;
        }
        return order;
    }

    // Check the status of an order - this needs to be called to update the status if the order has been delivered
    public Order checkDeliveryStatus(int orderId) throws SQLException {
        Order order = orderDAO.getOrderById(orderId);
        if (order != null) {
            // Check if 10 minutes have passed since the order was placed
            long timeSinceOrder = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - order.getOrderTime().getTime());
            if (timeSinceOrder >= 10) {
                order.setStatus("DELIVERED");
                orderDAO.updateOrderStatus(orderId, "DELIVERED");
            }
        }
        return order;
    }

    // Calculate the total amount of an order
    private BigDecimal calculateTotalAmount(List<OrderDetail> orderDetails) throws SQLException {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderDetail detail : orderDetails) {
            Product product = productDAO.getProductById(detail.getProductId());
            total = total.add(product.getPrice().multiply(new BigDecimal(detail.getQuantity())));
        }
        return total;
    }


}