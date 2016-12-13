package com.rafaelguimas.sunshine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
*  Classe principal que contem um Fragment de controle
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Esconde a actionbar
        getSupportActionBar().setElevation(0);
//        getSupportActionBar().hide();
    }
}
