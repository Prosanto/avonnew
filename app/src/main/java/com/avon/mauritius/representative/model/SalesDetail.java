package com.avon.mauritius.representative.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SalesDetail implements Serializable {
    private String quarter;
    private int previousYear;
    private int currentYear;
    public SalesDetail(String quarter, int previousYear, int currentYear) {
        this.quarter = quarter;
        this.previousYear = previousYear;
        this.currentYear = currentYear;
    }
    public String getQuarter() { return quarter; }
    public int getPreviousYear() { return previousYear; }
    public int getCurrentYear() { return currentYear; }

}
