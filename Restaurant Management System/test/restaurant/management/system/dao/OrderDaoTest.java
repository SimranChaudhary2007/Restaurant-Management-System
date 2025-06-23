/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package restaurant.management.system.dao;

import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import restaurant.management.system.model.OrderData;

/**
 *
 * @author acer
 */
public class OrderDaoTest {
    
    private OrderDao dao;
    private OrderData testOrder;
    private int testCustomerId = 1;
    private int testTableNumber = 5;
    private String testOrderDate = "2024-01-15";
    private String testOrderTime = "12:30:00";
    private double testTotalAmount = 150.75;
    private String testOrderStatus = "PENDING";
    
    @Before
    public void setUp() {
        dao = new OrderDao();
        
        // Create test order with order items
        testOrder = new OrderData();
        testOrder.setCustomerId(testCustomerId);
        testOrder.setTableNumber(testTableNumber);
        testOrder.setOrderDate(testOrderDate);
        testOrder.setOrderTime(testOrderTime);
        testOrder.setTotalAmount(testTotalAmount);
        testOrder.setOrderStatus(testOrderStatus);
        
        // Create test order items
        List<OrderData.OrderItem> orderItems = new ArrayList<>();
        
        OrderData.OrderItem item1 = new OrderData.OrderItem();
        item1.setMenuId(1);
        item1.setItemName("Test Pizza");
        item1.setQuantity(2);
        item1.setPrice(25.50);
        item1.setSubtotal(51.00);
        orderItems.add(item1);
        
        OrderData.OrderItem item2 = new OrderData.OrderItem();
        item2.setMenuId(2);
        item2.setItemName("Test Burger");
        item2.setQuantity(3);
        item2.setPrice(15.25);
        item2.setSubtotal(45.75);
        orderItems.add(item2);
        
        testOrder.setOrderItems(orderItems);
    }
    
    @Test
    public void saveOrderWithValidData() {
        boolean result = dao.saveOrder(testOrder);
        Assert.assertTrue("Order should be saved successfully", result);
        Assert.assertNotNull("Order ID should be set after saving", testOrder.getOrderId());
        Assert.assertFalse("Order ID should not be empty", testOrder.getOrderId().isEmpty());
    }
    
    @Test
    public void saveOrderWithEmptyItems() {
        OrderData emptyOrder = new OrderData();
        emptyOrder.setCustomerId(testCustomerId);
        emptyOrder.setTableNumber(testTableNumber);
        emptyOrder.setOrderDate(testOrderDate);
        emptyOrder.setOrderTime(testOrderTime);
        emptyOrder.setTotalAmount(0.0);
        emptyOrder.setOrderStatus(testOrderStatus);
        emptyOrder.setOrderItems(new ArrayList<>());
        
        boolean result = dao.saveOrder(emptyOrder);
        Assert.assertTrue("Order with empty items should be saved successfully", result);
    }
    
    @Test
    public void getAllOrdersWithData() {
        // First save a test order
        dao.saveOrder(testOrder);
        
        List<OrderData> orders = dao.getAllOrders();
        Assert.assertNotNull("Orders list should not be null", orders);
        Assert.assertTrue("Should have at least one order", orders.size() > 0);
        
        // Check if our test order is in the list
        boolean foundTestOrder = false;
        for (OrderData order : orders) {
            if (order.getCustomerId() == testCustomerId && 
                order.getTableNumber() == testTableNumber &&
                order.getOrderDate().equals(testOrderDate)) {
                foundTestOrder = true;
                Assert.assertEquals("Total amount should match", testTotalAmount, order.getTotalAmount(), 0.01);
                Assert.assertEquals("Order status should match", testOrderStatus, order.getOrderStatus());
                Assert.assertNotNull("Order items should not be null", order.getOrderItems());
                break;
            }
        }
        Assert.assertTrue("Test order should be found in all orders", foundTestOrder);
    }
    
