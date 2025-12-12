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
 * Tests all 8 search strategies demonstrating polymorphism.
 * Target: 100% coverage of strategy classes.
 */
public class SearchStrategyTest {

    private SalesItem testItem;

    @BeforeEach
    public void setUp() {
        // Create a test item with known values
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

    // ==================== SearchBySalesId Tests ====================

    @Test
    @Order(1)
    @DisplayName("Test Search By Sales ID - Matches Returns True")
    public void testSearchBySalesId_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.BySalesId();

        // Assert
        assertTrue(strategy.matches(testItem, "S001"), "Expected match for S001");
        assertTrue(strategy.matches(testItem, "s001"), "Expected case-insensitive match");
    }

    @Test
    @Order(2)
    @DisplayName("Test Search By Sales ID - No Match Returns False")
    public void testSearchBySalesId_NoMatch_ReturnsFalse() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.BySalesId();

        // Assert
        assertFalse(strategy.matches(testItem, "S999"), "Expected no match for S999");
    }

    // ==================== SearchByMemberId Tests ====================

    @Test
    @Order(3)
    @DisplayName("Test Search By Member ID - Matches Returns True")
    public void testSearchByMemberId_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByMemberId();

        // Assert
        assertTrue(strategy.matches(testItem, "M101"), "Expected match for M101");
    }

    @Test
    @Order(4)
    @DisplayName("Test Search By Member ID - No Match Returns False")
    public void testSearchByMemberId_NoMatch_ReturnsFalse() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByMemberId();

        // Assert
        assertFalse(strategy.matches(testItem, "M999"), "Expected no match for M999");
    }

    // ==================== SearchByItemCode Tests ====================

    @Test
    @Order(5)
    @DisplayName("Test Search By Item Code - Matches Returns True")
    public void testSearchByItemCode_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByItemCode();

        // Assert
        assertTrue(strategy.matches(testItem, "I001"), "Expected match for I001");
    }

    // ==================== SearchByBrand Tests ====================

    @Test
    @Order(6)
    @DisplayName("Test Search By Brand - Matches Returns True")
    public void testSearchByBrand_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByBrand();

        // Assert
        assertTrue(strategy.matches(testItem, "Nike"), "Expected match for Nike");
        assertTrue(strategy.matches(testItem, "NIKE"), "Expected case-insensitive match");
        assertTrue(strategy.matches(testItem, "nike"), "Expected case-insensitive match");
    }

    @Test
    @Order(7)
    @DisplayName("Test Search By Brand - No Match Returns False")
    public void testSearchByBrand_NoMatch_ReturnsFalse() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByBrand();

        // Assert
        assertFalse(strategy.matches(testItem, "Adidas"), "Expected no match for Adidas");
    }

    // ==================== SearchByDescription Tests ====================

    @Test
    @Order(8)
    @DisplayName("Test Search By Description - Partial Match Returns True")
    public void testSearchByDescription_PartialMatch_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByDescription();

        // Assert - Partial match (contains)
        assertTrue(strategy.matches(testItem, "Air"), "Expected partial match for Air");
        assertTrue(strategy.matches(testItem, "Jordan"), "Expected partial match for Jordan");
        assertTrue(strategy.matches(testItem, "air jordan"), "Expected case-insensitive match");
    }

    // ==================== SearchByColour Tests ====================

    @Test
    @Order(9)
    @DisplayName("Test Search By Colour - Matches Returns True")
    public void testSearchByColour_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByColour();

        // Assert
        assertTrue(strategy.matches(testItem, "Black"), "Expected match for Black");
        assertTrue(strategy.matches(testItem, "black"), "Expected case-insensitive match");
    }

    // ==================== SearchByPrice Tests ====================

    @Test
    @Order(10)
    @DisplayName("Test Search By Price - Matches Returns True")
    public void testSearchByPrice_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByPrice();

        // Assert
        assertTrue(strategy.matches(testItem, "199.99"), "Expected match for 199.99");
    }

    @Test
    @Order(11)
    @DisplayName("Test Search By Price - Invalid Number Returns False")
    public void testSearchByPrice_InvalidNumber_ReturnsFalse() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByPrice();

        // Assert
        assertFalse(strategy.matches(testItem, "invalid"), "Expected false for invalid number");
    }

    // ==================== SearchByQuantity Tests ====================

    @Test
    @Order(12)
    @DisplayName("Test Search By Quantity - Matches Returns True")
    public void testSearchByQuantity_Matches_ReturnsTrue() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByQuantity();

        // Assert
        assertTrue(strategy.matches(testItem, "5"), "Expected match for quantity 5");
    }

    @Test
    @Order(13)
    @DisplayName("Test Search By Quantity - Invalid Number Returns False")
    public void testSearchByQuantity_InvalidNumber_ReturnsFalse() {
        // Arrange
        SearchStrategy strategy = new SearchStrategies.ByQuantity();

        // Assert
        assertFalse(strategy.matches(testItem, "invalid"), "Expected false for invalid number");
    }

    // ==================== Strategy Field Name Tests ====================

    @Test
    @Order(14)
    @DisplayName("Test Get Field Name - Returns Correct Names")
    public void testGetFieldName_ReturnsCorrectNames() {
        // Assert
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
