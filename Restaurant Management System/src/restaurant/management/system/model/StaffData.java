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
public class StaffData {
    private int id;
    private String fullName;
    private String restaurantName;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private byte[] profilePicture;
    private String accountStatus;
    private Timestamp createdDate;
    private int ownerId;
    private String position;
    private double salary;
    
    public StaffData(String fullName, String restaurantName,String phoneNumber, String email){
        this.fullName  = fullName;
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
    public StaffData(int id, String fullName, String restaurantName,String phoneNumber, String email, String username, String password){
        this.id = id;
        this.fullName  = fullName;
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    public StaffData(int id, String fullName, String restaurantName, String phoneNumber, 
                    String email, String username, String password, String accountStatus, 
                    Timestamp createdDate, int ownerId) {
        this.id = id;
        this.fullName = fullName;
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
        this.accountStatus = accountStatus;
        this.createdDate = createdDate;
        this.ownerId = ownerId;
    }
    
    //setter
    public void setId(int id){
        this.id = id;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public void setRestaurantName(String restaurantName){
        this.restaurantName = restaurantName;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }
    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    //getter
    public int getId(){
        return id;
    }
    public String getFullName(){
        return fullName;
    }
    public String getRestaurantName(){
        return restaurantName;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getEmail(){
        return email;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public byte[] getProfilePicture() {
        return profilePicture;
    }
    public String getAccountStatus() {
        return accountStatus;
    }
    public Timestamp getCreatedDate() {
        return createdDate;
    }
    public int getOwnerId() {
        return ownerId;
    }
    public String getPosition() {
        return position;
    }
    public double getSalary() {
        return salary;
    }
}
