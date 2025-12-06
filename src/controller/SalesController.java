package controller;

import model.Sale;
import service.SalesService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for managing sales operations.
 * This class handles all CRUD operations, search functionality,
 * and coordinates between the model and view layers.
 * 
 * @author Sneaker Management System Team
 */
public class SalesController {
    private final List<Sale> salesList;
    private final SalesService salesService;

    /**
     * Constructs a new SalesController with an empty sales list.
     */
    public SalesController() {
        this.salesList = new ArrayList<>();
        this.salesService = new SalesService();
        initializeDefaultSales();
    }

    /**
     * Initializes the controller with default sales data.
     * This maintains backward compatibility with the original system.
     */
    private void initializeDefaultSales() {
        salesList.add(new Sale("S001", "M101", "I001", "NIKE", "First Running Shoes", "Black", 79.98, 1));
        salesList.add(new Sale("S002", "M102", "I002", "PUMA", "Athletic Sneakers", "White", 89.99, 9));
        salesList.add(new Sale("S003", "M103", "I003", "ADIDAS", "Casual Sneakers", "Grey", 99.97, 3));
        salesList.add(new Sale("S004", "M104", "I004", "PUMA", "Athletic Sneakers", "White", 89.49, 7));
        salesList.add(new Sale("S005", "M105", "I005", "PUMA", "Athletic Sneakers", "White", 89.79, 5));
    }

    // CRUD Operations

    /**
     * Adds a new sale to the system.
     * 
     * @param sale The sale to add
     * @return true if added successfully, false if sale is invalid or duplicate
     */
    public boolean addSale(Sale sale) {
        if (sale == null || !sale.isValid()) {
            return false;
        }
        if (!isSalesIdUnique(sale.getSalesId())) {
            return false;
        }
        return salesList.add(sale);
    }

    /**
     * Deletes a sale by its sales ID.
     * 
     * @param salesId The sales ID of the sale to delete
     * @return true if deleted successfully, false if not found
     */
    public boolean deleteSale(String salesId) {
        return salesList.removeIf(sale -> sale.getSalesId().equals(salesId));
    }

    /**
     * Updates an existing sale.
     * 
     * @param salesId The sales ID of the sale to update
     * @param updatedSale The new sale data
     * @return true if updated successfully, false if not found or invalid
     */
    public boolean updateSale(String salesId, Sale updatedSale) {
        if (updatedSale == null || !updatedSale.isValid()) {
            return false;
        }
        
        for (int i = 0; i < salesList.size(); i++) {
            if (salesList.get(i).getSalesId().equals(salesId)) {
                salesList.set(i, updatedSale);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a sale by its sales ID.
     * 
     * @param salesId The sales ID to search for
     * @return The sale if found, null otherwise
     */
    public Sale getSaleById(String salesId) {
        return salesList.stream()
                       .filter(sale -> sale.getSalesId().equals(salesId))
                       .findFirst()
                       .orElse(null);
    }

    /**
     * Gets all sales in the system.
     * 
     * @return A copy of the sales list
     */
    public List<Sale> getAllSales() {
        return new ArrayList<>(salesList);
    }

    // Search Operations

    /**
     * Searches for sales by sales ID.
     * 
     * @param salesId The sales ID to search for
     * @return List of matching sales
     */
    public List<Sale> searchBySalesId(String salesId) {
        return salesList.stream()
                       .filter(sale -> sale.getSalesId().equalsIgnoreCase(salesId))
                       .collect(Collectors.toList());
    }

    /**
     * Searches for sales by member ID.
     * 
     * @param memberId The member ID to search for
     * @return List of matching sales
     */
    public List<Sale> searchByMemberId(String memberId) {
        return salesList.stream()
                       .filter(sale -> sale.getMemberId().equalsIgnoreCase(memberId))
                       .collect(Collectors.toList());
    }

    /**
     * Searches for sales by item code.
     * 
     * @param itemCode The item code to search for
     * @return List of matching sales
     */
    public List<Sale> searchByItemCode(String itemCode) {
        return salesList.stream()
                       .filter(sale -> sale.getItemCode().equalsIgnoreCase(itemCode))
                       .collect(Collectors.toList());
    }

    /**
     * Searches for sales by brand.
     * 
     * @param brand The brand to search for
     * @return List of matching sales
     */
    public List<Sale> searchByBrand(String brand) {
        return salesList.stream()
                       .filter(sale -> sale.getBrand().equalsIgnoreCase(brand))
                       .collect(Collectors.toList());
    }

    /**
     * Searches for sales by item description.
     * 
     * @param description The description to search for
     * @return List of matching sales
     */
    public List<Sale> searchByDescription(String description) {
        return salesList.stream()
                       .filter(sale -> sale.getItemDescription().toLowerCase()
                                          .contains(description.toLowerCase()))
                       .collect(Collectors.toList());
    }

    /**
     * Searches for sales by colour.
     * 
     * @param colour The colour to search for
     * @return List of matching sales
     */
    public List<Sale> searchByColour(String colour) {
        return salesList.stream()
                       .filter(sale -> sale.getColour().equalsIgnoreCase(colour))
                       .collect(Collectors.toList());
    }

    /**
     * Searches for sales by item price.
     * 
     * @param price The price to search for
     * @return List of matching sales
     */
    public List<Sale> searchByPrice(double price) {
        return salesList.stream()
                       .filter(sale -> Math.abs(sale.getItemPrice() - price) < 0.01)
                       .collect(Collectors.toList());
    }

    /**
     * Searches for sales by quantity.
     * 
     * @param quantity The quantity to search for
     * @return List of matching sales
     */
    public List<Sale> searchByQuantity(int quantity) {
        return salesList.stream()
                       .filter(sale -> sale.getQuantitySales() == quantity)
                       .collect(Collectors.toList());
    }

    // Report and Statistics

    /**
     * Generates sales statistics report.
     * 
     * @return SalesStatistics object containing report data
     */
    public SalesService.SalesStatistics generateSalesReport() {
        return salesService.getSalesStatistics(salesList);
    }

    // Validation Methods

    /**
     * Checks if a sales ID is unique.
     * 
     * @param salesId The sales ID to check
     * @return true if unique, false if already exists
     */
    public boolean isSalesIdUnique(String salesId) {
        return salesList.stream()
                       .noneMatch(sale -> sale.getSalesId().equals(salesId));
    }

    /**
     * Checks if a member ID is unique.
     * 
     * @param memberId The member ID to check
     * @return true if unique, false if already exists
     */
    public boolean isMemberIdUnique(String memberId) {
        return salesList.stream()
                       .noneMatch(sale -> sale.getMemberId().equals(memberId));
    }

    /**
     * Checks if an item code is unique.
     * 
     * @param itemCode The item code to check
     * @return true if unique, false if already exists
     */
    public boolean isItemCodeUnique(String itemCode) {
        return salesList.stream()
                       .noneMatch(sale -> sale.getItemCode().equals(itemCode));
    }

    /**
     * Validates a sale object.
     * 
     * @param sale The sale to validate
     * @return true if valid, false otherwise
     */
    public boolean validateSale(Sale sale) {
        return sale != null && sale.isValid();
    }

    /**
     * Clears all sales from the system.
     */
    public void clearAllSales() {
        salesList.clear();
    }

    /**
     * Gets the total number of sales.
     * 
     * @return The count of sales
     */
    public int getSalesCount() {
        return salesList.size();
    }

    /**
     * Gets the sales service instance.
     * 
     * @return The sales service
     */
    public SalesService getSalesService() {
        return salesService;
    }
}
