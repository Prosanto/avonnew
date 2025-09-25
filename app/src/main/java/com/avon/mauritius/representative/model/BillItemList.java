package com.avon.mauritius.representative.model;

import com.google.gson.annotations.SerializedName;

public class BillItemList {
    @SerializedName("id")
    String id;
    @SerializedName("customer_id")
    String customer_id;
    @SerializedName("order_divistion")
    String order_divistion;
    @SerializedName("amount")
    String amount;
    @SerializedName("order_date")
    String order_date;
    @SerializedName("order_date_time")
    String order_date_time;

    @SerializedName("order_unique_id")
    String order_unique_id;

    public String getOrder_unique_id() {
        return order_unique_id;
    }

    public void setOrder_unique_id(String order_unique_id) {
        this.order_unique_id = order_unique_id;
    }

    public String getOrder_date_time() {
        return order_date_time;
    }

    public void setOrder_date_time(String order_date_time) {
        this.order_date_time = order_date_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getOrder_divistion() {
        return order_divistion;
    }

    public void setOrder_divistion(String order_divistion) {
        this.order_divistion = order_divistion;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
}
