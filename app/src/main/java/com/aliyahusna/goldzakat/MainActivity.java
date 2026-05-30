package com.aliyahusna.goldzakat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etValue;
    RadioButton rbKeep, rbWear;
    Button btnCalculate;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etWeight = findViewById(R.id.etWeight);
        etValue = findViewById(R.id.etValue);

        rbKeep = findViewById(R.id.rbKeep);
        rbWear = findViewById(R.id.rbWear);

        btnCalculate = findViewById(R.id.btnCalculate);

        tvResult = findViewById(R.id.tvResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String weightText =
                        etWeight.getText().toString().trim();

                String valueText =
                        etValue.getText().toString().trim();

                // Gold Weight kosong
                if (weightText.isEmpty()) {

                    etWeight.setError(
                            "Gold Weight is required.\nExample: 200.00");

                    etWeight.requestFocus();
                    return;
                }

                // Gold Value kosong
                if (valueText.isEmpty()) {

                    etValue.setError(
                            "Current Gold Value is required.\nExample: 520.00");

                    etValue.requestFocus();
                    return;
                }

                // Keep/Wear tak dipilih
                if (!rbKeep.isChecked() && !rbWear.isChecked()) {

                    tvResult.setText(
                            "Please select Keep or Wear.");

                    return;
                }

                try {

                    double weight =
                            Double.parseDouble(weightText);

                    double value =
                            Double.parseDouble(valueText);

                    // Weight negatif atau kosong
                    if (weight <= 0) {

                        etWeight.setError(
                                "Weight must be greater than 0.\nExample: 200.00");

                        etWeight.requestFocus();
                        return;
                    }

                    // Value negatif atau kosong
                    if (value <= 0) {

                        etValue.setError(
                                "Value must be greater than 0.\nExample: 520.00");

                        etValue.requestFocus();
                        return;
                    }

                    double uruf;

                    if (rbKeep.isChecked()) {
                        uruf = 85;
                    } else {
                        uruf = 200;
                    }

                    double totalGoldValue =
                            weight * value;

                    double zakatWeight =
                            weight - uruf;

                    if (zakatWeight < 0) {
                        zakatWeight = 0;
                    }

                    double zakatPayableValue =
                            zakatWeight * value;

                    double totalZakat =
                            zakatPayableValue * 0.025;

                    tvResult.setText(
                            "Total Gold Value : RM " + totalGoldValue +
                                    "\n\nZakat Payable Value : RM " + zakatPayableValue +
                                    "\n\nTotal Zakat : RM " + totalZakat);

                }
                catch (NumberFormatException e) {

                    tvResult.setText(
                            "Invalid input.\nPlease enter numbers only.\nExample: 200.00");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_about) {

            Intent intent =
                    new Intent(MainActivity.this,
                            AboutActivity.class);

            startActivity(intent);

            return true;
        }

        if (id == R.id.menu_share) {

            Intent shareIntent =
                    new Intent(Intent.ACTION_SEND);

            shareIntent.setType("text/plain");

            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "https://github.com/ALIYAHUSNA/GoldZakatCalculator"
            );

            startActivity(
                    Intent.createChooser(
                            shareIntent,
                            "Share Application"
                    )
            );

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}