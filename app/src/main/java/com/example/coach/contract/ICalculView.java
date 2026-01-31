package com.example.coach.contract;

public interface ICalculView extends IAllView {
    void afficherResultat(String image, double img, String message, boolean normal);
    void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe);
}
