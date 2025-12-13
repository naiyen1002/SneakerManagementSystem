package asg.SalesManagementModule.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import asg.SalesManagementModule.Model.SalesItem;
import asg.SalesManagementModule.Service.SalesService;
import asg.SalesManagementModule.View.SalesView;

/**
 * Unit tests for SalesView class.
 * 
 * This test class validates the View layer including:
 * - Menu display methods
 * - Table display with formatting
 * - Input methods (strings, numbers, confirmations)
 * - Brand and colour selection
 * - Message display methods
 * 
 * Uses output capture to verify console output and Scanner injection for input.
 */
public class SalesViewTest {

    // Output capture for verifying console output
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    // ==================== Setup and Teardown ====================

    /**
     * Captures System.out before each test for output verification.
     */
    @BeforeEach
    public void setUp() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Restores original System.out after each test.
     */
    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    // ==================== Menu Display Tests ====================

    /**
     * Tests that main menu displays title and options.
     */
    @Test
    @DisplayName("Test Display Main Menu - Contains Menu Title and Options")
    public void testDisplayMainMenu_ContainsMenuElements() {
        // Arrange
        SalesView view = new SalesView(new Scanner(""));

        // Act
        view.displayMainMenu();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("SALES MANAGEMENT MENU"), "Should contain menu title");
        assertTrue(output.contains("Enter your choice"), "Should contain choice prompt");
    }

    /**
     * Tests that search menu displays all search options.
     */
    @Test
    @DisplayName("Test Display Search Menu - Contains All Search Options")
    public void testDisplaySearchMenu_ContainsAllSearchOptions() {
        // Arrange
        SalesView view = new SalesView(new Scanner(""));

        // Act
        view.displaySearchMenu();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("SEARCH MENU"), "Should contain search menu title");
        assertTrue(output.contains("SALES ID"), "Should contain Sales ID option");
        assertTrue(output.contains("MEMBER ID"), "Should contain Member ID option");
        assertTrue(output.contains("BRAND"), "Should contain Brand option");
        assertTrue(output.contains("COLOUR"), "Should contain Colour option");
        assertTrue(output.contains("PRICE"), "Should contain Price option");
    }

    // ==================== Table Display Tests ====================

    /**
     * Nested tests for table display functionality.
     */
    @Nested
    @DisplayName("Table Display Tests")
    class TableDisplayTests {

        /**
         * Tests that table header shows all column names.
         */
        @Test
        @DisplayName("Test Display Table Header - Shows Column Headers")
        public void testDisplayTableHeader_ShowsColumnHeaders() {
            // Arrange
            SalesView view = new SalesView(new Scanner(""));

            // Act
            view.displayTableHeader();

            // Assert
            String output = outputStream.toString();
            assertTrue(output.contains("SALES ID"), "Should contain Sales ID column");
            assertTrue(output.contains("MEMBER ID"), "Should contain Member ID column");
            assertTrue(output.contains("ITEM CODE"), "Should contain Item Code column");
        }

        /**
         * Tests that table row displays all item data correctly.
         */
        @Test
        @DisplayName("Test Display Table Row - Shows Item Data")
        public void testDisplayTableRow_ShowsItemData() {
            // Arrange
            SalesView view = new SalesView(new Scanner(""));
            SalesItem item = new SalesItem("T001", "M001", "I001", "Nike", "Test Sneaker", "Black", 99.99, 5);

            // Act
            view.displayTableRow(item);

            // Assert
            String output = outputStream.toString();
            assertTrue(output.contains("T001"), "Should contain Sales ID");
            assertTrue(output.contains("M001"), "Should contain Member ID");
            assertTrue(output.contains("Nike"), "Should contain Brand");
        }

        /**
         * Tests that table displays all items with correct record count.
         */
        @Test
        @DisplayName("Test Display Sales Table - Shows All Items and Record Count")
        public void testDisplaySalesTable_ShowsAllItems() {
            // Arrange
            SalesView view = new SalesView(new Scanner(""));
            List<SalesItem> items = new ArrayList<>();
            items.add(new SalesItem("T001", "M001", "I001", "Nike", "Sneaker 1", "Black", 99.99, 5));
            items.add(new SalesItem("T002", "M002", "I002", "Puma", "Sneaker 2", "White", 149.99, 3));

            // Act
            view.displaySalesTable(items);

            // Assert
            String output = outputStream.toString();
            assertTrue(output.contains("T001"), "Should contain first item");
            assertTrue(output.contains("T002"), "Should contain second item");
            assertTrue(output.contains("2 records"), "Should show record count");
        }

        /**
         * Tests that empty list shows zero records.
         */
        @Test
        @DisplayName("Test Display Sales Table - Empty List Shows Zero Records")
        public void testDisplaySalesTable_EmptyList() {
            // Arrange
            SalesView view = new SalesView(new Scanner(""));

            // Act
            view.displaySalesTable(new ArrayList<>());

            // Assert
            String output = outputStream.toString();
            assertTrue(output.contains("0 records"), "Should show 0 records");
        }
    }

    // ==================== Sales Details Display Tests ====================

    /**
     * Tests that sales details displays all fields.
     */
    @Test
    @DisplayName("Test Display Sales Details - Shows All Fields")
    public void testDisplaySalesDetails_ShowsAllFields() {
        // Arrange
        SalesView view = new SalesView(new Scanner(""));
        SalesItem item = new SalesItem("T001", "M001", "I001", "Nike", "Test Sneaker", "Black", 99.99, 5);

        // Act
        view.displaySalesDetails(item);

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("T001"), "Should contain Sales ID");
        assertTrue(output.contains("M001"), "Should contain Member ID");
        assertTrue(output.contains("Nike"), "Should contain Brand");
        assertTrue(output.contains("99.99"), "Should contain Price");
    }

    // ==================== Report Display Tests ====================

    /**
     * Tests that report displays calculated totals.
     */
    @Test
    @DisplayName("Test Display Report - Shows Report Data")
    public void testDisplayReport_ShowsReportData() {
        // Arrange
        SalesView view = new SalesView(new Scanner(""));
        List<SalesItem> items = new ArrayList<>();
        items.add(new SalesItem("T001", "M001", "I001", "Nike", "Sneaker 1", "Black", 100.00, 2));
        SalesService.SalesReport report = new SalesService.SalesReport(1, 2, 200.00);

        // Act
        view.displayReport(items, report);

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("200.00"), "Should contain total amount");
    }

    // ==================== Input Method Tests ====================

    /**
     * Nested tests for input methods.
     */
    @Nested
    @DisplayName("Input Method Tests")
    class InputMethodTests {

        /**
         * Tests getMenuChoice() returns the first character.
         */
        @Test
        @DisplayName("Test Get Menu Choice - Returns Correct Character")
        public void testGetMenuChoice_ReturnsCorrectCharacter() {
            SalesView view = new SalesView(new Scanner("1"));
            assertEquals('1', view.getMenuChoice(), "Should return '1'");
        }

        /**
         * Tests getString() returns user input.
         */
        @Test
        @DisplayName("Test Get String - Returns Input")
        public void testGetString_ReturnsInput() {
            SalesView view = new SalesView(new Scanner("T001"));
            assertEquals("T001", view.getString("Enter ID: "), "Should return input string");
        }

        /**
         * Tests getLine() returns full line including spaces.
         */
        @Test
        @DisplayName("Test Get Line - Returns Full Line")
        public void testGetLine_ReturnsFullLine() {
            SalesView view = new SalesView(new Scanner("\nTest Description Line"));
            assertEquals("Test Description Line", view.getLine("Enter desc: "), "Should return full line");
        }

        /**
         * Tests getDouble() returns parsed double value.
         */
        @Test
        @DisplayName("Test Get Double - Returns Double Value")
        public void testGetDouble_ReturnsDoubleValue() {
            SalesView view = new SalesView(new Scanner("99.99"));
            assertEquals(99.99, view.getDouble("Enter price: "), 0.01, "Should return double");
        }

        /**
         * Tests getInt() returns parsed integer value.
         */
        @Test
        @DisplayName("Test Get Int - Returns Integer Value")
        public void testGetInt_ReturnsIntegerValue() {
            SalesView view = new SalesView(new Scanner("5"));
            assertEquals(5, view.getInt("Enter quantity: "), "Should return integer");
        }

        /**
         * Tests getConfirmation() returns true for Y and false for N.
         */
        @Test
        @DisplayName("Test Get Confirmation - Yes Returns True, No Returns False")
        public void testGetConfirmation_YesNo() {
            SalesView viewYes = new SalesView(new Scanner("Y"));
            assertTrue(viewYes.getConfirmation("Confirm? "), "Y should return true");

            SalesView viewNo = new SalesView(new Scanner("n"));
            assertFalse(viewNo.getConfirmation("Confirm? "), "n should return false");
        }
    }

    // ==================== Brand and Colour Choice Tests ====================

    /**
     * Nested tests for brand and colour selection.
     */
    @Nested
    @DisplayName("Choice Selection Tests")
    class ChoiceSelectionTests {

        /**
         * Tests getBrandChoice() returns correct brand for each option.
         */
        @Test
        @DisplayName("Test Get Brand Choice - Returns Correct Brand")
        public void testGetBrandChoice_ReturnsCorrectBrand() {
            assertEquals("NIKE", new SalesView(new Scanner("1")).getBrandChoice());
            assertEquals("PUMA", new SalesView(new Scanner("2")).getBrandChoice());
            assertEquals("ADIDAS", new SalesView(new Scanner("3")).getBrandChoice());
        }

        /**
         * Tests getColourChoice() returns correct colour for each option.
         */
        @Test
        @DisplayName("Test Get Colour Choice - Returns Correct Colour")
        public void testGetColourChoice_ReturnsCorrectColour() {
            assertEquals("Black", new SalesView(new Scanner("1")).getColourChoice());
            assertEquals("White", new SalesView(new Scanner("2")).getColourChoice());
        }
    }

    // ==================== Message Display Tests ====================

    /**
     * Tests that all message methods display correctly.
     */
    @Test
    @DisplayName("Test Show Messages - Display Correctly")
    public void testShowMessages_DisplayCorrectly() {
        // Arrange
        SalesView view = new SalesView(new Scanner(""));

        // Act
        view.showSuccess("Success!");
        view.showError("Error!");
        view.showInfo("Info!");

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Success!"), "Should contain success message");
        assertTrue(output.contains("Error!"), "Should contain error message");
        assertTrue(output.contains("Info!"), "Should contain info message");
    }

    // ==================== Utility Method Tests ====================

    /**
     * Tests that waitForEnter() displays the prompt.
     */
    @Test
    @DisplayName("Test Wait For Enter - Displays Prompt")
    public void testWaitForEnter_DisplaysPrompt() {
        // Arrange
        SalesView view = new SalesView(new Scanner("\n\n"));

        // Act
        view.waitForEnter();

        // Assert
        assertTrue(outputStream.toString().contains("Press Enter"), "Should show prompt");
    }
}
