/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ginwe
 */
public class StockItem extends SSdetails {
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
    
    @Override
    public String toString() {
        return itemCode + "\t" + itemDescription + "\t" + itemPrice + "\t" + quantityInStock;
    }

}
