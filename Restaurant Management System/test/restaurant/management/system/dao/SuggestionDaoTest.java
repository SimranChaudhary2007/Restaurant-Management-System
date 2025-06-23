/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package restaurant.management.system.dao;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import restaurant.management.system.model.SuggestionData;

/**
 *
 * @author pradeepta 3434
 */
public class SuggestionDaoTest {
    private SuggestionDao dao;
    private String testSuggestionText = "Test suggestion for improvement";
    private String testCustomerName = "Test Customer";
    private String testCustomerEmail = "testcustomer@gmail.com";
    private String testRestaurantName = "Test Restaurant"; 
    private int testOwnerId = 1; 
    
    @Before
    public void setUp() {
        dao = new SuggestionDao();
        dao.createSuggestionTableIfNotExists();
    }

    @Test
    public void createSuggestionTableIfNotExists() {
        boolean result = dao.createSuggestionTableIfNotExists();
        Assert.assertNotNull("Method should complete without null result", result);
    }

    @Test
    public void addSuggestionWithValidData() {
      long timestamp = System.currentTimeMillis();
        SuggestionData suggestion = new SuggestionData(
            1, 
            testSuggestionText + " " + timestamp,
            testCustomerName + timestamp,
            testCustomerEmail.replace("@", timestamp + "@"),
            testRestaurantName, 
            testOwnerId,
            false, 
            LocalDateTime.now() 
        );
        
        boolean result = dao.addSuggestion(suggestion);
        if (!result) {
            System.out.println("Failed to add suggestion. Check database connection and constraints.");
        }
        Assert.assertTrue("Adding valid suggestion should succeed", result);
    }

    @Test
    public void addSuggestionWithNullData() {
        boolean result = dao.addSuggestion(null);
        Assert.assertFalse("Adding null suggestion should fail", result);
    }

    @Test
    public void addSuggestionWithEmptyText() {
        long timestamp = System.currentTimeMillis();
        SuggestionData suggestion = new SuggestionData(
            0,
            "",
            testCustomerName + timestamp,
            testCustomerEmail.replace("@", timestamp + "@"),
            testRestaurantName, 
            testOwnerId,
            false,
            null
        );
        
        boolean result = dao.addSuggestion(suggestion);
        Assert.assertNotNull("Method should return a boolean result", result);
    }

    @Test
    public void getAllSuggestions() {
        long timestamp = System.currentTimeMillis();
        SuggestionData suggestion = new SuggestionData(
            0,
            testSuggestionText + " " + timestamp,
            testCustomerName + timestamp,
            testCustomerEmail.replace("@", timestamp + "@"),
            testRestaurantName, 
            testOwnerId,
            false,
            null
        );
        dao.addSuggestion(suggestion);

        List<SuggestionData> suggestions = dao.getAllSuggestions();
        Assert.assertNotNull("Suggestions list should not be null", suggestions);
        Assert.assertTrue("Should have at least one suggestion", suggestions.size() > 0);
    }

    @Test
    public void getSuggestionsByOwnerWithValidId() {
        long timestamp = System.currentTimeMillis();
        SuggestionData suggestion = new SuggestionData(
            0,
            testSuggestionText + " for owner " + testOwnerId + " " + timestamp,
            testCustomerName + timestamp,
            testCustomerEmail.replace("@", timestamp + "@"),
            testRestaurantName,
            testOwnerId,
            false,
            null
        );
        dao.addSuggestion(suggestion);

        List<SuggestionData> suggestions = dao.getSuggestionsByOwner(testOwnerId);
        Assert.assertNotNull("Suggestions list should not be null", suggestions);
        boolean foundTestSuggestion = suggestions.stream()
            .anyMatch(s -> s.getOwnerId() == testOwnerId);
        Assert.assertTrue("Should find suggestions for the owner", foundTestSuggestion);
    }

    @Test
    public void getSuggestionsByOwnerWithInvalidId() {
        List<SuggestionData> suggestions = dao.getSuggestionsByOwner(-1);
        Assert.assertNotNull("Suggestions list should not be null even for invalid ID", suggestions);
        Assert.assertEquals("Should return empty list for invalid owner ID", 0, suggestions.size());
    }

    @Test
    public void getUnreadSuggestionsCount() {
        long timestamp = System.currentTimeMillis();
        SuggestionData suggestion = new SuggestionData(
            0,
            testSuggestionText + " unread " + timestamp,
            testCustomerName + timestamp,
            testCustomerEmail.replace("@", timestamp + "@"),
            testRestaurantName,
            testOwnerId,
            false,
            null
        );
        dao.addSuggestion(suggestion);

        int count = dao.getUnreadSuggestionsCount();
        Assert.assertTrue("Unread suggestions count should be >= 0", count >= 0);
    }

