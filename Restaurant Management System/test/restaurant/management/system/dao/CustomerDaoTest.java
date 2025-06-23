/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package restaurant.management.system.dao;

import restaurant.management.system.model.LoginRequest;
import restaurant.management.system.model.CustomerData;
import restaurant.management.system.model.RestaurantData;
import java.util.List;
import org.junit.*;

/**
 *
 * @author samee
 */
public class CustomerDaoTest {
    String correctEmail = "testcustomer@gmail.com";
    String correctFullName = "Test Customer";
    String correctPhoneNumber = "9876543210";
    String correctAddress = "Test Address";
    String correctUsername = "testcustomer";
    String password = "passwordfortest";
    CustomerDao dao = new CustomerDao();

   @Test
public void registerWithNewDetails() {
    long timestamp = System.currentTimeMillis(); // Ensures uniqueness
    String uniqueEmail = "test" + timestamp + "@example.com";
    String uniqueUsername = "user" + timestamp;

    CustomerData customer = new CustomerData("Test User", "Test Address", "9876543210", uniqueEmail, uniqueUsername, "password123");


    boolean result = dao.register(customer);
    Assert.assertTrue("Register should work with unique details", result);
}


    @Test
    public void registerWithDuplicateDetails() {
        CustomerData customer = new CustomerData(correctFullName, correctAddress, correctPhoneNumber, 
                                                correctEmail, correctUsername, password);
        boolean result = dao.register(customer);
        Assert.assertFalse("Register should fail with duplicate credentials", result);
    }

@Test
public void loginWithCorrectCreds() {
    // Use unique data to avoid conflicts
    long timestamp = System.currentTimeMillis();
    String uniqueEmail = "login" + timestamp + "@test.com";
    String uniqueUsername = "loginUser" + timestamp;

    // Register the customer first
    CustomerData customerToRegister = new CustomerData("Login User", "Login Address", "9876543210", uniqueEmail, uniqueUsername, "password123");

    boolean isRegistered = dao.register(customerToRegister);
    Assert.assertTrue("Customer should be registered before login", isRegistered);

    // Attempt login
    LoginRequest req = new LoginRequest(uniqueEmail, "password123");
    CustomerData loggedInCustomer = dao.login(req);

    Assert.assertNotNull("Customer should not be null", loggedInCustomer);
    Assert.assertEquals("Full name should match", "Login User", loggedInCustomer.getFullName());
    Assert.assertEquals("Email should match", uniqueEmail, loggedInCustomer.getEmail());
    Assert.assertEquals("Password should match", "password123", loggedInCustomer.getPassword());
}


    @Test
    public void loginWithInvalidCreds() {
        LoginRequest req = new LoginRequest("invalid@test.com", "wrongpassword");
        CustomerData customer = dao.login(req);
        Assert.assertNull("Customer should be null for invalid credentials", customer);
    }

    @Test
public void updateProfilePictureWithValidData() {
    // Generate unique values to avoid collisions
    long timestamp = System.currentTimeMillis();
    String email = "profile" + timestamp + "@test.com";
    String username = "profileUser" + timestamp;
    String password = "password123";

    // Register the customer
    CustomerData customer = new CustomerData("Profile Pic User", "Some Address", "9876543210", email, username, password);
    boolean isRegistered = dao.register(customer);
    Assert.assertTrue("Customer should be registered first", isRegistered);

    // Login to get the customer ID
    LoginRequest req = new LoginRequest(email, password);
    CustomerData loggedInCustomer = dao.login(req);
    Assert.assertNotNull("Customer should exist for profile picture test", loggedInCustomer);

    // Upload profile picture
    byte[] testImage = "test image data".getBytes();
    boolean result = dao.updateProfilePicture(loggedInCustomer.getId(), testImage);
    Assert.assertTrue("Profile picture update should succeed", result);
}


    @Test
    public void updateProfilePictureWithInvalidId() {
        byte[] testImage = "test image data".getBytes();
        boolean result = dao.updateProfilePicture(-1, testImage);
        Assert.assertFalse("Profile picture update should fail with invalid ID", result);
    }

    @Test
public void getProfilePictureWithValidId() {
    // Generate unique test data
    long timestamp = System.currentTimeMillis();
    String email = "profileget" + timestamp + "@test.com";
    String username = "getProfileUser" + timestamp;
    String password = "test123";

    // Step 1: Register the customer
    CustomerData customer = new CustomerData("Get Profile User", "Sample Address", "9876543210", email, username, password);

    boolean isRegistered = dao.register(customer);
    Assert.assertTrue("Customer should be registered", isRegistered);

    // Step 2: Login to get the customer ID
    LoginRequest req = new LoginRequest(email, password);
    CustomerData loggedInCustomer = dao.login(req);
    Assert.assertNotNull("Customer should exist", loggedInCustomer);

    // Step 3: Upload a profile picture
    byte[] testImage = "test image data".getBytes();
    boolean uploadResult = dao.updateProfilePicture(loggedInCustomer.getId(), testImage);
    Assert.assertTrue("Profile picture upload should succeed", uploadResult);

    // Step 4: Retrieve and verify the image
    byte[] retrievedImage = dao.getProfilePicture(loggedInCustomer.getId());
    Assert.assertNotNull("Retrieved profile picture should not be null", retrievedImage);
    Assert.assertArrayEquals("Retrieved image should match uploaded image", testImage, retrievedImage);
}


    @Test
    public void getProfilePictureWithInvalidId() {
        byte[] retrievedImage = dao.getProfilePicture(-1);
        Assert.assertNull("Profile picture should be null for invalid ID", retrievedImage);
    }

