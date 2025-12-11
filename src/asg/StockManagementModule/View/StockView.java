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

    public StockView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void displayMenu() {
        System.out.println(StockConstants.LINE);
        System.out.println("         " + StockConstants.MENU_TITLE + "      ");
        System.out.println(StockConstants.LINE);

        for (StockMenuOption opt : StockMenuOption.values()) {
            System.out.println(opt.getCode() + ". " + opt.getDesc());
        }
        System.out.println(StockConstants.LINE);
    }

    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = scanner.nextInt();
                scanner.nextLine();
                return v;
            } catch (InputMismatchException e) {
                System.out.println(StockConstants.INVALID_NUMBER);
                scanner.nextLine();
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = scanner.nextDouble();
                scanner.nextLine();
                return v;
            } catch (InputMismatchException e) {
                System.out.println(StockConstants.INVALID_NUMBER);
                scanner.nextLine();
            }
        }
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public void displayErrorMessage(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void displayInfoMessage(String message) {
        System.out.println(message);
    }

    public void printItemDetails(StockItem item) {
        System.out.println("Item code: " + item.getItemCode());
        System.out.println("Item brand: " + item.getBrand());
        System.out.println("Item description: " + item.getItemDescription());
        System.out.println("Item color: " + item.getColour());
        System.out.println("Item price: " + item.getItemPrice());
        System.out.println("Quantity in stock: " + item.getQuantityInStock());
    }

    public void printItemTable(List<StockItem> items) {
        if (items.isEmpty()) {
            System.out.println(StockConstants.STOCK_EMPTY);
            return;
        }

        System.out.printf("%-8s %-10s %-30s %-18s %-10s %-10s%n",
                "Code", "Brand", "Description", "Color", "Price", "Qty");

        for (StockItem item : items) {
            System.out.printf("%-8s %-10s %-30s %-18s %-10.2f %-10d%n",
                    item.getItemCode(),
                    item.getBrand(),
                    item.getItemDescription(),
                    item.getColour(),
                    item.getItemPrice(),
                    item.getQuantityInStock());
        }
    }
}
