package model;

import java.util.ArrayList;

public class OrderDetails {
    private String orderCode;
    private String orderBrand;
    private String orderDescription;
    private String orderColor;
    private double orderPrice; 

    public OrderDetails(String orderCode, String orderBrand, String orderDescription, String orderColor, double orderPrice) {
        this.orderCode = orderCode;
        this.orderBrand = orderBrand;
        this.orderDescription = orderDescription;
        this.orderColor = orderColor;
        this.orderPrice = orderPrice;
    }

   

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderBrand() {
        return orderBrand;
    }

    public void setOrderBrand(String orderBrand) {
        this.orderBrand = orderBrand;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getOrderColor() {
        return orderColor;
    }

    public void setOrderColor(String orderColor) {
        this.orderColor = orderColor;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Override
    public String toString() {
        return "OrderDetails{" + "orderCode=" + orderCode + ", orderBrand=" + orderBrand + ", orderDescription=" + orderDescription + ", orderColor=" + orderColor + ", orderPrice=" + orderPrice + '}';
    }

    
}
