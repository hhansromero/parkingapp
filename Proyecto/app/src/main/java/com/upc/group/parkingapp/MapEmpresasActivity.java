package com.upc.group.parkingapp;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.upc.group.parkingapp.modelos.Empresa;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapEmpresasActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String jsonEmpresas;
    List<Empresa> lstEmpresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_empresas);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapEmpresas);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        getData();

        LatLng ubicacion;
        for (Empresa e:lstEmpresas) {
            ubicacion = new LatLng(Double.parseDouble(e.getLatitud()), Double.parseDouble(e.getLongitud()));
            mMap.addMarker(new MarkerOptions().position(ubicacion).title(e.getNombreLocal()));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 16));
        }
    }

    private void getData() {
        jsonEmpresas = getIntent().getStringExtra("jsonEmpresas");
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Empresa>>(){}.getType();
        lstEmpresas = new ArrayList<>();
        lstEmpresas = gson.fromJson(jsonEmpresas, listType);
    }
}