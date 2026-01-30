package com.example.coach.presenter;

import android.content.Context;

import com.example.coach.contract.ICalculView;
import com.example.coach.data.ProfilDAO;
import com.example.coach.model.Profil;

import java.util.Date;

public class CalculPresenter {

    // Référence vers la vue (pour appeler afficherResultat)
    private ICalculView vue;
    private ProfilDAO profilDAO;

    // Constructeur
    public CalculPresenter(ICalculView vue, Context context) {
        this.vue = vue;
        this.profilDAO = new ProfilDAO(context);

    }

    // Création du profil à partir des saisies + affichage du résultat
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {
        Profil profil = new Profil(poids, taille, age, sexe, new Date());

        profilDAO.insertProfil(profil);

        vue.afficherResultat(
                profil.getMessage(),
                profil.getImg(),
                profil.getImage(),
                profil.normal()
        );
    }

    public void chargerDernierProfil() {

        Profil profil = profilDAO.getLastProfil();

        if (profil != null) {
            vue.remplirChamps(
                    profil.getPoids(),
                    profil.getTaille(),
                    profil.getAge(),
                    profil.getSexe()
            );
        }
    }

}
