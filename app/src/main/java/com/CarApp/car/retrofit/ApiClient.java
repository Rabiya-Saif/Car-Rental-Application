package com.CarApp.car.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static String IP="192.168.10.14";
//    private static String BASE_URL = "http://"+IP+"/NGO/";
//    private static String BASE_URL = "https://"+IP+"/quickfix/";
//    private static String BASE_URL = "http://rider.usman.live/quickfix/";
//http://car.triteckodes.store/api/
    private static String BASE_URL= "http://taxi.tritecknodes.store/";
//    private static String BASE_URL= "http://192.168.10.18/taxi/public/api/";
//    private static String BASE_URL= "http://192.168.10.18/taxi/public/api/";
//    public static String URL_PAKECOM_UPDATE_RIDER_ONLNE = BASE_URL_PAKECOM+"rider_update_onlinestatus.php";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}