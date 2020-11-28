package com.upc.group.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.group.parkingapp.modelos.Persona;

public class LoginActivity extends AppCompatActivity {

    Button btnEntrar;
    EditText txtUser, txtPassword;
    FirebaseDatabase db;
    FloatingActionButton add_button;
    DatabaseReference reference;
    String tipoUsuario="";
    String personaId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializarFirebase();
        asignarReferencias();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference();
    }

    private void asignarReferencias() {
        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);
        btnEntrar = findViewById(R.id.btnEntrar);
        add_button = findViewById(R.id.add_button);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUser.getText().toString().equals("") || txtPassword.getText().toString().equals("")) {
                    validarRequeridos();
                } else {
                    loguearUsuario();
                }
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainRegistroUsuario.class);
                startActivity(intent);
            }
        });
    }

    private void validarRequeridos() {
        if(txtUser.getText().toString().equals("")){
            txtUser.setError("Campo requerido");
        }
        if (txtPassword.getText().toString().equals("")) {
            txtPassword.setError("Campo requerido");
        }
    }

    private void loguearUsuario() {
        reference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot item:snapshot.getChildren()) {
                    Persona p = item.getValue(Persona.class);

                    if (p.getUsuario().equals(txtUser.getText().toString())
                            && p.getPassword().equals(txtPassword.getText().toString())) {
                        tipoUsuario = p.getTipoUsuario();
                        personaId = p.getId();
                        break;
                    }
                }

                Intent intent;
                if (tipoUsuario.equals("A")) {
                    intent = new Intent(LoginActivity.this, MainMenuAdmin.class);
                    startActivity(intent);
                } else if (tipoUsuario.equals("C")) {
                    intent = new Intent(LoginActivity.this, MainMenuUsuario.class);
                    intent.putExtra("personaId", personaId);
                    startActivity(intent);
                } else if (tipoUsuario.equals("")) {
                    Toast.makeText(LoginActivity.this, "Usuario o Password incorrectos", Toast.LENGTH_SHORT).show();
                }
                tipoUsuario="";

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}