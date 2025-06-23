/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package restaurant.management.system.dao;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import restaurant.management.system.model.NoticeData;

/**
 *
 * @author pradeepta 3434
 */
public class NoticeDaoTest {
      private NoticeDao dao;
    private static final int TEST_OWNER_ID = 1; // Assuming this owner exists in test DB
    private String uniqueId;
    private String testTitle;
    private String testContent;
    private static final boolean TEST_IS_ACTIVE = true;

    @BeforeClass
    public static void setUpClass() {
        // Initialize database connection or test environment if needed
        // This runs once before all tests
    }

    @AfterClass
    public static void tearDownClass() {
        // Clean up database connection or test environment if needed
        // This runs once after all tests
    }

    @Before
    public void setUp() {
        dao = new NoticeDao();
        // Create unique identifier for each test run
        uniqueId = System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
        testTitle = "Test Notice " + uniqueId;
        testContent = "This is a test notice content " + uniqueId;
        cleanUpTestNotices();
    }

    @After
    public void tearDown() {
        cleanUpTestNotices();
        dao = null;
    }

    private void cleanUpTestNotices() {
        try {
            List<NoticeData> notices = dao.getAllNoticesByOwner(TEST_OWNER_ID);
            if (notices != null) {
                for (NoticeData notice : notices) {
                    if (notice != null && notice.getTitle() != null && 
                        (notice.getTitle().contains("Test Notice") || 
                         notice.getTitle().contains("Updated Title") ||
                         notice.getTitle().contains("Active Notice") ||
                         notice.getTitle().contains("Inactive Notice") ||
                         notice.getTitle().contains("Notice 1") ||
                         notice.getTitle().contains("Notice 2") ||
                         notice.getTitle().contains("Sequence Test") ||
                         notice.getTitle().contains("Special Chars") ||
                         notice.getTitle().contains(uniqueId))) {
                        dao.deleteNotice(notice.getNoticeId());
                    }
                }
            }
        } catch (Exception e) {
            // Log the exception but don't fail the test setup
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }

    private void waitForDatabaseConsistency() {
        try { 
            Thread.sleep(200); // Increased wait time for better consistency
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void testAddNoticeWithValidData() {
        NoticeData notice = new NoticeData(TEST_OWNER_ID, testTitle, testContent, TEST_IS_ACTIVE);
        boolean result = dao.addNotice(notice);
        assertTrue("Adding notice should succeed with valid data", result);
        
        waitForDatabaseConsistency();
        
        List<NoticeData> notices = dao.getAllNoticesByOwner(TEST_OWNER_ID);
        assertNotNull("Notice list should not be null", notices);
        
        // Verify the notice was actually added with correct data
        boolean foundNotice = false;
        for (NoticeData addedNotice : notices) {
            if (addedNotice != null && testTitle.equals(addedNotice.getTitle())) {
                foundNotice = true;
                assertEquals("Content should match", testContent, addedNotice.getContent());
                assertEquals("Active status should match", TEST_IS_ACTIVE, addedNotice.isActive());
                assertEquals("Owner ID should match", TEST_OWNER_ID, addedNotice.getOwnerId());
                break;
            }
        }
        assertTrue("Added notice should be found in the list", foundNotice);
    }

    @Test
    public void testAddNoticeWithNullNotice() {
        boolean result = dao.addNotice(null);
        assertFalse("Adding null notice should fail", result);
    }

    @Test
    public void testAddNoticeWithNullTitle() {
        NoticeData notice = new NoticeData(TEST_OWNER_ID, null, testContent, TEST_IS_ACTIVE);
        boolean result = dao.addNotice(notice);
        assertFalse("Adding notice should fail with null title", result);
    }

    @Test
    public void testAddNoticeWithEmptyTitle() {
        NoticeData notice = new NoticeData(TEST_OWNER_ID, "", testContent, TEST_IS_ACTIVE);
        boolean result = dao.addNotice(notice);
        assertFalse("Adding notice should fail with empty title", result);
    }

    @Test
    public void testAddNoticeWithWhitespaceTitle() {
        NoticeData notice = new NoticeData(TEST_OWNER_ID, "   ", testContent, TEST_IS_ACTIVE);
        boolean result = dao.addNotice(notice);
        assertFalse("Adding notice should fail with whitespace-only title", result);
    }

    @Test
    public void testAddNoticeWithInvalidOwnerId() {
        NoticeData notice = new NoticeData(-1, testTitle, testContent, TEST_IS_ACTIVE);
        boolean result = dao.addNotice(notice);
        assertFalse("Adding notice should fail with invalid owner ID", result);
    }

    @Test
    public void testAddNoticeWithZeroOwnerId() {
        NoticeData notice = new NoticeData(0, testTitle, testContent, TEST_IS_ACTIVE);
        boolean result = dao.addNotice(notice);
        assertFalse("Adding notice should fail with zero owner ID", result);
    }

    @Test
    public void testAddNoticeWithNullContent() {
        NoticeData notice = new NoticeData(TEST_OWNER_ID, testTitle, null, TEST_IS_ACTIVE);
        boolean result = dao.addNotice(notice);
        // This might be allowed depending on implementation - adjust as needed
        // For now, assume it should succeed (allowing null content)
        assertTrue("Adding notice with null content should succeed", result);
        
        if (result) {
            waitForDatabaseConsistency();
            List<NoticeData> notices = dao.getAllNoticesByOwner(TEST_OWNER_ID);
            boolean found = false;
            for (NoticeData n : notices) {
                if (n != null && testTitle.equals(n.getTitle())) {
                    found = true;
                    assertNull("Content should be null", n.getContent());
                    break;
                }
            }
            assertTrue("Notice should be found even with null content", found);
        }
    }

    @Test
    public void testAddNoticeWithEmptyContent() {
        String emptyContent = "";
        NoticeData notice = new NoticeData(TEST_OWNER_ID, testTitle, emptyContent, TEST_IS_ACTIVE);
        boolean result = dao.addNotice(notice);
        assertTrue("Adding notice with empty content should succeed", result);
        
        waitForDatabaseConsistency();
        List<NoticeData> notices = dao.getAllNoticesByOwner(TEST_OWNER_ID);
        boolean found = false;
        for (NoticeData n : notices) {
            if (n != null && testTitle.equals(n.getTitle())) {
                found = true;
                assertEquals("Content should be empty", emptyContent, n.getContent());
                break;
            }
        }
        assertTrue("Notice should be found with empty content", found);
    }

    @Test
    public void testGetActiveNoticesByOwner() {
        String activeTitle = "Active Notice " + uniqueId;
        String inactiveTitle = "Inactive Notice " + uniqueId;
        
        NoticeData activeNotice = new NoticeData(TEST_OWNER_ID, activeTitle, testContent, true);
        NoticeData inactiveNotice = new NoticeData(TEST_OWNER_ID, inactiveTitle, testContent, false);
        
        assertTrue("Active notice should be added", dao.addNotice(activeNotice));
        assertTrue("Inactive notice should be added", dao.addNotice(inactiveNotice));
        
        waitForDatabaseConsistency();
        
        List<NoticeData> activeNotices = dao.getActiveNoticesByOwner(TEST_OWNER_ID);
        assertNotNull("Active notices list should not be null", activeNotices);
        
        // Count only our test notices that are active
        boolean foundActiveNotice = false;
        boolean foundInactiveNotice = false;
        
        for (NoticeData notice : activeNotices) {
            if (notice != null) {
                if (activeTitle.equals(notice.getTitle())) {
                    foundActiveNotice = true;
                    assertTrue("Returned notice should be active", notice.isActive());
                }
                if (inactiveTitle.equals(notice.getTitle())) {
                    foundInactiveNotice = true;
                }
            }
        }
        
        assertTrue("Should find the active test notice", foundActiveNotice);
        assertFalse("Should not find the inactive test notice in active list", foundInactiveNotice);
    }

    @Test
    public void testGetActiveNoticesByOwnerWithInvalidId() {
        List<NoticeData> notices = dao.getActiveNoticesByOwner(-1);
        // This should return an empty list or null - adjust based on implementation
        if (notices != null) {
            assertTrue("Should return empty list for invalid owner ID", notices.isEmpty());
        }
    }

    @Test
    public void testGetActiveNoticesByOwnerWithZeroId() {
        List<NoticeData> notices = dao.getActiveNoticesByOwner(0);
        if (notices != null) {
            assertTrue("Should return empty list for zero owner ID", notices.isEmpty());
        }
    }

    @Test
    public void testGetAllNoticesByOwner() {
        String title1 = "Notice 1 " + uniqueId;
        String title2 = "Notice 2 " + uniqueId;
        
        NoticeData notice1 = new NoticeData(TEST_OWNER_ID, title1, testContent, true);
        NoticeData notice2 = new NoticeData(TEST_OWNER_ID, title2, testContent, false);
        
        assertTrue("Notice 1 should be added", dao.addNotice(notice1));
        assertTrue("Notice 2 should be added", dao.addNotice(notice2));
        
        waitForDatabaseConsistency();
        
        List<NoticeData> notices = dao.getAllNoticesByOwner(TEST_OWNER_ID);
        assertNotNull("Notices list should not be null", notices);
        
        // Count our test notices
        boolean found1 = false, found2 = false;
        for (NoticeData notice : notices) {
            if (notice != null) {
                if (title1.equals(notice.getTitle())) {
                    found1 = true;
                    assertTrue("Notice 1 should be active", notice.isActive());
                }
                if (title2.equals(notice.getTitle())) {
                    found2 = true;
                    assertFalse("Notice 2 should be inactive", notice.isActive());
                }
            }
        }
        
        assertTrue("Should find Notice 1", found1);
        assertTrue("Should find Notice 2", found2);
    }

    @Test
    public void testGetAllNoticesByOwnerWithInvalidId() {
        List<NoticeData> notices = dao.getAllNoticesByOwner(-1);
        // This should return an empty list or null - adjust based on implementation
        if (notices != null) {
            assertTrue("Should return empty list for invalid owner ID", notices.isEmpty());
        }
    }

    @Test
    public void testUpdateNotice() {
        NoticeData original = new NoticeData(TEST_OWNER_ID, testTitle, testContent, true);
        assertTrue("Original notice should be added", dao.addNotice(original));
        
        waitForDatabaseConsistency();
        
        List<NoticeData> notices = dao.getAllNoticesByOwner(TEST_OWNER_ID);
        assertNotNull("Notices list should not be null", notices);
        
        // Find our test notice
        NoticeData addedNotice = null;
        for (NoticeData notice : notices) {
            if (notice != null && testTitle.equals(notice.getTitle())) {
                addedNotice = notice;
                break;
            }
        }
        assertNotNull("Test notice should be found", addedNotice);
        
        int noticeId = addedNotice.getNoticeId();
        assertTrue("Notice ID should be positive", noticeId > 0);
        
        String updatedTitle = "Updated Title " + uniqueId;
        String updatedContent = "New Content " + uniqueId;
        
        NoticeData updated = new NoticeData(TEST_OWNER_ID, updatedTitle, updatedContent, false);
        updated.setNoticeId(noticeId);
        
        boolean result = dao.updateNotice(updated);
        assertTrue("Update should succeed", result);
        
        waitForDatabaseConsistency();
        
        NoticeData retrieved = dao.getNoticeById(noticeId);
        assertNotNull("Updated notice should be retrievable", retrieved);
        assertEquals("Title should be updated", updatedTitle, retrieved.getTitle());
        assertEquals("Content should be updated", updatedContent, retrieved.getContent());
        assertFalse("Active status should be updated", retrieved.isActive());
        assertEquals("Owner ID should remain the same", TEST_OWNER_ID, retrieved.getOwnerId());
    }

    @Test
    public void testUpdateNoticeWithInvalidId() {
        NoticeData updated = new NoticeData(TEST_OWNER_ID, testTitle, testContent, false);
        updated.setNoticeId(-1);
        
        boolean result = dao.updateNotice(updated);
        assertFalse("Update should fail with invalid ID", result);
    }

    @Test
    public void testUpdateNoticeWithZeroId() {
        NoticeData updated = new NoticeData(TEST_OWNER_ID, testTitle, testContent, false);
        updated.setNoticeId(0);
        
        boolean result = dao.updateNotice(updated);
        assertFalse("Update should fail with zero ID", result);
    }

    @Test
    public void testUpdateNoticeWithNullNotice() {
        boolean result = dao.updateNotice(null);
        assertFalse("Update should fail with null notice", result);
    }

    @Test
    public void testToggleNoticeStatus() {
        NoticeData notice = new NoticeData(TEST_OWNER_ID, testTitle, testContent, true);
        assertTrue("Notice should be added", dao.addNotice(notice));
        
        waitForDatabaseConsistency();
        
        List<NoticeData> notices = dao.getAllNoticesByOwner(TEST_OWNER_ID);
        assertNotNull("Notices list should not be null", notices);
        
        // Find our test notice
        NoticeData addedNotice = null;
        for (NoticeData n : notices) {
            if (n != null && testTitle.equals(n.getTitle())) {
                addedNotice = n;
                break;
            }
        }
        assertNotNull("Test notice should be found", addedNotice);
        
        int noticeId = addedNotice.getNoticeId();
        assertTrue("Notice ID should be positive", noticeId > 0);
        
        // Test deactivation
        boolean deactivateResult = dao.deactivateNotice(noticeId);
        assertTrue("Deactivation should succeed", deactivateResult);
        
        waitForDatabaseConsistency();
        
        NoticeData afterDeactivation = dao.getNoticeById(noticeId);
        assertNotNull("Notice should still exist after deactivation", afterDeactivation);
        assertFalse("Notice should be inactive", afterDeactivation.isActive());
        
        // Test activation
        boolean activateResult = dao.activateNotice(noticeId);
        assertTrue("Activation should succeed", activateResult);
        
        waitForDatabaseConsistency();
        
        NoticeData afterActivation = dao.getNoticeById(noticeId);
        assertNotNull("Notice should still exist after activation", afterActivation);
        assertTrue("Notice should be active", afterActivation.isActive());
    }

    @Test
    public void testDeactivateNoticeWithInvalidId() {
        boolean result = dao.deactivateNotice(-1);
        assertFalse("Deactivation should fail with invalid ID", result);
    }

    @Test
    public void testDeactivateNoticeWithZeroId() {
        boolean result = dao.deactivateNotice(0);
        assertFalse("Deactivation should fail with zero ID", result);
    }

    @Test
    public void testActivateNoticeWithInvalidId() {
        boolean result = dao.activateNotice(-1);
        assertFalse("Activation should fail with invalid ID", result);
    }

    @Test
    public void testActivateNoticeWithZeroId() {
        boolean result = dao.activateNotice(0);
        assertFalse("Activation should fail with zero ID", result);
    }

    @Test
    public void testDeleteNotice() {
        NoticeData notice = new NoticeData(TEST_OWNER_ID, testTitle, testContent, true);
        assertTrue("Notice should be added", dao.addNotice(notice));
        
        waitForDatabaseConsistency();
        
        List<NoticeData> notices = dao.getAllNoticesByOwner(TEST_OWNER_ID);
        assertNotNull("Notices list should not be null", notices);
        
        // Find our test notice
        NoticeData addedNotice = null;
        for (NoticeData n : notices) {
            if (n != null && testTitle.equals(n.getTitle())) {
                addedNotice = n;
                break;
            }
        }
        assertNotNull("Test notice should be found", addedNotice);
        
        int noticeId = addedNotice.getNoticeId();
        assertTrue("Notice ID should be positive", noticeId > 0);
        
        boolean result = dao.deleteNotice(noticeId);
        assertTrue("Deletion should succeed", result);
        
        waitForDatabaseConsistency();
        
        NoticeData deletedNotice = dao.getNoticeById(noticeId);
        assertNull("Notice should be deleted", deletedNotice);
    }

    @Test
    public void testDeleteNoticeWithInvalidId() {
        boolean result = dao.deleteNotice(-1);
        assertFalse("Deletion should fail with invalid ID", result);
    }

    @Test
    public void testDeleteNoticeWithZeroId() {
        boolean result = dao.deleteNotice(0);
        assertFalse("Deletion should fail with zero ID", result);
    }

    @Test
    public void testGetNoticeById() {
        NoticeData notice = new NoticeData(TEST_OWNER_ID, testTitle, testContent, true);
        assertTrue("Notice should be added", dao.addNotice(notice));
        
        waitForDatabaseConsistency();
        
        List<NoticeData> notices = dao.getAllNoticesByOwner(TEST_OWNER_ID);
        assertNotNull("Notices list should not be null", notices);
        
        // Find our test notice
        NoticeData addedNotice = null;
        for (NoticeData n : notices) {
            if (n != null && testTitle.equals(n.getTitle())) {
                addedNotice = n;
                break;
            }
        }
        assertNotNull("Test notice should be found", addedNotice);
        
        int noticeId = addedNotice.getNoticeId();
        assertTrue("Notice ID should be positive", noticeId > 0);
        
        NoticeData retrieved = dao.getNoticeById(noticeId);
        assertNotNull("Should retrieve notice by ID", retrieved);
        assertEquals("Title should match", testTitle, retrieved.getTitle());
        assertEquals("Content should match", testContent, retrieved.getContent());
        assertEquals("Active status should match", TEST_IS_ACTIVE, retrieved.isActive());
        assertEquals("Owner ID should match", TEST_OWNER_ID, retrieved.getOwnerId());
        assertEquals("Notice ID should match", noticeId, retrieved.getNoticeId());
    }

    @Test
    public void testGetNoticeByIdWithInvalidId() {
        NoticeData retrieved = dao.getNoticeById(-1);
        assertNull("Should return null for invalid ID", retrieved);
    }

    @Test
    public void testGetNoticeByIdWithZeroId() {
        NoticeData retrieved = dao.getNoticeById(0);
        assertNull("Should return null for zero ID", retrieved);
    }

    @Test
    public void testNoticeDataIntegrity() {
        // Test with special characters and longer content
        String specialTitle = "Test Notice with Special Chars !@#$% " + uniqueId;
        String longContent = "This is a very long content that tests the database field limits and special characters: !@#$%^&*()_+-=[]{}|;:,.<>? " + uniqueId;
        
        NoticeData notice = new NoticeData(TEST_OWNER_ID, specialTitle, longContent, true);
        assertTrue("Notice with special characters should be added", dao.addNotice(notice));
        
        waitForDatabaseConsistency();
        
        List<NoticeData> notices = dao.getAllNoticesByOwner(TEST_OWNER_ID);
        assertNotNull("Notices list should not be null", notices);
        
        boolean found = false;
        for (NoticeData n : notices) {
            if (n != null && specialTitle.equals(n.getTitle())) {
                found = true;
                assertEquals("Content should match exactly", longContent, n.getContent());
                assertTrue("Should be active", n.isActive());
                assertEquals("Owner ID should match", TEST_OWNER_ID, n.getOwnerId());
                break;
            }
        }
        assertTrue("Notice with special characters should be found", found);
    }

    @Test
    public void testMultipleOperationsSequence() {
        // Test a sequence of operations to ensure consistency
        String sequenceTitle = "Sequence Test " + uniqueId;
        
        // 1. Add notice
        NoticeData notice = new NoticeData(TEST_OWNER_ID, sequenceTitle, testContent, true);
        assertTrue("Step 1: Adding notice should succeed", dao.addNotice(notice));
        
        waitForDatabaseConsistency();
        
        // 2. Retrieve and verify
        List<NoticeData> notices = dao.getAllNoticesByOwner(TEST_OWNER_ID);
        NoticeData addedNotice = null;
        for (NoticeData n : notices) {
            if (n != null && sequenceTitle.equals(n.getTitle())) {
                addedNotice = n;
                break;
            }
        }
        assertNotNull("Step 2: Notice should be retrievable", addedNotice);
        int noticeId = addedNotice.getNoticeId();
        assertTrue("Step 2: Notice ID should be positive", noticeId > 0);
        
        // 3. Deactivate
        assertTrue("Step 3: Deactivation should succeed", dao.deactivateNotice(noticeId));
        waitForDatabaseConsistency();
        
        // 4. Verify deactivation
        NoticeData deactivated = dao.getNoticeById(noticeId);
        assertNotNull("Step 4: Notice should still exist", deactivated);
        assertFalse("Step 4: Notice should be inactive", deactivated.isActive());
        
        // 5. Update
        String updatedTitle = "Updated " + sequenceTitle;
        NoticeData updated = new NoticeData(TEST_OWNER_ID, updatedTitle, "Updated content", true);
        updated.setNoticeId(noticeId);
        assertTrue("Step 5: Update should succeed", dao.updateNotice(updated));
        waitForDatabaseConsistency();
        
        // 6. Verify update
        NoticeData finalNotice = dao.getNoticeById(noticeId);
        assertNotNull("Step 6: Updated notice should exist", finalNotice);
        assertEquals("Step 6: Title should be updated", updatedTitle, finalNotice.getTitle());
        assertEquals("Step 6: Content should be updated", "Updated content", finalNotice.getContent());
        assertTrue("Step 6: Notice should be active again", finalNotice.isActive());
        
        // 7. Delete
        assertTrue("Step 7: Deletion should succeed", dao.deleteNotice(noticeId));
        waitForDatabaseConsistency();
        
        // 8. Verify deletion
        NoticeData deleted = dao.getNoticeById(noticeId);
        assertNull("Step 8: Notice should be deleted", deleted);
    }

    @Test
    public void testDuplicateNoticeTitle() {
        // Test if duplicate titles are allowed (business logic dependent)
        String duplicateTitle = "Duplicate Title " + uniqueId;
        
        NoticeData notice1 = new NoticeData(TEST_OWNER_ID, duplicateTitle, "Content 1", true);
        NoticeData notice2 = new NoticeData(TEST_OWNER_ID, duplicateTitle, "Content 2", false);
        
        assertTrue("First notice should be added", dao.addNotice(notice1));
        // This might fail if duplicate titles are not allowed
        boolean result2 = dao.addNotice(notice2);
        
        // Adjust this assertion based on your business logic
        // If duplicates are allowed:
        // assertTrue("Second notice with duplicate title should be added", result2);
        // If duplicates are not allowed:
        // assertFalse("Second notice with duplicate title should fail", result2);
        
        // For now, assume duplicates are allowed
        assertTrue("Second notice with duplicate title should be added", result2);
        
        if (result2) {
            waitForDatabaseConsistency();
            List<NoticeData> notices = dao.getAllNoticesByOwner(TEST_OWNER_ID);
            int duplicateCount = 0;
            for (NoticeData n : notices) {
                if (n != null && duplicateTitle.equals(n.getTitle())) {
                    duplicateCount++;
                }
            }
            assertEquals("Should have exactly 2 notices with duplicate title", 2, duplicateCount);
        }
    }
}