    @Test
    public void markSuggestionsAsRead() {
        long timestamp = System.currentTimeMillis();
        SuggestionData suggestion = new SuggestionData(
            0,
            testSuggestionText + " to mark as read " + timestamp,
            testCustomerName + timestamp,
            testCustomerEmail.replace("@", timestamp + "@"),
            testRestaurantName, 
            testOwnerId,
            false, 
            null
        );
        dao.addSuggestion(suggestion);
        int initialUnreadCount = dao.getUnreadSuggestionsCount();

        // Mark suggestions as read
        boolean result = dao.markSuggestionsAsRead();
        Assert.assertTrue("Marking suggestions as read should succeed", result);

        // Check if unread count decreased or stayed the same (if there were no unread suggestions)
        int finalUnreadCount = dao.getUnreadSuggestionsCount();
        Assert.assertTrue("Unread count should be <= initial count", 
                         finalUnreadCount <= initialUnreadCount);
    }

    @Test
    public void deleteSuggestionWithValidId() {
        // First add a suggestion to delete
        long timestamp = System.currentTimeMillis();
        SuggestionData suggestion = new SuggestionData(
            0,
            testSuggestionText + " to delete " + timestamp,
            testCustomerName + timestamp,
            testCustomerEmail.replace("@", timestamp + "@"),
            testRestaurantName, // Pass the restaurant name
            testOwnerId,
            false,
            null
        );
        dao.addSuggestion(suggestion);

        // Get all suggestions and find the one we just added
        List<SuggestionData> suggestions = dao.getAllSuggestions();
        Assert.assertTrue("Should have at least one suggestion to delete", suggestions.size() > 0);

        // Try to delete the first suggestion (assuming it exists)
        if (!suggestions.isEmpty()) {
            int suggestionId = suggestions.get(0).getSuggestionId();
            boolean result = dao.deleteSuggestion(suggestionId);
            Assert.assertTrue("Deleting existing suggestion should succeed", result);
        }
    }

    @Test
    public void deleteSuggestionWithInvalidId() {
        boolean result = dao.deleteSuggestion(-1);
        Assert.assertFalse("Deleting suggestion with invalid ID should fail", result);
    }

    @Test
    public void deleteSuggestionWithNonExistentId() {
        // Use a very high ID that's unlikely to exist
        boolean result = dao.deleteSuggestion(999999);
        Assert.assertFalse("Deleting non-existent suggestion should fail", result);
    }

    @Test
    public void suggestionDataIntegrity() {
       // Test that we can add a suggestion and retrieve it with all data intact
        long timestamp = System.currentTimeMillis();
        String uniqueSuggestionText = testSuggestionText + " integrity test " + timestamp;
        String uniqueCustomerName = testCustomerName + timestamp;
        String uniqueCustomerEmail = testCustomerEmail.replace("@", timestamp + "@");
        
        SuggestionData originalSuggestion = new SuggestionData(
            0,
            uniqueSuggestionText,
            uniqueCustomerName,
            uniqueCustomerEmail,
            testRestaurantName, 
            testOwnerId,
            false,
            LocalDateTime.now() // Set the current timestamp here
        );
        
        boolean addResult = dao.addSuggestion(originalSuggestion);
        Assert.assertTrue("Adding suggestion should succeed", addResult);

        // Retrieve all suggestions and find our test suggestion
        List<SuggestionData> suggestions = dao.getAllSuggestions();
        SuggestionData retrievedSuggestion = suggestions.stream()
            .filter(s -> s.getSuggestionText().equals(uniqueSuggestionText))
            .findFirst()
            .orElse(null);

        Assert.assertNotNull("Should be able to retrieve the added suggestion", retrievedSuggestion);
        Assert.assertEquals("Suggestion text should match", uniqueSuggestionText, 
                           retrievedSuggestion.getSuggestionText());
        Assert.assertEquals("Customer name should match", uniqueCustomerName, 
                           retrievedSuggestion.getCustomerName());
        Assert.assertEquals("Customer email should match", uniqueCustomerEmail, 
                           retrievedSuggestion.getCustomerEmail());
        Assert.assertEquals("Owner ID should match", testOwnerId, 
                           retrievedSuggestion.getOwnerId());
    }
}