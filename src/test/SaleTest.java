package test;

import model.Sale;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Sale model class.
 * Tests validation methods, constructors, getters, setters, and object methods.
 */
public class SaleTest {

    private Sale testSale;

    @BeforeEach
    void setUp() {
        testSale = new Sale("S001", "M101", "I001", "NIKE", 
                           "Air Max 90", "Black", 99.99, 2);
    }

    // Constructor and Basic Getters Tests
    
    @Test
    @DisplayName("Test Sale constructor and getters")
    void testConstructorAndGetters() {
        assertEquals("S001", testSale.getSalesId());
        assertEquals("M101", testSale.getMemberId());
        assertEquals("I001", testSale.getItemCode());
        assertEquals("NIKE", testSale.getBrand());
        assertEquals("Air Max 90", testSale.getItemDescription());
        assertEquals("Black", testSale.getColour());
        assertEquals(99.99, testSale.getItemPrice(), 0.01);
        assertEquals(2, testSale.getQuantitySales());
    }

    // Setters Tests
    
    @Test
    @DisplayName("Test setSalesId")
    void testSetSalesId() {
        testSale.setSalesId("S999");
        assertEquals("S999", testSale.getSalesId());
    }

    @Test
    @DisplayName("Test setMemberId")
    void testSetMemberId() {
        testSale.setMemberId("M999");
        assertEquals("M999", testSale.getMemberId());
    }

    @Test
    @DisplayName("Test setQuantitySales")
    void testSetQuantitySales() {
        testSale.setQuantitySales(10);
        assertEquals(10, testSale.getQuantitySales());
    }

    // Validation Tests - Sales ID
    
    @Test
    @DisplayName("Test valid sales ID")
    void testValidSalesId() {
        assertTrue(Sale.isValidSalesId("S001"));
        assertTrue(Sale.isValidSalesId("ABCD"));
        assertTrue(Sale.isValidSalesId("1234"));
    }

    @Test
    @DisplayName("Test invalid sales ID - too short")
    void testInvalidSalesIdTooShort() {
        assertFalse(Sale.isValidSalesId("S01"));
        assertFalse(Sale.isValidSalesId("A"));
    }

    @Test
    @DisplayName("Test invalid sales ID - too long")
    void testInvalidSalesIdTooLong() {
        assertFalse(Sale.isValidSalesId("S0001"));
        assertFalse(Sale.isValidSalesId("ABCDE"));
    }

    @Test
    @DisplayName("Test invalid sales ID - null or empty")
    void testInvalidSalesIdNullOrEmpty() {
        assertFalse(Sale.isValidSalesId(null));
        assertFalse(Sale.isValidSalesId(""));
        assertFalse(Sale.isValidSalesId("    "));
    }

    // Validation Tests - Member ID
    
    @Test
    @DisplayName("Test valid member ID")
    void testValidMemberId() {
        assertTrue(Sale.isValidMemberId("M101"));
        assertTrue(Sale.isValidMemberId("WXYZ"));
    }

    @Test
    @DisplayName("Test invalid member ID")
    void testInvalidMemberId() {
        assertFalse(Sale.isValidMemberId("M1"));
        assertFalse(Sale.isValidMemberId("M10101"));
        assertFalse(Sale.isValidMemberId(null));
        assertFalse(Sale.isValidMemberId(""));
    }

    // Validation Tests - Item Code
    
    @Test
    @DisplayName("Test valid item code")
    void testValidItemCode() {
        assertTrue(Sale.isValidItemCode("I001"));
        assertTrue(Sale.isValidItemCode("ITEM"));
    }

    @Test
    @DisplayName("Test invalid item code")
    void testInvalidItemCode() {
        assertFalse(Sale.isValidItemCode("I1"));
        assertFalse(Sale.isValidItemCode("I00001"));
        assertFalse(Sale.isValidItemCode(null));
    }

    // Validation Tests - Brand
    
