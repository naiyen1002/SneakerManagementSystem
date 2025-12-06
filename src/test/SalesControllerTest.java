package test;

import controller.SalesController;
import model.Sale;
import service.SalesService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Unit tests for the SalesController class.
 * Tests all CRUD operations, search functionality, and validation methods.
 */
public class SalesControllerTest {

    private SalesController controller;
    private Sale testSale;

    @BeforeEach
    void setUp() {
        controller = new SalesController();
        testSale = new Sale("S999", "M999", "I999", "NIKE", 
                           "Test Sneaker", "Red", 150.00, 3);
    }

    // CRUD Operations Tests - Add

    @Test
    @DisplayName("Test add valid sale")
    void testAddValidSale() {
        assertTrue(controller.addSale(testSale));
        assertEquals(6, controller.getSalesCount()); // 5 default + 1 new
    }

    @Test
    @DisplayName("Test add duplicate sale ID")
    void testAddDuplicateSaleId() {
        controller.addSale(testSale);
        Sale duplicate = new Sale("S999", "M888", "I888", "PUMA", 
                                  "Another", "Blue", 100.00, 1);
        assertFalse(controller.addSale(duplicate));
        assertEquals(6, controller.getSalesCount());
    }

    @Test
    @DisplayName("Test add null sale")
    void testAddNullSale() {
        assertFalse(controller.addSale(null));
        assertEquals(5, controller.getSalesCount());
    }

    @Test
    @DisplayName("Test add invalid sale")
    void testAddInvalidSale() {
        Sale invalidSale = new Sale("S1", "M999", "I999", "NIKE", 
                                    "Test", "Red", 150.00, 3);
        assertFalse(controller.addSale(invalidSale));
        assertEquals(5, controller.getSalesCount());
    }

    // CRUD Operations Tests - Delete

    @Test
    @DisplayName("Test delete existing sale")
    void testDeleteExistingSale() {
        assertTrue(controller.deleteSale("S001"));
        assertEquals(4, controller.getSalesCount());
        assertNull(controller.getSaleById("S001"));
    }

    @Test
    @DisplayName("Test delete non-existing sale")
    void testDeleteNonExistingSale() {
        assertFalse(controller.deleteSale("S999"));
        assertEquals(5, controller.getSalesCount());
    }

    @Test
    @DisplayName("Test delete with null ID")
    void testDeleteWithNullId() {
        assertFalse(controller.deleteSale(null));
        assertEquals(5, controller.getSalesCount());
    }

    // CRUD Operations Tests - Update

    @Test
    @DisplayName("Test update existing sale")
    void testUpdateExistingSale() {
        Sale updatedSale = new Sale("S001", "M999", "I999", "PUMA", 
                                    "Updated", "Blue", 200.00, 5);
        assertTrue(controller.updateSale("S001", updatedSale));
        
        Sale retrieved = controller.getSaleById("S001");
        assertEquals("M999", retrieved.getMemberId());
        assertEquals("PUMA", retrieved.getBrand());
    }

    @Test
    @DisplayName("Test update non-existing sale")
    void testUpdateNonExistingSale() {
        assertFalse(controller.updateSale("S999", testSale));
    }

    @Test
    @DisplayName("Test update with null sale")
    void testUpdateWithNullSale() {
        assertFalse(controller.updateSale("S001", null));
    }

    @Test
    @DisplayName("Test update with invalid sale")
    void testUpdateWithInvalidSale() {
        Sale invalidSale = new Sale("S1", "M999", "I999", "NIKE", 
                                    "Test", "Red", 150.00, 3);
        assertFalse(controller.updateSale("S001", invalidSale));
    }

    // CRUD Operations Tests - Get

    @Test
    @DisplayName("Test get sale by existing ID")
    void testGetSaleByExistingId() {
        Sale sale = controller.getSaleById("S001");
        assertNotNull(sale);
        assertEquals("S001", sale.getSalesId());
        assertEquals("M101", sale.getMemberId());
    }

    @Test
    @DisplayName("Test get sale by non-existing ID")
    void testGetSaleByNonExistingId() {
        Sale sale = controller.getSaleById("S999");
        assertNull(sale);
    }

