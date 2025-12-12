package asg.StockManagementModule.Model;

public class StockItem extends Item {

    private int quantityInStock;

    public StockItem(String itemCode, String brand, String itemDescription, String colour,
            double itemPrice, int quantityInStock) {
        super(itemCode, brand, itemDescription, colour, itemPrice);
        this.quantityInStock = quantityInStock;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}
