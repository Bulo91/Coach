package com.example.coach.model;


public class Profil {
    // Constantes
    private static final int MIN_FEMME = 25;
    private static final int MAX_FEMME = 30;
    private static final int MIN_HOMME = 15;
    private static final int MAX_HOMME = 20;
    private static final String[] MESSAGE = {"trop faible", "normal", "trop élevé"};
    private static final String[] IMAGE = {"maigre", "normal", "graisse"};

    // Propriétés
    private Integer poids;
    private Integer taille;
    private Integer age;
    private Integer sexe;

    private double img;
    private int indice;


    // Constructeur
    public Profil(Integer poids, Integer taille, Integer age, Integer sexe) {
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.img = calculImg();
        this.indice = calculIndice();

    }

    private double calculImg() {
        // conversion de la taille en mètres
        double tailleMetre = taille / 100.0;

        // formule de calcul de l'IMG
        return (1.2 * poids / (tailleMetre * tailleMetre))
                + (0.23 * age)
                - (10.83 * sexe)
                - 5.4;
    }

    private int calculIndice() {
        int min;
        int max;
        if (sexe == 0) { // Femme
            min = MIN_FEMME;
            max = MAX_FEMME;
            } else { // Homme
            min = MIN_HOMME;
            max = MAX_HOMME;
        }
        if (img < min) {
            return 0; // trop faible
        } else if (img > max) {
            return 2; // trop élevé
        } else {
            return 1; // normal
        }
    }

    // Getters
    public double getImg() {
        return img;
    }

    public String getMessage() {
        return MESSAGE[indice];
    }

    public String getImage() {
        return IMAGE[indice];
    }

    public boolean normal() {
        return indice == 1;
    }
}
