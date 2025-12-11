package asg;

import asg.StockManagementModule.Controller.StockApp;

/**
 * Legacy-compatible entry for Stock module.
 * Delegates to the refactored StockApp.
 */
public class Stock {
    public static void main(String[] args) {
        StockApp.main(args); 
    }
}
