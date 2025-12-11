package asg.MakeOrderModule.View;

import java.util.List;
import asg.MakeOrderModule.Model.OrderDetails;

/**
 * Utility class for printing receipts.
 * Handles receipt formatting and display for checkout.
 */
public class ReceiptPrinter {
    
    /**
     * Prints a formatted receipt for the given orders.
     * 
     * @param orders List of order details to print on the receipt
     */
    public static void printReceipt(List<OrderDetails> orders) {
        if (orders.isEmpty()) {
            System.out.println("\nNo items to print on receipt.");
            return;
        }
        
        double subTotal = 0;
        int qtyOrder = 0;

        System.out.printf("\n=========================$ $ $=========================");
        System.out.printf("\n                        RECEIPT              ");
        System.out.printf("\n=========================$ $ $=========================\n");
        System.out.printf("\n%-8s %-23s %-20s %-12s", "Brands", "Item Description", "Color", "Price/Unit");
        System.out.printf("\n%-8s %-23s %-20s %-12s\n", "------", "--------------------", "-----------------", "----------");
        
        for (OrderDetails order : orders) {
            System.out.printf("%-8s %-23s %-20s RM %-12.2f\n", 
                    order.getOrderBrand(), 
                    order.getOrderDescription(),
                    order.getOrderColor(), 
                    order.getOrderPrice());
            subTotal += order.getOrderPrice();
            qtyOrder++;
        }
        
        System.out.printf("\n\nTOTAL QUANTITY ITEM(S) --> %d", qtyOrder);
        System.out.printf("\nSUB TOTAL --> RM %.2f\n", subTotal);
    }
  
}
