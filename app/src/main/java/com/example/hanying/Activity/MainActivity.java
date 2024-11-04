package com.example.hanying.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hanying.Adapter.CategoryAdapter;
import com.example.hanying.Adapter.PopularAdapter;
import com.example.hanying.Domain.CategoryDomain;
import com.example.hanying.Domain.FoodDomain;
import com.example.hanying.Helper.SessionManager;
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

public class MainActivity extends AppCompatActivity {

    private Button orderBtn;
    private LinearLayout homeBtn, menuBtn, locationBtn, profileBtn;
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView welcomeTxt = findViewById(R.id.welcomeTxt);
        String fullName = SessionManager.getInstance().getFullName();
        welcomeTxt.setText("Hi, " + fullName + "!");

        bottomNavigation();

        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        new CategoryList().execute();

        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        new PopularList().execute();

        orderBtn = findViewById(R.id.orderhereBtn);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });
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
                startActivity(new Intent(MainActivity.this, OrderListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapLocation.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
    }

    private class CategoryList extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String urlString = "http://192.168.1.2/hanying/category_item.php";
//            String urlString = "http://192.168.0.107/hanying/category_item.php";

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

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

                // Proses data JSON ke dalam ArrayList<CategoryDomain>
                ArrayList<CategoryDomain> categoryList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String catName = jsonObject.getString("catName");
                    String catImage = jsonObject.getString("catImage");

                    // Tambahkan data ke ArrayList
                    categoryList.add(new CategoryDomain(catName, catImage));
                }

                // Inisialisasi RecyclerView dan adapter
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
                adapter = new CategoryAdapter(categoryList);
                recyclerViewCategoryList.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Error processing JSON (Category)", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class PopularList extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String urlString = "http://192.168.1.2/hanying/popular_item.php";
//            String urlString = "http://192.168.0.107/hanying/popular_item.php";

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");

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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewPopularList.setLayoutManager(linearLayoutManager);
                adapter2 = new PopularAdapter(foodList);
                recyclerViewPopularList.setAdapter(adapter2);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Error processing JSON (Popular Menu)", Toast.LENGTH_SHORT).show();
            }
        }
    }

}