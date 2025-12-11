package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Constants.StockConstants;
import asg.StockManagementModule.Model.StockItem;

/**
 * Application controller: validation + high-level operations.
 */
public class StockController {

    private final StockItemController inventory;

    public StockController(StockItemController inventory) {
        this.inventory = inventory;
    }

    public StockItemController getInventory() {
        return inventory;
    }

    // -------- validation helpers --------

    public boolean isValidItemCode(String code) {
        return code != null && code.matches(StockConstants.ITEM_CODE_REGEX);
    }

    public boolean isValidBrand(String brand) {
        return brand != null && !brand.isBlank() && !brand.matches(".*\\d+.*");
    }

    public boolean isValidColour(String colour) {
        return colour != null && !colour.isBlank() && !colour.matches(".*\\d+.*");
    }

    public boolean isValidPrice(double price) {
        return price >= 0;
    }

    public boolean isValidQuantity(int qty) {
        return qty >= 0;
    }

    public boolean isValidDescription(String desc) {
        return desc != null && !desc.isBlank();
    }

    // -------- operations used by Service / Commands --------

    public boolean addItem(String code, String brand, String desc,
            String colour, double price, int qty) {
        if (!isValidItemCode(code))
            return false;
        if (inventory.exists(code))
            return false;
        if (!isValidBrand(brand))
            return false;
        if (!isValidDescription(desc))
            return false;
        if (!isValidColour(colour))
            return false;
        if (!isValidPrice(price))
            return false;
        if (!isValidQuantity(qty))
            return false;

        StockItem item = new StockItem(code, brand, desc, colour, price, qty);
        return inventory.addItem(item);
    }

    public boolean updateItem(String code, String brand, String desc,
            String colour, double price, int qty) {
        if (!isValidBrand(brand))
            return false;
        if (!isValidDescription(desc))
            return false;
        if (!isValidColour(colour))
            return false;
        if (!isValidPrice(price))
            return false;
        if (!isValidQuantity(qty))
            return false;

        return inventory.updateItem(code, brand, desc, colour, price, qty);
    }

    public boolean deleteItem(String code) {
        return inventory.deleteByCode(code);
    }
}
