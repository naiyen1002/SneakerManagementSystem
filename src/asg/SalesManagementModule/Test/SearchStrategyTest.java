package asg.SalesManagementModule.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import asg.SalesManagementModule.Model.SalesItem;
import asg.SalesManagementModule.Service.SearchStrategies;
import asg.SalesManagementModule.Service.SearchStrategy;

/**
 * Unit tests for SearchStrategy implementations.
 * 
 * This test class validates all 8 search strategy classes:
 * - BySalesId, ByMemberId, ByItemCode
 * - ByBrand, ByDescription, ByColour
 * - ByPrice, ByQuantity
 * 
 * Demonstrates Strategy Pattern with polymorphic behavior.
 * Each strategy is tested for matching and non-matching cases.
 */
public class SearchStrategyTest {

    // Test item with known values for all strategies
    private SalesItem testItem;

    // ==================== Setup ====================

    /**
     * Creates a test item with known values before each test.
     */
    @BeforeEach
    public void setUp() {
        testItem = new SalesItem(
                "S001", // salesId
                "M101", // memberId
                "I001", // itemCode
                "Nike", // brand
                "Air Jordan", // description
                "Black", // colour
                199.99, // price
                5 // quantity
        );
    }

    // ==================== BySalesId Tests ====================

    /**
     * Tests BySalesId strategy matches correct ID (case-insensitive).
     */
    @Test
    @Order(1)
    @DisplayName("Test Search By Sales ID - Matches Returns True")
    public void testSearchBySalesId_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.BySalesId();

