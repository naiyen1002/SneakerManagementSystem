package asg.SalesManagementModule.Service;

import asg.SalesManagementModule.Model.SalesItem;

/**
 * Contains all search strategy implementations as inner classes.
 * Strategy Pattern - defines a family of algorithms for searching sales items.
 * 
 * This pattern allows:
 * - Open/Closed Principle: Add new search strategies without modifying existing
 * code
 * - Single Responsibility: Each inner class handles one search type
 * - Polymorphism: Different behaviors through the same interface
 */
public class SearchStrategies {

    /**
     * Strategy for searching by Sales ID.
     */
    public static class BySalesId implements SearchStrategy {
        @Override
        public boolean matches(SalesItem item, String searchValue) {
            return item.getSalesId().equalsIgnoreCase(searchValue);
        }

        @Override
        public String getFieldName() {
            return "Sales ID";
        }
    }

    /**
     * Strategy for searching by Member ID.
     */
    public static class ByMemberId implements SearchStrategy {
        @Override
        public boolean matches(SalesItem item, String searchValue) {
            return item.getMemberId().equalsIgnoreCase(searchValue);
        }

        @Override
        public String getFieldName() {
            return "Member ID";
        }
    }

    /**
     * Strategy for searching by Item Code.
     */
    public static class ByItemCode implements SearchStrategy {
        @Override
        public boolean matches(SalesItem item, String searchValue) {
            return item.getItemCode().equalsIgnoreCase(searchValue);
        }

        @Override
        public String getFieldName() {
            return "Item Code";
        }
    }

    /**
     * Strategy for searching by Brand.
     */
    public static class ByBrand implements SearchStrategy {
        @Override
        public boolean matches(SalesItem item, String searchValue) {
            return item.getBrand().equalsIgnoreCase(searchValue);
        }

        @Override
        public String getFieldName() {
            return "Brand";
        }
    }

    /**
     * Strategy for searching by Item Description.
     * Uses contains() for partial matching.
     */
    public static class ByDescription implements SearchStrategy {
        @Override
        public boolean matches(SalesItem item, String searchValue) {
            return item.getItemDescription().toLowerCase().contains(searchValue.toLowerCase());
        }

        @Override
        public String getFieldName() {
            return "Item Description";
        }
    }

    /**
     * Strategy for searching by Colour.
     */
    public static class ByColour implements SearchStrategy {
        @Override
        public boolean matches(SalesItem item, String searchValue) {
            return item.getColour().equalsIgnoreCase(searchValue);
        }

        @Override
        public String getFieldName() {
            return "Colour";
        }
    }

    /**
     * Strategy for searching by Item Price.
     */
    public static class ByPrice implements SearchStrategy {
        @Override
        public boolean matches(SalesItem item, String searchValue) {
            try {
                double targetPrice = Double.parseDouble(searchValue);
                return item.getItemPrice() == targetPrice;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public String getFieldName() {
            return "Item Price";
        }
    }

    /**
     * Strategy for searching by Quantity Sales.
     */
    public static class ByQuantity implements SearchStrategy {
        @Override
        public boolean matches(SalesItem item, String searchValue) {
            try {
                int targetQuantity = Integer.parseInt(searchValue);
                return item.getQuantitySales() == targetQuantity;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public String getFieldName() {
            return "Quantity Sales";
        }
    }
}
