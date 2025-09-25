package com.avon.mauritius.representative.model;

import com.google.gson.annotations.SerializedName;

public class VideoItemList {

    @SerializedName("id")
    String id;
    @SerializedName("title_en")
    String title_en;
    @SerializedName("title_fr")
    String title_fr;
    @SerializedName("youtube_link")
    String youtube_link;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("updated_at")
    String updated_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getTitle_fr() {
        return title_fr;
    }

    public void setTitle_fr(String title_fr) {
        this.title_fr = title_fr;
    }

    public String getYoutube_link() {
        return youtube_link;
    }

    public void setYoutube_link(String youtube_link) {
        this.youtube_link = youtube_link;
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
