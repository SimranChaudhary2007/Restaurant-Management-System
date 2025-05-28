/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.model;

/**
 *
 * @author acer
 */
public class StaffData {
    private String fullName;
    private String restaurantName;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    
    public StaffData(String fullName, String restaurantName,String phoneNumber, String email){
        this.fullName  = fullName;
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
    public StaffData(String fullName, String restaurantName,String phoneNumber, String email, String username, String password){
        this.fullName  = fullName;
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    //setter
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
    public void setusername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    
    //getter
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
}
