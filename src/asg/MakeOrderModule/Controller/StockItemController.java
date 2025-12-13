package asg.MakeOrderModule.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import asg.MakeOrderModule.Constants.StockData;
import asg.MakeOrderModule.Model.StockItem;

/**
 * Controller for managing stock items.
 * Handles business logic for stock operations including search, retrieval, and availability checks.
 */
public class StockItemController {
    private static ArrayList<StockItem> stockItems = new ArrayList<>();

    /**
     * Initializes stock items from the Constants data.
     */
    public static void initializeStock() {
        stockItems = StockData.getInitialStockItems();
    }


    public static ArrayList<StockItem> getStockItems() {
        return stockItems;
    }


    public static void addStockItem(StockItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Stock item cannot be null");
        }
        stockItems.add(item);
    }

 
    public static StockItem findStockItemByCode(String itemCode) {
        if (itemCode == null || itemCode.trim().isEmpty()) {
            return null;
        }
        
        for (StockItem item : stockItems) {
            if (item.getItemCode().equalsIgnoreCase(itemCode.trim())) {
                return item;
            }
        }
        return null;
    }

  
    public static List<StockItem> findStockItemsByBrand(String brand) {
        if (brand == null || brand.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return stockItems.stream()
                .filter(item -> item.getBrand().equalsIgnoreCase(brand.trim()))
                .collect(Collectors.toList());
    }

    public static boolean isItemAvailable(String itemCode) {
        StockItem item = findStockItemByCode(itemCode);
        return item != null && item.isInStock();
    }

 
    public static int getStockItemCount() {
        return stockItems.size();
    }


    public static void clearStock() {
        stockItems.clear();
    }

    /**
     * Deducts stock quantity for an item.
     * @param itemCode The item code to deduct from
     * @param quantity The quantity to deduct
     * @return true if deduction successful, false if item not found or insufficient stock
     */
    public static boolean deductStock(String itemCode, int quantity) {
        if (itemCode == null || itemCode.trim().isEmpty() || quantity <= 0) {
            return false;
        }
        
        StockItem item = findStockItemByCode(itemCode);
        if (item == null) {
            return false;
        }
        
        if (item.getQuantityInStock() < quantity) {
            return false;  // Insufficient stock
        }
        
        item.setQuantityInStock(item.getQuantityInStock() - quantity);
        return true;
    }
}
