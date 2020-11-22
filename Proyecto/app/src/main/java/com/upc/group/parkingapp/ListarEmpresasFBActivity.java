package com.upc.group.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.group.parkingapp.modelos.Empresa;

import java.util.ArrayList;

public class ListarEmpresasFBActivity extends AppCompatActivity {

    EditText editTextRUC, editTextNombreLocal, editTextRepresentante,
            editTextDireccion, editTextNiveles, editTextTotalEst;
    Spinner spHorarios;

    Button btnBuscarEmpresa, btnActualizar, btnRegistrarE, btnEliminar;
    FirebaseDatabase db;
    DatabaseReference reference;

    private ArrayList<Empresa> listaEmpresa = new ArrayList<>();
    ArrayAdapter<Empresa> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_empresas_f_b);
        asignarReferencias();
    }

    private void asignarReferencias() {
        editTextRUC = findViewById(R.id.txtRUCBuscar);
        btnBuscarEmpresa = findViewById(R.id.btnBuscarEmpresa);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnRegistrarE = findViewById(R.id.btnRegistrarE);
        btnEliminar = findViewById(R.id.btnEliminar);
        inicializarFirebase();

        btnBuscarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarEmpresa();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarEmpresa();
            }
        });

        btnRegistrarE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListarEmpresasFBActivity.this, RegistrarEmpresaFBActivity.class);
                startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listarEmpresa();
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference();

    }

    private void listarEmpresa() {
        reference.child("Empresa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaEmpresa.clear();
                for(DataSnapshot item:snapshot.getChildren()){
                    Empresa e = item.getValue(Empresa.class);
                    listaEmpresa.add(e);
                }
                //adapter = new ArrayAdapter<>(FirebaseActivity.this, android.R.layout.simple_list_item_1,listaEmpresa);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}