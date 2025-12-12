package asg.MakeOrderModule.Model;

/**
 * Represents a stock item in the inventory.
 * Extends SSdetails with quantity tracking functionality.
 */
public class StockItem extends ShoesDetails {
    private int quantityInStock;


    public StockItem(String itemCode, String brand, String itemDescription, String colour, double itemPrice, int quantityInStock) {
        super(itemCode, brand, itemDescription, colour, itemPrice);

        this.quantityInStock = quantityInStock;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {

        this.quantityInStock = quantityInStock;
    }
    

    public boolean isInStock() {
        return quantityInStock > 0;
    }
    
    
}
