package controller;

import java.util.ArrayList;
import model.StockItem; // adjust package for your StockItem class

public class StockItemController {
    private final static ArrayList<StockItem> stockItems = new ArrayList<>();

    public static void initializeStock() {
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




    public static ArrayList<StockItem> getStockItems() {
        return stockItems;
    }

    public static void addStockItem(StockItem item) {
        stockItems.add(item);
    }
}
