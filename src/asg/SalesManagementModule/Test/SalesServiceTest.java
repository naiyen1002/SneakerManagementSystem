package asg.SalesManagementModule.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import asg.SalesManagementModule.Model.SalesItem;
import asg.SalesManagementModule.Service.SalesDAO;
import asg.SalesManagementModule.Service.SalesService;

/**
 * Unit tests for SalesService class.
 * 
 * This test class validates the business logic layer including:
 * - Add, delete, and update operations with validation
 * - Search and retrieval operations
 * - Report generation
 * - Uniqueness checking methods
 * 
 * Uses Dependency Injection with a fresh DAO instance for each test.
 */
public class SalesServiceTest {

    // Service and DAO instances for testing
    private SalesService service;
    private SalesDAO dao;

    // ==================== Setup and Teardown ====================

    /**
     * Resets Singleton and creates fresh service instance before each test.
     */
    @BeforeEach
    public void setUp() {
        SalesDAO.resetInstance();
        dao = SalesDAO.getInstance();
        dao.clearAll();
        service = new SalesService(dao);
    }

    /**
     * Cleans up data and resets Singleton after each test.
     */
    @AfterEach
    public void tearDown() {
        dao.clearAll();
        SalesDAO.resetInstance();
    }

    // ==================== Add Sales Tests ====================

    /**
     * Tests that adding a valid item succeeds.
     */
    @Test
    @Order(1)
    @DisplayName("Test Add Sales - Valid Item Returns True")
    public void testAddSales_ValidItem_ReturnsTrue() {
        // Arrange
        SalesItem item = createTestItem("T001", "M001", "I001");

        // Act
        boolean result = service.addSales(item);

        // Assert
        assertTrue(result, "Should return true for valid item");
        assertTrue(service.findById("T001").isPresent(), "Item should be findable after adding");
    }

    /**
     * Tests that adding an item with duplicate ID fails.
     */
    @Test
    @Order(2)
    @DisplayName("Test Add Sales - Duplicate ID Returns False")
    public void testAddSales_DuplicateId_ReturnsFalse() {
        // Arrange
        SalesItem item1 = createTestItem("T001", "M001", "I001");
        SalesItem item2 = createTestItem("T001", "M002", "I002");
        service.addSales(item1);

        // Act
        boolean result = service.addSales(item2);

        // Assert
        assertFalse(result, "Should return false for duplicate ID");
    }

    // ==================== Delete Sales Tests ====================

    /**
     * Tests successfully deleting an existing item.
     */
    @Test
    @Order(3)
    @DisplayName("Test Delete Sales - Existing ID Returns True")
    public void testDeleteSales_ExistingId_ReturnsTrue() {
        // Arrange
        SalesItem item = createTestItem("T001", "M001", "I001");
        service.addSales(item);

        // Act
        boolean result = service.deleteSales("T001");

        // Assert
        assertTrue(result, "Should return true for existing ID");
        assertFalse(service.findById("T001").isPresent(), "Item should not exist after delete");
    }

    /**
     * Tests that deleting a non-existent item fails.
     */
    @Test
    @Order(4)
    @DisplayName("Test Delete Sales - Non-Existing ID Returns False")
    public void testDeleteSales_NonExistingId_ReturnsFalse() {
        // Act
        boolean result = service.deleteSales("XXXX");

        // Assert
        assertFalse(result, "Should return false for non-existent ID");
    }

    // ==================== Update Sales Tests ====================

    /**
     * Tests successfully updating an existing item.
     */
    @Test
    @Order(5)
    @DisplayName("Test Update Sales - Existing Item Returns True")
    public void testUpdateSales_ExistingItem_ReturnsTrue() {
        // Arrange
        SalesItem original = createTestItem("T001", "M001", "I001");
        service.addSales(original);
        SalesItem updated = createTestItem("T002", "M002", "I002");

        // Act
        boolean result = service.updateSales("T001", updated);

        // Assert
        assertTrue(result, "Should return true for valid update");
        assertTrue(service.findById("T002").isPresent(), "Updated item should exist");
        assertFalse(service.findById("T001").isPresent(), "Original ID should not exist");
    }

