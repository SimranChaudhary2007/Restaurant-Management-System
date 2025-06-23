/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package restaurant.management.system.dao;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import restaurant.management.system.model.MenuData;

/**
 *
 * @author pradeepta 3434
 */
public class MenuDaoTest {
   private MenuDao dao = new MenuDao();
    private int testItemId = 1;
    private String testItemName = "Test Item";
    private String testCategory = "Test Category";
    private String testDescription = "Test Description";
    private double testPrice = 9.99;
    private byte[] testImage = "test image data".getBytes();

    @Before
    public void setUp() {
        // Clean up any existing test items before each test
        List<MenuData> existingItems = dao.getMenuByCategory(testCategory);
        for (MenuData item : existingItems) {
            if (item.getItemName().equals(testItemName)) {
                dao.deleteMenuItem(item.getItemId()); // Fixed method call
            }
        }
    }

    @After
    public void tearDown() {
        // Clean up any test data after test runs
        List<MenuData> allItems = dao.getAllMenuWithImages();
        for (MenuData item : allItems) {
            if (item.getItemName().startsWith("Test") || item.getItemName().startsWith("Updated")) {
                dao.deleteMenuItem(item.getItemId()); // Fixed method call
            }
        }
    }

    @Test
    public void testAddMenuItemWithValidData() {
        MenuData item = new MenuData();
        item.setItemId(testItemId);
        item.setItemName(testItemName);
        item.setItemCategory(testCategory);
        item.setItemDescription(testDescription);
        item.setItemPrice(testPrice);
        item.setItemImage(testImage);
        item.setRating(0.0);
        item.setReviews("");
        
        boolean result = dao.addMenuItem(item);
        assertTrue("Adding menu item should succeed with valid data", result);
    }

   @Test
    public void testAddMenuItemWithInvalidData() {
        // Test empty/null values
        MenuData emptyItem = new MenuData();
        assertFalse(dao.addMenuItem(emptyItem));

        // Test negative price
        MenuData negativePriceItem = new MenuData();
        negativePriceItem.setItemName("Test");
        negativePriceItem.setItemCategory("Test");
        negativePriceItem.setItemPrice(-1.0);
        assertFalse(dao.addMenuItem(negativePriceItem));

        // Test empty name
        MenuData emptyNameItem = new MenuData();
        emptyNameItem.setItemName("");
        emptyNameItem.setItemCategory("Test");
        emptyNameItem.setItemPrice(10.0);
        assertFalse(dao.addMenuItem(emptyNameItem));

        // Test null category
        MenuData nullCategoryItem = new MenuData();
        nullCategoryItem.setItemName("Test");
        nullCategoryItem.setItemCategory(null);
        nullCategoryItem.setItemPrice(10.0);
        assertFalse(dao.addMenuItem(nullCategoryItem));
    }

@Test
    public void testUpdateMenuItemWithValidData() {
        // First add an item
        MenuData item = new MenuData();
        item.setItemName(testItemName);
        item.setItemCategory(testCategory);
        item.setItemDescription(testDescription);
        item.setItemPrice(testPrice);
        item.setItemImage(testImage);
        item.setRating(0.0);
        item.setReviews("");

        assertTrue("Should add initial item", dao.addMenuItem(item));

        // Get the added item to retrieve its ID
        List<MenuData> items = dao.getMenuByCategory(testCategory);
        MenuData addedItem = items.stream()
                .filter(i -> i.getItemName().equals(testItemName))
                .findFirst()
                .orElse(null);
        assertNotNull("Test item should exist", addedItem);

        // Update the item
        String updatedName = "Updated Test Item";
        String updatedCategory = "Updated Category";
        String updatedDescription = "Updated Description";
        double updatedPrice = 12.99;
        byte[] updatedImage = "updated image data".getBytes();

        MenuData updatedItem = new MenuData();
        updatedItem.setItemId(addedItem.getItemId()); // Use the actual ID from database
        updatedItem.setItemName(updatedName);
        updatedItem.setItemCategory(updatedCategory);
        updatedItem.setItemDescription(updatedDescription);
        updatedItem.setItemPrice(updatedPrice);
        updatedItem.setItemImage(updatedImage);
        updatedItem.setRating(4.5); // Example updated rating
        updatedItem.setReviews("Excellent"); // Example updated review

        assertTrue("Update should succeed", dao.updateMenuItem(updatedItem));

        // Verify the update
        MenuData retrievedItem = dao.getAllMenuWithImages().stream()
                .filter(i -> i.getItemId() == addedItem.getItemId())
                .findFirst()
                .orElse(null);

        assertNotNull("Updated item should exist", retrievedItem);
        assertEquals("Name should be updated", updatedName, retrievedItem.getItemName());
        assertEquals("Category should be updated", updatedCategory, retrievedItem.getItemCategory());
        assertEquals("Description should be updated", updatedDescription, retrievedItem.getItemDescription());
        assertEquals("Price should be updated", updatedPrice, retrievedItem.getItemPrice(), 0.001);
        assertArrayEquals("Image should be updated", updatedImage, retrievedItem.getItemImage());
        assertEquals("Rating should be updated", 13.0, retrievedItem.getRating(), 0.001);
        assertEquals("Reviews should be updated", "Excellent", retrievedItem.getReviews());
    }

