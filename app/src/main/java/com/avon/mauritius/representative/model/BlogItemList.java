package com.avon.mauritius.representative.model;

import com.google.gson.annotations.SerializedName;

public class BlogItemList {

    @SerializedName("id")
    String id;
    @SerializedName("title")
    String title;
    @SerializedName("short_descrition")
    String short_descrition;
    @SerializedName("long_descrition")
    String long_descrition;
    @SerializedName("image")
    String image;
    @SerializedName("title_french")
    String title_french;
    @SerializedName("short_descrition_french")
    String short_descrition_french;
    @SerializedName("long_descrition_french")
    String long_descrition_french;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("updated_at")
    String updated_at;
    @SerializedName("document")
    String document;

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getTitle_french() {
        return title_french;
    }

    public void setTitle_french(String title_french) {
        this.title_french = title_french;
    }

    public String getShort_descrition_french() {
        return short_descrition_french;
    }

    public void setShort_descrition_french(String short_descrition_french) {
        this.short_descrition_french = short_descrition_french;
    }

    public String getLong_descrition_french() {
        return long_descrition_french;
    }

    public void setLong_descrition_french(String long_descrition_french) {
        this.long_descrition_french = long_descrition_french;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_descrition() {
        return short_descrition;
    }

    public void setShort_descrition(String short_descrition) {
        this.short_descrition = short_descrition;
    }

    public String getLong_descrition() {
        return long_descrition;
    }

    public void setLong_descrition(String long_descrition) {
        this.long_descrition = long_descrition;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
