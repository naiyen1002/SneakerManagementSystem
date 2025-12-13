package asg.StockManagementModule.Test;

import asg.MakeOrderModule.Model.StockItem;
import asg.StockManagementModule.Constants.StockConstants;
import asg.StockManagementModule.Constants.StockMenuOption;
import asg.StockManagementModule.Controller.*;
import asg.StockManagementModule.Service.StockService;
import asg.StockManagementModule.View.StockView;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class StockServiceAndCommandTest {

    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private StockItemController inventory;
    private StockController controller;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        inventory = new StockItemController();
        inventory.initializeDefaultStock();
        controller = new StockController(inventory);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private StockService createServiceWithInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);
        StockView view = new StockView(scanner);
        return new StockService(view, controller);
    }

    @Test
    @DisplayName("handleAddItem - valid inputs should add new item and show success message")
    void handleAddItem_validFlow_shouldAdd() {
        String userInput = "I150\nNike\nAir Zoom\nBlack\n399.9\n10\n";
        StockService service = createServiceWithInput(userInput);

        int initialSize = controller.getInventory().getAllItems().size();
        service.handleAddItem();
        int newSize = controller.getInventory().getAllItems().size();

        assertEquals(initialSize + 1, newSize);
        assertTrue(controller.getInventory().exists("I150"));

        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.ITEM_ADDED));
    }

    @Test
    @DisplayName("getValidItemCodeForAdd - duplicate then valid code should show error and use second code")
    void getValidItemCodeForAdd_duplicateThenValid() {
        String userInput = "I001\nI151\nNike\nAir Zoom\nBlack\n399.9\n10\n";
        StockService service = createServiceWithInput(userInput);

        int initialSize = controller.getInventory().getAllItems().size();
        service.handleAddItem();
        int newSize = controller.getInventory().getAllItems().size();

        assertEquals(initialSize + 1, newSize);
        assertTrue(controller.getInventory().exists("I151"));

        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.ITEM_EXISTS));
    }

    @Test
    @DisplayName("handleDeleteItem - item not found should show not found message and not delete anything")
    void handleDeleteItem_notFound_shouldShowError() {
        String userInput = "I999\n";
        StockService service = createServiceWithInput(userInput);

        int initialSize = controller.getInventory().getAllItems().size();
        service.handleDeleteItem();
        int newSize = controller.getInventory().getAllItems().size();

        assertEquals(initialSize, newSize);

        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.ITEM_NOT_FOUND));
    }

    @Test
    @DisplayName("handleDeleteItem - user enters no should cancel deletion")
    void handleDeleteItem_userCancel_shouldNotDelete() {
        String userInput = "I001\nno\n";
        StockService service = createServiceWithInput(userInput);

        int initialSize = controller.getInventory().getAllItems().size();
        service.handleDeleteItem();
        int newSize = controller.getInventory().getAllItems().size();

        assertEquals(initialSize, newSize);

        String output = outputStream.toString().toLowerCase();
        assertTrue(output.contains("cancel"));
    }

    @Test
    @DisplayName("handleSearchItem - existing code should print item details")
    void handleSearchItem_found_shouldPrintDetails() {
        String userInput = "I001\n";
        StockService service = createServiceWithInput(userInput);

        service.handleSearchItem();

        String output = outputStream.toString();
        assertTrue(output.contains("Item code: I001"));
    }

    @Test
    @DisplayName("handleSearchItem - non-existing code should show not found message")
    void handleSearchItem_notFound_shouldShowError() {
        String userInput = "I999\n";
        StockService service = createServiceWithInput(userInput);

        service.handleSearchItem();

        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.ITEM_NOT_FOUND));
    }

    @Test
    @DisplayName("handleModifyItem - not found should display error and no change")
    void handleModifyItem_notFound() {
        String userInput = "I999\n";
        StockService service = createServiceWithInput(userInput);

        int before = controller.getInventory().getAllItems().size();
        service.handleModifyItem();
        int after = controller.getInventory().getAllItems().size();

        assertEquals(before, after);
        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.ITEM_NOT_FOUND));
    }

    @Test
    @DisplayName("handleModifyItem - valid update (menu flow) should change fields")
    void handleModifyItem_valid() {
        // New flow: code -> choose field -> new value -> ... -> save
        String userInput = "I001\n" +
                "1\nNewBrand\n" +
                "2\nNewDesc\n" +
                "3\nNewColor\n" +
                "4\n300.0\n" +
                "5\n20\n" +
                "6\n";

        StockService service = createServiceWithInput(userInput);
        service.handleModifyItem();

        var itemOpt = controller.getInventory().findByCode("I001");
        assertTrue(itemOpt.isPresent());
        assertEquals("Newbrand", itemOpt.get().getBrand()); // toTitleCase
        assertEquals("NewDesc", itemOpt.get().getItemDescription());
        assertEquals("Newcolor", itemOpt.get().getColour()); // toTitleCase
        assertEquals(300.0, itemOpt.get().getItemPrice(), 0.001);
        assertEquals(20, itemOpt.get().getQuantityInStock());

        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.ITEM_MODIFIED));
    }

    @Test
    @DisplayName("handleModifyItem - invalid menu choice then cancel should not update")
    void handleModifyItem_invalidChoiceThenCancel_shouldNotUpdate() {
        var beforeOpt = controller.getInventory().findByCode("I001");
        assertTrue(beforeOpt.isPresent());
        asg.StockManagementModule.Model.StockItem before = beforeOpt.get();

        String beforeBrand = before.getBrand();
        String beforeDesc = before.getItemDescription();
        String beforeColour = before.getColour();
        double beforePrice = before.getItemPrice();
        int beforeQty = before.getQuantityInStock();

        String userInput = "I001\n" +
                "A\n" + // invalid choice
                "0\n"; // cancel

        StockService service = createServiceWithInput(userInput);
        service.handleModifyItem();

        var afterOpt = controller.getInventory().findByCode("I001");
        assertTrue(afterOpt.isPresent());

        assertEquals(beforeBrand, afterOpt.get().getBrand());
        assertEquals(beforeDesc, afterOpt.get().getItemDescription());
        assertEquals(beforeColour, afterOpt.get().getColour());
        assertEquals(beforePrice, afterOpt.get().getItemPrice(), 0.001);
        assertEquals(beforeQty, afterOpt.get().getQuantityInStock());

        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.INVALID_CHOICE));
        assertTrue(output.toLowerCase().contains("cancel"));
    }

    @Test
    @DisplayName("handleDisplayItems - should print table with codes")
    void handleDisplayItems_shouldPrintList() {
        StockService service = createServiceWithInput("");
        service.handleDisplayItems();
        String output = outputStream.toString();
        assertTrue(output.contains("Code"));
        assertTrue(output.contains("I001"));
    }

    @Test
    @DisplayName("getConfirmation - invalid then yes")
    void getConfirmation_invalidThenYes() {
        String input = "maybe\nyes\n";
        StockService service = createServiceWithInput(input);

        boolean result = service.getConfirmation("Confirm? ");
        assertTrue(result);

        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.ERROR_YES_NO_ONLY));
    }

    @Test
    @DisplayName("getValidPrice - negative token then valid number should reject and accept")
    void getValidPrice_negativeThenValid_shouldWork() {
        String input = "-0\n10\n";
        StockService service = createServiceWithInput(input);

        double price = service.getValidPrice(false);
        assertEquals(10.0, price, 0.001);

        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.INVALID_PRICE));
    }

    @Test
    @DisplayName("getValidPrice - invalid number then valid number should retry")
    void getValidPrice_invalidNumberThenValid_shouldWork() {
        String input = "abc\n15.5\n";
        StockService service = createServiceWithInput(input);

        double price = service.getValidPrice(false);
        assertEquals(15.5, price, 0.001);

        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.INVALID_NUMBER));
    }

    @Test
    @DisplayName("getValidQuantity - negative token then valid int should reject and accept")
    void getValidQuantity_negativeThenValid_shouldWork() {
        String input = "-1\n5\n";
        StockService service = createServiceWithInput(input);

        int qty = service.getValidQuantity(false);
        assertEquals(5, qty);

        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.INVALID_QTY));
    }

    @Test
    @DisplayName("getValidQuantity - invalid number then valid int should retry")
    void getValidQuantity_invalidNumberThenValid_shouldWork() {
        String input = "abc\n7\n";
        StockService service = createServiceWithInput(input);

        int qty = service.getValidQuantity(false);
        assertEquals(7, qty);

        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.INVALID_NUMBER));
    }

    @Test
    @DisplayName("getValidBrand - digit brand then valid brand should retry and title-case")
    void getValidBrand_digitThenValid_shouldWork() {
        String input = "Nike123\nadidas\n";
        StockService service = createServiceWithInput(input);

        String brand = service.getValidBrand(false);
        assertEquals("Adidas", brand);

        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.INVALID_BRAND));
    }

    @Test
    @DisplayName("CommandFactory should map menu option to correct command type")
    void commandFactory_shouldReturnCorrectCommand() {
        StockService dummyService = createServiceWithInput("");
        StockCommand cmdAdd = StockCommandFactory.fromOption(StockMenuOption.ADD_ITEM, dummyService);
        StockCommand cmdExit = StockCommandFactory.fromOption(StockMenuOption.EXIT, dummyService);
        StockCommand cmdInvalid = StockCommandFactory.fromOption(null, dummyService);

        assertNotNull(cmdAdd);
        assertTrue(cmdAdd instanceof AddItemCommand);

        assertNotNull(cmdExit);
        assertTrue(cmdExit instanceof ExitCommand);

        assertNull(cmdInvalid);
    }

    @Test
    @DisplayName("AddItemCommand execute() should delegate to service and add item")
    void addItemCommand_execute_shouldDelegate() {
        String userInput = "I160\nNike\nPegasus\nGrey\n350.0\n8\n";
        StockService service = createServiceWithInput(userInput);

        int initialSize = controller.getInventory().getAllItems().size();

        StockCommand cmd = new AddItemCommand(service);
        cmd.execute();

        int newSize = controller.getInventory().getAllItems().size();
        assertEquals(initialSize + 1, newSize);
        assertTrue(controller.getInventory().exists("I160"));
    }

    @Test
    @DisplayName("DeleteItemCommand execute() should delete item when user confirms yes")
    void deleteItemCommand_execute_shouldDelete() {
        assertTrue(controller.getInventory().exists("I001"));

        String input = "I001\nyes\n";
        StockService service = createServiceWithInput(input);

        int before = controller.getInventory().getAllItems().size();

        StockCommand cmd = new DeleteItemCommand(service);
        cmd.execute();

        int after = controller.getInventory().getAllItems().size();
        assertEquals(before - 1, after);
        assertFalse(controller.getInventory().exists("I001"));
    }

    @Test
    @DisplayName("DisplayItemCommand execute() should list items")
    void displayItemCommand_execute_shouldPrintList() {
        StockService service = createServiceWithInput("");

        StockCommand cmd = new DisplayItemCommand(service);
        cmd.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Code"));
        assertTrue(output.contains("I001"));
    }

    @Test
    @DisplayName("SearchItemCommand execute() should search and print item details")
    void searchItemCommand_execute_shouldSearch() {
        String input = "I001\n";
        StockService service = createServiceWithInput(input);

        StockCommand cmd = new SearchItemCommand(service);
        cmd.execute();

        String output = outputStream.toString();
        assertTrue(output.contains("Item code: I001"));
    }

    @Test
    @DisplayName("ModifyItemCommand execute() should update item via service (menu flow)")
    void modifyItemCommand_execute_shouldModify() {
        String input = "I001\n" +
                "1\nBrandX\n" +
                "2\nDescX\n" +
                "3\nColorX\n" +
                "4\n250.0\n" +
                "5\n15\n" +
                "6\n";

        StockService service = createServiceWithInput(input);

        StockCommand cmd = new ModifyItemCommand(service);
        cmd.execute();

        var itemOpt = controller.getInventory().findByCode("I001");
        assertTrue(itemOpt.isPresent());
        assertEquals("Brandx", itemOpt.get().getBrand()); // toTitleCase
        assertEquals("DescX", itemOpt.get().getItemDescription());
        assertEquals("Colorx", itemOpt.get().getColour()); // toTitleCase
        assertEquals(250.0, itemOpt.get().getItemPrice(), 0.001);
        assertEquals(15, itemOpt.get().getQuantityInStock());
    }

    @Test
    @DisplayName("ExitCommand execute() should print goodbye message")
    void exitCommand_execute_shouldPrintGoodbye() {

        StockCommand cmd = new ExitCommand();
        cmd.execute();

        String output = outputStream.toString();
        assertTrue(output.contains(StockConstants.GOODBYE));
    }
}