        // Assert
        assertTrue(strategy.matches(testItem, "S001"), "Should match S001");
        assertTrue(strategy.matches(testItem, "s001"), "Should match case-insensitive");
    }

    /**
     * Tests BySalesId strategy returns false for non-matching ID.
     */
    @Test
    @Order(2)
    @DisplayName("Test Search By Sales ID - No Match Returns False")
    public void testSearchBySalesId_NoMatch_ReturnsFalse() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.BySalesId();

        // Assert
        assertFalse(strategy.matches(testItem, "S999"), "Should not match S999");
    }

    // ==================== ByMemberId Tests ====================

    /**
     * Tests ByMemberId strategy matches correct Member ID.
     */
    @Test
    @Order(3)
    @DisplayName("Test Search By Member ID - Matches Returns True")
    public void testSearchByMemberId_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByMemberId();

        // Assert
        assertTrue(strategy.matches(testItem, "M101"), "Should match M101");
    }

    /**
     * Tests ByMemberId strategy returns false for non-matching ID.
     */
    @Test
    @Order(4)
    @DisplayName("Test Search By Member ID - No Match Returns False")
    public void testSearchByMemberId_NoMatch_ReturnsFalse() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByMemberId();

        // Assert
        assertFalse(strategy.matches(testItem, "M999"), "Should not match M999");
    }

    // ==================== ByItemCode Tests ====================

    /**
     * Tests ByItemCode strategy matches correct Item Code.
     */
    @Test
    @Order(5)
    @DisplayName("Test Search By Item Code - Matches Returns True")
    public void testSearchByItemCode_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByItemCode();

        // Assert
        assertTrue(strategy.matches(testItem, "I001"), "Should match I001");
    }

    // ==================== ByBrand Tests ====================

    /**
     * Tests ByBrand strategy matches correct Brand (case-insensitive).
     */
    @Test
    @Order(6)
    @DisplayName("Test Search By Brand - Matches Returns True")
    public void testSearchByBrand_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByBrand();

        // Assert
        assertTrue(strategy.matches(testItem, "Nike"), "Should match Nike");
        assertTrue(strategy.matches(testItem, "NIKE"), "Should match NIKE (case-insensitive)");
        assertTrue(strategy.matches(testItem, "nike"), "Should match nike (case-insensitive)");
    }

    /**
     * Tests ByBrand strategy returns false for non-matching Brand.
     */
    @Test
    @Order(7)
    @DisplayName("Test Search By Brand - No Match Returns False")
    public void testSearchByBrand_NoMatch_ReturnsFalse() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByBrand();

        // Assert
        assertFalse(strategy.matches(testItem, "Adidas"), "Should not match Adidas");
    }

    // ==================== ByDescription Tests ====================

    /**
     * Tests ByDescription strategy matches partial strings (contains).
     */
    @Test
    @Order(8)
    @DisplayName("Test Search By Description - Partial Match Returns True")
    public void testSearchByDescription_PartialMatch_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByDescription();

        // Assert: Uses contains() for partial matching
        assertTrue(strategy.matches(testItem, "Air"), "Should match partial 'Air'");
        assertTrue(strategy.matches(testItem, "Jordan"), "Should match partial 'Jordan'");
        assertTrue(strategy.matches(testItem, "air jordan"), "Should match case-insensitive");
    }

    // ==================== ByColour Tests ====================

    /**
     * Tests ByColour strategy matches correct Colour (case-insensitive).
     */
    @Test
    @Order(9)
    @DisplayName("Test Search By Colour - Matches Returns True")
    public void testSearchByColour_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByColour();

        // Assert
        assertTrue(strategy.matches(testItem, "Black"), "Should match Black");
        assertTrue(strategy.matches(testItem, "black"), "Should match case-insensitive");
    }

    // ==================== ByPrice Tests ====================

    /**
     * Tests ByPrice strategy matches exact price.
     */
    @Test
    @Order(10)
    @DisplayName("Test Search By Price - Matches Returns True")
    public void testSearchByPrice_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByPrice();

        // Assert
        assertTrue(strategy.matches(testItem, "199.99"), "Should match 199.99");
    }

    /**
     * Tests ByPrice strategy handles invalid number input gracefully.
     */
    @Test
    @Order(11)
    @DisplayName("Test Search By Price - Invalid Number Returns False")
    public void testSearchByPrice_InvalidNumber_ReturnsFalse() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByPrice();

        // Assert
        assertFalse(strategy.matches(testItem, "invalid"), "Should return false for invalid number");
    }

    // ==================== ByQuantity Tests ====================

    /**
     * Tests ByQuantity strategy matches exact quantity.
     */
    @Test
    @Order(12)
    @DisplayName("Test Search By Quantity - Matches Returns True")
    public void testSearchByQuantity_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByQuantity();

        // Assert
        assertTrue(strategy.matches(testItem, "5"), "Should match quantity 5");
    }

    /**
     * Tests ByQuantity strategy handles invalid number input gracefully.
     */
    @Test
    @Order(13)
    @DisplayName("Test Search By Quantity - Invalid Number Returns False")
    public void testSearchByQuantity_InvalidNumber_ReturnsFalse() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByQuantity();

        // Assert
        assertFalse(strategy.matches(testItem, "invalid"), "Should return false for invalid number");
    }

    // ==================== Field Name Tests ====================

    /**
     * Tests that all strategies return correct field names.
     * These names are used in search prompts.
     */
    @Test
    @Order(14)
    @DisplayName("Test Get Field Name - Returns Correct Names")
    public void testGetFieldName_ReturnsCorrectNames() {
        // Assert: Verify all field names
        assertEquals("Sales ID", new SearchStrategies.BySalesId().getFieldName());
        assertEquals("Member ID", new SearchStrategies.ByMemberId().getFieldName());
        assertEquals("Item Code", new SearchStrategies.ByItemCode().getFieldName());
        assertEquals("Brand", new SearchStrategies.ByBrand().getFieldName());
        assertEquals("Item Description", new SearchStrategies.ByDescription().getFieldName());
        assertEquals("Colour", new SearchStrategies.ByColour().getFieldName());
        assertEquals("Item Price", new SearchStrategies.ByPrice().getFieldName());
        assertEquals("Quantity Sales", new SearchStrategies.ByQuantity().getFieldName());
    }
}
