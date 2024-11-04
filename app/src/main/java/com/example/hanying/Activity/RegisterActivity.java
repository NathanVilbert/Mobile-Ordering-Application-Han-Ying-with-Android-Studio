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

import com.example.hanying.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    private EditText custnameEditText, usernameEditText, emailEditText, phoneEditText, passwordEditText;
    private Button registerButton;
    private TextView haveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        haveAccount = findViewById(R.id.haveAccTxt);
        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginActivity();
            }
        });

        custnameEditText = findViewById(R.id.edtCustname);
        usernameEditText = findViewById(R.id.edtUsername);
        emailEditText = findViewById(R.id.edtEmail);
        phoneEditText = findViewById(R.id.edtPhone);
        passwordEditText = findViewById(R.id.edtPassword);
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        registerButton = findViewById(R.id.btnRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String custname = custnameEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String custemail = emailEditText.getText().toString();
                String custphone = phoneEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                new RegisterTask().execute(custname, username, custemail, custphone, password);
            }
        });
    }

    private class RegisterTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String urlString = "http://192.168.1.2/hanying/register.php";
//            String urlString = "http://192.168.0.107/hanying/register.php";

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // Setup POST request
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Send data
                String postData = "username=" + params[0] + "&custname=" + params[1] + "&custemail=" + params[2] + "&custphone=" + params[3] +
                        "&password=" + params[4];
                OutputStream os = connection.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();

                // Read response
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                br.close();
                return response.toString();

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
                    // Registration success
                    Toast.makeText(getApplicationContext(), jsonResult.getString("message"), Toast.LENGTH_SHORT).show();

                    // Handle registration success action
                    // Pindah ke menu login setelah registrasi berhasil
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Menutup aktivitas registrasi agar pengguna tidak dapat kembali ke sini
                } else {
                    // Registration failed
                    Toast.makeText(getApplicationContext(), jsonResult.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void goToLoginActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
