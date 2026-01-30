package com.example.coach.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;
import com.google.gson.Gson;

import java.util.Date;

public class CalculPresenter {
    private static final String NOM_FIC = "coach_fic";
    private static final String PROFIL_CLE = "profil_json";

    // Référence vers la vue (pour appeler afficherResultat)
    private ICalculView vue;
    private Gson gson;
    private SharedPreferences prefs;

    // Constructeur
    public CalculPresenter(ICalculView vue, Context context) {
        this.vue = vue;

        this.prefs = context.getSharedPreferences(NOM_FIC, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    // Création du profil à partir des saisies + affichage du résultat
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {
        Profil profil = new Profil(poids, taille, age, sexe, new Date());

        sauvegarderProfil(profil);

        vue.afficherResultat(
                profil.getImage(),
                profil.getImg(),
                profil.getMessage(),
                profil.normal()
        );
    }
    private void sauvegarderProfil(Profil profil) {
        String json = gson.toJson(profil);
        prefs.edit().putString(PROFIL_CLE, json).apply();
    }
    public void chargerProfil() {

        String json = prefs.getString(PROFIL_CLE, null);

        if (json != null) {
            Profil profil = gson.fromJson(json, Profil.class);

            vue.remplirChamps(
                    profil.getPoids(),
                    profil.getTaille(),
                    profil.getAge(),
                    profil.getSexe()
            );
        }
    }


}