    @Test
    public void getOrderByIdWithValidId() {
        // First save a test order
        dao.saveOrder(testOrder);
        String orderId = testOrder.getOrderId();
        
        OrderData retrievedOrder = dao.getOrderById(orderId);
        Assert.assertNotNull("Retrieved order should not be null", retrievedOrder);
        Assert.assertEquals("Order ID should match", orderId, retrievedOrder.getOrderId());
        Assert.assertEquals("Customer ID should match", testCustomerId, retrievedOrder.getCustomerId());
        Assert.assertEquals("Table number should match", testTableNumber, retrievedOrder.getTableNumber());
        Assert.assertEquals("Total amount should match", testTotalAmount, retrievedOrder.getTotalAmount(), 0.01);
        Assert.assertNotNull("Order items should not be null", retrievedOrder.getOrderItems());
        Assert.assertTrue("Should have order items", retrievedOrder.getOrderItems().size() > 0);
    }
    
    @Test
    public void getOrderByIdWithInvalidId() {
        OrderData order = dao.getOrderById("99999");
        Assert.assertNull("Order should be null for invalid ID", order);
    }
    
    @Test
    public void getOrderItemsWithValidOrderId() {
        // First save a test order
        dao.saveOrder(testOrder);
        String orderId = testOrder.getOrderId();
        
        List<OrderData.OrderItem> orderItems = dao.getOrderItems(orderId);
        Assert.assertNotNull("Order items list should not be null", orderItems);
        Assert.assertEquals("Should have 2 order items", 2, orderItems.size());
        
        // Check first item
        OrderData.OrderItem item1 = orderItems.get(0);
        Assert.assertEquals("Item name should match", "Test Pizza", item1.getItemName());
        Assert.assertEquals("Quantity should match", 2, item1.getQuantity());
        Assert.assertEquals("Price should match", 25.50, item1.getPrice(), 0.01);
        Assert.assertEquals("Subtotal should match", 51.00, item1.getSubtotal(), 0.01);
    }
    
    @Test
    public void getOrderItemsWithInvalidOrderId() {
        List<OrderData.OrderItem> orderItems = dao.getOrderItems("99999");
        Assert.assertNotNull("Order items list should not be null", orderItems);
        Assert.assertEquals("Should return empty list for invalid order ID", 0, orderItems.size());
    }
    
    @Test
    public void updateOrderStatusWithValidId() {
        // First save a test order
        dao.saveOrder(testOrder);
        String orderId = testOrder.getOrderId();
        String newStatus = "COMPLETED";
        
        boolean result = dao.updateOrderStatus(orderId, newStatus);
        Assert.assertTrue("Order status update should succeed", result);
        
        // Verify the status was updated
        OrderData updatedOrder = dao.getOrderById(orderId);
        Assert.assertNotNull("Updated order should exist", updatedOrder);
        Assert.assertEquals("Status should be updated", newStatus, updatedOrder.getOrderStatus());
    }
    
    @Test
    public void updateOrderStatusWithInvalidId() {
        boolean result = dao.updateOrderStatus("99999", "COMPLETED");
        Assert.assertFalse("Order status update should fail with invalid ID", result);
    }
    
    @Test
    public void getOrdersByTableWithValidTable() {
        // First save a test order
        dao.saveOrder(testOrder);
        
        List<OrderData> orders = dao.getOrdersByTable(testTableNumber);
        Assert.assertNotNull("Orders list should not be null", orders);
        Assert.assertTrue("Should have at least one order for the table", orders.size() > 0);
        
        // Check if all returned orders belong to the specified table
        for (OrderData order : orders) {
            Assert.assertEquals("All orders should belong to the specified table", testTableNumber, order.getTableNumber());
        }
    }
    
    @Test
    public void getOrdersByTableWithInvalidTable() {
        List<OrderData> orders = dao.getOrdersByTable(999);
        Assert.assertNotNull("Orders list should not be null", orders);
        Assert.assertEquals("Should return empty list for invalid table", 0, orders.size());
    }
    
    @Test
    public void getOrdersByStatusWithValidStatus() {
        // First save a test order
        dao.saveOrder(testOrder);
        
        List<OrderData> orders = dao.getOrdersByStatus(testOrderStatus);
        Assert.assertNotNull("Orders list should not be null", orders);
        Assert.assertTrue("Should have at least one order with the status", orders.size() > 0);
        
        // Check if all returned orders have the specified status
        for (OrderData order : orders) {
            Assert.assertEquals("All orders should have the specified status", testOrderStatus, order.getOrderStatus());
        }
    }
    
