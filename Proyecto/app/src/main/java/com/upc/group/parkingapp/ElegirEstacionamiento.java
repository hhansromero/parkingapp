package com.upc.group.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.group.parkingapp.modelos.Estacionamiento;
import com.upc.group.parkingapp.modelos.Reserva;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class ElegirEstacionamiento extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference reference;

    TextView txtCodigo, txtTipo, txtTarifa;
    Button btnElegir, btnRegresar;

    String empresaId;
    String estacionamientoId;
    String personaId;
    boolean tieneReservas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_estacionamiento);
        inicializarFirebase();
        personaId = getIntent().getStringExtra("personaId");
        System.out.println("persona:::: "+ personaId);
        getEstacionamiento();
        asignarReferencias();
    }

    private void getEstacionamiento() {
        estacionamientoId = getIntent().getStringExtra("estacionamientoId");

        reference.child("Estacionamiento").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Estacionamiento objEst = new Estacionamiento();
                for(DataSnapshot item:snapshot.getChildren()) {
                    Estacionamiento e = item.getValue(Estacionamiento.class);
                    if (e.getId().equals(estacionamientoId)) {
                        objEst = e;
                        break;
                    }
                }
                if (objEst != null) {
                    txtCodigo.setText(objEst.getCodEst());
                    txtTipo.setText(objEst.getTipo());
                    txtTarifa.setText(String.valueOf(objEst.getTarifa()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void asignarReferencias() {
        txtCodigo = findViewById(R.id.txtCodigo);
        txtTipo = findViewById(R.id.txtTipo);
        txtTarifa = findViewById(R.id.txtTarifa);
        btnElegir = findViewById(R.id.btnElegir);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnElegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validarReservasPrevias();
                if (!tieneReservas) {
                    String estadoEstacionamiento = getIntent().getStringExtra("estadoEstacionamiento");
                    if (estadoEstacionamiento.equals("Disponible")) {
                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                        Reserva reserva = new Reserva();
                        reserva.setId(UUID.randomUUID().toString());
                        reserva.setEstacionamientoId(estacionamientoId);
                        reserva.setPersonaId(personaId);
                        reserva.setFechaHoraInicio(currentDateTimeString);
                        reserva.setEstado("Ocupado");

                        reference.child("Reserva").child(reserva.getId()).setValue(reserva);
                        reference.child("Estacionamiento").child(estacionamientoId).child("estado").setValue("Ocupado");

                        Toast.makeText(ElegirEstacionamiento.this, "¡Reserva generada en Parkink App!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ElegirEstacionamiento.this, VerDetalleSede.class);
                        intent.putExtra("empresaId", empresaId);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ElegirEstacionamiento.this, "Debe elegir un estacionmiento en estado Disponible", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ElegirEstacionamiento.this, "Estimado usuario, Ud ya tiene un estacionamiento ocupado", Toast.LENGTH_SHORT).show();
                }

            }
        });

        empresaId = getIntent().getStringExtra("empresaId");
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ElegirEstacionamiento.this, VerDetalleSede.class);
                intent.putExtra("empresaId", empresaId);
                startActivity(intent);
            }
        });

    }

    private void validarReservasPrevias() {
        reference.child("Reserva").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Reserva objR = null;
                for(DataSnapshot item:snapshot.getChildren()) {
                    Reserva r = item.getValue(Reserva.class);
                    if (r.getPersonaId().equals(personaId)) {
                        objR = r;
                        break;
                    }
                }
                if (objR != null) {
                    tieneReservas = true;
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