package com.CarApp.car.modelclass;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseModel {
    @SerializedName("id")
    int user_id;
    @SerializedName("error")
    boolean error;
    @SerializedName("index")
    String index;
    @SerializedName("phone_no")
    String phone_no;
    @SerializedName("bookingId")
    String bookingid;
    @SerializedName("error_msg")
    String error_msg;
    @SerializedName("data")
    ResponseModel responseModel;
    @SerializedName("busescount")
    String busescount;
    @SerializedName("name_signature")
    String name_signature;
    @SerializedName("drivercount")
    String drivercount;
    @SerializedName("studentcount")
    String studentcount;
    @SerializedName("loginadmin")
    ResponseModel loginadmin;
    @SerializedName("notification_data")
    ArrayList<ResponseModel> notification_dataarraylist;
    @SerializedName("cat_data")
    ArrayList<ResponseModel> arr_cat_data;
    @SerializedName("token_sp")
    String token_sp;
    @SerializedName("customer_name")
    String customer_name;
    @SerializedName("name")
    String sp_cat_name;
    @SerializedName("worker_id")
    String sp_cat_id;
    @SerializedName("currentlat")
    String currentlat;
    @SerializedName("currentlon")
    String currentlon;

    @SerializedName("customer_contact")
    String contact;
    @SerializedName("contact")
    String contactt;
    @SerializedName("profile")
    ArrayList<ResponseModel>arrprofile;
    @SerializedName("city")
    String city;
    @SerializedName("email")
    String email;
    @SerializedName("rating")
    String rating;
    @SerializedName("cat_id")
    String cat_id;
    @SerializedName("carModel")
    ArrayList<carModel> carModels;
    @SerializedName("cars")
    ArrayList<String> carsstring;

    public ArrayList<String> getCarsstring() {
        return carsstring;
    }

    public void setCarsstring(ArrayList<String> carsstring) {
        this.carsstring = carsstring;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getRating() {
        return rating;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getContactt() {
        return contactt;
    }

    public ArrayList<ResponseModel> getArrprofile() {
        return arrprofile;
    }

    public String getCurrentlat() {
        return currentlat;
    }

    public void setCurrentlat(String currentlat) {
        this.currentlat = currentlat;
    }

    public String getCurrentlon() {
        return currentlon;
    }

    public void setCurrentlon(String currentlon) {
        this.currentlon = currentlon;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSp_cat_id() {
        return sp_cat_id;
    }

    public String getSp_cat_name() {
        return sp_cat_name;
    }

    public void setSp_cat_name(String sp_cat_name) {
        this.sp_cat_name = sp_cat_name;
    }

    public ArrayList<ResponseModel> getArr_cat_data() {
        return arr_cat_data;
    }

    public void setArr_cat_data(ArrayList<ResponseModel> arr_cat_data) {
        this.arr_cat_data = arr_cat_data;
    }

    public String getToken_sp() {
        return token_sp;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public ArrayList<ResponseModel> getNotification_dataarraylist() {
        return notification_dataarraylist;
    }
    public ResponseModel getLoginadmin() {
        return loginadmin;
    }

    public void setLoginadmin(ResponseModel loginadmin) {
        this.loginadmin = loginadmin;
    }

    public String getStudentcount() {
        return studentcount;
    }

    public void setStudentcount(String studentcount) {
        this.studentcount = studentcount;
    }

    public String getDrivercount() {
        return drivercount;
    }

    public void setDrivercount(String drivercount) {
        this.drivercount = drivercount;
    }
    public String getBusescount() {
        return busescount;
    }

    public void setBusescount(String busescount) {
        this.busescount = busescount;
    }


    public ResponseModel getResponseModel() {
        return responseModel;
    }

    public void setResponseModel(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }



    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getName_signature() {
        return name_signature;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getBookingid() {
        return bookingid;
    }

    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    public ArrayList<carModel> getCarModels() {
        return carModels;
    }

    public void setCarModels(ArrayList<carModel> carModels) {
        this.carModels = carModels;
    }
}

