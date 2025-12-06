package view;

import model.Sale;
import service.SalesService;
import java.util.List;
import java.util.Scanner;

/**
 * View class for handling all user interface interactions for sales management.
 * This class is responsible for displaying information and collecting user input.
 * 
 * @author Sneaker Management System Team
 */
public class SalesView {
    private final Scanner scanner;

    /**
     * Constructs a new SalesView with a Scanner for user input.
     */
    public SalesView() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the main sales management menu.
     */
    public void displayMenu() {
        clearScreen();
        System.out.println("\n\n\n                                  ======================================");
        System.out.println("                                           SALES MANAGEMENT MENU      ");
        System.out.println("                                  ======================================");
        System.out.println("                                      1 -> Display Sales Information");
        System.out.println("                                      2 -> Add New Sales Information");
        System.out.println("                                      3 -> Delete Sales Information");
        System.out.println("                                      4 -> Modify Sales Information");
        System.out.println("                                      5 -> Search Sales Information");
        System.out.println("                                      6 -> Sales Report");
        System.out.println("                                      0 -> Exit");
        System.out.println("                                  ======================================");
        System.out.print("                                  Enter your choice: ");
    }

    /**
     * Displays the search menu.
     */
    public void displaySearchMenu() {
        System.out.println("\n\n\n                                  --------------------------------------");
        System.out.println("                                                SEARCH MENU             ");
        System.out.println("                                  --------------------------------------");
        System.out.println("                                      1 -> Search by SALES ID");
        System.out.println("                                      2 -> Search by MEMBER ID");
        System.out.println("                                      3 -> Search by ITEM CODE");
        System.out.println("                                      4 -> Search by BRAND");
        System.out.println("                                      5 -> Search by ITEM DESCRIPTION");
        System.out.println("                                      6 -> Search by COLOUR");
        System.out.println("                                      7 -> Search by ITEM PRICE");
        System.out.println("                                      8 -> Search by QUANTITY SALES");
        System.out.println("                                      0 -> Exit");
        System.out.println("                                  --------------------------------------");
        System.out.print("                                  Enter your choice: ");
    }

