package com.s23010804.findmyplayground;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.s23010804.findmyplayground.models.Playground;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int LOCATION_REQUEST_CODE = 101;
    private List<Playground> playgroundList;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // 🔹 Indoor Playground Data (with real or approx coordinates)
        playgroundList = new ArrayList<>();
        playgroundList.add(new Playground("Fun Factory – Mount Lavinia", 6.8164, 79.8820));
        playgroundList.add(new Playground("PG Matin Kiddies Kingdom – Colombo", 6.9271, 79.8612));
        playgroundList.add(new Playground("TinyHop – Kotte", 6.8941, 79.9003));
        playgroundList.add(new Playground("Super Kids Havelock", 6.9000, 79.8615));
        playgroundList.add(new Playground("Cheeky Monkeys – Wattala", 6.9915, 79.9158));
        playgroundList.add(new Playground("Super Kids Battaramulla", 6.9037, 79.9185));
        playgroundList.add(new Playground("Adventure Zone – Shangri‑La", 6.9280, 79.8428));
        playgroundList.add(new Playground("Kids Island – Colombo", 6.9180, 79.8400));
        playgroundList.add(new Playground("Sports World – Peliyagoda", 6.9710, 79.8820));
        playgroundList.add(new Playground("Score Arena – Malabe", 6.9150, 79.9600));
        playgroundList.add(new Playground("Riders – Kolonnawa", 6.9450, 79.9180));

        // 🔹 Indoor playgrounds in Kandy and Central
        playgroundList.add(new Playground("Kandy Indoor Sports Center (KISC)", 7.2906, 80.6336));
        playgroundList.add(new Playground("Kandy Sports Club (indoor)", 7.31653, 80.63389));
        playgroundList.add(new Playground("Asgiriya Stadium (indoor/multipurpose)", 7.29972, 80.6339));
        playgroundList.add(new Playground("Madawala Indoor Playground (approx)", 7.2906, 80.6336));
        playgroundList.add(new Playground("FTC Kandy Indoor Sports", 7.2906, 80.6336));
        playgroundList.add(new Playground("Triple I Sports Park (indoor)", 7.2906, 80.6336));
        playgroundList.add(new Playground("Synergy Sports Academy – Gampola", 7.1600, 80.6300));
        playgroundList.add(new Playground("Kandy Futsal (indoor)", 7.2906, 80.6336));
        playgroundList.add(new Playground("Toshiba Indoor Stadium (approx)", 7.2906, 80.6336));

        // Load the map
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
            return;
        }

        enableUserLocation();

        // 🔹 Show all playgrounds on map
        for (Playground pg : playgroundList) {
            LatLng location = new LatLng(pg.getLatitude(), pg.getLongitude());
            mMap.addMarker(new MarkerOptions().position(location).title(pg.getName()));
        }

        // 🔹 Move camera to first playground
        if (!playgroundList.isEmpty()) {
            LatLng first = new LatLng(playgroundList.get(0).getLatitude(),
                    playgroundList.get(0).getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(first, 7));
        }

        // 🔹 Find & show nearby playgrounds (filtered markers)
        findNearbyPlaygrounds();

        // 🔹 Marker click: open dialog
        mMap.setOnMarkerClickListener(marker -> {
            LatLng pos = marker.getPosition();
            String pgName = marker.getTitle();

            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle(pgName)
                    .setMessage("Choose an option")
                    .setPositiveButton("View Details", (dialog, which) -> {
                        Intent intent = new Intent(MapActivity.this, PlaygroundDetailActivity.class);
                        intent.putExtra("name", pgName);
                        intent.putExtra("latitude", pos.latitude);
                        intent.putExtra("longitude", pos.longitude);
                        startActivity(intent);
                    })
                    .setNegativeButton("Navigate", (dialog, which) -> {
                        String uri = "google.navigation:q=" + pos.latitude + "," + pos.longitude;
                        Intent navIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        navIntent.setPackage("com.google.android.apps.maps");

                        if (navIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(navIntent);
                        } else {
                            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://www.google.com/maps/dir/?api=1&destination="
                                            + pos.latitude + "," + pos.longitude));
                            startActivity(webIntent);
                        }
                    })
                    .show();

            return true;
        });
    }

    private void enableUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void findNearbyPlaygrounds() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                double userLat = location.getLatitude();
                double userLng = location.getLongitude();

                List<Playground> nearby = new ArrayList<>();
                for (Playground pg : playgroundList) {
                    float distance = pg.distanceTo(userLat, userLng);
                    if (distance <= 10000) { // within 10 km
                        nearby.add(pg);
                    }
                }

                showNearbyOnMap(nearby);
            }
        });
    }

    private void showNearbyOnMap(List<Playground> nearbyPlaygrounds) {
        mMap.clear(); // clear old markers

        for (Playground pg : nearbyPlaygrounds) {
            LatLng loc = new LatLng(pg.getLatitude(), pg.getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc).title(pg.getName()));
        }

        if (!nearbyPlaygrounds.isEmpty()) {
            LatLng first = new LatLng(nearbyPlaygrounds.get(0).getLatitude(),
                    nearbyPlaygrounds.get(0).getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(first, 12));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableUserLocation();
                findNearbyPlaygrounds();
            }
        }
    }
}
