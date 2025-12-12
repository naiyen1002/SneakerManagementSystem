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
 * Tests business logic including add, delete, modify, and search operations.
 * Target: 90%+ coverage of business logic.
 */
public class SalesServiceTest {

    private SalesService service;
    private SalesDAO dao;

    @BeforeEach
    public void setUp() {
        // Reset singleton instance for clean test state
        SalesDAO.resetInstance();
        dao = SalesDAO.getInstance();
        dao.clearAll();
        service = new SalesService(dao);
    }

    @AfterEach
    public void tearDown() {
        dao.clearAll();
        SalesDAO.resetInstance();
    }

    // ==================== Add Sales Tests ====================

    @Test
    @Order(1)
    @DisplayName("Test Add Sales - Valid Item Returns True")
    public void testAddSales_ValidItem_ReturnsTrue() {
        // Arrange
        SalesItem item = createTestItem("T001", "M001", "I001");

        // Act
        boolean result = service.addSales(item);

        // Assert
        assertTrue(result, "Expected addSales to return true for valid item");
        assertTrue(service.findById("T001").isPresent(), "Expected item to be found after adding");
    }

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
        assertFalse(result, "Expected addSales to return false for duplicate ID");
    }

    // ==================== Delete Sales Tests ====================

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
        assertTrue(result, "Expected deleteSales to return true for existing ID");
        assertFalse(service.findById("T001").isPresent(), "Expected item to be deleted");
    }

    @Test
    @Order(4)
    @DisplayName("Test Delete Sales - Non-Existing ID Returns False")
    public void testDeleteSales_NonExistingId_ReturnsFalse() {
        // Act
        boolean result = service.deleteSales("XXXX");

        // Assert
        assertFalse(result, "Expected deleteSales to return false for non-existing ID");
    }

    // ==================== Update Sales Tests ====================

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
        assertTrue(result, "Expected updateSales to return true");
        assertTrue(service.findById("T002").isPresent(), "Expected updated item to be found");
        assertFalse(service.findById("T001").isPresent(), "Expected original ID to not exist");
    }

    // ==================== Search Tests ====================

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
        assertEquals(2, allSales.size(), "Expected 2 items");
    }

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
        assertTrue(result.isPresent(), "Expected to find item");
        assertEquals("T001", result.get().getSalesId(), "Expected correct sales ID");
    }

    @Test
    @Order(8)
    @DisplayName("Test Find By ID - Non-Existing ID Returns Empty")
    public void testFindById_NonExistingId_ReturnsEmpty() {
        // Act
        Optional<SalesItem> result = service.findById("XXXX");

        // Assert
        assertFalse(result.isPresent(), "Expected empty result for non-existing ID");
    }

    // ==================== Report Tests ====================

    @Test
    @Order(9)
    @DisplayName("Test Generate Report - Returns Correct Totals")
    public void testGenerateReport_ReturnsCorrectTotals() {
        // Arrange - Add test items: 2 quantity at 100.00 = 200.00 total
        SalesItem item1 = new SalesItem("T001", "M001", "I001", "Nike", "Test Shoe", "Black", 100.00, 2);
        // 3 quantity at 50.00 = 150.00 total
        SalesItem item2 = new SalesItem("T002", "M002", "I002", "Puma", "Test Shoe 2", "White", 50.00, 3);
        service.addSales(item1);
        service.addSales(item2);

        // Act
        SalesService.SalesReport report = service.generateReport();

        // Assert
        assertEquals(2, report.getTotalItems(), "Expected 2 items");
        assertEquals(5, report.getTotalQuantity(), "Expected total quantity of 5");
        assertEquals(350.00, report.getTotalAmount(), "Expected total amount of 350.00");
    }

    // ==================== Validation Tests ====================

    @Test
    @Order(10)
    @DisplayName("Test Is Unique Sales ID - New ID Returns True")
    public void testIsUniqueSalesId_NewId_ReturnsTrue() {
        // Act
        boolean result = service.isUniqueSalesId("NEW1");

        // Assert
        assertTrue(result, "Expected new ID to be unique");
    }

    @Test
    @Order(11)
    @DisplayName("Test Is Unique Sales ID - Existing ID Returns False")
    public void testIsUniqueSalesId_ExistingId_ReturnsFalse() {
        // Arrange
        service.addSales(createTestItem("T001", "M001", "I001"));

        // Act
        boolean result = service.isUniqueSalesId("T001");

        // Assert
        assertFalse(result, "Expected existing ID to not be unique");
    }

    // ==================== Helper Methods ====================

    private SalesItem createTestItem(String salesId, String memberId, String itemCode) {
        return new SalesItem(salesId, memberId, itemCode, "Nike", "Test Sneaker", "Black", 99.99, 1);
    }
}
