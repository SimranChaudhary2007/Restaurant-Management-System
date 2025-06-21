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
    
    MySqlConnection mySql = new MySqlConnection();
    public boolean saveOrder(OrderData order) {
        Connection conn = mySql.openConnection();
        String orderQuery = "INSERT INTO orders (order_id, table_number, order_date, order_time, total_amount, order_status) VALUES (?, ?, ?, ?, ?, ?)";
        String orderItemQuery = "INSERT INTO order_items (order_id, menu_id, item_name, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            // Start transaction
            conn.setAutoCommit(false);
            
            // Save order
            try (PreparedStatement orderStmt = conn.prepareStatement(orderQuery)) {
                orderStmt.setString(1, order.getOrderId());
                orderStmt.setInt(2, order.getTableNumber());
                orderStmt.setString(3, order.getOrderDate());
                orderStmt.setString(4, order.getOrderTime());
                orderStmt.setDouble(5, order.getTotalAmount());
                orderStmt.setString(6, order.getOrderStatus());
                
                int orderRowsAffected = orderStmt.executeUpdate();
                
                if (orderRowsAffected > 0) {
                    // Save order items
                    try (PreparedStatement itemStmt = conn.prepareStatement(orderItemQuery)) {
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
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
            System.err.println("Error saving order: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error resetting auto-commit: " + e.getMessage());
            }
        }
    }
    
    /**
     * Retrieve all orders from the database
     * @return List of OrderData objects
     */
    public List<OrderData> getAllOrders() {
        List<OrderData> orders = new ArrayList<>();
        String query = "SELECT * FROM orders ORDER BY order_date DESC, order_time DESC";
        Connection conn = mySql.openConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                OrderData order = new OrderData();
                order.setOrderId(rs.getString("order_id"));
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
         Connection conn = mySql.openConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                OrderData.OrderItem item = new OrderData.OrderItem();
                item.setOrderItemId(rs.getInt("order_item_id"));
                item.setOrderId(rs.getString("order_id"));
                item.setMenuId(rs.getInt("menu_id"));
                item.setItemName(rs.getString("item_name"));
                item.setQuantity(rs.getInt("quantity"));
                item.setPrice(rs.getDouble("price"));
                item.setSubtotal(rs.getDouble("subtotal"));
                
                orderItems.add(item);
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
         Connection conn = mySql.openConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                OrderData order = new OrderData();
                order.setOrderId(rs.getString("order_id"));
                order.setTableNumber(rs.getInt("table_number"));
                order.setOrderDate(rs.getString("order_date"));
                order.setOrderTime(rs.getString("order_time"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setOrderStatus(rs.getString("order_status"));
                
                // Load order items
                order.setOrderItems(getOrderItems(order.getOrderId()));
                
                return order;
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
         Connection conn = mySql.openConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
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
         Connection conn = mySql.openConnection();
        List<OrderData> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE table_number = ? ORDER BY order_date DESC, order_time DESC";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, tableNumber);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                OrderData order = new OrderData();
                order.setOrderId(rs.getString("order_id"));
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
         Connection conn = mySql.openConnection();
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                OrderData order = new OrderData();
                order.setOrderId(rs.getString("order_id"));
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
         Connection conn = mySql.openConnection();
        
        try {
            // Start transaction
            conn.setAutoCommit(false);
            
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
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
            System.err.println("Error deleting order: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error resetting auto-commit: " + e.getMessage());
            }
        }
    }
}