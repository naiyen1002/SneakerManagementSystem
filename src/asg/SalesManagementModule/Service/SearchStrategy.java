package asg.SalesManagementModule.Service;

import asg.SalesManagementModule.Model.SalesItem;

/**
 * Strategy Pattern Interface for search functionality.
 * Defines a family of algorithms for searching sales items.
 * 
 * This pattern allows:
 * - Open/Closed Principle: Add new search strategies without modifying existing
 * code
 * - Single Responsibility: Each strategy class handles one search type
 * - Polymorphism: Different behaviors through the same interface
 */
public interface SearchStrategy {

    /**
     * Checks if the given sales item matches the search criteria.
     * 
     * @param item        The sales item to check
     * @param searchValue The value to search for
     * @return true if the item matches the search criteria, false otherwise
     */
    boolean matches(SalesItem item, String searchValue);

    /**
     * Gets the field name this strategy searches by.
     * Used for displaying search prompts to the user.
     * 
     * @return The name of the field being searched
     */
    String getFieldName();
}
