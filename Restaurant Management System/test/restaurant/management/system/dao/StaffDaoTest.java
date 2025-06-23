/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package restaurant.management.system.dao;

import restaurant.management.system.model.LoginRequest;
import restaurant.management.system.model.StaffData;

import org.junit.*;

/**
 *
 * @author pradeepta
 */
    public class StaffDaoTest {
        private String correctEmail = "teststaff@gmail.com";
        private String correctFullName = "Test Staff";
        private String correctRestaurantName = "Test Restaurant";
        private String correctPhoneNumber = "9876543210";
        private String correctUsername = "teststaff";
        private String password = "passwordfortest";
        private StaffDao dao;
        private static int testCounter = 0;

    @Before
    public void setUp() {
        dao = new StaffDao();
        testCounter++;
        correctEmail = "teststaff" + testCounter + "@gmail.com";
        correctUsername = "teststaff" + testCounter;
    }
    
    @After
    public void tearDown() {
        dao = null;
    }

    // Helper method to create staff with proper owner_id
    private StaffData createTestStaff(String fullName, String restaurantName, String phoneNumber, String email, String username, String password) {
        StaffData staff = new StaffData(fullName, restaurantName, phoneNumber, email, username, password);
        staff.setOwnerId(0); // Set to 0 or null to avoid FK constraint during testing
        return staff;
    }

    @Test
    public void testRegisterWithNewDetails() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean result = dao.register(staff);
        Assert.assertTrue("Register should work with unique details", result);
    }

    @Test
    public void testRegisterWithDuplicateDetails() {
        StaffData staff1 = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean result1 = dao.register(staff1);
        Assert.assertTrue("First registration should succeed", result1);
        
        StaffData staff2 = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean result2 = dao.register(staff2);
        Assert.assertFalse("Register should fail with duplicate credentials", result2);
    }

    @Test
    public void testRegisterWithNullData() {
        boolean result = dao.register(null);
        Assert.assertFalse("Register should fail with null data", result);
    }

    @Test
    public void testRegisterWithEmptyFullName() {
        StaffData staff = createTestStaff("", correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean result = dao.register(staff);
        Assert.assertFalse("Register should fail with empty full name", result);
    }

    @Test
    public void testRegisterWithNullFullName() {
        StaffData staff = createTestStaff(null, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean result = dao.register(staff);
        Assert.assertFalse("Register should fail with null full name", result);
    }

    @Test
    public void testRegisterWithEmptyEmail() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, "", correctUsername, password);
        boolean result = dao.register(staff);
        Assert.assertFalse("Register should fail with empty email", result);
    }

    @Test
    public void testRegisterWithNullEmail() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, null, correctUsername, password);
        boolean result = dao.register(staff);
        Assert.assertFalse("Register should fail with null email", result);
    }

    @Test
    public void testRegisterWithEmptyPassword() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, "");
        boolean result = dao.register(staff);
        Assert.assertFalse("Register should fail with empty password", result);
    }

    @Test
    public void testRegisterWithNullPassword() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, null);
        boolean result = dao.register(staff);
        Assert.assertFalse("Register should fail with null password", result);
    }

    @Test
    public void testRegisterWithEmptyUsername() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, "", password);
        boolean result = dao.register(staff);
        Assert.assertFalse("Register should fail with empty username", result);
    }

    @Test
    public void testRegisterWithNullUsername() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, null, password);
        boolean result = dao.register(staff);
        Assert.assertFalse("Register should fail with null username", result);
    }

    @Test
    public void testLoginWithCorrectCreds() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean registerResult = dao.register(staff);
        Assert.assertTrue("Registration should succeed before login test", registerResult);
        
        LoginRequest req = new LoginRequest(correctEmail, password);
        StaffData loggedInStaff = dao.login(req);
        Assert.assertNotNull("Staff should not be null", loggedInStaff);
        Assert.assertEquals("Full name should match", correctFullName, loggedInStaff.getFullName());
        Assert.assertEquals("Email should match", correctEmail, loggedInStaff.getEmail());
        Assert.assertEquals("Restaurant name should match", correctRestaurantName, loggedInStaff.getRestaurantName());
    }

    @Test
    public void testLoginWithInvalidCreds() {
        LoginRequest req = new LoginRequest("invalid@test.com", "wrongpassword");
        StaffData staff = dao.login(req);
        Assert.assertNull("Staff should be null for invalid credentials", staff);
    }

    @Test
    public void testLoginWithNullData() {
        StaffData staff = dao.login(null);
        Assert.assertNull("Staff should be null for null login request", staff);
    }

    @Test
    public void testLoginWithNullEmail() {
        LoginRequest req = new LoginRequest(null, password);
        StaffData staff = dao.login(req);
        Assert.assertNull("Staff should be null for null email", staff);
    }

    @Test
    public void testLoginWithNullPassword() {
        LoginRequest req = new LoginRequest(correctEmail, null);
        StaffData staff = dao.login(req);
        Assert.assertNull("Staff should be null for null password", staff);
    }

    @Test
    public void testUpdateProfilePictureWithValidData() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean registerResult = dao.register(staff);
        Assert.assertTrue("Registration should succeed", registerResult);
        
        LoginRequest req = new LoginRequest(correctEmail, password);
        StaffData loggedInStaff = dao.login(req);
        Assert.assertNotNull("Staff should exist for profile picture test", loggedInStaff);

        byte[] testImage = "test image data".getBytes();
        boolean result = dao.updateProfilePicture(loggedInStaff.getId(), testImage);
        Assert.assertTrue("Profile picture update should succeed", result);
    }

    @Test
    public void testUpdateProfilePictureWithInvalidId() {
        byte[] testImage = "test image data".getBytes();
        boolean result = dao.updateProfilePicture(-1, testImage);
        Assert.assertFalse("Profile picture update should fail with invalid ID", result);
    }

    @Test
    public void testUpdateProfilePictureWithNullData() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean registerResult = dao.register(staff);
        Assert.assertTrue("Registration should succeed", registerResult);
        
        LoginRequest req = new LoginRequest(correctEmail, password);
        StaffData loggedInStaff = dao.login(req);
        Assert.assertNotNull("Staff should exist", loggedInStaff);

        boolean result = dao.updateProfilePicture(loggedInStaff.getId(), null);
        Assert.assertFalse("Profile picture update should fail with null data", result);
    }

    @Test
    public void testGetProfilePictureWithValidId() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean registerResult = dao.register(staff);
        Assert.assertTrue("Registration should succeed", registerResult);
        
        LoginRequest req = new LoginRequest(correctEmail, password);
        StaffData loggedInStaff = dao.login(req);
        Assert.assertNotNull("Staff should exist", loggedInStaff);

        byte[] testImage = "test image data".getBytes();
        boolean updateResult = dao.updateProfilePicture(loggedInStaff.getId(), testImage);
        Assert.assertTrue("Profile picture update should succeed", updateResult);

        byte[] retrievedImage = dao.getProfilePicture(loggedInStaff.getId());
        Assert.assertNotNull("Retrieved profile picture should not be null", retrievedImage);
        Assert.assertArrayEquals("Retrieved image should match uploaded image", testImage, retrievedImage);
    }

    @Test
    public void testGetProfilePictureWithInvalidId() {
        byte[] retrievedImage = dao.getProfilePicture(-1);
        Assert.assertNull("Profile picture should be null for invalid ID", retrievedImage);
    }

    @Test
    public void testGetStaffByIdWithValidId() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean registerResult = dao.register(staff);
        Assert.assertTrue("Registration should succeed", registerResult);
        
        LoginRequest req = new LoginRequest(correctEmail, password);
        StaffData originalStaff = dao.login(req);
        Assert.assertNotNull("Original staff should exist", originalStaff);

        StaffData retrievedStaff = dao.getStaffById(originalStaff.getId());
        Assert.assertNotNull("Retrieved staff should not be null", retrievedStaff);
        Assert.assertEquals("Staff IDs should match", originalStaff.getId(), retrievedStaff.getId());
        Assert.assertEquals("Full names should match", originalStaff.getFullName(), retrievedStaff.getFullName());
        Assert.assertEquals("Emails should match", originalStaff.getEmail(), retrievedStaff.getEmail());
        Assert.assertEquals("Restaurant names should match", originalStaff.getRestaurantName(), retrievedStaff.getRestaurantName());
    }

    @Test
    public void testGetStaffByIdWithInvalidId() {
        StaffData staff = dao.getStaffById(-1);
        Assert.assertNull("Staff should be null for invalid ID", staff);
    }

    @Test
    public void testUpdateStaffProfileWithValidData() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean registerResult = dao.register(staff);
        Assert.assertTrue("Registration should succeed", registerResult);
        
        LoginRequest req = new LoginRequest(correctEmail, password);
        StaffData loggedInStaff = dao.login(req);
        Assert.assertNotNull("Staff should exist", loggedInStaff);

        String newFullName = "Updated Staff Name";
        String newRestaurantName = "Updated Restaurant Name";
        String newPhoneNumber = "1234567890";
        String newEmail = "updated" + testCounter + "@gmail.com";

        boolean result = dao.updateStaffProfile(loggedInStaff.getId(), newFullName, newRestaurantName, newPhoneNumber, newEmail);
        Assert.assertTrue("Staff profile update should succeed", result);

        StaffData updatedStaff = dao.getStaffById(loggedInStaff.getId());
        Assert.assertNotNull("Updated staff should exist", updatedStaff);
        Assert.assertEquals("Full name should be updated", newFullName, updatedStaff.getFullName());
        Assert.assertEquals("Restaurant name should be updated", newRestaurantName, updatedStaff.getRestaurantName());
        Assert.assertEquals("Phone number should be updated", newPhoneNumber, updatedStaff.getPhoneNumber());
        Assert.assertEquals("Email should be updated", newEmail, updatedStaff.getEmail());
    }

    @Test
    public void testUpdateStaffProfileWithInvalidId() {
        boolean result = dao.updateStaffProfile(
            -1,
            "Test Name",
            "Test Restaurant",
            "1234567890",
            "test@test.com"
        );
        Assert.assertFalse("Staff profile update should fail with invalid ID", result);
    }

    @Test
    public void testUpdateStaffProfileWithNullData() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean registerResult = dao.register(staff);
        Assert.assertTrue("Registration should succeed", registerResult);
        
        LoginRequest req = new LoginRequest(correctEmail, password);
        StaffData loggedInStaff = dao.login(req);
        Assert.assertNotNull("Staff should exist", loggedInStaff);

        boolean result = dao.updateStaffProfile(loggedInStaff.getId(), null, null, null, null);
        Assert.assertFalse("Staff profile update should fail with null data", result);
    }

    @Test
    public void testStaffDataIntegrity() {
        long timestamp = System.currentTimeMillis();
        String uniqueFullName = "Staff Integrity Test " + timestamp;
        String uniqueEmail = "integrity" + timestamp + "@test.com";
        String uniquePhone = "9999" + (timestamp % 1000000);
        String uniqueUsername = "integrity" + timestamp;

        StaffData originalStaff = createTestStaff(
            uniqueFullName,
            correctRestaurantName,
            uniquePhone,
            uniqueEmail,
            uniqueUsername,
            password
        );

        boolean registerResult = dao.register(originalStaff);
        Assert.assertTrue("Registering staff should succeed", registerResult);

        LoginRequest loginRequest = new LoginRequest(uniqueEmail, password);
        StaffData retrievedStaff = dao.login(loginRequest);

        Assert.assertNotNull("Should be able to retrieve the registered staff", retrievedStaff);
        Assert.assertEquals("Email should match", uniqueEmail, retrievedStaff.getEmail());
        Assert.assertEquals("Full name should match", uniqueFullName, retrievedStaff.getFullName());
        Assert.assertEquals("Restaurant name should match", correctRestaurantName, retrievedStaff.getRestaurantName());
        Assert.assertEquals("Phone number should match", uniquePhone, retrievedStaff.getPhoneNumber());
    }

    @Test
    public void testProfilePictureIntegrity() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean registerResult = dao.register(staff);
        Assert.assertTrue("Registration should succeed", registerResult);
        
        LoginRequest req = new LoginRequest(correctEmail, password);
        StaffData loggedInStaff = dao.login(req);
        Assert.assertNotNull("Staff should exist", loggedInStaff);

        byte[] originalPicture = "test image data for integrity".getBytes();

        boolean updateResult = dao.updateProfilePicture(loggedInStaff.getId(), originalPicture);
        Assert.assertTrue("Profile picture update should succeed", updateResult);

        byte[] retrievedPicture = dao.getProfilePicture(loggedInStaff.getId());
        Assert.assertNotNull("Profile picture should be retrievable", retrievedPicture);
        Assert.assertArrayEquals("Profile picture data should match", originalPicture, retrievedPicture);
    }

    @Test
    public void testIsEmailRegistered() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean registerResult = dao.register(staff);
        Assert.assertTrue("Registration should succeed", registerResult);

        boolean isRegistered = dao.isEmailRegistered(correctEmail);
        Assert.assertTrue("Email should be registered", isRegistered);

        boolean isNotRegistered = dao.isEmailRegistered("nonexistent@test.com");
        Assert.assertFalse("Non-existent email should not be registered", isNotRegistered);
    }

    @Test
    public void testIsUsernameRegistered() {
        StaffData staff = createTestStaff(correctFullName, correctRestaurantName, correctPhoneNumber, correctEmail, correctUsername, password);
        boolean registerResult = dao.register(staff);
        Assert.assertTrue("Registration should succeed", registerResult);

        boolean isRegistered = dao.isUsernameRegistered(correctUsername);
        Assert.assertTrue("Username should be registered", isRegistered);

        boolean isNotRegistered = dao.isUsernameRegistered("nonexistentuser");
        Assert.assertFalse("Non-existent username should not be registered", isNotRegistered);
    }
}