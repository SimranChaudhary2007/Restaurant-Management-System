/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package restaurant.management.system.dao;

import java.util.List;
import org.junit.*;
import restaurant.management.system.model.StaffRequestData;

/**
 *
 * @author samee
 */
public class StaffRequestDaoTest {
    String correctFullName = "Test Staff";
    String correctRestaurantName = "Test Restaurant";
    String correctPhoneNumber = "9876543210";
    String correctEmail = "teststaff@gmail.com";
    String correctUsername = "teststaff";
    String password = "passwordfortest";
    int testOwnerId = 1;
    StaffRequestDao dao = new StaffRequestDao();

    @Test
    public void addPendingRequestWithValidData() {
        StaffRequestData request = new StaffRequestData(correctFullName, correctRestaurantName, 
                                                       correctPhoneNumber, correctEmail, 
                                                       correctUsername, password, testOwnerId);
        boolean result = dao.addPendingRequest(request);
        Assert.assertTrue("Staff request should be submitted successfully", result);
    }

    @Test
    public void addPendingRequestWithDuplicateEmail() {
        StaffRequestData request = new StaffRequestData(correctFullName, correctRestaurantName, 
                                                       correctPhoneNumber, correctEmail, 
                                                       correctUsername, password, testOwnerId);
        dao.addPendingRequest(request);
        
        StaffRequestData duplicateRequest = new StaffRequestData("Another Staff", correctRestaurantName, 
                                                               "1234567890", correctEmail, 
                                                               "anotheruser", password, testOwnerId);
        boolean result = dao.addPendingRequest(duplicateRequest);
        Assert.assertFalse("Staff request should fail with duplicate email", result);
    }

    @Test
    public void getAllPendingRequestsWithData() {
        StaffRequestData request = new StaffRequestData(correctFullName, correctRestaurantName, 
                                                       correctPhoneNumber, "pending@test.com", 
                                                       correctUsername, password, testOwnerId);
        dao.addPendingRequest(request);
        
        List<StaffRequestData> requests = dao.getAllPendingRequests(testOwnerId);
        Assert.assertNotNull("Pending requests list should not be null", requests);
        Assert.assertTrue("Should have at least one pending request", requests.size() > 0);
        
        boolean foundTestRequest = false;
        for (StaffRequestData req : requests) {
            if (req.getEmail().equals("pending@test.com") && "PENDING".equals(req.getStatus())) {
                foundTestRequest = true;
                Assert.assertEquals("Full name should match", correctFullName, req.getFullName());
                Assert.assertEquals("Restaurant name should match", correctRestaurantName, req.getRestaurantName());
                Assert.assertEquals("Owner ID should match", testOwnerId, req.getOwnerId());
                break;
            }
        }
        Assert.assertTrue("Test request should be found in pending requests", foundTestRequest);
    }

    @Test
    public void getRequestByIdWithValidId() {
        StaffRequestData request = new StaffRequestData("Get By ID Test", correctRestaurantName, 
                                                       "5555555555", "getbyid@test.com", 
                                                       "getbyiduser", password, testOwnerId);
        dao.addPendingRequest(request);
        
        List<StaffRequestData> requests = dao.getAllPendingRequests(testOwnerId);
        StaffRequestData testRequest = null;
        for (StaffRequestData req : requests) {
            if ("getbyid@test.com".equals(req.getEmail())) {
                testRequest = req;
                break;
            }
        }
        Assert.assertNotNull("Test request should exist", testRequest);
        
        StaffRequestData retrievedRequest = dao.getRequestById(testRequest.getRequestId());
        Assert.assertNotNull("Retrieved request should not be null", retrievedRequest);
        Assert.assertEquals("Request IDs should match", testRequest.getRequestId(), retrievedRequest.getRequestId());
        Assert.assertEquals("Full names should match", testRequest.getFullName(), retrievedRequest.getFullName());
    }

    @Test
    public void getRequestByIdWithInvalidId() {
        StaffRequestData request = dao.getRequestById(-1);
        Assert.assertNull("Request should be null for invalid ID", request);
    }

    @Test
    public void approveRequestWithValidId() {
        StaffRequestData request = new StaffRequestData("Approve Test Staff", correctRestaurantName, 
                                                       "7777777777", "approve@test.com", 
                                                       "approveuser", password, testOwnerId);
        dao.addPendingRequest(request);
        
        List<StaffRequestData> requests = dao.getAllPendingRequests(testOwnerId);
        StaffRequestData testRequest = null;
        for (StaffRequestData req : requests) {
            if ("approve@test.com".equals(req.getEmail())) {
                testRequest = req;
                break;
            }
        }
        Assert.assertNotNull("Test request should exist", testRequest);
        
        String approvedBy = "test@admin.com";
        boolean result = dao.approveRequest(testRequest.getRequestId(), approvedBy);
        Assert.assertTrue("Request approval should succeed", result);
        
        StaffRequestData updatedRequest = dao.getRequestById(testRequest.getRequestId());
        Assert.assertNotNull("Updated request should exist", updatedRequest);
        Assert.assertEquals("Status should be APPROVED", "APPROVED", updatedRequest.getStatus());
        Assert.assertEquals("Processed by should match", approvedBy, updatedRequest.getProcessedBy());
        Assert.assertNotNull("Processed date should not be null", updatedRequest.getProcessedDate());
    }

