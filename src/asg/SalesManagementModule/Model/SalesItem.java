package asg.SalesManagementModule.Model;

import java.util.Objects;

/**
 * Represents a sale transaction in the sneaker management system.
 * This class encapsulates all information related to a single sale including
 * the sale ID, member ID, and quantity sold, along with inherited item details.
 * 
 * @author Sneaker Management System Team
 */
public class SalesItem extends Product {
    private String salesId;
    private String memberId;
    private int quantitySales;

    /**
     * Constructs a new Sale with the specified details.
     * 
     * @param salesId         The unique identifier for this sale (must be 4
     *                        characters)
     * @param memberId        The member ID associated with this sale (must be 4
     *                        characters)
     * @param itemCode        The item code of the product sold
     * @param itemBrand       The brand of the product
     * @param itemDescription The description of the product
     * @param itemColor       The color of the product
     * @param itemPrice       The price per unit of the product
     * @param quantitySales   The quantity of items sold
     */
    public SalesItem(String salesId, String memberId, String itemCode, String itemBrand,
            String itemDescription, String itemColor, double itemPrice, int quantitySales) {
        super(itemCode, itemBrand, itemDescription, itemColor, itemPrice);
        this.salesId = salesId;
        this.memberId = memberId;
        this.quantitySales = quantitySales;
    }

    // Getters and Setters

    /**
     * Gets the sales ID.
     * 
     * @return The sales ID
     */
    public String getSalesId() {
        return salesId;
    }

    /**
     * Sets the sales ID.
     * 
     * @param salesId The sales ID to set
     */
    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    /**
     * Gets the member ID.
     * 
     * @return The member ID
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * Sets the member ID.
     * 
     * @param memberId The member ID to set
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * Gets the quantity of items sold.
     * 
     * @return The quantity sold
     */
    public int getQuantitySales() {
        return quantitySales;
    }

    /**
     * Sets the quantity of items sold.
     * 
     * @param quantitySales The quantity to set
     */
    public void setQuantitySales(int quantitySales) {
        this.quantitySales = quantitySales;
    }

    // Validation Methods

    /**
     * Validates if the sales ID is in the correct format.
     * Sales ID must be exactly 4 characters long and not null or empty.
     * 
     * @param salesId The sales ID to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidSalesId(String salesId) {
        return salesId != null && salesId.length() == 4 && !salesId.trim().isEmpty();
    }

    /**
     * Validates if the member ID is in the correct format.
     * Member ID must be exactly 4 characters long and not null or empty.
     * 
     * @param memberId The member ID to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidMemberId(String memberId) {
        return memberId != null && memberId.length() == 4 && !memberId.trim().isEmpty();
    }

    /**
     * Validates if the item code is in the correct format.
     * Item code must be exactly 4 characters long and not null or empty.
     * 
     * @param itemCode The item code to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidItemCode(String itemCode) {
        return itemCode != null && itemCode.length() == 4 && !itemCode.trim().isEmpty();
    }

    /**
     * Validates if the brand name is in the correct format.
     * Brand must not be null, not empty, and not exceed 9 characters.
     * 
     * @param brand The brand to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidBrand(String brand) {
        return brand != null && !brand.trim().isEmpty() && brand.length() <= 9;
    }

    /**
     * Validates if the item description is in the correct format.
     * Description must not be null, not empty, and not exceed 29 characters.
     * 
     * @param description The description to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDescription(String description) {
        return description != null && !description.trim().isEmpty() && description.length() <= 29;
    }

    /**
     * Validates if the color is in the correct format.
     * Color must not be null, not empty, and not exceed 10 characters.
     * 
     * @param colour The color to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidColour(String colour) {
        return colour != null && !colour.trim().isEmpty() && colour.length() <= 10;
    }

    /**
     * Validates if the price is in the correct range.
     * Price must be positive and not exceed 99,999,999,999.99.
     * 
     * @param price The price to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPrice(double price) {
        return price > 0 && price <= 99999999999.99;
    }

    /**
     * Validates if the quantity is valid.
     * Quantity must be a positive integer.
     * 
     * @param quantity The quantity to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidQuantity(int quantity) {
        return quantity > 0;
    }

    /**
     * Validates all fields of this sale object.
     * 
     * @return true if all fields are valid, false otherwise
     */
    public boolean isValid() {
        return isValidSalesId(this.salesId) &&
                isValidMemberId(this.memberId) &&
                isValidItemCode(this.itemCode) &&
                isValidBrand(this.brand) &&
                isValidDescription(this.itemDescription) &&
                isValidColour(this.colour) &&
                isValidPrice(this.itemPrice) &&
                isValidQuantity(this.quantitySales);
    }

    /**
     * Calculates the total price for this sale.
     * 
     * @return The total price (item price * quantity)
     */
    public double getTotalPrice() {
        return itemPrice * quantitySales;
    }

    // Object Methods

    /**
     * Compares this sale with another object for equality.
     * Two sales are equal if they have the same sales ID.
     * 
     * @param o The object to compare with
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SalesItem sale = (SalesItem) o;
        return Objects.equals(salesId, sale.salesId);
    }

    /**
     * Generates a hash code for this sale.
     * 
     * @return The hash code based on sales ID
     */
    @Override
    public int hashCode() {
        return Objects.hash(salesId);
    }

    /**
     * Returns a string representation of this sale.
     * 
     * @return A formatted string containing all sale details
     */
    @Override
    public String toString() {
        return String.format("Sale{salesId='%s', memberId='%s', itemCode='%s', brand='%s', " +
                "description='%s', colour='%s', price=%.2f, quantity=%d, total=%.2f}",
                salesId, memberId, itemCode, brand, itemDescription,
                colour, itemPrice, quantitySales, getTotalPrice());
    }
}
