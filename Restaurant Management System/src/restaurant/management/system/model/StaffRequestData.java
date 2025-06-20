/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.model;

import java.sql.Timestamp;
/**
 *
 * @author acer
 */
public class StaffRequestData {
    private int requestId;
    private String fullName;
    private String restaurantName;
    private String phoneNumber;
    private String email;
    private String status;
    private Timestamp requestDate;
    private Timestamp processedDate;
    private String processedBy;
    
    public StaffRequestData() {
   }
    
    public StaffRequestData(String fullName, String restaurantName, String phoneNumber, String email) {
        this.fullName = fullName;
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = "PENDING";
        this.requestDate = new Timestamp(System.currentTimeMillis());
    }
    
    public StaffRequestData(int requestId, String fullName, String restaurantName, 
                              String phoneNumber, String email, String status, 
                              Timestamp requestDate, Timestamp processedDate, String processedBy) {
        this.requestId = requestId;
        this.fullName = fullName;
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = status;
        this.requestDate = requestDate;
        this.processedDate = processedDate;
        this.processedBy = processedBy;
    }
    
    //setter
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }
    public void setProcessedDate(Timestamp processedDate) {
        this.processedDate = processedDate;
    }
    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }
    
    //getters
    public int getRequestId() {
        return requestId;
    }
    public String getFullName() {
        return fullName;
    }
    public String getRestaurantName() {
        return restaurantName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public String getStatus() {
        return status;
    }
    public Timestamp getRequestDate() {
        return requestDate;
    }
    public Timestamp getProcessedDate() {
        return processedDate;
    }
    public String getProcessedBy() {
        return processedBy;
    }
}

