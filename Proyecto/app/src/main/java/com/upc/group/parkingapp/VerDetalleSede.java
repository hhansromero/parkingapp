package com.upc.group.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.upc.group.parkingapp.adapters.EstacionamientosAdapter;
import com.upc.group.parkingapp.modelos.Empresa;
import com.upc.group.parkingapp.modelos.Estacionamiento;

import java.util.ArrayList;
import java.util.List;

public class VerDetalleSede extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference reference;

    ListView lstEstacionamientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalle_sede);
        inicializarFirebase();
        asignarReferencias();
        getEstacionamientos();
    }

    private void asignarReferencias() {
        lstEstacionamientos = findViewById(R.id.lstEstacionamientos);

        lstEstacionamientos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Estacionamiento objEst = (Estacionamiento) parent.getAdapter().getItem(position);
            }
        });
    }

    private void getEstacionamientos() {

        String empresaId = getIntent().getStringExtra("empresaId");
        System.out.println(empresaId);


        // Construct the data source - TEMPORAL
        ArrayList<Estacionamiento> arrayOfEsts = new ArrayList<Estacionamiento>();
        Estacionamiento est1 = new Estacionamiento();
        est1.setCodEst("EST099");
        est1.setTipo("TYPE554");
        est1.setNivel("P1");
        est1.setEstado("Disponible");
        arrayOfEsts.add(est1);
        est1 = new Estacionamiento();
        est1.setCodEst("EST102");
        est1.setTipo("TYPE554");
        est1.setNivel("P1");
        est1.setEstado("Disponible");
        arrayOfEsts.add(est1);
        est1 = new Estacionamiento();
        est1.setCodEst("EST089");
        est1.setTipo("TYPE554");
        est1.setNivel("P1");
        est1.setEstado("Disponible");
        arrayOfEsts.add(est1);

        // Create the adapter to convert the array to views
        EstacionamientosAdapter adapter = new EstacionamientosAdapter(this, arrayOfEsts);
        // Attach the adapter to a ListView
        lstEstacionamientos.setAdapter(adapter);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference();
    }
}