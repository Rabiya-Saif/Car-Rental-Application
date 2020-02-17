package com.CarApp.car.retrofit;


import com.CarApp.car.modelclass.ResponseModel;
import com.CarApp.car.modelclass.ServerResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("/api/login")
    Call<ResponseModel> login(@Query("email") String email,
                              @Query("password") String password
                              );
    @POST("/api/booking/number")
    Call<ResponseModel> saveData(@Query("userId") String id,
                              @Query("number") String number
    );
    @Multipart
    @POST("/api/booking/image")
    Call<ServerResponse> uploadFile(@Part MultipartBody.Part file,
                                    @Part("file") RequestBody name,
                                    @Query("name") String imagename,
                                    @Query("bookingId") String bookingId
                                    );
    @POST("/api/booking/complain")
    Call<ResponseModel> saveComplain(@Query("bookingId") String bookingId,
                                 @Query("complain") String complain,
                                 @Query("tag") String tag
    );
    @POST("/api/booking/tyre")
    Call<ResponseModel> saveTyreComplain(
                                    @Query("bookingId") String bookingId,
                                    @Query("meterReading") String meterReading,
                                    @Query("wheelComment") String comment,
                                    @Query("fRWheel") int fRWhell,
                                    @Query("fLWheel") int fLWhell,
                                    @Query("bRWheel") int bRWhell,
                                    @Query("bLWheel") int bLWhell
    );
    @POST("/api/bookings")
    Call<ResponseModel> fetchUserData(@Query("userId") String id
    );

    @POST("/api/cars")
    Call<ResponseModel> fetccars();
}
