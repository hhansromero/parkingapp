package com.upc.group.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EstOpciones extends AppCompatActivity {

    Button btnBuscarEstacionamiento;
    Button btnMarcarIS;
    Button btnLiberarEstacionamiento;
    Button btnConfirmarContinuidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_est_opciones);
        asignarReferencias();
    }

    private void asignarReferencias() {
        btnBuscarEstacionamiento = findViewById(R.id.btnBuscarEstacionamiento);
        btnLiberarEstacionamiento = findViewById(R.id.btnLiberarEstacionamiento);
        btnConfirmarContinuidad = findViewById(R.id.btnConfirmarContinuidad);

        btnBuscarEstacionamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EstOpciones.this, BuscarEstacionamiento.class);
                intent.putExtra("personaId", getIntent().getStringExtra("personaId"));
                startActivity(intent);
            }
        });

        btnLiberarEstacionamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EstOpciones.this, LiberarEstacionamiento.class);
                intent.putExtra("personaId", getIntent().getStringExtra("personaId"));
                startActivity(intent);
            }
        });
    }
}