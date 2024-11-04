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

public class AddItemActivity extends AppCompatActivity {
    ProgressBar progressBar;
    EditText edtproductName, edtPrice, edtStock, edtImage;

    Button btnTambah;
    String productID, productName, image;
    double price;
    int stock;

    String url_tambah_makanan = "http://192.168.1.2/hanying/add_to_cart.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        edtproductName = findViewById(R.id.edtProductName);
        edtPrice = findViewById(R.id.edtPrice);
        edtStock = findViewById(R.id.edtStock);
        btnTambah = findViewById(R.id.btnAddToCart);
        edtImage = findViewById(R.id.edtImage);
        progressBar = findViewById(R.id.progressBar);

        btnTambah.setOnClickListener(v -> {
            productName = edtproductName.getText().toString();
            price = Double.parseDouble(edtPrice.getText().toString());
            stock = Integer.parseInt(edtStock.getText().toString());
            image = edtImage.getText().toString();
            progressBar.setVisibility(View.VISIBLE);
            RequestQueue queue = Volley.newRequestQueue(AddItemActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    url_tambah_makanan, response -> {
                try {
                    JSONObject jObj = new JSONObject(response);
                    int sukses = jObj.getInt("success");
                    if (sukses == 1) {
                        Toast.makeText(AddItemActivity.this, "Data Product" +
                                "Berhasil disimpan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));

                        finish();
                    }
                    progressBar.setVisibility(View.GONE);
                } catch (Exception ex) {
                    Log.e("Error", ex.toString());
                    progressBar.setVisibility(View.GONE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error: ", error.getMessage());
                    Toast.makeText(AddItemActivity.this, "Silahkan cek koneksi " +
                            "internet Anda!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("productName", productName);
                    params.put("price", String.valueOf(price));
                    params.put("stock", String.valueOf(stock));
                    params.put("image", image);

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
