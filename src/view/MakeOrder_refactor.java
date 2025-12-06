package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.OrderDetailsController;
import controller.StockItemController;
import model.OrderDetails;
import model.StockItem;

public class MakeOrder_refactor {

    private static ArrayList<OrderDetails> orderItems = new ArrayList<>();
    private static ArrayList<StockItem> stockItems = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main() {
        // StockItemController stock = new StockItemController();
        // StockItemController.initializeStock();
        stockItems = StockItemController.getStockItems();
        orderItems = OrderDetailsController.getOrderDetails();
        int choice = 0;

        do {
            System.out.println("\n\n--------------------------------");
            System.out.println("         Make Order Menu          ");
            System.out.println("--------------------------------\n");
            System.out.println("1. Display All Product Details");
            System.out.println("2. Make New Order");
            System.out.println("3. Search Product Details");
            System.out.println("4. Delete Order");
            System.out.println("5. Check Out/Make Payment");
            System.out.println("6. Exit/Back To Menu\n");

           choice = getMenuChoice();

            switch (choice) {
                case 1:
                    displayProductDetails();
                    break;
                case 2:
                    makeOrder();
                    break;
                case 3:
                    searchProduct();
                    break;
                case 4:
                    deleteOrder();
                    break;
                case 5:
                    makePayment();
                    break;
                case 6:
                    System.out.println("\nEXITING MENU...\n");
                    break;
                default:
                    System.out.println("INVALID CHOICE!!! Please Key In Again.\n");
            }
        } while (choice != 6);
    }


    public static void clearCart() {
        orderItems.clear();
    }


    //-----------------------------menu-----------------------------------------------------------
    private static int getMenuChoice() {
        int choice = -1;
        boolean invalidChoice = false;

        while (!invalidChoice) {
            System.out.print("Choose From 1,2,3,4,5,6 : ");
            if (!scanner.hasNextLine()) {   
                    break;
                }

            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);

                if (choice >= 1 && choice <= 6) {
                    invalidChoice = true;
                } else {
                   System.out.println("Invalid Number!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter Digit Only Helloo");
            }
        }

