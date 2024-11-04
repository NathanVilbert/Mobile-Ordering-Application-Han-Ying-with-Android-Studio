package com.example.hanying.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanying.Adapter.MenuAdapter;
import com.example.hanying.Domain.FoodDomain;
import com.example.hanying.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout homeBtn, menuBtn, locationBtn, profileBtn;
    private RecyclerView
            recyclerViewMenuList,
            recyclerViewMenuList2,
            recyclerViewMenuList3,
            recyclerViewMenuList4,
            recyclerViewMenuList5;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bottomNavigation();

        recyclerViewMenuList = findViewById(R.id.menuview);
        recyclerViewMenuList2 = findViewById(R.id.menuview2);
        recyclerViewMenuList3 = findViewById(R.id.menuview3);
        recyclerViewMenuList4 = findViewById(R.id.menuview4);
        recyclerViewMenuList5 = findViewById(R.id.menuview5);

        recyclerViewMenu("Steam", recyclerViewMenuList);
        recyclerViewMenu("Fried", recyclerViewMenuList2);
        recyclerViewMenu("Soup", recyclerViewMenuList3);
        recyclerViewMenu("Noodles", recyclerViewMenuList4);
        recyclerViewMenu("Drinks", recyclerViewMenuList5);
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
                startActivity(new Intent(MenuActivity.this, OrderListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MenuActivity.class));
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MapLocation.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
            }
        });
    }

    private void recyclerViewMenu(String category, RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MenuActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(null);
        new MenuTask(category, recyclerView).execute();
    }

    private class MenuTask extends AsyncTask<Void, Void, String> {
        private String category;
        private RecyclerView recyclerView;

        public MenuTask(String category, RecyclerView recyclerView) {
            this.category = category;
            this.recyclerView = recyclerView;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String urlString = "http://192.168.1.2/hanying/menu.php?productCat=" + category;
//            String urlString = "http://192.168.0.107/hanying/menu.php?productCat=" + category;

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Set metode permintaan menjadi GET
                connection.setRequestMethod("GET");

                // Baca response
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }

                    return response.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray jsonArray = new JSONArray(result);

                // Proses data JSON ke dalam ArrayList<FoodDomain>
                ArrayList<FoodDomain> foodList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String prodName = jsonObject.getString("productName");
                    String prodImage = jsonObject.getString("image");
                    String prodDesc = jsonObject.getString("productDesc");
                    double prodPrice = jsonObject.getDouble("price");

                    // Tambahkan data ke ArrayList
                    foodList.add(new FoodDomain(prodName, prodImage, prodDesc, prodPrice));
                }

                // Inisialisasi RecyclerView dan adapter
                adapter = new MenuAdapter(foodList);
                recyclerView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MenuActivity.this, "Error processing JSON (Menu)", Toast.LENGTH_SHORT).show();
            }
        }
    }
}