package com.example.a1_jen_henry_faraz_safdar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etHours, etRate;
    private Button btnCalculate;
    private TextView tvResults;
    public static ArrayList<String> paymentList = new ArrayList<>();
    private DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etHours = findViewById(R.id.etHours);
        etRate = findViewById(R.id.etRate);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResults = findViewById(R.id.tvResults);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatePay();
            }
        });

    }

    private void calculatePay() {
        String hoursStr = etHours.getText().toString();
        String rateStr = etRate.getText().toString();

        if (hoursStr.isEmpty() || rateStr.isEmpty()) {
            Toast.makeText(this, "Please enter both hours and rate", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double hours = Double.parseDouble(hoursStr);
            double rate = Double.parseDouble(rateStr);

            if (hours < 0 || rate < 0) {
                Toast.makeText(this, "Please enter positive values", Toast.LENGTH_SHORT).show();
                return;
            }

            double regularPay, overtimePay, totalPay, tax;

            if (hours <= 40) {
                regularPay = hours * rate;
                overtimePay = 0;
            } else {
                regularPay = 40 * rate;
                overtimePay = (hours - 40) * rate * 1.5;
            }

            totalPay = regularPay + overtimePay;
            tax = totalPay * 0.18;

            String results = "Regular Pay: $" + df.format(regularPay) + "\n" +
                    "Overtime Pay: $" + df.format(overtimePay) + "\n" +
                    "Total Pay: $" + df.format(totalPay) + "\n" +
                    "Tax: $" + df.format(tax);

            tvResults.setText(results);

            // Add to payment list
            String paymentEntry = "Hours: " + hours + ", Rate: $" + rate +
                    ", Total: $" + df.format(totalPay);
            paymentList.add(paymentEntry);

            Toast.makeText(this, "Payment calculated successfully!", Toast.LENGTH_SHORT).show();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_detail) {
            Intent intent = new Intent(this, DetailActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}