package restaurant.management.system.dao;

import restaurant.management.system.model.ReviewData;
import java.util.List;
import java.sql.*;
import org.junit.*;
import static org.junit.Assert.*;
import restaurant.management.system.database.MySqlConnection;

/**
 * Comprehensive test suite for ReviewDao
 * Tests all CRUD operations and validation logic
 * 
 * @author pradeepta
 */
public class ReviewDaoTest {
    
    // Test data constants
    private static final String VALID_CUSTOMER_NAME = "John Doe";
    private static final String VALID_CUSTOMER_EMAIL = "john.doe@email.com";
    private static final String VALID_CUSTOMER_EMAIL_2 = "jane.smith@email.com";
    private static final int VALID_ITEM_ID = 1;
    private static final int VALID_RATING = 4;
    private static final String VALID_COMMENT = "Great food and excellent service!";
    
    private ReviewDao reviewDao;
    private MySqlConnection mySql;
    private static boolean isSetupComplete = false;
    
    @Before
    public void setUp() {
        reviewDao = new ReviewDao();
        mySql = new MySqlConnection();
        
        // Setup test data only once
        if (!isSetupComplete) {
            setupTestData();
            isSetupComplete = true;
        }
    }
    
    /**
     * Setup test data including menu items that reviews can reference
     */
    private void setupTestData() {
        Connection conn = null;
        try {
            conn = mySql.openConnection();
            
            // Create menu table if not exists (required for foreign key)
            String createMenuTable = "CREATE TABLE IF NOT EXISTS menu ("
                + "item_id INT AUTO_INCREMENT PRIMARY KEY,"
                + "item_name VARCHAR(100) NOT NULL,"
                + "price DECIMAL(10,2) NOT NULL,"
                + "category VARCHAR(50),"
                + "description TEXT,"
                + "is_available BOOLEAN DEFAULT true"
                + ")";
            
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(createMenuTable);
                
                // Insert test menu items
                String insertTestItems = "INSERT IGNORE INTO menu (item_id, item_name, price, category) VALUES "
                    + "(1, 'Test Burger', 12.99, 'Main Course'),"
                    + "(101, 'Test Pizza', 15.99, 'Main Course'),"
                    + "(102, 'Test Pasta', 11.99, 'Main Course'),"
                    + "(103, 'Test Salad', 8.99, 'Appetizer')";
                    
                stmt.executeUpdate(insertTestItems);
            }
            
        } catch (SQLException e) {
            System.err.println("Error setting up test data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                mySql.closeConnection(conn);
            }
        }
    }
    
    /**
     * Clean up test reviews before each test to ensure isolation
     */
    @Before
    public void cleanupReviews() {
        Connection conn = null;
        try {
            conn = mySql.openConnection();
            String deleteReviews = "DELETE FROM reviews WHERE customer_email LIKE '%@email.com'";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(deleteReviews);
            }
        } catch (SQLException e) {
            // Ignore cleanup errors
        } finally {
            if (conn != null) {
                mySql.closeConnection(conn);
            }
        }
    }
    
    // ============= ADD REVIEW TESTS =============
    
    @Test
    public void testAddReviewWithValidData() {
        ReviewData review = createValidReview();
        boolean result = reviewDao.addReview(review);
        assertTrue("Review should be added successfully with valid data", result);
        
        // Verify the review was actually added
        ReviewData addedReview = reviewDao.getCustomerReview(VALID_ITEM_ID, VALID_CUSTOMER_EMAIL);
        assertNotNull("Added review should be retrievable", addedReview);
        assertEquals("Customer name should match", VALID_CUSTOMER_NAME, addedReview.getCustomerName());
        assertEquals("Rating should match", VALID_RATING, addedReview.getRating());
        assertEquals("Comment should match", VALID_COMMENT, addedReview.getComment());
    }
    
    @Test
    public void testAddReviewWithNullCustomerName() {
        ReviewData review = new ReviewData(0, VALID_ITEM_ID, null, VALID_CUSTOMER_EMAIL, 
                                         VALID_RATING, VALID_COMMENT, null, null);
        boolean result = reviewDao.addReview(review);
        assertFalse("Review should fail with null customer name", result);
    }
    
    @Test
    public void testAddReviewWithEmptyCustomerName() {
        ReviewData review = new ReviewData(0, VALID_ITEM_ID, "", VALID_CUSTOMER_EMAIL, VALID_RATING, VALID_COMMENT, null, null);
        boolean result = reviewDao.addReview(review);
        assertFalse("Review should fail with empty customer name", result);
    }
    
    @Test
    public void testAddReviewWithWhitespaceOnlyCustomerName() {
        ReviewData review = new ReviewData(0, VALID_ITEM_ID, "   ", VALID_CUSTOMER_EMAIL, VALID_RATING, VALID_COMMENT, null, null);
        boolean result = reviewDao.addReview(review);
        assertFalse("Review should fail with whitespace-only customer name", result);
    }
    
    @Test
    public void testAddReviewWithNullCustomerEmail() {
        ReviewData review = new ReviewData(0, VALID_ITEM_ID, VALID_CUSTOMER_NAME, null, VALID_RATING, VALID_COMMENT, null, null);
        boolean result = reviewDao.addReview(review);
        assertFalse("Review should fail with null customer email", result);
    }
    
    @Test
    public void testAddReviewWithEmptyCustomerEmail() {
        ReviewData review = new ReviewData(0, VALID_ITEM_ID, VALID_CUSTOMER_NAME, "", 
                                         VALID_RATING, VALID_COMMENT, null, null);
        boolean result = reviewDao.addReview(review);
        assertFalse("Review should fail with empty customer email", result);
    }
    
    @Test
    public void testAddReviewWithInvalidRatingTooLow() {
        ReviewData review = new ReviewData(0, VALID_ITEM_ID, VALID_CUSTOMER_NAME, VALID_CUSTOMER_EMAIL, 
                                         0, VALID_COMMENT, null, null);
        boolean result = reviewDao.addReview(review);
        assertFalse("Review should fail with rating below 1", result);
    }
    
    @Test
    public void testAddReviewWithInvalidRatingTooHigh() {
        ReviewData review = new ReviewData(0, VALID_ITEM_ID, VALID_CUSTOMER_NAME, VALID_CUSTOMER_EMAIL, 
                                         6, VALID_COMMENT, null, null);
        boolean result = reviewDao.addReview(review);
        assertFalse("Review should fail with rating above 5", result);
    }
    
    @Test
    public void testAddReviewWithNullComment() {
        ReviewData review = new ReviewData(0, VALID_ITEM_ID, VALID_CUSTOMER_NAME, VALID_CUSTOMER_EMAIL, 
                                         VALID_RATING, null, null, null);
        boolean result = reviewDao.addReview(review);
        assertFalse("Review should fail with null comment", result);
    }
    
    @Test
    public void testAddReviewWithEmptyComment() {
        ReviewData review = new ReviewData(0, VALID_ITEM_ID, VALID_CUSTOMER_NAME, VALID_CUSTOMER_EMAIL, 
                                         VALID_RATING, "", null, null);
        boolean result = reviewDao.addReview(review);
        assertFalse("Review should fail with empty comment", result);
    }
    
    @Test
    public void testAddDuplicateReview() {
        // First review should succeed
        ReviewData review1 = createValidReview();
        boolean result1 = reviewDao.addReview(review1);
        assertTrue("First review should be added successfully", result1);
        
        // Second review with same customer email and item ID should fail
        ReviewData review2 = new ReviewData(0, VALID_ITEM_ID, "Different Name", VALID_CUSTOMER_EMAIL, 
                                          5, "Different comment", null, null);
        boolean result2 = reviewDao.addReview(review2);
        assertFalse("Duplicate review should fail", result2);
    }
    
    @Test
    public void testAddReviewWithValidRatingBoundaries() {
        // Test rating = 1
        ReviewData review1 = new ReviewData(0, VALID_ITEM_ID, VALID_CUSTOMER_NAME, "test1@email.com", 
                                          1, VALID_COMMENT, null, null);
        assertTrue("Rating of 1 should be valid", reviewDao.addReview(review1));
        
        // Test rating = 5
        ReviewData review5 = new ReviewData(0, VALID_ITEM_ID, VALID_CUSTOMER_NAME, "test5@email.com", 
                                          5, VALID_COMMENT, null, null);
        assertTrue("Rating of 5 should be valid", reviewDao.addReview(review5));
    }
    
    // ============= UPDATE REVIEW TESTS =============
    
    @Test
    public void testUpdateReviewWithValidData() {
        // First add a review
        ReviewData originalReview = createValidReview();
        assertTrue("Original review should be added", reviewDao.addReview(originalReview));
        
        // Get the added review to get its ID
        ReviewData addedReview = reviewDao.getCustomerReview(VALID_ITEM_ID, VALID_CUSTOMER_EMAIL);
        assertNotNull("Added review should be retrievable", addedReview);
        
        // Update the review
        String updatedComment = "Updated comment with new feedback";
        int updatedRating = 5;
        ReviewData updateData = new ReviewData(
            addedReview.getReviewId(), VALID_ITEM_ID, "Updated Name", VALID_CUSTOMER_EMAIL,
            updatedRating, updatedComment, null, null
        );
        
        boolean updateResult = reviewDao.updateReview(updateData);
        assertTrue("Review update should succeed", updateResult);
        
        // Verify the update
        ReviewData updatedReview = reviewDao.getCustomerReview(VALID_ITEM_ID, VALID_CUSTOMER_EMAIL);
        assertNotNull("Updated review should be retrievable", updatedReview);
        assertEquals("Comment should be updated", updatedComment, updatedReview.getComment());
        assertEquals("Rating should be updated", updatedRating, updatedReview.getRating());
        assertEquals("Customer name should be updated", "Updated Name", updatedReview.getCustomerName());
    }
    
    @Test
    public void testUpdateNonExistentReview() {
        ReviewData nonExistentReview = new ReviewData(
            999, VALID_ITEM_ID, VALID_CUSTOMER_NAME, "nonexistent@email.com",
            VALID_RATING, VALID_COMMENT, null, null
        );
        
        boolean result = reviewDao.updateReview(nonExistentReview);
        assertFalse("Update should fail for non-existent review", result);
    }
    
    // ============= DELETE REVIEW TESTS =============
    
    @Test
    public void testDeleteReviewWithValidData() {
        // First add a review
        ReviewData review = createValidReview();
        assertTrue("Review should be added", reviewDao.addReview(review));
        
        // Get the added review to get its ID
        ReviewData addedReview = reviewDao.getCustomerReview(VALID_ITEM_ID, VALID_CUSTOMER_EMAIL);
        assertNotNull("Added review should exist", addedReview);
        
        // Delete the review
        boolean deleteResult = reviewDao.deleteReview(addedReview.getReviewId(), VALID_CUSTOMER_EMAIL);
        assertTrue("Review deletion should succeed", deleteResult);
        
        // Verify deletion
        ReviewData deletedReview = reviewDao.getCustomerReview(VALID_ITEM_ID, VALID_CUSTOMER_EMAIL);
        assertNull("Deleted review should not exist", deletedReview);
    }
    
    @Test
    public void testDeleteNonExistentReview() {
        boolean result = reviewDao.deleteReview(999, "nonexistent@email.com");
        assertFalse("Delete should fail for non-existent review", result);
    }
    
    @Test
    public void testDeleteReviewWithWrongEmail() {
        // Add a review
        ReviewData review = createValidReview();
        assertTrue("Review should be added", reviewDao.addReview(review));
        
        // Get the added review
        ReviewData addedReview = reviewDao.getCustomerReview(VALID_ITEM_ID, VALID_CUSTOMER_EMAIL);
        assertNotNull("Added review should exist", addedReview);
        
        // Try to delete with wrong email
        boolean result = reviewDao.deleteReview(addedReview.getReviewId(), "wrong@email.com");
        assertFalse("Delete should fail with wrong email", result);
        
        // Verify review still exists
        ReviewData stillExists = reviewDao.getCustomerReview(VALID_ITEM_ID, VALID_CUSTOMER_EMAIL);
        assertNotNull("Review should still exist after failed delete", stillExists);
    }
    
    // ============= GET REVIEWS TESTS =============
    
    @Test
    public void testGetReviewsByItemIdWithValidId() {
        // Add multiple reviews for the same item
        ReviewData review1 = new ReviewData(0, VALID_ITEM_ID, "Customer 1", "customer1@email.com", 
                                          4, "Good food", null, null);
        ReviewData review2 = new ReviewData(0, VALID_ITEM_ID, "Customer 2", "customer2@email.com", 
                                          5, "Excellent service", null, null);
        
        assertTrue("First review should be added", reviewDao.addReview(review1));
        assertTrue("Second review should be added", reviewDao.addReview(review2));
        
        List<ReviewData> reviews = reviewDao.getReviewsByItemId(VALID_ITEM_ID);
        assertNotNull("Reviews list should not be null", reviews);
        assertTrue("Should have at least 2 reviews", reviews.size() >= 2);
        
        // Verify reviews are ordered by created_at DESC (most recent first)
        boolean foundReview1 = false, foundReview2 = false;
        for (ReviewData review : reviews) {
            if ("customer1@email.com".equals(review.getCustomerEmail())) {
                foundReview1 = true;
                assertEquals("First review comment should match", "Good food", review.getComment());
            }
            if ("customer2@email.com".equals(review.getCustomerEmail())) {
                foundReview2 = true;
                assertEquals("Second review comment should match", "Excellent service", review.getComment());
            }
        }
        assertTrue("Should find first review", foundReview1);
        assertTrue("Should find second review", foundReview2);
    }
    
    @Test
    public void testGetReviewsByItemIdWithNoReviews() {
        int nonExistentItemId = 999;
        List<ReviewData> reviews = reviewDao.getReviewsByItemId(nonExistentItemId);
        assertNotNull("Reviews list should not be null", reviews);
        assertTrue("Reviews list should be empty", reviews.isEmpty());
    }
    
    @Test
    public void testGetCustomerReviewWithValidData() {
        // Add a review
        ReviewData review = createValidReview();
        assertTrue("Review should be added", reviewDao.addReview(review));
        
        // Get the specific customer review
        ReviewData customerReview = reviewDao.getCustomerReview(VALID_ITEM_ID, VALID_CUSTOMER_EMAIL);
        assertNotNull("Customer review should be found", customerReview);
        assertEquals("Customer name should match", VALID_CUSTOMER_NAME, customerReview.getCustomerName());
        assertEquals("Customer email should match", VALID_CUSTOMER_EMAIL, customerReview.getCustomerEmail());
        assertEquals("Rating should match", VALID_RATING, customerReview.getRating());
        assertEquals("Comment should match", VALID_COMMENT, customerReview.getComment());
        assertEquals("Item ID should match", VALID_ITEM_ID, customerReview.getItemId());
    }
    
    @Test
    public void testGetCustomerReviewWithNonExistentData() {
        ReviewData customerReview = reviewDao.getCustomerReview(999, "nonexistent@email.com");
        assertNull("Non-existent customer review should return null", customerReview);
    }
    
    // ============= RATING AND COUNT TESTS =============
    
    @Test
    public void testGetAverageRatingWithMultipleReviews() {
        int testItemId = 101; // Use different item ID to avoid conflicts
        
        // Add reviews with different ratings
        ReviewData review1 = new ReviewData(0, testItemId, "Customer 1", "avg1@email.com", 
                                          2, "Poor", null, null);
        ReviewData review2 = new ReviewData(0, testItemId, "Customer 2", "avg2@email.com", 
                                          4, "Good", null, null);
        ReviewData review3 = new ReviewData(0, testItemId, "Customer 3", "avg3@email.com", 
                                          5, "Excellent", null, null);
        
        assertTrue("First review should be added", reviewDao.addReview(review1));
        assertTrue("Second review should be added", reviewDao.addReview(review2));
        assertTrue("Third review should be added", reviewDao.addReview(review3));
        
        double averageRating = reviewDao.getAverageRating(testItemId);
        double expectedAverage = (2.0 + 4.0 + 5.0) / 3.0; // 3.67
        assertEquals("Average rating should be correct", expectedAverage, averageRating, 0.01);
    }
    
    @Test
    public void testGetAverageRatingWithNoReviews() {
        double averageRating = reviewDao.getAverageRating(999);
        assertEquals("Average rating should be 0.0 for item with no reviews", 0.0, averageRating, 0.01);
    }
    
    @Test
    public void testGetReviewCountWithMultipleReviews() {
        int testItemId = 102; // Use different item ID to avoid conflicts
        
        // Add multiple reviews
        ReviewData review1 = new ReviewData(0, testItemId, "Customer 1", "count1@email.com", 
                                          3, "Average", null, null);
        ReviewData review2 = new ReviewData(0, testItemId, "Customer 2", "count2@email.com", 
                                          4, "Good", null, null);
        
        assertTrue("First review should be added", reviewDao.addReview(review1));
        assertTrue("Second review should be added", reviewDao.addReview(review2));
        
        int reviewCount = reviewDao.getReviewCount(testItemId);
        assertEquals("Review count should be 2", 2, reviewCount);
    }
    
    @Test
    public void testGetReviewCountWithNoReviews() {
        int reviewCount = reviewDao.getReviewCount(999);
        assertEquals("Review count should be 0 for item with no reviews", 0, reviewCount);
    }
    
    // ============= HAS CUSTOMER REVIEWED TESTS =============
    
    @Test
    public void testHasCustomerReviewedWhenReviewExists() {
        // Add a review
        ReviewData review = createValidReview();
        assertTrue("Review should be added", reviewDao.addReview(review));
        
        // Check if customer has reviewed
        boolean hasReviewed = reviewDao.hasCustomerReviewed(VALID_ITEM_ID, VALID_CUSTOMER_EMAIL);
        assertTrue("Should return true when customer has reviewed", hasReviewed);
    }
    
    @Test
    public void testHasCustomerReviewedWhenReviewDoesNotExist() {
        boolean hasReviewed = reviewDao.hasCustomerReviewed(999, "nonexistent@email.com");
        assertFalse("Should return false when customer has not reviewed", hasReviewed);
    }
    
    // ============= INTEGRATION TESTS =============
    
    @Test
    public void testCompleteReviewLifecycle() {
        String testEmail = "lifecycle@email.com";
        int testItemId = 103;
        
        // 1. Add review
        ReviewData originalReview = new ReviewData(0, testItemId, "Lifecycle Tester", testEmail, 
                                                 3, "Initial review", null, null);
        assertTrue("Review should be added", reviewDao.addReview(originalReview));
        
        // 2. Verify it exists
        ReviewData addedReview = reviewDao.getCustomerReview(testItemId, testEmail);
        assertNotNull("Review should exist", addedReview);
        assertEquals("Initial comment should match", "Initial review", addedReview.getComment());
        
        // 3. Update review
        ReviewData updateData = new ReviewData(addedReview.getReviewId(), testItemId, 
                                             "Updated Tester", testEmail, 5, "Updated review", null, null);
        assertTrue("Review should be updated", reviewDao.updateReview(updateData));
        
        // 4. Verify update
        ReviewData updatedReview = reviewDao.getCustomerReview(testItemId, testEmail);
        assertEquals("Updated comment should match", "Updated review", updatedReview.getComment());
        assertEquals("Updated rating should match", 5, updatedReview.getRating());
        
        // 5. Check statistics
        double avgRating = reviewDao.getAverageRating(testItemId);
        assertEquals("Average rating should be 5.0", 5.0, avgRating, 0.01);
        
        int count = reviewDao.getReviewCount(testItemId);
        assertEquals("Review count should be 1", 1, count);
        
        // 6. Delete review
        assertTrue("Review should be deleted", 
                  reviewDao.deleteReview(updatedReview.getReviewId(), testEmail));
        
        // 7. Verify deletion
        assertNull("Review should not exist after deletion", 
                  reviewDao.getCustomerReview(testItemId, testEmail));
        assertFalse("Customer should not have reviewed after deletion", 
                   reviewDao.hasCustomerReviewed(testItemId, testEmail));
    }
    
    // ============= HELPER METHODS =============
    
    private ReviewData createValidReview() {
        return new ReviewData(0, VALID_ITEM_ID, VALID_CUSTOMER_NAME, VALID_CUSTOMER_EMAIL, 
                            VALID_RATING, VALID_COMMENT, null, null);
    }
    
    /**
     * Alternative method to create a ReviewData object that matches your DAO's expected structure
     */
    private ReviewData createReviewWithItemId(int itemId, String email) {
        return new ReviewData(0, itemId, VALID_CUSTOMER_NAME, email, 
                            VALID_RATING, VALID_COMMENT, null, null);
    }
    
    // ============= CLEANUP =============
    
    @After
    public void tearDown() {
        // Cleanup is handled by @Before cleanupReviews method for test isolation
    }
    
    /**
     * Clean up all test data at the end of all tests
     */
    @AfterClass
    public static void cleanupAllTestData() {
        MySqlConnection mySql = new MySqlConnection();
        Connection conn = null;
        try {
            conn = mySql.openConnection();
            String deleteReviews = "DELETE FROM reviews WHERE customer_email LIKE '%@email.com'";
            String deleteMenuItems = "DELETE FROM menu WHERE item_id IN (1, 101, 102, 103)";
            
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(deleteReviews);
                stmt.executeUpdate(deleteMenuItems);
            }
        } catch (SQLException e) {
            // Ignore cleanup errors
        } finally {
            if (conn != null) {
                mySql.closeConnection(conn);
            }
        }
    }
}