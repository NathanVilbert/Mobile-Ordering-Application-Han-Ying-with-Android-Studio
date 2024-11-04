package com.example.hanying.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hanying.Helper.SessionManager;
import com.example.hanying.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView textViewCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textViewCreateAccount = findViewById(R.id.createTxt);
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegisterActivity();
            }
        });

        usernameEditText = findViewById(R.id.edtUsername2);
        passwordEditText = findViewById(R.id.edtPassword2);
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        loginButton = findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                new LoginTask().execute(username, password);
            }
        });
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String urlString = "http://192.168.1.2/hanying/login.php";
//            String urlString = "http://192.168.0.107/hanying/login.php";

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Setup POST request
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Send data
                String postData = "username=" + params[0] + "&password=" + params[1];
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(postData.getBytes());
                }

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

                if (jsonResult.getString("status").equals("success")) {
                    // Login success
                    Toast.makeText(getApplicationContext(), jsonResult.getString("message"), Toast.LENGTH_SHORT).show();

                    // Handle login success action, e.g., start new activity
                    if (jsonResult.has("role")) {
                        String role = jsonResult.getString("role");

                        // Pemilihan aktivitas berdasarkan peran (role)
                        Class<?> TargetActivity;
                        switch (role) {
                            case "admin":
                                TargetActivity = AdminActivity.class;
                                break;
                            case "user":
                                TargetActivity = MainActivity.class;

                                String fullName = jsonResult.getString("custname");
                                String userName = jsonResult.getString("username");

                                SessionManager.getInstance().setUserData(userName, fullName);
                                break;
                            default:
                                // Peran (role) tidak valid
                                Toast.makeText(getApplicationContext(), "Undefined role", Toast.LENGTH_SHORT).show();
                                return;
                        }

                        // Pindah ke aktivitas yang sesuai
                        Intent intent = new Intent(LoginActivity.this, TargetActivity);
                        startActivity(intent);

                        finish(); // Menutup aktivitas login agar pengguna tidak dapat kembali ke sini
                    } else {
                        // Role tidak ditemukan dalam respons
                        Toast.makeText(getApplicationContext(), "Role not found in response", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Login failed
                    Toast.makeText(getApplicationContext(), jsonResult.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void goToRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
