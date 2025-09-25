package com.avon.mauritius.representative.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductList implements Serializable {

    @SerializedName("id")
    String id;
    @SerializedName("name")
    String name;
    @SerializedName("description")
    String description;
    @SerializedName("product_type")
    String product_type;
    @SerializedName("product_category")
    String product_category;
    @SerializedName("product_code")
    String product_code;
    @SerializedName("price")
    String price;
    @SerializedName("limit")
    String limit;
    @SerializedName("status")
    String status;

    @SerializedName("stock")
    String stock="0";
    @SerializedName("product_source")
    String product_source="";

    @SerializedName("picture")
    String picture;
    @SerializedName("SubProducts")
    ArrayList<SubProducts> allProductItems= new ArrayList<>();

    public String getProduct_source() {
        return product_source;
    }

    public void setProduct_source(String product_source) {
        this.product_source = product_source;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ArrayList<SubProducts> getAllProductItems() {
        return allProductItems;
    }

    public void setAllProductItems(ArrayList<SubProducts> allProductItems) {
        this.allProductItems = allProductItems;
    }
}
