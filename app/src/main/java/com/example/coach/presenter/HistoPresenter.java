package com.example.coach.presenter;

import com.example.coach.api.CoachApi;
import com.example.coach.api.HelperApi;
import com.example.coach.api.ICallbackApi;
import com.example.coach.contract.IHistoView;
import com.example.coach.model.Profil;

import java.util.Collections;
import java.util.List;

public class HistoPresenter {

    private final IHistoView vue;

    public HistoPresenter(IHistoView vue) {
        this.vue = vue;
    }

    public void chargerProfils() {
        HelperApi.call(HelperApi.getApi().getProfils(), new ICallbackApi<List<Profil>>() {
            @Override
            public void onSuccess(List<Profil> result) {
                if (result != null && !result.isEmpty()) {
                    Collections.sort(result, (p1, p2) ->
                            p2.getDateMesure().compareTo(p1.getDateMesure())
                    );
                    vue.afficherListe(result);
                } else {
                    vue.afficherMessage("échec récupération profils");
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("échec récupération profils");
            }
        });
    }

    public void supprProfil(Profil profil, ICallbackApi<Void> callback) {
        String profilJson = CoachApi.getGson().toJson(profil);

        HelperApi.call(HelperApi.getApi().supprProfil(profilJson), new ICallbackApi<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                if (result != null && result == 1) {
                    vue.afficherMessage("profil supprimé");
                    callback.onSuccess(null);
                } else {
                    vue.afficherMessage("échec suppression profil");
                    callback.onError();
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("échec suppression profil");
                callback.onError();
            }
        });
    }

    public void transfertProfil(Profil profil) {
        vue.transfertProfil(profil);
    }
}
