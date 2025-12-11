package asg.MakeOrderModule.Service;

import java.util.List;
import asg.MakeOrderModule.Controller.OrderDetailsController;
import asg.MakeOrderModule.Controller.StockItemController;
import asg.MakeOrderModule.Model.OrderDetails;
import asg.MakeOrderModule.Model.StockItem;

/**
 * Service class for the Make Order module.
 * Acts as a bridge between the Controller (UI logic) and the Model (Data logic).
 * Handles business rules and data coordination.
 */
public class MakeOrderService {

    /**
     * Initializes the service by ensuring stock is loaded.
     */
    public void initialize() {
        StockItemController.initializeStock();
    }

    /**
     * Gets all available stock items.
     * @return List of all stock items
     */
    public List<StockItem> getAllProducts() {
        return StockItemController.getStockItems();
    }

    /**
     * Finds a stock item by its code.
     * @param itemCode The code to search for
     * @return The found StockItem, or null if not found
     */
    public StockItem findProductByCode(String itemCode) {
        return StockItemController.findStockItemByCode(itemCode);
    }

    /**
     * Finds stock items by brand.
     * @param brand The brand to search for
     * @return List of matching StockItems
     */
    public List<StockItem> findProductsByBrand(String brand) {
        return StockItemController.findStockItemsByBrand(brand);
    }

    /**
     * Adds an item to the cart.
     * @param item The StockItem to add
     */
    public void addToCart(StockItem item) {
        OrderDetailsController.addOrderDetail(item);
    }

    /**
     * Calculates the current total of the cart.
     * @return The total price
     */
    public double calculateCartTotal() {
        return OrderDetailsController.calculateTotal();
    }

    /**
     * Checks if the cart is empty.
     * @return true if empty, false otherwise
     */
    public boolean isCartEmpty() {
        return OrderDetailsController.isEmpty();
    }

    /**
     * Gets all items currently in the cart.
     * @return List of OrderDetails
     */
    public List<OrderDetails> getCartItems() {
        return OrderDetailsController.getOrderDetails();
    }

    /**
     * Finds an order detail by its item code.
     * @param orderCode The code to search for
     * @return The OrderDetails object if found, null otherwise
     */
    public OrderDetails findOrderDetail(String orderCode) {
        return OrderDetailsController.getOrderDetailByCode(orderCode);
    }

    /**
     * Removes an item from the cart by its order code.
     * @param orderCode The code of the item to remove
     * @return true if removed, false if not found
     */
    public boolean removeOrderDetail(String orderCode) {
        // First check if it exists (Service layer validation)
        if (OrderDetailsController.getOrderDetailByCode(orderCode) == null) {
            return false;
        }
        OrderDetailsController.removeOrderDetail(orderCode);
        return true;
    }

}
