/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package restaurant.management.system.dao;

import restaurant.management.system.model.LoginRequest;
import restaurant.management.system.model.OwnerData;
import restaurant.management.system.model.RestaurantData;
import java.util.List;
import org.junit.*;

/**
 *
 * @author pradeepta
 */
public class OwnerDaoTest {
    String correctEmail = "testowner@gmail.com";
    String correctFullName = "Test Owner";
    String correctRestaurantName = "Test Restaurant";
    String correctPhoneNumber = "9876543210";
    String correctAddress = "Test Address";
    String correctUsername = "testowner";
    String password = "passwordfortest";
    OwnerDao dao = new OwnerDao();

    @Test
    public void registerWithNewDetails() {
        OwnerData owner = new OwnerData(correctFullName, correctRestaurantName, correctPhoneNumber, correctAddress, correctEmail, correctUsername, password);
        boolean result = dao.register(owner);
        Assert.assertTrue("Register should work with unique details",result);
    }

    @Test
    public void registerWithDuplicateDetails() {
        OwnerData owner = new OwnerData(correctFullName, correctRestaurantName, correctPhoneNumber, correctAddress, correctEmail, correctUsername, password);
        boolean result = dao.register(owner);
        Assert.assertFalse("Register should fail with duplicate credentials",result);
    }

    @Test
    public void loginWithCorrectCreds() {
        LoginRequest req = new LoginRequest(correctEmail, password);
        OwnerData owner = dao.login(req);
        Assert.assertNotNull("Owner should not be null",owner);
        Assert.assertEquals("Full name should match",correctFullName, owner.getFullName() );
        Assert.assertEquals("Email should match",correctEmail, owner.getEmail());
        Assert.assertEquals("Restaurant name should match",correctRestaurantName, owner.getRestaurantName());
        Assert.assertEquals("Password should match",password, owner.getPassword());
    }

    @Test
    public void loginWithInvalidCreds() {
        LoginRequest req = new LoginRequest("invalid@test.com", "wrongpassword");
        OwnerData owner = dao.login(req);
        Assert.assertNull("Owner should be null for invalid credentials",owner);
    }

    @Test
    public void updateProfilePictureWithValidData() {
        LoginRequest req = new LoginRequest(correctEmail, password);
        OwnerData owner = dao.login(req);
        Assert.assertNotNull("Owner should exist for profile picture test",owner);

        byte[] testImage = "test image data".getBytes();
        boolean result = dao.updateProfilePicture(owner.getId(), testImage);
        Assert.assertTrue("Profile picture update should succeed",result);
    }

    @Test
    public void updateProfilePictureWithInvalidId() {
        byte[] testImage = "test image data".getBytes();
        boolean result = dao.updateProfilePicture(-1, testImage);
        Assert.assertFalse("Profile picture update should fail with invalid ID",result);
    }

    @Test
    public void getProfilePictureWithValidId() {
        LoginRequest req = new LoginRequest(correctEmail, password);
        OwnerData owner = dao.login(req);
        Assert.assertNotNull("Owner should exist",owner);

        byte[] testImage = "test image data".getBytes();
        dao.updateProfilePicture(owner.getId(), testImage);

        byte[] retrievedImage = dao.getProfilePicture(owner.getId());
        Assert.assertNotNull("Retrieved profile picture should not be null",retrievedImage);
        Assert.assertArrayEquals("Retrieved image should match uploaded image",testImage, retrievedImage);
    }

    @Test
    public void getProfilePictureWithInvalidId() {
        byte[] retrievedImage = dao.getProfilePicture(-1);
        Assert.assertNull("Profile picture should be null for invalid ID",retrievedImage);
    }

    @Test
    public void updateRestaurantPictureWithValidData() {
        LoginRequest req = new LoginRequest(correctEmail, password);
        OwnerData owner = dao.login(req);
        Assert.assertNotNull("Owner should exist for restaurant picture test",owner);

        byte[] testRestaurantImage = "test restaurant image data".getBytes();
        boolean result = dao.updateRestaurantPicture(owner.getId(), testRestaurantImage);
        Assert.assertTrue( "Restaurant picture update should succeed",result);
    }

    @Test
    public void updateRestaurantPictureWithInvalidId() {
        byte[] testRestaurantImage = "test restaurant image data".getBytes();
        boolean result = dao.updateRestaurantPicture(-1, testRestaurantImage);
        Assert.assertFalse("Restaurant picture update should fail with invalid ID",result);
    }

    @Test
    public void getRestaurantPictureWithValidId() {
        LoginRequest req = new LoginRequest(correctEmail, password);
        OwnerData owner = dao.login(req);
        Assert.assertNotNull( "Owner should exist",owner);

        byte[] testRestaurantImage = "test restaurant image data".getBytes();
        dao.updateRestaurantPicture(owner.getId(), testRestaurantImage);

        byte[] retrievedImage = dao.getRestaurantPicture(owner.getId());
        Assert.assertNotNull("Retrieved restaurant picture should not be null",retrievedImage);
        Assert.assertArrayEquals("Retrieved restaurant image should match uploaded image",testRestaurantImage, retrievedImage);
    }

