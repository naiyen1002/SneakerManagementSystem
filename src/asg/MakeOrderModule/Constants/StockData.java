package asg.MakeOrderModule.Constants;

import asg.MakeOrderModule.Model.StockItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Constants class containing initial stock data for the sneaker inventory.
 * This class follows the MVC pattern by separating data initialization from business logic.
 */
public class StockData {
    
    /**
     * Returns a list of all initial stock items.
     * This data is used to initialize the inventory when the application starts.
     * 
     * @return ArrayList of StockItem objects with predefined inventory
     */
    public static ArrayList<StockItem> getInitialStockItems() {
        return new ArrayList<>(Arrays.asList(
            // NIKE Products
            new StockItem("I001", "NIKE", "Air Max 90", "Black/White/Grey", 480.00, 50),
            new StockItem("I002", "NIKE", "Revolution 5", "Blue/White", 220.00, 30),
            new StockItem("I003", "NIKE", "Jordan 1 Mid", "Red/Black/White", 570.00, 25),
            new StockItem("I004", "NIKE", "Roshe One", "Green/Black", 340.00, 40),
            new StockItem("I005", "NIKE", "Flex Experience RN 10", "Grey/Blue", 280.00, 35),
            new StockItem("I006", "NIKE", "Air Max 270", "Pink/White", 600.00, 45),
            new StockItem("I007", "NIKE", "Tanjun", "Purple/Grey", 220.00, 20),
            new StockItem("I008", "NIKE", "React Element 55", "Teal/Black", 500.00, 28),
            new StockItem("I009", "NIKE", "Free RN 5.0", "Coral/White", 260.00, 22),
            new StockItem("I010", "NIKE", "Air Force 1", "Lavender/White", 330.00, 38),
            
            // PUMA Products
            new StockItem("I011", "PUMA", "Suede Classic", "Black/White", 290.00, 33),
            new StockItem("I012", "PUMA", "Future Rider", "Blue/Red/White", 320.00, 15),
            new StockItem("I013", "PUMA", "Cell Venom", "Grey/Yellow/Black", 440.00, 27),
            new StockItem("I014", "PUMA", "Carson 2", "Navy/White", 230.00, 22),
            new StockItem("I015", "PUMA", "RS-X³", "Black/Green", 370.00, 28),
            new StockItem("I016", "PUMA", "Cali", "White/Pink", 360.00, 25),
            new StockItem("I017", "PUMA", "Nova", "Peach/Beige", 380.00, 30),
            new StockItem("I018", "PUMA", "Basket Heart", "Pastel Blue", 290.00, 40),
            new StockItem("I019", "PUMA", "Muse 2", "Lavender/Grey", 400.00, 20),
            new StockItem("I020", "PUMA", "Vikky", "Black/Rose Gold", 180.00, 15),
            
            // ADIDAS Products
            new StockItem("I021", "ADIDAS", "Originals Superstar", "White/Black", 330.00, 32),
            new StockItem("I022", "ADIDAS", "Ultraboost 21", "Black/White", 800.00, 18),
            new StockItem("I023", "ADIDAS", "NMD R1", "Grey/Red/Blue", 550.00, 25),
            new StockItem("I024", "ADIDAS", "Cloudfoam Ultimate", "Navy/White", 280.00, 28),
            new StockItem("I025", "ADIDAS", "Gazelle", "Black/Gold", 300.00, 22),
            new StockItem("I026", "ADIDAS", "Stan Smith", "White/Pink", 310.00, 30),
            new StockItem("I027", "ADIDAS", "Sleek", "Pastel Blue", 360.00, 25),
            new StockItem("I028", "ADIDAS", "Swift Run", "Coral/White", 380.00, 20),
            new StockItem("I029", "ADIDAS", "Continental 80", "Pink/White", 320.00, 28),
            new StockItem("I030", "ADIDAS", "Falcon", "Black/Rose Gold", 380.00, 15)
        ));
    }
    
}
