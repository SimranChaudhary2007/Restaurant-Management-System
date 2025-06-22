/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.controller;

import restaurant.management.system.dao.OrderDao;
import restaurant.management.system.model.OrderData;
import restaurant.management.system.view.CustomerOrderView;
import java.util.List;

/**
 *
 * @author acer
 */
public class CustomerOrderController {
    private CustomerOrderView customerOrderView;
    private int currentCustomerId;
    private OrderDao orderDao;

    public CustomerOrderController(CustomerOrderView view, int customerId) {
        this.customerOrderView = view;
        this.currentCustomerId = customerId;
        this.orderDao = new OrderDao();
        
        // Add debugging
        System.out.println("CustomerOrderController initialized with customerId: " + customerId);
        loadAndDisplayOrders();
    }

    private void loadAndDisplayOrders() {
        try {
            System.out.println("Loading orders for customer: " + currentCustomerId);
            List<OrderData> orders = orderDao.getOrdersByCustomer(currentCustomerId);
            
            // Debug output
            if (orders == null) {
                System.out.println("ERROR: OrderDao returned null");
                return;
            }
            
            System.out.println("Found " + orders.size() + " orders");
            for (OrderData order : orders) {
                System.out.println("Order: " + order.getOrderId() + ", Table: " + order.getTableNumber());
            }
            
            customerOrderView.displayOrders(orders);
            System.out.println("Orders sent to view for display");
            
        } catch (Exception e) {
            System.err.println("Error loading orders: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void refreshOrders() {
        loadAndDisplayOrders();
    }

    public void open() {
        customerOrderView.setVisible(true);
    }

    public void close() {
        customerOrderView.dispose();
    }
}