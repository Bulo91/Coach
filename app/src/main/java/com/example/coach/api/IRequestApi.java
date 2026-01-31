package com.example.coach.api;

import com.example.coach.model.Profil;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRequestApi {

    // Récupération de tous les profils
    @GET("profil")
    Call<ResponseApi<List<Profil>>> getProfils();

    // Création d’un profil (profil envoyé dans un champ "champs" au format json)
    @FormUrlEncoded
    @POST("profil")
    Call<ResponseApi<Integer>> creerProfil(@Field("champs") String profilJson);

    // Suppression d’un profil (profil json dans l’URL)
    @DELETE("profil/{champs}")
    Call<ResponseApi<Integer>> supprProfil(@Path(value = "champs", encoded = true) String profilJson);
}
