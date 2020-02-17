package com.CarApp.car.modelclass;

import com.google.gson.annotations.SerializedName;

public class carModel {
    @SerializedName("name")
    String name;
    @SerializedName("fLWheel")
    int fLWheel;
    @SerializedName("fRWheel")
    int fRWheel;
    @SerializedName("bLWheel")
    int bLWheel;
    @SerializedName("bRWheel")
    int bRWheel;
    @SerializedName("gasInspection")
    int gasInspection;
    @SerializedName("policeInspection")
    int policeInspection;
    @SerializedName("problem")
    int problem;
    @SerializedName("created_at")
    String date;
    @SerializedName("car")
    numberModel car;


    public numberModel getCar() {
        return car;
    }

    public int getfLWheel() {
        return fLWheel;
    }

    public void setfLWheel(int fLWheel) {
        this.fLWheel = fLWheel;
    }

    public int getfRWheel() {
        return fRWheel;
    }

    public void setfRWheel(int fRWheel) {
        this.fRWheel = fRWheel;
    }

    public int getbLWheel() {
        return bLWheel;
    }

    public void setbLWheel(int bLWheel) {
        this.bLWheel = bLWheel;
    }

    public int getbRWheel() {
        return bRWheel;
    }

    public void setbRWheel(int bRWheel) {
        this.bRWheel = bRWheel;
    }

    public int getGasInspection() {
        return gasInspection;
    }

    public void setGasInspection(int gasInspection) {
        this.gasInspection = gasInspection;
    }

    public int getPoliceInspection() {
        return policeInspection;
    }

    public void setPoliceInspection(int policeInspection) {
        this.policeInspection = policeInspection;
    }

    public int getProblem() {
        return problem;
    }

    public void setProblem(int problem) {
        this.problem = problem;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCar(numberModel car) {
        this.car = car;
    }
}
