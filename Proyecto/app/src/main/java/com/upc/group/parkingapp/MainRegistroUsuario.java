package com.upc.group.parkingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.upc.group.parkingapp.modelos.Persona;

import java.util.UUID;

public class MainRegistroUsuario extends AppCompatActivity {

    FloatingActionButton Log_button;
    EditText txtUsuario, txtNombres, txtApellidos, txtDNI, txtEmail, txtPassword, txtCelular;
    Spinner spTipo;
    CheckBox cbAcepto;
    Button btnRegistrar;

    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_registro_usuario);
        asignarReferencias();
    }

   private void asignarReferencias() {

       txtUsuario = findViewById(R.id.txtUsuario);
       txtNombres = findViewById(R.id.txtNombres);
       txtApellidos = findViewById(R.id.txtApellidos);
       txtDNI = findViewById(R.id.txtDNI);
       txtCelular = findViewById(R.id.txtCelular);
       txtEmail = findViewById(R.id.txtEmail);
       txtPassword = findViewById(R.id.txtPassword);
       spTipo = findViewById(R.id.spTipo);
       cbAcepto = findViewById(R.id.cbAcepto);
       Log_button = findViewById(R.id.log_button);
       btnRegistrar=findViewById(R.id.btnRegistrar);

       inicializarFirebase();

       Log_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainRegistroUsuario.this, LoginActivity.class);
               startActivity(intent);
           }
       });

       btnRegistrar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               registrarUsuario();
           }
       });

       cbAcepto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (!cbAcepto.isChecked()) {
                   btnRegistrar.setEnabled(false);
               } else {
                   btnRegistrar.setEnabled(true);
               }
           }
       });

   }

    private void registrarUsuario(){

        Persona p = new Persona ();
        p.setId(UUID.randomUUID().toString());
        p.setUsuario(txtUsuario.getText().toString());
        p.setNombres(txtNombres.getText().toString());
        p.setApellidos(txtApellidos.getText().toString());
        p.setDni(txtDNI.getText().toString());
        p.setCelular(txtCelular.getText().toString());
        p.setEmail(txtEmail.getText().toString());
        String tipo="";
        if (spTipo.getSelectedItem().toString().equals("Administrador")) {
            tipo = "A";
        } else if (spTipo.getSelectedItem().toString().equals("Cliente")) {
            tipo = "C";
        }
        p.setTipoUsuario(tipo);
        p.setPassword(txtPassword.getText().toString());

        if (validarFormulario() == true) {
            reference.child("Persona").child(p.getId()).setValue(p);
            Toast.makeText(this, "¡Usuario registrado en Parkink App!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainRegistroUsuario.this, LoginActivity.class);
            startActivity(intent);
        }

    }

   private boolean validarFormulario(){
       boolean isValid = true;

        if (txtUsuario.getText().toString().equals("")) {
            txtUsuario.setError("Campo requerido");
            isValid=false;
        }
       if (txtPassword.getText().toString().equals("")) {
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


