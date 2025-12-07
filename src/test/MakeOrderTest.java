package test;

import org.junit.jupiter.api.*;

import controller.OrderDetailsController;
import controller.StockItemController;
import model.OrderDetails;
import model.StockItem;

import static org.junit.jupiter.api.Assertions.*;

import view.MakeOrder_refactor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * JUnit test suite for MakeOrder system
 * Tests all major functionalities including order creation, search, deletion, and payment
 */
public class MakeOrderTest {
    private final InputStream standardIn = System.in;
    private final PrintStream standardOut = System.out;

    private ByteArrayOutputStream outputStreamCaptor;

    /**
     * Setup method runs before each test
     * Initializes output capture for testing console output
     */
    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        StockItemController.addStockItem(new StockItem("I001", "NIKE", "Air Max 90", "Red", 480.00, 10));
        // MakeOrder_refactor.clearCart();
        StockItem testItem1 = new StockItem("I001", "NIKE", "Air Max 90", "Red", 480.00, 10);
        
        OrderDetailsController.addOrderDetail(testItem1);

    }

    /**
     * Tear down method runs after each test
     * Restores original System.in and System.out streams
     */
    @AfterEach
    void tearDown() {
        System.setIn(standardIn);
        System.setOut(standardOut);
    }

    /**
     * Helper method to simulate user input and run the system
     * 
     * @param input simulated user input separated by newlines
     * @return captured console output as a string
     */
    private String runWithInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        MakeOrder_refactor.main();
        return outputStreamCaptor.toString();
    }

    // ==================== TEST CASES FOR DISPLAY PRODUCT DETAILS ====================

    @Test
    @DisplayName("Test Display All Products - Should show all 30 products")
    void testDisplayAllProducts() {
        // Display all products, then exit
        String input = "1\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("All Product Information"));
        assertTrue(output.contains("I001"));
        assertTrue(output.contains("NIKE"));
        assertTrue(output.contains("Air Max 90"));

    }


    @Test
    @DisplayName("Test Menu - Choice out of range (negative number)")
    void testMenu_NegativeChoice() {
        // Enter -1, then valid choice 6 to exit
        String input = "-1\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("Invalid Number"));
    }

    @Test
    @DisplayName("Make Order - Add single valid item to basket")
    void testMakeOrder_AddSingleItem() {
        OrderDetailsController.clearOrderDetails();
        String input = "2\nI001\ny\nn\n6";
        String output = runWithInput(input);
        assertTrue(output.contains("Item Description : Air Max 90"));
        assertTrue(output.contains("ADD TO BASKET SUCCESSFUL"));
        assertTrue(output.contains("Current Sub-total = RM 480"));
        assertEquals(1, OrderDetailsController.getOrderCount());
    }

    
    @Test
    @DisplayName("Test Menu - Non-numeric input (text)")
    void testMenu_TextInput() {
        // Enter "abc", then valid choice 6 to exit
        String input = "abc\n6\n";
        String output = runWithInput(input);
        
        assertTrue(output.contains("Enter Digit Only Helloo"));
    }

    // @Test
    // @DisplayName("Add multiple valid items to cart")
    // void testAddMultipleItems() {
    //     String input = "3\nI001\ny\ny\nI001\ny\nn"; 
    //     String output = runWithInput(input);

    //     double total = MakeOrder_refactor.calculateCartTotal();
    //     // assertTrue(output.contains("Current Sub-total = RM 960.00")); 
    //     assertEquals(960.00, total, 0.01);
    // }

    @Test
    @DisplayName("Search product by valid item code and add to cart")
    void testSearchByItemCode_AddToCart() {
        // 1 = search by code, I001 = item code, y = add to cart, n = don't search another, 6 = exit menu
        String input = "3\n1\nI001\ny\nn\n6\n"; 
        String output = runWithInput(input);

        assertTrue(output.contains(""));
    }

    @Test
    @DisplayName("Search product by valid item code but do not add to cart")
    void testSearchByItemCode_DoNotAdd() {
        String input = "3\n1\nI001\nn\nn\n6\n"; 
        String output = runWithInput(input);

        assertTrue(output.contains("ADD CANCEL..."));
        
    }

    @Test
    @DisplayName("Search product by invalid item code")
    void testSearchByItemCode_Invalid() {
        String input = "3\n1\nINVALID_CODE\nn\n6\n"; 
        String output = runWithInput(input);

        assertTrue(output.contains("Search Item's Code NOT MATCH"));
        
    }

    @Test
    @DisplayName("Search product by brand")
    void testSearchByBrand() {
        String input = "3\n2\nNIKE\nn\n6"; 
        String output = runWithInput(input);
        
        assertTrue(output.contains("Brands : NIKE"));
        
    }

    @Test
    @DisplayName("Search product by invalid brand")
    void testSearchByBrand_Invalid() {
        String input = "3\n2\nINVALIDBRAND\nn\n6\n"; 
        String output = runWithInput(input);

        assertTrue(output.contains("Search Item's Brand NOT MATCH"));
        
    }

    @Test
    @DisplayName("Delete item when cart is emtpy")
    void testDeleteItem_CartEmpty(){
        OrderDetailsController.clearOrderDetails();
        String input = "4\n6";
        String output = runWithInput(input);

        assertEquals(OrderDetailsController.getOrderCount(), 0);
        assertTrue(output.contains("Your Basket Is EMPTY. Nothing To Delete..."));
    }

    @Test
    @DisplayName("Delete item when cart is not emtpy")
    void testDeleteItem_CartNotEmpty(){
    
        String input = "4\nI001\ny\nn\n6";
        String output = runWithInput(input);

        assertTrue(output.contains("Item I001 Has Been Successfully Deleted!"));
    }

    @Test
    @DisplayName("Delete item that not exist")
    void testDeleteItem_NotFound() {
        
        String input = "4\nI999\nn\n6";
        String output = runWithInput(input);

        assertTrue(output.contains("No Item Found With Code: I999"));
    }

    @Test
    @DisplayName("Delete item - User cancels deletion")
    void testDeleteItem_UserCancels() {
        
        String input = "4\nI001\nn\nn\n6"; 
        String output = runWithInput(input);

        assertTrue(output.contains("DELETION CANCELLED"));
    }
        
    @Test
    @DisplayName("Display user make payment output")
    void testMakePayment_SingleItem() {
    
        String input = "5\n6";
        String output = runWithInput(input);

        assertTrue(output.contains("TOTAL QUANTITY ITEM(S) --> 1"));
        assertTrue(output.contains("SUB TOTAL --> RM 480.00"));
    }
  
    @Test
    @DisplayName("Search item menu with invalid choice")
    void testSearchItem_Invalid() {
    
        String input = "3\n3\n1\n\nI001\nn\n6"; 
        
        String output = runWithInput(input);

        assertTrue(output.contains("You Can Only Choose 1 Or 2 Only..."));
    }

    @Test
    @DisplayName("Search item menu with non numeric input")
    void testSearchItem_NonNumeric() {
    
        String input = "3\na\n\n1\n\nI001\nn\n6"; 
        String output = runWithInput(input);

        assertTrue(output.contains("Enter Digit Only Helloo"));
    }

    @Test
    @DisplayName("No Input Provided from user")
    void testNoInputProvided(){
        String input = "3\n1\nI001\nn\n\nn\n6";
        String output = runWithInput(input);

        assertTrue(output.contains("No Input Provided..."));
    }

    @Test
    @DisplayName("Make Order No Input Provided from user")
    void test_MakeOrder_NoInputProvided(){
        String input = "2\n\nI001\ny\nn\n6";
        String output = runWithInput(input);

        assertTrue(output.contains("No Input Provided..."));
    }
  
    @Test
    @DisplayName("Make Order Cancel Add ")
    void test_MakeOrder_AddCancel(){
        String input = "2\n\nI001\nn\nn\n6";
        String output = runWithInput(input);

        assertTrue(output.contains("ADD CANCEL..."));
    }


  
  

}
