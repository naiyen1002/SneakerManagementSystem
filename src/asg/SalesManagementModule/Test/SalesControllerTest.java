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
 * Tests controller logic using dependency injection for View and Service.
 */
public class SalesControllerTest {

    private SalesService service;
    private SalesDAO dao;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        SalesDAO.resetInstance();
        dao = SalesDAO.getInstance();
        dao.clearAll();
        service = new SalesService(dao);

        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        dao.clearAll();
        SalesDAO.resetInstance();
        System.setOut(originalOut);
    }

    // ==================== handleDisplay Tests ====================

    @Test
    @DisplayName("Test Handle Display - Shows All Sales Records")
    public void testHandleDisplay_ShowsAllSales() {
        service.addSales(createTestItem("T001"));
        service.addSales(createTestItem("T002"));

        String input = "\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        controller.handleDisplay();

        String output = outputStream.toString();
        assertTrue(output.contains("T001"));
        assertTrue(output.contains("T002"));
        assertTrue(output.contains("2 records"));
    }

    // ==================== handleSearch Tests ====================

    @Test
    @DisplayName("Test Handle Search - Finds Existing Item")
    public void testHandleSearch_FindsExistingItem() {
        service.addSales(createTestItem("T001"));

        String input = "1\nT001\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        controller.handleSearch();

        String output = outputStream.toString();
        assertTrue(output.contains("T001") || output.contains("Found"));
    }

    @Test
    @DisplayName("Test Handle Search - No Results Found")
    public void testHandleSearch_NoResultsFound() {
        String input = "1\nXXXX\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        controller.handleSearch();

        String output = outputStream.toString();
        assertTrue(output.contains("No matching"));
    }

    // ==================== handleReport Tests ====================

    @Test
    @DisplayName("Test Handle Report - Shows Report Data")
    public void testHandleReport_ShowsReportData() {
        service.addSales(new SalesItem("T001", "M001", "I001", "Nike", "Test", "Black", 100.00, 2));
        service.addSales(new SalesItem("T002", "M002", "I002", "Puma", "Test2", "White", 50.00, 3));

        String input = "\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        controller.handleReport();

        String output = outputStream.toString();
        assertTrue(output.contains("350.00") || output.contains("Total"));
    }

    // ==================== handleDelete Tests ====================

    @Test
    @DisplayName("Test Handle Delete - Deletes Existing Item")
    public void testHandleDelete_DeletesExistingItem() {
        service.addSales(createTestItem("T001"));
        assertEquals(1, service.getAllSales().size());

        String input = "T001\nY\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        controller.handleDelete();

        assertEquals(0, service.getAllSales().size());
    }

    @Test
    @DisplayName("Test Handle Delete - Non-Existing Item Shows Error")
    public void testHandleDelete_NonExistingItem_ShowsError() {
        String input = "XXXX\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        controller.handleDelete();

        assertTrue(outputStream.toString().contains("not found"));
    }

    @Test
    @DisplayName("Test Handle Delete - Cancel Keeps Item")
    public void testHandleDelete_CancelDelete_ItemRemains() {
        service.addSales(createTestItem("T001"));

        String input = "T001\nN\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        controller.handleDelete();

        assertTrue(service.findById("T001").isPresent());
    }

    // ==================== handleModify Tests ====================

    @Test
    @DisplayName("Test Handle Modify - Modifies Existing Item")
    public void testHandleModify_ModifiesExistingItem() {
        service.addSales(createTestItem("T001"));

        String input = "T001\nT002\nM002\nI002\n1\nUpdated Desc\n1\n150.00\n10\nY\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        controller.handleModify();

        Optional<SalesItem> modified = service.findById("T002");
        assertTrue(modified.isPresent(), "Expected modified item to exist with new ID");
    }

    // ==================== handleAdd Tests ====================

    @Test
    @DisplayName("Test Handle Add - Adds New Item")
    public void testHandleAdd_AddsNewItem() {
        String input = "T001\nM001\nI001\n1\nTest Sneaker\n1\n99.99\n5\nY\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        controller.handleAdd();

        List<SalesItem> allSales = service.getAllSales();
        assertEquals(1, allSales.size());
        assertEquals("T001", allSales.get(0).getSalesId());
    }

    @Test
    @DisplayName("Test Handle Add - Cancel Adds Nothing")
    public void testHandleAdd_CancelAdd_NothingAdded() {
        String input = "T001\nM001\nI001\n1\nTest Sneaker\n1\n99.99\n5\nN\nN\n\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        controller.handleAdd();

        assertEquals(0, service.getAllSales().size());
    }

    // ==================== run() Menu Tests ====================

    @Test
    @DisplayName("Test Run - Exit Immediately")
    public void testRun_ExitImmediately() {
        String input = "0\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        controller.run();

        assertTrue(outputStream.toString().contains("Exiting"));
    }

    @Test
    @DisplayName("Test Run - Invalid Choice Shows Error")
    public void testRun_InvalidChoice_ShowsError() {
        String input = "9\n\n0\n";
        SalesView view = new SalesView(new Scanner(input));
        SalesController controller = new SalesController(service, view);

        controller.run();

        assertTrue(outputStream.toString().contains("Invalid"));
    }

    // ==================== Helper Methods ====================

    private SalesItem createTestItem(String salesId) {
        return new SalesItem(salesId, "M001", "I001", "Nike", "Test Sneaker", "Black", 99.99, 1);
    }
}
