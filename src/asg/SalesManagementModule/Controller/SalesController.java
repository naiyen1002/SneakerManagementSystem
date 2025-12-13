package asg.SalesManagementModule.Controller;

import asg.SalesManagementModule.Constants.SalesConstants;
import asg.SalesManagementModule.Model.SalesItem;
import asg.SalesManagementModule.Service.SalesService;
import asg.SalesManagementModule.Service.SearchStrategies;
import asg.SalesManagementModule.Service.SearchStrategy;
import asg.SalesManagementModule.View.SalesView;

import java.util.List;
import java.util.Optional;

/**
 * Controller for Sales Management Module.
 * 
 * Implements Facade Pattern - provides a simplified interface to the complex
 * subsystem.
 * Uses Dependency Injection for testability.
 * 
 * Facade Pattern provides:
 * - Simple interface for the main system to call
 * - Hides complexity of View, Service, and DAO interactions
 * - Single entry point for the sales module
 */
public class SalesController {

    private final SalesService service;
    private final SalesView view;

    // Search strategies (Strategy Pattern) - using inner classes
    private final SearchStrategy[] searchStrategies = {
            new SearchStrategies.BySalesId(),
            new SearchStrategies.ByMemberId(),
            new SearchStrategies.ByItemCode(),
            new SearchStrategies.ByBrand(),
            new SearchStrategies.ByDescription(),
            new SearchStrategies.ByColour(),
            new SearchStrategies.ByPrice(),
            new SearchStrategies.ByQuantity()
    };

    /**
     * Constructor with Dependency Injection.
     * Used for testing with mock Service and View.
     * 
     * @param service The SalesService instance
     * @param view    The SalesView instance
     */
    public SalesController(SalesService service, SalesView view) {
        this.service = service;
        this.view = view;
    }

    /**
     * Default constructor using default Service and View.
     * Used in production code.
     */
    public SalesController() {
        this(new SalesService(), new SalesView());
    }

    /**
     * Static entry point for the module (Wrapper).
     * Matches the MakeOrderController pattern for consistent module initialization.
     */
    public static void main() {
        SalesController controller = new SalesController();
        controller.run();
    }

    /**
     * Runs the sales management module.
     * This is the main entry point called by the main system.
     */
    public void run() {
        char choice;

        do {
            view.displayMainMenu();
            choice = view.getMenuChoice();

            switch (choice) {
                case '1':
                    handleDisplay();
                    break;
                case '2':
                    handleAdd();
                    break;
                case '3':
                    handleDelete();
                    break;
                case '4':
                    handleModify();
                    break;
                case '5':
                    handleSearch();
                    break;
                case '6':
                    handleReport();
                    break;
                case '0':
                    view.showInfo(SalesConstants.EXITING_MENU);
                    break;
                default:
                    view.showError(SalesConstants.ERROR_INVALID_CHOICE);
                    view.waitForEnter();
            }
        } while (choice != '0');
    }

    /**
     * Handles display all sales operation.
     */
    public void handleDisplay() {
        List<SalesItem> allSales = service.getAllSales();
        view.displaySalesTable(allSales);
        view.waitForEnter();
    }

    /**
     * Handles add new sales operation.
     */
    public void handleAdd() {
        int addedCount = 0;
        boolean continueAdding;

        do {
            // Get sales details from user
            String salesId = getValidSalesId();
            String memberId = view.getString(SalesConstants.ENTER_MEMBER_ID);
            String itemCode = view.getString(SalesConstants.ENTER_ITEM_CODE);
            String brand = view.getBrandChoice();
            String description = view.getLine(SalesConstants.ENTER_DESCRIPTION);
            String colour = view.getColourChoice();
            double price = view.getDouble(SalesConstants.ENTER_PRICE);
            int quantity = view.getInt(SalesConstants.ENTER_QUANTITY);

            // Create and display the item
            SalesItem newItem = new SalesItem(salesId, memberId, itemCode, brand,
                    description, colour, price, quantity);
            view.showInfo("\nNew Sales Information:");
            view.displaySalesDetails(newItem);

            // Confirm and save
            if (view.getConfirmation(SalesConstants.CONFIRM_ADD)) {
                if (service.addSales(newItem)) {
                    view.showSuccess(SalesConstants.SUCCESS_SALES_ADDED);
                    addedCount++;
                } else {
                    view.showError("Failed to add sales record. Please check the data.");
                }
            } else {
                view.showInfo(SalesConstants.ACTION_CANCELLED);
            }

            continueAdding = view.getConfirmation(SalesConstants.CONFIRM_CONTINUE_ADD);
        } while (continueAdding);

        view.showInfo(String.format(SalesConstants.INFO_RECORDS_ADDED, addedCount));
        view.waitForEnter();
    }