    @Test
    public void approveRequestWithInvalidId() {
        boolean result = dao.approveRequest(-1, "test@admin.com");
        Assert.assertFalse("Request approval should fail with invalid ID", result);
    }

    @Test
    public void rejectRequestWithValidId() {
        StaffRequestData newRequest = new StaffRequestData("Reject Test Staff", correctRestaurantName, 
                                                          "8888888888", "reject@test.com", 
                                                          "rejectuser", password, testOwnerId);
        dao.addPendingRequest(newRequest);
        
        List<StaffRequestData> requests = dao.getAllPendingRequests(testOwnerId);
        StaffRequestData testRequest = null;
        for (StaffRequestData request : requests) {
            if ("reject@test.com".equals(request.getEmail())) {
                testRequest = request;
                break;
            }
        }
        Assert.assertNotNull("Test request should exist", testRequest);
        
        String rejectedBy = "test@admin.com";
        boolean result = dao.rejectRequest(testRequest.getRequestId(), rejectedBy);
        Assert.assertTrue("Request rejection should succeed", result);
        
        // Verify the request status was updated
        StaffRequestData updatedRequest = dao.getRequestById(testRequest.getRequestId());
        Assert.assertNotNull("Updated request should exist", updatedRequest);
        Assert.assertEquals("Status should be REJECTED", "REJECTED", updatedRequest.getStatus());
        Assert.assertEquals("Processed by should match", rejectedBy, updatedRequest.getProcessedBy());
        Assert.assertNotNull("Processed date should not be null", updatedRequest.getProcessedDate());
    }

    @Test
    public void rejectRequestWithInvalidId() {
        boolean result = dao.rejectRequest(-1, "test@admin.com");
        Assert.assertFalse("Request rejection should fail with invalid ID", result);
    }

    @Test
    public void hasExistingPendingRequestWithExistingEmail() {
        StaffRequestData request = new StaffRequestData("Existing Test", correctRestaurantName, 
                                                       "3333333333", "existing@test.com", 
                                                       "existinguser", password, testOwnerId);
        dao.addPendingRequest(request);
        
        boolean hasExisting = dao.hasExistingPendingRequest("existing@test.com");
        Assert.assertTrue("Should find existing pending request", hasExisting);
    }

    @Test
    public void hasExistingPendingRequestWithNewEmail() {
        boolean hasExisting = dao.hasExistingPendingRequest("nonexistent@test.com");
        Assert.assertFalse("Should not find non-existent request", hasExisting);
    }

    @Test
    public void cleanupOldRequestsWithValidDays() {
        boolean result = dao.cleanupOldRequests(30);
        Assert.assertTrue("Cleanup should execute successfully", result);
    }

    @Test
    public void cleanupOldRequestsWithInvalidDays() {
        boolean result = dao.cleanupOldRequests(-1);
        Assert.assertTrue("Cleanup should still execute (returns >= 0)", result);
    }

    @Test
    public void getAllPendingRequestsWithInvalidOwnerId() {
        List<StaffRequestData> requests = dao.getAllPendingRequests(-1);
        Assert.assertNotNull("Requests list should not be null", requests);
        Assert.assertEquals("Should return empty list for invalid owner ID", 0, requests.size());
    }

    @Test
    public void getAllPendingRequestsVerifyOwnerIdFilter() {
        StaffRequestData request1 = new StaffRequestData("Owner1 Staff", correctRestaurantName, 
                                                        "1111111111", "owner1@test.com", 
                                                        "owner1user", password, 1);
        StaffRequestData request2 = new StaffRequestData("Owner2 Staff", correctRestaurantName, 
                                                        "2222222222", "owner2@test.com", 
                                                        "owner2user", password, 2);
        
        dao.addPendingRequest(request1);
        dao.addPendingRequest(request2);
        
        List<StaffRequestData> owner1Requests = dao.getAllPendingRequests(1);
        Assert.assertNotNull("Owner 1 requests should not be null", owner1Requests);
        
        for (StaffRequestData request : owner1Requests) {
            Assert.assertEquals("All requests should belong to owner 1", 1, request.getOwnerId());
        }
        
        List<StaffRequestData> owner2Requests = dao.getAllPendingRequests(2);
        Assert.assertNotNull("Owner 2 requests should not be null", owner2Requests);
        
        for (StaffRequestData request : owner2Requests) {
            Assert.assertEquals("All requests should belong to owner 2", 2, request.getOwnerId());
        }
    }
}