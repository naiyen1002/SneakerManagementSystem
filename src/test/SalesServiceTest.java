package test;

import model.Sale;
import service.SalesService;
import service.SalesService.SalesStatistics;
import service.SalesService.ValidationResult;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the SalesService class.
 * Tests business logic, calculations, and validation methods.
 */
public class SalesServiceTest {

    private SalesService service;
    private List<Sale> testSales;

    @BeforeEach
    void setUp() {
        service = new SalesService();
        testSales = new ArrayList<>();
        
        testSales.add(new Sale("S001", "M101", "I001", "NIKE", "Air Max", "Black", 100.00, 2));
        testSales.add(new Sale("S002", "M102", "I002", "PUMA", "Suede", "White", 80.00, 3));
        testSales.add(new Sale("S003", "M103", "I003", "ADIDAS", "Superstar", "Grey", 90.00, 1));
    }

    // Revenue Calculation Tests

    @Test
    @DisplayName("Test calculate total revenue")
    void testCalculateTotalRevenue() {
        // (100*2) + (80*3) + (90*1) = 200 + 240 + 90 = 530
        double revenue = service.calculateTotalRevenue(testSales);
        assertEquals(530.00, revenue, 0.01);
    }

    @Test
    @DisplayName("Test calculate total revenue - empty list")
    void testCalculateTotalRevenueEmptyList() {
        double revenue = service.calculateTotalRevenue(new ArrayList<>());
        assertEquals(0.0, revenue, 0.01);
    }

    @Test
    @DisplayName("Test calculate total revenue - null list")
    void testCalculateTotalRevenueNullList() {
        double revenue = service.calculateTotalRevenue(null);
        assertEquals(0.0, revenue, 0.01);
    }

    @Test
    @DisplayName("Test calculate total revenue - single item")
    void testCalculateTotalRevenueSingleItem() {
        List<Sale> singleSale = new ArrayList<>();
        singleSale.add(new Sale("S001", "M101", "I001", "NIKE", "Air Max", "Black", 50.00, 4));
        double revenue = service.calculateTotalRevenue(singleSale);
        assertEquals(200.00, revenue, 0.01);
    }

    // Quantity Calculation Tests

    @Test
    @DisplayName("Test calculate total quantity sold")
    void testCalculateTotalQuantitySold() {
        // 2 + 3 + 1 = 6
        int quantity = service.calculateTotalQuantitySold(testSales);
        assertEquals(6, quantity);
    }

    @Test
    @DisplayName("Test calculate total quantity sold - empty list")
    void testCalculateTotalQuantitySoldEmptyList() {
        int quantity = service.calculateTotalQuantitySold(new ArrayList<>());
        assertEquals(0, quantity);
    }

    @Test
    @DisplayName("Test calculate total quantity sold - null list")
    void testCalculateTotalQuantitySoldNullList() {
        int quantity = service.calculateTotalQuantitySold(null);
        assertEquals(0, quantity);
    }

    // Statistics Tests

    @Test
    @DisplayName("Test get sales statistics")
    void testGetSalesStatistics() {
        SalesStatistics stats = service.getSalesStatistics(testSales);
        
        assertNotNull(stats);
        assertEquals(3, stats.getTotalSalesItems());
        assertEquals(6, stats.getTotalQuantitySold());
        assertEquals(530.00, stats.getTotalRevenue(), 0.01);
    }

    @Test
    @DisplayName("Test get sales statistics - empty list")
    void testGetSalesStatisticsEmptyList() {
        SalesStatistics stats = service.getSalesStatistics(new ArrayList<>());
        
        assertNotNull(stats);
        assertEquals(0, stats.getTotalSalesItems());
        assertEquals(0, stats.getTotalQuantitySold());
        assertEquals(0.0, stats.getTotalRevenue(), 0.01);
    }

    @Test
    @DisplayName("Test get sales statistics - null list")
    void testGetSalesStatisticsNullList() {
        SalesStatistics stats = service.getSalesStatistics(null);
        
        assertNotNull(stats);
        assertEquals(0, stats.getTotalSalesItems());
        assertEquals(0, stats.getTotalQuantitySold());
        assertEquals(0.0, stats.getTotalRevenue(), 0.01);
    }

    @Test
    @DisplayName("Test sales statistics toString")
    void testSalesStatisticsToString() {
        SalesStatistics stats = service.getSalesStatistics(testSales);
        String result = stats.toString();
        
        assertTrue(result.contains("List Sales Item"));
        assertTrue(result.contains("Total Quantity Sales"));
        assertTrue(result.contains("SubTotal Sales"));
        assertTrue(result.contains("3"));
        assertTrue(result.contains("6"));
        assertTrue(result.contains("530"));
    }

    // Validation Tests

    @Test
    @DisplayName("Test validate sale data - all valid")
    void testValidateSaleDataAllValid() {
        ValidationResult result = service.validateSaleData(
            "S001", "M101", "I001", "NIKE", "Air Max 90", "Black", 99.99, 5
        );
        
        assertTrue(result.isValid());
        assertTrue(result.getErrorMessage().isEmpty());
    }

