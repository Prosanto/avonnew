package com.avon.mauritius.representative.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductItems implements Serializable {
    @SerializedName("id")
    String id;
    @SerializedName("subproduct_id")
    String subproduct_id;
    @SerializedName("name")
    String name;
    @SerializedName("product_code")
    String product_code;
    @SerializedName("price")
    String price;
    @SerializedName("limit")
    String limit;
    @SerializedName("status")
    String status;
    @SerializedName("picture")
    String picture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubproduct_id() {
        return subproduct_id;
    }

    public void setSubproduct_id(String subproduct_id) {
        this.subproduct_id = subproduct_id;
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
}
