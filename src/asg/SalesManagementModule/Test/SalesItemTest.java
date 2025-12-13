package asg.SalesManagementModule.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import asg.SalesManagementModule.Model.SalesItem;

/**
 * Unit tests for SalesItem model class.
 * 
 * This test class validates the SalesItem entity including:
 * - Constructor and getter/setter functionality
 * - Field validation methods (IDs, text fields, numeric fields)
 * - Composite validation via isValid()
 * - Business logic methods like getTotalPrice()
 * - Object methods (equals, hashCode, toString)
 */
public class SalesItemTest {

    // Test item instance used across tests
    private SalesItem testItem;

    // ==================== Setup ====================

    /**
     * Creates a fresh test item before each test.
     */
    @BeforeEach
    public void setUp() {
        testItem = new SalesItem("T001", "M001", "I001", "Nike", "Test Sneaker", "Black", 99.99, 5);
    }

    // ==================== Constructor and Getter/Setter Tests ====================

    /**
     * Tests that the constructor correctly initializes all fields.
     */
    @Test
    @DisplayName("Test Constructor and Getters - All Fields Set Correctly")
    public void testConstructor_AllFieldsSetCorrectly() {
        // Assert: Verify all fields are set correctly
        assertEquals("T001", testItem.getSalesId());
        assertEquals("M001", testItem.getMemberId());
        assertEquals("I001", testItem.getItemCode());
        assertEquals("Nike", testItem.getBrand());
        assertEquals("Test Sneaker", testItem.getItemDescription());
        assertEquals("Black", testItem.getColour());
        assertEquals(99.99, testItem.getItemPrice(), 0.01);
        assertEquals(5, testItem.getQuantitySales());
    }

    /**
     * Tests that all setter methods correctly update field values.
     */
    @Test
    @DisplayName("Test Setters - All Values Updated")
    public void testSetters_AllValuesUpdated() {
        // Act: Update all fields
        testItem.setSalesId("T002");
        testItem.setMemberId("M002");
        testItem.setItemCode("I002");
        testItem.setBrand("Puma");
        testItem.setItemDescription("Updated Sneaker");
        testItem.setColour("White");
        testItem.setItemPrice(149.99);
        testItem.setQuantitySales(10);

        // Assert: Verify all fields are updated
        assertEquals("T002", testItem.getSalesId());
        assertEquals("M002", testItem.getMemberId());
        assertEquals("I002", testItem.getItemCode());
        assertEquals("Puma", testItem.getBrand());
        assertEquals("Updated Sneaker", testItem.getItemDescription());
        assertEquals("White", testItem.getColour());
        assertEquals(149.99, testItem.getItemPrice(), 0.01);
        assertEquals(10, testItem.getQuantitySales());
    }

    // ==================== Validation Tests ====================

    /**
     * Nested tests for ID field validation (Sales ID, Member ID, Item Code).
     * All IDs must be exactly 4 characters.
     */
    @Nested
    @DisplayName("ID Validation Tests")
    class IdValidationTests {

        /**
         * Tests that valid 4-character IDs pass validation.
         */
        @Test
        @DisplayName("Valid IDs - 4 Characters")
        public void testValidIds() {
            assertTrue(SalesItem.isValidSalesId("ABCD"), "4 chars should be valid");
            assertTrue(SalesItem.isValidMemberId("M001"), "4 chars should be valid");
            assertTrue(SalesItem.isValidItemCode("I001"), "4 chars should be valid");
        }

        /**
         * Tests that invalid IDs (null, wrong length, whitespace) fail validation.
         */
        @Test
        @DisplayName("Invalid IDs - Null, Wrong Length, Whitespace")
        public void testInvalidIds() {
            // Null values
            assertFalse(SalesItem.isValidSalesId(null), "Null should be invalid");
            assertFalse(SalesItem.isValidMemberId(null), "Null should be invalid");
            assertFalse(SalesItem.isValidItemCode(null), "Null should be invalid");

            // Wrong length
            assertFalse(SalesItem.isValidSalesId("ABC"), "3 chars should be invalid");
            assertFalse(SalesItem.isValidSalesId("ABCDE"), "5 chars should be invalid");
            assertFalse(SalesItem.isValidMemberId("M01"), "3 chars should be invalid");
            assertFalse(SalesItem.isValidItemCode("I0"), "2 chars should be invalid");

            // Whitespace only
            assertFalse(SalesItem.isValidSalesId("    "), "Whitespace should be invalid");
        }
    }

    /**
     * Nested tests for text field validation (Brand, Description, Colour).
     * Each field has maximum length constraints.
     */
    @Nested
    @DisplayName("Text Field Validation Tests")
    class TextFieldValidationTests {

        /**
         * Tests that valid text fields within length limits pass validation.
         */
        @Test
        @DisplayName("Valid Text Fields - Within Limits")
        public void testValidTextFields() {
            assertTrue(SalesItem.isValidBrand("Nike"), "Short brand should be valid");
            assertTrue(SalesItem.isValidBrand("123456789"), "9 chars max should be valid");
            assertTrue(SalesItem.isValidDescription("Test Description"), "Valid description");
            assertTrue(SalesItem.isValidColour("Black"), "Valid colour");
        }

