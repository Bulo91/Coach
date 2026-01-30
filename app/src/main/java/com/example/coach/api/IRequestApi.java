package com.example.coach.api;

import com.example.coach.model.Profil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IRequestApi {

    // Récupération de tous les profils
    @GET("profil")
    Call<ResponseApi<List<Profil>>> getProfils();


    // Enregistrement d’un profil
    @POST("profil")
    Call<ResponseApi<Integer>> insertProfil(@Body Profil profil);

    @FormUrlEncoded
    @POST("profil")
    Call<ResponseApi<Integer>> creerProfil(@Field("champs") String profilJson);

}