        @Test
        public void testDeleteMenuItemWithValidId() {
            // First add an item
            MenuData item = new MenuData();
            item.setItemId(testItemId);
            item.setItemName(testItemName);
            item.setItemCategory(testCategory);
            item.setItemDescription(testDescription);
            item.setItemPrice(testPrice);
            item.setItemImage(testImage);
            item.setRating(0.0);
            item.setReviews("");

            dao.addMenuItem(item);

            // Get the added item to retrieve its ID
            List<MenuData> items = dao.getMenuByCategory(testCategory);
            MenuData addedItem = items.stream()
                    .filter(i -> i.getItemName().equals(testItemName))
                    .findFirst()
                    .orElse(null);
            assertNotNull("Test item should exist", addedItem);

            // Delete the item
            boolean result = dao.deleteMenuItem(addedItem.getItemId());
            assertTrue("Deleting menu item should succeed", result);

            // Verify deletion
            MenuData deletedItem = dao.getAllMenuWithImages().stream()
                    .filter(i -> i.getItemId() == addedItem.getItemId())
                    .findFirst()
                    .orElse(null);
            assertNull("Item should be deleted", deletedItem);
        }

    @Test
    public void testDeleteMenuItemWithInvalidId() {
        boolean result = dao.deleteMenuItem(-1);
        assertFalse("Deleting with invalid ID should fail", result);
    }

    @Test
    public void testGetAllMenuWithImages() {
        // First add an item
        MenuData item = new MenuData();
        item.setItemId(testItemId);
        item.setItemName(testItemName);
        item.setItemCategory(testCategory);
        item.setItemDescription(testDescription);
        item.setItemPrice(testPrice);
        item.setItemImage(testImage);
        item.setRating(0.0);
        item.setReviews("");
        
        dao.addMenuItem(item);

        List<MenuData> menuItems = dao.getAllMenuWithImages();
        assertNotNull("Menu items list should not be null", menuItems);
        assertTrue("Should have at least one menu item", menuItems.size() > 0);

        MenuData testItem = menuItems.stream()
                .filter(i -> i.getItemName().equals(testItemName))
                .findFirst()
                .orElse(null);
        assertNotNull("Test item should be in the list", testItem);
        assertArrayEquals("Image data should match", testImage, testItem.getItemImage());
    }

    @Test
    public void testGetMenuByCategory() {
        // First add an item
        MenuData item = new MenuData();
        item.setItemId(testItemId);
        item.setItemName(testItemName);
        item.setItemCategory(testCategory);
        item.setItemDescription(testDescription);
        item.setItemPrice(testPrice);
        item.setItemImage(testImage);
        item.setRating(0.0);
        item.setReviews("");
        
        dao.addMenuItem(item);

        List<MenuData> items = dao.getMenuByCategory(testCategory);
        assertNotNull("Items list should not be null", items);
        assertTrue("Should have at least one item in category", items.size() > 0);

        MenuData testItem = items.stream()
                .filter(i -> i.getItemName().equals(testItemName))
                .findFirst()
                .orElse(null);
        assertNotNull("Test item should be in the category list", testItem);
        assertEquals("Category should match", testCategory, testItem.getItemCategory());
    }

    @Test
    public void testGetMenuByInvalidCategory() {
        List<MenuData> items = dao.getMenuByCategory("Non-existent Category");
        assertNotNull("Should not return null for invalid category", items);
        assertTrue("Should return empty list for invalid category", items.isEmpty());
    }

    @Test
    public void testGetMenuByNullCategory() {
        List<MenuData> items = dao.getMenuByCategory(null);
        assertNotNull("Should not return null for null category", items);
        assertTrue("Should return empty list for null category", items.isEmpty());
    }
}