package com.example.hanying.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.hanying.Adapter.ProductAdapter;
import com.example.hanying.Domain.ProductDomain;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditItemActivity extends AppCompatActivity {

    ProductAdapter adapter;
    public static ArrayList<ProductDomain> arraylistProductDomain = new ArrayList<>();
    EditText edtproductName, edtPrice, edtStock, edtImage, editTextId;
    Button btnUpdate, btnHapus, btnKembali;
    String url_update_makanan = "http://192.168.1.2/hanying/edit_cart_item.php";
    String url_delete_makanan = "http://192.168.1.2/hanying/delete_item.php";
    private int position;
    private AlertDialog.Builder alertDialogBuilder;
    String productID, productName, image;
    double price;
    int stock;
    ProductDomain product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            position = intent.getExtras().getInt("position", -1);
        } else {
            finish();
            return;
        }
        setContentView(R.layout.activity_edit_item);
        btnUpdate = findViewById(R.id.btnEdit);
        btnHapus = findViewById(R.id.btnHapus);
        btnKembali = findViewById(R.id.btnBatal);
        edtproductName = findViewById(R.id.edtNama);
        edtPrice = findViewById(R.id.edtPrice);
        edtStock = findViewById(R.id.edtStock);
        edtImage = findViewById(R.id.edtImage);
        editTextId = findViewById(R.id.id_product);

        edtproductName.setText(CartActivity.arraylistproduct.get(position).getProductName());
        edtPrice.setText(String.valueOf(CartActivity.arraylistproduct.get(position).getPrice()));
        edtStock.setText(String.valueOf(CartActivity.arraylistproduct.get(position).getStock()));
        edtImage.setText(CartActivity.arraylistproduct.get(position).getImage());
        editTextId.setText(CartActivity.arraylistproduct.get(position).getProductID());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtproductName.getText().toString().isEmpty()) {
                    Toast.makeText(EditItemActivity.this, "please enter your productName...", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edtPrice.getText().toString().isEmpty()) {
                    Toast.makeText(EditItemActivity.this, "please input price...", Toast.LENGTH_SHORT).show();
                } else if (edtStock.getText().toString().isEmpty()) {
                    Toast.makeText(EditItemActivity.this, "please input stock", Toast.LENGTH_SHORT).show();
                } else if (edtImage.getText().toString().isEmpty()) {
                    Toast.makeText(EditItemActivity.this, "please enter your URL Image...", Toast.LENGTH_SHORT).show();
                } else
                    callPUTDataMethod(edtproductName.getText().toString(), Double.parseDouble(edtPrice.getText().toString()), Integer.parseInt(edtStock.getText().toString()), edtImage.getText().toString(),
                            editTextId.getText().toString());
            }
        });

        adapter = new ProductAdapter(this, arraylistProductDomain);

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtproductName.getText().toString().isEmpty()) {
                    Toast.makeText(EditItemActivity.this, "please enter your data", Toast.LENGTH_SHORT).show();
                } else if (edtPrice.getText().toString().isEmpty()) {
                    Toast.makeText(EditItemActivity.this, "please enter your data", Toast.LENGTH_SHORT).show();
                } else if (edtStock.getText().toString().isEmpty()) {
                    Toast.makeText(EditItemActivity.this, "please enter your data", Toast.LENGTH_SHORT).show();
                } else if (edtImage.getText().toString().isEmpty()) {
                    Toast.makeText(EditItemActivity.this, "please enter your data", Toast.LENGTH_SHORT).show();
                } else
                    deletedata(CartActivity.arraylistproduct.get(position).getProductID(),
                            CartActivity.arraylistproduct.get(position).getProductName(),
                            CartActivity.arraylistproduct.get(position).getPrice(),
                            CartActivity.arraylistproduct.get(position).getStock(),
                            CartActivity.arraylistproduct.get(position).getImage());
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kembali();
            }
        });
    }

    private void kembali() {
        final ProgressDialog progressDialog = new ProgressDialog(EditItemActivity.this);
        progressDialog.show();

        Toast.makeText(EditItemActivity.this, "kembali ke menu utama!!!.....", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
        finish();
        progressDialog.dismiss();
    }

    private void deletedata(final String productID, final String productName, final double price, final int stock, final String image) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting......");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url_delete_makanan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            adapter.notifyDataSetChanged();

                            AlertDialog.Builder builder = new AlertDialog.Builder(EditItemActivity.this);
                            builder.setCancelable(true);
                            builder.setTitle("Yakin ingin menghapus?");
                            builder.setMessage("Data yang sudah dihapus tidak dapat dikembalikan");

                            builder.setPositiveButton("YAKIN",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            edtproductName.setText("");
                                            edtPrice.setText("");
                                            edtStock.setText("");
                                            edtImage.setText("");
                                            editTextId.setText("");

                                            Toast.makeText(EditItemActivity.this, "Data deleted Successfully...", Toast.LENGTH_SHORT).show();

                                            startActivity(new Intent(getApplicationContext(), AdminActivity.class));

                                            finish();
                                            progressDialog.dismiss();

                                            // Perbarui data lokal setelah penghapusan
                                            if (!arraylistProductDomain.isEmpty() && position < arraylistProductDomain.size()) {
                                                arraylistProductDomain.remove(position);
                                                if (!CartActivity.arraylistproduct.isEmpty() && position < CartActivity.arraylistproduct.size()) {
                                                    CartActivity.arraylistproduct.remove(position);
                                                } else {
                                                    Log.e("DEBUG", "Invalid position or empty arraylistProduct");
                                                }
                                                adapter.notifyDataSetChanged();
                                            } else {
                                                Log.e("DEBUG", "Invalid positionor empty arraylistProduct");
                                            }
                                        }
                                    });
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(EditItemActivity.this, "Kamu menekan cancel, perhatikan data yang akan anda hapus kemnbali !~!", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    adapter.notifyDataSetChanged();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        } catch (JSONException jsonObject) {
                            jsonObject.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditItemActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(EditItemActivity.this, "Fail to delete data..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("productID", productID);
                params.put("productName", productName);
                params.put("price", String.valueOf(price));
                params.put("stock", String.valueOf(stock));
                params.put("image", image);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void callPUTDataMethod(String productName, double price, int stock, String image, String productID) {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        StringRequest request = new StringRequest(Request.Method.POST, url_update_makanan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DEBUG", "response" + response);
                        adapter.notifyDataSetChanged();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            edtproductName.setText("");
                            edtPrice.setText("");
                            edtStock.setText("");
                            edtImage.setText("");
                            editTextId.setText("");

                            Toast.makeText(EditItemActivity.this, "Data Updated..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), AdminActivity.class));

                            // Update local data after the update
                            if (!arraylistProductDomain.isEmpty() && position < arraylistProductDomain.size()) {
                                arraylistProductDomain.get(position).setProductName(productName);
                                arraylistProductDomain.get(position).setPrice(price);
                                arraylistProductDomain.get(position).setStock(stock);
                                arraylistProductDomain.get(position).setImage(image);

                                // Update EditText widgets with new data
                                populateData();

                            } else {
                                Log.e("DEBUG", "Invalid position or empty arraylistProduct");
                            }

                            finish();
                        } catch (JSONException jsonObject) {
                            jsonObject.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditItemActivity.this, "Fail to update data..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                adapter.notifyDataSetChanged();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("productID", productID);
                params.put("productName", productName);
                params.put("price", String.valueOf(price));
                params.put("stock", String.valueOf(stock));
                params.put("image", image);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(EditItemActivity.this);
        queue.add(request);
    }
    private void populateData() {
        edtproductName.setText(arraylistProductDomain.get(position).getProductName());
        edtPrice.setText(String.valueOf(arraylistProductDomain.get(position).getPrice()));
        edtStock.setText(String.valueOf(arraylistProductDomain.get(position).getStock()));
        edtImage.setText(arraylistProductDomain.get(position).getImage());
        editTextId.setText(arraylistProductDomain.get(position).getProductID());
    }
}
