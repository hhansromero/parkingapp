package com.upc.group.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MantOpciones extends AppCompatActivity {

    Button btnMantOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mant_opciones);
        asignarReferencias();
    }

    private void asignarReferencias() {
        btnMantOpciones = findViewById(R.id.btnMantOpciones);

        btnMantOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MantOpciones.this, ListarEmpresasFBActivity.class);
                startActivity(intent);
            }
        });
    }
}