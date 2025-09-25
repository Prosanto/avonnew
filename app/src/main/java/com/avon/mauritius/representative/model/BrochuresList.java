package com.avon.mauritius.representative.model;

import com.google.gson.annotations.SerializedName;

public class BrochuresList {
    @SerializedName("id")
    String id;
    @SerializedName("title")
    String title;
    @SerializedName("title_french")
    String title_french;
    @SerializedName("brochurefile")
    String brochurefile;

    public String getTitle_french() {
        return title_french;
    }

    public void setTitle_french(String title_french) {
        this.title_french = title_french;
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

    public String getBrochurefile() {
        return brochurefile;
    }

    public void setBrochurefile(String brochurefile) {
        this.brochurefile = brochurefile;
    }
}