    /**
     * Handles delete sales operation.
     */
    public void handleDelete() {
        int deletedCount = 0;
        boolean continueDeleting;

        do {
            String salesId = view.getString(SalesConstants.ENTER_SALES_ID_TO_DELETE);
            Optional<SalesItem> item = service.findById(salesId);

            if (item.isPresent()) {
                view.showInfo(SalesConstants.INFO_ITEM_FOUND);
                view.displaySalesDetails(item.get());

                if (view.getConfirmation(SalesConstants.CONFIRM_DELETE)) {
                    if (service.deleteSales(salesId)) {
                        view.showSuccess(String.format(SalesConstants.SUCCESS_SALES_DELETED, salesId));
                        deletedCount++;
                    } else {
                        view.showError("Failed to delete sales record.");
                    }
                } else {
                    view.showInfo(SalesConstants.ACTION_CANCELLED);
                }
            } else {
                view.showError(String.format(SalesConstants.INFO_ITEM_NOT_FOUND, salesId));
            }

            continueDeleting = view.getConfirmation(SalesConstants.CONFIRM_CONTINUE_DELETE);
        } while (continueDeleting);

        view.showInfo(String.format(SalesConstants.INFO_RECORDS_DELETED, deletedCount));
        view.waitForEnter();
    }

    /**
     * Handles modify sales operation.
     */
    public void handleModify() {
        int modifiedCount = 0;
        boolean continueModifying;

        do {
            String salesId = view.getString(SalesConstants.ENTER_SALES_ID_TO_MODIFY);
            Optional<SalesItem> item = service.findById(salesId);

            if (item.isPresent()) {
                view.showInfo(SalesConstants.INFO_ITEM_FOUND);
                view.displaySalesDetails(item.get());

                // Get new values from user
                String newSalesId = view.getString(SalesConstants.ENTER_NEW_SALES_ID);
                String newMemberId = view.getString(SalesConstants.ENTER_NEW_MEMBER_ID);
                String newItemCode = view.getString(SalesConstants.ENTER_NEW_ITEM_CODE);
                String newBrand = view.getBrandChoice();
                String newDescription = view.getLine(SalesConstants.ENTER_DESCRIPTION);
                String newColour = view.getColourChoice();
                double newPrice = view.getDouble(SalesConstants.ENTER_PRICE);
                int newQuantity = view.getInt(SalesConstants.ENTER_QUANTITY);

                SalesItem updatedItem = new SalesItem(newSalesId, newMemberId, newItemCode,
                        newBrand, newDescription, newColour, newPrice, newQuantity);

                view.showInfo("\nUpdated Sales Information:");
                view.displaySalesDetails(updatedItem);

                if (view.getConfirmation(SalesConstants.CONFIRM_MODIFY)) {
                    if (service.updateSales(salesId, updatedItem)) {
                        view.showSuccess(String.format(SalesConstants.SUCCESS_SALES_MODIFIED, salesId));
                        modifiedCount++;
                    } else {
                        view.showError("Failed to modify sales record. Please check the data.");
                    }
                } else {
                    view.showInfo(SalesConstants.ACTION_CANCELLED);
                }
            } else {
                view.showError(String.format(SalesConstants.INFO_ITEM_NOT_FOUND, salesId));
            }

            continueModifying = view.getConfirmation(SalesConstants.CONFIRM_CONTINUE_MODIFY);
        } while (continueModifying);

        view.showInfo(String.format(SalesConstants.INFO_RECORDS_MODIFIED, modifiedCount));
        view.waitForEnter();
    }

    /**
     * Handles search sales operation using Strategy Pattern.
     */
    public void handleSearch() {
        boolean continueSearching;

        do {
            view.displaySearchMenu();
            char choice = view.getMenuChoice();

            if (choice >= '1' && choice <= '8') {
                int index = choice - '1';
                SearchStrategy strategy = searchStrategies[index];

                String searchValue = view.getString("Enter " + strategy.getFieldName() + " to search: ");
                List<SalesItem> results = service.search(strategy, searchValue);

                if (results.isEmpty()) {
                    view.showError(SalesConstants.INFO_NO_MATCHING_RECORDS);
                } else {
                    view.showInfo("Found " + results.size() + " matching record(s):");
                    for (int i = 0; i < results.size(); i++) {
                        view.showInfo("\nResult " + (i + 1) + ":");
                        view.displaySalesDetails(results.get(i));
                    }
                }
            } else if (choice == '0') {
                view.showInfo(SalesConstants.EXITING_SEARCH_MENU);
            } else {
                view.showError(SalesConstants.ERROR_INVALID_CHOICE);
            }

            continueSearching = view.getConfirmation(SalesConstants.CONFIRM_CONTINUE_SEARCH);
        } while (continueSearching);

        view.waitForEnter();
    }

    /**
     * Handles sales report generation.
     */
    public void handleReport() {
        List<SalesItem> allSales = service.getAllSales();
        SalesService.SalesReport report = service.generateReport();
        view.displayReport(allSales, report);
        view.waitForEnter();
    }

    // ==================== Helper Methods ====================

    /**
     * Gets a valid unique sales ID from user.
     * Validates length and uniqueness.
     * 
     * @return A valid unique sales ID
     */
    private String getValidSalesId() {
        String salesId;
        boolean isValid;

        do {
            salesId = view.getString(SalesConstants.ENTER_SALES_ID);
            isValid = true;

            if (salesId.length() != SalesConstants.ID_LENGTH) {
                view.showError(SalesConstants.ERROR_INVALID_LENGTH);
                isValid = false;
            } else if (!service.isUniqueSalesId(salesId)) {
                view.showError(SalesConstants.ERROR_SALES_ID_EXISTS);
                isValid = false;
            }
        } while (!isValid);

        return salesId;
    }
}