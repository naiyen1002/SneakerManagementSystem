package asg.SalesManagementModule.View;

import asg.SalesManagementModule.Constants.SalesConstants;
import asg.SalesManagementModule.Constants.SalesMenuOption;
import asg.SalesManagementModule.Model.SalesItem;
import asg.SalesManagementModule.Service.SalesService;

import java.util.List;
import java.util.Scanner;

/**
 * View layer for Sales Management Module.
 * Handles all console output and input operations.
 * Follows Single Responsibility Principle - only handles UI.
 */
public class SalesView {

    private final Scanner scanner;

    /**
     * Constructor with Scanner injection for testing.
     * 
     * @param scanner The scanner to use for input
     */
    public SalesView(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Default constructor using System.in.
     */
    public SalesView() {
        this(new Scanner(System.in));
    }

    // ==================== Menu Display ====================

    /**
     * Displays the main sales management menu.
     */
    public void displayMainMenu() {
        System.out.println(SalesConstants.MENU_HEADER);
        System.out.println(SalesConstants.MENU_TITLE);
        System.out.println(SalesConstants.MENU_HEADER);

        for (SalesMenuOption option : SalesMenuOption.values()) {
            System.out.println("\t\t\t\t\t      " + option);
        }

        System.out.println(SalesConstants.MENU_FOOTER);
        System.out.print("\t\t\t\t\t  " + SalesConstants.ENTER_CHOICE);
    }

    /**
     * Displays the search menu.
     */
    public void displaySearchMenu() {
        System.out.println(SalesConstants.SEARCH_MENU_HEADER);
        System.out.println(SalesConstants.SEARCH_MENU_TITLE);
        System.out.println(SalesConstants.SEARCH_MENU_HEADER);
        System.out.println("\t\t\t\t\t      1 -> Search by SALES ID");
        System.out.println("\t\t\t\t\t      2 -> Search by MEMBER ID");
        System.out.println("\t\t\t\t\t      3 -> Search by ITEM CODE");
        System.out.println("\t\t\t\t\t      4 -> Search by BRAND");
        System.out.println("\t\t\t\t\t      5 -> Search by ITEM DESCRIPTION");
        System.out.println("\t\t\t\t\t      6 -> Search by COLOUR");
        System.out.println("\t\t\t\t\t      7 -> Search by ITEM PRICE");
        System.out.println("\t\t\t\t\t      8 -> Search by QUANTITY SALES");
        System.out.println("\t\t\t\t\t      0 -> Back");
        System.out.println(SalesConstants.SEARCH_MENU_HEADER);
        System.out.print("\t\t\t\t\t  " + SalesConstants.ENTER_CHOICE);
    }

    // ==================== Table Display ====================

    /**
     * Displays the sales table header.
     */
    public void displayTableHeader() {
        System.out.println(SalesConstants.TABLE_HEADER_SEPARATOR);
        System.out.println(SalesConstants.TABLE_HEADER);
        System.out.println(SalesConstants.TABLE_HEADER_SEPARATOR);
    }

    /**
     * Displays a single sales item as a table row.
     * 
     * @param item The sales item to display
     */
    public void displayTableRow(SalesItem item) {
        System.out.printf(SalesConstants.TABLE_ROW_FORMAT + "%n",
                item.getSalesId(),
                item.getMemberId(),
                item.getItemCode(),
                item.getBrand(),
                item.getItemDescription(),
                item.getColour(),
                item.getItemPrice(),
                item.getQuantitySales());
        System.out.println(SalesConstants.TABLE_ROW_SEPARATOR);
    }

    /**
     * Displays all sales items in a table.
     * 
     * @param items The list of sales items to display
     */
    public void displaySalesTable(List<SalesItem> items) {
        displayTableHeader();

        for (SalesItem item : items) {
            displayTableRow(item);
        }

        System.out.println("\n" + String.format(SalesConstants.INFO_RECORDS_DISPLAYED, items.size()));
    }

    // ==================== Item Details Display ====================

    /**
     * Displays a single sales item's details.
     * 
     * @param item The sales item to display
     */
    public void displaySalesDetails(SalesItem item) {
        System.out.println("\n" + SalesConstants.INFO_ITEM_FOUND);
        System.out.println(SalesConstants.LABEL_SALES_ID + item.getSalesId());
        System.out.println(SalesConstants.LABEL_MEMBER_ID + item.getMemberId());
        System.out.println(SalesConstants.LABEL_ITEM_CODE + item.getItemCode());
        System.out.println(SalesConstants.LABEL_BRAND + item.getBrand());
        System.out.println(SalesConstants.LABEL_DESCRIPTION + item.getItemDescription());
        System.out.println(SalesConstants.LABEL_COLOUR + item.getColour());
        System.out.println(SalesConstants.LABEL_PRICE + String.format("%.2f", item.getItemPrice()));
        System.out.println(SalesConstants.LABEL_QUANTITY + item.getQuantitySales());
    }

    // ==================== Report Display ====================

    /**
     * Displays the sales report.
     * 
     * @param items  The list of sales items
     * @param report The report data
     */
    public void displayReport(List<SalesItem> items, SalesService.SalesReport report) {
        displayTableHeader();

        for (SalesItem item : items) {
            displayTableRow(item);
        }

        System.out.println("\n" + SalesConstants.REPORT_LIST_SALES_ITEM + report.getTotalItems());
        System.out.println(SalesConstants.REPORT_TOTAL_QUANTITY + report.getTotalQuantity());
        System.out.println(SalesConstants.REPORT_SUBTOTAL + String.format("%.2f", report.getTotalAmount()));
    }

    // ==================== Input Methods ====================

    /**
     * Gets a menu choice from the user.
     * 
     * @return The character entered
     */
    public char getMenuChoice() {
        return scanner.next().charAt(0);
    }

    /**
     * Gets a string input from the user.
     * 
     * @param prompt The prompt to display
     * @return The input string
     */
    public String getString(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    /**
     * Gets a line of string input from the user.
     * 
     * @param prompt The prompt to display
     * @return The input string
     */
    public String getLine(String prompt) {
        System.out.print(prompt);
        scanner.nextLine(); // Consume newline
        return scanner.nextLine();
    }

    /**
     * Gets a double input from the user.
     * 
     * @param prompt The prompt to display
     * @return The input double
     */
    public double getDouble(String prompt) {
        System.out.print(prompt);
        return scanner.nextDouble();
    }

    /**
     * Gets an integer input from the user.
     * 
     * @param prompt The prompt to display
     * @return The input integer
     */
    public int getInt(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    /**
     * Gets a yes/no confirmation from the user.
     * 
     * @param prompt The prompt to display
     * @return true for yes, false for no
     */
    public boolean getConfirmation(String prompt) {
        char response;
        do {
            System.out.print(prompt);
            response = Character.toUpperCase(scanner.next().charAt(0));
            if (response != 'Y' && response != 'N') {
                showError(SalesConstants.ERROR_YES_NO_ONLY);
            }
        } while (response != 'Y' && response != 'N');

        return response == 'Y';
    }

    /**
     * Displays brand options and gets user selection.
     * 
     * @return The selected brand name
     */
    public String getBrandChoice() {
        System.out.println("\n" + SalesConstants.ENTER_BRAND);
        String[] options = SalesConstants.BRAND_OPTIONS;
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Enter your choice (1-" + options.length + "): ");

        int choice = scanner.nextInt();
        if (choice >= 1 && choice < options.length) {
            return options[choice - 1];
        } else if (choice == options.length) {
            return getLine("Enter Other Brand: ");
        } else {
            return "Other";
        }
    }

    /**
     * Displays colour options and gets user selection.
     * 
     * @return The selected colour name
     */
    public String getColourChoice() {
        System.out.println("\nChoose Item Colour:");
        String[] options = SalesConstants.COLOUR_OPTIONS;
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("Enter your choice (1-" + options.length + "): ");

        int choice = scanner.nextInt();
        if (choice >= 1 && choice < options.length) {
            return options[choice - 1];
        } else if (choice == options.length) {
            return getLine("Enter Other Colour: ");
        } else {
            return "Other";
        }
    }

    // ==================== Message Display ====================

    /**
     * Shows a success message.
     * 
     * @param message The message to display
     */
    public void showSuccess(String message) {
        System.out.println("\n" + message);
    }

    /**
     * Shows an error message.
     * 
     * @param message The error message to display
     */
    public void showError(String message) {
        System.out.println("\n" + message);
    }

    /**
     * Shows an info message.
     * 
     * @param message The info message to display
     */
    public void showInfo(String message) {
        System.out.println("\n" + message);
    }

    /**
     * Waits for user to press Enter.
     */
    public void waitForEnter() {
        System.out.print("\n" + SalesConstants.PRESS_ENTER_CONTINUE);
        scanner.nextLine();
        scanner.nextLine();
    }

    /**
     * Clears the screen by printing newlines.
     */
    public void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