    @Test
    public void getRestaurantPictureWithInvalidId() {
        byte[] retrievedImage = dao.getRestaurantPicture(-1);
        Assert.assertNull("Restaurant picture should be null for invalid ID",retrievedImage);
    }

    @Test
    public void getOwnerByIdWithValidId() {
        LoginRequest req = new LoginRequest(correctEmail, password);
        OwnerData originalOwner = dao.login(req);
        Assert.assertNotNull( "Original owner should exist",originalOwner);

        OwnerData retrievedOwner = dao.getOwnerById(originalOwner.getId());
        Assert.assertNotNull("Retrieved owner should not be null",retrievedOwner);
        Assert.assertEquals("Owner IDs should match",originalOwner.getId(), retrievedOwner.getId());
        Assert.assertEquals("Full names should match",originalOwner.getFullName(), retrievedOwner.getFullName());
        Assert.assertEquals("Emails should match",originalOwner.getEmail(), retrievedOwner.getEmail());
    }

    @Test
    public void getOwnerByIdWithInvalidId() {
        OwnerData owner = dao.getOwnerById(-1);
        Assert.assertNull("Owner should be null for invalid ID",owner);
    }

    @Test
    public void getAllRestaurantsWithImages() {
        LoginRequest req = new LoginRequest(correctEmail, password);
        OwnerData owner = dao.login(req);
        Assert.assertNotNull("Owner should exist",owner);

        byte[] testRestaurantImage = "test restaurant image data".getBytes();
        dao.updateRestaurantPicture(owner.getId(), testRestaurantImage);

        List<RestaurantData> restaurants = dao.getAllRestaurantsWithImages();
        Assert.assertNotNull("Restaurants list should not be null",restaurants);
        Assert.assertTrue("Should have at least one restaurant with image",restaurants.size() > 0);

        RestaurantData testRestaurant = restaurants.stream()
                .filter(r -> r.getEmail().equals(correctEmail))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull("Test restaurant should be found in the list",testRestaurant);
        Assert.assertEquals("Restaurant name should match",correctRestaurantName, testRestaurant.getRestaurantName());
    }

    @Test
    public void getRestaurantByOwnerIdWithValidId() {
        LoginRequest req = new LoginRequest(correctEmail, password);
        OwnerData owner = dao.login(req);
        Assert.assertNotNull("Owner should exist",owner);

        byte[] testRestaurantImage = "test restaurant image data".getBytes();
        dao.updateRestaurantPicture(owner.getId(), testRestaurantImage);

        RestaurantData restaurant = dao.getRestaurantByOwnerId(owner.getId());
        Assert.assertNotNull("Restaurant should not be null",restaurant);
        Assert.assertEquals("Owner IDs should match",owner.getId(), restaurant.getOwnerId());
        Assert.assertEquals("Restaurant names should match",correctRestaurantName, restaurant.getRestaurantName());
        Assert.assertNotNull("Restaurant image should not be null",restaurant.getRestaurantImage());
    }

    @Test
    public void getRestaurantByOwnerIdWithInvalidId() {
        RestaurantData restaurant = dao.getRestaurantByOwnerId(-1);
        Assert.assertNull("Restaurant should be null for invalid owner ID",restaurant);
    }

    @Test
    public void updateOwnerProfileWithValidData() {
        LoginRequest req = new LoginRequest(correctEmail, password);
        OwnerData owner = dao.login(req);
        Assert.assertNotNull("Owner should exist",owner);

        String newFullName = "Updated Owner Name";
        String newRestaurantName = "Updated Restaurant Name";
        String newPhoneNumber = "1234567890";
        String newEmail = "updated@gmail.com";
        String newAddress = "Updated Address";

        boolean result = dao.updateOwnerProfile(owner.getId(), newFullName, newRestaurantName, 
                                              newPhoneNumber, newEmail, newAddress);
        Assert.assertTrue("Owner profile update should succeed",result);

        OwnerData updatedOwner = dao.getOwnerById(owner.getId());
        Assert.assertNotNull("Updated owner should exist",updatedOwner);
        Assert.assertEquals("Full name should be updated",newFullName, updatedOwner.getFullName());
        Assert.assertEquals("Restaurant name should be updated",newRestaurantName, updatedOwner.getRestaurantName() );
        Assert.assertEquals("Phone number should be updated",newPhoneNumber, updatedOwner.getPhoneNumber());
    }

    @Test
    public void updateOwnerProfileWithInvalidId() {
        boolean result = dao.updateOwnerProfile(
            -1,
            "Test Name",
            "Test Restaurant",
            "1234567890",
            "test@test.com",
            "Test Address"
        );
        Assert.assertFalse("Owner profile update should fail with invalid ID",result);
    }
}
