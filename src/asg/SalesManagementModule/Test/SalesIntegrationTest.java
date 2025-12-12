package asg.SalesManagementModule.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import asg.SalesManagementModule.Model.SalesItem;
import asg.SalesManagementModule.Service.SalesDAO;
import asg.SalesManagementModule.Service.SalesService;
import asg.SalesManagementModule.Service.SearchStrategies;
import asg.SalesManagementModule.Service.SearchStrategy;

/**
 * Integration tests for Sales Management Module.
 * 
 * This test class verifies that components work correctly together:
 * - Service and DAO integration for CRUD operations
 * - Strategy Pattern integration for search functionality
 * - Report generation with real data
 * - Singleton Pattern ensuring data consistency across instances
 * 
 * These tests use real implementations (not mocks) to verify end-to-end
 * behavior.
 */
public class SalesIntegrationTest {

    // Service and DAO instances for testing
    private SalesService service;
    private SalesDAO dao;

    // ==================== Setup and Teardown ====================

    /**
     * Resets Singleton and creates fresh instances before each test.
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

    // ==================== Full CRUD Integration Tests ====================

    /**
     * Tests complete Create-Read-Update-Delete workflow.
     * Verifies all CRUD operations work correctly together.
     */
    @Test
    @Order(1)
    @DisplayName("Test Full CRUD Workflow")
    public void testFullCRUDWorkflow() {
        // CREATE
        SalesItem item = new SalesItem("T001", "M001", "I001", "Nike", "Test Shoe", "Black", 99.99, 2);
        boolean added = service.addSales(item);
        assertTrue(added, "CREATE: Should add item successfully");

        // READ
        List<SalesItem> allItems = service.getAllSales();
        assertEquals(1, allItems.size(), "READ: Should have 1 item");
        assertTrue(service.findById("T001").isPresent(), "READ: Should find item by ID");

        // UPDATE
        SalesItem updated = new SalesItem("T002", "M002", "I002", "Puma", "Updated Shoe", "White", 149.99, 5);
        boolean modified = service.updateSales("T001", updated);
        assertTrue(modified, "UPDATE: Should update item successfully");
        assertTrue(service.findById("T002").isPresent(), "UPDATE: New ID should exist");
        assertFalse(service.findById("T001").isPresent(), "UPDATE: Old ID should not exist");

        // DELETE
        boolean deleted = service.deleteSales("T002");
        assertTrue(deleted, "DELETE: Should delete item successfully");
        assertFalse(service.findById("T002").isPresent(), "DELETE: Item should not exist");
        assertTrue(service.getAllSales().isEmpty(), "DELETE: List should be empty");
    }

    // ==================== Search Strategy Integration Tests ====================

    /**
     * Tests Strategy Pattern integration with Service layer.
     * Verifies multiple search strategies work correctly with real data.
     */
    @Test
    @Order(2)
    @DisplayName("Test Search Integration with Strategy Pattern")
    public void testSearchIntegration() {
        // Arrange: Add diverse test data
        service.addSales(new SalesItem("S001", "M001", "I001", "Nike", "Air Jordan", "Black", 199.99, 2));
        service.addSales(new SalesItem("S002", "M002", "I002", "Nike", "Air Max", "White", 149.99, 3));
        service.addSales(new SalesItem("S003", "M003", "I003", "Puma", "Suede", "Black", 99.99, 1));

        // Test: Search by Brand (should find 2 Nike items)
        SearchStrategy brandStrategy = new SearchStrategies.ByBrand();
        List<SalesItem> nikeItems = service.search(brandStrategy, "Nike");
        assertEquals(2, nikeItems.size(), "Should find 2 Nike items");

        // Test: Search by Colour (should find 2 Black items)
        SearchStrategy colourStrategy = new SearchStrategies.ByColour();
        List<SalesItem> blackItems = service.search(colourStrategy, "Black");
        assertEquals(2, blackItems.size(), "Should find 2 Black items");

        // Test: Search by Sales ID (should find 1 item)
        SearchStrategy idStrategy = new SearchStrategies.BySalesId();
        List<SalesItem> s001Items = service.search(idStrategy, "S001");
        assertEquals(1, s001Items.size(), "Should find 1 item with ID S001");
    }

    // ==================== Report Integration Tests ====================

    /**
     * Tests report generation with real calculated totals.
     * Item 1: 2 qty * 100.00 = 200.00
     * Item 2: 3 qty * 50.00 = 150.00
     * Expected: 2 items, 5 total quantity, 350.00 total amount
     */
    @Test
    @Order(3)
    @DisplayName("Test Report Generation Integration")
    public void testReportIntegration() {
        // Arrange
        service.addSales(new SalesItem("S001", "M001", "I001", "Nike", "Shoe 1", "Black", 100.00, 2));
        service.addSales(new SalesItem("S002", "M002", "I002", "Puma", "Shoe 2", "White", 50.00, 3));

        // Act
        SalesService.SalesReport report = service.generateReport();

        // Assert
        assertEquals(2, report.getTotalItems(), "Should have 2 items");
        assertEquals(5, report.getTotalQuantity(), "Total quantity should be 5");
        assertEquals(350.00, report.getTotalAmount(), "Total amount should be 350.00");
    }

    // ==================== Singleton Integration Tests ====================

    /**
     * Tests that Singleton Pattern ensures data consistency across service
     * instances.
     * Data added via one service instance should be visible to another instance.
     */
    @Test
    @Order(4)
    @DisplayName("Test Singleton Pattern in DAO")
    public void testSingletonIntegration() {
        // Arrange: Add item via first service instance
        service.addSales(new SalesItem("T001", "M001", "I001", "Nike", "Test", "Black", 99.99, 1));

        // Act: Create new service instance (should use same DAO Singleton)
        SalesService newService = new SalesService();

        // Assert: New service should see the same data
        assertTrue(newService.findById("T001").isPresent(),
                "New service instance should see data from first instance");
    }
}
