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
    public void submitStaffRequestWithValidData() {
        StaffRequestData request = new StaffRequestData(correctFullName, correctRestaurantName, 
                                                       correctPhoneNumber, correctEmail, 
                                                       correctUsername, password, testOwnerId);
        boolean result = dao.submitStaffRequest(request);
        Assert.assertTrue("Staff request should be submitted successfully", result);
    }

    @Test
    public void submitStaffRequestWithDuplicateEmail() {
        StaffRequestData request = new StaffRequestData(correctFullName, correctRestaurantName, 
                                                       correctPhoneNumber, correctEmail, 
                                                       correctUsername, password, testOwnerId);
        boolean result = dao.submitStaffRequest(request);
        Assert.assertFalse("Staff request should fail with duplicate email", result);
    }

    @Test
    public void getAllPendingRequestsWithData() {
        List<StaffRequestData> requests = dao.getAllPendingRequests();
        Assert.assertNotNull("Pending requests list should not be null", requests);
        Assert.assertTrue("Should have at least one pending request", requests.size() > 0);
        
        // Check if our test request is in the list
        boolean foundTestRequest = false;
        for (StaffRequestData request : requests) {
            if (request.getEmail().equals(correctEmail) && "PENDING".equals(request.getStatus())) {
                foundTestRequest = true;
                Assert.assertEquals("Full name should match", correctFullName, request.getFullName());
                Assert.assertEquals("Restaurant name should match", correctRestaurantName, request.getRestaurantName());
                break;
            }
        }
        Assert.assertTrue("Test request should be found in pending requests", foundTestRequest);
    }

    @Test
    public void getRequestByIdWithValidId() {
        // First get all pending requests to find a valid ID
        List<StaffRequestData> requests = dao.getAllPendingRequests();
        Assert.assertTrue("Should have at least one request", requests.size() > 0);
        
        StaffRequestData testRequest = requests.get(0);
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
        // First get a pending request
        List<StaffRequestData> requests = dao.getAllPendingRequests();
        Assert.assertTrue("Should have at least one pending request", requests.size() > 0);
        
        StaffRequestData testRequest = requests.get(0);
        String approvedBy = "Test Admin";
        
        boolean result = dao.approveRequest(testRequest.getRequestId(), approvedBy);
        Assert.assertTrue("Request approval should succeed", result);
        
        // Verify the request status was updated
        StaffRequestData updatedRequest = dao.getRequestById(testRequest.getRequestId());
        Assert.assertNotNull("Updated request should exist", updatedRequest);
        Assert.assertEquals("Status should be APPROVED", "APPROVED", updatedRequest.getStatus());
        Assert.assertEquals("Processed by should match", approvedBy, updatedRequest.getProcessedBy());
        Assert.assertNotNull("Processed date should not be null", updatedRequest.getProcessedDate());
    }

    @Test
    public void approveRequestWithInvalidId() {
        boolean result = dao.approveRequest(-1, "Test Admin");
        Assert.assertFalse("Request approval should fail with invalid ID", result);
    }

    @Test
    public void rejectRequestWithValidId() {
        // Submit a new request for rejection test
        StaffRequestData newRequest = new StaffRequestData("Reject Test Staff", correctRestaurantName, 
                                                          "1234567890", "rejecttest@gmail.com", 
                                                          "rejecttest", password, testOwnerId);
        dao.submitStaffRequest(newRequest);
        
        // Get the newly created request
        List<StaffRequestData> requests = dao.getAllPendingRequests();
        StaffRequestData testRequest = null;
        for (StaffRequestData request : requests) {
            if ("rejecttest@gmail.com".equals(request.getEmail())) {
                testRequest = request;
                break;
            }
        }
        Assert.assertNotNull("Test request should exist", testRequest);
        
        String rejectedBy = "Test Admin";
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
        boolean result = dao.rejectRequest(-1, "Test Admin");
        Assert.assertFalse("Request rejection should fail with invalid ID", result);
    }

    @Test
    public void getAllRequestsByOwnerIdWithValidOwnerId() {
        List<StaffRequestData> requests = dao.getAllRequestsByOwnerId(testOwnerId);
        Assert.assertNotNull("Requests list should not be null", requests);
        
        // Check if all returned requests belong to the specified owner
        for (StaffRequestData request : requests) {
            Assert.assertEquals("All requests should belong to the specified owner", testOwnerId, request.getOwnerId());
        }
    }

    @Test
    public void getAllRequestsByOwnerIdWithInvalidOwnerId() {
        List<StaffRequestData> requests = dao.getAllRequestsByOwnerId(-1);
        Assert.assertNotNull("Requests list should not be null", requests);
        Assert.assertEquals("Should return empty list for invalid owner ID", 0, requests.size());
    }

    @Test
    public void getApprovedRequestsWithData() {
        List<StaffRequestData> approvedRequests = dao.getApprovedRequests();
        Assert.assertNotNull("Approved requests list should not be null", approvedRequests);
        
        // Check if all returned requests have APPROVED status
        for (StaffRequestData request : approvedRequests) {
            Assert.assertEquals("All requests should have APPROVED status", "APPROVED", request.getStatus());
            Assert.assertNotNull("Processed date should not be null", request.getProcessedDate());
            Assert.assertNotNull("Processed by should not be null", request.getProcessedBy());
        }
    }

    @Test
    public void getRejectedRequestsWithData() {
        List<StaffRequestData> rejectedRequests = dao.getRejectedRequests();
        Assert.assertNotNull("Rejected requests list should not be null", rejectedRequests);
        
        // Check if all returned requests have REJECTED status
        for (StaffRequestData request : rejectedRequests) {
            Assert.assertEquals("All requests should have REJECTED status", "REJECTED", request.getStatus());
            Assert.assertNotNull("Processed date should not be null", request.getProcessedDate());
            Assert.assertNotNull("Processed by should not be null", request.getProcessedBy());
        }
    }

    @Test
    public void deleteRequestWithValidId() {
        // Submit a new request for deletion test
        StaffRequestData deleteRequest = new StaffRequestData("Delete Test Staff", correctRestaurantName, 
                                                             "5555555555", "deletetest@gmail.com", 
                                                             "deletetest", password, testOwnerId);
        dao.submitStaffRequest(deleteRequest);
        
        // Get the newly created request
        List<StaffRequestData> requests = dao.getAllPendingRequests();
        StaffRequestData testRequest = null;
        for (StaffRequestData request : requests) {
            if ("deletetest@gmail.com".equals(request.getEmail())) {
                testRequest = request;
                break;
            }
        }
        Assert.assertNotNull("Test request should exist", testRequest);
        
        boolean result = dao.deleteRequest(testRequest.getRequestId());
        Assert.assertTrue("Request deletion should succeed", result);
        
        // Verify the request was deleted
        StaffRequestData deletedRequest = dao.getRequestById(testRequest.getRequestId());
        Assert.assertNull("Request should be null after deletion", deletedRequest);
    }

    @Test
    public void deleteRequestWithInvalidId() {
        boolean result = dao.deleteRequest(-1);
        Assert.assertFalse("Request deletion should fail with invalid ID", result);
    }

    @Test
    public void updateRequestStatusWithValidData() {
        // Get a pending request
        List<StaffRequestData> requests = dao.getAllPendingRequests();
        if (requests.size() > 0) {
            StaffRequestData testRequest = requests.get(0);
            String newStatus = "IN_REVIEW";
            String processedBy = "Test Reviewer";
            
            boolean result = dao.updateRequestStatus(testRequest.getRequestId(), newStatus, processedBy);
            Assert.assertTrue("Request status update should succeed", result);
            
            // Verify the status was updated
            StaffRequestData updatedRequest = dao.getRequestById(testRequest.getRequestId());
            Assert.assertNotNull("Updated request should exist", updatedRequest);
            Assert.assertEquals("Status should be updated", newStatus, updatedRequest.getStatus());
            Assert.assertEquals("Processed by should be updated", processedBy, updatedRequest.getProcessedBy());
        }
    }

    @Test
    public void updateRequestStatusWithInvalidId() {
        boolean result = dao.updateRequestStatus(-1, "APPROVED", "Test Admin");
        Assert.assertFalse("Request status update should fail with invalid ID", result);
    }

    @Test
    public void isEmailAlreadyRequestedWithExistingEmail() {
        boolean isRequested = dao.isEmailAlreadyRequested(correctEmail);
        Assert.assertTrue("Email should be already requested", isRequested);
    }

    @Test
    public void isEmailAlreadyRequestedWithNewEmail() {
        boolean isRequested = dao.isEmailAlreadyRequested("newemail@test.com");
        Assert.assertFalse("New email should not be already requested", isRequested);
    }
}
