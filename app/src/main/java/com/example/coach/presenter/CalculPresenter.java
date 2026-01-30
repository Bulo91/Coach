package com.example.coach.presenter;

import android.util.Log;

import com.example.coach.api.CoachApi;
import com.example.coach.api.IRequestApi;
import com.example.coach.api.ResponseApi;
import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalculPresenter {

    // Référence vers la vue (pour appeler afficherResultat)
    private ICalculView vue;

    // Constructeur
    public CalculPresenter(ICalculView vue) {
        this.vue = vue;
    }

    // Création du profil à partir des saisies + affichage du résultat
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {

        Profil profil = new Profil(poids, taille, age, sexe, new Date());

        // Conversion du profil en json
        String profilJson = CoachApi.getGson().toJson(profil);

        // Création de l'objet d'accès à l'API
        IRequestApi api = CoachApi.getRetrofit().create(IRequestApi.class);

        // Construction de la requête POST
        Call<ResponseApi<Integer>> call = api.creerProfil(profilJson);

        // Envoi asynchrone
        call.enqueue(new Callback<ResponseApi<Integer>>() {
            @Override
            public void onResponse(Call<ResponseApi<Integer>> call, Response<ResponseApi<Integer>> response) {

                // Affichage debug (attention : body peut être null si erreur serveur)
                if (response.body() != null) {
                    Log.d("API", "code : " + response.body().getCode() +
                            " message : " + response.body().getMessage() +
                            " result : " + response.body().getResult()
                    );
                }

                // Test réponse OK + 1 ligne insérée
                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().getResult() != null
                        && response.body().getResult() == 1) {

                    vue.afficherResultat(
                            profil.getMessage(),
                            profil.getImg(),
                            profil.getImage(),
                            profil.normal()
                    );

                } else {
                    Log.e("API", "Erreur API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<Integer>> call, Throwable throwable) {
                Log.e("API", "Échec d'accès à l'api", throwable);
            }
        });
    }

    /**
     * Récupère tous les profils via l'API, puis en extrait le plus récent (dateMesure max)
     * et remplit les champs de la vue.
     */
    public void chargerDernierProfil() {

        // Accès à l'API
        IRequestApi api = CoachApi.getRetrofit().create(IRequestApi.class);

        // Construction de la requête GET
        Call<ResponseApi<List<Profil>>> call = api.getProfils();

        // Envoi asynchrone
        call.enqueue(new Callback<ResponseApi<List<Profil>>>() {
            @Override
            public void onResponse(Call<ResponseApi<List<Profil>>> call,
                                   Response<ResponseApi<List<Profil>>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Profil> profils = response.body().getResult();

                    if (profils != null && !profils.isEmpty()) {

                        Profil dernier = Collections.max(
                                profils,
                                (p1, p2) -> p1.getDateMesure().compareTo(p2.getDateMesure())
                        );

                        vue.remplirChamps(
                                dernier.getPoids(),
                                dernier.getTaille(),
                                dernier.getAge(),
                                dernier.getSexe()
                        );
                    }

                } else {
                    Log.e("API", "Erreur API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<List<Profil>>> call, Throwable throwable) {
                Log.e("API", "Échec d'accès à l'api", throwable);
            }
        });
    }


}
