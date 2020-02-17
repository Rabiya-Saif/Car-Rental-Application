package com.CarApp.car.modelclass;

import com.google.gson.annotations.SerializedName;

public class numberModel {

    @SerializedName("number")
    String carnumber;

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }
}
