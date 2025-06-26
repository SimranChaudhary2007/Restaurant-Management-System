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
    private String username;
    private String password;
    private int ownerId;
    private String requestType;
    private String requestDescription;
    
    public StaffRequestData() {
   }
    
     public StaffRequestData(String fullName, String restaurantName, String phoneNumber, String email, String username, String password, int ownerId) {
        this.fullName = fullName;
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.ownerId = ownerId;
        this.status = "PENDING";
        this.requestDate = new Timestamp(System.currentTimeMillis());
    }
     
    public StaffRequestData(String fullName, String restaurantName, String phoneNumber, String email, String username, String password, int ownerId, String requestType, String requestDescription) {
        this.fullName = fullName;
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.ownerId = ownerId;
        this.status = "PENDING";
        this.requestDate = new Timestamp(System.currentTimeMillis());
        this.requestType = requestType;
        this.requestDescription = requestDescription;
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
    public void setUsername(String username) { 
        this.username = username; 
    }
    public void setPassword(String password) { 
        this.password = password; 
    }
    public void setOwnerId(int ownerId) { 
        this.ownerId = ownerId; 
    }
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
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
    public String getUsername() { 
        return username; 
    }
    public String getPassword() { 
        return password; 
    }
    public int getOwnerId() { 
        return ownerId; 
    }
    public String getRequestType() {
        return requestType;
    }
    public String getRequestDescription() {
        return requestDescription;
    }
}

