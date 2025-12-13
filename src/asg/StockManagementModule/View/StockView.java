package asg.StockManagementModule.View;

import asg.StockManagementModule.Constants.StockConstants;
import asg.StockManagementModule.Constants.StockMenuOption;
import asg.StockManagementModule.Model.StockItem;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * View layer: only UI, no business logic.
 */
public class StockView {

    private final Scanner scanner;

    private static final String INDENT = "\t\t\t\t\t";

    public StockView(Scanner scanner) {
        this.scanner = scanner;
    }

    // ========================= MENU =========================

    public void displayMenu() {
        System.out.println(StockConstants.LINE);
        System.out.println(INDENT + StockConstants.MENU_TITLE);
        System.out.println(StockConstants.LINE);

        for (StockMenuOption opt : StockMenuOption.values()) {
            System.out.printf("%s%d. %s%n", INDENT, opt.getCode(), opt.getDesc());
        }

        System.out.println(StockConstants.LINE);
    }

    // ========================= INPUT =========================

    public int readInt(String prompt) {
        while (true) {
            System.out.print(INDENT + prompt);
            try {
                int v = scanner.nextInt();
                scanner.nextLine();
                return v;
            } catch (InputMismatchException e) {
                System.out.println(INDENT + StockConstants.INVALID_NUMBER);
                scanner.nextLine();
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            System.out.print(INDENT + prompt);
            try {
                double v = scanner.nextDouble();
                scanner.nextLine();
                return v;
            } catch (InputMismatchException e) {
                System.out.println(INDENT + StockConstants.INVALID_NUMBER);
                scanner.nextLine();
            }
        }
    }

    public String readLine(String prompt) {
        System.out.print(INDENT + prompt);
        return scanner.nextLine().trim();
    }

    // ========================= MESSAGES =========================

    public void displayErrorMessage(String message) {
        System.out.println(INDENT + "[ERROR]" + message);
    }

    public void displayInfoMessage(String message) {
        System.out.println(INDENT + message);
    }

    // ========================= ITEM DISPLAY =========================

    public void printItemDetails(StockItem item) {
        System.out.println(INDENT + "Item code: " + item.getItemCode());
        System.out.println(INDENT + "Item brand: " + item.getBrand());
        System.out.println(INDENT + "Item description: " + item.getItemDescription());
        System.out.println(INDENT + "Item color: " + item.getColour());
        System.out.println(INDENT + "Item price: " + item.getItemPrice());
        System.out.println(INDENT + "Quantity in stock: " + item.getQuantityInStock());
    }

    public void printItemTable(List<StockItem> items) {
        if (items.isEmpty()) {
            System.out.println(INDENT + StockConstants.STOCK_EMPTY);
            return;
        }

        // Header
        System.out.printf(
                INDENT + "%-8s %-10s %-30s %-18s %-10s %-10s%n",
                "Code", "Brand", "Description", "Color", "Price", "Qty");

        // Rows
        for (StockItem item : items) {
            System.out.printf(
                    INDENT + "%-8s %-10s %-30s %-18s %-10.2f %-10d%n",
                    item.getItemCode(),
                    item.getBrand(),
                    item.getItemDescription(),
                    item.getColour(),
                    item.getItemPrice(),
                    item.getQuantityInStock());
        }
    }
}
