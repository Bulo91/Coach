package com.example.coach.view;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coach.R;
import com.example.coach.contract.ICalculView;
import com.example.coach.presenter.CalculPresenter;

public class MainActivity extends AppCompatActivity implements ICalculView {
    // Zones de saisie
    private EditText txtPoids;
    private EditText txtTaille;
    private EditText txtAge;

    // Boutons radio
    private RadioButton rdHomme;
    private RadioButton rdFemme;

    // Affichage du résultat
    private TextView lblIMG;
    private ImageView imgSmiley;

    // Bouton calcul
    private Button btnCalc;

    //Presenter
    private CalculPresenter presenter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialisation des objets graphiques
        init();
    }


    @Override
    public void afficherResultat(String image, double img, String message, boolean normal) {

        // 4E1 - Affichage de l'image
        int imageId = getResources().getIdentifier(image, "drawable", getPackageName());

        if (imageId == 0) {
            imgSmiley.setImageResource(R.drawable.normal);
        } else {
            imgSmiley.setImageResource(imageId);
        }

        // 4E2 - Affichage du texte
        String texte = String.format("%.01f", img) + " : IMG " + message;
        lblIMG.setText(texte);

        // Couleur du texte
        if (normal) {
            lblIMG.setTextColor(Color.GREEN);
        } else {
            lblIMG.setTextColor(Color.RED);
        }
    }

    @Override
    public void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe) {
        txtPoids.setText(poids.toString());
        txtTaille.setText(taille.toString());
        txtAge.setText(age.toString());

        if (sexe == 1) {
            rdHomme.setChecked(true);
        } else {
            rdFemme.setChecked(true);
        }
    }

    private void init() {
        chargeObjetsGraphiques();
        presenter = new CalculPresenter(this, this);
        presenter.chargerProfil();
        btnCalc.setOnClickListener(v -> btnCalc_clic());

    }
    private void chargeObjetsGraphiques() {
        txtPoids = (EditText) findViewById(R.id.txtPoids);
        txtTaille = (EditText) findViewById(R.id.txtTaille);
        txtAge = (EditText) findViewById(R.id.txtAge);

        rdHomme = (RadioButton) findViewById(R.id.rdHomme);
        rdFemme = (RadioButton) findViewById(R.id.rdFemme);

        lblIMG = (TextView) findViewById(R.id.lblResultat);
        imgSmiley = (ImageView) findViewById(R.id.imgSmiley);

        btnCalc = (Button) findViewById(R.id.btnCalc);
    }

    private void btnCalc_clic() {

        // Variables locales initialisées à 0
        Integer poids = 0;
        Integer taille = 0;
        Integer age = 0;
        Integer sexe = 0; // femme par défaut

        // Tentative de récupération des saisies
        try {
            poids = Integer.parseInt(txtPoids.getText().toString());
            taille = Integer.parseInt(txtTaille.getText().toString());
            age = Integer.parseInt(txtAge.getText().toString());
        } catch (Exception ignored) {
        }

        // Gestion du sexe
        if (rdHomme.isChecked()) {
            sexe = 1;
        }

        // Vérification des saisies
        if (poids == 0 || taille == 0 || age == 0) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            // Appel au presenter
            presenter.creerProfil(poids, taille, age, sexe);
        }
    }



}