    @Test
    @DisplayName("Test validate sale data - invalid sales ID")
    void testValidateSaleDataInvalidSalesId() {
        ValidationResult result = service.validateSaleData(
            "S1", "M101", "I001", "NIKE", "Air Max 90", "Black", 99.99, 5
        );
        
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("Sales ID"));
    }

    @Test
    @DisplayName("Test validate sale data - invalid member ID")
    void testValidateSaleDataInvalidMemberId() {
        ValidationResult result = service.validateSaleData(
            "S001", "M1", "I001", "NIKE", "Air Max 90", "Black", 99.99, 5
        );
        
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("Member ID"));
    }

    @Test
    @DisplayName("Test validate sale data - invalid item code")
    void testValidateSaleDataInvalidItemCode() {
        ValidationResult result = service.validateSaleData(
            "S001", "M101", "I1", "NIKE", "Air Max 90", "Black", 99.99, 5
        );
        
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("Item Code"));
    }

    @Test
    @DisplayName("Test validate sale data - invalid brand")
    void testValidateSaleDataInvalidBrand() {
        ValidationResult result = service.validateSaleData(
            "S001", "M101", "I001", "VeryLongBrandName", "Air Max 90", "Black", 99.99, 5
        );
        
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("Brand"));
    }

    @Test
    @DisplayName("Test validate sale data - empty brand")
    void testValidateSaleDataEmptyBrand() {
        ValidationResult result = service.validateSaleData(
            "S001", "M101", "I001", "", "Air Max 90", "Black", 99.99, 5
        );
        
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("Brand"));
    }

    @Test
    @DisplayName("Test validate sale data - invalid description")
    void testValidateSaleDataInvalidDescription() {
        ValidationResult result = service.validateSaleData(
            "S001", "M101", "I001", "NIKE", 
            "This is a very long description that exceeds the maximum allowed length", 
            "Black", 99.99, 5
        );
        
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("Description"));
    }

    @Test
    @DisplayName("Test validate sale data - empty description")
    void testValidateSaleDataEmptyDescription() {
        ValidationResult result = service.validateSaleData(
            "S001", "M101", "I001", "NIKE", "", "Black", 99.99, 5
        );
        
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("Description"));
    }

    @Test
    @DisplayName("Test validate sale data - invalid colour")
    void testValidateSaleDataInvalidColour() {
        ValidationResult result = service.validateSaleData(
            "S001", "M101", "I001", "NIKE", "Air Max 90", "VeryLongColourName", 99.99, 5
        );
        
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("Colour"));
    }

    @Test
    @DisplayName("Test validate sale data - invalid price negative")
    void testValidateSaleDataInvalidPriceNegative() {
        ValidationResult result = service.validateSaleData(
            "S001", "M101", "I001", "NIKE", "Air Max 90", "Black", -10.00, 5
        );
        
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("Price"));
    }

    @Test
    @DisplayName("Test validate sale data - invalid price too high")
    void testValidateSaleDataInvalidPriceTooHigh() {
        ValidationResult result = service.validateSaleData(
            "S001", "M101", "I001", "NIKE", "Air Max 90", "Black", 100000000000.00, 5
        );
        
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("Price"));
    }

    @Test
    @DisplayName("Test validate sale data - invalid quantity zero")
    void testValidateSaleDataInvalidQuantityZero() {
        ValidationResult result = service.validateSaleData(
            "S001", "M101", "I001", "NIKE", "Air Max 90", "Black", 99.99, 0
        );
        
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("Quantity"));
    }

    @Test
    @DisplayName("Test validate sale data - invalid quantity negative")
    void testValidateSaleDataInvalidQuantityNegative() {
        ValidationResult result = service.validateSaleData(
            "S001", "M101", "I001", "NIKE", "Air Max 90", "Black", 99.99, -5
        );
        
        assertFalse(result.isValid());
        assertTrue(result.getErrorMessage().contains("Quantity"));
    }

    @Test
    @DisplayName("Test validate sale data - multiple errors")
    void testValidateSaleDataMultipleErrors() {
        ValidationResult result = service.validateSaleData(
            "S1", "M1", "I1", "", "", "", -10.00, 0
        );
        
        assertFalse(result.isValid());
        String errorMsg = result.getErrorMessage();
        assertTrue(errorMsg.contains("Sales ID"));
        assertTrue(errorMsg.contains("Member ID"));
        assertTrue(errorMsg.contains("Item Code"));
        assertTrue(errorMsg.contains("Brand"));
        assertTrue(errorMsg.contains("Description"));
        assertTrue(errorMsg.contains("Colour"));
        assertTrue(errorMsg.contains("Price"));
        assertTrue(errorMsg.contains("Quantity"));
    }

    // Format Sale for Display Tests

    @Test
    @DisplayName("Test format sale for display")
    void testFormatSaleForDisplay() {
        Sale sale = new Sale("S001", "M101", "I001", "NIKE", "Air Max 90", "Black", 99.99, 2);
        String formatted = service.formatSaleForDisplay(sale);
        
        assertNotNull(formatted);
        assertTrue(formatted.contains("S001"));
        assertTrue(formatted.contains("M101"));
        assertTrue(formatted.contains("I001"));
        assertTrue(formatted.contains("NIKE"));
        assertTrue(formatted.contains("Air Max 90"));
        assertTrue(formatted.contains("Black"));
        assertTrue(formatted.contains("99.99"));
        assertTrue(formatted.contains("2"));
    }

    @Test
    @DisplayName("Test format sale for display - null sale")
    void testFormatSaleForDisplayNullSale() {
        String formatted = service.formatSaleForDisplay(null);
        assertEquals("", formatted);
    }

    @Test
    @DisplayName("Test format sale for display - proper alignment")
    void testFormatSaleForDisplayAlignment() {
        Sale sale = new Sale("S001", "M101", "I001", "NIKE", "Air Max 90", "Black", 99.99, 2);
        String formatted = service.formatSaleForDisplay(sale);
        
        // Check that it starts and ends with pipe character (table format)
        assertTrue(formatted.startsWith("|"));
        assertTrue(formatted.endsWith("|"));
    }
}
