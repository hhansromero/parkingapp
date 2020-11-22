package com.upc.group.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BuscarEstacionamiento extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mMapView;
    Button btnVerDetalleSede;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_estacionamiento);
        asignarReferencias();

        mMapView = (MapView) findViewById(R.id.mapViewBuscarEst);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
    }

    private void asignarReferencias() {
        btnVerDetalleSede = findViewById(R.id.btnVerDetalleSede);

        btnVerDetalleSede.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuscarEstacionamiento.this, VerDetalleSede.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}