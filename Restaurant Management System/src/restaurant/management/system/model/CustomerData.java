/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant.management.system.model;

/**
 *
 * @author acer
 */
public class CustomerData {
    private int id;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private byte[] profilePicture;
    
    // Add this constructor to your CustomerData class

    public CustomerData(String fullName, String address, String phoneNumber, String email, String username, String password){
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    public CustomerData(String fullName, String address, String phoneNumber, String email){
        this.fullName  = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
    
    public CustomerData(int id, String fullName, String address, String phoneNumber, String email, String username, String password){
        this.id = id;
        this.fullName  = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    //setter
    public void setId(int id){
        this.id = id;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public void setAddress(String address){
        this.address = address;
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
    
    //getter
    public int getId(){
        return id;
    }
    public String getFullName(){
        return fullName;
    }
    public String getAddress(){
        return address;
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
}
