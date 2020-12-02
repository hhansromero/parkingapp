package com.upc.group.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuAdmin extends AppCompatActivity {

    BottomNavigationView menuOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_admin);
        asignarReferencias();
    }

    private void asignarReferencias() {
        menuOpciones = findViewById(R.id.botonNavegacion);
        menuOpciones.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;

                if(menuItem.getItemId() == R.id.menu_2) {
                    intent = new Intent(MainMenuAdmin.this, MantOpciones.class);
                    startActivity(intent);
                }

                if(menuItem.getItemId() == R.id.menu_3) {
                    intent = new Intent(MainMenuAdmin.this, MainMenuCochera.class);
                    startActivity(intent);
                }

                return false;
            }
        });
    }
}