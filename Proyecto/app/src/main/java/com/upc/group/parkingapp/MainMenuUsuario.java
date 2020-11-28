package com.upc.group.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuUsuario extends AppCompatActivity {

    BottomNavigationView menuOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_usuario);
        asignarReferencias();
    }

    private void asignarReferencias() {
        menuOpciones = findViewById(R.id.botonNavegacion);
        menuOpciones.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent;

                if(menuItem.getItemId() == R.id.menu_2) {
                    intent = new Intent(MainMenuUsuario.this, EstOpciones.class);
                    intent.putExtra("personaId", getIntent().getStringExtra("personaId"));
                    startActivity(intent);
                }
                else if (menuItem.getItemId() == R.id.menu_3){
                    intent = new Intent(MainMenuUsuario.this, MainPerfilCliente.class);
                    startActivity(intent);

                }

                return false;

            }
        });
    }
}