/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package restaurant.management.system.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import restaurant.management.system.model.LoginRequest;
import restaurant.management.system.model.StaffData;

/**
 *
 * @author pradeepta 3434
 */
public class StaffDaoTest {
    
    public StaffDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of register method, of class StaffDao.
     */
    @Test
    public void testRegister() {
        System.out.println("register");
        StaffData staff = null;
        StaffDao instance = new StaffDao();
        boolean expResult = false;
        boolean result = instance.register(staff);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of login method, of class StaffDao.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        LoginRequest loginSData = null;
        StaffDao instance = new StaffDao();
        StaffData expResult = null;
        StaffData result = instance.login(loginSData);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateProfilePicture method, of class StaffDao.
     */
    @Test
    public void testUpdateProfilePicture() {
        System.out.println("updateProfilePicture");
        int staffId = 0;
        byte[] profilePicture = null;
        StaffDao instance = new StaffDao();
        boolean expResult = false;
        boolean result = instance.updateProfilePicture(staffId, profilePicture);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProfilePicture method, of class StaffDao.
     */
    @Test
    public void testGetProfilePicture() {
        System.out.println("getProfilePicture");
        int staffId = 0;
        StaffDao instance = new StaffDao();
        byte[] expResult = null;
        byte[] result = instance.getProfilePicture(staffId);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStaffById method, of class StaffDao.
     */
    @Test
    public void testGetStaffById() {
        System.out.println("getStaffById");
        int staffId = 0;
        StaffDao instance = new StaffDao();
        StaffData expResult = null;
        StaffData result = instance.getStaffById(staffId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateStaffProfile method, of class StaffDao.
     */
    @Test
    public void testUpdateStaffProfile() {
        System.out.println("updateStaffProfile");
        int staffId = 0;
        String fullName = "";
        String restaurantName = "";
        String phoneNumber = "";
        String email = "";
        StaffDao instance = new StaffDao();
        boolean expResult = false;
        boolean result = instance.updateStaffProfile(staffId, fullName, restaurantName, phoneNumber, email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
