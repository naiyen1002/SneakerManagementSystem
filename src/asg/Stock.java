package asg;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import asg.MakeOrderModule.Model.StockItem;

/**
 *
 * @author ginwe
 */
public class Stock {

    /**
     * @param args the command line arguments
     */
    public static void main() {
        Stock NewStock = new Stock();
        NewStock.initializeStock();
        NewStock.Stock();
    }

    public final ArrayList<StockItem> stockItems;
    public final Scanner scanner;

    public Stock() {
        this.stockItems = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void Stock() {
        int choice;
        do {
            displayMenu();
            choice = getUserChoice();
            switch (choice) {
                case 1 -> addItem();
                case 2 -> searchItem();
                case 3 -> modifyItem();
                case 4 -> displayItems();
                case 5 -> deleteItem();
                case 6 -> closeSystem();
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    public void displayMenu() {
        System.out.println("====================================");
        System.out.println("         STOCK MANAGEMENT MENU      ");
        System.out.println("====================================");
        System.out.println("1. Add Item");
        System.out.println("2. Search Item");
        System.out.println("3. Modify Item");
        System.out.println("4. Display Stock List");
        System.out.println("5. Delete Item");
        System.out.println("6. Exit");
        System.out.println("====================================");
    }

    public int getUserChoice() {
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice >= 1 && choice <= 6) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
        return choice;
    }

    public void addItem() {
        System.out.print("Enter item code: ");
        String itemCode = scanner.nextLine();
        if (!itemCode.matches("\\d+")) {
            System.out.println("Invalid item code. Please enter numbers only.");
            return;
        }

        System.out.print("Enter item Brand: ");
        String itemBrand = scanner.nextLine();
        if (itemBrand.matches(".*\\d+.*")) {
            System.out.println("Item brand cannot contain numbers.");
            return;
        }

        System.out.print("Enter item description: ");
        String itemDescription = scanner.nextLine();

        System.out.print("Enter item price: ");
        float itemPrice;
        try {
            itemPrice = scanner.nextFloat();
            scanner.nextLine();
            if (itemPrice < 0) {
                System.out.println("Item price cannot less than zero.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine();
            return;
        }

        System.out.print("Enter item Color: ");
        String itemColor = scanner.nextLine();
        if (itemColor.matches(".*\\d+.*")) {
            System.out.println("Item color cannot contain numbers.");
            return;
        }

        System.out.print("Enter quantity in stock: ");
        int quantityInStock;
        try {
            quantityInStock = scanner.nextInt();
            scanner.nextLine();
            if (quantityInStock < 0) {
                System.out.println("Quantity in stock cannot less than zero.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine();
            return;
        }

        StockItem item = new StockItem(itemCode, itemBrand, itemDescription, itemColor, itemPrice, quantityInStock);
        stockItems.add(item);

        System.out.println("New item added successfully.");
    }

    public void searchItem() {
        System.out.print("Enter item code to search: ");
        String searchCode = scanner.nextLine();
        boolean found = false;

        for (StockItem item : stockItems) {
            if (item.getItemCode().equals(searchCode)) {
                displayItemDetails(item);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Item not found.");
        }
    }

    public void modifyItem() {
        System.out.print("Enter item code to modify: ");
        String modifyCode = scanner.nextLine();
        boolean found = false;

        for (StockItem item : stockItems) {
            if (item.getItemCode().equals(modifyCode)) {
                displayItemDetails(item);
                updateItemDetails(item);
                found = true;
                System.out.println("Item modified successfully.");
                break;
            }
        }

        if (!found) {
            System.out.println("Item not found.");
        }
    }

    public void updateItemDetails(StockItem item) {
        System.out.print("Enter new item brand: ");
        String newItemBrand = scanner.nextLine();
        if (newItemBrand.matches(".*\\d+.*")) {
            System.out.println("Item Brand cannot contain numbers.");
            return;
        }
        item.setBrand(newItemBrand);

        System.out.print("Enter new item description: ");
        String newItemDescription = scanner.nextLine();
        if (newItemDescription.matches(".*\\d+.*")) {
            System.out.println("Item description cannot contain numbers.");
            return;
        }
        item.setItemDescription(newItemDescription);

        System.out.print("Enter new item color: ");
        String newItemColor = scanner.nextLine();
        if (newItemColor.matches(".*\\d+.*")) {
            System.out.println("Item color cannot contain numbers.");
            return;
        }
        item.setBrand(newItemColor);

        System.out.print("Enter new item price: ");
        Double newItemPrice;
        try {
            newItemPrice = scanner.nextDouble();
            scanner.nextLine();
            if (newItemPrice < 0) {
                System.out.println("Item price cannot less than zero.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine();
            return;
        }
        item.setItemPrice(newItemPrice);

        System.out.print("Enter new quantity in stock: ");
        int newQuantityInStock;
        try {
            newQuantityInStock = scanner.nextInt();
            scanner.nextLine();
            if (newQuantityInStock < 0) {
                System.out.println("Quantity in stock cannot less than zero.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine();
            return;
        }
        item.setQuantityInStock(newQuantityInStock);
    }

    public void displayItems() {
        if (stockItems.isEmpty()) {
            System.out.println("The stock is empty.");
        } else {
            System.out.printf("%-12s %-12s %-30s %-18s %-12s %-18s%n", "Item Code", "Item Brand", "Item Description",
                    "Item Color", "Item Price",
                    "Quantity in Stock");

            for (StockItem item : stockItems) {
                String itemCode = item.getItemCode();
                String itemBrand = item.getBrand();
                String itemDescription = item.getItemDescription();
                String itemColor = item.getColour();
                double itemPrice = item.getItemPrice();
                int quantityInStock = item.getQuantityInStock();

                System.out.printf("%-12s %-12s %-30s %-18s %-12s %-18s%n", itemCode, itemBrand, itemDescription,
                        itemColor, itemPrice,
                        quantityInStock);
            }
        }
    }

    public void deleteItem() {
        System.out.print("Enter item code to delete: ");
        String deleteCode = scanner.nextLine();
        boolean found = false;

        for (int i = 0; i < stockItems.size(); i++) {
            StockItem item = stockItems.get(i);
            if (item.getItemCode().equals(deleteCode)) {
                stockItems.remove(i);
                found = true;
                System.out.println("Item deleted successfully.");
                break;
            }
        }

        if (!found) {
            System.out.println("Item not found.");
        }
    }

    public void closeSystem() {
        System.out.println("\nThank you for using the stock management system. Goodbye!\n");
    }

    public void displayItemDetails(StockItem item) {
        System.out.println("Item found:");
        System.out.println("Item code: " + item.getItemCode());
        System.out.println("Item brand: " + item.getBrand());
        System.out.println("Item description: " + item.getItemDescription());
        System.out.println("Item color: " + item.getColour());
        System.out.println("Item price: " + item.getItemPrice());
        System.out.println("Quantity in stock: " + item.getQuantityInStock());
    }

    public void initializeStock() {
        StockItem item1 = new StockItem("I001", "NIKE", "Air Max 90", "Black/White/Grey", 480.00, 50);
        StockItem item2 = new StockItem("I002", "NIKE", "Revolution 5", "Blue/White", 220.00, 30);
       StockItem item3 = new StockItem("I003", "NIKE", "Jordan 1 Mid", "Red/Black/White", 570.00, 25);
       StockItem item4 = new StockItem("I004", "NIKE", "Roshe One", "Green/Black", 340.00, 40);
       StockItem item5 = new StockItem("I005", "NIKE", "Flex Experience RN 10", "Grey/Blue", 280.00, 35);
       StockItem item6 = new StockItem("I006", "NIKE", "Air Max 270", "Pink/White", 600.00, 45);
       StockItem item7 = new StockItem("I007", "NIKE", "Tanjun", "Purple/Grey", 220.00, 20);
       StockItem item8 = new StockItem("I008", "NIKE", "React Element 55", "Teal/Black", 500.00, 28);
       StockItem item9 = new StockItem("I009", "NIKE", "Free RN 5.0", "Coral/White", 260.00, 22);
       StockItem item10 = new StockItem("I010", "NIKE", "Air Force 1", "Lavender/White", 330.00, 38);
       StockItem item11 = new StockItem("I011", "PUMA", "Suede Classic", "Black/White", 290.00, 33);
       StockItem item12 = new StockItem("I012", "PUMA", "Future Rider", "Blue/Red/White", 320.00, 15);
       StockItem item13 = new StockItem("I013", "PUMA", "Cell Venom", "Grey/Yellow/Black", 440.00, 27);
       StockItem item14 = new StockItem("I014", "PUMA", "Carson 2", "Navy/White", 230.00, 22);
       StockItem item15 = new StockItem("I015", "PUMA", "RS-X³", "Black/Green", 370.00, 28);
       StockItem item16 = new StockItem("I016", "PUMA", "Cali", "White/Pink", 360.00, 25);
       StockItem item17 = new StockItem("I017", "PUMA", "Nova", "Peach/Beige", 380.00, 30);
       StockItem item18 = new StockItem("I018", "PUMA", "Basket Heart", "Pastel Blue", 290.00, 40);
       StockItem item19 = new StockItem("I019", "PUMA", "Muse 2", "Lavender/Grey", 400.00, 20);
       StockItem item20 = new StockItem("I020", "PUMA", "Vikky", "Black/Rose Gold", 180.00, 15);
       StockItem item21 = new StockItem("I021", "ADIDAS", "Originals Superstar", "White/Black", 330.00, 32);
       StockItem item22 = new StockItem("I022", "ADIDAS", "Ultraboost 21", "Black/White", 800.00, 18);
       StockItem item23 = new StockItem("I023", "ADIDAS", "NMD R1", "Grey/Red/Blue", 550.00, 25);
       StockItem item24 = new StockItem("I024", "ADIDAS", "Cloudfoam Ultimate", "Navy/White", 280.00, 28);
       StockItem item25 = new StockItem("I025", "ADIDAS", "Gazelle", "Black/Gold", 300.00, 22);
       StockItem item26 = new StockItem("I026", "ADIDAS", "Stan Smith", "White/Pink", 310.00, 30);
       StockItem item27 = new StockItem("I027", "ADIDAS", "Sleek", "Pastel Blue", 360.00, 25);
       StockItem item28 = new StockItem("I028", "ADIDAS", "Swift Run", "Coral/White", 380.00, 20);
       StockItem item29 = new StockItem("I029", "ADIDAS", "Continental 80", "Pink/White", 320.00, 28);
       StockItem item30 = new StockItem("I030", "ADIDAS", "Falcon", "Black/Rose Gold", 380.00, 15);


        stockItems.add(item1);
        stockItems.add(item2);
        stockItems.add(item3);
        stockItems.add(item4);
        stockItems.add(item5);
        stockItems.add(item6);
        stockItems.add(item7);
        stockItems.add(item8);
        stockItems.add(item9);
        stockItems.add(item10);
        stockItems.add(item11);
        stockItems.add(item12);
        stockItems.add(item13);
        stockItems.add(item14);
        stockItems.add(item15);
        stockItems.add(item16);
        stockItems.add(item17);
        stockItems.add(item18);
        stockItems.add(item19);
        stockItems.add(item20);
        stockItems.add(item21);
        stockItems.add(item22);
        stockItems.add(item23);
        stockItems.add(item24);
        stockItems.add(item25);
        stockItems.add(item26);
        stockItems.add(item27);
        stockItems.add(item28);
        stockItems.add(item29);
        stockItems.add(item30);
    }    

    
// }
