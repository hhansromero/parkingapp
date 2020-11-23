package com.upc.group.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.upc.group.parkingapp.modelos.Empresa;

import java.util.ArrayList;

public class BuscarEstacionamiento extends AppCompatActivity {

    Button btnBuscar, btnVerDetalleSede;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_estacionamiento);
        asignarReferencias();
    }

    private void asignarReferencias() {
        btnBuscar = findViewById(R.id.btnBuscar);
        btnVerDetalleSede = findViewById(R.id.btnVerDetalleSede);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Empresa> empresaList = new ArrayList<>();

                Intent intent = new Intent(BuscarEstacionamiento.this, MapEmpresasActivity.class);
                //intent.putParcelableArrayListExtra("empresas", (ArrayList<? extends Parcelable>) empresaList);

                startActivity(intent);
            }
        });
        btnVerDetalleSede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuscarEstacionamiento.this, VerDetalleSede.class);
                startActivity(intent);
            }
        });
    }
}