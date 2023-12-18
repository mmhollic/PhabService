package wholesalerpackage;

import java.sql.*;
import java.util.ArrayList;

import common.DAO;

public class OrderDAO extends DAO{

    public int createOrder(Order order) throws SQLException {
        String insertOrderSql = "INSERT INTO orders (customer_id, order_time, status, total_amount) VALUES (?, ?, ?, ?)";
        String insertOrderDetailSql = "INSERT INTO order_details (order_id, product_id, quantity) VALUES (?, ?, ?)";
        int orderId=0;
        try (Connection conn = getConnection()) {
            try {
                conn.setAutoCommit(false);


                // Insert the order
                try (PreparedStatement pstmt = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setInt(1, order.getCustomerId());
                    pstmt.setTimestamp(2, order.getOrderTime());
                    pstmt.setString(3, order.getStatus());
                    pstmt.setBigDecimal(4, order.getTotalAmount());
                    pstmt.executeUpdate();

                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            order.setOrderId(generatedKeys.getInt(1));
                            orderId=order.getOrderId();
                        } else {
                            throw new SQLException("Creating order failed, no ID obtained.");
                        }
                    }
                }

                // Insert order details
                try (PreparedStatement pstmt = conn.prepareStatement(insertOrderDetailSql, Statement.RETURN_GENERATED_KEYS)) {
                    for (OrderDetail detail : order.getOrderDetails()) {
                        pstmt.setInt(1, order.getOrderId());
                        pstmt.setInt(2, detail.getProductId());
                        pstmt.setInt(3, detail.getQuantity());
                        pstmt.executeUpdate();
                        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                detail.setOrderDetailId(generatedKeys.getInt(1));
                            } else {
                                throw new SQLException("Creating order failed, no ID obtained.");
                            }
                        }
                    }
                }

                conn.commit();

            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            }
        }
        return orderId;
    }

    public Order getOrderById(int orderId) throws SQLException {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        String odsql = "SELECT * FROM order_details WHERE order_id = ?";
        Order order = null;

        // try block 'with resources' to ensure resources are closed
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             PreparedStatement pstmtod = conn.prepareStatement(odsql)) {
            pstmt.setInt(1, orderId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    order = new Order(
                            rs.getInt("order_id"),
                            rs.getInt("customer_id"),
                            rs.getTimestamp("order_time"),
                            rs.getString("status"),
                            rs.getBigDecimal("total_amount")
                    );
                }
                else return null; // Order not found
            }

            pstmtod.setInt(1, orderId);

            try (ResultSet rs = pstmtod.executeQuery()) {
                ArrayList<OrderDetail> od=new ArrayList<>();
                while (rs.next()) {
                    od.add(new OrderDetail(
                            rs.getInt("order_detail_id"),
                            rs.getInt("order_id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity"))
                    );
                }
                order.setOrderDetails(od);
            }
        }
        return order;
    }
    public void addOrderDetail(OrderDetail orderDetail) throws SQLException {
        String sql = "INSERT INTO order_details (order_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderDetail.getOrderId());
            pstmt.setInt(2, orderDetail.getProductId());
            pstmt.setInt(3, orderDetail.getQuantity());

            pstmt.executeUpdate();
        }
    }
    public void updateOrderStatus(int orderId, String status) throws SQLException {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, orderId);
            pstmt.executeUpdate();
        }
    }
    public void commitTransaction() throws SQLException {
        if (conn != null) {
            conn.commit();
        }
    }

    /**
     * Begins a new transaction.
     *
     * @throws SQLException If there is an issue with starting the transaction.
     */
    public void beginTransaction() throws SQLException {
        if (conn != null) {
            conn.setAutoCommit(false);
        }
    }

    /**
     * Rolls back the current transaction.
     *
     * @throws SQLException If there is an issue with rolling back the transaction.
     */
    public void rollbackTransaction() throws SQLException {
        if (conn != null) {
            conn.rollback();
        }
    }
    // Additional methods as needed
}