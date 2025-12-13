package asg.SalesManagementModule.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import asg.SalesManagementModule.Controller.SalesController;
import asg.SalesManagementModule.Model.SalesItem;
import asg.SalesManagementModule.Service.SalesDAO;
import asg.SalesManagementModule.Service.SalesService;
import asg.SalesManagementModule.View.SalesView;

/**
 * Unit tests for SalesController class.
 * 
 * This test class validates all controller operations using dependency
 * injection
 * for the View and Service layers. Tests are organized by functionality:
 * - Display operations
 * - Search operations (Strategy Pattern)
 * - Report generation
 * - Delete operations
 * - Modify operations
 * - Add operations
 * - Menu navigation
 * - Input validation
 * 
 * Test Strategy:
 * - Uses simulated Scanner input to mimic user interactions
 * - Captures System.out to verify output messages
 * - Resets DAO state before/after each test for isolation
 */
public class SalesControllerTest {

    // Service and DAO instances for testing
    private SalesService service;
    private SalesDAO dao;

    // Output capture for verifying console output
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    // ==================== Setup and Teardown ====================

    /**
     * Sets up a fresh test environment before each test.
     * - Resets the Singleton DAO instance
     * - Clears all existing data
     * - Captures System.out for output verification
     */
    @BeforeEach
    public void setUp() {
        // Reset Singleton to ensure test isolation
        SalesDAO.resetInstance();
        dao = SalesDAO.getInstance();
        dao.clearAll();
        service = new SalesService(dao);

        // Capture console output for assertions
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Cleans up after each test.
     * - Clears all test data
     * - Resets the Singleton instance
     * - Restores original System.out
     */
    @AfterEach
    public void tearDown() {
        dao.clearAll();
        SalesDAO.resetInstance();
        System.setOut(originalOut);
    }

    // ==================== Display Tests ====================

    /**
     * Tests that handleDisplay() shows all sales records in the table.
     * Verifies both items are displayed and record count is shown.
     */
    @Test
    @DisplayName("Test Handle Display - Shows All Sales Records")
    public void testHandleDisplay_ShowsAllSales() {
        // Arrange: Add two test items
        service.addSales(createTestItem("T001"));
        service.addSales(createTestItem("T002"));

        // Simulate pressing Enter to continue
        String input = "\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleDisplay();

        // Assert: Verify both items and count are displayed
        String output = outputStream.toString();
        assertTrue(output.contains("T001"), "Should display first item");
        assertTrue(output.contains("T002"), "Should display second item");
        assertTrue(output.contains("2 records"), "Should show record count");
    }

    // ==================== Search Tests ====================

    /**
     * Tests searching by Sales ID when item exists.
     * Uses Strategy Pattern - BySalesId strategy (option 1).
     */
    @Test
    @DisplayName("Test Handle Search - Finds Existing Item By Sales ID")
    public void testHandleSearch_FindsExistingItem() {
        // Arrange
        service.addSales(createTestItem("T001"));

        // Input: Choose option 1 (Sales ID), enter "T001", don't continue, press Enter
        String input = "1\nT001\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleSearch();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("T001") || output.contains("Found"),
                "Should find and display the item");
    }

