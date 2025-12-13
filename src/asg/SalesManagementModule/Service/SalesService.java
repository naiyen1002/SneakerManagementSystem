package asg.SalesManagementModule.Service;

import asg.MemberManagementModule.Model.Member;
import asg.MemberManagementModule.Service.MemberService;
import asg.SalesManagementModule.Model.SalesItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Sales Management business logic.
 * Uses Strategy pattern for flexible search functionality.
 * Uses Dependency Injection for testability.
 * 
 * This class handles:
 * - Business logic and validation
 * - Coordination between DAO and Controller
 * - Search operations using Strategy pattern
 */
public class SalesService {

    private final SalesDAO dao;

    /**
     * Constructor with Dependency Injection.
     * Used for testing with mock DAO.
     * 
     * @param dao The SalesDAO instance to use
     */
    public SalesService(SalesDAO dao) {
        this.dao = dao;
    }

    /**
     * Default constructor using Singleton DAO.
     * Used in production code.
     */
    public SalesService() {
        this(SalesDAO.getInstance());
    }

    // ==================== Business Operations ====================

    /**
     * Gets all sales items.
     * 
     * @return List of all sales items
     */
    public List<SalesItem> getAllSales() {
        return dao.findAll();
    }

    /**
     * Finds a sales item by ID.
     * 
     * @param salesId The sales ID to find
     * @return Optional containing the item if found
     */
    public Optional<SalesItem> findById(String salesId) {
        return dao.findById(salesId);
    }

    /**
     * Adds a new sales item with validation.
     * 
     * @param item The sales item to add
     * @return true if added successfully, false otherwise
     */
    public boolean addSales(SalesItem item) {
        // Validate the item
        if (!item.isValid()) {
            return false;
        }

        // Check for duplicate IDs
        if (dao.existsById(item.getSalesId())) {
            return false;
        }

        boolean saved = dao.save(item);
        if (saved) {
            MemberService memberService = MemberService.getInstance();
            Member member = memberService.findMemberById(item.getMemberId());
            if (member != null) {
                member.addSpending(item.getTotalPrice());
            }
        }
        return saved;
    }

    /**
     * Deletes a sales item by ID.
     * 
     * @param salesId The sales ID to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteSales(String salesId) {
        return dao.delete(salesId);
    }

    /**
     * Updates a sales item.
     * 
     * @param originalSalesId The original sales ID
     * @param updatedItem     The updated item
     * @return true if updated, false otherwise
     */
    public boolean updateSales(String originalSalesId, SalesItem updatedItem) {
        // Validate the updated item
        if (!updatedItem.isValid()) {
            return false;
        }

        // If changing sales ID, check for duplicates
        if (!originalSalesId.equalsIgnoreCase(updatedItem.getSalesId())) {
            if (dao.existsById(updatedItem.getSalesId())) {
                return false;
            }
        }

        return dao.update(originalSalesId, updatedItem);
    }

    // ==================== Search Operations (Strategy Pattern)
    // ====================

    /**
     * Searches for sales items using the Strategy pattern.
     * This is the key method demonstrating polymorphism.
     * 
     * @param strategy    The search strategy to use
     * @param searchValue The value to search for
     * @return List of matching sales items
     */
    public List<SalesItem> search(SearchStrategy strategy, String searchValue) {
        List<SalesItem> results = new ArrayList<>();

        for (SalesItem item : dao.findAll()) {
            if (strategy.matches(item, searchValue)) {
                results.add(item);
            }
        }

        return results;
    }

    // ==================== Validation Methods ====================

    /**
     * Checks if a sales ID is unique (doesn't exist).
     * 
     * @param salesId The sales ID to check
     * @return true if unique, false if already exists
     */
    public boolean isUniqueSalesId(String salesId) {
        return !dao.existsById(salesId);
    }

    /**
     * Checks if an item code is unique (doesn't exist).
     * 
     * @param itemCode The item code to check
     * @return true if unique, false if already exists
     */
    public boolean isUniqueItemCode(String itemCode) {
        return !dao.existsByItemCode(itemCode);
    }

    // ==================== Report Operations ====================

    /**
     * Generates a sales report summary.
     * 
     * @return SalesReport containing totals
     */
    public SalesReport generateReport() {
        List<SalesItem> allSales = dao.findAll();

        int totalItems = allSales.size();
        int totalQuantity = 0;
        double totalAmount = 0.0;

        for (SalesItem item : allSales) {
            totalQuantity += item.getQuantitySales();
            totalAmount += item.getTotalPrice();
        }

        return new SalesReport(totalItems, totalQuantity, totalAmount);
    }

    /**
     * Inner class for sales report data.
     */
    public static class SalesReport {
        private final int totalItems;
        private final int totalQuantity;
        private final double totalAmount;

        public SalesReport(int totalItems, int totalQuantity, double totalAmount) {
            this.totalItems = totalItems;
            this.totalQuantity = totalQuantity;
            this.totalAmount = totalAmount;
        }

        public int getTotalItems() {
            return totalItems;
        }

        public int getTotalQuantity() {
            return totalQuantity;
        }

        public double getTotalAmount() {
            return totalAmount;
        }
    }
}
