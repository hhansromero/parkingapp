package com.upc.group.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.group.parkingapp.modelos.PerfilPersona;
import com.upc.group.parkingapp.modelos.Persona;

import java.util.UUID;

public class MainPerfilCliente extends AppCompatActivity {
// hola
    //pruebaaaaa

    EditText txtPlaca, txtModelo;
    CheckBox cbE1, cbE2, cbE3, cbS1, cbS2, cbS3;
    Button btnGuarda;
    boolean existe=false;
    PerfilPersona objPP = null;

    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_perfil_cliente);
        asignarReferencias();
        obtenerPerfilPersona();
    }

    private void obtenerPerfilPersona() {
        final String personaId = getIntent().getStringExtra("personaId");

        reference.child("PerfilPersona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item : snapshot.getChildren()) {
                    PerfilPersona pp = item.getValue(PerfilPersona.class);
                    if (pp.getPersonId().equals(personaId)) {
                        objPP = pp;
                        break;
                    }
                }

                if (objPP != null) {
                    txtPlaca.setText(objPP.getNumeroPlaca());
                    txtModelo.setText(objPP.getModeloAuto());
                    cbE1.setChecked(objPP.isInicioSesionEmail());
                    cbE2.setChecked(objPP.isNotificacion15MinEmail());
                    cbE3.setChecked(objPP.isNewsEmail());
                    cbS1.setChecked(objPP.isInicioSesionSMS());
                    cbS2.setChecked(objPP.isNotificacion15MinSMS());
                    cbS3.setChecked(objPP.isNewsSMS());
                    existe = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void asignarReferencias() {
        txtPlaca = findViewById(R.id.txtPlaca);
        txtModelo = findViewById(R.id.txtModelo);
        cbE1 = findViewById(R.id.cbE1);
        cbE2 = findViewById(R.id.cbE2);
        cbE3 = findViewById(R.id.cbE3);
        cbS1 = findViewById(R.id.cbS1);
        cbS2 = findViewById(R.id.cbS2);
        cbS3 = findViewById(R.id.cbS3);
        btnGuarda = findViewById(R.id.btnGuarda);

        inicializarFirebase();

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarPerfil();
            }
        });

    }

    private void registrarPerfil(){

        PerfilPersona p =new PerfilPersona();
        if (existe) {
            p.setId(objPP.getId());
        } else {
            p.setId(UUID.randomUUID().toString());
        }

        p.setNumeroPlaca(txtPlaca.getText().toString());
        p.setModeloAuto(txtModelo.getText().toString());
        p.setPersonId(getIntent().getStringExtra("personaId"));

        if (cbE1.isChecked()){
            p.setInicioSesionEmail(true);
        }
        if (cbE2.isChecked()){
            p.setNotificacion15MinEmail(true);
        }
        if (cbE3.isChecked()){
            p.setNewsEmail(true);
        }
        if (cbS1.isChecked()){
            p.setInicioSesionSMS(true);
        }
        if (cbS2.isChecked()){
            p.setNotificacion15MinSMS(true);
        }
        if (cbS3.isChecked()){
            p.setNewsSMS(true);
        }


        if (validarFormulario() == true) {
            reference.child("PerfilPersona").child(p.getId()).setValue(p);
            Toast.makeText(this, "¡Perfil registrado en Parkink App!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainPerfilCliente.this, MainMenuUsuario.class);
            startActivity(intent);
        }
    }


    private boolean validarFormulario(){
        boolean isValid = true;

        if (txtPlaca.getText().toString().equals("")) {
            txtPlaca.setError("Campo requerido");
            isValid=false;
        }
       /* if (txtPassword.getText().toString().equals("")) {
            txtPassword.setError("Campo requerido");
            isValid=false;
        }
        if (txtNombres.getText().toString().equals("")) {
            txtNombres.setError("Campo requerido");
            isValid=false;
        }
        if (txtApellidos.getText().toString().equals("")) {
            txtApellidos.setError("Campo requerido");
            isValid=false;
        }
        if (txtDNI.getText().toString().equals("")) {
            txtDNI.setError("Campo requerido");
            isValid=false;
        }
       /*
       if (spTipo.getSelectedItem().toString().equals("")) {
       }
       */
        return isValid;
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference();
    }
}