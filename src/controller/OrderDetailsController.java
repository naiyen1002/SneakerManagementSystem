package controller;

import model.OrderDetails;
import model.StockItem;

import java.util.ArrayList;

public class OrderDetailsController {
    private static ArrayList<OrderDetails> orderDetails = new ArrayList<>();
    
    // Add order detail
    public static void addOrderDetail(StockItem item) {
       OrderDetails order = new OrderDetails(
            item.getItemCode(),
            item.getBrand(),
            item.getItemDescription(),
            item.getColour(),
            item.getItemPrice()
        );
        orderDetails.add(order);
    }
    
    // Get all order details
    public static ArrayList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }
    
    // Get order detail by code
    public static OrderDetails getOrderDetailByCode(String orderCode) {
        for (OrderDetails order : orderDetails) {
            if (order.getOrderCode().equalsIgnoreCase(orderCode)) {
                return order;
            }
        }
        return null;
    }
    
    // Remove order detail
    public static boolean removeOrderDetail(String orderCode) {
        return orderDetails.removeIf(order -> 
            order.getOrderCode().equalsIgnoreCase(orderCode));
    }
    
    // Clear all orders
    public static void clearOrderDetails() {
        orderDetails.clear();
    }
    
    // Calculate total
    public static double calculateTotal() {
        return orderDetails.stream()
            .mapToDouble(OrderDetails::getOrderPrice)
            .sum();
    }
    
    // Get order count
    public static int getOrderCount() {
        return orderDetails.size();
    }
}
