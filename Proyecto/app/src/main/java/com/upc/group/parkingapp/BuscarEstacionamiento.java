package com.upc.group.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.maps.android.ui.IconGenerator;
import com.upc.group.parkingapp.modelos.Empresa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BuscarEstacionamiento extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    GoogleMap mMap;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLatitude = 0;
    double currentLongitude = 0;
    SearchView svAddressToFind;

    Button btnBuscar;
    FirebaseDatabase db;
    DatabaseReference reference;
    List<Empresa> lstEmpresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_estacionamiento);
        inicializarFirebase();
        getEmpresasFromFireBase();
        asignarReferencias();
        getCurrentLocation();
    }

    private void asignarReferencias() {
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapEmpresas);
        svAddressToFind = findViewById(R.id.svAddressToFind);

        svAddressToFind.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String strAddress = svAddressToFind.getQuery().toString();
                List<Address> addressList = null;
                if (strAddress != null || !strAddress.equals("")) {
                    Geocoder geocoder = new Geocoder(BuscarEstacionamiento.this);
                    try {
                        addressList = geocoder.getFromLocationName(strAddress, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address mainAddress = addressList.get(0);
                    LatLng mainLocation = new LatLng(mainAddress.getLatitude(), mainAddress.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(mainLocation).title(strAddress));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mainLocation, 15));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BuscarEstacionamiento.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        } else {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentLatitude = location.getLatitude();
                        currentLongitude = location.getLongitude();

                        mostrarMapa();
                    }
                }
            });
        }
    }

    private void mostrarMapa() {
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.style_json)));

                LatLng currentPosition = new LatLng(currentLatitude, currentLongitude);
                mMap.addMarker(new MarkerOptions().position(currentPosition).title("MyLocation"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15));

                crearEmpresasLocation();

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        if (marker.getSnippet() != null) {
                            Intent intent = new Intent(BuscarEstacionamiento.this, VerDetalleSede.class);
                            intent.putExtra("empresaId", marker.getSnippet());
                            intent.putExtra("personaId", getIntent().getStringExtra("personaId"));
                            startActivity(intent);
                        }
                        return false;
                    }
                });
            }
        });
    }

    private void crearEmpresasLocation() {
        IconGenerator iconGen = new IconGenerator(BuscarEstacionamiento.this);

        MarkerOptions markerOptions;
        LatLng latLngEmp;
        for (Empresa e:lstEmpresas) {
            latLngEmp = new LatLng(Double.parseDouble(e.getLatitud()), Double.parseDouble(e.getLongitud()));
            markerOptions = new MarkerOptions().
                    icon(BitmapDescriptorFactory.fromBitmap(iconGen.makeIcon(e.getNombreLocal()))).
                    position(latLngEmp).
                    snippet(e.getId()).
                    anchor(iconGen.getAnchorU(), iconGen.getAnchorV());

            mMap.addMarker(
                    markerOptions
                            /*
                            new MarkerOptions().position(latLngEmp)
                                    .title(e.getNombreLocal())
                                    .icon(changeIcon(getApplicationContext(), R.drawable.trademark_parking_location))
                                    .snippet(e.getId())*/
            );
        }
    }

    private void getEmpresasFromFireBase(){
        lstEmpresas = new ArrayList<>();

        reference.child("Empresa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item:snapshot.getChildren()) {
                    Empresa e = item.getValue(Empresa.class);
                    if (e.getLatitud()!=null && e.getLongitud()!=null) {
                        lstEmpresas.add(e);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private BitmapDescriptor changeIcon(Context context, int id) {
        Drawable image = ContextCompat.getDrawable(context, id);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(image.getIntrinsicWidth(), image.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        image.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference();
    }
}