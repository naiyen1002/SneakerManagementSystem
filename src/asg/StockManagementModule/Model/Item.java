package asg.StockManagementModule.Model;

/**
 * Basic item information.
 */
public class Item {

    private String itemCode;
    private String brand;
    private String itemDescription;
    private String colour;
    private double itemPrice;

    public Item(String itemCode, String brand, String itemDescription, String colour, double itemPrice) {
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
}
