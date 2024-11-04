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
import com.example.hanying.Adapter.UserAdapter;
import com.example.hanying.Domain.UserDomain;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditUserActivity extends AppCompatActivity {

    UserAdapter adapter;
    public static ArrayList<UserDomain> arraylistuser = new ArrayList<>();
    EditText edtusername, edtcustname, edtcustemail, edtcustphone, edtrole, edtpassword;
    Button btnUpdate, btnHapus, btnKembali;
    String url_update_user = "http://192.168.1.2/hanying/edit_user.php";
    String url_delete_user = "http://192.168.1.2/hanying/delete_user.php";
    private int position;
    private AlertDialog.Builder alertDialogBuilder;
    String username, custname, custemail, custphone, role, password;
    UserDomain UserDomain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            position = intent.getExtras().getInt("position",-1);
        } else {
            finish();
            return;
        }
        //position = intent.getExtras().getInt("position");
        setContentView(R.layout.activity_edit_user);
        btnUpdate = findViewById(R.id.btnedit);
        btnHapus = findViewById(R.id.btnhapus);
        btnKembali = findViewById(R.id.btnbatal);
        edtusername = findViewById(R.id.edtusername);
        edtcustname = findViewById(R.id.edtcustname);
        edtcustemail = findViewById(R.id.edtcustemail);
        edtcustphone = findViewById(R.id.edtcustphone);
        edtrole = findViewById(R.id.edtrole);
        edtpassword = findViewById(R.id.edtpassword);

        edtusername.setText(UserActivity.arraylistuser.get(position).getUsername());
        edtcustname.setText(UserActivity.arraylistuser.get(position).getCustname());
        edtcustemail.setText(UserActivity.arraylistuser.get(position).getCustemail());
        edtcustphone.setText(UserActivity.arraylistuser.get(position).getCustphone());
        edtrole.setText(UserActivity.arraylistuser.get(position).getRole());
        edtpassword.setText(UserActivity.arraylistuser.get(position).getPassword());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtusername.getText().toString().isEmpty()) {
                    // Your custom logic for handling empty username
                    Toast.makeText(EditUserActivity.this, "Please input your username...", Toast.LENGTH_SHORT).show();
                } else if (edtcustname.getText().toString().isEmpty()) {
                    Toast.makeText(EditUserActivity.this, "please enter your customer name...", Toast.LENGTH_SHORT).show();
                } else if (edtcustemail.getText().toString().isEmpty()) {
                    Toast.makeText(EditUserActivity.this, "Please input your customer email...", Toast.LENGTH_SHORT).show();
                } else if (edtcustphone.getText().toString().isEmpty()) {
                    Toast.makeText(EditUserActivity.this, "Please input your customer number phone...", Toast.LENGTH_SHORT).show();
                } else if (edtrole.getText().toString().isEmpty()) {
                    Toast.makeText(EditUserActivity.this, "Please input your the role...", Toast.LENGTH_SHORT).show();
                } else if (edtpassword.getText().toString().isEmpty()) {
                    Toast.makeText(EditUserActivity.this, "Please input your password...", Toast.LENGTH_SHORT).show();
                } else {
                    callPUTDataMethod(edtusername.getText().toString(), edtcustname.getText().toString(), edtcustemail.getText().toString(), edtcustphone.getText().toString(), edtrole.getText().toString(), edtpassword.getText().toString());
                }
            }

            });
        adapter = new UserAdapter(this, arraylistuser);

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtcustname.getText().toString().isEmpty()) {
                    Toast.makeText(EditUserActivity.this, "please enter your data", Toast.LENGTH_SHORT).show();
                } else if (edtcustemail.getText().toString().isEmpty()) {
                    Toast.makeText(EditUserActivity.this, "please enter your data", Toast.LENGTH_SHORT).show();
                } else if (edtcustphone.getText().toString().isEmpty()) {
                    Toast.makeText(EditUserActivity.this, "please enter your data", Toast.LENGTH_SHORT).show();
                } else if (edtrole.getText().toString().isEmpty()) {
                    Toast.makeText(EditUserActivity.this, "please enter your data", Toast.LENGTH_SHORT).show();
                } else if (edtpassword.getText().toString().isEmpty()) {
                    Toast.makeText(EditUserActivity.this, "please enter your data", Toast.LENGTH_SHORT).show();
                } else
                    deletedata2(UserActivity.arraylistuser.get(position).getUsername(),
                            UserActivity.arraylistuser.get(position).getCustname(),
                            UserActivity.arraylistuser.get(position).getCustemail(),
                            UserActivity.arraylistuser.get(position).getCustphone(),
                            UserActivity.arraylistuser.get(position).getRole(),
                            UserActivity.arraylistuser.get(position).getPassword());
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
        final ProgressDialog progressDialog = new ProgressDialog(EditUserActivity.this);
        progressDialog.show();

        Toast.makeText(EditUserActivity.this, "Kembali ke menu utama!!!.....", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
        finish();
        progressDialog.dismiss();
    }

    private void deletedata2(final String username, final String custname, final String custemail, final String custphone, final String role, final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting.........");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, url_delete_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            adapter.notifyDataSetChanged();

                            AlertDialog.Builder builder = new AlertDialog.Builder(EditUserActivity.this);
                            builder.setCancelable(true);
                            builder.setTitle("Yakin ingin menghapus?");
                            builder.setMessage("Data yang sudah dihapus tidak dapat dikembalikan");

                            builder.setPositiveButton("YAKIN",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            edtusername.setText("");
                                            edtcustname.setText("");
                                            edtcustemail.setText("");
                                            edtcustphone.setText("");
                                            edtrole.setText("");
                                            edtpassword.setText("");

                                            Toast.makeText(EditUserActivity.this, "Data Deleted Successfully...", Toast.LENGTH_SHORT).show();

                                            startActivity(new Intent(getApplicationContext(), AdminActivity.class));

                                            finish();
                                            progressDialog.dismiss();

                                            if (!arraylistuser.isEmpty() && position < arraylistuser.size()) {
                                                arraylistuser.remove(position);
                                                if (!UserActivity.arraylistuser.isEmpty() && position < UserActivity.arraylistuser.size()) {
                                                    UserActivity.arraylistuser.remove(position);
                                                } else {
                                                    Log.e("DEBUG", "Invalid position or empty arraylistuser");
                                                }
                                                adapter.notifyDataSetChanged();
                                            } else {
                                                Log.e("DEBUG","Invalid position or empty arraylistuser");
                                            }
                                        }
                                    });
                            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(EditUserActivity.this, "Kamu menekan cancel, perhatikan data yang akan anda hapus kembali !~!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(EditUserActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(EditUserActivity.this,"Fail to delete data...", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("custname", custname);
                params.put("custemail", custemail);
                params.put("custphone", custphone);
                params.put("role", role);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void callPUTDataMethod(String username, String custname, String custemail, String custphone, String role, String password) {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        StringRequest request = new StringRequest(Request.Method.POST, url_update_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("DEBUG", "response" + response);
                        adapter.notifyDataSetChanged();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            edtusername.setText("");
                            edtcustname.setText("");
                            edtcustemail.setText("");
                            edtcustphone.setText("");
                            edtrole.setText("");
                            edtpassword.setText("");

                            Toast.makeText(EditUserActivity.this, "Data updated..", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getApplicationContext(), AdminActivity.class));

                            if (!arraylistuser.isEmpty() && position < arraylistuser.size()) {
                                arraylistuser.get(position).setUsername(username);
                                arraylistuser.get(position).setCustname(custname);
                                arraylistuser.get(position).setCustemail(custemail);
                                arraylistuser.get(position).setCustphone(custphone);
                                arraylistuser.get(position).setRole(role);
                                arraylistuser.get(position).setPassword(password);

                                populateData();
                            } else {
                                Log.e("DEBUG", "Invalid position or empty arraylistuser");
                            }

                            finish();
                        } catch (JSONException jsonObject) {
                            jsonObject.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditUserActivity.this, "Fail to update data..", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                adapter.notifyDataSetChanged();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("custname", custname);
                params.put("custemail", custemail);
                params.put("custphone", custphone);
                params.put("role", role);
                params.put("password", password);

                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(EditUserActivity.this);
        queue.add(request);
    }

    private void populateData() {
        edtusername.setText(arraylistuser.get(position).getUsername());
        edtcustname.setText(arraylistuser.get(position).getCustname());
        edtcustemail.setText(arraylistuser.get(position).getCustemail());
        edtcustphone.setText(arraylistuser.get(position).getCustphone());
        edtrole.setText(arraylistuser.get(position).getRole());
        edtpassword.setText(arraylistuser.get(position).getPassword());
    }
}