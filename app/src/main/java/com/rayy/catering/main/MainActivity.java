package com.rayy.catering.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rayy.catering.Map.MapActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.rayy.catering.R;
import com.rayy.catering.history.HistoryOrderActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    List<ModelCategories> modelCategoriesList = new ArrayList<>();
    List<ModelTrending> modelTrendingList = new ArrayList<>();
    CategoriesAdapter categoriesAdapter;
    TrendingAdapter trendingAdapter;
    ModelCategories modelCategories;
    ModelTrending modelTrending;
    RecyclerView rvCategories, rvTrending;
    CardView cvHistory;

    private GoogleMap mMap;
    private boolean isMapVisible = false; // Status peta

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Mengambil username dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String strUsername = sharedPreferences.getString("username", "User");

        // Menampilkan username di TextView
        TextView greetingTextView = findViewById(R.id.greetingTextView);
        greetingTextView.setText("Hallo, " + strUsername);

        // Inisialisasi TextView lokasi
        TextView locationTextView = findViewById(R.id.locationTextView);
        locationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Memulai MapActivity
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        setStatusbar();
        setInitLayout();
        setCategories();
        setTrending();
    }

    private void showMapFragment() {
        // Inisialisasi SupportMapFragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Lokasi yang ingin ditampilkan di peta (contoh: Bantul, Yogyakarta)
        LatLng location = new LatLng(-7.8833, 110.3333);

        // Tambahkan marker di lokasi
        mMap.addMarker(new MarkerOptions()
                .position(location)
                .title("Bantul, Yogyakarta"));

        // Pindahkan kamera ke lokasi dengan zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }

    private void setInitLayout() {
        cvHistory = findViewById(R.id.cvHistory);
        cvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryOrderActivity.class);
                startActivity(intent);
            }
        });

        rvCategories = findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(new GridLayoutManager(this, 3));
        rvCategories.setHasFixedSize(true);

        rvTrending = findViewById(R.id.rvTrending);
        rvTrending.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvTrending.setHasFixedSize(true);
    }

    private void setCategories() {
        modelCategories = new ModelCategories(R.drawable.ic_complete, "Complete Package");
        modelCategoriesList.add(modelCategories);
        modelCategories = new ModelCategories(R.drawable.ic_saving, "Saving Package");
        modelCategoriesList.add(modelCategories);
        modelCategories = new ModelCategories(R.drawable.ic_healthy, "Healthy Package");
        modelCategoriesList.add(modelCategories);
        modelCategories = new ModelCategories(R.drawable.ic_fast, "FastFood");
        modelCategoriesList.add(modelCategories);
        modelCategories = new ModelCategories(R.drawable.ic_event, "Event Packages");
        modelCategoriesList.add(modelCategories);
        modelCategories = new ModelCategories(R.drawable.ic_more_food, "Others");
        modelCategoriesList.add(modelCategories);

        categoriesAdapter = new CategoriesAdapter(this, modelCategoriesList);
        rvCategories.setAdapter(categoriesAdapter);
    }

    private void setTrending() {
        modelTrending = new ModelTrending(R.drawable.complete_1,"Menu Prasmanan", "2.200 disukai");
        modelTrendingList.add(modelTrending);
        modelTrending = new ModelTrending(R.drawable.complete_2,"Menu Traditional", "1.220 disukai");
        modelTrendingList.add(modelTrending);
        modelTrending = new ModelTrending(R.drawable.complete_3,"Menu Fast Food", "345 disukai");
        modelTrendingList.add(modelTrending);
        modelTrending = new ModelTrending(R.drawable.complete_4,"Menu Healthy", "590 disukai");
        modelTrendingList.add(modelTrending);

        trendingAdapter = new TrendingAdapter(this, modelTrendingList);
        rvTrending.setAdapter(trendingAdapter);
    }

    public void setStatusbar() {
        if (Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(@NonNull Activity activity, final int bits, boolean on) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        window.setAttributes(layoutParams);
    }
}
