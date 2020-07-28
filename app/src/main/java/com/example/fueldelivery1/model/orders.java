package com.example.fueldelivery1.model;

public class orders {

    Integer id;
    String orderType;
    String orderTime;
    Float Price;



    public orders() {
    }

    public orders(Integer id,String orderType,String orderTime,Float price) {
        this.id = id;
        this.orderType = orderType;
        this.orderTime = orderTime;
        this.Price = price;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
