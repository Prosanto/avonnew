package com.avon.mauritius.representative.reservoir;

public interface ReservoirClearCallback {
    public void onSuccess();

    public void onFailure(Exception e);
}
