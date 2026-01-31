package com.example.coach.view;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coach.R;
import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;
import com.example.coach.presenter.CalculPresenter;

public class CalculActivity extends AppCompatActivity implements ICalculView {

    private EditText txtPoids;
    private EditText txtTaille;
    private EditText txtAge;

    private RadioButton rdHomme;
    private RadioButton rdFemme;

    private TextView lblResultat;
    private ImageView imgSmiley;

    private Button btnCalc;

    private CalculPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcul);
        init();
    }

    private void init() {
        chargeObjetsGraphiques();

        presenter = new CalculPresenter(this);

        // au lieu de chargerDernierProfil() ici :
        recupProfil();

        btnCalc.setOnClickListener(v -> btnCalc_clic());
    }

    private void chargeObjetsGraphiques() {
        txtPoids = findViewById(R.id.txtPoids);
        txtTaille = findViewById(R.id.txtTaille);
        txtAge = findViewById(R.id.txtAge);

        rdHomme = findViewById(R.id.rdHomme);
        rdFemme = findViewById(R.id.rdFemme);

        lblResultat = findViewById(R.id.lblResultat);
        imgSmiley = findViewById(R.id.imgSmiley);

        btnCalc = findViewById(R.id.btnCalc);
    }

    private void recupProfil() {
        Profil profil = (Profil) getIntent().getSerializableExtra("profil");

        if (profil != null) {
            remplirChamps(
                    profil.getPoids(),
                    profil.getTaille(),
                    profil.getAge(),
                    profil.getSexe()
            );
        } else {
            presenter.chargerDernierProfil();
        }
    }

    private void btnCalc_clic() {
        Integer poids = 0, taille = 0, age = 0, sexe = 0;

        try {
            poids = Integer.parseInt(txtPoids.getText().toString());
            taille = Integer.parseInt(txtTaille.getText().toString());
            age = Integer.parseInt(txtAge.getText().toString());
        } catch (Exception ignored) { }

        if (rdHomme.isChecked()) sexe = 1;

        if (poids == 0 || taille == 0 || age == 0) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        presenter.creerProfil(poids, taille, age, sexe);
    }

    @Override
    public void afficherResultat(String image, double img, String message, boolean normal) {

        int imageId = getResources().getIdentifier(image, "drawable", getPackageName());
        if (imageId == 0) imgSmiley.setImageResource(R.drawable.normal);
        else imgSmiley.setImageResource(imageId);

        String texte = String.format("%.01f", img) + " : IMG " + message;
        lblResultat.setText(texte);

        lblResultat.setTextColor(normal ? Color.GREEN : Color.RED);
    }

    @Override
    public void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe) {
        txtPoids.setText(poids.toString());
        txtTaille.setText(taille.toString());
        txtAge.setText(age.toString());

        if (sexe != null && sexe == 1) rdHomme.setChecked(true);
        else rdFemme.setChecked(true);
    }

    @Override
    public void afficherMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
