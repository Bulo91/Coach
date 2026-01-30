package com.example.coach.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CoachApi {

    // Adresse de l'API (émulateur Android -> 10.0.2.2)
    private static final String API_URL = "http://10.0.2.2/rest_coach/";

    // Objet Retrofit unique (style singleton)
    private static Retrofit retrofit = null;

    // Gson avec format de date compatible MySQL DATETIME
    private static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    // Construction / récupération unique de Retrofit
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    // Getter sur gson (utilisé ailleurs pour convertir avant envoi)
    public static Gson getGson() {
        return gson;
    }
}
