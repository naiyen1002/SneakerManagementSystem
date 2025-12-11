package asg.MakeOrderModule.Model;

/**
 * Base class representing sneaker/shoe details.
 * Contains common attributes shared by all sneaker items in the system.
 */
public class SSdetails {
    protected String itemCode;
    protected String brand;
    protected String itemDescription;
    protected String colour;
    protected double itemPrice;

    public SSdetails(String itemCode, String brand, String itemDescription, String colour, double itemPrice) {
        
        this.itemCode = itemCode;
        this.brand = brand;
        this.itemDescription = itemDescription;
        this.colour = colour;
        this.itemPrice = itemPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return String.format("SSdetails{itemCode='%s', brand='%s', description='%s', colour='%s', price=%.2f}",
                itemCode, brand, itemDescription, colour, itemPrice);
    }
}
