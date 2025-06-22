/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import restaurant.management.system.database.DbConnection;
import restaurant.management.system.database.MySqlConnection;
import restaurant.management.system.model.OrderData;

/**
 *
 * @author labis
 */
public class OrderDao {
    
    private MySqlConnection mySql = new MySqlConnection();
    
    private void createOrderTableIfNotExists(){
        String createOrderTableSQL = """
                                CREATE TABLE IF NOT EXISTS orders (
                                    order_id INT AUTO_INCREMENT PRIMARY KEY,
                                    customer_id INT NOT NULL,
                                    table_number INT NOT NULL,
                                    order_date VARCHAR(20) NOT NULL,
                                    order_time VARCHAR(20) NOT NULL,
                                    total_amount DECIMAL(10, 2) NOT NULL,
                                    order_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    customer_id INT
                                )
                                """;
        try (Connection conn = mySql.openConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createOrderTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     private void createOrderItemsTableIfNotExists(){
        String createOrderItemsTableSQL = """
                                CREATE TABLE IF NOT EXISTS order_items (
                                    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
                                    order_id INT NOT NULL,
                                    item_id INT NOT NULL,
                                    item_name VARCHAR(100) NOT NULL,
                                    quantity INT NOT NULL,
                                    price DECIMAL(8, 2) NOT NULL,
                                    subtotal DECIMAL(10, 2) NOT NULL,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
                                    FOREIGN KEY (item_id) REFERENCES menu(item_id) ON DELETE CASCADE,
                                    INDEX idx_order_id (order_id),
                                    INDEX idx_item_id (item_id)
                                )
                                """;
        try (Connection conn = mySql.openConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createOrderItemsTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean saveOrder(OrderData order) {
        // Ensure tables exist
        createOrderTableIfNotExists();
        createOrderItemsTableIfNotExists();

        // Modified query - don't insert order_id, let it auto-increment
        String orderQuery = "INSERT INTO orders (customer_id, table_number, order_date, order_time, total_amount, order_status) VALUES (?, ?, ?, ?, ?, ?)";
        String orderItemQuery = "INSERT INTO order_items (order_id, item_id, item_name, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = mySql.openConnection()) {
            // Start transaction
            conn.setAutoCommit(false);

            // Save order and get generated ID
            try (PreparedStatement orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, order.getCustomerId());
                orderStmt.setInt(2, order.getTableNumber());
                orderStmt.setString(3, order.getOrderDate());
                orderStmt.setString(4, order.getOrderTime());
                orderStmt.setDouble(5, order.getTotalAmount());
                orderStmt.setString(6, order.getOrderStatus());

                int orderRowsAffected = orderStmt.executeUpdate();

                if (orderRowsAffected > 0) {
                    // Get the generated order ID
                    try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedOrderId = generatedKeys.getInt(1);

                            // Save order items
                            try (PreparedStatement itemStmt = conn.prepareStatement(orderItemQuery)) {
                                for (OrderData.OrderItem item : order.getOrderItems()) {
                                    itemStmt.setInt(1, generatedOrderId);
                                    itemStmt.setInt(2, item.getMenuId());
                                    itemStmt.setString(3, item.getItemName());
                                    itemStmt.setInt(4, item.getQuantity());
                                    itemStmt.setDouble(5, item.getPrice());
                                    itemStmt.setDouble(6, item.getSubtotal());
                                    itemStmt.addBatch();
                                }

                                int[] itemRowsAffected = itemStmt.executeBatch();

                                // Check if all items were saved
                                boolean allItemsSaved = true;
                                for (int rows : itemRowsAffected) {
                                    if (rows <= 0) {
                                        allItemsSaved = false;
                                        break;
                                    }
                                }

                                if (allItemsSaved) {
                                    conn.commit();
                                    // Update the order object with the generated ID
                                    order.setOrderId(String.valueOf(generatedOrderId));
                                    return true;
                                } else {
                                    conn.rollback();
                                    return false;
                                }
                            }
                        } else {
                            conn.rollback();
                            return false;
                        }
                    }
                } else {
                    conn.rollback();
                    return false;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error saving order: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Retrieve all orders from the database
     * @return List of OrderData objects
     */
    public List<OrderData> getAllOrders() {
        // Ensure tables exist
        createOrderTableIfNotExists();
        createOrderItemsTableIfNotExists();
        
        List<OrderData> orders = new ArrayList<>();
        String query = "SELECT * FROM orders ORDER BY order_date DESC, order_time DESC";
        
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                OrderData order = new OrderData();
                order.setOrderId(rs.getString("order_id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setTableNumber(rs.getInt("table_number"));
                order.setOrderDate(rs.getString("order_date"));
                order.setOrderTime(rs.getString("order_time"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setOrderStatus(rs.getString("order_status"));
                
                // Load order items
                order.setOrderItems(getOrderItems(order.getOrderId()));
                
                orders.add(order);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving orders: " + e.getMessage());
            e.printStackTrace();
        }
        
        return orders;
    }
    
    /**
     * Retrieve order items for a specific order
     * @param orderId Order ID
     * @return List of OrderItem objects
     */
    public List<OrderData.OrderItem> getOrderItems(String orderId) {
        List<OrderData.OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM order_items WHERE order_id = ?";
        
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderData.OrderItem item = new OrderData.OrderItem();
                    item.setOrderItemId(rs.getInt("order_item_id"));
                    item.setOrderId(rs.getString("order_id"));
                    item.setMenuId(rs.getInt("item_id"));
                    item.setItemName(rs.getString("item_name"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getDouble("price"));
                    item.setSubtotal(rs.getDouble("subtotal"));
                    
                    orderItems.add(item);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving order items: " + e.getMessage());
            e.printStackTrace();
        }
        
        return orderItems;
    }
    
    /**
     * Get order by ID
     * @param orderId Order ID
     * @return OrderData object or null if not found
     */
    public OrderData getOrderById(String orderId) {
        String query = "SELECT * FROM orders WHERE order_id = ?";
        
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    OrderData order = new OrderData();
                    order.setOrderId(rs.getString("order_id"));
                    order.setCustomerId(rs.getInt("customer_id"));
                    order.setTableNumber(rs.getInt("table_number"));
                    order.setOrderDate(rs.getString("order_date"));
                    order.setOrderTime(rs.getString("order_time"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setOrderStatus(rs.getString("order_status"));
                    
                    // Load order items
                    order.setOrderItems(getOrderItems(order.getOrderId()));
                    
                    return order;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving order by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Update order status
     * @param orderId Order ID
     * @param newStatus New status
     * @return true if updated successfully, false otherwise
     */
    public boolean updateOrderStatus(String orderId, String newStatus) {
        String query = "UPDATE orders SET order_status = ? WHERE order_id = ?";
        
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, newStatus);
            stmt.setString(2, orderId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating order status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get orders by table number
     * @param tableNumber Table number
     * @return List of OrderData objects
     */
    public List<OrderData> getOrdersByTable(int tableNumber) {
        List<OrderData> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE table_number = ? ORDER BY order_date DESC, order_time DESC";
        
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, tableNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderData order = new OrderData();
                    order.setOrderId(rs.getString("order_id"));
                    order.setCustomerId(rs.getInt("customer_id"));
                    order.setTableNumber(rs.getInt("table_number"));
                    order.setOrderDate(rs.getString("order_date"));
                    order.setOrderTime(rs.getString("order_time"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setOrderStatus(rs.getString("order_status"));
                    
                    // Load order items
                    order.setOrderItems(getOrderItems(order.getOrderId()));
                    
                    orders.add(order);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving orders by table: " + e.getMessage());
            e.printStackTrace();
        }
        
        return orders;
    }
    
    /**
     * Get orders by status
     * @param status Order status
     * @return List of OrderData objects
     */
    public List<OrderData> getOrdersByStatus(String status) {
        List<OrderData> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE order_status = ? ORDER BY order_date DESC, order_time DESC";
        
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderData order = new OrderData();
                    order.setOrderId(rs.getString("order_id"));
                    order.setCustomerId(rs.getInt("customer_id"));
                    order.setTableNumber(rs.getInt("table_number"));
                    order.setOrderDate(rs.getString("order_date"));
                    order.setOrderTime(rs.getString("order_time"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setOrderStatus(rs.getString("order_status"));
                    
                    // Load order items
                    order.setOrderItems(getOrderItems(order.getOrderId()));
                    
                    orders.add(order);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving orders by status: " + e.getMessage());
            e.printStackTrace();
        }
        
        return orders;
    }
    
    /**
     * Delete an order and its items
     * @param orderId Order ID
     * @return true if deleted successfully, false otherwise
     */
    public boolean deleteOrder(String orderId) {
        String deleteItemsQuery = "DELETE FROM order_items WHERE order_id = ?";
        String deleteOrderQuery = "DELETE FROM orders WHERE order_id = ?";
        
        Connection conn = null;
        try {
            conn = mySql.openConnection();
            // Start transaction
            conn.setAutoCommit(false);
            
            try {
                // Delete order items first
                try (PreparedStatement itemStmt = conn.prepareStatement(deleteItemsQuery)) {
                    itemStmt.setString(1, orderId);
                    itemStmt.executeUpdate();
                }
                
                // Delete order
                try (PreparedStatement orderStmt = conn.prepareStatement(deleteOrderQuery)) {
                    orderStmt.setString(1, orderId);
                    int rowsAffected = orderStmt.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        conn.commit();
                        return true;
                    } else {
                        conn.rollback();
                        return false;
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
            
        } catch (SQLException e) {
            System.err.println("Error deleting order: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    System.err.println("Error closing connection: " + closeEx.getMessage());
                }
            }
        }
    }
    
    public boolean deleteOrderItems(String orderId) {
        String query = "DELETE FROM order_items WHERE order_id = ?";
        
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, orderId);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " order items for order: " + orderId);
            return true; // Return true even if no items were deleted (empty order)
            
        } catch (SQLException e) {
            System.err.println("Error deleting order items: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<OrderData> getOrdersByCustomer(int customerId) {
        // Ensure tables exist
        createOrderTableIfNotExists();
        createOrderItemsTableIfNotExists();
        
        List<OrderData> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE customer_id = ? ORDER BY order_date DESC, order_time DESC";
        
        System.out.println("Executing query for customer ID: " + customerId);
        
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, customerId);
            System.out.println("Prepared statement: " + stmt.toString());
            
            try (ResultSet rs = stmt.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    count++;
                    OrderData order = new OrderData();
                    order.setOrderId(rs.getString("order_id"));
                    order.setCustomerId(rs.getInt("customer_id"));
                    order.setTableNumber(rs.getInt("table_number"));
                    order.setOrderDate(rs.getString("order_date"));
                    order.setOrderTime(rs.getString("order_time"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setOrderStatus(rs.getString("order_status"));
                    order.setOrderItems(getOrderItems(order.getOrderId()));
                    orders.add(order);
                    
                    System.out.println("Found order: " + order.getOrderId() + " for customer: " + order.getCustomerId());
                }
                System.out.println("Total orders found: " + count);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving orders by customer: " + e.getMessage());
            e.printStackTrace();
        }
        
        return orders;
    }
    
    /**
     * Debug method to check if orders table has any data
     * @return count of all orders in the database
     */
    public int getTotalOrderCount() {
        String query = "SELECT COUNT(*) as total FROM orders";
        
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                int total = rs.getInt("total");
                System.out.println("Total orders in database: " + total);
                return total;
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total order count: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }

    public boolean updateOrderWithItems(OrderData order) {
        String updateOrderQuery = "UPDATE orders SET total_amount = ?, order_status = ? WHERE order_id = ?";
        String insertItemQuery = "INSERT INTO order_items (order_id, item_id, item_name, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = mySql.openConnection();
            conn.setAutoCommit(false);
            
            // Update order total and status
            try (PreparedStatement orderStmt = conn.prepareStatement(updateOrderQuery)) {
                orderStmt.setDouble(1, order.getTotalAmount());
                orderStmt.setString(2, order.getOrderStatus());
                orderStmt.setString(3, order.getOrderId());
                
                int orderRowsAffected = orderStmt.executeUpdate();
                
                if (orderRowsAffected > 0) {
                    // Insert new order items
                    try (PreparedStatement itemStmt = conn.prepareStatement(insertItemQuery)) {
                        for (OrderData.OrderItem item : order.getOrderItems()) {
                            itemStmt.setString(1, order.getOrderId());
                            itemStmt.setInt(2, item.getMenuId());
                            itemStmt.setString(3, item.getItemName());
                            itemStmt.setInt(4, item.getQuantity());
                            itemStmt.setDouble(5, item.getPrice());
                            itemStmt.setDouble(6, item.getSubtotal());
                            itemStmt.addBatch();
                        }
                        
                        int[] itemRowsAffected = itemStmt.executeBatch();
                        
                        // Check if all items were saved
                        boolean allItemsSaved = true;
                        for (int rows : itemRowsAffected) {
                            if (rows <= 0) {
                                allItemsSaved = false;
                                break;
                            }
                        }
                        
                        if (allItemsSaved) {
                            conn.commit();
                            return true;
                        } else {
                            conn.rollback();
                            return false;
                        }
                    }
                } else {
                    conn.rollback();
                    return false;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error updating order with items: " + e.getMessage());
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Error rolling back transaction: " + rollbackEx.getMessage());
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException closeEx) {
                    System.err.println("Error closing connection: " + closeEx.getMessage());
                }
            }
        }
    }
    
    public List<Integer> getAllCustomerIdsWithOrders() {
        List<Integer> customerIds = new ArrayList<>();
        String query = "SELECT DISTINCT customer_id FROM orders ORDER BY customer_id";
        
        try (Connection conn = mySql.openConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                int customerId = rs.getInt("customer_id");
                customerIds.add(customerId);
                System.out.println("Customer ID with orders: " + customerId);
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting customer IDs: " + e.getMessage());
            e.printStackTrace();
        }
        
        return customerIds;
    }
}