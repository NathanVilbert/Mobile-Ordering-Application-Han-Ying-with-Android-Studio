package com.example.hanying.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hanying.Helper.SessionManager;
import com.example.hanying.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.widget.LinearLayout;

import com.example.hanying.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {
    private TextView custname, custemail, custphone;
    private LinearLayout homeBtn, menuBtn, locationBtn, profileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        custname = findViewById(R.id.custnameTxt);
        custemail = findViewById(R.id.custemailTxt);
        custphone = findViewById(R.id.phoneInput);

        bottomNavigation();

        String username = SessionManager.getInstance().getUsername();

        new GetProfileDataTask(username).execute();
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
                startActivity(new Intent(ProfileActivity.this, OrderListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MenuActivity.class));
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MapLocation.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ProfileActivity.class));
            }
        });
    }

    private class GetProfileDataTask extends AsyncTask<String, Void, String> {

        private String username;

        public GetProfileDataTask(String username) {
            this.username = username;
        }

        @Override
        protected String doInBackground(String... params) {
            String urlString = "http://192.168.1.2/hanying/profile.php?username=" + username;

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Setup GET request
                connection.setRequestMethod("GET");

                // Read response
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
                JSONObject jsonResult = new JSONObject(result);

                if (jsonResult.has("status") && jsonResult.getString("status").equals("success")) {
                    // Get the data array
                    JSONObject data = jsonResult.getJSONObject("data");

                    // Set data ke TextView
                    custname.setText(data.getString("custname"));
                    custemail.setText(data.getString("custemail"));
                    custphone.setText("+" + data.getString("custphone"));
                } else {
                    // Handle error
                    Toast.makeText(getApplicationContext(), jsonResult.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}