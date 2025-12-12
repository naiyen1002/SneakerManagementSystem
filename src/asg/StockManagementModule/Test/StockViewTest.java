package asg.StockManagementModule.Test;

import asg.StockManagementModule.Constants.StockConstants;
import asg.StockManagementModule.Constants.StockMenuOption;
import asg.StockManagementModule.Model.StockItem;
import asg.StockManagementModule.View.StockView;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for StockView class
 * 
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockViewTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private StockView view;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStream));
        Scanner scanner = new Scanner(new ByteArrayInputStream(new byte[0]));
        view = new StockView(scanner);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    private String getOutput() {
        return outputStream.toString();
    }

    // ------------------- MENU UI TESTS ------------------- //

    /**
     * View - Display Stock Menu with all options
     */
    @Test
    @Order(1)
    @DisplayName("View - Display Stock Menu")
    public void testDisplayMenuShowsAllOptions() {
        view.displayMenu();

        String output = getOutput();

        assertTrue(output.contains(StockConstants.MENU_TITLE),
                "Should display stock menu title");
        for (StockMenuOption opt : StockMenuOption.values()) {
            assertTrue(output.contains(opt.getDesc()),
                    "Menu should display option: " + opt.getDesc());
        }
    }

    // ------------------- MESSAGE DISPLAY TESTS ------------------- //

    /**
     * View - Display Error Message
     */
    @Test
    @Order(2)
    @DisplayName("View - Error Message Display")
    public void testDisplayErrorMessage() {
        view.displayErrorMessage(StockConstants.INVALID_CHOICE);

        String output = getOutput();
        assertTrue(output.contains("[ERROR]"),
                "Should prefix error with [ERROR]");
        assertTrue(output.contains(StockConstants.INVALID_CHOICE),
                "Should display error message content");
    }

    /**
     * View - Display Info Message
     */
    @Test
    @Order(3)
    @DisplayName("View - Info Message Display")
    public void testDisplayInfoMessage() {
        String msg = "Test info message";
        view.displayInfoMessage(msg);

        String output = getOutput();
        assertTrue(output.contains(msg),
                "Should display info message");
    }

    // ------------------- ITEM DISPLAY TESTS ------------------- //

    /**
     * View - Print Single Item Details
     */
    @Test
    @Order(4)
    @DisplayName("View - Print Item Details")
    public void testPrintItemDetails() {
        StockItem item = new StockItem(
                "I001",
                "Nike",
                "Air Max 90",
                "Black",
                399.90,
                10);

        view.printItemDetails(item);

        String output = getOutput();
        assertTrue(output.contains("Item code: I001"), "Should show item code");
        assertTrue(output.contains("Item brand: Nike"), "Should show brand");
        assertTrue(output.contains("Item description: Air Max 90"), "Should show description");
        assertTrue(output.contains("Item color: Black"), "Should show colour");
        assertTrue(output.contains("Item price: 399.9"), "Should show price");
        assertTrue(output.contains("Quantity in stock: 10"), "Should show quantity");
    }

    /**
     * View - Print Item Table with data
     */
    @Test
    @Order(5)
    @DisplayName("View - Print Item Table with Items")
    public void testPrintItemTableWithItems() {
        List<StockItem> items = Arrays.asList(
                new StockItem("I001", "Nike", "Air Max 90", "Black", 399.90, 10),
                new StockItem("I002", "Adidas", "Ultraboost", "White", 499.00, 5));

        view.printItemTable(items);

        String output = getOutput();

        assertTrue(output.contains("Code"), "Should print table header Code");
        assertTrue(output.contains("Brand"), "Should print table header Brand");
        assertTrue(output.contains("Description"), "Should print table header Description");
        assertTrue(output.contains("I001"), "Should contain first item code");
        assertTrue(output.contains("I002"), "Should contain second item code");
        assertTrue(output.contains("Nike"), "Should contain Nike");
        assertTrue(output.contains("Adidas"), "Should contain Adidas");
    }

    /**
     * View - Print Item Table when stock is empty
     */
    @Test
    @Order(6)
    @DisplayName("View - Print Item Table Empty")
    public void testPrintItemTableEmpty() {
        view.printItemTable(Collections.emptyList());

        String output = getOutput();
        assertTrue(output.contains(StockConstants.STOCK_EMPTY),
                "Should display stock empty message when no items");
    }

    // ------------------- INPUT (readLine / readInt / readDouble) TESTS
    // ------------------- //

    /**
     * View - readLine returns trimmed input
     */
    @Test
    @Order(7)
    @DisplayName("View - readLine Trims Input")
    public void testReadLine() {
        String input = "  hello stock  \n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        view = new StockView(new Scanner(in));

        String result = view.readLine("Enter text: ");
        assertEquals("hello stock", result,
                "readLine should trim whitespace");
    }

    /**
     * View - readInt handles invalid then valid input
     */
    @Test
    @Order(8)
    @DisplayName("View - readInt Handles Invalid Then Valid")
    public void testReadIntInvalidThenValid() {
        String input = "abc\n5\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        view = new StockView(new Scanner(in));

        int value = view.readInt("Enter number: ");
        String output = getOutput();

        assertEquals(5, value, "Should return the valid integer after retry");
        assertTrue(output.contains(StockConstants.INVALID_NUMBER),
                "Should print invalid number message for wrong input");
    }

    /**
     * View - readDouble handles invalid then valid input
     */
    @Test
    @Order(9)
    @DisplayName("View - readDouble Handles Invalid Then Valid")
    public void testReadDoubleInvalidThenValid() {
        String input = "xyz\n199.9\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        view = new StockView(new Scanner(in));

        double value = view.readDouble("Enter price: ");
        String output = getOutput();

        assertEquals(199.9, value, 0.0001,
                "Should return the valid double after retry");
        assertTrue(output.contains(StockConstants.INVALID_NUMBER),
                "Should print invalid number message for wrong input");
    }
}
