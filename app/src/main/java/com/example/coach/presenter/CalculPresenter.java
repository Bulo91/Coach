package com.example.coach.presenter;

import com.example.coach.api.CoachApi;
import com.example.coach.api.HelperApi;
import com.example.coach.api.ICallbackApi;
import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CalculPresenter {

    private final ICalculView vue;

    public CalculPresenter(ICalculView vue) {
        this.vue = vue;
    }

    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {
        Profil profil = new Profil(poids, taille, age, sexe, new Date());

        // Afficher immédiatement (ne bloque pas si l’API est down)
        vue.afficherResultat(
                profil.getImage(),
                profil.getImg(),
                profil.getMessage(),
                profil.normal()
        );

        String profilJson = CoachApi.getGson().toJson(profil);

        HelperApi.call(HelperApi.getApi().creerProfil(profilJson), new ICallbackApi<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                if (result != null && result == 1) {
                    vue.afficherMessage("profil enregistré");
                } else {
                    vue.afficherMessage("échec enregistrement profil");
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("échec enregistrement profil");
            }
        });
    }

    public void chargerDernierProfil() {
        HelperApi.call(HelperApi.getApi().getProfils(), new ICallbackApi<List<Profil>>() {
            @Override
            public void onSuccess(List<Profil> result) {
                if (result != null && !result.isEmpty()) {
                    Profil dernier = Collections.max(result,
                            (p1, p2) -> p1.getDateMesure().compareTo(p2.getDateMesure())
                    );
                    vue.remplirChamps(
                            dernier.getPoids(),
                            dernier.getTaille(),
                            dernier.getAge(),
                            dernier.getSexe()
                    );
                } else {
                    vue.afficherMessage("échec récupération dernier profil");
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("échec récupération dernier profil");
            }
        });
    }
}