    /**
     * Displays all sales in a formatted table.
     * 
     * @param sales The list of sales to display
     */
    public void displayAllSales(List<Sale> sales) {
        System.out.println("+========+=========+=========+=========+=============================+==========+==============+=========+");
        System.out.println("|SALES ID|MEMBER ID|ITEM CODE|  BRAND  |       ITEM DESCRIPTION      |  COLOUR  |ITEM PRICE(RM)|QTY SALES|");
        System.out.println("+========+=========+=========+=========+=============================+==========+==============+=========+");

        for (Sale sale : sales) {
            displaySaleRow(sale);
        }

        System.out.println("\n\n" + sales.size() + " records have been displayed.\n");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Displays a single sale row in table format.
     * 
     * @param sale The sale to display
     */
    public void displaySaleRow(Sale sale) {
        System.out.printf("|  %-6s|  %-7s|  %-7s|%-9s|%-29s|%-10s|%14.2f|%9d|%n",
                         sale.getSalesId(), sale.getMemberId(), sale.getItemCode(),
                         sale.getBrand(), sale.getItemDescription(), sale.getColour(),
                         sale.getItemPrice(), sale.getQuantitySales());
        System.out.println("+--------+---------+---------+---------+-----------------------------+----------+--------------+---------+");
    }

    /**
     * Displays detailed information for a single sale.
     * 
     * @param sale The sale to display
     */
    public void displaySaleDetails(Sale sale) {
        System.out.println("\n\nItem Found:");
        System.out.println("\nSALES ID        :" + sale.getSalesId());
        System.out.println("MEMBER ID       :" + sale.getMemberId());
        System.out.println("ITEM CODE       :" + sale.getItemCode());
        System.out.println("BRAND           :" + sale.getBrand());
        System.out.println("ITEM DESCRIPTION:" + sale.getItemDescription());
        System.out.println("COLOUR          :" + sale.getColour());
        System.out.println("ITEM PRICE      :RM" + sale.getItemPrice());
        System.out.println("QUANTITY SALES  :" + sale.getQuantitySales());
    }

    /**
     * Displays search results.
     * 
     * @param sales The list of sales found
     * @param searchTerm The term that was searched for
     */
    public void displaySearchResults(List<Sale> sales, String searchTerm) {
        if (sales.isEmpty()) {
            System.out.println("\nNo sales found matching: " + searchTerm);
        } else {
            for (Sale sale : sales) {
                displaySaleDetails(sale);
            }
        }
    }

    /**
     * Displays the sales report.
     * 
     * @param sales The list of all sales
     * @param statistics The sales statistics
     */
    public void displaySalesReport(List<Sale> sales, SalesService.SalesStatistics statistics) {
        System.out.println("+========+=========+=========+=========+=============================+==========+==============+=========+");
        System.out.println("|SALES ID|MEMBER ID|ITEM CODE|  BRAND  |       ITEM DESCRIPTION      |  COLOUR  |ITEM PRICE(RM)|QTY SALES|");
        System.out.println("+========+=========+=========+=========+=============================+==========+==============+=========+");

        for (Sale sale : sales) {
            displaySaleRow(sale);
        }

        System.out.println("\n\n" + statistics.toString() + "\n");
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Gets the user's menu choice.
     * 
     * @return The menu choice as a character
     */
    public char getMenuChoice() {
        return scanner.next().charAt(0);
    }

    /**
     * Gets sale input from the user.
     * 
     * @return A new Sale object with user-provided data, or null if cancelled
     */
    public Sale getSaleInput() {
        String salesId = getValidatedInput("Enter Sales ID (FOUR characters): ", 
                                          input -> input.length() == 4,
                                          "Invalid sales id length. Please enter exactly 4 characters.");
        
        String memberId = getValidatedInput("Enter Member ID (FOUR characters): ",
                                           input -> input.length() == 4,
                                           "Invalid member id length. Please enter exactly 4 characters.");
        
        String itemCode = getValidatedInput("Enter Item Code (FOUR characters): ",
                                           input -> input.length() == 4,
                                           "Invalid item code length. Please enter exactly 4 characters.");
        
        String brand = getBrandInput();
        
        scanner.nextLine(); // Consume newline
        String description = getValidatedInput("Enter Item Description: ",
                                              input -> !input.isEmpty() && input.length() <= 29,
                                              "Item description must not be empty and max 29 characters.");
        
        String colour = getColourInput();
        
        double price = getPriceInput();
        
        System.out.print("\nEnter Item Quantity Sales: ");
        int quantity = scanner.nextInt();
        
        return new Sale(salesId, memberId, itemCode, brand, description, colour, price, quantity);
    }

    /**
     * Gets validated input from the user.
     */
    private String getValidatedInput(String prompt, java.util.function.Predicate<String> validator, String errorMessage) {
        String input;
        boolean isValid;
        do {
            System.out.print("\n" + prompt);
            input = scanner.next();
            isValid = validator.test(input);
            if (!isValid) {
                System.out.println(errorMessage);
            }
        } while (!isValid);
        return input;
    }

    /**
     * Gets brand input from the user.
     */
    private String getBrandInput() {
        System.out.println("\nEnter Brand: ");
        System.out.println("1. NIKE");
        System.out.println("2. PUMA");
        System.out.println("3. ADIDAS");
        System.out.println("4. Other");
        System.out.print("Enter your choice (1-4): ");
        
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1: return "NIKE";
            case 2: return "PUMA";
            case 3: return "ADIDAS";
            case 4:
                scanner.nextLine();
                System.out.print("Enter Other Brand: ");
                return scanner.nextLine();
            default:
                System.out.println("Invalid choice. Setting brand to 'Other'.");
                return "Other";
        }
    }

    /**
     * Gets colour input from the user.
     */
    private String getColourInput() {
        System.out.println("\nChoose Item Color:");
        System.out.println("1. Black");
        System.out.println("2. White");
        System.out.println("3. Grey");
        System.out.println("4. Red");
        System.out.println("5. Other");
        System.out.print("Enter your choice (1-5): ");
        
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1: return "Black";
            case 2: return "White";
            case 3: return "Grey";
            case 4: return "Red";
            case 5:
                scanner.nextLine();
                System.out.print("Enter Other Color: ");
                return scanner.nextLine();
            default:
                System.out.println("Invalid choice. Setting color to 'Other'.");
                return "Other";
        }
    }

    /**
     * Gets price input from the user.
     */
    private double getPriceInput() {
        double price;
        do {
            System.out.print("\nEnter Item Price:RM ");
            price = scanner.nextDouble();
            if (price > 99999999999.99) {
                System.out.println("Item price exceeds the maximum allowed amount of RM99999999999.99.");
            }
        } while (price > 99999999999.99);
        return price;
    }

    /**
     * Gets a string input from the user.
     */
    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    /**
     * Gets a line of text from the user.
     */
    public String getLineInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Gets a double input from the user.
     */
    public double getDoubleInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextDouble();
    }

    /**
     * Gets an integer input from the user.
     */
    public int getIntInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    /**
     * Displays a success message.
     */
    public void showSuccessMessage(String message) {
        System.out.println("\n" + message);
    }

    /**
     * Displays an error message.
     */
    public void showErrorMessage(String message) {
        System.out.println("\nERROR: " + message);
    }

    /**
     * Gets a yes/no confirmation from the user.
     * 
     * @param message The confirmation message
     * @return true if user confirms (Y/y), false otherwise
     */
    public boolean confirmAction(String message) {
        char response;
        do {
            System.out.print("\n" + message + " (Y/N): ");
            response = scanner.next().charAt(0);
            if (response != 'y' && response != 'Y' && response != 'n' && response != 'N') {
                System.out.println("Enter y, Y, n, or N only!!!");
            }
        } while (response != 'y' && response != 'Y' && response != 'n' && response != 'N');
        
        return response == 'y' || response == 'Y';
    }

    /**
     * Clears the console screen.
     */
    public void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    /**
     * Waits for user to press Enter.
     */
    public void waitForEnter() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }
}
