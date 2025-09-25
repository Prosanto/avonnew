package com.avon.mauritius.representative.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BillProductItems implements Serializable {
    @SerializedName("id")
    String id;
    @SerializedName("subproduct_id")
    String subproduct_id;
    @SerializedName("name")
    String name;
    @SerializedName("product_code")
    String product_code;

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
}
