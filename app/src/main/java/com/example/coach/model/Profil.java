package com.example.coach.model;

import java.io.Serializable;
import java.util.Date;

public class Profil implements Serializable {

    // Constantes seuils
    private static final int MIN_FEMME = 25;
    private static final int MAX_FEMME = 30;
    private static final int MIN_HOMME = 15;
    private static final int MAX_HOMME = 20;

    private static final String[] MESSAGE = {"trop faible", "normal", "trop élevé"};
    private static final String[] IMAGE = {"maigre", "normal", "graisse"};

    // Champs BDD/API
    private Integer poids;
    private Integer taille;
    private Integer age;
    private Integer sexe;      // 0=femme, 1=homme
    private Date dateMesure;

    // Champs calculés (non envoyés à l’API)
    private transient double img;
    private transient int indice;

    public Profil(Integer poids, Integer taille, Integer age, Integer sexe, Date dateMesure) {
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.dateMesure = dateMesure;

        // calcul initial (utile si on crée le profil côté app)
        this.img = calculImg();
        this.indice = calculIndice();
    }

    private double calculImg() {
        double tailleMetre = taille / 100.0;
        return (1.2 * poids / (tailleMetre * tailleMetre))
                + (0.23 * age)
                - (10.83 * sexe)
                - 5.4;
    }

    private int calculIndice() {
        int min = (sexe != null && sexe == 1) ? MIN_HOMME : MIN_FEMME;
        int max = (sexe != null && sexe == 1) ? MAX_HOMME : MAX_FEMME;

        double v = calculImg();
        if (v < min) return 0;
        if (v > max) return 2;
        return 1;
    }

    // Getters (IMG recalculé pour les profils reçus de l’API)
    public double getImg() {
        return calculImg();
    }

    public String getMessage() {
        return MESSAGE[calculIndice()];
    }

    public String getImage() {
        return IMAGE[calculIndice()];
    }

    public boolean normal() {
        return calculIndice() == 1;
    }

    public Integer getPoids() { return poids; }
    public Integer getTaille() { return taille; }
    public Integer getAge() { return age; }
    public Integer getSexe() { return sexe; }
    public Date getDateMesure() { return dateMesure; }
}
