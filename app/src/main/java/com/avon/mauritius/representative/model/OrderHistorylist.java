package com.avon.mauritius.representative.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderHistorylist implements Serializable {
    @SerializedName("order_date")
    String order_date;
    @SerializedName("products")
    ArrayList<OrderHistoryProduct> orderHistoryProducts= new ArrayList<>();

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public ArrayList<OrderHistoryProduct> getOrderHistoryProducts() {
        return orderHistoryProducts;
    }

    public void setOrderHistoryProducts(ArrayList<OrderHistoryProduct> orderHistoryProducts) {
        this.orderHistoryProducts = orderHistoryProducts;
    }
}
