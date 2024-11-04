package com.example.hanying.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hanying.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddUserActivity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText edtusername, edtcustname, edtcustemail, edtcustphone, edtrole, edtpassword;
    Button btnAdd;
    String username, custname, custemail, custphone, role, password;

    String url_tambah_user ="http://192.168.1.2/hanying/add_to_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        edtusername = findViewById(R.id.edtusername);
        edtcustname = findViewById(R.id.edtcustname);
        edtcustemail = findViewById(R.id.edtcustemail);
        edtcustphone = findViewById(R.id.edtcustphone);
        edtrole = findViewById(R.id.edtrole);
        edtpassword = findViewById(R.id.edtpassword);
        progressBar = findViewById(R.id.progressBar);
        btnAdd = findViewById(R.id.btnadd);

        btnAdd.setOnClickListener(v -> {
            username = edtusername.getText().toString();
            custname = edtcustname.getText().toString();
            custemail = edtcustemail.getText().toString();
            custphone = edtcustphone.getText().toString();
            role = edtrole.getText().toString();
            password = edtpassword.getText().toString();
            progressBar.setVisibility(View.VISIBLE);
            RequestQueue queue = Volley.newRequestQueue(AddUserActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    url_tambah_user, response -> {
                try {
                    if (response.startsWith("{")) {
                        JSONObject jObj = new JSONObject(response);
                        int sukses = jObj.getInt("success");
                        if (sukses == 1) {
                            Toast.makeText(AddUserActivity.this, "Data User" +
                                    "Berhasil disimpan", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                            finish();
                        } else {
                            Toast.makeText(AddUserActivity.this, "Gagal menyimpan data. Username sudah digunakan.", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(AddUserActivity.this, "Non-JSON Response: " + response, Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                    ex.printStackTrace();
                    Toast.makeText(AddUserActivity.this, "Error converting response to JSON", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error: ", error.getMessage());
                    Toast.makeText(AddUserActivity.this, "Silahkan cek koneksi " +
                            "internet Anda!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }) {
                @Override
                protected Map<String,String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("custname", custname);
                    params.put("custemail", custemail);
                    params.put("custphone", custphone);
                    params.put("role", role);
                    params.put("password", password);

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-type", "application/x-www-form-urlencoded");
                    return params;
                }

            };
            queue.getCache().clear();
            queue.add(stringRequest);
        });
    }
}
