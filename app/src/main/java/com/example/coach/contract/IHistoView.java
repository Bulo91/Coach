package com.example.coach.contract;

import com.example.coach.model.Profil;
import java.util.List;

public interface IHistoView extends IAllView {
    void afficherListe(List<Profil> profils);
    void transfertProfil(Profil profil);
}
