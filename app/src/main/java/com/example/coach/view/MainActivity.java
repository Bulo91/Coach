package com.example.coach.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coach.R;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnMonIMG;
    private ImageButton btnMonHistorique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        chargeObjetsGraphiques();
        creerMenu();
    }

    private void chargeObjetsGraphiques() {
        btnMonIMG = findViewById(R.id.btnMonIMG);
        btnMonHistorique = findViewById(R.id.btnMonHistorique);
    }

    private void creerMenu() {
        btnMonIMG.setOnClickListener(v -> ecouteMenu(CalculActivity.class));
        btnMonHistorique.setOnClickListener(v -> ecouteMenu(HistoActivity.class));
    }

    private void ecouteMenu(Class<?> classe) {
        Intent intent = new Intent(MainActivity.this, classe);
        startActivity(intent);
    }
}
