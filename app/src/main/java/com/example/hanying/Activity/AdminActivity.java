package com.example.hanying.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hanying.R;


public class AdminActivity extends AppCompatActivity {

    private Button lihatAnggota, tambahAnggota, lihatuser, tambahuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        lihatAnggota = findViewById(R.id.btnProduct);
        tambahAnggota = findViewById(R.id.btnTambah);
        lihatuser = findViewById(R.id.btnuser);
        tambahuser = findViewById(R.id.btnadduser);

        tambahAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddItemActivity.class));
            }
        });

        lihatAnggota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });

        lihatuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
            }
        });

        tambahuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddUserActivity.class));
            }
        });
    }
}
