package com.avon.mauritius.representative.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class BillProductList implements Serializable {

    @SerializedName("id")
    String id;
    @SerializedName("order_id")
    String order_id;
    @SerializedName("customer_id")
    String customer_id;
    @SerializedName("product_id")
    String product_id;
    @SerializedName("name")
    String name;
    @SerializedName("product_code")
    String product_code;
    @SerializedName("product_type")
    String product_type;
    @SerializedName("product_category")
    String product_category;
    @SerializedName("price")
    String price;
    @SerializedName("qty")
    String qty;
    @SerializedName("product_quote")
    String product_quote;
    @SerializedName("order_date")
    String order_date;
    @SerializedName("SubProducts")
    ArrayList<BillSubProducts> billSubProducts= new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getProduct_quote() {
        return product_quote;
    }

    public void setProduct_quote(String product_quote) {
        this.product_quote = product_quote;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public ArrayList<BillSubProducts> getBillSubProducts() {
        return billSubProducts;
    }

    public void setBillSubProducts(ArrayList<BillSubProducts> billSubProducts) {
        this.billSubProducts = billSubProducts;
    }
}