   @Test
public void getCustomerByIdWithValidId() {
    // Step 1: Register a unique test customer
    long timestamp = System.currentTimeMillis();
    String email = "getcustomer" + timestamp + "@test.com";
    String username = "getcustomeruser" + timestamp;
    String password = "test123";

    CustomerData customer = new CustomerData("Original Test Customer", "Test Address", "9876543210", email, username, password);
    boolean registered = dao.register(customer);
    Assert.assertTrue("Customer should be registered successfully", registered);

    // Step 2: Login to get the customer with ID
    LoginRequest req = new LoginRequest(email, password);
    CustomerData originalCustomer = dao.login(req);
    Assert.assertNotNull("Original customer should exist", originalCustomer);

    // Step 3: Fetch by ID
    CustomerData retrievedCustomer = dao.getCustomerById(originalCustomer.getId());
    Assert.assertNotNull("Retrieved customer should not be null", retrievedCustomer);
    Assert.assertEquals("Customer IDs should match", originalCustomer.getId(), retrievedCustomer.getId());
    Assert.assertEquals("Full names should match", originalCustomer.getFullName(), retrievedCustomer.getFullName());
    Assert.assertEquals("Emails should match", originalCustomer.getEmail(), retrievedCustomer.getEmail());
}

    @Test
    public void getCustomerByIdWithInvalidId() {
        CustomerData customer = dao.getCustomerById(-1);
        Assert.assertNull("Customer should be null for invalid ID", customer);
    }



@Test
public void updateCustomerProfileWithValidData() {
    // Step 1: Generate unique data using timestamp
    long timestamp = System.currentTimeMillis();
    String uniqueEmail = "user" + timestamp + "@gmail.com";
    String uniqueUsername = "user" + timestamp;

    // Step 2: Register customer
    CustomerData customer = new CustomerData(
        "Original Name",
        "Original Address",
        "9999999999",
        uniqueEmail,
        uniqueUsername,
        "originalpassword"
    );
    boolean registered = dao.register(customer);
    Assert.assertTrue("Customer should be registered", registered);

    // Step 3: Login to get customer ID
    LoginRequest req = new LoginRequest(uniqueEmail, "originalpassword");
    CustomerData loggedInCustomer = dao.login(req);
    Assert.assertNotNull("Customer should exist", loggedInCustomer);

    // Step 4: Update profile
    String newFullName = "Updated Customer Name";
    String newPhoneNumber = "1234567890";
    String newEmail = "updated" + timestamp + "@gmail.com";  // new unique email
    String newAddress = "Updated Address";

    boolean result = dao.updateCustomerProfile(
        loggedInCustomer.getId(),
        newFullName,
        newAddress,       // ⚠ Make sure order matches DAO method
        newPhoneNumber,
        newEmail
    );
    Assert.assertTrue("Customer profile update should succeed", result);

    // Step 5: Verify update
    CustomerData updatedCustomer = dao.getCustomerById(loggedInCustomer.getId());
    Assert.assertNotNull("Updated customer should exist", updatedCustomer);
    Assert.assertEquals("Full name should be updated", newFullName, updatedCustomer.getFullName());
    Assert.assertEquals("Phone number should be updated", newPhoneNumber, updatedCustomer.getPhoneNumber());
    Assert.assertEquals("Email should be updated", newEmail, updatedCustomer.getEmail());
    Assert.assertEquals("Address should be updated", newAddress, updatedCustomer.getAddress());
}



    @Test
    public void updateCustomerProfileWithInvalidId() {
        boolean result = dao.updateCustomerProfile(
            -1,
            "Test Name",
            "1234567890",
            "test@test.com",
            "Test Address"
        );
        Assert.assertFalse("Customer profile update should fail with invalid ID", result);
    }

    @Test
public void checkEmailWithValidEmail() {
    // Create unique email/username for each test run
    long timestamp = System.currentTimeMillis();
    String email = "user" + timestamp + "@gmail.com";
    String username = "user" + timestamp;

    // Register the customer
    CustomerData customer = new CustomerData("Test User", "Test Address", "9876543210", email, username, "password123");

    boolean registered = dao.register(customer);
    Assert.assertTrue("Customer should be registered first", registered);

    // Now check if the email is found
    CustomerData foundCustomer = dao.checkEmail(email);
    Assert.assertNotNull("Customer should be found with valid email", foundCustomer);
    Assert.assertEquals("Email should match", email, foundCustomer.getEmail());
}


    @Test
    public void checkEmailWithInvalidEmail() {
        CustomerData foundCustomer = dao.checkEmail("nonexistent@test.com");
        Assert.assertNull("Customer should be null for invalid email", foundCustomer);
    }

   @Test
public void isEmailRegisteredWithExistingEmail() {
    // Use a unique email to avoid duplication
    long timestamp = System.currentTimeMillis();
    String email = "test" + timestamp + "@gmail.com";
    String username = "user" + timestamp;

    // First register the customer
    CustomerData customer = new CustomerData("Test User", "Test Address", "9876543210", email, username, "password123");


    boolean registered = dao.register(customer);
    Assert.assertTrue("Customer should be registered first", registered);

    // Now check if the email is registered
    boolean isRegistered = dao.isEmailRegistered(email);
    Assert.assertTrue("Email should be registered", isRegistered);
}


    @Test
    public void isEmailRegisteredWithNonExistingEmail() {
        boolean isRegistered = dao.isEmailRegistered("nonexistent@test.com");
        Assert.assertFalse("Email should not be registered", isRegistered);
    }
}