    @Test
    public void getOrdersByStatusWithInvalidStatus() {
        List<OrderData> orders = dao.getOrdersByStatus("INVALID_STATUS");
        Assert.assertNotNull("Orders list should not be null", orders);
        Assert.assertEquals("Should return empty list for invalid status", 0, orders.size());
    }
    
    @Test
    public void getOrdersByCustomerWithValidCustomerId() {
        // First save a test order
        dao.saveOrder(testOrder);
        
        List<OrderData> orders = dao.getOrdersByCustomer(testCustomerId);
        Assert.assertNotNull("Orders list should not be null", orders);
        Assert.assertTrue("Should have at least one order for the customer", orders.size() > 0);
        
        // Check if all returned orders belong to the specified customer
        for (OrderData order : orders) {
            Assert.assertEquals("All orders should belong to the specified customer", testCustomerId, order.getCustomerId());
            Assert.assertNotNull("Order items should be loaded", order.getOrderItems());
        }
    }
    
    @Test
    public void getOrdersByCustomerWithInvalidCustomerId() {
        List<OrderData> orders = dao.getOrdersByCustomer(999);
        Assert.assertNotNull("Orders list should not be null", orders);
        Assert.assertEquals("Should return empty list for invalid customer ID", 0, orders.size());
    }
    
    @Test
    public void deleteOrderWithValidId() {
        // First save a test order
        dao.saveOrder(testOrder);
        String orderId = testOrder.getOrderId();
        
        boolean result = dao.deleteOrder(orderId);
        Assert.assertTrue("Order deletion should succeed", result);
        
        // Verify the order was deleted
        OrderData deletedOrder = dao.getOrderById(orderId);
        Assert.assertNull("Order should be null after deletion", deletedOrder);
        
        // Verify order items were also deleted
        List<OrderData.OrderItem> deletedItems = dao.getOrderItems(orderId);
        Assert.assertNotNull("Order items list should not be null", deletedItems);
        Assert.assertEquals("Order items should be empty after deletion", 0, deletedItems.size());
    }
    
    @Test
    public void deleteOrderWithInvalidId() {
        boolean result = dao.deleteOrder("99999");
        Assert.assertFalse("Order deletion should fail with invalid ID", result);
    }
    
    @Test
    public void deleteOrderItemsWithValidOrderId() {
        // First save a test order
        dao.saveOrder(testOrder);
        String orderId = testOrder.getOrderId();
        
        boolean result = dao.deleteOrderItems(orderId);
        Assert.assertTrue("Order items deletion should succeed", result);
        
        // Verify order items were deleted
        List<OrderData.OrderItem> deletedItems = dao.getOrderItems(orderId);
        Assert.assertNotNull("Order items list should not be null", deletedItems);
        Assert.assertEquals("Order items should be empty after deletion", 0, deletedItems.size());
        
        // Verify the order itself still exists
        OrderData order = dao.getOrderById(orderId);
        Assert.assertNotNull("Order should still exist after items deletion", order);
    }
    
    @Test
    public void deleteOrderItemsWithInvalidOrderId() {
        boolean result = dao.deleteOrderItems("99999");
        Assert.assertTrue("Order items deletion should return true even for invalid ID", result);
    }
    
    @Test
    public void updateOrderWithItemsWithValidData() {
        // First save a test order
        dao.saveOrder(testOrder);
        String orderId = testOrder.getOrderId();
        
        // Delete existing items first
        dao.deleteOrderItems(orderId);
        
        // Create new items for update
        List<OrderData.OrderItem> newItems = new ArrayList<>();
        OrderData.OrderItem newItem = new OrderData.OrderItem();
        newItem.setMenuId(3);
        newItem.setItemName("Updated Item");
        newItem.setQuantity(1);
        newItem.setPrice(20.00);
        newItem.setSubtotal(20.00);
        newItems.add(newItem);
        
        testOrder.setOrderItems(newItems);
        testOrder.setTotalAmount(20.00);
        testOrder.setOrderStatus("UPDATED");
        
        boolean result = dao.updateOrderWithItems(testOrder);
        Assert.assertTrue("Order update with items should succeed", result);
        
        // Verify the order was updated
        OrderData updatedOrder = dao.getOrderById(orderId);
        Assert.assertNotNull("Updated order should exist", updatedOrder);
        Assert.assertEquals("Total amount should be updated", 20.00, updatedOrder.getTotalAmount(), 0.01);
        Assert.assertEquals("Status should be updated", "UPDATED", updatedOrder.getOrderStatus());
        Assert.assertEquals("Should have 1 order item", 1, updatedOrder.getOrderItems().size());
        Assert.assertEquals("Item name should match", "Updated Item", updatedOrder.getOrderItems().get(0).getItemName());
    }
    
