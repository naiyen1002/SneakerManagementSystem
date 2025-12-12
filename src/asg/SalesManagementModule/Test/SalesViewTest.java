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
 * Tests display methods and input handling.
 */
public class SalesViewTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    // ==================== Menu Display Tests ====================

    @Test
    @DisplayName("Test Display Main Menu - Contains Menu Title and Options")
    public void testDisplayMainMenu_ContainsMenuElements() {
        SalesView view = new SalesView(new Scanner(""));
        view.displayMainMenu();
        String output = outputStream.toString();
        assertTrue(output.contains("SALES MANAGEMENT MENU"));
        assertTrue(output.contains("Enter your choice"));
    }

    @Test
    @DisplayName("Test Display Search Menu - Contains All Search Options")
    public void testDisplaySearchMenu_ContainsAllSearchOptions() {
        SalesView view = new SalesView(new Scanner(""));
        view.displaySearchMenu();
        String output = outputStream.toString();
        assertTrue(output.contains("SEARCH MENU"));
        assertTrue(output.contains("SALES ID"));
        assertTrue(output.contains("MEMBER ID"));
        assertTrue(output.contains("BRAND"));
        assertTrue(output.contains("COLOUR"));
        assertTrue(output.contains("PRICE"));
    }

    // ==================== Table Display Tests ====================

    @Nested
    @DisplayName("Table Display Tests")
    class TableDisplayTests {

        @Test
        @DisplayName("Test Display Table Header - Shows Column Headers")
        public void testDisplayTableHeader_ShowsColumnHeaders() {
            SalesView view = new SalesView(new Scanner(""));
            view.displayTableHeader();
            String output = outputStream.toString();
            assertTrue(output.contains("SALES ID"));
            assertTrue(output.contains("MEMBER ID"));
            assertTrue(output.contains("ITEM CODE"));
        }

        @Test
        @DisplayName("Test Display Table Row - Shows Item Data")
        public void testDisplayTableRow_ShowsItemData() {
            SalesView view = new SalesView(new Scanner(""));
            SalesItem item = new SalesItem("T001", "M001", "I001", "Nike", "Test Sneaker", "Black", 99.99, 5);
            view.displayTableRow(item);
            String output = outputStream.toString();
            assertTrue(output.contains("T001"));
            assertTrue(output.contains("M001"));
            assertTrue(output.contains("Nike"));
        }

        @Test
        @DisplayName("Test Display Sales Table - Shows All Items and Record Count")
        public void testDisplaySalesTable_ShowsAllItems() {
            SalesView view = new SalesView(new Scanner(""));
            List<SalesItem> items = new ArrayList<>();
            items.add(new SalesItem("T001", "M001", "I001", "Nike", "Sneaker 1", "Black", 99.99, 5));
            items.add(new SalesItem("T002", "M002", "I002", "Puma", "Sneaker 2", "White", 149.99, 3));

            view.displaySalesTable(items);
            String output = outputStream.toString();
            assertTrue(output.contains("T001"));
            assertTrue(output.contains("T002"));
            assertTrue(output.contains("2 records"));
        }

        @Test
        @DisplayName("Test Display Sales Table - Empty List Shows Zero Records")
        public void testDisplaySalesTable_EmptyList() {
            SalesView view = new SalesView(new Scanner(""));
            view.displaySalesTable(new ArrayList<>());
            String output = outputStream.toString();
            assertTrue(output.contains("0 records"));
        }
    }

    // ==================== Sales Details Display Tests ====================

    @Test
    @DisplayName("Test Display Sales Details - Shows All Fields")
    public void testDisplaySalesDetails_ShowsAllFields() {
        SalesView view = new SalesView(new Scanner(""));
        SalesItem item = new SalesItem("T001", "M001", "I001", "Nike", "Test Sneaker", "Black", 99.99, 5);

        view.displaySalesDetails(item);
        String output = outputStream.toString();
        assertTrue(output.contains("T001"));
        assertTrue(output.contains("M001"));
        assertTrue(output.contains("Nike"));
        assertTrue(output.contains("99.99"));
    }

    // ==================== Report Display Tests ====================

    @Test
    @DisplayName("Test Display Report - Shows Report Data")
    public void testDisplayReport_ShowsReportData() {
        SalesView view = new SalesView(new Scanner(""));
        List<SalesItem> items = new ArrayList<>();
        items.add(new SalesItem("T001", "M001", "I001", "Nike", "Sneaker 1", "Black", 100.00, 2));

        SalesService.SalesReport report = new SalesService.SalesReport(1, 2, 200.00);

        view.displayReport(items, report);
        String output = outputStream.toString();
        assertTrue(output.contains("200.00")); // total amount
    }

    // ==================== Input Method Tests ====================

    @Nested
    @DisplayName("Input Method Tests")
    class InputMethodTests {

        @Test
        @DisplayName("Test Get Menu Choice - Returns Correct Character")
        public void testGetMenuChoice_ReturnsCorrectCharacter() {
            SalesView view = new SalesView(new Scanner("1"));
            assertEquals('1', view.getMenuChoice());
        }

        @Test
        @DisplayName("Test Get String - Returns Input")
        public void testGetString_ReturnsInput() {
            SalesView view = new SalesView(new Scanner("T001"));
            assertEquals("T001", view.getString("Enter ID: "));
        }

        @Test
        @DisplayName("Test Get Line - Returns Full Line")
        public void testGetLine_ReturnsFullLine() {
            SalesView view = new SalesView(new Scanner("\nTest Description Line"));
            assertEquals("Test Description Line", view.getLine("Enter desc: "));
        }

        @Test
        @DisplayName("Test Get Double - Returns Double Value")
        public void testGetDouble_ReturnsDoubleValue() {
            SalesView view = new SalesView(new Scanner("99.99"));
            assertEquals(99.99, view.getDouble("Enter price: "), 0.01);
        }

        @Test
        @DisplayName("Test Get Int - Returns Integer Value")
        public void testGetInt_ReturnsIntegerValue() {
            SalesView view = new SalesView(new Scanner("5"));
            assertEquals(5, view.getInt("Enter quantity: "));
        }

        @Test
        @DisplayName("Test Get Confirmation - Yes Returns True, No Returns False")
        public void testGetConfirmation_YesNo() {
            SalesView viewYes = new SalesView(new Scanner("Y"));
            assertTrue(viewYes.getConfirmation("Confirm? "));

            SalesView viewNo = new SalesView(new Scanner("n"));
            assertFalse(viewNo.getConfirmation("Confirm? "));
        }
    }

    // ==================== Brand and Colour Choice Tests ====================

    @Nested
    @DisplayName("Choice Selection Tests")
    class ChoiceSelectionTests {

        @Test
        @DisplayName("Test Get Brand Choice - Returns Correct Brand")
        public void testGetBrandChoice_ReturnsCorrectBrand() {
            assertEquals("NIKE", new SalesView(new Scanner("1")).getBrandChoice());
            assertEquals("PUMA", new SalesView(new Scanner("2")).getBrandChoice());
            assertEquals("ADIDAS", new SalesView(new Scanner("3")).getBrandChoice());
        }

        @Test
        @DisplayName("Test Get Colour Choice - Returns Correct Colour")
        public void testGetColourChoice_ReturnsCorrectColour() {
            assertEquals("Black", new SalesView(new Scanner("1")).getColourChoice());
            assertEquals("White", new SalesView(new Scanner("2")).getColourChoice());
        }
    }

    // ==================== Message Display Tests ====================

    @Test
    @DisplayName("Test Show Messages - Display Correctly")
    public void testShowMessages_DisplayCorrectly() {
        SalesView view = new SalesView(new Scanner(""));

        view.showSuccess("Success!");
        view.showError("Error!");
        view.showInfo("Info!");

        String output = outputStream.toString();
        assertTrue(output.contains("Success!"));
        assertTrue(output.contains("Error!"));
        assertTrue(output.contains("Info!"));
    }

    // ==================== Utility Method Tests ====================

    @Test
    @DisplayName("Test Wait For Enter - Displays Prompt")
    public void testWaitForEnter_DisplaysPrompt() {
        SalesView view = new SalesView(new Scanner("\n\n"));
        view.waitForEnter();
        assertTrue(outputStream.toString().contains("Press Enter"));
    }
}
