package com.avon.mauritius.representative.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class BillSubProducts implements Serializable {
    @SerializedName("id")
    String id;
    @SerializedName("product_id")
    String product_id;
    @SerializedName("name")
    String name;
    @SerializedName("product_code")
    String product_code;

    @SerializedName("ProductItems")
    ArrayList<BillProductItems> billProductItems= new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ArrayList<BillProductItems> getBillProductItems() {
        return billProductItems;
    }

    public void setBillProductItems(ArrayList<BillProductItems> billProductItems) {
        this.billProductItems = billProductItems;
    }
}