        return choice;
    }


    //-----------------------------get confirmation for yes/no from user -----------------------------
    private static char getYesNoResponse(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine();
            while (input.isEmpty()) {
                System.out.println("\nNo Input Provided...");
                System.out.print(prompt);
                input = scanner.nextLine();
            }
            char response = Character.toUpperCase(input.charAt(0));
            if (response == 'Y' || response == 'N') {
                return response;
            }
            System.out.println("\nINVALID RESPONSE!!! Please Enter y=Yes or n=No");
        } while (true);
    }

    private static String getNonEmptyString(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("\nNo Input Provided...");
            }
        } while (input.isEmpty());
        return input;
    }

    //-------------------------------------helper method for display item details--------------------------------
    private static void displayItemDetails(StockItem item) {
        System.out.println("\n===============================================");
        System.out.printf("Item Code : %s", item.getItemCode());
        System.out.printf("\nBrands : %s", item.getBrand());
        System.out.printf("\nItem Description : %s", item.getItemDescription());
        System.out.printf("\nColor : %s", item.getColour());
        System.out.printf("\nPrice/Unit : RM %.2f", item.getItemPrice());
        System.out.printf("\nStock Available : %d", item.getQuantityInStock());
        System.out.printf("\n===============================================\n");
    }

    public static double calculateCartTotal() {
        double total = 0;
        for (OrderDetails order : orderItems) {
            total += order.getOrderPrice();
        }
        return total;
    }

    //-------------------------------------stock item operation ---------------------------------

    private static StockItem findStockItemByCode(String itemCode) {
        for (StockItem item : stockItems) {
            if (item.getItemCode().equalsIgnoreCase(itemCode)) {
                return item;
            }
        }
        return null;
    }

    private static List<StockItem> findStockItemsByBrand(String brand) {
        List<StockItem> results = new ArrayList<>();
        for (StockItem item : stockItems) {
            if (item.getBrand().equalsIgnoreCase(brand)) {
                results.add(item);
            }
        }
        return results;
    }

    private static void searchByItemCode() {
        System.out.printf("\nItem Code --> ");
        String searchCode = scanner.nextLine();

        if (searchCode.isEmpty()) {
            System.out.printf("\nType Something If You Want to Search...\n");
            return;
        }

        StockItem found = findStockItemByCode(searchCode);
        
        if (found == null) {
            System.out.printf("\nSearch Item's Code NOT MATCH!!!\n");
            return;
        }

        displayItemDetails(found);
        
        char confirmAdd = getYesNoResponse("\nAre You Want To Add This Item To Basket ? (y=Yes/n=No) : ");

        if (confirmAdd == 'Y') {
            addItemToCart(found);
            System.out.printf("\nADD TO BASKET SUCCESSFUL!!!\n");
            System.out.printf("\nCurrent Sub-total = RM %.2f\n", calculateCartTotal());
        } else {
            System.out.printf("\nADD CANCEL...\n");
        }
    }

    // Extract search by brand logic
    private static void searchByBrand() {
        System.out.printf("\nEnter Brand (NIKE/PUMA/ADIDAS) --> ");
        String searchBrand = scanner.nextLine();

        if (searchBrand.isEmpty()) {
            System.out.printf("\nType Something If You Want to Search...\n");
            return;
        }

        List<StockItem> foundItems = findStockItemsByBrand(searchBrand);
        
        if (foundItems.isEmpty()) {
            System.out.printf("\nSearch Item's Brand NOT MATCH!!! Please Type NIKE/PUMA/ADIDAS Only.\n");
            return;
        }

        // Display all items with matching brand
        for (StockItem item : foundItems) {
            displayItemDetails(item);
        }
    }

    


    public static void addItemToCart(StockItem item) {
        OrderDetails order = new OrderDetails(
            item.getItemCode(),
            item.getBrand(),
            item.getItemDescription(),
            item.getColour(),
            item.getItemPrice()
        );
        orderItems.add(order);
    }

    // ALL PRODUCT DETAILS
    

    private static void displayProductDetails() {
        System.out.printf("\n--------------------------------\n");
        System.out.printf("     All Product Information\n");
        System.out.printf("--------------------------------\n");
        System.out.printf("\n%-12s %-12s %-25s %-20s %-12s %-18s", "Item Code", "Brands", "Item Description", "Color",
                "Price/Unit", "Stock Available");
        System.out.printf("\n%-12s %-12s %-25s %-20s %-12s %-18s\n", "---------", "------", "--------------------",
                "-----------------", "----------", "---------------");
        for (StockItem item : stockItems) {
            String itemCode = item.getItemCode();
            String itemBrand = item.getBrand();
            String itemDescription = item.getItemDescription();
            String itemColor = item.getColour();
            double itemPrice = item.getItemPrice();
            int quantityInStock = item.getQuantityInStock();

            System.out.printf("%-12s %-12s %-25s %-20s RM %-12.2f %-18d\n", itemCode, itemBrand, itemDescription,
                    itemColor, itemPrice,
                    quantityInStock);
        }
    }

 
    private static void makeOrder() {
        double currentTotal = 0;
        char continueAddingToCart = 0;

        do {
            System.out.println("\n--------------------------------");
            System.out.println("        MAKE ORDER        ");
            System.out.println("--------------------------------");

            String orderCodeID = getNonEmptyString("\nEnter Product Item Code That Need To Order --> ");
            StockItem foundItem = findStockItemByCode(orderCodeID);
            
            if (foundItem == null) {
                System.out.printf("\nItem Code's NOT MATCH!!!\n");
            } else {
                displayItemDetails(foundItem);
                
                char confirmAdd = getYesNoResponse("\nAre You Want To Add This Item To Basket ? (y=Yes/n=No) : ");

                if (confirmAdd == 'Y') {
                    addItemToCart(foundItem);
                    currentTotal = calculateCartTotal();
                    System.out.printf("\nADD TO BASKET SUCCESSFUL!!!\n");
                    System.out.printf("\nCurrent Sub-total = RM %.2f\n", currentTotal);
                } else {
                    System.out.printf("\nADD CANCEL...\n");
                }
            }

            continueAddingToCart = getYesNoResponse("\nWant To Add Another Item(s) To Basket ? (y=Yes/n=No) : ");

        } while (continueAddingToCart == 'Y');
    }


    private static void searchProduct() {

        boolean invalidSearchChoice;
        int choice = 0;
        char conSearchAnotherOrder = 'n';

        // boolean match = false;
        // String searchCode;
        // String addToBasket;
        // char confirmAdd = 'n';
        // double currentTotal = 0;
        // String searchAnotherOrder;

        // int i;
        // String searchBrand;

        do {
            System.out.println("\n--------------------------------");
            System.out.println("   Search Item(s) Information\t\t");
            System.out.println("--------------------------------\n");
            System.out.println("1. Item Code");
            System.out.println("2. Brands");

            do {
                invalidSearchChoice = false;
                try {
                    if (!invalidSearchChoice) {
                        do {
                            System.out.printf("\nEnter 1/2 To Search Item(s) Informations : ");
                            choice = scanner.nextInt();
                            if (choice < 0 || choice > 2) {
                                System.out.println("\nYou Can Only Choose 1 Or 2 Only...\n");
                            }
                        } while (choice < 0 || choice > 2);
                    }
                } catch (Exception exc) {
                    System.out.println("\nEnter Digit Only Helloo...");
                    invalidSearchChoice = true;
                }
                scanner.nextLine();
            } while (invalidSearchChoice);

            switch (choice) {
                case 1:
                    searchByItemCode();
                    break;

                case 2:
                   searchByBrand();
                    break;

            }

            conSearchAnotherOrder = getYesNoResponse("\nWant Search Another Item(s) Information ? (y=Yes/n=No) : ");
        } while (Character.toUpperCase(conSearchAnotherOrder) == 'Y');
    }

    private static void deleteOrder() {
        char deleteAnother;
        do {
            System.out.println("\n--------------------------------");
            System.out.println("          DELETE ORDER          ");
            System.out.println("--------------------------------");

            if (orderItems.isEmpty()) {
                System.out.println("\nYour Basket Is EMPTY. Nothing To Delete...");
                return;
            }

            // Display all orders in cart
            for (OrderDetails order : orderItems) {
                System.out.println("\n===============================================");
                System.out.printf("Item Code : %s", order.getOrderCode());
                System.out.printf("\nBrands : %s", order.getOrderBrand());
                System.out.printf("\nItem Description : %s", order.getOrderDescription());
                System.out.printf("\nColor : %s", order.getOrderColor());
                System.out.printf("\nPrice/Unit : RM %.2f", order.getOrderPrice());
                System.out.printf("\n===============================================\n");
            }

            String codeToDelete = getNonEmptyString("\nEnter The Item Code To Delete : ");

            // Find matching order index
            int indexToRemove = -1;
            for (int i = 0; i < orderItems.size(); i++) {
                if (orderItems.get(i).getOrderCode().equalsIgnoreCase(codeToDelete)) {
                    indexToRemove = i;
                    break;
                }
            }

            if (indexToRemove == -1) {
                System.out.printf("\nNo Item Found With Code: %s\n", codeToDelete);
            } else {
                char confirm = getYesNoResponse("Confirm Delete This Item? (y=Yes/n=No) : ");
                if (confirm == 'Y') {
                    orderItems.remove(indexToRemove);
                    System.out.printf("\nItem %s Has Been Successfully Deleted!\n", codeToDelete);
                } else {
                    System.out.println("\nDELETION CANCELLED...");
                }
            }

            deleteAnother = getYesNoResponse("\nDelete Another Item? (y=Yes/n=No) : ");

        } while (deleteAnother == 'Y');
    }


    private static void makePayment() {

        double subTotal = 0;
        int qtyOrder = 0;

        System.out.printf("\n=========================$ $ $=========================");
        System.out.printf("\n                        RECEIPT              ");
        System.out.printf("\n=========================$ $ $=========================\n");
        System.out.printf("\n%-8s %-23s %-20s %-12s", "Brands", "Item Description", "Color", "Price/Unit");
        System.out.printf("\n%-8s %-23s %-20s %-12s\n", "------", "--------------------", "-----------------",
                "----------");
        for (OrderDetails order : orderItems) {
            System.out.printf("%-8s %-23s %-20s RM %-12.2f\n", order.getOrderBrand(), order.getOrderDescription(),
                    order.getOrderColor(), order.getOrderPrice());
            subTotal += order.getOrderPrice();
            qtyOrder++;
        }
        System.out.printf("\n\nTOTAL QUANTITY ITEM(S) --> %d", qtyOrder);
        System.out.printf("\nSUB TOTAL --> RM %.2f", subTotal);

    }
}