    // ==================== Search Tests ====================

    /**
     * Tests that getAllSales() returns all items.
     */
    @Test
    @Order(6)
    @DisplayName("Test Get All Sales - Returns All Items")
    public void testGetAllSales_ReturnsAllItems() {
        // Arrange
        service.addSales(createTestItem("T001", "M001", "I001"));
        service.addSales(createTestItem("T002", "M002", "I002"));

        // Act
        List<SalesItem> allSales = service.getAllSales();

        // Assert
        assertEquals(2, allSales.size(), "Should return 2 items");
    }

    /**
     * Tests finding an existing item by ID.
     */
    @Test
    @Order(7)
    @DisplayName("Test Find By ID - Existing ID Returns Item")
    public void testFindById_ExistingId_ReturnsItem() {
        // Arrange
        SalesItem item = createTestItem("T001", "M001", "I001");
        service.addSales(item);

        // Act
        Optional<SalesItem> result = service.findById("T001");

        // Assert
        assertTrue(result.isPresent(), "Should find existing item");
        assertEquals("T001", result.get().getSalesId(), "ID should match");
    }

    /**
     * Tests that finding a non-existent item returns empty.
     */
    @Test
    @Order(8)
    @DisplayName("Test Find By ID - Non-Existing ID Returns Empty")
    public void testFindById_NonExistingId_ReturnsEmpty() {
        // Act
        Optional<SalesItem> result = service.findById("XXXX");

        // Assert
        assertFalse(result.isPresent(), "Should return empty for non-existent ID");
    }

    // ==================== Report Tests ====================

    /**
     * Tests that report generation calculates correct totals.
     * Item 1: 2 qty * 100.00 = 200.00
     * Item 2: 3 qty * 50.00 = 150.00
     * Total: 5 qty, 350.00 amount
     */
    @Test
    @Order(9)
    @DisplayName("Test Generate Report - Returns Correct Totals")
    public void testGenerateReport_ReturnsCorrectTotals() {
        // Arrange
        SalesItem item1 = new SalesItem("T001", "M001", "I001", "Nike", "Test Shoe", "Black", 100.00, 2);
        SalesItem item2 = new SalesItem("T002", "M002", "I002", "Puma", "Test Shoe 2", "White", 50.00, 3);
        service.addSales(item1);
        service.addSales(item2);

        // Act
        SalesService.SalesReport report = service.generateReport();

        // Assert
        assertEquals(2, report.getTotalItems(), "Should have 2 items");
        assertEquals(5, report.getTotalQuantity(), "Total quantity should be 5");
        assertEquals(350.00, report.getTotalAmount(), "Total amount should be 350.00");
    }

    // ==================== Validation Tests ====================

    /**
     * Tests that a new ID is considered unique.
     */
    @Test
    @Order(10)
    @DisplayName("Test Is Unique Sales ID - New ID Returns True")
    public void testIsUniqueSalesId_NewId_ReturnsTrue() {
        // Act
        boolean result = service.isUniqueSalesId("NEW1");

        // Assert
        assertTrue(result, "New ID should be unique");
    }

    /**
     * Tests that an existing ID is not considered unique.
     */
    @Test
    @Order(11)
    @DisplayName("Test Is Unique Sales ID - Existing ID Returns False")
    public void testIsUniqueSalesId_ExistingId_ReturnsFalse() {
        // Arrange
        service.addSales(createTestItem("T001", "M001", "I001"));

        // Act
        boolean result = service.isUniqueSalesId("T001");

        // Assert
        assertFalse(result, "Existing ID should not be unique");
    }

    // ==================== Helper Methods ====================

    /**
     * Creates a standard test SalesItem for reuse across tests.
     * 
     * @param salesId  The unique Sales ID
     * @param memberId The Member ID
     * @param itemCode The Item Code
     * @return A new SalesItem with default test values
     */
    private SalesItem createTestItem(String salesId, String memberId, String itemCode) {
        return new SalesItem(salesId, memberId, itemCode, "Nike", "Test Sneaker", "Black", 99.99, 1);
    }
}
