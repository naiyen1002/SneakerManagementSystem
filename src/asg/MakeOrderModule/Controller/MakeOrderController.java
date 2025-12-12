package asg.MakeOrderModule.Controller;

import java.util.List;
import java.util.Scanner;

import asg.MakeOrderModule.Constants.MenuOption;
import asg.MakeOrderModule.Model.OrderDetails;
import asg.MakeOrderModule.Model.StockItem;
import asg.MakeOrderModule.Service.MakeOrderService;
import asg.MakeOrderModule.Strategy.*;
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
    private ValidationContext<Integer> menuValidator = new ValidationContext<>();
    private ValidationContext<String> stringValidator = new ValidationContext<>();
    private ValidationContext<Character> yesNoValidator = new ValidationContext<>();
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

            stringValidator.setStrategy(new NonEmptyStringValidator());
            String itemCode = stringValidator.execute(scanner,
                    "\nEnter Product Item Code That Need To Order --> ");

            StockItem foundItem = service.findProductByCode(itemCode);

            if (foundItem == null) {
                System.out.println("\nItem Code's NOT MATCH!!!");
            } else {
                MenuDisplay.displayItemDetails(foundItem);

                yesNoValidator.setStrategy(new YesNoValidator());
                char confirmAdd = yesNoValidator.execute(scanner,
                        "\nAre You Want To Add This Item To Basket ? (y=Yes/n=No) : ");

                if (confirmAdd == 'Y') {
                    service.addToCart(foundItem);
                    System.out.printf("\nADD TO BASKET SUCCESSFUL!!!\n");
                    System.out.printf("\nCurrent Sub-total = RM %.2f\n", service.calculateCartTotal());
                } else {
                    System.out.println("\nADD CANCEL...");
                }
            }

            yesNoValidator.setStrategy(new YesNoValidator());
            continueAdding = yesNoValidator.execute(scanner,
                    "\nWant To Add Another Item(s) To Basket ? (y=Yes/n=No) : ");

        } while (continueAdding == 'Y');
    }


    private void searchProduct() {
        char searchAnother;

        do {
            MenuDisplay.displaySearchMenu();

            menuValidator.setStrategy(new MenuChoiceValidator(1, 2));
            int choice = menuValidator.execute(scanner, "Choose From 1 to 2 : ");

            ProductSearchContext context = new ProductSearchContext();
            String keyword;

            if (choice == 1) {
                context.setStrategy(new SearchByCode());
                stringValidator.setStrategy(new NonEmptyStringValidator());
                keyword = stringValidator.execute(scanner, "\nItem Code --> ");

            } else {
                context.setStrategy(new SearchByBrand());
                stringValidator.setStrategy(new NonEmptyStringValidator());
                keyword = stringValidator.execute(scanner, "\nEnter Brand (NIKE/PUMA/ADIDAS) --> ");
            }

            List<StockItem> results = context.executeSearch(service.getAllProducts(), keyword);

            if (results.isEmpty()) {
                System.out.println(choice == 1
                        ? "\nSearch Item's Code NOT MATCH!!!"
                        : "\nSearch Item's Brand NOT MATCH!!! Please Type NIKE/PUMA/ADIDAS Only.");
            } else {
                results.forEach(MenuDisplay::displayItemDetails);

                if (choice == 1) {
                    yesNoValidator.setStrategy(new YesNoValidator());
                    char confirmAdd = yesNoValidator.execute(scanner,
                            "\nAre You Want To Add This Item To Basket ? (y=Yes/n=No) : ");

                    if (confirmAdd == 'Y') {
                        service.addToCart(results.get(0));
                        System.out.printf("\nADD TO BASKET SUCCESSFUL!!!\n");
                        System.out.printf("\nCurrent Sub-total = RM %.2f\n", service.calculateCartTotal());
                    }
                }
            }

            yesNoValidator.setStrategy(new YesNoValidator());
            searchAnother = yesNoValidator.execute(scanner,
                    "\nWant Search Another Item(s) Information ? (y=Yes/n=No) : ");

        } while (searchAnother == 'Y');
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

            stringValidator.setStrategy(new NonEmptyStringValidator());
            String codeToDelete = stringValidator.execute(scanner,
                    "\nEnter The Item Code To Delete : ");

            OrderDetails toDelete = service.findOrderDetail(codeToDelete);

            if (toDelete == null) {
                System.out.printf("\nNo Item Found With Code: %s\n", codeToDelete);
            } else {
                yesNoValidator.setStrategy(new YesNoValidator());
                char confirm = yesNoValidator.execute(scanner,
                        "Confirm Delete This Item? (y=Yes/n=No) : ");

                if (confirm == 'Y') {
                    service.removeOrderDetail(codeToDelete);
                    System.out.printf("\nItem %s Has Been Successfully Deleted!\n", codeToDelete);
                }
            }

            yesNoValidator.setStrategy(new YesNoValidator());
            deleteAnother = yesNoValidator.execute(scanner,
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
