package com.upc.group.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.group.parkingapp.modelos.ControlAccesoEstacionamiento;
import com.upc.group.parkingapp.modelos.Persona;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MainRegIngresoCliente extends AppCompatActivity {

    //DECLARANDO VARIABLES A USAR

    EditText txtDniConductor, txtPlacaVehi, txtMarcaVehi, txtModeloVehi;
    Button btnRegistrarVehiculocliente;
    TextView horaIngreso, fechaIngreso;
    Boolean estado = false;
    Date date;

    //REFERENCIAS LA CLASES FIREBASE

    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_ingreso_cliente);

        asignarReferencias();
    }


    //INGRESO DE HORA Y FECHA //


    private void asignarReferencias() {
        txtDniConductor = findViewById(R.id.txtDniCliente);
        txtPlacaVehi = findViewById(R.id.txtIngresarPlaca);
        txtMarcaVehi = findViewById(R.id.txtIngresarMarca);
        txtModeloVehi = findViewById(R.id.txtIngresarModelo);
        btnRegistrarVehiculocliente = findViewById(R.id.btnRegistrarIngreso);
        fechaIngreso = findViewById(R.id.txtViewFecha);
        horaIngreso = findViewById(R.id.txtViewHora);

        inicializarFirebase();

        btnRegistrarVehiculocliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDNI();
            }
        });
    }

    private void registrarIngresoVehiculo() {

        ControlAccesoEstacionamiento cae = new ControlAccesoEstacionamiento();
        date = new Date();
        SimpleDateFormat f= new SimpleDateFormat("d / MM / yyyy ");

        cae.setId(UUID.randomUUID().toString());
        cae.setFechaIngreso(f.format(date));
        cae.setPersonaDni(txtDniConductor.getText().toString());
        cae.setEstado(1);
        cae.setHoraIngreso(date.getHours()+":"+date.getMinutes());
        cae.setHoraSalida("");
        cae.setPlaca(txtPlacaVehi.getText().toString());
        cae.setMarca(txtMarcaVehi.getText().toString());
        cae.setModelo(txtModeloVehi.getText().toString());
        reference.child("IngresoVehiculos").child(cae.getId()).setValue(cae);

        //SE ENVIA MENSAJE DE REGISTRO CORRECTO//
        Toast.makeText(this, "Registro exitoso!", Toast.LENGTH_SHORT).show();

        //SE LIMPIA LOS DATOS DE LOS TEXTS /////
        txtDniConductor.setText("");
        txtPlacaVehi.setText("");
        txtMarcaVehi.setText("");
        txtModeloVehi.setText("");
        horaIngreso.setText("");
        fechaIngreso.setText("");

        fechaIngreso.setText(""+f.format(date));
        horaIngreso.setText(date.getHours()+":"+date.getMinutes());

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference();

    }

    private void validarDNI(){

        reference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Persona objPer = null;

                for(DataSnapshot item:snapshot.getChildren()) {
                    objPer = item.getValue(Persona.class);
                    if(txtDniConductor.getText().toString().equals(objPer.getDni())){
                        estado = true;
                        break;
                    }
                }

                if(estado) {
                    registrarIngresoVehiculo();
                    estado = false;
                }
                else
                    Toast.makeText(MainRegIngresoCliente.this, "El cliente no esta registrado", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainRegIngresoCliente.this, "Ha ocurrido un error en la consulta", Toast.LENGTH_SHORT).show();
            }
        });

    }



}