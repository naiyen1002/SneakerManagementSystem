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
 * Tests validation methods, getters/setters, and object methods.
 */
public class SalesItemTest {

    private SalesItem testItem;

    @BeforeEach
    public void setUp() {
        testItem = new SalesItem("T001", "M001", "I001", "Nike", "Test Sneaker", "Black", 99.99, 5);
    }

    // ==================== Constructor and Getter/Setter Tests ====================

    @Test
    @DisplayName("Test Constructor and Getters - All Fields Set Correctly")
    public void testConstructor_AllFieldsSetCorrectly() {
        assertEquals("T001", testItem.getSalesId());
        assertEquals("M001", testItem.getMemberId());
        assertEquals("I001", testItem.getItemCode());
        assertEquals("Nike", testItem.getBrand());
        assertEquals("Test Sneaker", testItem.getItemDescription());
        assertEquals("Black", testItem.getColour());
        assertEquals(99.99, testItem.getItemPrice(), 0.01);
        assertEquals(5, testItem.getQuantitySales());
    }

    @Test
    @DisplayName("Test Setters - All Values Updated")
    public void testSetters_AllValuesUpdated() {
        testItem.setSalesId("T002");
        testItem.setMemberId("M002");
        testItem.setItemCode("I002");
        testItem.setBrand("Puma");
        testItem.setItemDescription("Updated Sneaker");
        testItem.setColour("White");
        testItem.setItemPrice(149.99);
        testItem.setQuantitySales(10);

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

    @Nested
    @DisplayName("ID Validation Tests")
    class IdValidationTests {
        @Test
        @DisplayName("Valid IDs - 4 Characters")
        public void testValidIds() {
            assertTrue(SalesItem.isValidSalesId("ABCD"));
            assertTrue(SalesItem.isValidMemberId("M001"));
            assertTrue(SalesItem.isValidItemCode("I001"));
        }

        @Test
        @DisplayName("Invalid IDs - Null, Wrong Length, Whitespace")
        public void testInvalidIds() {
            // Null
            assertFalse(SalesItem.isValidSalesId(null));
            assertFalse(SalesItem.isValidMemberId(null));
            assertFalse(SalesItem.isValidItemCode(null));

            // Wrong length
            assertFalse(SalesItem.isValidSalesId("ABC"));
            assertFalse(SalesItem.isValidSalesId("ABCDE"));
            assertFalse(SalesItem.isValidMemberId("M01"));
            assertFalse(SalesItem.isValidItemCode("I0"));

            // Whitespace only
            assertFalse(SalesItem.isValidSalesId("    "));
        }
    }

    @Nested
    @DisplayName("Text Field Validation Tests")
    class TextFieldValidationTests {
        @Test
        @DisplayName("Valid Text Fields - Within Limits")
        public void testValidTextFields() {
            assertTrue(SalesItem.isValidBrand("Nike"));
            assertTrue(SalesItem.isValidBrand("123456789")); // 9 chars max
            assertTrue(SalesItem.isValidDescription("Test Description"));
            assertTrue(SalesItem.isValidColour("Black"));
        }

        @Test
        @DisplayName("Invalid Text Fields - Null, Empty, Too Long")
        public void testInvalidTextFields() {
            // Null and empty
            assertFalse(SalesItem.isValidBrand(null));
            assertFalse(SalesItem.isValidBrand(""));
            assertFalse(SalesItem.isValidDescription(null));
            assertFalse(SalesItem.isValidColour(""));

            // Too long
            assertFalse(SalesItem.isValidBrand("1234567890")); // 10 chars
            assertFalse(SalesItem.isValidDescription("123456789012345678901234567890")); // 30 chars
            assertFalse(SalesItem.isValidColour("12345678901")); // 11 chars
        }
    }

    @Nested
    @DisplayName("Numeric Validation Tests")
    class NumericValidationTests {
        @Test
        @DisplayName("Valid Price and Quantity")
        public void testValidNumericFields() {
            assertTrue(SalesItem.isValidPrice(0.01));
            assertTrue(SalesItem.isValidPrice(99999999999.99));
            assertTrue(SalesItem.isValidQuantity(1));
            assertTrue(SalesItem.isValidQuantity(100));
        }

        @Test
        @DisplayName("Invalid Price and Quantity")
        public void testInvalidNumericFields() {
            assertFalse(SalesItem.isValidPrice(0));
            assertFalse(SalesItem.isValidPrice(-1));
            assertFalse(SalesItem.isValidPrice(100000000000.00)); // Exceeds max
            assertFalse(SalesItem.isValidQuantity(0));
            assertFalse(SalesItem.isValidQuantity(-1));
        }
    }

    // ==================== isValid() Composite Tests ====================

    @Test
    @DisplayName("Test isValid - Valid Item Returns True")
    public void testIsValid_ValidItem_ReturnsTrue() {
        assertTrue(testItem.isValid());
    }

    @Test
    @DisplayName("Test isValid - Invalid Fields Return False")
    public void testIsValid_InvalidFields_ReturnsFalse() {
        testItem.setSalesId("ABC"); // Invalid - only 3 chars
        assertFalse(testItem.isValid());

        testItem.setSalesId("T001"); // Reset
        testItem.setItemPrice(-10.0);
        assertFalse(testItem.isValid());
    }

    // ==================== getTotalPrice() Test ====================

    @Test
    @DisplayName("Test Get Total Price - Calculates Correctly")
    public void testGetTotalPrice_CalculatesCorrectly() {
        // 99.99 * 5 = 499.95
        assertEquals(499.95, testItem.getTotalPrice(), 0.01);
    }

    // ==================== equals() and hashCode() Tests ====================

    @Test
    @DisplayName("Test Equals - Based on Sales ID")
    public void testEquals_BasedOnSalesId() {
        SalesItem sameId = new SalesItem("T001", "M002", "I002", "Puma", "Different", "White", 50.0, 1);
        SalesItem differentId = new SalesItem("T002", "M001", "I001", "Nike", "Test Sneaker", "Black", 99.99, 5);

        assertEquals(testItem, sameId);
        assertNotEquals(testItem, differentId);
        assertNotEquals(testItem, null);
    }

    @Test
    @DisplayName("Test HashCode - Same ID Has Same HashCode")
    public void testHashCode_SameSalesId_SameHashCode() {
        SalesItem sameId = new SalesItem("T001", "M002", "I002", "Puma", "Different", "White", 50.0, 1);
        assertEquals(testItem.hashCode(), sameId.hashCode());
    }

    // ==================== toString() Test ====================

    @Test
    @DisplayName("Test ToString - Contains Key Fields")
    public void testToString_ContainsKeyFields() {
        String str = testItem.toString();
        assertTrue(str.contains("T001"));
        assertTrue(str.contains("Nike"));
    }
}
