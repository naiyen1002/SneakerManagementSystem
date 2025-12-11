package asg.StockManagementModule.Controller;

import asg.StockManagementModule.Constants.StockData;
import asg.StockManagementModule.Model.StockItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Core inventory operations (CRUD on list).
 */
public class StockItemController {

    private final List<StockItem> stockItems = new ArrayList<>();

    public void initializeDefaultStock() {
        if (!stockItems.isEmpty())
            return;
        stockItems.addAll(StockData.defaultStock());
    }

    public List<StockItem> getAllItems() {
        return Collections.unmodifiableList(stockItems);
    }

    public Optional<StockItem> findByCode(String code) {
        if (code == null)
            return Optional.empty();
        return stockItems.stream()
                .filter(i -> code.equalsIgnoreCase(i.getItemCode()))
                .findFirst();
    }

    public boolean exists(String code) {
        return findByCode(code).isPresent();
    }

    public boolean addItem(StockItem item) {
        if (item == null)
            return false;
        if (exists(item.getItemCode()))
            return false;
        stockItems.add(item);
        return true;
    }

    public boolean deleteByCode(String code) {
        Optional<StockItem> found = findByCode(code);
        found.ifPresent(stockItems::remove);
        return found.isPresent();
    }

    public boolean updateItem(String code, String newBrand, String newDesc,
            String newColour, double newPrice, int newQty) {
        Optional<StockItem> found = findByCode(code);
        if (found.isEmpty())
            return false;

        StockItem item = found.get();
        item.setBrand(newBrand);
        item.setItemDescription(newDesc);
        item.setColour(newColour);
        item.setItemPrice(newPrice);
        item.setQuantityInStock(newQty);
        return true;
    }
}
