package asg.SalesManagementModule.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import asg.SalesManagementModule.Model.SalesItem;
import asg.SalesManagementModule.Service.SalesDAO;

/**
 * Unit tests for SalesDAO class.
 * 
 * This test class validates the Singleton Data Access Object including:
 * - Singleton pattern implementation
 * - CRUD operations (Create, Read, Update, Delete)
 * - Existence checking methods
 * 
 * Tests reset the Singleton instance before/after each test for isolation.
 */
public class SalesDAOTest {

    // DAO instance for testing
    private SalesDAO dao;

    // ==================== Setup and Teardown ====================

    /**
     * Resets Singleton and creates fresh DAO instance before each test.
     */
    @BeforeEach
    public void setUp() {
        SalesDAO.resetInstance();
        dao = SalesDAO.getInstance();
        dao.clearAll();
    }

    /**
     * Cleans up DAO data and resets Singleton after each test.
     */
    @AfterEach
    public void tearDown() {
        dao.clearAll();
        SalesDAO.resetInstance();
    }

    // ==================== Singleton Tests ====================

    /**
     * Tests that getInstance() always returns the same instance.
     * Verifies the Singleton pattern is correctly implemented.
     */
    @Test
    @Order(1)
    @DisplayName("Test Singleton - Get Instance Returns Same Instance")
    public void testGetInstance_ReturnsSameInstance() {
        // Arrange & Act
        SalesDAO instance1 = SalesDAO.getInstance();
        SalesDAO instance2 = SalesDAO.getInstance();

        // Assert: Same object reference
        assertTrue(instance1 == instance2, "Should return same Singleton instance");
    }

    // ==================== Save Tests ====================

    /**
     * Tests saving a new item successfully.
     */
    @Test
    @Order(2)
    @DisplayName("Test Save - New Item Returns True")
    public void testSave_NewItem_ReturnsTrue() {
        // Arrange
        SalesItem item = createTestItem("T001");

        // Act
        boolean result = dao.save(item);

        // Assert
        assertTrue(result, "Save should return true for new item");
        assertEquals(1, dao.count(), "Count should be 1 after save");
    }

    /**
     * Tests that saving an item with duplicate ID fails.
     */
    @Test
    @Order(3)
    @DisplayName("Test Save - Duplicate ID Returns False")
    public void testSave_DuplicateId_ReturnsFalse() {
        // Arrange
        dao.save(createTestItem("T001"));

        // Act
        boolean result = dao.save(createTestItem("T001"));

        // Assert
        assertFalse(result, "Save should return false for duplicate ID");
        assertEquals(1, dao.count(), "Count should still be 1");
    }

    // ==================== Find Tests ====================

    /**
     * Tests finding an existing item by ID.
     */
    @Test
    @Order(4)
    @DisplayName("Test Find By ID - Existing ID Returns Item")
    public void testFindById_ExistingId_ReturnsItem() {
        // Arrange
        dao.save(createTestItem("T001"));

        // Act
        Optional<SalesItem> result = dao.findById("T001");

        // Assert
        assertTrue(result.isPresent(), "Should find existing item");
        assertEquals("T001", result.get().getSalesId(), "ID should match");
    }

    /**
     * Tests that finding a non-existent item returns empty Optional.
     */
    @Test
    @Order(5)
    @DisplayName("Test Find By ID - Non-Existing ID Returns Empty")
    public void testFindById_NonExistingId_ReturnsEmpty() {
        // Act
        Optional<SalesItem> result = dao.findById("XXXX");

        // Assert
        assertFalse(result.isPresent(), "Should return empty for non-existent ID");
    }

    // ==================== Delete Tests ====================

    /**
     * Tests successfully deleting an existing item.
     */
    @Test
    @Order(6)
    @DisplayName("Test Delete - Existing ID Returns True")
    public void testDelete_ExistingId_ReturnsTrue() {
        // Arrange
        dao.save(createTestItem("T001"));

        // Act
        boolean result = dao.delete("T001");

        // Assert
        assertTrue(result, "Delete should return true for existing item");
        assertEquals(0, dao.count(), "Count should be 0 after delete");
    }

    /**
     * Tests that deleting a non-existent item fails.
     */
    @Test
    @Order(7)
    @DisplayName("Test Delete - Non-Existing ID Returns False")
    public void testDelete_NonExistingId_ReturnsFalse() {
        // Act
        boolean result = dao.delete("XXXX");

        // Assert
        assertFalse(result, "Delete should return false for non-existent ID");
    }

    // ==================== Update Tests ====================

    /**
     * Tests successfully updating an existing item.
     */
    @Test
    @Order(8)
    @DisplayName("Test Update - Existing ID Returns True")
    public void testUpdate_ExistingId_ReturnsTrue() {
        // Arrange
        dao.save(createTestItem("T001"));
        SalesItem updated = new SalesItem("T002", "M002", "I002", "Puma", "Updated", "White", 199.99, 5);

        // Act
        boolean result = dao.update("T001", updated);

        // Assert
        assertTrue(result, "Update should return true");
        assertTrue(dao.findById("T002").isPresent(), "Updated item should exist with new ID");
    }

    // ==================== Exists Tests ====================

    /**
     * Tests existsById() returns true for existing item.
     */
    @Test
    @Order(9)
    @DisplayName("Test Exists By ID - Existing ID Returns True")
    public void testExistsById_ExistingId_ReturnsTrue() {
        // Arrange
        dao.save(createTestItem("T001"));

        // Assert
        assertTrue(dao.existsById("T001"), "Should return true for existing ID");
    }

    /**
     * Tests existsById() returns false for non-existent item.
     */
    @Test
    @Order(10)
    @DisplayName("Test Exists By ID - Non-Existing ID Returns False")
    public void testExistsById_NonExistingId_ReturnsFalse() {
        // Assert
        assertFalse(dao.existsById("XXXX"), "Should return false for non-existent ID");
    }

    // ==================== Helper Methods ====================

    /**
     * Creates a standard test SalesItem for reuse across tests.
     * 
     * @param salesId The unique Sales ID for the item
     * @return A new SalesItem with default test values
     */
    private SalesItem createTestItem(String salesId) {
        return new SalesItem(salesId, "M001", "I001", "Nike", "Test", "Black", 99.99, 1);
    }
}