    @Test
    @DisplayName("Test get all sales")
    void testGetAllSales() {
        List<Sale> sales = controller.getAllSales();
        assertEquals(5, sales.size());
    }

    @Test
    @DisplayName("Test get all sales returns copy")
    void testGetAllSalesReturnsCopy() {
        List<Sale> sales = controller.getAllSales();
        sales.clear();
        assertEquals(5, controller.getSalesCount());
    }

    // Search Operations Tests

    @Test
    @DisplayName("Test search by sales ID - found")
    void testSearchBySalesIdFound() {
        List<Sale> results = controller.searchBySalesId("S001");
        assertEquals(1, results.size());
        assertEquals("S001", results.get(0).getSalesId());
    }

    @Test
    @DisplayName("Test search by sales ID - not found")
    void testSearchBySalesIdNotFound() {
        List<Sale> results = controller.searchBySalesId("S999");
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Test search by sales ID - case insensitive")
    void testSearchBySalesIdCaseInsensitive() {
        List<Sale> results = controller.searchBySalesId("s002");
        assertEquals(1, results.size());
        assertEquals("S002", results.get(0).getSalesId());
    }

    @Test
    @DisplayName("Test search by member ID")
    void testSearchByMemberId() {
        List<Sale> results = controller.searchByMemberId("M101");
        assertEquals(1, results.size());
        assertEquals("M101", results.get(0).getMemberId());
    }

    @Test
    @DisplayName("Test search by item code")
    void testSearchByItemCode() {
        List<Sale> results = controller.searchByItemCode("I001");
        assertEquals(1, results.size());
        assertEquals("I001", results.get(0).getItemCode());
    }

    @Test
    @DisplayName("Test search by brand - multiple results")
    void testSearchByBrandMultipleResults() {
        List<Sale> results = controller.searchByBrand("PUMA");
        assertEquals(3, results.size());
        for (Sale sale : results) {
            assertEquals("PUMA", sale.getBrand());
        }
    }

    @Test
    @DisplayName("Test search by brand - not found")
    void testSearchByBrandNotFound() {
        List<Sale> results = controller.searchByBrand("REEBOK");
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Test search by description")
    void testSearchByDescription() {
        List<Sale> results = controller.searchByDescription("Athletic");
        assertEquals(3, results.size());
    }

    @Test
    @DisplayName("Test search by description - partial match")
    void testSearchByDescriptionPartialMatch() {
        List<Sale> results = controller.searchByDescription("Sneakers");
        assertTrue(results.size() >= 2);
    }

    @Test
    @DisplayName("Test search by colour")
    void testSearchByColour() {
        List<Sale> results = controller.searchByColour("White");
        assertEquals(3, results.size());
    }

    @Test
    @DisplayName("Test search by price")
    void testSearchByPrice() {
        List<Sale> results = controller.searchByPrice(89.99);
        assertEquals(1, results.size());
        assertEquals(89.99, results.get(0).getItemPrice(), 0.01);
    }

    @Test
    @DisplayName("Test search by price - no exact match")
    void testSearchByPriceNoMatch() {
        List<Sale> results = controller.searchByPrice(999.99);
        assertTrue(results.isEmpty());
    }

    @Test
    @DisplayName("Test search by quantity")
    void testSearchByQuantity() {
        List<Sale> results = controller.searchByQuantity(9);
        assertEquals(1, results.size());
        assertEquals(9, results.get(0).getQuantitySales());
    }

    // Validation Tests

    @Test
    @DisplayName("Test sales ID is unique - true")
    void testSalesIdIsUniqueTrue() {
        assertTrue(controller.isSalesIdUnique("S999"));
    }

    @Test
    @DisplayName("Test sales ID is unique - false")
    void testSalesIdIsUniqueFalse() {
        assertFalse(controller.isSalesIdUnique("S001"));
    }

    @Test
    @DisplayName("Test member ID is unique - true")
    void testMemberIdIsUniqueTrue() {
        assertTrue(controller.isMemberIdUnique("M999"));
    }

    @Test
    @DisplayName("Test member ID is unique - false")
    void testMemberIdIsUniqueFalse() {
        assertFalse(controller.isMemberIdUnique("M101"));
    }

    @Test
    @DisplayName("Test item code is unique - true")
    void testItemCodeIsUniqueTrue() {
        assertTrue(controller.isItemCodeUnique("I999"));
    }

    @Test
    @DisplayName("Test item code is unique - false")
    void testItemCodeIsUniqueFalse() {
        assertFalse(controller.isItemCodeUnique("I001"));
    }

    @Test
    @DisplayName("Test validate sale - valid")
    void testValidateSaleValid() {
        assertTrue(controller.validateSale(testSale));
    }

    @Test
    @DisplayName("Test validate sale - null")
    void testValidateSaleNull() {
        assertFalse(controller.validateSale(null));
    }

    @Test
    @DisplayName("Test validate sale - invalid")
    void testValidateSaleInvalid() {
        Sale invalidSale = new Sale("S1", "M999", "I999", "NIKE", 
                                    "Test", "Red", -10.00, 3);
        assertFalse(controller.validateSale(invalidSale));
    }

    // Report and Statistics Tests

    @Test
    @DisplayName("Test generate sales report")
    void testGenerateSalesReport() {
        SalesService.SalesStatistics stats = controller.generateSalesReport();
        assertNotNull(stats);
        assertEquals(5, stats.getTotalSalesItems());
        assertTrue(stats.getTotalQuantitySold() > 0);
        assertTrue(stats.getTotalRevenue() > 0);
    }

    @Test
    @DisplayName("Test generate sales report - empty list")
    void testGenerateSalesReportEmptyList() {
        controller.clearAllSales();
        SalesService.SalesStatistics stats = controller.generateSalesReport();
        assertNotNull(stats);
        assertEquals(0, stats.getTotalSalesItems());
        assertEquals(0, stats.getTotalQuantitySold());
        assertEquals(0.0, stats.getTotalRevenue(), 0.01);
    }

    // Clear and Count Tests

    @Test
    @DisplayName("Test clear all sales")
    void testClearAllSales() {
        controller.clearAllSales();
        assertEquals(0, controller.getSalesCount());
        assertTrue(controller.getAllSales().isEmpty());
    }

    @Test
    @DisplayName("Test get sales count")
    void testGetSalesCount() {
        assertEquals(5, controller.getSalesCount());
        controller.addSale(testSale);
        assertEquals(6, controller.getSalesCount());
        controller.deleteSale("S001");
        assertEquals(5, controller.getSalesCount());
    }

    // Integration Tests

    @Test
    @DisplayName("Test complete CRUD workflow")
    void testCompleteCRUDWorkflow() {
        // Add
        assertTrue(controller.addSale(testSale));
        assertEquals(6, controller.getSalesCount());
        
        // Read
        Sale retrieved = controller.getSaleById("S999");
        assertNotNull(retrieved);
        assertEquals("Test Sneaker", retrieved.getItemDescription());
        
        // Update
        Sale updated = new Sale("S999", "M888", "I888", "ADIDAS", 
                               "Updated Sneaker", "Blue", 200.00, 5);
        assertTrue(controller.updateSale("S999", updated));
        retrieved = controller.getSaleById("S999");
        assertEquals("Updated Sneaker", retrieved.getItemDescription());
        
        // Delete
        assertTrue(controller.deleteSale("S999"));
        assertNull(controller.getSaleById("S999"));
        assertEquals(5, controller.getSalesCount());
    }

    @Test
    @DisplayName("Test search workflow")
    void testSearchWorkflow() {
        controller.addSale(testSale);
        
        // Search by different criteria
        assertEquals(1, controller.searchBySalesId("S999").size());
        assertEquals(1, controller.searchByMemberId("M999").size());
        assertEquals(2, controller.searchByBrand("NIKE").size()); // S001 (default) + S999 (test)
        assertEquals(1, controller.searchByColour("Red").size());
        
        controller.deleteSale("S999");
        assertTrue(controller.searchBySalesId("S999").isEmpty());
    }
}
