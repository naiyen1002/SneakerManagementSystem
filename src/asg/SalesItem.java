/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asg;

import model.SSdetails;

/**
 *
 * @author ginwe
 */
public class SalesItem extends SSdetails {
    protected String salesId;
    protected String memberId;
    protected int quantitySales;

    public SalesItem(String salesId, String memberId, String itemCode, String itemBrand, String itemDescription, String itemColor, double itemPrice, int quantitySales) {
        super( itemCode, itemBrand, itemDescription, itemColor, itemPrice);
        this.salesId = salesId;
        this.memberId = memberId;
        this.quantitySales = quantitySales;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getQuantitySales() {
        return quantitySales;
    }

    public void setQuantitySales(int quantitySales) {
        this.quantitySales = quantitySales;
    }
    
}
