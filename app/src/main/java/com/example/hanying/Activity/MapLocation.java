package com.example.hanying.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanying.Adapter.TrendsAdapter;
import com.example.hanying.Domain.TrendsDomain;
import com.example.hanying.R;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MapLocation extends AppCompatActivity implements OnMapReadyCallback, TrendsAdapter.buttonClickListener {
    private LinearLayout homeBtn, menuBtn, locationBtn, profileBtn;
    private RecyclerView recyclerView;
    private ArrayList<Integer> integerArrayList;
    private TrendsAdapter adapterTrends;
    private ArrayList<TrendsDomain> trendItemList;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private GoogleMap map;
    private LatLng home, position;
    private int PLACE_AUTO = 1;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        home = new LatLng(-6.256201442141357, 106.61856120065639);
        map.addMarker(new MarkerOptions().position(home).title("Welcome to UMN!")).showInfoWindow();
        map.moveCamera(CameraUpdateFactory.newLatLng(home));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 16));
        map.setTrafficEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);

        recyclerView = findViewById(R.id.view1);

        trendItemList = new ArrayList<>();
        trendItemList.add(new TrendsDomain(R.drawable.allo, "Hanying Allogio",1));
        trendItemList.add(new TrendsDomain(R.drawable.bsd, "Hanying BSD City",2));
        trendItemList.add(new TrendsDomain(R.drawable.alsut, "Hanying Alam Sutera",3));

        TrendsAdapter adapterTrends = new TrendsAdapter(this, trendItemList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapterTrends);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            initializeLocation();
        }
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mapFragment);
            mapFragment.getMapAsync(this);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        Button terrain = findViewById(R.id.btnTerrainMode);
        Button hybrid = findViewById(R.id.btnHybrid);
        Button satellite = findViewById(R.id.btnSatelliteMode);
        Button normal = findViewById(R.id.btnNormalMode);

        terrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
        });

        hybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });

        satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        bottomNavigation();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.order_btn);
        homeBtn = findViewById(R.id.homeBtn);
        menuBtn = findViewById(R.id.menuBtn);
        locationBtn = findViewById(R.id.locationBtn);
        profileBtn = findViewById(R.id.profileBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapLocation.this, OrderListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapLocation.this, MainActivity.class));
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapLocation.this, MenuActivity.class));
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapLocation.this, MapLocation.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapLocation.this, ProfileActivity.class));
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTO) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                LatLng search = place.getLatLng();
                double x = place.getLatLng().latitude;
                double y = place.getLatLng().longitude;
                String address = place.getAddress().toString();
                String phone = place.getPhoneNumber().toString();
                String name = place.getName().toString();
                String snippet = address + System.getProperty("line.separator") + phone;
                map.addMarker(new MarkerOptions().position(search).title(name).snippet(snippet)).showInfoWindow();
                map.animateCamera(CameraUpdateFactory.newLatLng(search));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(search, 18));

            }
        }
    }

    private void initializeLocation() {

    }

//    @Override
//    public void onItemClick(int position) {
//        int p  = position +1;
//        Toast.makeText(MapLocation.this, "Item Clicked" + p,Toast.LENGTH_SHORT).show();
//
//    }

    @Override
    public void onButtonClick(int position) {
        TrendsDomain trendItem = trendItemList.get(position);
        int buttonAction = trendItem.getButtonAction();

        switch (buttonAction) {
            case 1:
                LatLng location1 = new LatLng(-6.280770, 106.663774);
                map.addMarker(new MarkerOptions().position(location1).title("Hanying Allogio")).showInfoWindow();
                map.animateCamera(CameraUpdateFactory.newLatLng(location1));
                break;

            case 2:
                LatLng location2 = new LatLng(-6.256201442141357, 106.61856120065639);
                map.addMarker(new MarkerOptions().position(location2).title("Hanying BSD City")).showInfoWindow();
                map.animateCamera(CameraUpdateFactory.newLatLng(location2));
                break;

            case 3:
                LatLng location3 = new LatLng(-6.220716, 106.659409);
                map.addMarker(new MarkerOptions().position(location3).title("Hanying Alam Sutera")).showInfoWindow();
                map.animateCamera(CameraUpdateFactory.newLatLng(location3));
                break;
        }
    }
}