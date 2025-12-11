package asg.MakeOrderModule.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import asg.MakeOrderModule.Model.OrderDetails;
import asg.MakeOrderModule.Model.StockItem;

/**
 * Controller for managing order details (shopping cart).
 * Handles business logic for cart operations including add, remove, search, and calculations.
 */
public class OrderDetailsController {
    private static ArrayList<OrderDetails> orderDetails = new ArrayList<>();
    
    /**
     * Adds an order detail from a stock item.
     * 
     * @param item The stock item to add to the cart
     */
    public static void addOrderDetail(StockItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Stock item cannot be null");
        }
        
        OrderDetails order = new OrderDetails(
            item.getItemCode(),
            item.getBrand(),
            item.getItemDescription(),
            item.getColour(),
            item.getItemPrice()
        );
        orderDetails.add(order);
    }
    
    /**
     * Gets all order details in the cart.
     * 
     * @return ArrayList of all order details
     */
    public static ArrayList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }
    
    /**
     * Finds a single order detail by order code.
     * 
     * @param orderCode The order code to search for
     * @return The first matching OrderDetails, or null if not found
     */
    public static OrderDetails getOrderDetailByCode(String orderCode) {
        if (orderCode == null || orderCode.trim().isEmpty()) {
            return null;
        }
        
        for (OrderDetails order : orderDetails) {
            if (order.getOrderCode().equalsIgnoreCase(orderCode.trim())) {
                return order;
            }
        }
        return null;
    }
    
    
    /**
     * Removes a single order detail by order code.
     * Only removes the first matching order.
     * 
     * @param orderCode The order code to remove
     * @return true if an order was removed, false otherwise
     */
    public static boolean removeOrderDetail(String orderCode) {
        return orderDetails.removeIf(order -> 
            order.getOrderCode().equalsIgnoreCase(orderCode));
    }
    
    
    /**
     * Clears all order details from the cart.
     */
    public static void clearOrderDetails() {
        orderDetails.clear();
    }
    
    /**
     * Calculates the total price of all orders in the cart.
     * 
     * @return Total price
     */
    public static double calculateTotal() {
        return orderDetails.stream()
            .mapToDouble(OrderDetails::getOrderPrice)
            .sum();
    }
    
    /**
     * Gets the number of items in the cart.
     * 
     * @return Order count
     */
    public static int getOrderCount() {
        return orderDetails.size();
    }
    
    /**
     * Checks if the cart is empty.
     * 
     * @return true if cart has no items, false otherwise
     */
    public static boolean isEmpty() {
        return orderDetails.isEmpty();
    }

}
