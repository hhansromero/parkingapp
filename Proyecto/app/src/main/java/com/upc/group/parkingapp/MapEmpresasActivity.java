package com.upc.group.parkingapp;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
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

        googleMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));

        getData();

        LatLng ubicacion;
        for (Empresa e:lstEmpresas) {
            ubicacion = new LatLng(Double.parseDouble(e.getLatitud()), Double.parseDouble(e.getLongitud()));
            mMap.addMarker(
                    new MarkerOptions().position(ubicacion)
                            .title(e.getNombreLocal())
                            .icon(changeIcon(getApplicationContext(), R.drawable.trademark_parking_location))
            );
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 16));
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(MapEmpresasActivity.this, VerDetalleSede.class);
                intent.putExtra("nombreEmpresa", marker.getTitle());
                startActivity(intent);
                return false;
            }
        });
    }

    private void getData() {
        jsonEmpresas = getIntent().getStringExtra("jsonEmpresas");
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Empresa>>(){}.getType();
        lstEmpresas = new ArrayList<>();
        lstEmpresas = gson.fromJson(jsonEmpresas, listType);
    }

    private BitmapDescriptor changeIcon(Context context, int id) {
        Drawable image = ContextCompat.getDrawable(context, id);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(image.getIntrinsicWidth(), image.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        image.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}