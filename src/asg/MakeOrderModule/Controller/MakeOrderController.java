package asg.MakeOrderModule.Controller;

import java.util.List;
import java.util.Scanner;

import asg.MakeOrderModule.Constants.MenuOption;
import asg.MakeOrderModule.Model.StockItem;
import asg.MakeOrderModule.Service.MakeOrderService;
import asg.MakeOrderModule.View.InputValidator;
import asg.MakeOrderModule.View.MenuDisplay;
import asg.MakeOrderModule.View.ReceiptPrinter;

/**
 * Main Controller for the Make Order module.
 * Orchestrates the application flow using the Service layer.
 */
public class MakeOrderController {

    private Scanner scanner;
    private MakeOrderService service;

    public MakeOrderController() {
        this.scanner = new Scanner(System.in);
        this.service = new MakeOrderService();
        service.initialize();
    }

    /**
     * Static entry point for the module (Wrapper).
     */
    public static void main() {
        MakeOrderController controller = new MakeOrderController();
        controller.run();
    }

    /**
     * Starts the main application loop.
     */
    public void run() {
        MenuOption selectedOption = null;

        do {
            MenuDisplay.displayMainMenu();
            int choice = InputValidator.getMenuChoice(scanner, 1, 6);

            try {
                selectedOption = MenuOption.fromCode(choice);

                switch (selectedOption) {
                    case DISPLAY_ALL_PRODUCTS:
                        displayProductDetails();
                        break;
                    case MAKE_NEW_ORDER:
                        makeOrder();
                        break;
                    case SEARCH_PRODUCT:
                        searchProduct();
                        break;
                    case DELETE_ORDER:
                        deleteOrder();
                        break;
                    case CHECKOUT:
                        makePayment();
                        break;
                    case EXIT:
                        System.out.println("\nEXITING MENU...\n");
                        break;
                }

            } catch (IllegalArgumentException e) {
                System.out.println("INVALID CHOICE!!! Please Key In Again.\n");
            }
        } while (selectedOption != MenuOption.EXIT);
    }

    private void displayProductDetails() {
        MenuDisplay.displayAllProducts(service.getAllProducts());
    }

    private void makeOrder() {
        char continueAdding;

        do {
            MenuDisplay.displaySectionHeader("MAKE ORDER");

            String itemCode = InputValidator.getNonEmptyString(scanner, 
                    "\nEnter Product Item Code That Need To Order --> ");
            
            StockItem foundItem = service.findProductByCode(itemCode);
            
            if (foundItem == null) {
                System.out.printf("\nItem Code's NOT MATCH!!!\n");
            } else {
                MenuDisplay.displayItemDetails(foundItem);
                
                char confirmAdd = InputValidator.getYesNoResponse(scanner, 
                        "\nAre You Want To Add This Item To Basket ? (y=Yes/n=No) : ");

                if (confirmAdd == 'Y') {
                    service.addToCart(foundItem);
                    System.out.printf("\nADD TO BASKET SUCCESSFUL!!!\n");
                    System.out.printf("\nCurrent Sub-total = RM %.2f\n", 
                            service.calculateCartTotal());
                } else {
                    System.out.printf("\nADD CANCEL...\n");
                }
            }

            continueAdding = InputValidator.getYesNoResponse(scanner, 
                    "\nWant To Add Another Item(s) To Basket ? (y=Yes/n=No) : ");

        } while (continueAdding == 'Y');
    }

    private void searchProduct() {
        char searchAnother;

        do {
            MenuDisplay.displaySearchMenu();
            int choice = InputValidator.getMenuChoice(scanner, 1, 2);

            switch (choice) {
                case 1:
                    searchByItemCode();
                    break;
                case 2:
                    searchByBrand();
                    break;
            }

            searchAnother = InputValidator.getYesNoResponse(scanner, 
                    "\nWant Search Another Item(s) Information ? (y=Yes/n=No) : ");
        } while (searchAnother == 'Y');
    }

    private void searchByItemCode() {
        String itemCode = InputValidator.getNonEmptyString(scanner, "\nItem Code --> ");
        
        StockItem found = service.findProductByCode(itemCode);
        
        if (found == null) {
            System.out.printf("\nSearch Item's Code NOT MATCH!!!\n");
            return;
        }

        MenuDisplay.displayItemDetails(found);
        
        char confirmAdd = InputValidator.getYesNoResponse(scanner, 
                "\nAre You Want To Add This Item To Basket ? (y=Yes/n=No) : ");

        if (confirmAdd == 'Y') {
            service.addToCart(found);
            System.out.printf("\nADD TO BASKET SUCCESSFUL!!!\n");
            System.out.printf("\nCurrent Sub-total = RM %.2f\n", 
                    service.calculateCartTotal());
        } else {
            System.out.printf("\nADD CANCEL...\n");
        }
    }

    private void searchByBrand() {
        String brand = InputValidator.getNonEmptyString(scanner, 
                "\nEnter Brand (NIKE/PUMA/ADIDAS) --> ");
        
        List<StockItem> foundItems = service.findProductsByBrand(brand);
        
        if (foundItems.isEmpty()) {
            System.out.printf("\nSearch Item's Brand NOT MATCH!!! Please Type NIKE/PUMA/ADIDAS Only.\n");
            return;
        }

        for (StockItem item : foundItems) {
            MenuDisplay.displayItemDetails(item);
        }
    }

    private void deleteOrder() {
        char deleteAnother;
        
        do {
            MenuDisplay.displaySectionHeader("DELETE ORDER");

            if (service.isCartEmpty()) {
                System.out.println("\nYour Basket Is EMPTY. Nothing To Delete...");
                return;
            }

            MenuDisplay.displayOrderList(service.getCartItems());

            String codeToDelete = InputValidator.getNonEmptyString(scanner, 
                    "\nEnter The Item Code To Delete : ");

            if (service.findOrderDetail(codeToDelete) == null) {
                System.out.printf("\nNo Item Found With Code: %s\n", codeToDelete);
            } else {
                char confirm = InputValidator.getYesNoResponse(scanner, 
                        "Confirm Delete This Item? (y=Yes/n=No) : ");
                
                if (confirm == 'Y') {
                    service.removeOrderDetail(codeToDelete);
                    System.out.printf("\nItem %s Has Been Successfully Deleted!\n", codeToDelete);
                } else {
                    System.out.println("\nDELETION CANCELLED...");
                }
            }

            deleteAnother = InputValidator.getYesNoResponse(scanner, 
                    "\nDelete Another Item? (y=Yes/n=No) : ");

        } while (deleteAnother == 'Y');
    }

    private void makePayment() {
        if (service.isCartEmpty()) {
            System.out.println("\nYour basket is empty. Nothing to checkout.");
            return;
        }
        ReceiptPrinter.printReceipt(service.getCartItems());
    }
    

}
