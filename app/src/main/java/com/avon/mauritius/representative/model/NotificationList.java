package com.avon.mauritius.representative.model;

import com.google.gson.annotations.SerializedName;

public class NotificationList {
    @SerializedName("id")
    String id;
    @SerializedName("customer_id")
    String customer_id;
    @SerializedName("title")
    String title;
    @SerializedName("messages")
    String messages;
    @SerializedName("date")
    String date;
    @SerializedName("is_read")
    String is_read;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }
}
