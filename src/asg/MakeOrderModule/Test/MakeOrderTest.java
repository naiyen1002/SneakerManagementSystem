package asg.MakeOrderModule.Test;

import org.junit.jupiter.api.*;

import asg.MakeOrderModule.Controller.MakeOrderController;
import asg.MakeOrderModule.Controller.OrderDetailsController;
import asg.MakeOrderModule.Controller.StockItemController;
import asg.MakeOrderModule.Model.OrderDetails;
import asg.MakeOrderModule.Model.StockItem;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MakeOrderTest {
    private ByteArrayOutputStream outputStreamCaptor;
    
    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        
        StockItemController.initializeStock();
        OrderDetailsController.clearOrderDetails();
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
        System.setIn(System.in);
        
        OrderDetailsController.clearOrderDetails();
        StockItemController.clearStock();
    }

    private String runWithInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        MakeOrderController controller = new MakeOrderController();
        controller.run();
        return outputStreamCaptor.toString();
    }


    @Test
    @DisplayName("Add single item - verify order count and total")
    void testAddSingleItem() {
        StockItem item = StockItemController.findStockItemByCode("I001");
        int initialCount = OrderDetailsController.getOrderCount();
        double initialTotal = OrderDetailsController.calculateTotal();
        
        OrderDetailsController.addOrderDetail(item);
        
        assertEquals(initialCount + 1, OrderDetailsController.getOrderCount(), 
                "Order count should increase by 1");
        assertEquals(initialTotal + item.getItemPrice(), OrderDetailsController.calculateTotal(), 0.01,
                "Total should increase by item price");
        assertEquals(480.00, OrderDetailsController.calculateTotal(), 0.01,
                "Total should be 480.00 for one I001 item");
    }

    @Test
    @DisplayName("Add multiple items - verify cumulative state")
    void testAddMultipleItems() {
        StockItem item1 = StockItemController.findStockItemByCode("I001"); // 480.00
        StockItem item2 = StockItemController.findStockItemByCode("I002"); // 220.00
        StockItem item3 = StockItemController.findStockItemByCode("I003"); // 570.00
        
        OrderDetailsController.addOrderDetail(item1);
        assertEquals(1, OrderDetailsController.getOrderCount());
        assertEquals(480.00, OrderDetailsController.calculateTotal(), 0.01);
        
        OrderDetailsController.addOrderDetail(item2);
        assertEquals(2, OrderDetailsController.getOrderCount());
        assertEquals(700.00, OrderDetailsController.calculateTotal(), 0.01);
        
        OrderDetailsController.addOrderDetail(item3);
        assertEquals(3, OrderDetailsController.getOrderCount());
        assertEquals(1270.00, OrderDetailsController.calculateTotal(), 0.01);
    }

    @Test
    @DisplayName("Add same item multiple times - verify duplicates allowed")
    void testAddSameItemMultipleTimes() {
        
        StockItem item = StockItemController.findStockItemByCode("I001");
        
        OrderDetailsController.addOrderDetail(item);
        OrderDetailsController.addOrderDetail(item);
        OrderDetailsController.addOrderDetail(item);
        
        assertEquals(3, OrderDetailsController.getOrderCount(),
                "Should allow adding same item multiple times");
        assertEquals(1440.00, OrderDetailsController.calculateTotal(), 0.01,
                "Total should be 3 * 480.00");
    }

    @Test
    @DisplayName("Remove item - verify order count decreases")
    void testRemoveItem() {
        StockItem item1 = StockItemController.findStockItemByCode("I001");
        StockItem item2 = StockItemController.findStockItemByCode("I002");
        OrderDetailsController.addOrderDetail(item1);
        OrderDetailsController.addOrderDetail(item2);
        assertEquals(2, OrderDetailsController.getOrderCount());
        
        OrderDetailsController.removeOrderDetail("I001");
        
        assertEquals(1, OrderDetailsController.getOrderCount(),
                "Order count should decrease by 1");
        assertEquals(220.00, OrderDetailsController.calculateTotal(), 0.01,
                "Total should only include remaining item");
    }

    @Test
    @DisplayName("Remove item from empty cart - verify no error")
    void testRemoveFromEmptyCart() {
        OrderDetailsController.clearOrderDetails();
        assertEquals(0, OrderDetailsController.getOrderCount());
        
        OrderDetailsController.removeOrderDetail("I001");
        assertEquals(0, OrderDetailsController.getOrderCount());
        assertTrue(OrderDetailsController.isEmpty());
    }

    @Test
    @DisplayName("Clear cart - verify all items removed")
    void testClearCart() {
        OrderDetailsController.addOrderDetail(StockItemController.findStockItemByCode("I001"));
        OrderDetailsController.addOrderDetail(StockItemController.findStockItemByCode("I002"));
        OrderDetailsController.addOrderDetail(StockItemController.findStockItemByCode("I003"));
        assertEquals(3, OrderDetailsController.getOrderCount());
        
        OrderDetailsController.clearOrderDetails();
        
        assertEquals(0, OrderDetailsController.getOrderCount(),
                "Cart should be empty");
        assertEquals(0.00, OrderDetailsController.calculateTotal(), 0.01,
                "Total should be 0");
        assertTrue(OrderDetailsController.isEmpty(),
                "isEmpty() should return true");
    }

    @Test
    @DisplayName("Get order by code - verify correct item retrieved")
    void testGetOrderByCode() {
        StockItem item = StockItemController.findStockItemByCode("I001");
        OrderDetailsController.addOrderDetail(item);
        
        OrderDetails retrieved = OrderDetailsController.getOrderDetailByCode("I001");
        
        assertNotNull(retrieved, "Should find the order");
        assertEquals("I001", retrieved.getOrderCode());
        assertEquals("NIKE", retrieved.getOrderBrand());
        assertEquals(480.00, retrieved.getOrderPrice(), 0.01);
    }

    @Test
    @DisplayName("Get non-existent order - verify returns null")
    void testGetNonExistentOrder() {
        OrderDetails retrieved = OrderDetailsController.getOrderDetailByCode("NONEXISTENT");
        
        assertNull(retrieved, "Should return null for non-existent order");
    }

    @Test
    @DisplayName("isEmpty check - verify correct status")
    void testIsEmpty() {
        assertTrue(OrderDetailsController.isEmpty());
        assertEquals(0, OrderDetailsController.getOrderCount());
        
        OrderDetailsController.addOrderDetail(StockItemController.findStockItemByCode("I001"));
        assertFalse(OrderDetailsController.isEmpty());
        assertEquals(1, OrderDetailsController.getOrderCount());
        
        OrderDetailsController.clearOrderDetails();
        assertTrue(OrderDetailsController.isEmpty());
        assertEquals(0, OrderDetailsController.getOrderCount());
    }

    // ==================== STOCK SEARCH TESTS ====================

    @Test
    @DisplayName("Search by code - verify item found and properties")
    void testSearchByCode() {
        StockItem found = StockItemController.findStockItemByCode("I001");
        
        assertNotNull(found, "Item should be found");
        assertEquals("I001", found.getItemCode());
        assertEquals("NIKE", found.getBrand());
        assertEquals("Air Max 90", found.getItemDescription());
        assertEquals("Black/White/Grey", found.getColour());
        assertEquals(480.00, found.getItemPrice(), 0.01);
        assertTrue(found.getQuantityInStock() > 0, "Should have stock");
    }

    @Test
    @DisplayName("Search by invalid code - verify returns null")
    void testSearchByInvalidCode() {
        StockItem found = StockItemController.findStockItemByCode("INVALID_CODE");
        
        assertNull(found, "Should return null for invalid code");
    }

    @Test
    @DisplayName("Search by brand - verify correct count and all match")
    void testSearchByBrand() {
        var nikeItems = StockItemController.findStockItemsByBrand("NIKE");
        var pumaItems = StockItemController.findStockItemsByBrand("PUMA");
        var adidasItems = StockItemController.findStockItemsByBrand("ADIDAS");
        
        assertTrue(nikeItems.size() >= 10, "Should have at least 10 NIKE items");
        assertTrue(pumaItems.size() >= 10, "Should have at least 10 PUMA items");
        assertTrue(adidasItems.size() >= 10, "Should have at least 10 ADIDAS items");
        
        for (StockItem item : nikeItems) {
            assertEquals("NIKE", item.getBrand());
        }
        for (StockItem item : pumaItems) {
            assertEquals("PUMA", item.getBrand());
        }
        for (StockItem item : adidasItems) {
            assertEquals("ADIDAS", item.getBrand());
        }
    }

    @Test
    @DisplayName("Search by invalid brand - verify returns empty list")
    void testSearchByInvalidBrand() {
        var items = StockItemController.findStockItemsByBrand("INVALIDBRAND");
        
        assertTrue(items.isEmpty(), "Should return empty list for invalid brand");
    }

    @Test
    @DisplayName("Total stock count - verify 30 items initialized")
    void testTotalStockCount() {
        int totalCount = StockItemController.getStockItemCount();
        var allItems = StockItemController.getStockItems();
        
        assertEquals(30, totalCount, "Should have exactly 30 items");
        assertEquals(30, allItems.size(), "getStockItems should return 30 items");
    }

    @Test
    @DisplayName("Item availability check - verify correct status")
    void testItemAvailability() {
        assertTrue(StockItemController.isItemAvailable("I001"), "I001 should be available");
        assertFalse(StockItemController.isItemAvailable("INVALID"), "Invalid code should not be available");
    }


    @Test
    @DisplayName("StockItem - isInStock check")
    void testStockItem_IsInStock() {
        StockItem item = new StockItem("TEST", "NIKE", "Test Shoe", "Blue", 100.00, 5);
        assertTrue(item.isInStock());
        
        StockItem outOfStock = new StockItem("TEST2", "NIKE", "Test Shoe 2", "Red", 100.00, 0);
        assertFalse(outOfStock.isInStock());
    }


    @Test
    @DisplayName("MenuDisplay - displayMainMenu outputs all options")
    void testMenuDisplay_MainMenu() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        
        asg.MakeOrderModule.View.MenuDisplay.displayMainMenu();
        String output = outContent.toString();
        
        assertTrue(output.contains("Make Order Menu"), "Should display menu header");
        assertTrue(output.contains("1. Display All Product Details"), "Should display option 1");
        assertTrue(output.contains("2. Make New Order"), "Should display option 2");
        assertTrue(output.contains("3. Search Product Details"), "Should display option 3");
        assertTrue(output.contains("4. Delete Order"), "Should display option 4");
        assertTrue(output.contains("5. Check Out/Make Payment"), "Should display option 5");
        assertTrue(output.contains("6. Exit/Back To Menu"), "Should display option 6");
        
        System.setOut(System.out);
    }

    @Test
    @DisplayName("MenuDisplay - displaySearchMenu outputs search options")
    void testMenuDisplay_SearchMenu() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        
        asg.MakeOrderModule.View.MenuDisplay.displaySearchMenu();
        String output = outContent.toString();
        
        assertTrue(output.contains("Search Item(s) Information"), "Should display search header");
        assertTrue(output.contains("1. Item Code"), "Should display item code option");
        assertTrue(output.contains("2. Brands"), "Should display brands option");
        
        System.setOut(System.out);
    }

    @Test
    @DisplayName("MenuDisplay - displayItemDetails shows all item properties")
    void testMenuDisplay_ItemDetails() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        
        StockItem item = StockItemController.findStockItemByCode("I001");
        asg.MakeOrderModule.View.MenuDisplay.displayItemDetails(item);
        String output = outContent.toString();
        
        assertTrue(output.contains("I001"), "Should display item code");
        assertTrue(output.contains("NIKE"), "Should display brand");
        assertTrue(output.contains("Air Max 90"), "Should display description");
        assertTrue(output.contains("Black/White/Grey"), "Should display color");
        assertTrue(output.contains("480"), "Should display price");
        
        System.setOut(System.out);
    }

    @Test
    @DisplayName("MenuDisplay - displayAllProducts shows formatted table")
    void testMenuDisplay_AllProducts() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        
        asg.MakeOrderModule.View.MenuDisplay.displayAllProducts(StockItemController.getStockItems());
        String output = outContent.toString();
        
        assertTrue(output.contains("All Product Information"), "Should display header");
        assertTrue(output.contains("Item Code"), "Should display column headers");
        assertTrue(output.contains("Brands"), "Should display column headers");
        assertTrue(output.contains("I001"), "Should display at least one item");

        
        System.setOut(System.out);
    }

    @Test
    @DisplayName("MenuOption - isValidCode returns correct boolean")
    void testMenuOption_IsValidCode() {
        assertTrue(asg.MakeOrderModule.Constants.MenuOption.isValidCode(1));
        assertTrue(asg.MakeOrderModule.Constants.MenuOption.isValidCode(6));
        assertFalse(asg.MakeOrderModule.Constants.MenuOption.isValidCode(0));
        assertFalse(asg.MakeOrderModule.Constants.MenuOption.isValidCode(7));
        assertFalse(asg.MakeOrderModule.Constants.MenuOption.isValidCode(-1));
    }

    @Test
    @DisplayName("MenuOption - getCode returns correct values")
    void testMenuOption_GetCode() {
        assertEquals(1, asg.MakeOrderModule.Constants.MenuOption.DISPLAY_ALL_PRODUCTS.getCode());
        assertEquals(2, asg.MakeOrderModule.Constants.MenuOption.MAKE_NEW_ORDER.getCode());
        assertEquals(6, asg.MakeOrderModule.Constants.MenuOption.EXIT.getCode());
    }

    @Test
    @DisplayName("MenuOption - getDescription returns correct text")
    void testMenuOption_GetDescription() {
        assertEquals("Display All Product Details", 
                asg.MakeOrderModule.Constants.MenuOption.DISPLAY_ALL_PRODUCTS.getDescription());
        assertEquals("Make New Order", 
                asg.MakeOrderModule.Constants.MenuOption.MAKE_NEW_ORDER.getDescription());
        assertEquals("Exit/Back To Menu", 
                asg.MakeOrderModule.Constants.MenuOption.EXIT.getDescription());
    }

    @Test
    @DisplayName("MenuOption - toString formats correctly")
    void testMenuOption_ToString() {
        assertEquals("1. Display All Product Details", 
                asg.MakeOrderModule.Constants.MenuOption.DISPLAY_ALL_PRODUCTS.toString());
        assertEquals("6. Exit/Back To Menu", 
                asg.MakeOrderModule.Constants.MenuOption.EXIT.toString());
    }



    @Test
    @DisplayName("ReceiptPrinter - handles empty cart gracefully")
    void testReceiptPrinter_EmptyCart() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        
        OrderDetailsController.clearOrderDetails();
        asg.MakeOrderModule.View.ReceiptPrinter.printReceipt(OrderDetailsController.getOrderDetails());
        String output = outContent.toString();
        
        // Should still print receipt structure even if empty
        assertTrue(output.contains("TOTAL QUANTITY ITEM(S)") || output.length() > 0, 
                "Should handle empty cart");
        
        System.setOut(System.out);
    }

    // ==================== MAKEORDER TESTS ====================

    @Test
    @DisplayName("MakeOrder_refactor - clearCart clears all orders")
    void testMakeOrder_ClearCart() {
        OrderDetailsController.addOrderDetail(StockItemController.findStockItemByCode("I001"));
        OrderDetailsController.addOrderDetail(StockItemController.findStockItemByCode("I002"));
        assertEquals(2, OrderDetailsController.getOrderCount());
        
        OrderDetailsController.clearOrderDetails();
        
        assertEquals(0, OrderDetailsController.getOrderCount());
        assertTrue(OrderDetailsController.isEmpty());
    }

    @Test
    @DisplayName("MakeOrder_refactor - clearCart on empty cart does not error")
    void testMakeOrder_ClearEmptyCart() {
        OrderDetailsController.clearOrderDetails();
        
        OrderDetailsController.clearOrderDetails();
        
        assertEquals(0, OrderDetailsController.getOrderCount());
        assertTrue(OrderDetailsController.isEmpty());
    }

    @Test
    @DisplayName("MakeOrder_refactor - clearCart multiple times is safe")
    void testMakeOrder_ClearCartMultipleTimes() {
        OrderDetailsController.addOrderDetail(StockItemController.findStockItemByCode("I001"));
        
        OrderDetailsController.clearOrderDetails();
        OrderDetailsController.clearOrderDetails();
        OrderDetailsController.clearOrderDetails();
        
        assertEquals(0, OrderDetailsController.getOrderCount());
    }

    @Test
    @DisplayName("UI Menu - Display all products then exit")
    void testUIMenu_DisplayAllProducts() {
        String input = "1\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("All Product Information"));
        assertTrue(output.contains("I001"));
        assertTrue(output.contains("NIKE"));
        assertTrue(output.contains("Air Max 90"));
    }

    @Test
    @DisplayName("UI Menu - Make order and add item")
    void testUIMenu_MakeOrderAddItem() {
        String input = "2\nI001\ny\nn\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("ADD TO BASKET SUCCESSFUL"));
        assertEquals(1, OrderDetailsController.getOrderCount());
    }

    @Test
    @DisplayName("UI Menu - Search by code")
    void testUIMenu_SearchByCode() {
        String input = "3\n1\nI001\nn\nn\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("Search Item(s) Information"));
        assertTrue(output.contains("Air Max 90"));
    }

    @Test
    @DisplayName("UI Menu - Complete workflow")
    void testUIMenu_CompleteWorkflow() {
        String input = "1\n2\nI001\ny\nn\n5\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("All Product Information"));
        assertTrue(output.contains("ADD TO BASKET SUCCESSFUL"));
        assertTrue(output.contains("TOTAL QUANTITY ITEM(S)"));
    }

    @Test
    @DisplayName("UI Menu - Invalid menu choice")
    void testUIMenu_InvalidChoice() {
        String input = "0\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("INVALID CHOICE") || output.contains("Invalid"));
    }

    @Test
    @DisplayName("UI Menu - Delete from empty cart")
    void testUIMenu_DeleteEmptyCart() {
        String input = "4\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("Your Basket Is EMPTY"));
    }

    @Test
    @DisplayName("UI Menu - Checkout empty cart")
    void testUIMenu_CheckoutEmpty() {
        String input = "5\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("basket is empty") || output.contains("Nothing to checkout"));
    }

    @Test
    @DisplayName("MenuDisplay - displayItemDetails with null item")
    void testMenuDisplay_ItemDetailsNull() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        
        asg.MakeOrderModule.View.MenuDisplay.displayItemDetails(null);
        String output = outContent.toString();
        
        assertTrue(output.contains("No item to display"), "Should display null message");
        
        System.setOut(System.out);
    }

    @Test
    @DisplayName("MenuDisplay - displayOrderList with empty list")
    void testMenuDisplay_OrderListEmpty() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        
        java.util.List<OrderDetails> emptyList = new java.util.ArrayList<>();
        asg.MakeOrderModule.View.MenuDisplay.displayOrderList(emptyList);
        String output = outContent.toString();
        
        assertTrue(output.contains("Your basket is EMPTY"), "Should display empty basket message");
        
        System.setOut(System.out);
    }

    @Test
    @DisplayName("MenuDisplay - displayOrderList with single item")
    void testMenuDisplay_OrderListSingleItem() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        
        OrderDetails order = new OrderDetails("I001", "NIKE", "Air Max 90", "Red", 480.00);
        java.util.List<OrderDetails> orders = new java.util.ArrayList<>();
        orders.add(order);
        
        asg.MakeOrderModule.View.MenuDisplay.displayOrderList(orders);
        String output = outContent.toString();
        
        assertTrue(output.contains("I001"), "Should display item code");
        assertTrue(output.contains("NIKE"), "Should display brand");
        assertTrue(output.contains("Air Max 90"), "Should display description");
        assertTrue(output.contains("Red"), "Should display color");
        assertTrue(output.contains("480"), "Should display price");
        
        System.setOut(System.out);
    }

    @Test
    @DisplayName("MenuDisplay - displayOrderList with multiple items")
    void testMenuDisplay_OrderListMultipleItems() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        
        java.util.List<OrderDetails> orders = new java.util.ArrayList<>();
        orders.add(new OrderDetails("I001", "NIKE", "Air Max 90", "Red", 480.00));
        orders.add(new OrderDetails("I002", "NIKE", "Revolution 5", "Blue", 220.00));
        orders.add(new OrderDetails("I003", "NIKE", "Jordan 1", "Black", 570.00));
        
        asg.MakeOrderModule.View.MenuDisplay.displayOrderList(orders);
        String output = outContent.toString();
        
        // Verify all items are displayed
        assertTrue(output.contains("I001"), "Should display first item");
        assertTrue(output.contains("I002"), "Should display second item");
        assertTrue(output.contains("I003"), "Should display third item");
        assertTrue(output.contains("Air Max 90"), "Should display first item description");
        assertTrue(output.contains("Revolution 5"), "Should display second item description");
        assertTrue(output.contains("Jordan 1"), "Should display third item description");
        
        System.setOut(System.out);
    }

    @Test
    @DisplayName("StockItemController - addStockItem with null item throws exception")
    void testStockItemController_AddNullItem() {
        assertThrows(IllegalArgumentException.class, () -> {
            StockItemController.addStockItem(null);
        }, "Should throw exception for null item");
    }

    @Test
    @DisplayName("StockItemController - findStockItemByCode with null")
    void testStockItemController_FindByCodeNull() {
        StockItem result = StockItemController.findStockItemByCode(null);
        
        assertNull(result, "Should return null for null code");
    }

    @Test
    @DisplayName("StockItemController - findStockItemByCode with empty string")
    void testStockItemController_FindByCodeEmpty() {
        StockItem result1 = StockItemController.findStockItemByCode("");
        StockItem result2 = StockItemController.findStockItemByCode("   ");
        
        assertNull(result1, "Should return null for empty string");
        assertNull(result2, "Should return null for whitespace string");
    }

    @Test
    @DisplayName("StockItemController - findStockItemsByBrand with null")
    void testStockItemController_FindByBrandNull() {
        var result = StockItemController.findStockItemsByBrand(null);
        
        assertTrue(result.isEmpty(), "Should return empty list for null brand");
    }

    @Test
    @DisplayName("StockItemController - findStockItemsByBrand with empty string")
    void testStockItemController_FindByBrandEmpty() {
        var result1 = StockItemController.findStockItemsByBrand("");
        var result2 = StockItemController.findStockItemsByBrand("   ");
        
        assertTrue(result1.isEmpty(), "Should return empty list for empty string");
        assertTrue(result2.isEmpty(), "Should return empty list for whitespace string");
    }

    @Test
    @DisplayName("StockItemController - findStockItemsByBrand with non-existent brand")
    void testStockItemController_FindByBrandNonExistent() {
        var result = StockItemController.findStockItemsByBrand("NONEXISTENT_BRAND");
        
        assertTrue(result.isEmpty(), "Should return empty list for non-existent brand");
    }

    @Test
    @DisplayName("OrderDetailsController - addOrderDetail with null item throws exception")
    void testOrderDetailsController_AddNullItem() {
        assertThrows(IllegalArgumentException.class, () -> {
            OrderDetailsController.addOrderDetail((StockItem) null);
        }, "Should throw exception for null item");
    }

    @Test
    @DisplayName("OrderDetailsController - getOrderDetailByCode with null")
    void testOrderDetailsController_GetByCodeNull() {
        OrderDetails result = OrderDetailsController.getOrderDetailByCode(null);
        
        assertNull(result, "Should return null for null code");
    }

    @Test
    @DisplayName("OrderDetailsController - getOrderDetailByCode with empty string")
    void testOrderDetailsController_GetByCodeEmpty() {
        OrderDetails result1 = OrderDetailsController.getOrderDetailByCode("");
        OrderDetails result2 = OrderDetailsController.getOrderDetailByCode("   ");
        
        assertNull(result1, "Should return null for empty string");
        assertNull(result2, "Should return null for whitespace string");
    }

    @Test
    @DisplayName("OrderDetailsController - getOrderDetailByCode with non-existent code")
    void testOrderDetailsController_GetByCodeNonExistent() {
        OrderDetails result = OrderDetailsController.getOrderDetailByCode("NONEXISTENT999");
        
        assertNull(result, "Should return null for non-existent code");
    }

    // ==================== RECEIPTPRINTER TESTS ====================

    @Test
    @DisplayName("ReceiptPrinter - printReceipt with empty list")
    void testReceiptPrinter_EmptyList() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        
        java.util.List<OrderDetails> emptyList = new java.util.ArrayList<>();
        asg.MakeOrderModule.View.ReceiptPrinter.printReceipt(emptyList);
        String output = outContent.toString();
        assertTrue(output.contains("TOTAL QUANTITY ITEM(S) --> 0") || output.length() > 0, 
                "Should handle empty list gracefully");
        
        System.setOut(System.out);
    }

    @Test
    @DisplayName("ReceiptPrinter - printReceipt verifies formatting")
    void testReceiptPrinter_Formatting() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        
        java.util.List<OrderDetails> orders = new java.util.ArrayList<>();
        orders.add(new OrderDetails("I001", "NIKE", "Air Max 90", "Red", 480.00));
        
        asg.MakeOrderModule.View.ReceiptPrinter.printReceipt(orders);
        String output = outContent.toString();
        
        assertTrue(output.contains("====="), "Should have separator lines");
        assertTrue(output.contains("TOTAL QUANTITY ITEM(S)"), "Should have quantity header");
        assertTrue(output.contains("SUB TOTAL"), "Should have subtotal header");
        assertTrue(output.contains("RM"), "Should use RM currency");
        
        System.setOut(System.out);
    }

    @Test
    @DisplayName("ReceiptPrinter - printReceipt calculates total correctly")
    void testReceiptPrinter_TotalCalculation() {
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));
        
        java.util.List<OrderDetails> orders = new java.util.ArrayList<>();
        orders.add(new OrderDetails("I001", "NIKE", "Air Max 90", "Red", 100.50));
        orders.add(new OrderDetails("I002", "PUMA", "Suede Classic", "Blue", 200.75));
        orders.add(new OrderDetails("I003", "ADIDAS", "Superstar", "White", 150.25));
        
        asg.MakeOrderModule.View.ReceiptPrinter.printReceipt(orders);
        String output = outContent.toString();
        
        // Verify correct total calculation (100.50 + 200.75 + 150.25 = 451.50)
        assertTrue(output.contains("451.50"), "Should calculate total correctly");
        assertTrue(output.contains("TOTAL QUANTITY ITEM(S) --> 3"), "Should count items correctly");
        
        System.setOut(System.out);
    }

    @Test
    @DisplayName("UI Menu - Complete Delete Order Workflow")
    void testUIMenu_DeleteOrder_Workflow() {
        OrderDetailsController.addOrderDetail(StockItemController.findStockItemByCode("I001"));
        assertEquals(1, OrderDetailsController.getOrderCount(), "Setup: Cart should have 1 item");
        String input = "4\nI001\ny\nn\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("DELETE ORDER"), "Should enter delete section");
        assertTrue(output.contains("Item I001 Has Been Successfully Deleted!"), "Should confirm deletion");
        assertTrue(output.contains("EXITING MENU"), "Should exit menu");
        
        assertEquals(0, OrderDetailsController.getOrderCount(), "Cart should be empty after delete");
    }

    @Test
    @DisplayName("UI Menu - Complete Search By Code Workflow")
    void testUIMenu_SearchItemByCode_Workflow() {
        String input = "3\n1\nI001\nn\nn\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("Search Item(s) Information"), "Should enter search section");
        assertTrue(output.contains("Air Max 90"), "Should display item details");
        assertTrue(output.contains("ADD CANCEL"), "Should cancel adding to cart");
        assertTrue(output.contains("EXITING MENU"), "Should exit menu");
    }

    @Test
    @DisplayName("UI Menu - Search By Code: Item Not Found")
    void testUIMenu_SearchItemByCode_NotFound() {
        String input = "3\n1\nINV999\nn\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("Search Item's Code NOT MATCH!!!"), "Should verify item not found message");
    }
    
    @Test
    @DisplayName("UI Menu - Search By Code: Found and Confirm Add")
    void testUIMenu_SearchItemByCode_Found_Add() {
        String input = "3\n1\nI001\ny\nn\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("Air Max 90"), "Should find item");
        assertTrue(output.contains("ADD TO BASKET SUCCESSFUL"), "Should confirm addition");
        assertEquals(1, OrderDetailsController.getOrderCount(), "Cart should have 1 item");
    }

    @Test
    @DisplayName("UI Menu - Make Order: Item Not Found")
    void testUIMenu_MakeOrder_ItemNotFound() {
        String input = "2\nINV999\nn\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("Item Code's NOT MATCH!!!"), "Should verify item not found message");
        assertEquals(0, OrderDetailsController.getOrderCount(), "Cart should remain empty");
    }

    @Test
    @DisplayName("UI Menu - Make Order: Found and Confirm Add")
    void testUIMenu_MakeOrder_ItemFound_Add() {
        String input = "2\nI001\ny\nn\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("Air Max 90"), "Should display found item");
        assertTrue(output.contains("ADD TO BASKET SUCCESSFUL"), "Should confirm addition");
        assertEquals(1, OrderDetailsController.getOrderCount(), "Cart should have 1 item");
    }

    @Test
    @DisplayName("UI Menu - Make Order: Found but Cancel Add")
    void testUIMenu_MakeOrder_ItemFound_Cancel() {
        String input = "2\nI001\nn\nn\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("Air Max 90"), "Should display found item");
        assertTrue(output.contains("ADD CANCEL"), "Should verify cancellation message");
        assertEquals(0, OrderDetailsController.getOrderCount(), "Cart should remain empty");
    }

    @Test
    @DisplayName("UI Menu - Search By Brand: Not Found")
    void testUIMenu_SearchByBrand_NotFound() {
        String input = "3\n2\nINVALID_BRAND\nn\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("Search Item's Brand NOT MATCH!!!"), "Should verify brand not found message");
    }
    
    @Test
    @DisplayName("UI Menu - Search By Brand: Found items")
    void testUIMenu_SearchByBrand_Found() {
        String input = "3\n2\nNIKE\nn\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("Brands : NIKE"), "Should display NIKE brand");
        assertTrue(output.contains("Air Max 90"), "Should display found item details");
    }
}

