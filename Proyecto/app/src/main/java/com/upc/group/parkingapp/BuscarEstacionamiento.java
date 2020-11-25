package com.upc.group.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.upc.group.parkingapp.modelos.Empresa;

import java.util.ArrayList;
import java.util.List;

public class BuscarEstacionamiento extends AppCompatActivity {

    Button btnBuscar;
    FirebaseDatabase db;
    DatabaseReference reference;
    List<Empresa> empresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_estacionamiento);
        inicializarFirebase();
        getEmpresas();
        asignarReferencias();
    }

    private void asignarReferencias() {
        btnBuscar = findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String jsonEmpresas = gson.toJson(empresas);

                Intent intent = new Intent(BuscarEstacionamiento.this, MapEmpresasActivity.class);
                intent.putExtra("jsonEmpresas", jsonEmpresas);
                startActivity(intent);
            }
        });
    }

    private void getEmpresas(){
        empresas = new ArrayList<>();

        reference.child("Empresa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()) {
                    Empresa e = item.getValue(Empresa.class);
                    if (e.getLatitud()!=null && e.getLongitud()!=null) {
                        empresas.add(e);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference();
    }
}