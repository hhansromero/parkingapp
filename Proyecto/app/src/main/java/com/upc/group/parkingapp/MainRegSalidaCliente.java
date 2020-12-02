package com.upc.group.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainRegSalidaCliente extends AppCompatActivity {

    TextView txtVerPlaca, txtVerMarca, txtVerModelo,horaSalida, fechaSalida;
    EditText txtDNI;
    Button btnConsultar;
    Button btnRegSalida;
    Date date;

    //refrenciando a la base de datos ////
    FirebaseDatabase db;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_salida_cliente);

        txtDNI = (EditText)findViewById(R.id.txtBuscarDniCliente);
        btnConsultar = (Button)findViewById(R.id.btnValidarEstado);
        txtVerPlaca = (TextView)findViewById(R.id.textViewPlaca);
        txtVerMarca = (TextView)findViewById(R.id.textViewMarca);
        txtVerModelo = (TextView)findViewById(R.id.textViewModelo);

        inicializarFirebase();

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarDNI();
            }
        });

        asignarReferencias();



    }




    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference();
    }

    public void consultarDNI(){
        reference.child("IngresoVehiculos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ControlAccesoEstacionamiento objVeh = null;
                String placa="", marca="", modelo="";

                for(DataSnapshot item:snapshot.getChildren()) {
                    objVeh = item.getValue(ControlAccesoEstacionamiento.class);
                    if(txtDNI.getText().toString().equals(objVeh.getPersonaDni())){

                        if(objVeh.getEstado() == 1) {
                            placa = objVeh.getPlaca();
                            marca = objVeh.getMarca();
                            modelo = objVeh.getModelo();
                            txtVerPlaca.setText(placa);
                            txtVerMarca.setText(marca);
                            txtVerModelo.setText(modelo);
                            break;
                            //Toast.makeText(MainRegSalidaCliente.this, "Placa: "+placa+", Marca: "+marca+", Modelo: "+modelo, Toast.LENGTH_SHORT).show();
                            // UPDATE DE DATOS DE SALIDA Y DE ESTADO set(hora)
                        }
                        else{
                            Toast.makeText(MainRegSalidaCliente.this,"El auto estado inactivo",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainRegSalidaCliente.this,"Validación exitosa!",Toast.LENGTH_SHORT).show();
                    }
                }

                //Toa.makeText(MainRegSalidaCliente.this, "Placa: "+placa+", Marca: "+marca+", Modelo: "+modelo, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void asignarReferencias(){

        btnRegSalida = findViewById(R.id.btnRegistrarIngreso);
        fechaSalida = findViewById(R.id.txtViewMostrarFechHorSalid);
        horaSalida = findViewById(R.id.txtViewMostTiempUsado);

        inicializarFirebase();
    }

    private void registrarSalida(){
        ControlAccesoEstacionamiento cae = new ControlAccesoEstacionamiento();
        date = new Date();
        SimpleDateFormat f= new SimpleDateFormat("d / MM / yyyy ");

        fechaSalida.setText(""+f.format(date));
        horaSalida.setText(date.getHours()+":"+date.getMinutes());
    }

}