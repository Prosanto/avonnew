package com.avon.mauritius.representative.model;

import java.io.Serializable;

public class SalesDetailYearly implements Serializable {
    private String quarter;
    private int previousYear;
    private int currentYear;
    public SalesDetailYearly(String quarter, int previousYear, int currentYear) {
        this.quarter = quarter;
        this.previousYear = previousYear;
        this.currentYear = currentYear;
    }
    public String getQuarter() { return quarter; }
    public int getPreviousYear() { return previousYear; }
    public int getCurrentYear() { return currentYear; }

}