    @Test
    public void updateOrderWithItemsWithInvalidOrderId() {
        testOrder.setOrderId("99999");
        boolean result = dao.updateOrderWithItems(testOrder);
        Assert.assertFalse("Order update should fail with invalid order ID", result);
    }
    
    @Test
    public void getTotalOrderCount() {
        // Save a test order first
        dao.saveOrder(testOrder);
        
        int totalCount = dao.getTotalOrderCount();
        Assert.assertTrue("Total order count should be greater than 0", totalCount > 0);
    }
    
    @Test
    public void getAllCustomerIdsWithOrders() {
        // Save test orders for different customers
        dao.saveOrder(testOrder);
        
        OrderData anotherOrder = new OrderData();
        anotherOrder.setCustomerId(2);
        anotherOrder.setTableNumber(3);
        anotherOrder.setOrderDate("2024-01-16");
        anotherOrder.setOrderTime("13:30:00");
        anotherOrder.setTotalAmount(75.50);
        anotherOrder.setOrderStatus("PENDING");
        anotherOrder.setOrderItems(new ArrayList<>());
        dao.saveOrder(anotherOrder);
        
        List<Integer> customerIds = dao.getAllCustomerIdsWithOrders();
        Assert.assertNotNull("Customer IDs list should not be null", customerIds);
        Assert.assertTrue("Should have at least one customer ID", customerIds.size() > 0);
        Assert.assertTrue("Should contain test customer ID", customerIds.contains(testCustomerId));
        Assert.assertTrue("Should contain another customer ID", customerIds.contains(2));
    }
    
    @Test
    public void saveOrderSetsGeneratedId() {
        OrderData newOrder = new OrderData();
        newOrder.setCustomerId(5);
        newOrder.setTableNumber(1);
        newOrder.setOrderDate("2024-01-17");
        newOrder.setOrderTime("14:30:00");
        newOrder.setTotalAmount(50.00);
        newOrder.setOrderStatus("PENDING");
        newOrder.setOrderItems(new ArrayList<>());
        
        // Order ID should be null before saving
        Assert.assertNull("Order ID should be null before saving", newOrder.getOrderId());
        
        boolean result = dao.saveOrder(newOrder);
        Assert.assertTrue("Order should be saved successfully", result);
        
        // Order ID should be set after saving
        Assert.assertNotNull("Order ID should be set after saving", newOrder.getOrderId());
        Assert.assertFalse("Order ID should not be empty", newOrder.getOrderId().isEmpty());
        
        // Verify the order can be retrieved by the generated ID
        OrderData retrievedOrder = dao.getOrderById(newOrder.getOrderId());
        Assert.assertNotNull("Order should be retrievable by generated ID", retrievedOrder);
        Assert.assertEquals("Retrieved order should match", newOrder.getOrderId(), retrievedOrder.getOrderId());
    }
    
    @Test
    public void orderItemsContainCorrectOrderId() {
        dao.saveOrder(testOrder);
        String orderId = testOrder.getOrderId();
        
        List<OrderData.OrderItem> items = dao.getOrderItems(orderId);
        Assert.assertNotNull("Order items should not be null", items);
        Assert.assertTrue("Should have order items", items.size() > 0);
        
        for (OrderData.OrderItem item : items) {
            Assert.assertEquals("Order item should have correct order ID", orderId, item.getOrderId());
            Assert.assertTrue("Order item ID should be greater than 0", item.getOrderItemId() > 0);
            Assert.assertTrue("Menu ID should be greater than 0", item.getMenuId() > 0);
        }
    }
}