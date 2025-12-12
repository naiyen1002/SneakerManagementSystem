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
 * Tests CRUD operations for the Singleton data access object.
 * Target: 80%+ coverage of data access logic.
 */
public class SalesDAOTest {

    private SalesDAO dao;

    @BeforeEach
    public void setUp() {
        SalesDAO.resetInstance();
        dao = SalesDAO.getInstance();
        dao.clearAll();
    }

    @AfterEach
    public void tearDown() {
        dao.clearAll();
        SalesDAO.resetInstance();
    }

    // ==================== Singleton Tests ====================

    @Test
    @Order(1)
    @DisplayName("Test Singleton - Get Instance Returns Same Instance")
    public void testGetInstance_ReturnsSameInstance() {
        // Arrange & Act
        SalesDAO instance1 = SalesDAO.getInstance();
        SalesDAO instance2 = SalesDAO.getInstance();

        // Assert
        assertTrue(instance1 == instance2, "Expected same instance (Singleton)");
    }

    // ==================== Save Tests ====================

    @Test
    @Order(2)
    @DisplayName("Test Save - New Item Returns True")
    public void testSave_NewItem_ReturnsTrue() {
        // Arrange
        SalesItem item = createTestItem("T001");

        // Act
        boolean result = dao.save(item);

        // Assert
        assertTrue(result, "Expected save to return true");
        assertEquals(1, dao.count(), "Expected count to be 1");
    }

    @Test
    @Order(3)
    @DisplayName("Test Save - Duplicate ID Returns False")
    public void testSave_DuplicateId_ReturnsFalse() {
        // Arrange
        dao.save(createTestItem("T001"));

        // Act
        boolean result = dao.save(createTestItem("T001"));

        // Assert
        assertFalse(result, "Expected save to return false for duplicate");
        assertEquals(1, dao.count(), "Expected count to still be 1");
    }

    // ==================== Find Tests ====================

    @Test
    @Order(4)
    @DisplayName("Test Find By ID - Existing ID Returns Item")
    public void testFindById_ExistingId_ReturnsItem() {
        // Arrange
        dao.save(createTestItem("T001"));

        // Act
        Optional<SalesItem> result = dao.findById("T001");

        // Assert
        assertTrue(result.isPresent(), "Expected to find item");
        assertEquals("T001", result.get().getSalesId(), "Expected correct ID");
    }

    @Test
    @Order(5)
    @DisplayName("Test Find By ID - Non-Existing ID Returns Empty")
    public void testFindById_NonExistingId_ReturnsEmpty() {
        // Act
        Optional<SalesItem> result = dao.findById("XXXX");

        // Assert
        assertFalse(result.isPresent(), "Expected empty result");
    }

    // ==================== Delete Tests ====================

    @Test
    @Order(6)
    @DisplayName("Test Delete - Existing ID Returns True")
    public void testDelete_ExistingId_ReturnsTrue() {
        // Arrange
        dao.save(createTestItem("T001"));

        // Act
        boolean result = dao.delete("T001");

        // Assert
        assertTrue(result, "Expected delete to return true");
        assertEquals(0, dao.count(), "Expected count to be 0");
    }

    @Test
    @Order(7)
    @DisplayName("Test Delete - Non-Existing ID Returns False")
    public void testDelete_NonExistingId_ReturnsFalse() {
        // Act
        boolean result = dao.delete("XXXX");

        // Assert
        assertFalse(result, "Expected delete to return false");
    }

    // ==================== Update Tests ====================

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
        assertTrue(result, "Expected update to return true");
        assertTrue(dao.findById("T002").isPresent(), "Expected to find updated item");
    }

    // ==================== Exists Tests ====================

    @Test
    @Order(9)
    @DisplayName("Test Exists By ID - Existing ID Returns True")
    public void testExistsById_ExistingId_ReturnsTrue() {
        // Arrange
        dao.save(createTestItem("T001"));

        // Assert
        assertTrue(dao.existsById("T001"), "Expected existsById to return true");
    }

    @Test
    @Order(10)
    @DisplayName("Test Exists By ID - Non-Existing ID Returns False")
    public void testExistsById_NonExistingId_ReturnsFalse() {
        // Assert
        assertFalse(dao.existsById("XXXX"), "Expected existsById to return false");
    }

    // ==================== Helper Methods ====================

    private SalesItem createTestItem(String salesId) {
        return new SalesItem(salesId, "M001", "I001", "Nike", "Test", "Black", 99.99, 1);
    }
}
