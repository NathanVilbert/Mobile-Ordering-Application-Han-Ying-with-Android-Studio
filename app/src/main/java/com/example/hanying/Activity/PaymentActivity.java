package com.example.hanying.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hanying.R;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        double totalAmount = getIntent().getDoubleExtra("TOTAL_AMOUNT", 0.0);

        TextView textView6 = findViewById(R.id.textView6);
        textView6.setText(String.valueOf(totalAmount));

        Button payNowButton = findViewById(R.id.button3);
        payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform any necessary payment processing here

                // Assuming payment is successful, navigate to MainActivity
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                startActivity(intent);

                // Show a toast message indicating successful payment
                Toast.makeText(PaymentActivity.this, "Successfully paid $" + totalAmount + "! Please kindly await the delivery of your order.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
