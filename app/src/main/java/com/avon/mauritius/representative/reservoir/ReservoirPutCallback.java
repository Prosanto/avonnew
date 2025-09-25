package com.avon.mauritius.representative.reservoir;

public interface ReservoirPutCallback {
    public void onSuccess();

    public void onFailure(Exception e);
}
