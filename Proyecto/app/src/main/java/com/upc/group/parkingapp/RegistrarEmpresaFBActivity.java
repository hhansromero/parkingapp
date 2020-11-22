package com.upc.group.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.group.parkingapp.modelos.Empresa;
import com.upc.group.parkingapp.modelos.Estacionamiento;

import java.util.ArrayList;
import java.util.UUID;

public class RegistrarEmpresaFBActivity extends AppCompatActivity {

    EditText editTextRUC, editTextNombreLocal, editTextRepresentante,
            editTextDireccion, editTextNiveles, editTextTotalEst;
    CheckBox checkBoxTermCondiciones;
    Spinner spHorarios;

    Button btnRegistrarEmpresa;
    FirebaseDatabase db;
    DatabaseReference reference;

    private ArrayList<Empresa> listaEmpresa = new ArrayList<>();
    ArrayAdapter<Empresa> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_empresa_f_b);
        asignarReferencias();
    }

    private void asignarReferencias() {

        editTextRUC = findViewById(R.id.editTextRUC);
        editTextNombreLocal = findViewById(R.id.editTextNombreLocal);
        editTextNombreLocal = findViewById(R.id.editTextNombreLocal);
        editTextRepresentante = findViewById(R.id.editTextRepresentante);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        editTextNiveles = findViewById(R.id.editTextNiveles);
        editTextTotalEst = findViewById(R.id.editTextTotalEst);
        checkBoxTermCondiciones = findViewById(R.id.checkBoxTermCondiciones);
        btnRegistrarEmpresa = findViewById(R.id.btnRegistrarEmpresa);
        spHorarios = findViewById(R.id.spHorarios);

        inicializarFirebase();

        btnRegistrarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validarFormulario()==true) {
                    registrarEmpresa();
                    Toast.makeText(RegistrarEmpresaFBActivity.this, "¡Empresa registrada en Parkink App!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegistrarEmpresaFBActivity.this, ListarEmpresasFBActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    private void registrarEmpresa() {
        Empresa e = new Empresa();
        Estacionamiento s = new Estacionamiento();
        e.setId(UUID.randomUUID().toString());
        e.setRuc(editTextRUC.getText().toString());
        e.setNombreLocal(editTextNombreLocal.getText().toString());
        e.setRepresentante(editTextRepresentante.getText().toString());
        e.setDireccion(editTextDireccion.getText().toString());
        e.setNiveles(Integer.parseInt(editTextNiveles.getText().toString()));
        e.setTotalEst(Integer.parseInt(editTextTotalEst.getText().toString()));
        e.setHorario(spHorarios.getSelectedItem().toString());
        e.setTermCondiciones(Boolean.parseBoolean(checkBoxTermCondiciones.getText().toString()));

        reference.child("Empresa").child(e.getId()).setValue(e);
        editTextRUC.setText("");
        editTextNombreLocal.setText("");
        editTextRepresentante.setText("");
        editTextDireccion.setText("");
        editTextNiveles.setText("");
        spHorarios.setSelected(false);
        checkBoxTermCondiciones.setChecked(true);

    }

    private boolean validarFormulario() {
        boolean isValid = true;

        if (editTextRUC.getText().toString().equals("")) {
            editTextRUC.setError("Campo requerido");
            isValid=false;
        }
        if (editTextNombreLocal.getText().toString().equals("")) {
            editTextNombreLocal.setError("Campo requerido");
            isValid=false;
        }
        if (editTextRepresentante.getText().toString().equals("")) {
            editTextRepresentante.setError("Campo requerido");
            isValid=false;
        }

        return isValid;
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference();

    }
}