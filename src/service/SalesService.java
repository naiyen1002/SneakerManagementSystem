package service;

import model.Sale;
import java.util.List;

/**
 * Service class for sales-related business logic and calculations.
 * This class handles complex operations such as statistics generation,
 * revenue calculations, and data validation.
 * 
 * @author Sneaker Management System Team
 */
public class SalesService {

    /**
     * Calculates the total revenue from a list of sales.
     * 
     * @param sales The list of sales to calculate revenue from
     * @return The total revenue
     */
    public double calculateTotalRevenue(List<Sale> sales) {
        if (sales == null || sales.isEmpty()) {
            return 0.0;
        }
        return sales.stream()
                   .mapToDouble(Sale::getTotalPrice)
                   .sum();
    }

    /**
     * Calculates the total quantity of items sold.
     * 
     * @param sales The list of sales to calculate from
     * @return The total quantity sold
     */
    public int calculateTotalQuantitySold(List<Sale> sales) {
        if (sales == null || sales.isEmpty()) {
            return 0;
        }
        return sales.stream()
                   .mapToInt(Sale::getQuantitySales)
                   .sum();
    }

    /**
     * Gets comprehensive sales statistics.
     * 
     * @param sales The list of sales to analyze
     * @return A SalesStatistics object containing all statistics
     */
    public SalesStatistics getSalesStatistics(List<Sale> sales) {
        if (sales == null) {
            return new SalesStatistics(0, 0, 0.0);
        }
        
        int totalItems = sales.size();
        int totalQuantity = calculateTotalQuantitySold(sales);
        double totalRevenue = calculateTotalRevenue(sales);
        
        return new SalesStatistics(totalItems, totalQuantity, totalRevenue);
    }

    /**
     * Validates all sale data fields.
     * 
     * @param salesId The sales ID to validate
     * @param memberId The member ID to validate
     * @param itemCode The item code to validate
     * @param brand The brand to validate
     * @param description The description to validate
     * @param colour The colour to validate
     * @param price The price to validate
     * @param quantity The quantity to validate
     * @return A ValidationResult containing validation status and error messages
     */
    public ValidationResult validateSaleData(String salesId, String memberId, String itemCode,
                                            String brand, String description, String colour,
                                            double price, int quantity) {
        StringBuilder errors = new StringBuilder();
        
        if (!Sale.isValidSalesId(salesId)) {
            errors.append("Sales ID must be exactly 4 characters. ");
        }
        if (!Sale.isValidMemberId(memberId)) {
            errors.append("Member ID must be exactly 4 characters. ");
        }
        if (!Sale.isValidItemCode(itemCode)) {
            errors.append("Item Code must be exactly 4 characters. ");
        }
        if (!Sale.isValidBrand(brand)) {
            errors.append("Brand must not be empty and max 9 characters. ");
        }
        if (!Sale.isValidDescription(description)) {
            errors.append("Description must not be empty and max 29 characters. ");
        }
        if (!Sale.isValidColour(colour)) {
            errors.append("Colour must not be empty and max 10 characters. ");
        }
        if (!Sale.isValidPrice(price)) {
            errors.append("Price must be positive and not exceed 99,999,999,999.99. ");
        }
        if (!Sale.isValidQuantity(quantity)) {
            errors.append("Quantity must be positive. ");
        }
        
        boolean isValid = errors.length() == 0;
        return new ValidationResult(isValid, errors.toString().trim());
    }

    /**
     * Formats a sale for display purposes.
     * 
     * @param sale The sale to format
     * @return A formatted string representation
     */
    public String formatSaleForDisplay(Sale sale) {
        if (sale == null) {
            return "";
        }
        return String.format("|  %-6s|  %-7s|  %-7s|%-9s|%-29s|%-10s|%14.2f|%9d|",
                           sale.getSalesId(), sale.getMemberId(), sale.getItemCode(),
                           sale.getBrand(), sale.getItemDescription(), sale.getColour(),
                           sale.getItemPrice(), sale.getQuantitySales());
    }

    /**
     * Inner class to hold sales statistics.
     */
    public static class SalesStatistics {
        private final int totalSalesItems;
        private final int totalQuantitySold;
        private final double totalRevenue;

        public SalesStatistics(int totalSalesItems, int totalQuantitySold, double totalRevenue) {
            this.totalSalesItems = totalSalesItems;
            this.totalQuantitySold = totalQuantitySold;
            this.totalRevenue = totalRevenue;
        }

        public int getTotalSalesItems() {
            return totalSalesItems;
        }

        public int getTotalQuantitySold() {
            return totalQuantitySold;
        }

        public double getTotalRevenue() {
            return totalRevenue;
        }

        @Override
        public String toString() {
            return String.format("List Sales Item      = %d%nTotal Quantity Sales = %d%nSubTotal Sales       = RM%.2f",
                               totalSalesItems, totalQuantitySold, totalRevenue);
        }
    }

    /**
     * Inner class to hold validation results.
     */
    public static class ValidationResult {
        private final boolean valid;
        private final String errorMessage;

        public ValidationResult(boolean valid, String errorMessage) {
            this.valid = valid;
            this.errorMessage = errorMessage;
        }

        public boolean isValid() {
            return valid;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