        /**
         * Tests that invalid text fields (null, empty, too long) fail validation.
         */
        @Test
        @DisplayName("Invalid Text Fields - Null, Empty, Too Long")
        public void testInvalidTextFields() {
            // Null and empty
            assertFalse(SalesItem.isValidBrand(null), "Null brand should be invalid");
            assertFalse(SalesItem.isValidBrand(""), "Empty brand should be invalid");
            assertFalse(SalesItem.isValidDescription(null), "Null description should be invalid");
            assertFalse(SalesItem.isValidColour(""), "Empty colour should be invalid");

            // Too long (exceeds max length)
            assertFalse(SalesItem.isValidBrand("1234567890"), "10 chars should be invalid");
            assertFalse(SalesItem.isValidDescription("123456789012345678901234567890"), "30 chars should be invalid");
            assertFalse(SalesItem.isValidColour("12345678901"), "11 chars should be invalid");
        }
    }

    /**
     * Nested tests for numeric field validation (Price, Quantity).
     */
    @Nested
    @DisplayName("Numeric Validation Tests")
    class NumericValidationTests {

        /**
         * Tests that valid price and quantity values pass validation.
         */
        @Test
        @DisplayName("Valid Price and Quantity")
        public void testValidNumericFields() {
            assertTrue(SalesItem.isValidPrice(0.01), "Minimum price should be valid");
            assertTrue(SalesItem.isValidPrice(99999999999.99), "Large price should be valid");
            assertTrue(SalesItem.isValidQuantity(1), "Minimum quantity should be valid");
            assertTrue(SalesItem.isValidQuantity(100), "Large quantity should be valid");
        }

        /**
         * Tests that invalid price and quantity values fail validation.
         */
        @Test
        @DisplayName("Invalid Price and Quantity")
        public void testInvalidNumericFields() {
            assertFalse(SalesItem.isValidPrice(0), "Zero price should be invalid");
            assertFalse(SalesItem.isValidPrice(-1), "Negative price should be invalid");
            assertFalse(SalesItem.isValidPrice(100000000000.00), "Price exceeds max");
            assertFalse(SalesItem.isValidQuantity(0), "Zero quantity should be invalid");
            assertFalse(SalesItem.isValidQuantity(-1), "Negative quantity should be invalid");
        }
    }

    // ==================== isValid() Composite Tests ====================

    /**
     * Tests that isValid() returns true for a completely valid item.
     */
    @Test
    @DisplayName("Test isValid - Valid Item Returns True")
    public void testIsValid_ValidItem_ReturnsTrue() {
        assertTrue(testItem.isValid(), "Valid item should return true");
    }

    /**
     * Tests that isValid() returns false when any field is invalid.
     */
    @Test
    @DisplayName("Test isValid - Invalid Fields Return False")
    public void testIsValid_InvalidFields_ReturnsFalse() {
        // Test with invalid sales ID
        testItem.setSalesId("ABC"); // Only 3 chars - invalid
        assertFalse(testItem.isValid(), "Invalid ID should make item invalid");

        // Reset and test with invalid price
        testItem.setSalesId("T001");
        testItem.setItemPrice(-10.0);
        assertFalse(testItem.isValid(), "Invalid price should make item invalid");
    }

    // ==================== getTotalPrice() Test ====================

    /**
     * Tests that getTotalPrice() correctly calculates price * quantity.
     */
    @Test
    @DisplayName("Test Get Total Price - Calculates Correctly")
    public void testGetTotalPrice_CalculatesCorrectly() {
        // Price: 99.99, Quantity: 5, Expected Total: 499.95
        assertEquals(499.95, testItem.getTotalPrice(), 0.01, "Total should be price * quantity");
    }

    // ==================== equals() and hashCode() Tests ====================

    /**
     * Tests that equals() compares items based on Sales ID.
     */
    @Test
    @DisplayName("Test Equals - Based on Sales ID")
    public void testEquals_BasedOnSalesId() {
        // Same ID, different other fields
        SalesItem sameId = new SalesItem("T001", "M002", "I002", "Puma", "Different", "White", 50.0, 1);
        // Different ID
        SalesItem differentId = new SalesItem("T002", "M001", "I001", "Nike", "Test Sneaker", "Black", 99.99, 5);

        assertEquals(testItem, sameId, "Same ID should be equal");
        assertNotEquals(testItem, differentId, "Different ID should not be equal");
        assertNotEquals(testItem, null, "Should not equal null");
    }

    /**
     * Tests that hashCode() is consistent for items with the same Sales ID.
     */
    @Test
    @DisplayName("Test HashCode - Same ID Has Same HashCode")
    public void testHashCode_SameSalesId_SameHashCode() {
        SalesItem sameId = new SalesItem("T001", "M002", "I002", "Puma", "Different", "White", 50.0, 1);
        assertEquals(testItem.hashCode(), sameId.hashCode(), "Same ID should have same hashCode");
    }

    // ==================== toString() Test ====================

    /**
     * Tests that toString() contains key identifying fields.
     */
    @Test
    @DisplayName("Test ToString - Contains Key Fields")
    public void testToString_ContainsKeyFields() {
        String str = testItem.toString();
        assertTrue(str.contains("T001"), "Should contain sales ID");
        assertTrue(str.contains("Nike"), "Should contain brand");
    }
}
