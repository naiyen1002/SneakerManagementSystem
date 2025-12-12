package asg.SalesManagementModule.Service;

import asg.SalesManagementModule.Constants.SalesData;
import asg.SalesManagementModule.Model.SalesItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) for Sales Management.
 * Implements Singleton Pattern to ensure only one instance manages all data.
 * 
 * Singleton Pattern provides:
 * - Single source of truth for sales data
 * - Global access point via getInstance()
 * - Controlled data access through defined methods
 * 
 * Supports Dependency Injection for testability.
 */
public class SalesDAO {
    
    // Singleton instance
    private static SalesDAO instance;
    
    // Data storage
    private List<SalesItem> salesDetails;
    
    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the data with sample data.
     */
    private SalesDAO() {
        salesDetails = new ArrayList<>(SalesData.initializeSalesData());
    }
    
    /**
     * Gets the singleton instance of SalesDAO.
     * Uses lazy initialization - creates instance on first call.
     * 
     * @return The singleton SalesDAO instance
     */
    public static SalesDAO getInstance() {
        if (instance == null) {
            instance = new SalesDAO();
        }
        return instance;
    }
    
    /**
     * Resets the singleton instance (useful for testing).
     */
    public static void resetInstance() {
        instance = null;
    }
    
    // ==================== CRUD Operations ====================
    
    /**
     * Retrieves all sales items.
     * 
     * @return A copy of the sales list to prevent external modification
     */
    public List<SalesItem> findAll() {
        return new ArrayList<>(salesDetails);
    }
    
    /**
     * Finds a sales item by its Sales ID.
     * 
     * @param salesId The sales ID to search for
     * @return Optional containing the item if found, empty otherwise
     */
    public Optional<SalesItem> findById(String salesId) {
        for (SalesItem item : salesDetails) {
            if (item.getSalesId().equalsIgnoreCase(salesId)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }
    
    /**
     * Checks if a sales ID already exists.
     * 
     * @param salesId The sales ID to check
     * @return true if exists, false otherwise
     */
    public boolean existsById(String salesId) {
        return findById(salesId).isPresent();
    }
    
    /**
     * Checks if a member ID already exists.
     * 
     * @param memberId The member ID to check
     * @return true if exists, false otherwise
     */
    public boolean existsByMemberId(String memberId) {
        for (SalesItem item : salesDetails) {
            if (item.getMemberId().equalsIgnoreCase(memberId)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if an item code already exists.
     * 
     * @param itemCode The item code to check
     * @return true if exists, false otherwise
     */
    public boolean existsByItemCode(String itemCode) {
        for (SalesItem item : salesDetails) {
            if (item.getItemCode().equalsIgnoreCase(itemCode)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Saves a new sales item.
     * 
     * @param item The sales item to save
     * @return true if saved successfully, false if ID already exists
     */
    public boolean save(SalesItem item) {
        if (existsById(item.getSalesId())) {
            return false;
        }
        salesDetails.add(item);
        return true;
    }
    
    /**
     * Deletes a sales item by its Sales ID.
     * 
     * @param salesId The sales ID of the item to delete
     * @return true if deleted, false if not found
     */
    public boolean delete(String salesId) {
        return salesDetails.removeIf(item -> item.getSalesId().equalsIgnoreCase(salesId));
    }
    
    /**
     * Updates an existing sales item.
     * 
     * @param salesId The original sales ID of the item to update
     * @param updatedItem The updated sales item
     * @return true if updated, false if not found
     */
    public boolean update(String salesId, SalesItem updatedItem) {
        for (int i = 0; i < salesDetails.size(); i++) {
            if (salesDetails.get(i).getSalesId().equalsIgnoreCase(salesId)) {
                salesDetails.set(i, updatedItem);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the count of all sales items.
     * 
     * @return The number of sales items
     */
    public int count() {
        return salesDetails.size();
    }
    
    /**
     * Clears all sales data (useful for testing).
     */
    public void clearAll() {
        salesDetails.clear();
    }
    
    /**
     * Reloads the initial sample data.
     */
    public void reloadData() {
        salesDetails = new ArrayList<>(SalesData.initializeSalesData());
    }
}