    @Test
    @DisplayName("Test valid brand")
    void testValidBrand() {
        assertTrue(Sale.isValidBrand("NIKE"));
        assertTrue(Sale.isValidBrand("PUMA"));
        assertTrue(Sale.isValidBrand("ADIDAS"));
        assertTrue(Sale.isValidBrand("NewBrand"));
    }

    @Test
    @DisplayName("Test invalid brand - too long")
    void testInvalidBrandTooLong() {
        assertFalse(Sale.isValidBrand("VeryLongBrandName"));
    }

    @Test
    @DisplayName("Test invalid brand - null or empty")
    void testInvalidBrandNullOrEmpty() {
        assertFalse(Sale.isValidBrand(null));
        assertFalse(Sale.isValidBrand(""));
        assertFalse(Sale.isValidBrand("   "));
    }

    // Validation Tests - Description
    
    @Test
    @DisplayName("Test valid description")
    void testValidDescription() {
        assertTrue(Sale.isValidDescription("Air Max 90"));
        assertTrue(Sale.isValidDescription("Running Shoes"));
        assertTrue(Sale.isValidDescription("A very long description here"));
    }

    @Test
    @DisplayName("Test invalid description - too long")
    void testInvalidDescriptionTooLong() {
        assertFalse(Sale.isValidDescription("This is a very long description that exceeds the maximum allowed"));
    }

    @Test
    @DisplayName("Test invalid description - null or empty")
    void testInvalidDescriptionNullOrEmpty() {
        assertFalse(Sale.isValidDescription(null));
        assertFalse(Sale.isValidDescription(""));
        assertFalse(Sale.isValidDescription("   "));
    }

    // Validation Tests - Colour
    
    @Test
    @DisplayName("Test valid colour")
    void testValidColour() {
        assertTrue(Sale.isValidColour("Black"));
        assertTrue(Sale.isValidColour("White"));
        assertTrue(Sale.isValidColour("Red"));
    }

    @Test
    @DisplayName("Test invalid colour - too long")
    void testInvalidColourTooLong() {
        assertFalse(Sale.isValidColour("VeryLongColorName"));
    }

    @Test
    @DisplayName("Test invalid colour - null or empty")
    void testInvalidColourNullOrEmpty() {
        assertFalse(Sale.isValidColour(null));
        assertFalse(Sale.isValidColour(""));
    }

    // Validation Tests - Price
    
    @Test
    @DisplayName("Test valid price")
    void testValidPrice() {
        assertTrue(Sale.isValidPrice(0.01));
        assertTrue(Sale.isValidPrice(99.99));
        assertTrue(Sale.isValidPrice(1000.00));
        assertTrue(Sale.isValidPrice(99999999999.99));
    }

    @Test
    @DisplayName("Test invalid price - zero or negative")
    void testInvalidPriceZeroOrNegative() {
        assertFalse(Sale.isValidPrice(0.0));
        assertFalse(Sale.isValidPrice(-1.0));
        assertFalse(Sale.isValidPrice(-99.99));
    }

    @Test
    @DisplayName("Test invalid price - too high")
    void testInvalidPriceTooHigh() {
        assertFalse(Sale.isValidPrice(100000000000.00));
    }

    // Validation Tests - Quantity
    
    @Test
    @DisplayName("Test valid quantity")
    void testValidQuantity() {
        assertTrue(Sale.isValidQuantity(1));
        assertTrue(Sale.isValidQuantity(10));
        assertTrue(Sale.isValidQuantity(1000));
    }

    @Test
    @DisplayName("Test invalid quantity - zero or negative")
    void testInvalidQuantityZeroOrNegative() {
        assertFalse(Sale.isValidQuantity(0));
        assertFalse(Sale.isValidQuantity(-1));
        assertFalse(Sale.isValidQuantity(-10));
    }

    // isValid() Method Tests
    
    @Test
    @DisplayName("Test isValid with valid sale")
    void testIsValidWithValidSale() {
        assertTrue(testSale.isValid());
    }

