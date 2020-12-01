package com.upc.group.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.group.parkingapp.modelos.Estacionamiento;
import com.upc.group.parkingapp.modelos.Reserva;

public class LiberarEstacionamiento extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference reference;

    String personaId;

    TextView txtCodigo, txtTipo, txtTarifa;
    Button btnLiberar, btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liberar_estacionamiento);
        inicializarFirebase();
        getReservaUsuario();
        asignarReferencias();

    }

    private void asignarReferencias() {
        txtCodigo = findViewById(R.id.txtCodigo);
        txtTarifa = findViewById(R.id.txtTarifa);
        txtTipo = findViewById(R.id.txtTipo);
        btnLiberar = findViewById(R.id.btnLiberar);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnLiberar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LiberarEstacionamiento.this, EstOpciones.class);
                startActivity(intent);
            }
        });
    }

    private void getReservaUsuario() {
        personaId = getIntent().getStringExtra("personaId");
        System.out.println("get persona: "+ personaId);


        reference.child("Reserva").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Reserva objR = null;

                for(DataSnapshot item:snapshot.getChildren()) {
                    Reserva reserva = item.getValue(Reserva.class);
                    if (reserva.getPersonaId().equals(personaId)) {
                        objR = reserva;
                        break;
                    }
                }

                if (objR != null) {
                    final Reserva finalObjR = objR;
                    reference.child("Estacionamiento").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Estacionamiento objEst = null;
                            for(DataSnapshot item:snapshot.getChildren()) {
                                Estacionamiento estacionamiento = item.getValue(Estacionamiento.class);
                                if (estacionamiento.getId().equals(finalObjR.getEstacionamientoId())) {
                                    objEst = estacionamiento;
                                    break;
                                }
                            }
                            if (objEst!=null) {
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