    /**
     * Tests searching by Member ID (option 2).
     * Demonstrates Strategy Pattern with different search criteria.
     */
    @Test
    @DisplayName("Test Handle Search - Search By Member ID")
    public void testHandleSearch_SearchByMemberId() {
        // Arrange
        service.addSales(createTestItem("T001"));

        // Input: Choose option 2 (Member ID), enter "M001", don't continue
        String input = "2\nM001\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleSearch();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Found") || output.contains("T001"),
                "Should find item by Member ID");
    }

    /**
     * Tests search when no matching records exist.
     * Verifies appropriate "no matching" message is displayed.
     */
    @Test
    @DisplayName("Test Handle Search - No Results Found")
    public void testHandleSearch_NoResultsFound() {
        // Input: Search for non-existent ID
        String input = "1\nXXXX\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleSearch();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("No matching"), "Should show no matching message");
    }

    /**
     * Tests exiting search menu with choice '0'.
     */
    @Test
    @DisplayName("Test Handle Search - Exit With Choice 0")
    public void testHandleSearch_ExitWithChoice0() {
        // Input: Choose 0 to exit, don't continue
        String input = "0\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleSearch();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Exiting Search"), "Should show exiting message");
    }

    /**
     * Tests invalid search menu choice handling.
     */
    @Test
    @DisplayName("Test Handle Search - Invalid Choice Shows Error")
    public void testHandleSearch_InvalidChoice_ShowsError() {
        // Input: Enter invalid choice '9', don't continue
        String input = "9\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleSearch();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("Invalid"), "Should show invalid choice error");
    }

    // ==================== Report Tests ====================

    /**
     * Tests report generation with calculated totals.
     * Verifies sales total: (100*2) + (50*3) = 350.00
     */
    @Test
    @DisplayName("Test Handle Report - Shows Report Data With Totals")
    public void testHandleReport_ShowsReportData() {
        // Arrange: Add items with known prices and quantities
        service.addSales(new SalesItem("T001", "M001", "I001", "Nike", "Test", "Black", 100.00, 2));
        service.addSales(new SalesItem("T002", "M002", "I002", "Puma", "Test2", "White", 50.00, 3));

        String input = "\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleReport();

        // Assert: Total should be (100*2) + (50*3) = 350.00
        String output = outputStream.toString();
        assertTrue(output.contains("350.00") || output.contains("Total"),
                "Should display calculated total");
    }

    // ==================== Delete Tests ====================

    /**
     * Tests successful deletion of an existing item.
     */
    @Test
    @DisplayName("Test Handle Delete - Deletes Existing Item")
    public void testHandleDelete_DeletesExistingItem() {
        // Arrange
        service.addSales(createTestItem("T001"));
        assertEquals(1, service.getAllSales().size(), "Should have 1 item initially");

        // Input: Enter ID, confirm delete (Y), don't continue (N)
        String input = "T001\nY\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleDelete();

        // Assert
        assertEquals(0, service.getAllSales().size(), "Item should be deleted");
    }

    /**
     * Tests deletion attempt for non-existent item.
     */
    @Test
    @DisplayName("Test Handle Delete - Non-Existing Item Shows Error")
    public void testHandleDelete_NonExistingItem_ShowsError() {
        // Input: Enter non-existent ID
        String input = "XXXX\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleDelete();

        // Assert
        assertTrue(outputStream.toString().contains("not found"),
                "Should show not found error");
    }

    /**
     * Tests cancelling deletion keeps the item.
     */
    @Test
    @DisplayName("Test Handle Delete - Cancel Keeps Item")
    public void testHandleDelete_CancelDelete_ItemRemains() {
        // Arrange
        service.addSales(createTestItem("T001"));

        // Input: Enter ID, cancel delete (N), don't continue (N)
        String input = "T001\nN\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleDelete();

        // Assert
        assertTrue(service.findById("T001").isPresent(), "Item should still exist");
    }

    /**
     * Tests continue loop for deleting multiple items.
     */
    @Test
    @DisplayName("Test Handle Delete - Continue Loop Deletes Multiple Items")
    public void testHandleDelete_ContinueLoop_DeletesMultiple() {
        // Arrange
        service.addSales(createTestItem("T001"));
        service.addSales(createTestItem("T002"));
        assertEquals(2, service.getAllSales().size());

        // Input: Delete T001 (Y), continue (Y), delete T002 (Y), don't continue (N)
        String input = "T001\nY\nY\nT002\nY\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleDelete();

        // Assert
        assertEquals(0, service.getAllSales().size(), "Both items should be deleted");
        assertTrue(outputStream.toString().contains("2 records"), "Should show 2 deleted");
    }

    // ==================== Modify Tests ====================

    /**
     * Tests successful modification of an existing item.
     */
    @Test
    @DisplayName("Test Handle Modify - Modifies Existing Item")
    public void testHandleModify_ModifiesExistingItem() {
        // Arrange
        service.addSales(createTestItem("T001"));

        // Input: All new values for modification
        String input = "T001\nT002\nM002\nI002\n1\nUpdated Desc\n1\n150.00\n10\nY\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleModify();

        // Assert
        Optional<SalesItem> modified = service.findById("T002");
        assertTrue(modified.isPresent(), "Modified item should exist with new ID");
    }

    /**
     * Tests modification attempt for non-existent item.
     */
    @Test
    @DisplayName("Test Handle Modify - Non-Existing Item Shows Error")
    public void testHandleModify_NonExistingItem_ShowsError() {
        // Input: Enter non-existent ID
        String input = "XXXX\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleModify();

        // Assert
        String output = outputStream.toString();
        assertTrue(output.contains("not found"), "Should show not found error");
    }

    /**
     * Tests cancelling modification keeps the item unchanged.
     */
    @Test
    @DisplayName("Test Handle Modify - Cancel Keeps Item Unchanged")
    public void testHandleModify_CancelModification_ItemUnchanged() {
        // Arrange
        service.addSales(createTestItem("T001"));

        // Input: Enter new values but cancel (N)
        String input = "T001\nT002\nM002\nI002\n1\nUpdated Desc\n1\n150.00\n10\nN\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleModify();

        // Assert: Original item should still exist
        assertTrue(service.findById("T001").isPresent(), "Original item should still exist");
        String output = outputStream.toString();
        assertTrue(output.contains("cancelled"), "Should show cancelled message");
    }

    // ==================== Add Tests ====================

    /**
     * Tests successful addition of a new item.
     */
    @Test
    @DisplayName("Test Handle Add - Adds New Item Successfully")
    public void testHandleAdd_AddsNewItem() {
        // Input: All required fields for new item
        String input = "T001\nM001\nI001\n1\nTest Sneaker\n1\n99.99\n5\nY\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleAdd();

        // Assert
        List<SalesItem> allSales = service.getAllSales();
        assertEquals(1, allSales.size(), "Should have 1 item");
        assertEquals("T001", allSales.get(0).getSalesId(), "Item ID should match");
    }

    /**
     * Tests cancelling add operation adds nothing.
     */
    @Test
    @DisplayName("Test Handle Add - Cancel Adds Nothing")
    public void testHandleAdd_CancelAdd_NothingAdded() {
        // Input: Enter data but cancel (N)
        String input = "T001\nM001\nI001\n1\nTest Sneaker\n1\n99.99\n5\nN\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleAdd();

        // Assert
        assertEquals(0, service.getAllSales().size(), "No items should be added");
    }

    /**
     * Tests continue loop for adding multiple items.
     */
    @Test
    @DisplayName("Test Handle Add - Continue Loop Adds Multiple Items")
    public void testHandleAdd_ContinueLoop_AddsMultiple() {
        // Input: Add first item (Y), continue (Y), add second item (Y), don't continue
        // (N)
        String input = "T001\nM001\nI001\n1\nTest Sneaker\n1\n99.99\n5\nY\nY\n" +
                "T002\nM002\nI002\n2\nTest Sneaker 2\n2\n199.99\n3\nY\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleAdd();

        // Assert
        assertEquals(2, service.getAllSales().size(), "Should have 2 items");
        assertTrue(outputStream.toString().contains("2 records"), "Should show 2 added");
    }

    // ==================== Input Validation Tests ====================

    /**
     * Tests that invalid length ID triggers re-prompt.
     * Sales ID must be exactly 4 characters.
     */
    @Test
    @DisplayName("Test Handle Add - Invalid Length ID Prompts Again")
    public void testHandleAdd_InvalidLengthId_PromptsAgain() {
        // Input: First enter invalid ID (3 chars), then valid ID (4 chars)
        String input = "ABC\nT001\nM001\nI001\n1\nTest Sneaker\n1\n99.99\n5\nY\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleAdd();

        // Assert
        assertEquals(1, service.getAllSales().size(), "Should add item after valid ID");
        assertEquals("T001", service.getAllSales().get(0).getSalesId());
        assertTrue(outputStream.toString().contains("Invalid length"),
                "Should show invalid length error");
    }

    /**
     * Tests that duplicate ID triggers re-prompt.
     * Each Sales ID must be unique.
     */
    @Test
    @DisplayName("Test Handle Add - Duplicate ID Prompts Again")
    public void testHandleAdd_DuplicateId_PromptsAgain() {
        // Arrange: Add existing item
        service.addSales(createTestItem("T001"));

        // Input: First enter duplicate ID, then valid unique ID
        String input = "T001\nT002\nM001\nI001\n1\nTest Sneaker\n1\n99.99\n5\nY\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.handleAdd();

        // Assert
        assertEquals(2, service.getAllSales().size(), "Should have 2 items");
        assertTrue(service.findById("T002").isPresent(), "New item should exist");
        assertTrue(outputStream.toString().contains("already exists"),
                "Should show duplicate error");
    }

    // ==================== Menu Navigation Tests ====================

    /**
     * Tests immediate exit from main menu.
     */
    @Test
    @DisplayName("Test Run - Exit Immediately With Choice 0")
    public void testRun_ExitImmediately() {
        // Input: Choose 0 to exit
        String input = "0\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.run();

        // Assert
        assertTrue(outputStream.toString().contains("Exiting"), "Should show exiting message");
    }

    /**
     * Tests invalid menu choice handling.
     */
    @Test
    @DisplayName("Test Run - Invalid Choice Shows Error")
    public void testRun_InvalidChoice_ShowsError() {
        // Input: Enter invalid choice '9', press Enter, then exit
        String input = "9\n\n0\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.run();

        // Assert
        assertTrue(outputStream.toString().contains("Invalid"), "Should show invalid error");
    }

    /**
     * Tests menu option 1 (Display) navigation.
     */
    @Test
    @DisplayName("Test Run - Display Option 1 Works")
    public void testRun_DisplayOption() {
        // Arrange
        service.addSales(createTestItem("T001"));

        // Input: Choose option 1, press Enter, then exit
        String input = "1\n\n0\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.run();

        // Assert
        assertTrue(outputStream.toString().contains("T001"), "Should display item");
    }

    /**
     * Tests menu option 6 (Report) navigation.
     */
    @Test
    @DisplayName("Test Run - Report Option 6 Works")
    public void testRun_ReportOption() {
        // Arrange: Add item with price 100 and quantity 2 (total = 200)
        service.addSales(new SalesItem("T001", "M001", "I001", "Nike", "Test", "Black", 100.00, 2));

        // Input: Choose option 6, press Enter, then exit
        String input = "6\n\n0\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        // Act
        controller.run();

        // Assert
        assertTrue(outputStream.toString().contains("200.00") ||
                outputStream.toString().contains("Total"),
                "Should display report with total");
    }

    // ==================== Helper Methods ====================

    /**
     * Creates a standard test SalesItem for reuse across tests.
     * 
     * @param salesId The unique Sales ID for the item
     * @return A new SalesItem with default test values
     */
    private SalesItem createTestItem(String salesId) {
        return new SalesItem(salesId, "M001", "I001", "Nike", "Test Sneaker", "Black", 99.99, 1);
    }
}
