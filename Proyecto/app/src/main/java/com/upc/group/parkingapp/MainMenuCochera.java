package com.upc.group.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuCochera extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cochera);

        Button btn = (Button) findViewById(R.id.btnIngresoCliente);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainRegIngresoCliente.class);
                startActivityForResult(intent, 0);
            }
        });

        Button btnDos = (Button) findViewById(R.id.btnSalidaCliente);
        btnDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainRegSalidaCliente.class);
                startActivityForResult(intent, 0);
            }
        });

    }
}