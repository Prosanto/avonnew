package com.avon.mauritius.representative.reservoir;

public interface ReservoirGetCallback<T> {
    public void onSuccess(T object);

    public void onFailure(Exception e);
}
