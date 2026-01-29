package com.example.coach.presenter;

import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;

import java.util.Date;

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

        vue.afficherResultat(
                profil.getImage(),
                profil.getImg(),
                profil.getMessage(),
                profil.normal()
        );
    }
}
