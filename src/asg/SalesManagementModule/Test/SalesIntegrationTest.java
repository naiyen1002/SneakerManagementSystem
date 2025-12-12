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
 * Tests Service + DAO working together.
 * Verifies that the components integrate correctly.
 */
public class SalesIntegrationTest {

    private SalesService service;
    private SalesDAO dao;

    @BeforeEach
    public void setUp() {
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

    // ==================== Integration Tests ====================

    @Test
    @Order(1)
    @DisplayName("Test Full CRUD Workflow")
    public void testFullCRUDWorkflow() {
        // CREATE
        SalesItem item = new SalesItem("T001", "M001", "I001", "Nike", "Test Shoe", "Black", 99.99, 2);
        boolean added = service.addSales(item);
        assertTrue(added, "Failed to add item");

        // READ
        List<SalesItem> allItems = service.getAllSales();
        assertEquals(1, allItems.size(), "Expected 1 item");
        assertTrue(service.findById("T001").isPresent(), "Failed to find item");

        // UPDATE
        SalesItem updated = new SalesItem("T002", "M002", "I002", "Puma", "Updated Shoe", "White", 149.99, 5);
        boolean modified = service.updateSales("T001", updated);
        assertTrue(modified, "Failed to update item");
        assertTrue(service.findById("T002").isPresent(), "Updated item not found");
        assertFalse(service.findById("T001").isPresent(), "Old item should not exist");

        // DELETE
        boolean deleted = service.deleteSales("T002");
        assertTrue(deleted, "Failed to delete item");
        assertFalse(service.findById("T002").isPresent(), "Deleted item should not exist");
        assertTrue(service.getAllSales().isEmpty(), "Expected empty list");
    }

    @Test
    @Order(2)
    @DisplayName("Test Search Integration with Strategy Pattern")
    public void testSearchIntegration() {
        // Arrange - Add test data
        service.addSales(new SalesItem("S001", "M001", "I001", "Nike", "Air Jordan", "Black", 199.99, 2));
        service.addSales(new SalesItem("S002", "M002", "I002", "Nike", "Air Max", "White", 149.99, 3));
        service.addSales(new SalesItem("S003", "M003", "I003", "Puma", "Suede", "Black", 99.99, 1));

        // Test search by brand using combined class
        SearchStrategy brandStrategy = new SearchStrategies.ByBrand();
        List<SalesItem> nikeItems = service.search(brandStrategy, "Nike");
        assertEquals(2, nikeItems.size(), "Expected 2 Nike items");

        // Test search by colour using combined class
        SearchStrategy colourStrategy = new SearchStrategies.ByColour();
        List<SalesItem> blackItems = service.search(colourStrategy, "Black");
        assertEquals(2, blackItems.size(), "Expected 2 Black items");

        // Test search by sales ID using combined class
        SearchStrategy idStrategy = new SearchStrategies.BySalesId();
        List<SalesItem> s001Items = service.search(idStrategy, "S001");
        assertEquals(1, s001Items.size(), "Expected 1 item with ID S001");
    }

    @Test
    @Order(3)
    @DisplayName("Test Report Generation Integration")
    public void testReportIntegration() {
        // Arrange - Add test data
        // Item 1: 2 * 100 = 200
        service.addSales(new SalesItem("S001", "M001", "I001", "Nike", "Shoe 1", "Black", 100.00, 2));
        // Item 2: 3 * 50 = 150
        service.addSales(new SalesItem("S002", "M002", "I002", "Puma", "Shoe 2", "White", 50.00, 3));
        // Total: 5 quantity, 350 amount

        // Act
        SalesService.SalesReport report = service.generateReport();

        // Assert
        assertEquals(2, report.getTotalItems(), "Expected 2 items");
        assertEquals(5, report.getTotalQuantity(), "Expected quantity 5");
        assertEquals(350.00, report.getTotalAmount(), "Expected amount 350.00");
    }

    @Test
    @Order(4)
    @DisplayName("Test Singleton Pattern in DAO")
    public void testSingletonIntegration() {
        // Arrange - Add item via service
        service.addSales(new SalesItem("T001", "M001", "I001", "Nike", "Test", "Black", 99.99, 1));

        // Act - Create new service instance - should see same data
        SalesService newService = new SalesService();

        // Assert
        assertTrue(newService.findById("T001").isPresent(), "New service should see same data");
    }
}
