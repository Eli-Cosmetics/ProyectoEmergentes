package com.example.elicosmetics.api;

import com.example.elicosmetics.utils.DateSerializer;
import com.example.elicosmetics.utils.TimeSerializer;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Date;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Esta clase es para hacer las consultas del servicio
public class ConfigApi {
    //conexion al servicio: 10.0.2.2 es la ip de android
    public static final String baseUrlE = "http://10.0.2.2:9090";

    private static Retrofit retrofit;
    private static String token = "";

    static {
        initClient();
    }

    private static void initClient() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Time.class, new TimeSerializer())
                .create();
        retrofit = new Retrofit.Builder()
                //Esta variable se encargara de hacerle peticion al servicio
                .baseUrl(baseUrlE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient())
                .build();
    }

    private static OkHttpClient getClient() {
        HttpLoggingInterceptor loggin = new HttpLoggingInterceptor();
        loggin.level(HttpLoggingInterceptor.Level.BODY);
        StethoInterceptor stheto = new StethoInterceptor();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggin)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(stheto);

        return builder.build();

    }

}
