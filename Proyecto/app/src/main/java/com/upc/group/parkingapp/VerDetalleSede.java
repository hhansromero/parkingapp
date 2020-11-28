package com.upc.group.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.group.parkingapp.adapters.EstacionamientosAdapter;
import com.upc.group.parkingapp.modelos.Empresa;
import com.upc.group.parkingapp.modelos.Estacionamiento;

import java.util.ArrayList;

public class VerDetalleSede extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference reference;

    ListView lstEstacionamientos;
    TextView txtNombreEmpresa, txtRepresentanteEmpresa, txtDireccion, txtNiveles, txtHorario;

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
        txtNombreEmpresa = findViewById(R.id.txtNombreEmpresa);
        txtRepresentanteEmpresa = findViewById(R.id.txtRepresentanteEmpresa);
        txtNiveles = findViewById(R.id.txtNiveles);
        txtHorario = findViewById(R.id.txtHorario);
        txtDireccion = findViewById(R.id.txtDireccion);

        lstEstacionamientos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Estacionamiento objEst = (Estacionamiento) parent.getAdapter().getItem(position);

                Intent intent = new Intent(VerDetalleSede.this, ElegirEstacionamiento.class);
                intent.putExtra("empresaId", objEst.getEmpresaId());
                intent.putExtra("estacionamientoId", objEst.getId());
                intent.putExtra("estadoEstacionamiento", objEst.getEstado());
                intent.putExtra("personaId", getIntent().getStringExtra("personaId"));
                startActivity(intent);
            }
        });
    }

    private void getEstacionamientos() {

        final String empresaId = getIntent().getStringExtra("empresaId");

        reference.child("Empresa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Empresa objEmp = new Empresa();
                for(DataSnapshot item:snapshot.getChildren()) {
                    Empresa e = item.getValue(Empresa.class);
                    if (e.getId().equals(empresaId)) {
                        objEmp = e;
                        break;
                    }
                }
                if (objEmp != null) {
                    txtNombreEmpresa.setText(objEmp.getNombreLocal());
                    txtRepresentanteEmpresa.setText(objEmp.getRepresentante());
                    txtNiveles.setText(String.valueOf(objEmp.getNiveles()));
                    txtHorario.setText(objEmp.getHorario());
                    txtDireccion.setText(objEmp.getDireccion());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        reference.child("Estacionamiento").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Estacionamiento> arrayOfEsts = new ArrayList<>();
                for(DataSnapshot item:snapshot.getChildren()) {
                    Estacionamiento e = item.getValue(Estacionamiento.class);
                    if (e.getEmpresaId().equals(empresaId)) {
                        arrayOfEsts.add(e);
                    }
                }
                // Create the adapter to convert the array to views
                EstacionamientosAdapter adapter = new EstacionamientosAdapter(VerDetalleSede.this, arrayOfEsts);
                // Attach the adapter to a ListView
                lstEstacionamientos.setAdapter(adapter);
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