    @Test
    @DisplayName("Test isValid with invalid sales ID")
    void testIsValidWithInvalidSalesId() {
        Sale invalidSale = new Sale("S1", "M101", "I001", "NIKE", 
                                    "Air Max", "Black", 99.99, 2);
        assertFalse(invalidSale.isValid());
    }

    @Test
    @DisplayName("Test isValid with invalid price")
    void testIsValidWithInvalidPrice() {
        Sale invalidSale = new Sale("S001", "M101", "I001", "NIKE", 
                                    "Air Max", "Black", -10.0, 2);
        assertFalse(invalidSale.isValid());
    }

    @Test
    @DisplayName("Test isValid with invalid quantity")
    void testIsValidWithInvalidQuantity() {
        Sale invalidSale = new Sale("S001", "M101", "I001", "NIKE", 
                                    "Air Max", "Black", 99.99, 0);
        assertFalse(invalidSale.isValid());
    }

    // getTotalPrice() Tests
    
    @Test
    @DisplayName("Test getTotalPrice calculation")
    void testGetTotalPrice() {
        assertEquals(199.98, testSale.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test getTotalPrice with different quantity")
    void testGetTotalPriceWithDifferentQuantity() {
        testSale.setQuantitySales(5);
        assertEquals(499.95, testSale.getTotalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test getTotalPrice with quantity 1")
    void testGetTotalPriceWithQuantityOne() {
        testSale.setQuantitySales(1);
        assertEquals(99.99, testSale.getTotalPrice(), 0.01);
    }

    // equals() and hashCode() Tests
    
    @Test
    @DisplayName("Test equals with same object")
    void testEqualsSameObject() {
        assertEquals(testSale, testSale);
    }

    @Test
    @DisplayName("Test equals with same sales ID")
    void testEqualsWithSameSalesId() {
        Sale sameSale = new Sale("S001", "M999", "I999", "PUMA", 
                                "Different", "White", 50.0, 1);
        assertEquals(testSale, sameSale);
    }

    @Test
    @DisplayName("Test equals with different sales ID")
    void testEqualsWithDifferentSalesId() {
        Sale differentSale = new Sale("S002", "M101", "I001", "NIKE", 
                                      "Air Max 90", "Black", 99.99, 2);
        assertNotEquals(testSale, differentSale);
    }

    @Test
    @DisplayName("Test equals with null")
    void testEqualsWithNull() {
        assertNotEquals(null, testSale);
    }

    @Test
    @DisplayName("Test equals with different class")
    void testEqualsWithDifferentClass() {
        assertNotEquals("String", testSale);
    }

    @Test
    @DisplayName("Test hashCode consistency")
    void testHashCodeConsistency() {
        Sale sameSale = new Sale("S001", "M999", "I999", "PUMA", 
                                "Different", "White", 50.0, 1);
        assertEquals(testSale.hashCode(), sameSale.hashCode());
    }

    @Test
    @DisplayName("Test hashCode different for different sales ID")
    void testHashCodeDifferent() {
        Sale differentSale = new Sale("S002", "M101", "I001", "NIKE", 
                                      "Air Max 90", "Black", 99.99, 2);
        assertNotEquals(testSale.hashCode(), differentSale.hashCode());
    }

    // toString() Tests
    
    @Test
    @DisplayName("Test toString contains all fields")
    void testToStringContainsAllFields() {
        String result = testSale.toString();
        assertTrue(result.contains("S001"));
        assertTrue(result.contains("M101"));
        assertTrue(result.contains("I001"));
        assertTrue(result.contains("NIKE"));
        assertTrue(result.contains("Air Max 90"));
        assertTrue(result.contains("Black"));
        assertTrue(result.contains("99.99"));
        assertTrue(result.contains("2"));
    }

    @Test
    @DisplayName("Test toString format")
    void testToStringFormat() {
        String result = testSale.toString();
        assertTrue(result.startsWith("Sale{"));
        assertTrue(result.contains("salesId="));
        assertTrue(result.contains("total="));
    }
}
