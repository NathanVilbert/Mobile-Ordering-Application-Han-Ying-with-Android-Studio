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
import com.example.hanying.Domain.UserDomain;
import com.example.hanying.Adapter.UserAdapter;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UserActivity extends AppCompatActivity {

    ProgressBar progressBar;
    UserAdapter adapter;

    public static ArrayList<UserDomain> arraylistuser = new ArrayList<>();
    UserDomain UserDomain;
    ListView lv;
    ArrayList<HashMap<String, String>> list_user;

    String url_get_user = "http://192.168.1.2/hanying/get_user.php";
    private  static final String TAG_USER = "data2";
    private static final String TAG_USERNAME = "username";
    private static final String TAG_CUSTNAME = "custname";
    private static final String TAG_CUSTEMAIL = "custemail";
    private static final String TAG_CUSTPHONE = "custphone";
    private static final String TAG_ROLE = "role";
    private static final String TAG_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user);

        list_user = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        lv = findViewById(R.id.listView);
        adapter = new UserAdapter(this, arraylistuser);

        lv.setAdapter(adapter);
        mendapatkandata2();
    }

    private void mendapatkandata2() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(UserActivity.this, "No internet Connection!", Toast.LENGTH_SHORT).show();
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        arraylistuser.clear();
                        try {
                            JSONObject jObj = new JSONObject(response);
                            JSONArray member = jObj.getJSONArray(TAG_USER);

                            for (int i = 0; i < member.length(); i++) {
                                JSONObject a = member.getJSONObject(i);

                                String username = a.getString(TAG_USERNAME);
                                String custname = a.getString(TAG_CUSTNAME);
                                String custemail = a.getString(TAG_CUSTEMAIL);
                                String custphone = a.getString(TAG_CUSTPHONE);
                                String role = a.getString(TAG_ROLE);
                                String password = a.getString(TAG_PASSWORD);

                                HashMap<String, String> map = new HashMap<>();
                                map.put("username", username);
                                map.put("custname", custname);
                                map.put("custemail", custemail);
                                map.put("custphone", custphone);
                                map.put("role", role);
                                map.put("password", password);
                                list_user.add(map);

                                UserDomain = new UserDomain(username, custname, custemail, custphone, role, password);
                                arraylistuser.add(UserDomain);
                                adapter.notifyDataSetChanged();
                            }
                            progressBar.setVisibility(View.GONE);
                            ListAdapter adapter = new SimpleAdapter(UserActivity.this, list_user,
                                    R.layout.list_user, new String[]{
                                    TAG_USERNAME, TAG_CUSTNAME, TAG_CUSTEMAIL, TAG_CUSTPHONE, TAG_ROLE, TAG_PASSWORD
                            }, new int[]{R.id.text, R.id.edtcustname, R.id.edtcustemail, R.id.edtcustphone, R.id.edtrole, R.id.edtpassword});
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
                Toast.makeText(UserActivity.this,"Silahkan cek koneksi Internet Anda!!", Toast.LENGTH_SHORT).show();
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

                CharSequence[] dialoguser = {"edit data!"};
                builder.setTitle(arraylistuser.get(position).getCustname());
                builder.setItems(dialoguser, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), EditUserActivity.class).
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