package com.example.hanying.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hanying.R;
import com.example.hanying.Domain.ProductDomain;
import com.example.hanying.Adapter.ProductAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ProductAdapter adapter;

    public static ArrayList<ProductDomain> arraylistproduct = new ArrayList<>();

    ProductDomain product;

    ListView lv;

    ArrayList<HashMap<String, String>> list_makanan;

    String url_get_product = "http://192.168.1.2/hanying/get_cart_items.php";

    private static final String TAG_PRODUCT = "data";
    private static final String TAG_ID = "productID";
    private static final String TAG_NAME = "productName";
    private static final String TAG_PRICE = "price";
    private static final String TAG_STOCK = "stock";
    private static final String TAG_IMAGE = "image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_item);

        list_makanan = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        lv =findViewById(R.id.listView);
        adapter = new ProductAdapter(this, arraylistproduct);

        lv.setAdapter(adapter);

        mendapatkandata();
    }

    private void mendapatkandata() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(CartActivity.this, "No internet Connection!", Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_product,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        arraylistproduct.clear();
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray member = jObj.getJSONArray(TAG_PRODUCT);

                            for (int i = 0; i < member.length(); i++) {
                                JSONObject a = member.getJSONObject(i);

                                String productID = a.getString(TAG_ID);
                                String productName = a.getString(TAG_NAME);
                                double price = a.getDouble(TAG_PRICE);
                                int stock = a.getInt(TAG_STOCK);
                                String image = a.getString(TAG_IMAGE);

                                HashMap<String, String> map = new HashMap<>();
                                map.put("productID", productID);
                                map.put("productName", productName);
                                map.put("price", String.valueOf(price));
                                map.put("stock", String.valueOf(stock));
                                map.put("image", image);
                                list_makanan.add(map);

                                product = new ProductDomain(productID, productName, price, stock, image);
                                arraylistproduct.add(product);
                                adapter.notifyDataSetChanged();
                            }
                            progressBar.setVisibility(View.GONE);
                            ListAdapter adapter = new SimpleAdapter(
                                    CartActivity.this, list_makanan,
                                    R.layout.list_item, new String[]{
                                    TAG_ID, TAG_NAME, TAG_PRICE, TAG_STOCK, TAG_IMAGE}, new int[]{R.id.edtproductID,
                                    R.id.edtproductName, R.id.edtPrice, R.id.edtStock, R.id.edtImage});
                            lv.setAdapter(adapter);
                        } catch (Exception ex) {
                            Log.e("Error", ex.toString());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null) {
                    Log.e("Error: ", error.getMessage());
                } else {
                    Log.e("Error: ", "Unknown error occurred");
                }
                Toast.makeText(CartActivity.this, "Silahkan cek koneksi Internet Anda!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        lv.setAdapter(lv.getAdapter());
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                adapter.notifyDataSetChanged();

                CharSequence[] dialogitem = {"Edit data!"};
                builder.setTitle(arraylistproduct.get(position).getProductName());
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), EditItemActivity.class).
                                putExtra("position", position));
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.create().show();
                return false;
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}