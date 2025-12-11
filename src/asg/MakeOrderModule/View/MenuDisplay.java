package asg.MakeOrderModule.View;

import java.util.List;
import asg.MakeOrderModule.Constants.MenuOption;
import asg.MakeOrderModule.Model.OrderDetails;
import asg.MakeOrderModule.Model.StockItem;

/**
 * Utility class for displaying menus and item information.
 * Handles all console output formatting for the MakeOrder module.
 */
public class MenuDisplay {
    
    /**
     * Displays the main menu for the Make Order module.
     * Dynamically generates menu options from MenuOption enum.
     */
    public static void displayMainMenu() {
        System.out.println("\n\n--------------------------------");
        System.out.println("         Make Order Menu          ");
        System.out.println("--------------------------------\n");
        
        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.toString());
        }
        System.out.println();
    }

    /**
     * Displays the search menu.
     */
    public static void displaySearchMenu() {
        System.out.println("\n--------------------------------");
        System.out.println("   Search Item(s) Information\t\t");
        System.out.println("--------------------------------\n");
        System.out.println("1. Item Code");
        System.out.println("2. Brands");
    }

    /**
     * Displays detailed information for a single stock item.
     * 
     * @param item The stock item to display
     */
    public static void displayItemDetails(StockItem item) {
        if (item == null) {
            System.out.println("\nNo item to display.");
            return;
        }
        
        System.out.println("\n===============================================");
        System.out.printf("Item Code : %s", item.getItemCode());
        System.out.printf("\nBrands : %s", item.getBrand());
        System.out.printf("\nItem Description : %s", item.getItemDescription());
        System.out.printf("\nColor : %s", item.getColour());
        System.out.printf("\nPrice/Unit : RM %.2f", item.getItemPrice());
        System.out.printf("\nStock Available : %d", item.getQuantityInStock());
        System.out.printf("\n===============================================\n");
    }

    /**
     * Displays all products in a formatted table.
     * 
     * @param items List of stock items to display
     */
    public static void displayAllProducts(List<StockItem> items) {
        System.out.printf("\n--------------------------------\n");
        System.out.printf("     All Product Information\n");
        System.out.printf("--------------------------------\n");
        System.out.printf("\n%-12s %-12s %-25s %-20s %-12s %-18s", 
                "Item Code", "Brands", "Item Description", "Color", "Price/Unit", "Stock Available");
        System.out.printf("\n%-12s %-12s %-25s %-20s %-12s %-18s\n", 
                "---------", "------", "--------------------", "-----------------", "----------", "---------------");
        
        for (StockItem item : items) {
            System.out.printf("%-12s %-12s %-25s %-20s RM %-12.2f %-18d\n", 
                    item.getItemCode(), 
                    item.getBrand(), 
                    item.getItemDescription(),
                    item.getColour(), 
                    item.getItemPrice(),
                    item.getQuantityInStock());
        }
    }

    /**
     * Displays all order items in the cart.
     * 
     * @param orders List of order details to display
     */
    public static void displayOrderList(List<OrderDetails> orders) {
        if (orders.isEmpty()) {
            System.out.println("\nYour basket is EMPTY.");
            return;
        }
        
        for (OrderDetails order : orders) {
            System.out.println("\n===============================================");
            System.out.printf("Item Code : %s", order.getOrderCode());
            System.out.printf("\nBrands : %s", order.getOrderBrand());
            System.out.printf("\nItem Description : %s", order.getOrderDescription());
            System.out.printf("\nColor : %s", order.getOrderColor());
            System.out.printf("\nPrice/Unit : RM %.2f", order.getOrderPrice());
            System.out.printf("\n===============================================\n");
        }
    }

    /**
     * Displays a section header.
     * 
     * @param title The title to display
     */
    public static void displaySectionHeader(String title) {
        System.out.println("\n--------------------------------");
        System.out.printf("        %s        \n", title);
        System.out.println("--------------------------------");
    }

}
